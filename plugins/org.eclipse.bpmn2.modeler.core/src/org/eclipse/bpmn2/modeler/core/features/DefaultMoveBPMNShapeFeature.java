/******************************************************************************* 
 * Copyright (c) 2011, 2012 Red Hat, Inc. 
 *  All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 *
 * @author Ivar Meikas
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.core.features;

import org.eclipse.bpmn2.SubProcess;
import org.eclipse.bpmn2.modeler.core.LifecycleEvent;
import org.eclipse.bpmn2.modeler.core.LifecycleEvent.EventType;
import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.core.runtime.TargetRuntime;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.datatypes.ILocation;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IMoveShapeContext;
import org.eclipse.graphiti.features.context.impl.MoveShapeContext;
import org.eclipse.graphiti.features.impl.DefaultMoveShapeFeature;
import org.eclipse.graphiti.mm.algorithms.AbstractText;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;

/**
 * Default Graphiti {@code MoveShapeFeature} class for Shapes.
 * <p>
 */
public class DefaultMoveBPMNShapeFeature extends DefaultMoveShapeFeature {

	/** The shape's x coordinate before the move. */
	protected int preShapeX;
	
	/** The shape's y coordinate before the move. */
	protected int preShapeY;
	
	/**
	 * Instantiates a new default MoveShapeFature.
	 *
	 * @param fp the Feature Provider
	 */
	public DefaultMoveBPMNShapeFeature(IFeatureProvider fp) {
		super(fp);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.features.impl.DefaultMoveShapeFeature#canMoveShape(org.eclipse.graphiti.features.context.IMoveShapeContext)
	 */
	public boolean canMoveShape(IMoveShapeContext context) {
		if (Graphiti.getPeService().getProperty(context.getShape(), RoutingNet.LANE)!=null) {
			return false;
		}
		ContainerShape targetContainer = context.getTargetContainer();
		if (FeatureSupport.isLabelShape(targetContainer))
			return false; // can't move a shape into a label
		
		if (Graphiti.getPeService().getProperty(targetContainer, RoutingNet.LANE)!=null) {
			int x = context.getX();
			int y = context.getY();
			ILocation loc = Graphiti.getPeService().getLocationRelativeToDiagram(targetContainer);
			((MoveShapeContext)context).setX(x + loc.getX());
			((MoveShapeContext)context).setY(y + loc.getY());
			((MoveShapeContext)context).setSourceContainer(targetContainer.getContainer());
			((MoveShapeContext)context).setTargetContainer(targetContainer.getContainer());
		}
		
		boolean doit = true;
//		context.getSourceContainer() != null && context.getSourceContainer().equals(context.getTargetContainer());
		
		LifecycleEvent event = new LifecycleEvent(EventType.PICTOGRAMELEMENT_CAN_MOVE,
				getFeatureProvider(), context, context.getPictogramElement());
		event.doit = doit;
		TargetRuntime.getCurrentRuntime().notify(event);
		return event.doit;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.features.impl.DefaultMoveShapeFeature#moveShape(org.eclipse.graphiti.features.context.IMoveShapeContext)
	 */
	@Override
	public void moveShape(IMoveShapeContext context) {
		TargetRuntime rt = TargetRuntime.getCurrentRuntime();
		rt.notify(new LifecycleEvent(EventType.PICTOGRAMELEMENT_PRE_MOVE,
				getFeatureProvider(), context, context.getPictogramElement()));
		
		super.moveShape(context);
		
		rt.notify(new LifecycleEvent(EventType.PICTOGRAMELEMENT_POST_MOVE,
				getFeatureProvider(), context, context.getPictogramElement()));
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.features.impl.DefaultMoveShapeFeature#preMoveShape(org.eclipse.graphiti.features.context.IMoveShapeContext)
	 */
	@Override
	protected void preMoveShape(IMoveShapeContext context) {
		super.preMoveShape(context);
		preShapeX = 0;
		preShapeX = 0;
		
		if (context.getShape().getGraphicsAlgorithm() != null){
			preShapeX = context.getShape().getGraphicsAlgorithm().getX();
			preShapeY = context.getShape().getGraphicsAlgorithm().getY();
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.features.impl.DefaultMoveShapeFeature#postMoveShape(org.eclipse.graphiti.features.context.IMoveShapeContext)
	 */
	@Override
	protected void postMoveShape(IMoveShapeContext context) {
		DIUtils.updateDIShape(context.getPictogramElement());

		Shape shape = context.getShape();
		// if this shape has a label, align the label so that it is centered below its owner
		for (EObject o : shape.getLink().getBusinessObjects()) {
			if (o instanceof Shape && FeatureSupport.isLabelShape((Shape)o)) {
				// this is it!
				ContainerShape textContainerShape = (ContainerShape)o;
				GraphicsUtil.alignWithShape(
						(AbstractText) textContainerShape.getChildren().get(0).getGraphicsAlgorithm(), 
						textContainerShape,
						shape.getGraphicsAlgorithm().getWidth(),
						shape.getGraphicsAlgorithm().getHeight(),
						shape.getGraphicsAlgorithm().getX(),
						shape.getGraphicsAlgorithm().getY(),
						preShapeX,
						preShapeY
				);
				// make sure the text label is moved to its new parent container as well
				if (context.getSourceContainer() != context.getTargetContainer()) {
					context.getTargetContainer().getChildren().add(textContainerShape);
				}
				break;
			}
		}
		
		if (shape instanceof ContainerShape) {
			PictogramElement pe = (PictogramElement) ((ContainerShape)shape).eContainer();
			if (BusinessObjectUtil.containsElementOfType(pe, SubProcess.class)) {
				layoutPictogramElement(pe);
			}
		}

		FeatureSupport.updateConnections(getFeatureProvider(), shape);
		
		for (Connection connection : getDiagram().getConnections()) {
			if (GraphicsUtil.intersects(shape, connection)) {
				if (Graphiti.getPeService().getProperty(connection, RoutingNet.CONNECTION)!=null) {
					FeatureSupport.updateConnection(getFeatureProvider(), connection);
				}
			}
		}

		FeatureSupport.updateCategoryValues(getFeatureProvider(), shape);
		
		for (Anchor a : shape.getAnchors()) {
			for (Connection c : a.getIncomingConnections()) {
				FeatureSupport.updateCategoryValues(getFeatureProvider(), c);
			}
			for (Connection c : a.getOutgoingConnections()) {
				FeatureSupport.updateCategoryValues(getFeatureProvider(), c);
			}
		}

	}
}
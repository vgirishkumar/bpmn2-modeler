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
 * @author Bob Brodt
 ******************************************************************************/

package org.eclipse.bpmn2.modeler.ui.features.activity.subprocess;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.FlowElementsContainer;
import org.eclipse.bpmn2.Participant;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.di.BPMNPlane;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.core.features.choreography.ChoreographyUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.ui.ImageProvider;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.osgi.util.NLS;

/**
 * @author Bob Brodt
 *
 */
public class PullupFeature extends AbstractPushPullFeature {

	protected String description;
	protected ContainerShape containerShape;
	protected FlowElementsContainer businessObject;
	protected BPMNDiagram bpmnDiagram;
	protected BPMNShape bpmnShape;
	protected List<Shape> childShapes = new ArrayList<Shape>();
	protected Rectangle boundingRectangle = null;

	/**
	 * @param fp
	 */
	public PullupFeature(IFeatureProvider fp) {
		super(fp);
	}
	
	@Override
	public String getName() {
	    return Messages.PullupFeature_Name;
	}
	
	@Override
	public String getDescription() {
		if (description==null)
			description = Messages.PullupFeature_Description;
		return description;
	}

	@Override
	public String getImageId() {
		return ImageProvider.IMG_16_PULLUP;
	}

	@Override
	public boolean isAvailable(IContext context) {
		if (context instanceof ICustomContext) {
			PictogramElement[] pes = ((ICustomContext)context).getPictogramElements();
			if (pes != null && pes.length == 1) {
				PictogramElement pe = pes[0];
				if (!ChoreographyUtil.isChoreographyParticipantBand(pe)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1 && pes[0] instanceof ContainerShape) {
			containerShape = (ContainerShape) pes[0];
			if (ChoreographyUtil.isChoreographyParticipantBand(containerShape))
				return false;
			
			Object bo = getBusinessObjectForPictogramElement(containerShape);
			description = NLS.bind(
				Messages.PullupFeature_Description_1,
				ModelUtil.getLabel(bo)
			);
			
			if (bo instanceof Participant) {
				Diagram diagram = Graphiti.getPeService().getDiagramForShape(containerShape);
				boolean isReference = FeatureSupport.isParticipantReference(diagram, (Participant)bo);
				if (isReference)
					return false;
				bo = ((Participant)bo).getProcessRef();
			}
			if (bo instanceof FlowElementsContainer) {
				return FeatureSupport.hasBpmnDiagram((FlowElementsContainer)bo);
			}
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.features.custom.ICustomFeature#execute(org.eclipse.graphiti.features.context.ICustomContext)
	 */
	@Override
	public void execute(ICustomContext context) {
		// we already know there's one and only one PE element in canExecute() and that it's
		// a ContainerShape for an expandable activity
		containerShape = (ContainerShape)context.getPictogramElements()[0];
		Object bo = getBusinessObjectForPictogramElement(containerShape);

		bpmnDiagram = DIUtils.findBPMNDiagram(containerShape);
		bpmnShape = null;
		if (bo instanceof Participant) {
			bpmnShape = DIUtils.findBPMNShape(bpmnDiagram, (Participant)bo);
			businessObject = ((Participant)bo).getProcessRef();
		}
		else if (bo instanceof FlowElementsContainer) {
			bpmnShape = DIUtils.findBPMNShape(bpmnDiagram, (FlowElementsContainer)bo);
			businessObject = (FlowElementsContainer)bo;
		}
		
		// find out which BPMNPlane this sub process lives in - this will be the new home
		// for the DI elements in the existing BPMNDiagram.
		BPMNDiagram newBpmnDiagram = DIUtils.getBPMNDiagram(bpmnShape);
		BPMNPlane newPlane = newBpmnDiagram.getPlane();
		Diagram newDiagram = DIUtils.findDiagram(getDiagramBehavior(), newBpmnDiagram);
		
		BPMNDiagram oldBpmnDiagram = DIUtils.findBPMNDiagram(businessObject);
		BPMNPlane oldPlane = oldBpmnDiagram.getPlane();
		Diagram oldDiagram = DIUtils.findDiagram(getDiagramBehavior(), oldBpmnDiagram);
		
		// copy the elements into the same plane as the container
		newPlane.getPlaneElement().addAll(oldPlane.getPlaneElement());
		
		// collect all Shapes from old diagram
		collectChildShapes(oldDiagram, childShapes);
		
		// calculate bounding rectangle for all children shapes
		boundingRectangle = GraphicsUtil.getBoundingRectangle(childShapes);
		// Shapes added to Pools will have different offsets depending on
		// whether the Pool is horizontal or vertical
		Point offset = getChildOffset(containerShape);
		boundingRectangle.x -= MARGIN + offset.getX();
		boundingRectangle.y -= MARGIN + offset.getY();
		boundingRectangle.width += 2 * MARGIN + offset.getX();
		boundingRectangle.height += 2 * MARGIN + offset.getY();

		// Move shapes into container and translate to top-left corner
		moveChildren(childShapes, oldDiagram, containerShape, boundingRectangle.x, boundingRectangle.y);
		
		// collect all internal Connections on old diagram
		List<Connection> connections = new ArrayList<Connection>();
		connections.addAll(oldDiagram.getConnections());
		newDiagram.getConnections().addAll(connections);
		
		newDiagram.getPictogramLinks().addAll(oldDiagram.getPictogramLinks());
		newDiagram.getColors().addAll(oldDiagram.getColors());
		newDiagram.getFonts().addAll(oldDiagram.getFonts());
		newDiagram.getStyles().addAll(oldDiagram.getStyles());
		
		// get rid of the old BPMNDiagram
		DIUtils.deleteDiagram(getDiagramBehavior(), oldBpmnDiagram);
		
		// tell feature provider to reload the active diagram
		getFeatureProvider().getDiagramTypeProvider().resourceReloaded(newDiagram);
		
		FeatureSupport.updateConnections(getFeatureProvider(), connections, true);

		// expand the sub process
		if (FeatureSupport.isExpandableElement(businessObject)) {
			bpmnShape.setIsExpanded(false);
			ExpandFlowNodeFeature expandFeature = new ExpandFlowNodeFeature(getFeatureProvider());
			expandFeature.execute(context);
		}
	}
	
	protected void collectChildShapes(ContainerShape containerShape, List<Shape> shapes) {
		shapes.addAll(containerShape.getChildren());
	}
}

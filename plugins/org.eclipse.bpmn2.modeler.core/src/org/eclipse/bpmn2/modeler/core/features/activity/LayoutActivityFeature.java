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
package org.eclipse.bpmn2.modeler.core.features.activity;


import java.util.Iterator;

import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.SubProcess;
import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.core.features.DefaultLayoutBPMNShapeFeature;
import org.eclipse.bpmn2.modeler.core.features.GraphitiConstants;
import org.eclipse.bpmn2.modeler.core.features.event.AbstractBoundaryEventOperation;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;

// TODO: Auto-generated Javadoc
/**
 * The Class LayoutActivityFeature.
 */
public class LayoutActivityFeature extends DefaultLayoutBPMNShapeFeature {

	/**
	 * Instantiates a new layout activity feature.
	 *
	 * @param fp the fp
	 */
	public LayoutActivityFeature(IFeatureProvider fp) {
		super(fp);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.features.DefaultLayoutBPMNShapeFeature#canLayout(org.eclipse.graphiti.features.context.ILayoutContext)
	 */
	@Override
	public boolean canLayout(ILayoutContext context) {
		Object bo = BusinessObjectUtil.getFirstElementOfType(context.getPictogramElement(), BaseElement.class);
		return bo != null && bo instanceof Activity;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.features.DefaultLayoutBPMNShapeFeature#layout(org.eclipse.graphiti.features.context.ILayoutContext)
	 */
	@Override
	public boolean layout(ILayoutContext context) {
		ContainerShape containerShape = (ContainerShape) context.getPictogramElement();
		GraphicsAlgorithm parentGa = containerShape.getGraphicsAlgorithm();
		int newWidth = parentGa.getWidth();
		int newHeight = parentGa.getHeight();

		GraphicsUtil.setActivityMarkerOffest(containerShape, getMarkerContainerOffset());
		GraphicsUtil.layoutActivityMarkerContainer(containerShape);
		
		Iterator<Shape> iterator = Graphiti.getPeService().getAllContainedShapes(containerShape).iterator();
		while (iterator.hasNext()) {
			Shape shape = iterator.next();
			GraphicsAlgorithm ga = shape.getGraphicsAlgorithm();
			IGaService gaService = Graphiti.getGaService();


//			String markerProperty = Graphiti.getPeService().getPropertyValue(shape,
//					GraphicsUtil.ACTIVITY_MARKER_CONTAINER);
//			if (markerProperty != null && new Boolean(markerProperty)) {
//				int x = (newWidth / 2) - (ga.getWidth() / 2);
//				int y = newHeight - ga.getHeight() - 3 - getMarkerContainerOffset();
//				gaService.setLocation(ga, x, y);
//				continue;
//			}

			Shape rectShape = FeatureSupport.getShape(containerShape, GraphitiConstants.IS_ACTIVITY, Boolean.toString(true));
			gaService.setSize(rectShape.getGraphicsAlgorithm(), newWidth, newHeight);
			layoutInRectangle((RoundedRectangle) rectShape.getGraphicsAlgorithm());

			Object[] objects = getAllBusinessObjectsForPictogramElement(shape);
			for (Object bo : objects) {
				layoutHook(shape, ga, bo, newWidth, newHeight);
			}
		}

		Activity activity = BusinessObjectUtil.getFirstElementOfType(containerShape, Activity.class);
		new AbstractBoundaryEventOperation() {
			@Override
			protected void doWorkInternal(ContainerShape container) {
				layoutPictogramElement(container);
			}
		}.doWork(activity, getDiagram());

		DIUtils.updateDIShape(containerShape);
		layoutConnections(containerShape);
		
		if (containerShape.eContainer() instanceof ContainerShape) {
			PictogramElement pe = (PictogramElement) containerShape.eContainer();
			if (BusinessObjectUtil.containsElementOfType(pe, SubProcess.class)) {
				layoutPictogramElement(pe);
			}
		}
		return true;
	}

	/**
	 * Gets the marker container offset.
	 *
	 * @return the marker container offset
	 */
	protected int getMarkerContainerOffset() {
		return 0;
	}

	/**
	 * Layout in rectangle.
	 *
	 * @param rect the rect
	 */
	protected void layoutInRectangle(RoundedRectangle rect) {
	}

	/**
	 * Layout hook.
	 *
	 * @param shape the shape
	 * @param ga the ga
	 * @param bo the bo
	 * @param newWidth the new width
	 * @param newHeight the new height
	 * @return true, if successful
	 */
	protected boolean layoutHook(Shape shape, GraphicsAlgorithm ga, Object bo, int newWidth, int newHeight) {
		return false;
	}
}
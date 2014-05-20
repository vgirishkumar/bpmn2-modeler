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
package org.eclipse.bpmn2.modeler.core.features.choreography;

import java.util.List;

import org.eclipse.bpmn2.ChoreographyActivity;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.di.ParticipantBandKind;
import org.eclipse.bpmn2.modeler.core.features.AbstractLayoutBpmn2ShapeFeature;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.context.impl.UpdateContext;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeService;

public class LayoutChoreographyFeature extends AbstractLayoutBpmn2ShapeFeature {

	protected IPeService peService = Graphiti.getPeService();
	protected IGaService gaService = Graphiti.getGaService();

	public LayoutChoreographyFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canLayout(ILayoutContext context) {
		return BusinessObjectUtil.getFirstElementOfType(context.getPictogramElement(), ChoreographyActivity.class) != null;
	}

	@Override
	public boolean layout(ILayoutContext context) {
		ContainerShape choreographyActivityContainer = (ContainerShape) context.getPictogramElement();
		GraphicsAlgorithm parentGa = choreographyActivityContainer.getGraphicsAlgorithm();

		int newWidth = parentGa.getWidth();
		int newHeight = parentGa.getHeight();

		Shape rectShape = choreographyActivityContainer.getChildren().get(0);
		gaService.setSize(rectShape.getGraphicsAlgorithm(), newWidth, newHeight);
		
		int height = choreographyActivityContainer.getGraphicsAlgorithm().getHeight();
		int minY = height;
		List<ContainerShape> bandShapes = FeatureSupport.getParticipantBandContainerShapes(choreographyActivityContainer);
		for (ContainerShape b : bandShapes) {
			BPMNShape bpmnShape = BusinessObjectUtil.getFirstElementOfType(b, BPMNShape.class);
			ParticipantBandKind bandKind = bpmnShape.getParticipantBandKind();
			if (bandKind == ParticipantBandKind.BOTTOM_INITIATING ||
					bandKind == ParticipantBandKind.BOTTOM_NON_INITIATING ||
					bandKind == ParticipantBandKind.MIDDLE_NON_INITIATING) {
				int y = b.getGraphicsAlgorithm().getY();
				if (y<minY)
					minY = y;
			}
		}
		GraphicsUtil.setActivityMarkerOffest(choreographyActivityContainer, height - minY);
		GraphicsUtil.layoutActivityMarkerContainer(choreographyActivityContainer);

		IUpdateFeature feature = new UpdateChoreographyLabelFeature(getFeatureProvider());
		IUpdateContext updateContext = new UpdateContext(choreographyActivityContainer);
		feature.update(updateContext);
		return true;
	}
}
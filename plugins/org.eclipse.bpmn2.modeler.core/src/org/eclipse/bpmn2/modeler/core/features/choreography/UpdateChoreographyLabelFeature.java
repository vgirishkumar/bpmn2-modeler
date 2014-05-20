/*******************************************************************************
 * Copyright (c) 2011, 2012, 2013, 2014 Red Hat, Inc.
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

package org.eclipse.bpmn2.modeler.core.features.choreography;

import java.util.List;

import org.eclipse.bpmn2.CallChoreography;
import org.eclipse.bpmn2.GlobalChoreographyTask;
import org.eclipse.bpmn2.SubChoreography;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.di.ParticipantBandKind;
import org.eclipse.bpmn2.modeler.core.features.label.UpdateLabelFeature;
import org.eclipse.bpmn2.modeler.core.preferences.ShapeStyle.LabelPosition;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.graphiti.datatypes.ILocation;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.algorithms.AbstractText;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;

public class UpdateChoreographyLabelFeature extends UpdateLabelFeature {

	/**
	 * @param fp
	 */
	public UpdateChoreographyLabelFeature(IFeatureProvider fp) {
		super(fp);
	}
	

	@Override
	protected LabelPosition getLabelPosition(AbstractText text) {
		PictogramElement pe = text.getPictogramElement();
		Object bo = BusinessObjectUtil.getBusinessObjectForPictogramElement(pe);
		if (FeatureSupport.isElementExpanded(bo)) {
			return LabelPosition.TOP;
		}
		return LabelPosition.CENTER;
	}

	@Override
	protected ContainerShape getTargetContainer(PictogramElement ownerPE) {
		return (ContainerShape) ownerPE;
	}

	@Override
	protected Rectangle getLabelBounds(PictogramElement pe, boolean isAddingLabel, Point offset) {
		Rectangle bounds = super.getLabelBounds(pe, isAddingLabel, offset);
		Object bo = BusinessObjectUtil.getBusinessObjectForPictogramElement(pe);
		if (FeatureSupport.isElementExpanded(bo)) {
			// This is a CallChoreography or SubChoreography so the label will be
			// position at the TOP of the figure. We'll need to adjust this so that
			// if there is a Participant Band at the top, the label needs to be below
			// that band.
			int y = bounds.y;
			int x = bounds.x;
			pe = FeatureSupport.getLabelOwner(pe);
			List<ContainerShape> bandShapes = FeatureSupport.getParticipantBandContainerShapes((ContainerShape)pe);
			for (ContainerShape b : bandShapes) {
				BPMNShape bpmnShape = BusinessObjectUtil.getFirstElementOfType(b, BPMNShape.class);
				ParticipantBandKind bandKind = bpmnShape.getParticipantBandKind();
				if (bandKind == ParticipantBandKind.TOP_INITIATING ||
						bandKind == ParticipantBandKind.TOP_NON_INITIATING) {
					int yb = Graphiti.getLayoutService().getLocationRelativeToDiagram(b).getY() + b.getGraphicsAlgorithm().getHeight();
					if (yb>y)
						y = yb;
				}
			}
			ILocation loc = Graphiti.getPeLayoutService().getLocationRelativeToDiagram((ContainerShape)pe);
			if (bo instanceof CallChoreography) {
				CallChoreography cc = (CallChoreography) bo;
				if (!(cc.getCalledChoreographyRef() instanceof GlobalChoreographyTask)) {
					x = loc.getX() + 5;
				}
			}
			if (bo instanceof SubChoreography) {
				x = loc.getX() + 5;
			}
			bounds.setX(x);
			bounds.setY(y);
		}
		return bounds;
	}
}
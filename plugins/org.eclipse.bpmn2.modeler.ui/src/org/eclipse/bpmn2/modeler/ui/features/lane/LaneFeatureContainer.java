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
 * @author Innar Made
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.ui.features.lane;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Lane;
import org.eclipse.bpmn2.modeler.core.features.BaseElementFeatureContainer;
import org.eclipse.bpmn2.modeler.core.features.MultiAddFeature;
import org.eclipse.bpmn2.modeler.core.features.MultiUpdateFeature;
import org.eclipse.bpmn2.modeler.core.features.activity.UpdateActivityCompensateMarkerFeature;
import org.eclipse.bpmn2.modeler.core.features.activity.UpdateActivityLoopAndMultiInstanceMarkerFeature;
import org.eclipse.bpmn2.modeler.core.features.label.AddShapeLabelFeature;
import org.eclipse.bpmn2.modeler.core.features.label.UpdateLabelFeature;
import org.eclipse.bpmn2.modeler.core.features.lane.AddLaneFeature;
import org.eclipse.bpmn2.modeler.core.features.lane.DirectEditLaneFeature;
import org.eclipse.bpmn2.modeler.core.features.lane.LayoutLaneFeature;
import org.eclipse.bpmn2.modeler.core.features.lane.MoveLaneFeature;
import org.eclipse.bpmn2.modeler.core.features.lane.ResizeLaneFeature;
import org.eclipse.bpmn2.modeler.core.features.lane.UpdateLaneFeature;
import org.eclipse.bpmn2.modeler.core.preferences.ShapeStyle.LabelPosition;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.ui.features.AbstractDefaultDeleteFeature;
import org.eclipse.bpmn2.modeler.ui.features.activity.AppendActivityFeature;
import org.eclipse.bpmn2.modeler.ui.features.activity.task.BusinessRuleTaskFeatureContainer.AddBusinessRuleTask;
import org.eclipse.bpmn2.modeler.ui.features.choreography.AddChoreographyMessageFeature;
import org.eclipse.bpmn2.modeler.ui.features.participant.RotatePoolFeature;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.algorithms.AbstractText;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;

public class LaneFeatureContainer extends BaseElementFeatureContainer {

	@Override
	public boolean canApplyTo(Object o) {
		return super.canApplyTo(o) && o instanceof Lane;
	}

	@Override
	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new CreateLaneFeature(fp);
	}

	@Override
	public IAddFeature getAddFeature(IFeatureProvider fp) {
		MultiAddFeature multiAdd = new MultiAddFeature(fp);
		multiAdd.addFeature(new AddLaneFeature(fp));
		multiAdd.addFeature(new AddShapeLabelFeature(fp));
		return multiAdd;
	}

	@Override
	public IUpdateFeature getUpdateFeature(IFeatureProvider fp) {
		MultiUpdateFeature multiUpdate = new MultiUpdateFeature(fp);
		multiUpdate.addFeature(new UpdateLaneFeature(fp));
		multiUpdate.addFeature(new UpdateLabelFeature(fp) {
			
			@Override
			protected int getLabelWidth(AbstractText text) {
				PictogramElement pe = FeatureSupport.getLabelOwner(text);
				if (FeatureSupport.isHorizontal((ContainerShape)pe))
					return text.getHeight();
				return text.getWidth();
			}

			@Override
			protected int getLabelHeight(AbstractText text) {
				PictogramElement pe = FeatureSupport.getLabelOwner(text);
				if (FeatureSupport.isHorizontal((ContainerShape)pe))
					return text.getWidth();
				return text.getHeight();
			}

			@Override
			protected LabelPosition getLabelPosition(BaseElement element) {
				return LabelPosition.TOP;
			}
			
			@Override
			protected void adjustLabelLocation(PictogramElement pe, boolean isImporting, Point offset) {
				Shape labelShape = FeatureSupport.getLabelShape(pe);
				if (labelShape != null) {
					AbstractText textGA = (AbstractText) labelShape.getGraphicsAlgorithm();
					textGA.setHorizontalAlignment(Orientation.ALIGNMENT_TOP);
					Graphiti.getPeService().sendToFront((Shape)FeatureSupport.getLabelOwner(pe));
				}
				super.adjustLabelLocation(pe, isImporting, offset);
			}			
		});
		return multiUpdate;
	}

	@Override
	public IDirectEditingFeature getDirectEditingFeature(IFeatureProvider fp) {
		return new DirectEditLaneFeature(fp);
	}

	@Override
	public ILayoutFeature getLayoutFeature(IFeatureProvider fp) {
		return new LayoutLaneFeature(fp);
	}

	@Override
	public IMoveShapeFeature getMoveFeature(IFeatureProvider fp) {
		return new MoveLaneFeature(fp);
	}

	@Override
	public IResizeShapeFeature getResizeFeature(IFeatureProvider fp) {
		return new ResizeLaneFeature(fp);
	}

	@Override
	public IDeleteFeature getDeleteFeature(IFeatureProvider fp) {
		return new DeleteLaneFeature(fp);
	}

	@Override
	public ICustomFeature[] getCustomFeatures(IFeatureProvider fp) {
		ICustomFeature[] superFeatures = super.getCustomFeatures(fp);
		ICustomFeature[] thisFeatures = new ICustomFeature[1 + superFeatures.length];
		int i;
		for (i=0; i<superFeatures.length; ++i)
			thisFeatures[i] = superFeatures[i];
		thisFeatures[i++] = new RotateLaneFeature(fp);
		return thisFeatures;
	}
}
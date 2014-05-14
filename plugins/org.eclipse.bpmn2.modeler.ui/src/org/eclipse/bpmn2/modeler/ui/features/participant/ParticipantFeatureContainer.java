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
package org.eclipse.bpmn2.modeler.ui.features.participant;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Participant;
import org.eclipse.bpmn2.modeler.core.features.BaseElementFeatureContainer;
import org.eclipse.bpmn2.modeler.core.features.MultiAddFeature;
import org.eclipse.bpmn2.modeler.core.features.MultiUpdateFeature;
import org.eclipse.bpmn2.modeler.core.features.label.AddShapeLabelFeature;
import org.eclipse.bpmn2.modeler.core.features.label.LabelFeatureContainer;
import org.eclipse.bpmn2.modeler.core.features.label.UpdateLabelFeature;
import org.eclipse.bpmn2.modeler.core.features.participant.AddParticipantFeature;
import org.eclipse.bpmn2.modeler.core.features.participant.DirectEditParticipantFeature;
import org.eclipse.bpmn2.modeler.core.features.participant.LayoutParticipantFeature;
import org.eclipse.bpmn2.modeler.core.features.participant.ResizeParticipantFeature;
import org.eclipse.bpmn2.modeler.core.features.participant.UpdateParticipantFeature;
import org.eclipse.bpmn2.modeler.core.features.participant.UpdateParticipantMultiplicityFeature;
import org.eclipse.bpmn2.modeler.core.preferences.ShapeStyle.LabelPosition;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.ui.features.activity.AppendActivityFeature;
import org.eclipse.bpmn2.modeler.ui.features.activity.subprocess.PullupFeature;
import org.eclipse.bpmn2.modeler.ui.features.activity.subprocess.PushdownFeature;
import org.eclipse.bpmn2.modeler.ui.features.activity.task.BusinessRuleTaskFeatureContainer.AddBusinessRuleTask;
import org.eclipse.bpmn2.modeler.ui.features.choreography.AddChoreographyMessageFeature;
import org.eclipse.bpmn2.modeler.ui.features.choreography.AddChoreographyParticipantFeature;
import org.eclipse.bpmn2.modeler.ui.features.choreography.BlackboxFeature;
import org.eclipse.bpmn2.modeler.ui.features.choreography.RemoveChoreographyMessageFeature;
import org.eclipse.bpmn2.modeler.ui.features.choreography.RemoveChoreographyParticipantFeature;
import org.eclipse.bpmn2.modeler.ui.features.choreography.ShowDiagramPageFeature;
import org.eclipse.bpmn2.modeler.ui.features.choreography.UpdateChoreographyMessageLinkFeature;
import org.eclipse.bpmn2.modeler.ui.features.choreography.WhiteboxFeature;
import org.eclipse.bpmn2.modeler.ui.features.event.AppendEventFeature;
import org.eclipse.bpmn2.modeler.ui.features.gateway.AppendGatewayFeature;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.IRemoveFeature;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.algorithms.AbstractText;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

public class ParticipantFeatureContainer extends BaseElementFeatureContainer {

	@Override
	public boolean canApplyTo(Object o) {
		return super.canApplyTo(o) && o instanceof Participant;
	}

	@Override
	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new CreateParticipantFeature(fp);
	}

	@Override
	public IAddFeature getAddFeature(IFeatureProvider fp) {
		MultiAddFeature multiAdd = new MultiAddFeature(fp);
		multiAdd.addFeature(new AddParticipantFeature(fp));
		multiAdd.addFeature(new AddShapeLabelFeature(fp) {
			
			@Override
			protected AbstractText createText(Shape labelShape, String labelText) {
				// need to override the default MultiText created by super
				// because the Graphiti layout algorithm doesn't work as
				// expected when text angle is -90
				return gaService.createText(labelShape, labelText);
			}

			@Override
			public void applyStyle(AbstractText text, BaseElement be) {
				super.applyStyle(text, be);
				text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
				text.setVerticalAlignment(Orientation.ALIGNMENT_CENTER);
			}

		});
		return multiAdd;
	}

	@Override
	public IUpdateFeature getUpdateFeature(IFeatureProvider fp) {
		MultiUpdateFeature multiUpdate = new MultiUpdateFeature(fp);
		multiUpdate.addFeature(new UpdateParticipantFeature(fp));
		multiUpdate.addFeature(new UpdateParticipantMultiplicityFeature(fp));
		multiUpdate.addFeature(new UpdateChoreographyMessageLinkFeature(fp));
		multiUpdate.addFeature(new UpdateLabelFeature(fp) {

			@Override
			protected LabelPosition getLabelPosition(AbstractText text) {
				if (text.getAngle() == -90)
					return LabelPosition.LEFT;
				return LabelPosition.TOP;
			}

			protected int getLabelWidth(AbstractText text) {
				if (text.getAngle() == -90)
					return getLabelSize(text).height;
				return getLabelSize(text).width;
			}

			protected int getLabelHeight(AbstractText text) {
				if (text.getAngle() == -90)
					return getLabelSize(text).width;
				return getLabelSize(text).height + 2*LabelFeatureContainer.LABEL_MARGIN;
			}

			@Override
			protected void adjustLabelLocation(PictogramElement pe, boolean isAdding, Point offset) {
				Shape labelShape = FeatureSupport.getLabelShape(pe);
				if (labelShape != null) {
					AbstractText textGA = (AbstractText) labelShape.getGraphicsAlgorithm();
					pe = FeatureSupport.getLabelOwner(pe);
					if (FeatureSupport.isHorizontal((ContainerShape) pe)) {
						textGA.setAngle(-90);
					}
					else {
						textGA.setAngle(0);
					}
				}
				super.adjustLabelLocation(pe, isAdding, offset);
			}			
		});
		return multiUpdate;
	}

	@Override
	public IDirectEditingFeature getDirectEditingFeature(IFeatureProvider fp) {
		return new DirectEditParticipantFeature(fp);
	}

	@Override
	public ILayoutFeature getLayoutFeature(IFeatureProvider fp) {
		return new LayoutParticipantFeature(fp);
	}

	@Override
	public IMoveShapeFeature getMoveFeature(IFeatureProvider fp) {
		return new MoveParticipantFeature(fp);
	}

	@Override
	public IResizeShapeFeature getResizeFeature(IFeatureProvider fp) {
		return new ResizeParticipantFeature(fp);
	}

	@Override
	public IDeleteFeature getDeleteFeature(IFeatureProvider fp) {
		return new DeleteParticipantFeature(fp);
	}

	@Override
	public IRemoveFeature getRemoveFeature(IFeatureProvider fp) {
		return new RemoveChoreographyParticipantFeature(fp);
	}

	@Override
	public ICustomFeature[] getCustomFeatures(IFeatureProvider fp) {
		ICustomFeature[] superFeatures = super.getCustomFeatures(fp);
		ICustomFeature[] thisFeatures = new ICustomFeature[8 + superFeatures.length];
		thisFeatures[0] = new ShowDiagramPageFeature(fp);
		int i;
		for (i=0; i<superFeatures.length; ++i)
			thisFeatures[i+1] = superFeatures[i];
		thisFeatures[++i] = new AddChoreographyMessageFeature(fp);
		thisFeatures[++i] = new RemoveChoreographyMessageFeature(fp);
		thisFeatures[++i] = new RotatePoolFeature(fp);
		thisFeatures[++i] = new WhiteboxFeature(fp);
		thisFeatures[++i] = new BlackboxFeature(fp);
		thisFeatures[++i] = new PushdownFeature(fp);
		thisFeatures[++i] = new PullupFeature(fp);
		return thisFeatures;
	}
}
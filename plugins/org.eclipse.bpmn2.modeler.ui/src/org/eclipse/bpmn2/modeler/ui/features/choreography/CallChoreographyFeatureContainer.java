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
package org.eclipse.bpmn2.modeler.ui.features.choreography;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.CallChoreography;
import org.eclipse.bpmn2.modeler.core.features.AbstractCreateFlowElementFeature;
import org.eclipse.bpmn2.modeler.core.features.MultiAddFeature;
import org.eclipse.bpmn2.modeler.core.features.MultiUpdateFeature;
import org.eclipse.bpmn2.modeler.core.features.label.AddShapeLabelFeature;
import org.eclipse.bpmn2.modeler.core.features.label.UpdateLabelFeature;
import org.eclipse.bpmn2.modeler.core.preferences.ShapeStyle.LabelPosition;
import org.eclipse.bpmn2.modeler.ui.ImageProvider;
import org.eclipse.bpmn2.modeler.ui.features.activity.subprocess.AbstractExpandableActivityFeatureContainer;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.algorithms.AbstractText;

public class CallChoreographyFeatureContainer extends AbstractChoreographyFeatureContainer {

	@Override
	public boolean canApplyTo(Object o) {
		return super.canApplyTo(o) && o instanceof CallChoreography;
	}

	@Override
	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new CreateCallChoreographyFeature(fp);
	}

	@Override
	public IAddFeature getAddFeature(IFeatureProvider fp) {
		MultiAddFeature multiAdd = new MultiAddFeature(fp);
		multiAdd.addFeature(new AddCallChoreographyFeature(fp));
		multiAdd.addFeature(new AddShapeLabelFeature(fp));
		return multiAdd;
	}

	@Override
	public MultiUpdateFeature getUpdateFeature(IFeatureProvider fp) {
		MultiUpdateFeature multiUpdate = new MultiUpdateFeature(fp);
		multiUpdate.addFeature(new UpdateChoreographyParticipantRefsFeature(fp) {
			@Override
			protected boolean isShowNames() {
				return false;
			}
		});
		multiUpdate.addFeature(new UpdateChoreographyInitiatingParticipantFeature(fp));
		// multiUpdate.addUpdateFeature(new UpdateChoreographyMarkerFeature(fp)); use it when property editor supports
		// enums
		multiUpdate.addFeature(new UpdateLabelFeature(fp) {

			@Override
			protected LabelPosition getLabelPosition(AbstractText text) {
				if (AbstractExpandableActivityFeatureContainer.isElementExpanded(text)) {
					return LabelPosition.TOP;
				}
				return LabelPosition.CENTER;
			}
			
		});
		return multiUpdate;
	}

	public static class CreateCallChoreographyFeature extends AbstractCreateFlowElementFeature<CallChoreography> {

		public CreateCallChoreographyFeature(IFeatureProvider fp) {
			super(fp, Messages.CallChoreographyFeatureContainer_Name, Messages.CallChoreographyFeatureContainer_Description);
		}

		@Override
		public String getStencilImageId() {
			return ImageProvider.IMG_16_CHOREOGRAPHY_TASK;
		}
		
		public EClass getBusinessObjectClass() {
			return Bpmn2Package.eINSTANCE.getCallChoreography();
		}
	}
}

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
import org.eclipse.bpmn2.Participant;
import org.eclipse.bpmn2.SubChoreography;
import org.eclipse.bpmn2.modeler.core.features.activity.AbstractCreateExpandableFlowNodeFeature;
import org.eclipse.bpmn2.modeler.ui.ImageProvider;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;

public class SubChoreographyFeatureContainer extends AbstractChoreographyFeatureContainer {

	@Override
	public boolean canApplyTo(Object o) {
		return super.canApplyTo(o) &&
				(o instanceof SubChoreography || o instanceof Participant);
	}

	@Override
	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new CreateSubChoreographyFeature(fp);
	}

	@Override
	public IAddFeature getAddFeature(IFeatureProvider fp) {
		return new AddSubChoreographyFeature(fp);
	}

	public static class CreateSubChoreographyFeature extends AbstractCreateExpandableFlowNodeFeature<SubChoreography> {

		public CreateSubChoreographyFeature(IFeatureProvider fp) {
			super(fp);
		}

		/* (non-Javadoc)
		 * @see org.eclipse.bpmn2.modeler.core.features.AbstractCreateFlowElementFeature#getStencilImageId()
		 */
		@Override
		public String getStencilImageId() {
			return ImageProvider.IMG_16_CHOREOGRAPHY_TASK;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.bpmn2.modeler.core.features.AbstractCreateFlowElementFeature#getFlowElementClass()
		 */
		@Override
		public EClass getBusinessObjectClass() {
			return Bpmn2Package.eINSTANCE.getSubChoreography();
		}
	}
}
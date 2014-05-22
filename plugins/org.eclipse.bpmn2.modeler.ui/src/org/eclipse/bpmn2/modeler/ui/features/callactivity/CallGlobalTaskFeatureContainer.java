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
package org.eclipse.bpmn2.modeler.ui.features.callactivity;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.modeler.ui.ImageProvider;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;


public class CallGlobalTaskFeatureContainer extends CallActivityFeatureContainer {
	

	@Override
	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new CreateCallGlobalTaskFeature(fp);
	}

	public static class CreateCallGlobalTaskFeature extends CreateCallActivityFeature {

		/**
		 * @param fp
		 */
		public CreateCallGlobalTaskFeature(IFeatureProvider fp) {
			super(fp);
		}

		@Override
		public Object[] create(ICreateContext context) {
			Object[] elems = super.create(context);
			return elems;
		}

		@Override
		public String getStencilImageId() {
			return ImageProvider.IMG_16_CALL_ACTIVITY;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.bpmn2.modeler.core.features.AbstractCreateFlowElementFeature#getFlowElementClass()
		 */
		@Override
		public EClass getBusinessObjectClass() {
			return Bpmn2Package.eINSTANCE.getGlobalTask();
		}
	}
}
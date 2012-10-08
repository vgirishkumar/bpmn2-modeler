/*******************************************************************************
 * Copyright (c) 2011, 2012 Red Hat, Inc. 
 * All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 *
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 *******************************************************************************/
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.features;

import org.eclipse.bpmn2.IntermediateCatchEvent;
import org.eclipse.bpmn2.modeler.ui.features.event.IntermediateCatchEventFeatureContainer;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;

public class JbpmIntermediateCatchEventFeatureContainer extends IntermediateCatchEventFeatureContainer {

	@Override
	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new JbpmCreateCatchEventFeature(fp);
	}

	public class JbpmCreateCatchEventFeature extends CreateIntermediateCatchEventFeature {

		public JbpmCreateCatchEventFeature(IFeatureProvider fp) {
			super(fp);
		}
		
		@Override
		public IntermediateCatchEvent createBusinessObject(ICreateContext context) {
			IntermediateCatchEvent event = super.createBusinessObject(context);
			event.setName("");
			return event;
		}
	}
}

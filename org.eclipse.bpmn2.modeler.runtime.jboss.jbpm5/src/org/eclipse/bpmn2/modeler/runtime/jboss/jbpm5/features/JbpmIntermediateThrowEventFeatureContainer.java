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

import org.eclipse.bpmn2.IntermediateThrowEvent;
import org.eclipse.bpmn2.modeler.ui.features.event.IntermediateThrowEventFeatureContainer;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;

public class JbpmIntermediateThrowEventFeatureContainer extends IntermediateThrowEventFeatureContainer {

	@Override
	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new JbpmCreateIntermediateThrowEventFeature(fp);
	}

	public class JbpmCreateIntermediateThrowEventFeature extends CreateIntermediateThrowEventFeature {

		public JbpmCreateIntermediateThrowEventFeature(IFeatureProvider fp) {
			super(fp);
		}
		
		@Override
		public IntermediateThrowEvent createBusinessObject(ICreateContext context) {
			IntermediateThrowEvent event = super.createBusinessObject(context);
			event.setName("");
			return event;
		}
	}
}

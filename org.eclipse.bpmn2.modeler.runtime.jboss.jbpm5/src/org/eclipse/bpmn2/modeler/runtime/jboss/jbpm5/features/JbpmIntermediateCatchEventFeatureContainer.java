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

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.IntermediateCatchEvent;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ElementParameters;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.util.JbpmModelUtil;
import org.eclipse.bpmn2.modeler.ui.features.event.IntermediateCatchEventFeatureContainer;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

public class JbpmIntermediateCatchEventFeatureContainer extends IntermediateCatchEventFeatureContainer {

	@Override
	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new JbpmCreateCatchEventFeature(fp);
	}

	@Override
	public IAddFeature getAddFeature(IFeatureProvider fp) {
		return new AddIntermediateCatchEventFeature(fp){

			@Override
			public PictogramElement add(IAddContext context) {
				PictogramElement pe = super.add(context);
				BaseElement be = BusinessObjectUtil.getFirstBaseElement(pe);
				ElementParameters ep = JbpmModelUtil.getElementParameters(be);
				getFeatureProvider().link(pe, ep);
				return pe;
			}
			
		};
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

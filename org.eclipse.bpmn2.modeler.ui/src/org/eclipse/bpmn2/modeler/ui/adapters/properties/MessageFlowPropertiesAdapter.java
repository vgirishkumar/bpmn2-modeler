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
 * @author Bob Brodt
 ******************************************************************************/

package org.eclipse.bpmn2.modeler.ui.adapters.properties;

import org.eclipse.bpmn2.MessageFlow;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.bpmn2.modeler.core.adapters.ObjectDescriptor;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.ui.features.choreography.ChoreographyUtil;
import org.eclipse.emf.common.notify.AdapterFactory;

/**
 * @author Gary Brown
 *
 */
public class MessageFlowPropertiesAdapter extends ExtendedPropertiesAdapter<MessageFlow> {

	/**
	 * @param adapterFactory
	 * @param object
	 */
	public MessageFlowPropertiesAdapter(AdapterFactory adapterFactory, MessageFlow object) {
		super(adapterFactory, object);
		
    	setObjectDescriptor(new ObjectDescriptor<MessageFlow>(adapterFactory, object) {
			@Override
			public String getDisplayName(Object context) {
				final MessageFlow flow = adopt(context);
				String text = "";
				if (flow.getName()!=null)
					text = flow.getName();
				else {
					if (flow.getMessageRef()!=null) {
						text += ChoreographyUtil.getMessageFlowName(flow);
					}
					
					if (flow.getSourceRef() != null) {
						text += "(" + ModelUtil.getDisplayName(flow.getSourceRef())+"->";
						
						if (flow.getTargetRef() != null) {
							text += ModelUtil.getDisplayName(flow.getTargetRef());
						}
						text += ")";
					}
				}
				return text;
			}
    	});
	}

}

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

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.Interface;
import org.eclipse.bpmn2.Message;
import org.eclipse.bpmn2.Operation;
import org.eclipse.bpmn2.Participant;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.ReceiveTask;
import org.eclipse.bpmn2.RootElement;
import org.eclipse.bpmn2.modeler.core.ModelHandler;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * @author Bob Brodt
 *
 */
public class OperationRefFeatureDescriptor<T extends BaseElement> extends RootElementRefFeatureDescriptor<T> {

	/**
	 * @param adapterFactory
	 * @param object
	 * @param feature
	 */
	public OperationRefFeatureDescriptor(AdapterFactory adapterFactory, T object, EStructuralFeature feature) {
		super(adapterFactory, object, feature);
	}
	
	@Override
	public Hashtable<String, Object> getChoiceOfValues(Object context) {
		final T object = adopt(context);
		Hashtable<String,Object> choices = super.getChoiceOfValues(context);

		// collect all defined Interfaces and add their Operations to the list of available choices
		// Whether or not the Interface is actually supported by the underlying Process is a job
		// for validation
		List<Interface> interfaces = new ArrayList<Interface>();
		Definitions definitions = ModelUtil.getDefinitions(object);
		if (definitions!=null) {
			for (RootElement re : definitions.getRootElements()) {
				if (re instanceof Interface) {
					interfaces.add((Interface)re);
				}
			}
		}
		
		for (Interface intf : interfaces) {
			for (Operation operation : intf.getOperations()) {
				choices.put(ModelUtil.getDisplayName(operation), operation);
			}
		}

		return choices;
	}
}

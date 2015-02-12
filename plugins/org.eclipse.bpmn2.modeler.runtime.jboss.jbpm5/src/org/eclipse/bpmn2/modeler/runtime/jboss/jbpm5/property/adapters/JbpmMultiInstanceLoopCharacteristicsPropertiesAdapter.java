/*******************************************************************************
 * Copyright (c) 2011, 2012, 2013 Red Hat, Inc.
 * All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * 	Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.adapters;

import java.util.Hashtable;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.MultiInstanceLoopCharacteristics;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.SubProcess;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.JavaVariableNameObjectEditor;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.ui.adapters.properties.MultiInstanceLoopCharacteristicsPropertiesAdapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class JbpmMultiInstanceLoopCharacteristicsPropertiesAdapter extends MultiInstanceLoopCharacteristicsPropertiesAdapter {

	public JbpmMultiInstanceLoopCharacteristicsPropertiesAdapter(AdapterFactory adapterFactory, MultiInstanceLoopCharacteristics object) {
		super(adapterFactory, object);

		EStructuralFeature feature = Bpmn2Package.eINSTANCE.getMultiInstanceLoopCharacteristics_InputDataItem();
		setProperty(feature, UI_OBJECT_EDITOR_CLASS, JavaVariableNameObjectEditor.class);
		
		feature = Bpmn2Package.eINSTANCE.getMultiInstanceLoopCharacteristics_OutputDataItem();
		setProperty(feature, UI_OBJECT_EDITOR_CLASS, JavaVariableNameObjectEditor.class);

		setFeatureDescriptor(LOOP_DATA_INPUT_REF, new LoopCharacteristicsDataIoFeatureDescriptor(this, object, LOOP_DATA_INPUT_REF) {
			public Hashtable<String, Object> getChoiceOfValues() {
				Hashtable<String, Object> choices = new Hashtable<String, Object>();
				EObject container = ModelUtil.getContainer(object);
				if (container instanceof SubProcess) {
					// get the Property instances (a.k.a. "local variables") of the containing Process or SubProcess
					for (EObject p : ModelUtil.collectAncestorObjects(object, "properties", new Class[] {Process.class, SubProcess.class})) { 
						choices.put( getChoiceString(p), p);
					}
				}
				return choices;
			}
		});
		
		setFeatureDescriptor(LOOP_DATA_OUTPUT_REF, new LoopCharacteristicsDataIoFeatureDescriptor(this, object, LOOP_DATA_OUTPUT_REF) {
			public Hashtable<String, Object> getChoiceOfValues() {
				Hashtable<String, Object> choices = new Hashtable<String, Object>();
				EObject container = ModelUtil.getContainer(object);
				if (container instanceof SubProcess) {
					// get the Property instances (a.k.a. "local variables") of the containing Process or SubProcess
					for (EObject p : ModelUtil.collectAncestorObjects(object, "properties", new Class[] {Process.class, SubProcess.class})) { 
						choices.put( getChoiceString(p), p);
					}
				}
				return choices;
			}
		});
	}

}

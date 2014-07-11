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
import java.util.List;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.DataAssociation;
import org.eclipse.bpmn2.DataInputAssociation;
import org.eclipse.bpmn2.DataOutputAssociation;
import org.eclipse.bpmn2.InputOutputSpecification;
import org.eclipse.bpmn2.ItemAwareElement;
import org.eclipse.bpmn2.ItemDefinition;
import org.eclipse.bpmn2.Message;
import org.eclipse.bpmn2.Task;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.emf.common.notify.AdapterFactory;

/**
 * @author Bob Brodt
 *
 */
public class TaskPropertiesAdapter<T extends Task> extends ActivityPropertiesAdapter<T> {

	/**
	 * @param adapterFactory
	 * @param object
	 */
	public TaskPropertiesAdapter(AdapterFactory adapterFactory, T object) {
		super(adapterFactory, object);
    	setProperty(Bpmn2Package.eINSTANCE.getInteractionNode_IncomingConversationLinks(), UI_CAN_CREATE_NEW, Boolean.FALSE);
    	setProperty(Bpmn2Package.eINSTANCE.getInteractionNode_OutgoingConversationLinks(), UI_CAN_CREATE_NEW, Boolean.FALSE);
	}

	private static boolean typesEqual(ItemDefinition i1, ItemDefinition i2) {
		if (i1==i2)
			return true;
		if (i1==null) {
			if (i2==null)
				return true;
			return false;
		}
		if (i2==null) {
			return false;
		}
		return i1.equals(i2);
	}
	
	protected static void fixDataInputs(Task activity, Message message) {
		// try to match up the given Message data type with one of the
		// Input Association types.
		ItemDefinition messageType = null;
		if (message!=null)
			messageType = message.getItemRef();
		for (DataInputAssociation dia : activity.getDataInputAssociations()) {
			for (ItemAwareElement e : dia.getSourceRef()) {
				ItemDefinition inputType = e.getItemSubjectRef();
				if (typesEqual(messageType, inputType)) {
					return;
				}
			}
		}
		clearDataInputs(activity);
	}
	
	protected static void clearDataInputs(Task activity) {
		List<DataAssociation> deletedAssociations = new ArrayList<DataAssociation>();
		deletedAssociations.addAll(activity.getDataInputAssociations());
		for (DataAssociation da : deletedAssociations) {
			// we can delete the connection line by simply setting either the source
			// or target of the DataAssociation to null
			ExtendedPropertiesAdapter adapter = ExtendedPropertiesAdapter.adapt(da);
			adapter.getFeatureDescriptor(Bpmn2Package.eINSTANCE.getDataAssociation_SourceRef()).setValue(null);
		}
		activity.getDataInputAssociations().clear();
		InputOutputSpecification ioSpec = activity.getIoSpecification();
		if (ioSpec!=null) {
			ioSpec.getDataInputs().clear();
			ioSpec.getInputSets().get(0).getDataInputRefs().clear();
		}
	}

	protected static void fixDataOutputs(Task activity, Message message) {
		// try to match up the given Message data type with one of the
		// Output Association types.
		ItemDefinition messageType = null;
		if (message!=null)
			messageType = message.getItemRef();
		for (DataOutputAssociation doa : activity.getDataOutputAssociations()) {
			ItemAwareElement e = doa.getTargetRef();
			ItemDefinition inputType = e.getItemSubjectRef();
			if (typesEqual(messageType, inputType)) {
				return;
			}
		}
		clearDataOutputs(activity);
	}
	
	protected static void clearDataOutputs(Task activity) {
		// we need to delete the existing Data Associations along with their visuals
		List<DataAssociation> deletedAssociations = new ArrayList<DataAssociation>();
		deletedAssociations.addAll(activity.getDataOutputAssociations());
		for (DataAssociation da : deletedAssociations) {
			// we can delete the connection line by simply setting either the source
			// or target of the DataAssociation to null
			ExtendedPropertiesAdapter adapter = ExtendedPropertiesAdapter.adapt(da);
			adapter.getFeatureDescriptor(Bpmn2Package.eINSTANCE.getDataAssociation_SourceRef()).setValue(null);
		}
		activity.getDataOutputAssociations().clear();
		InputOutputSpecification ioSpec = activity.getIoSpecification();
		if (ioSpec!=null) {
			ioSpec.getDataOutputs().clear();
			ioSpec.getOutputSets().get(0).getDataOutputRefs().clear();
		}
	}
}

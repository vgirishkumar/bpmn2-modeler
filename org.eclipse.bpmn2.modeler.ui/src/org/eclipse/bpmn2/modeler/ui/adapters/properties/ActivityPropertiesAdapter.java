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

import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.AdHocSubProcess;
import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.MultiInstanceLoopCharacteristics;
import org.eclipse.bpmn2.StandardLoopCharacteristics;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.bpmn2.modeler.core.adapters.FeatureDescriptor;
import org.eclipse.bpmn2.modeler.core.model.Bpmn2ModelerFactory;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

/**
 * @author Bob Brodt
 *
 */
public class ActivityPropertiesAdapter<T extends Activity> extends ExtendedPropertiesAdapter<T> {

	/**
	 * @param adapterFactory
	 * @param object
	 */
	public ActivityPropertiesAdapter(AdapterFactory adapterFactory, T object) {
		super(adapterFactory, object);
    	setProperty(Bpmn2Package.eINSTANCE.getActivity_LoopCharacteristics(), UI_CAN_CREATE_NEW, Boolean.FALSE);
    	setProperty(Bpmn2Package.eINSTANCE.getActivity_LoopCharacteristics(), UI_CAN_EDIT, Boolean.FALSE);

		EStructuralFeature feature = Bpmn2Package.eINSTANCE.getActivity_LoopCharacteristics();
    	setProperty(feature, UI_CAN_CREATE_NEW, Boolean.FALSE);
    	setProperty(feature, UI_CAN_EDIT, Boolean.FALSE);
		setFeatureDescriptor(feature,
				new FeatureDescriptor<T>(adapterFactory,object,feature) {
					@Override
					public void setValue(Object context, Object value) {
						final T object = adopt(context);
						if (value instanceof String) {
							if ("MultiInstanceLoopCharacteristics".equals(value)) {
								MultiInstanceLoopCharacteristics lc = Bpmn2ModelerFactory.create(object.eResource(), MultiInstanceLoopCharacteristics.class);
								value = lc;
							}
							else if ("StandardLoopCharacteristics".equals(value)) {
								StandardLoopCharacteristics lc = Bpmn2ModelerFactory.create(object.eResource(), StandardLoopCharacteristics.class);
								value = lc;
							}
						}
						super.setValue(object, value);
					}
				}
			);

		feature = Bpmn2Package.eINSTANCE.getActivity_Properties();
		setFeatureDescriptor(feature,
			new FeatureDescriptor<T>(adapterFactory,object,feature) {
				@Override
				public EObject createFeature(Resource resource, Object context, EClass eclass) {
					T activity = adopt(context);
					return PropertyPropertiesAdapter.createProperty(activity.getProperties());
				}
			}
		);

		if (object instanceof AdHocSubProcess) {
			feature = Bpmn2Package.eINSTANCE.getAdHocSubProcess_CompletionCondition();
			setFeatureDescriptor(feature,
					new FeatureDescriptor<T>(adapterFactory,object,feature) {
						@Override
						public String getLabel(Object context) {
							return "Completion Condition";
						}
					}
				);
		}
	}

}

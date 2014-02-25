/*******************************************************************************
 * Copyright (c) 2011, 2012, 2013, 2014 Red Hat, Inc.
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

package org.eclipse.bpmn2.modeler.core.model;

import org.eclipse.bpmn2.ExtensionAttributeValue;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/**
 * This adapter is added to the dynamic EPackage managed by the ModelDecorator.
 * Clients will use this adapter to find the ModelDecorator instance that owns the EPackage.
 * It is also used by the XMLHandler to resolve object features during loading of a BPMN2 Resource.
 */
public class ModelDecoratorAdapter extends AdapterImpl {
	ModelDecorator modelDecorator;
	
	public static void adapt(ModelDecorator md) {
		for (Adapter a : md.getEPackage().eAdapters()) {
			if (a.getClass() == ModelDecoratorAdapter.class) {
				// this EPackage already has an adapter
				return;
			}
		}
		ModelDecoratorAdapter adapter = new ModelDecoratorAdapter(md);
		md.getEPackage().eAdapters().add(adapter);
	}
	
	private ModelDecoratorAdapter(ModelDecorator modelDecorator) {
		this.modelDecorator = modelDecorator;
	}
	
	public ModelDecorator getModelDecorator() {
		return modelDecorator;
	}

	public EClass getEClass(EObject object) {
		if (object instanceof EClass)
			return modelDecorator.getEClass(((EClass)object).getName());
		// FIXME: The ExtensionAttributeValues container is used to attach EXTENSION ELEMENTS (not attributes)
		// to a BaseElement object (i.e. EXTENSION ELEMENTS are contained by an ExtensionAttributeValue
		// object owned by the BaseElement, not the BaseElement itself). We need to resolve this ownership
		// issue in a central place.
		if (object instanceof ExtensionAttributeValue && object.eContainer()!=null) {
			object = object.eContainer();
		}
		return modelDecorator.getEClass(object.eClass().getName());
	}
	
	public EStructuralFeature getEStructuralFeature(EObject object, EStructuralFeature feature) {
		EClass eClass = getEClass(object);
		if (eClass!=null) {
			feature = eClass.getEStructuralFeature(feature.getName());
			if (feature!=null) {
				modelDecorator.adaptFeature(null, object, feature);
				return feature;
			}
		}
		return null;
	}
	
	public EStructuralFeature getEStructuralFeature(EObject object, String name) {
		EClass eClass = getEClass(object);
		if (eClass!=null) {
			EStructuralFeature feature = eClass.getEStructuralFeature(name);
			if (feature!=null) {
				modelDecorator.adaptFeature(null, object, feature);
				return feature;
			}
		}
		return null;
	}
}
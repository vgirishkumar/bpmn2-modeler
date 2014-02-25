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

import org.eclipse.bpmn2.modeler.core.adapters.AdapterUtil;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.bpmn2.modeler.core.adapters.FeatureDescriptor;
import org.eclipse.bpmn2.modeler.core.adapters.ObjectDescriptor;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.xml.type.AnyType;

/**
 * An ExtendedPropertiesAdapter for handling AnyType objects. This adapter knows how
 * to resolve dynamic attributes and elements, and will create them in the AnyType
 * object if they don't already exist.
 * 
 * The ModelDecorator class is used to determine the set of structural features that
 * have been defined dynamically.
 */
class AnyTypeExtendedPropertiesAdapter extends ExtendedPropertiesAdapter<AnyType> {

	ModelDecorator modelDecorator;

	public AnyTypeExtendedPropertiesAdapter(AdapterFactory adapterFactory, AnyType object) {
		super(adapterFactory, object);
	}

	/**
	 * Look up the ModelDecorator from the given feature. The feature's namespace URI
	 * is used to find the EPackage that is owned by the ModelDecorator. From that we
	 * can find the ModelDecorator instance.
	 * 
	 * @param feature
	 * @return
	 */
	public ModelDecorator getModelDecorator(EStructuralFeature feature) {

		if (modelDecorator==null) {
			String nsURI = ExtendedMetaData.INSTANCE.getNamespace(feature);
			EPackage pkg = ModelDecorator.getEPackage(nsURI);
			if (pkg!=null) {
				ModelDecoratorAdapter mda = AdapterUtil.adapt(pkg, ModelDecoratorAdapter.class);
				modelDecorator = mda.getModelDecorator();
			}
		}
		return modelDecorator;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter#getObjectDescriptor()
	 * This override sets the UI label to be used for the object being adapted. This label can be
	 * found in an EAnnotation in the object's EClass.
	 * 
	 * @see ModelDecorator#getLabel(EClass)
	 */
	@Override
	public ObjectDescriptor<AnyType> getObjectDescriptor() {
		ObjectDescriptor<AnyType> od = super.getObjectDescriptor();
		EObject object = od.getObject();
		String label = ModelDecorator.getLabel(object.eClass());
		if (label!=null) {
			od.setLabel(label);
		}
		return od;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter#getFeature(java.lang.String)
	 */
	@Override
	public EStructuralFeature getFeature(String name) {
		AnyType object = (AnyType) getTarget();
		EStructuralFeature feature = object.eClass().getEStructuralFeature(name);
		this.adaptFeature(feature);
		return feature;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter#getFeatureDescriptor(org.eclipse.emf.ecore.EStructuralFeature)
	 * This override substitutes the ModelDecorator FeatureDescriptor for the default implementation.
	 * This FeatureDescriptor knows how to resolve the feature, adding it to the object's "anyAttribute"
	 * or "extensionValues" feature if necessary (depending if the feature is an attribute or element).
	 */
	@Override
	public FeatureDescriptor<AnyType> getFeatureDescriptor(EStructuralFeature feature) {
		this.adaptFeature(feature);
		return super.getFeatureDescriptor(feature);
	}
	
	/**
	 * Does the actual work of adding the ModelDecorator's FeatureDescriptor to the
	 * ExtendedPropertiesAdapter in the object.
	 *  
	 * @param feature
	 */
	private void adaptFeature(EStructuralFeature feature) {
		AnyType object = (AnyType) getTarget();
		if (!hasFeatureDescriptor(feature) ||
				!(super.getFeatureDescriptor(feature) instanceof AnyTypeFeatureDescriptor)) {
			if (getModelDecorator(feature) != null) {
				modelDecorator.adaptFeature(this, object, feature);
			}
		}
	}
}
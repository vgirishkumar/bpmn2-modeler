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

import java.util.List;

import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.bpmn2.modeler.core.adapters.FeatureDescriptor;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.ExtendedMetaData;

/**
 * This adapter is a FeatureDescriptor replacement for dynamic EStructuralFeatures that created and
 * managed by the ModelDecorator.
 */
class AnyTypeFeatureDescriptor extends FeatureDescriptor {

	/**
	 * 
	 */
	private final ModelDecorator modelDecorator;

	public AnyTypeFeatureDescriptor(ModelDecorator modelDecorator, ExtendedPropertiesAdapter adapter,
			EObject object, EStructuralFeature feature) {
		super(object, feature);
		this.modelDecorator = modelDecorator;
	}
	
	/**
	 * Check if the given feature in the specified object is NOT a dynamic feature.
	 * 
	 * @param object
	 * @param feature
	 * @return
	 */
	private boolean hasStructuralFeatureFeature(EObject object, EStructuralFeature feature) {
		String name = feature.getName();
		if (object instanceof EClass)
			return ((EClass)object).getEStructuralFeature(name) != null;
		return object.eClass().getEStructuralFeature(name) != null;
	}
	
	/**
	 * Check if the given feature in the specified object is a dynamic attribute.
	 * 
	 * @param object
	 * @param feature
	 * @return
	 */
	private boolean isAnyAttribute(EObject object, EStructuralFeature feature) {
		if (hasStructuralFeatureFeature(object,feature))
			return false;
		String name = feature.getName();
		feature = ModelDecorator.getAnyAttribute(object, name);
		if (feature!=null)
			return true;
		return false;
	}

	/**
	 * Check if the given feature in the specified object is a dynamic element.
	 * 
	 * @param object
	 * @param feature
	 * @return
	 */
	private boolean isExtensionAttribute(EObject object, EStructuralFeature feature) {
		if (hasStructuralFeatureFeature(object,feature))
			return false;
		String name = feature.getName();
		feature = ModelDecorator.getExtensionAttribute(object, name);
		if (feature!=null)
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.adapters.FeatureDescriptor#getLabel()
	 * 
	 * Fetch the label for our feature from the feature's EAnnotations list.
	 * @see ModelDecorator#getLabel(EModelElement)
	 */
	@Override
	public String getLabel() {
		String label = ModelDecorator.getLabel(feature);
		if (label!=null) {
			return label;
		}
		return super.getLabel();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.adapters.FeatureDescriptor#getValue(int)
	 * 
	 * Return the value of our feature. If the feature is a dynamic feature, the value
	 * is retrieved from either the "anyAttribute" feature map if it is an attribute,
	 * or from the BaseElement's extension values container.
	 */
	@Override
	public Object getValue(int index) {
		if (hasStructuralFeatureFeature(object,feature)) {
			return super.getValue(index);
		}
		if (isAnyAttribute(object,feature)) {
			Object value = null;
			try {
				value = object.eGet(feature);
			}
			catch (Exception e1) {
			}
			return value;
		}
		if (isExtensionAttribute(object,feature)) {
			List result = ModelDecorator.getAllExtensionAttributeValues(object, feature);
			if (result.size()==0) {
				return null;
			}
			if (index>=0)
				return result.get(index);
			return result.get(0);
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.adapters.FeatureDescriptor#internalSet(org.eclipse.emf.ecore.EObject, org.eclipse.emf.ecore.EStructuralFeature, java.lang.Object, int)
	 * 
	 * Set the value of our feature. If the feature is a dynamic feature, the value
	 * is set in either the "anyAttribute" feature map if it is an attribute,
	 * or in the BaseElement's extension values container.
	 */
	@Override
	protected void internalSet(EObject object, EStructuralFeature feature, Object value, int index) {
		if (hasStructuralFeatureFeature(object,feature) || feature.isMany()) {
			object.eGet(feature);
			super.internalSet(object,feature,value,index);
		}
		else {
			// the feature does not exist in this object, so we either need to
			// create an "anyAttribute" entry or, if the object is an ExtensionAttributeValue,
			// create an entry in its "value" feature map.
			String name = feature.getName();
			if (feature instanceof EAttribute) {
				EStructuralFeature f = ModelDecorator.getAnyAttribute(object, name);
				if (f!=null) {
					object.eSet(f, value);
				}
				else {
					String namespace = ExtendedMetaData.INSTANCE.getNamespace(feature);
					String type = feature.getEType().getName();
					this.modelDecorator.addAnyAttribute(object, namespace, name, type, value);
				}
			}
			else {
				// FIXME: access to ExtensionAttributeValues MUST go through the ModelExtensionDescriptor's
				// modelDecorator so that we can properly find, and optionally create and initialize
				// the EPackage that contains the extensions
				ModelDecorator.addExtensionAttributeValue(object, feature, value, index, false);
			}
		}
	}
}
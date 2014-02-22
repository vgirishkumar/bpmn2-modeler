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

package org.eclipse.bpmn2.modeler.core.adapters;

import org.eclipse.bpmn2.modeler.core.features.activity.task.ICustomElementFeatureContainer;
import org.eclipse.bpmn2.modeler.core.model.Bpmn2ModelerFactory;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;
import org.eclipse.emf.edit.provider.ComposeableAdapterFactory;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ItemProviderAdapter;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.osgi.util.NLS;
import org.eclipse.core.runtime.Assert;

/**
 * @author Bob Brodt
 *
 */
public class ObjectDescriptor<T extends EObject> {

	protected T object;
	protected String label;
	protected String name;
	protected ExtendedPropertiesAdapter owner;

	public ObjectDescriptor(ExtendedPropertiesAdapter owner, T object) {
		this.owner = owner;
		this.object = object;
	}

	public ObjectDescriptor(T object) {
		this.owner = owner;
		this.object = object;
	}
	
	public ExtendedPropertiesAdapter getOwner() {
		return owner;
	}

	public void setOwner(ExtendedPropertiesAdapter owner) {
		this.owner = owner;
	}
	
	public void setObject(T object) {
		this.object = object;
	}
	
	public void setLabel(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		EClass eclass = (object instanceof EClass) ?
				(EClass)object :
				object.eClass();
		if (label==null) {
			label = ModelUtil.toCanonicalString(eclass.getName());
		}
		return label;
	}
	
	public void setTextValue(String name) {
		this.name = name;
	}
	
	public String getTextValue() {
		if (name==null) {
			// derive text from feature's value: default behavior is
			// to use the "name" attribute if there is one;
			// if not, use the "id" attribute;
			// fallback is to use the feature's toString()
			String text = ModelUtil.toCanonicalString(object.eClass().getName());
			Object value = null;
			EStructuralFeature f = null;
			f = object.eClass().getEStructuralFeature("name"); //$NON-NLS-1$
			if (f!=null) {
				value = object.eGet(f);
				if (value==null || value.toString().isEmpty())
					value = null;
			}
			if (value==null) {
				f = object.eClass().getEStructuralFeature("id"); //$NON-NLS-1$
				if (f!=null) {
					value = object.eGet(f);
					if (value==null || value.toString().isEmpty())
						value = null;
				}
			}
			if (value==null)
				value = NLS.bind(Messages.ObjectDescriptor_Unnamed, text);
			return (String)value;
		}
		return name;
	}

	protected IItemPropertyDescriptor getPropertyDescriptor(EStructuralFeature feature) {
		return getPropertyDescriptor(object, feature);
	}

	protected IItemPropertyDescriptor getPropertyDescriptor(T object, EStructuralFeature feature) {
		ItemProviderAdapter adapter = null;
		for (Adapter a : object.eAdapters()) {
			if (a instanceof ItemProviderAdapter) {
				adapter = (ItemProviderAdapter)a;
				break;
			}
		}
		if (adapter!=null)
			return adapter.getPropertyDescriptor(object, feature);
		return null;
	}
	
	@SuppressWarnings("unchecked")
	protected EObject clone(T oldObject) {
		T newObject = null;
		if (oldObject!=null) {
			EClass eClass = oldObject.eClass();
			newObject = (T) eClass.getEPackage().getEFactoryInstance().create(eClass);
			for (EStructuralFeature f : eClass.getEAllAttributes()) {
				newObject.eSet(f, oldObject.eGet(f));
			}
		}
		return newObject;
	}

	@SuppressWarnings("unchecked")
	public boolean equals(Object otherObject) {
		EObject thisObject = this.object;
		if (otherObject instanceof EObject) {
			// compare feature values of both EObjects:
			// this should take care of most of the BPMN2 elements
			return ExtendedPropertiesAdapter.compare(thisObject, (EObject)otherObject, false);
		}
		return super.equals(otherObject);
	}

	public boolean similar(Object otherObject) {
		EObject thisObject = this.object;
		if (otherObject instanceof EObject) {
			// compare feature values of both EObjects:
			// this should take care of most of the BPMN2 elements
			return ExtendedPropertiesAdapter.compare(thisObject, (EObject)otherObject, true);
		}
		return super.equals(otherObject);
	}
	
	protected boolean compare(EObject v2, boolean similar) {
		return ExtendedPropertiesAdapter.compare(object, v2, similar);
	}

	/**
	 * Some methods accept java Objects as a context variable. In many cases (especially the
	 * default implementations) the context object must have the same type as the specialized
	 * class. 
	 * 
	 * @param context
	 * @return the context variable if it has the same type as this.object, or this.object if not.
	 */
	@SuppressWarnings("unchecked")
	protected T adopt(Object context) {
		T result = (this.object.getClass().isInstance(context)) ? (T)context : this.object;
		return result;
	}

	public TransactionalEditingDomain getEditingDomain(Object context) {
		T object = adopt(context);
		// check the EObject's contained Resource
		EditingDomain result = AdapterFactoryEditingDomain.getEditingDomainFor(object);
		if (result == null) {
			if (object instanceof IEditingDomainProvider) {
				// the object itself may be a provider
				result = ((IEditingDomainProvider) object).getEditingDomain();
			}
			if (result == null) {
				// check the object's adapters for providers
				IEditingDomainProvider provider = AdapterUtil.adapt(object, IEditingDomainProvider.class);
				if (provider!=null) {
					result = provider.getEditingDomain();
				}
				if (result == null) {
					// finally, check our adapter factory
					result = owner.getEditingDomain();
				}
			}
		}
		// it's gotta be a Transactional Editing Domain or nothing!
		if (result instanceof TransactionalEditingDomain)
			return (TransactionalEditingDomain)result;
		return null;
	}

	public T createObject(Object context) {
		return createObject(getResource(),context);
	}
	
	public Resource getResource() {
		return owner.getResource();
	}
	
	@SuppressWarnings("unchecked")
	public T createObject(Resource resource, Object context) {
		
		EClass eClass = null;
		if (context instanceof EClass) {
			eClass = (EClass)context;
		}
		else if (context instanceof EObject) {
			eClass = ((EObject)context).eClass();
		}
		else {
			eClass = object.eClass();
		}
		Assert.isTrue(object.eClass().isSuperTypeOf(eClass));

		if (resource==null)
			resource = getResource();

		// set the Resource into the Factory's adapter temporarily for use during
		// object construction and initialization (@see ModelExtensionDescriptor)
		EFactory factory = eClass.getEPackage().getEFactoryInstance();
		ResourceProvider adapter = ResourceProvider.adapt(factory, resource);
		Object value = owner.getProperty(ICustomElementFeatureContainer.CUSTOM_ELEMENT_ID);
		if (value!=null)
			adapter.putProperty(ICustomElementFeatureContainer.CUSTOM_ELEMENT_ID, value);
		T newObject = null;
		synchronized(factory) {
			try {
				newObject = (T) factory.create(eClass);
			}
			finally {
				// the factory is shared among editor instances,
				// so make sure this is cleared when we're done
				adapter.setResource(null);
				adapter.putProperty(ICustomElementFeatureContainer.CUSTOM_ELEMENT_ID, null);
			}
		}
		
		// if the object has an "id", assign it now.
		String id = ModelUtil.setID(newObject,resource);
		// also set a default name
		EStructuralFeature feature = newObject.eClass().getEStructuralFeature("name"); //$NON-NLS-1$
		if (feature!=null && !newObject.eIsSet(feature)) {
			if (id!=null)
				newObject.eSet(feature, ModelUtil.toCanonicalString(id));
			else {
				String name = ModelUtil.toCanonicalString(newObject.eClass().getName());
				newObject.eSet(feature, NLS.bind(Messages.ObjectDescriptor_New, name));
			}
		}
		
		adapter = ExtendedPropertiesAdapter.adapt(newObject);
		adapter.setResource(resource);
		
		return newObject;
	}
}

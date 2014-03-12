/*******************************************************************************
 * Copyright (c) 2011, 2012, 2013, 2014 Red Hat, Inc.
 * All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.core.adapters;

import java.util.Hashtable;

import org.eclipse.bpmn2.util.Bpmn2Resource;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.TransactionalEditingDomain;

public class ResourceProvider extends AdapterImpl implements IResourceProvider {

	Resource resource;
	Hashtable<String, Object> properties = new Hashtable<String, Object>();
	
	public static ResourceProvider adapt(EObject object, Resource resource) {
		ResourceProvider adapter = getAdapter(object);
		if (adapter!=null)
			adapter.setResource(resource);
		else {
			adapter = new ResourceProvider(resource);
			object.eAdapters().add(adapter);
		}
		return adapter;
	}
	
	public static ResourceProvider getAdapter(EObject object) {
		for (Adapter a : object.eAdapters()) {
			if (a instanceof ResourceProvider) {
				return (ResourceProvider)a;
			}
		}
		return null;
	}
	
	public ResourceProvider(Resource resource) {
		if (resource!=null)
			setResource(resource);
	}
	
	@Override
	public EditingDomain getEditingDomain() {
		Resource resource = getResource();
		if (resource!=null) {
			EditingDomain result = AdapterFactoryEditingDomain.getEditingDomainFor(resource);
			if (result instanceof TransactionalEditingDomain)
				return result;
		}
		return null;
	}

	@Override
	public Resource getResource() {
		return resource;
	}
	
	@Override
	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public void setProperty(String key, Object value) {
		if (value==null)
			properties.remove(key);
		else
			properties.put(key, value);
	}
	
	public Object getProperty(String key) {
		return properties.get(key);
	}
	
	/**
	 * Given an EObject always returns the BPMN2 Resource that is associated with that object.
	 * This may involve searching for all Resources in the ResourceSet that the EObject belongs to.
	 * This also searches for a Resource in the object's InsertionAdapter if the object is not yet
	 * contained in any Resource.
	 * 
	 * @param object
	 * @return
	 */
	public static Resource getResource(EObject object) {
		Resource resource = null;
		if (object!=null) {
			resource = object.eResource();
			if (resource==null) {
				IResourceProvider rp = AdapterRegistry.INSTANCE.adapt(object, IResourceProvider.class);
				if (rp!=null)
					resource = rp.getResource();
			}
			
			// make sure we get a BPMN2 Resource
			if (resource!=null && !(resource instanceof Bpmn2Resource)) {
				ResourceSet rs = resource.getResourceSet();
				if (rs!=null) {
					for (Resource r : rs.getResources()) {
						if (r instanceof Bpmn2Resource) {
							resource = r;
							break;
						}
					}
				}
			}
		}
		return resource;
	}
}

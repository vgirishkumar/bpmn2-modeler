package org.eclipse.bpmn2.modeler.core.adapters;

import org.eclipse.bpmn2.util.Bpmn2Resource;
import org.eclipse.core.runtime.Assert;
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
	
	public static IResourceProvider adapt(EObject object, Resource resource) {
		IResourceProvider adapter = getAdapter(object);
		if (adapter!=null)
			adapter.setResource(resource);
		else {
			adapter = new ResourceProvider(resource);
			object.eAdapters().add(adapter);
		}
		return adapter;
	}
	
	public static IResourceProvider getAdapter(EObject object) {
		for (Adapter a : object.eAdapters()) {
			if (a instanceof IResourceProvider) {
				return (IResourceProvider)a;
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

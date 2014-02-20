package org.eclipse.bpmn2.modeler.core.adapters;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.IEditingDomainProvider;

/**
 * An EMF Resource provider interface. This is also an Editing Domain provider since
 * the Resource MAY have a reference to an Editing Domain.
 */
public interface IResourceProvider extends IEditingDomainProvider, Adapter {
	Resource getResource();
	void setResource(Resource resource);
}

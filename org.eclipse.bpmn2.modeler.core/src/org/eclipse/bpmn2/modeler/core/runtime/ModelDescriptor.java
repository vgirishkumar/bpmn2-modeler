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
package org.eclipse.bpmn2.modeler.core.runtime;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.ecore.EFactory;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.impl.ResourceFactoryImpl;

public class ModelDescriptor extends BaseRuntimeExtensionDescriptor {
	
	public final static String EXTENSION_NAME = "model";

	protected EPackage ePackage;
	protected EFactory eFactory;
	protected ResourceFactoryImpl resourceFactory;
	
	public ModelDescriptor(IConfigurationElement e) {
		targetRuntime = TargetRuntime.getRuntime(e);
		if (e.getAttribute("uri")!=null) { //$NON-NLS-1$
			String uri = e.getAttribute("uri"); //$NON-NLS-1$
			setEPackage(EPackage.Registry.INSTANCE.getEPackage(uri));
			setEFactory(getEPackage().getEFactoryInstance());
		}
		if (e.getAttribute("resourceFactory")!=null) { //$NON-NLS-1$
			try {
				setResourceFactory((ResourceFactoryImpl) e.createExecutableExtension("resourceFactory"));
			} catch (CoreException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public String getExtensionName() {
		return EXTENSION_NAME;
	}

	public EFactory getEFactory() {
		return eFactory;
	}
	
	public ResourceFactoryImpl getResourceFactory() {
		return resourceFactory;
	}
	
	public EPackage getEPackage() {
		return ePackage;
	}

	public void setEPackage(EPackage ePackage) {
		this.ePackage = ePackage;
	}

	public void setEFactory(EFactory eFactory) {
		this.eFactory = eFactory;
	}

	public void setResourceFactory(ResourceFactoryImpl resourceFactory) {
		this.resourceFactory = resourceFactory;
	}
}
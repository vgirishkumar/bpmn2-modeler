/*******************************************************************************
 * Copyright (c) 2011, 2012 Red Hat, Inc. 
 * All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 *
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 *******************************************************************************/
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.validation;

import java.util.Iterator;

import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.modeler.core.validation.BPMN2ProjectValidator;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

public class ProcessConstraint extends AbstractModelConstraint {

	@Override
	public IStatus validate(IValidationContext ctx) {
		EObject eObj = ctx.getTarget();
		if (eObj instanceof Process) {
			URI modelUri = eObj.eResource().getURI();
			String uriString = modelUri.toPlatformString(true);
			if (uriString!=null) {
				IPath fullPath = new Path(uriString);
				IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(fullPath);
				try {
					IMarker[] markers = file.findMarkers(BPMN2ProjectValidator.BPMN2_MARKER_ID, true, IResource.DEPTH_ZERO);
					for (IMarker m : markers) {
						System.out.println(m);
					}
				}
				catch (CoreException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

			
			Process process = (Process)eObj;
			
			String name = null;
			Iterator<FeatureMap.Entry> iter = process.getAnyAttribute().iterator();
			while (iter.hasNext()) {
				FeatureMap.Entry entry = iter.next();
				if (entry.getEStructuralFeature().getName().equals("packageName")) {
					name = (String) entry.getValue();
					if (name==null || name.isEmpty()) {
						ctx.addResult(entry.getEStructuralFeature());
						return ctx.createFailureStatus("Process has no package name.");
					}
				}
			}
	
			name = process.getName();
			if (name==null || name.isEmpty()) {
				ctx.addResult(process.eClass().getEStructuralFeature("name"));
				return ctx.createFailureStatus("Process has no name.");
			}
		}	
		return ctx.createSuccessStatus();
	}

}

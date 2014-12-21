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
package org.eclipse.bpmn2.modeler.core.validation;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.runtime.TargetRuntime;
import org.eclipse.bpmn2.modeler.core.validation.validators.AbstractBpmn2ElementValidator;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleReference;

public class BPMN2ValidationConstraints extends AbstractModelConstraint {

	public final static String CATEGORY_ID = "org.eclipse.bpmn2.modeler.core.validation"; //$NON-NLS-1$
	
	public BPMN2ValidationConstraints() {
	}

	@Override
	public IStatus validate(IValidationContext ctx) {
		EObject eObj = ctx.getTarget();
		Bpmn2Preferences prefs = Bpmn2Preferences.getInstance(eObj);
		if (prefs.getDoCoreValidation()) {
			
			if (deferValidationToTargetRuntime(ctx, eObj))
				return ctx.createSuccessStatus();

			// find a validator class to handle this object type
			AbstractBpmn2ElementValidator validator = AbstractBpmn2ElementValidator.getValidator(ctx, eObj.getClass());
			if (validator!=null) {
				return validator.validate((BaseElement)eObj);
			}
			for (EClass eClass : eObj.eClass().getEAllSuperTypes()) {
				validator = AbstractBpmn2ElementValidator.getValidator(ctx, eClass.getInstanceClass());
				if (validator!=null) {
					return validator.validate((BaseElement)eObj);
				}
			}
		}
		return ctx.createSuccessStatus();
	}
	
	private boolean deferValidationToTargetRuntime(IValidationContext ctx, EObject object) {
		TargetRuntime rt = TargetRuntime.getCurrentRuntime();
		if (rt != TargetRuntime.getDefaultRuntime()) {
			ClassLoader cl = rt.getRuntimeExtension().getClass().getClassLoader();
			Bundle b = ((BundleReference)cl).getBundle();
			String pluginId = b.getSymbolicName();
			IConfigurationElement[] elements = Platform.getExtensionRegistry().getConfigurationElementsFor("org.eclipse.emf.validation.constraintProviders");
			for (IConfigurationElement e : elements) {
				if (pluginId.equals(e.getDeclaringExtension().getContributor().getName())) {
					if ("constraintProvider".equals(e.getName())) {
						for (IConfigurationElement e1 : e.getChildren("constraints")) {
							String categories = e1.getAttribute("categories");
							if (categories!=null && categories.contains(CATEGORY_ID)) {
								for (IConfigurationElement e2 : e1.getChildren("constraint")) {
									for (IConfigurationElement e3 : e2.getChildren("target")) {
										String className = e3.getAttribute("class");
										if (object.eClass().getName().equals(className))
											return true;
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}
}

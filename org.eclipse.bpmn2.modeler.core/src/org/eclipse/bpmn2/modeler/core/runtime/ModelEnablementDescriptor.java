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

import java.util.Collection;

import org.eclipse.bpmn2.modeler.core.preferences.ModelEnablements;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil.Bpmn2DiagramType;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.graphiti.features.IFeature;

/**
 * Target Runtime Extension Descriptor class for model enablements. This class controls the visibility of
 * an object and its features in the UI Property Sheets and dialogs. Any object or object feature that is
 * not explicitly "enabled" will not be visible in the UI.
 * 
 * Instances of this class correspond to <modelEnablement> extension elements in the extension's plugin.xml
 * See the description of the "modelEnablement" element in the org.eclipse.bpmn2.modeler.runtime extension point schema.
 */
public class ModelEnablementDescriptor extends BaseRuntimeExtensionDescriptor {

	public final static String EXTENSION_NAME = "modelEnablement";

	// Model Types that are enabled
	private ModelEnablements modelEnablements;
	// BPMN Diagram Type
	private Bpmn2DiagramType diagramType;
	// Tool Profile name
	private String profile;

	
	public ModelEnablementDescriptor(IConfigurationElement e) {
		super(e);
		TargetRuntime rt = TargetRuntime.getRuntime(e);
		modelEnablements = new ModelEnablements(rt, null, null);
		String type = e.getAttribute("type"); //$NON-NLS-1$
		String profile = e.getAttribute("profile"); //$NON-NLS-1$
		String ref = e.getAttribute("ref"); //$NON-NLS-1$
		setDiagramType(Bpmn2DiagramType.fromString(type));
		setProfile(profile);
		if (ref!=null) {
			String a[] = ref.split(":"); //$NON-NLS-1$
			rt = TargetRuntime.getRuntime(a[0]);
			type = a[1];
			profile = a[2];
			initializeFromTargetRuntime(rt, Bpmn2DiagramType.fromString(type), profile);
		}
		
		for (IConfigurationElement c : e.getChildren()) {
			String object = c.getAttribute("object"); //$NON-NLS-1$
			String feature = c.getAttribute("feature"); //$NON-NLS-1$
			if (c.getName().equals("enable")) { //$NON-NLS-1$
				setEnabled(object, feature, true);
			} else if (c.getName().equals("disable")) { //$NON-NLS-1$
				setEnabled(object, feature, false);
			}
		}

	}
	
	public String getExtensionName() {
		return EXTENSION_NAME;
	}

	public ModelEnablementDescriptor(TargetRuntime rt) {
		super(rt);
//		modelEnablements.setEnabledAll(true);
	}

	public void setDiagramType(Bpmn2DiagramType type) {
		this.diagramType = type;
	}
	
	public Bpmn2DiagramType getDiagramType() {
		return diagramType;
	}
	
	public String getProfile() {
		return profile;
	}

	public void setProfile(String profile) {
		this.profile = profile;
	}
	
	public void setEnabled(EClass eClass, boolean enabled) {
		modelEnablements.setEnabled(eClass, enabled);
	}
	
	public void setEnabled(String className, boolean enabled) {
		modelEnablements.setEnabled(className, enabled);
	}
	
	public void setEnabled(String className, String featureName, boolean enabled) {
		modelEnablements.setEnabled(className,  featureName, enabled);
	}

	public void initializeFromTargetRuntime(TargetRuntime rt, Bpmn2DiagramType type, String profile) {
		ModelEnablementDescriptor med = rt.getModelEnablements(type, profile);
		Collection<String> enabledClasses = med.modelEnablements.getAllEnabledClasses();
		for (String c : enabledClasses) {
			Collection<String> enabledFeatures = med.modelEnablements.getAllEnabledFeatures(c);
			for (String f : enabledFeatures) {
				setEnabled(c, f, true);
			}
		}
	}

	public boolean isEnabled(String className, String featureName) {
		return modelEnablements.isEnabled(className, featureName);
	}
	
	public boolean isEnabled(EClass eClass, EStructuralFeature feature) {
		return modelEnablements.isEnabled(eClass, feature);
	}
	
	public boolean isEnabled(EClass eClass) {
		return modelEnablements.isEnabled(eClass);
	}

	public boolean isEnabled(String className) {
		return modelEnablements.isEnabled(className);
	}
	
	public boolean isEnabled(IFeature feature) {
		return modelEnablements.isEnabled(feature);
	}

	public Collection<String> getAllEnabled() {
		return modelEnablements.getAllEnabled();
	}
}

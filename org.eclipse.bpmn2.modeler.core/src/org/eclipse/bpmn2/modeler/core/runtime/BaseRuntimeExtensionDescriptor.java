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

import java.util.List;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.bpmn2.modeler.core.preferences.ShapeStyle;
import org.eclipse.core.resources.IFile;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.ExtendedMetaData;

public abstract class BaseRuntimeExtensionDescriptor implements IRuntimeExtensionDescriptor {

	protected TargetRuntime targetRuntime;
	protected IFile configFile;

	public static <T extends BaseRuntimeExtensionDescriptor> T getDescriptor(EObject object, Class type) {
		ExtendedPropertiesAdapter adapter = ExtendedPropertiesAdapter.adapt(object);
		if (adapter!=null) {
			return (T)adapter.getProperty(type.getName());
		}
		return null;
	}
	
	public BaseRuntimeExtensionDescriptor() {
	}

	public void dispose() {
		List<IRuntimeExtensionDescriptor> list = targetRuntime.getRuntimeExtensionDescriptors(getExtensionName());
		list.remove(this);
	}

	public BaseRuntimeExtensionDescriptor(TargetRuntime rt) {
		targetRuntime = rt;
	}
	
	public IFile getConfigFile() {
		return configFile;
	}

	public void setConfigFile(IFile configFile) {
		this.configFile = configFile;
	}
	
	public TargetRuntime getRuntime() {
		return targetRuntime;
	}

	public void setRuntime(TargetRuntime targetRuntime) {
		this.targetRuntime = targetRuntime;
		List<IRuntimeExtensionDescriptor> list = targetRuntime.getRuntimeExtensionDescriptors(getExtensionName());
		list.add(this);
	}
	
	public EPackage getEPackage() {
		if (targetRuntime.getModelDescriptor()!=null)
			return targetRuntime.getModelDescriptor().getEPackage();
		return Bpmn2Package.eINSTANCE;
	}

	public EStructuralFeature getFeature(String className, String featureName) {
		return getFeature(className + "." + featureName); //$NON-NLS-1$
	}
	
	/**
	 * Search the Target Runtime's EPackage for a structural feature with the specified name.
	 * If the feature is not found in the runtime package, search the Bpmn2Package.
	 * 
	 * @param name - name of the feature that specifies both an EClass and an EStructuralFeature
	 *               in the form "EClassName.EStructuralFeatureName"
	 * @return
	 */
	public EStructuralFeature getFeature(String name) {
		String[] parts = name.split("\\."); //$NON-NLS-1$
		EClass eClass = (EClass)getEPackage().getEClassifier(parts[0]);
		if (eClass==null) {
			eClass = (EClass)Bpmn2Package.eINSTANCE.getEClassifier(parts[0]);
		}
		if (eClass!=null) {
			EStructuralFeature feature = eClass.getEStructuralFeature(parts[1]);
			if (feature!=null) {
				if (ExtendedMetaData.INSTANCE.getFeatureKind(feature) == ExtendedMetaData.UNSPECIFIED_FEATURE) {
					if (feature instanceof EAttribute) {
						ExtendedMetaData.INSTANCE.setFeatureKind(feature,ExtendedMetaData.ATTRIBUTE_FEATURE);
					}
					else {
						ExtendedMetaData.INSTANCE.setFeatureKind(feature,ExtendedMetaData.ELEMENT_FEATURE);
					}
					ExtendedMetaData.INSTANCE.setNamespace(feature, eClass.getEPackage().getNsURI());
					ExtendedMetaData.INSTANCE.setName(feature, feature.getName());
				}
			}
			return feature;
		}
		return null;
	}

	public EClassifier getClassifier(String name) {
		EClass eClass = (EClass)getEPackage().getEClassifier(name);
		if (eClass==null) {
			eClass = (EClass)Bpmn2Package.eINSTANCE.getEClassifier(name);
		}
		return eClass;
	}
}
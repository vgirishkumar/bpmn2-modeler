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

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.ExtendedMetaData;

public class BaseRuntimeDescriptor {
	
	protected TargetRuntime targetRuntime;
	
	public BaseRuntimeDescriptor() {
	}

	public BaseRuntimeDescriptor(TargetRuntime rt) {
		targetRuntime = rt;
	}
	
	public TargetRuntime getRuntime() {
		return targetRuntime;
	}

	public void setRuntime(TargetRuntime targetRuntime) {
		this.targetRuntime = targetRuntime;
	}
	
	public EPackage getEPackage() {
		if (targetRuntime.getModelDescriptor()!=null)
			return targetRuntime.getModelDescriptor().getEPackage();
		return Bpmn2Package.eINSTANCE;
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
		String[] parts = name.split("\\.");
		EClass eClass = (EClass)getEPackage().getEClassifier(parts[0]);
		if (eClass==null) {
			eClass = (EClass)Bpmn2Package.eINSTANCE.getEClassifier(parts[0]);
		}
		if (eClass!=null) {
			EStructuralFeature feature = eClass.getEStructuralFeature(parts[1]);
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
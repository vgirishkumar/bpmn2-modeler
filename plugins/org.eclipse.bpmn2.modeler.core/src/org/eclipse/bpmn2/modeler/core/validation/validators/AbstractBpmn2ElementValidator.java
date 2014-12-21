/*******************************************************************************
 * Copyright (c) 2011, 2012, 2013, 2014 Red Hat, Inc.
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

package org.eclipse.bpmn2.modeler.core.validation.validators;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesProvider;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.validation.IValidationContext;
import org.eclipse.emf.validation.model.ConstraintStatus;

/**
 *
 */
public abstract class AbstractBpmn2ElementValidator<T extends BaseElement> {

	private AbstractBpmn2ElementValidator parent;
	protected IValidationContext ctx;
	protected List<IStatus> result = new ArrayList<IStatus>();

	public AbstractBpmn2ElementValidator(IValidationContext ctx) {
		this.ctx = ctx;
	}
	
	public AbstractBpmn2ElementValidator(AbstractBpmn2ElementValidator other) {
		this.parent = other.getParent();
		this.ctx = parent.ctx;
		this.result = parent.result;
	}
	
	public static AbstractBpmn2ElementValidator getValidator(IValidationContext ctx, Class c) {
		String className = AbstractBpmn2ElementValidator.class.getPackage().getName() + "." + c.getSimpleName();
		if (className.endsWith("Impl")) {
			className = className.replaceFirst("Impl$","");
		}
		className += "Validator";
		try {
			Class validatorClass = AbstractBpmn2ElementValidator.class.getClassLoader().loadClass(className);
			if (validatorClass!=null) {
				return (AbstractBpmn2ElementValidator) validatorClass.getConstructor(IValidationContext.class).newInstance(ctx);
			}
		} catch (Exception e) {
		}
		return null;
	}
	
	@SuppressWarnings("unused")
	protected AbstractBpmn2ElementValidator() {
	}
	
	protected void addStatus(EObject object, int severity, String messagePattern, Object... messageArguments) {
		IStatus status = ConstraintStatus.createStatus(ctx, object, null,
				severity, 0,
				messagePattern, messageArguments);
		addStatus(status);
	}
	
	protected void addStatus(EObject object, String featureName, int severity, String messagePattern, Object... messageArguments) {
		List<EObject> resultLocus = null;
		EStructuralFeature feature = object.eClass().getEStructuralFeature(featureName);
		if (feature!=null) {
			resultLocus = new ArrayList<EObject>();;
			resultLocus.add(feature);
		}
		IStatus status = ConstraintStatus.createStatus(ctx, object, resultLocus,
				severity, 0,
				messagePattern, messageArguments);
		addStatus(status);
	}

	protected void addMissingFeatureStatus(EObject object, String featureName, int severity) {
		EStructuralFeature feature = object.eClass().getEStructuralFeature(featureName);
		// change error message slightly for connections
		String message;
		if (feature.getEType() == Bpmn2Package.eINSTANCE.getSequenceFlow())
			message = "{0} has no {1} Connections";
		else
			message = "{0} has missing or incomplete {1}";
		ctx.addResult(feature);
		addStatus(object, severity, message, ExtendedPropertiesProvider.getLabel(object), ExtendedPropertiesProvider.getLabel(object, feature));
	}

	protected void addStatus(IStatus status) {
		result.add(status);
	}
	
	protected IStatus getResult() {
		if (result.isEmpty())
			return ctx.createSuccessStatus();
		if (result.size()==1)
			return result.get(0);
		return ConstraintStatus.createMultiStatus(ctx, result);
	}
	
	protected AbstractBpmn2ElementValidator getParent() {
		if (parent==null)
			return this;
		return parent;
	}
	
	@SuppressWarnings("rawtypes")
	protected static boolean isEmpty(Object object) {
		if (object instanceof String) {
			String str = (String) object;
			return str == null || str.isEmpty();
		}
		else if (object instanceof List) {
			return ((List)object).isEmpty();
		}
		else if (ModelUtil.isStringWrapper(object)) {
			String w = ModelUtil.getStringWrapperValue(object);
			if (w==null || w.isEmpty())
				return true;
		}
		else if (object==null)
			return true;
		return false;
	}

	public abstract IStatus validate(T object);
}

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

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesProvider;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.core.validation.SyntaxCheckerUtils;
import org.eclipse.bpmn2.modeler.core.validation.validators.AbstractBpmn2ElementValidator;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.GlobalType;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

public class ProcessVariableNameConstraint extends AbstractModelConstraint {

	@Override
	public IStatus validate(IValidationContext ctx) {
		EObject object = ctx.getTarget();
		if (object instanceof BaseElement) {
			return new ProcessVariableNameValidator(ctx).validate((BaseElement) object);
		}
		return ctx.createSuccessStatus();
	}

	public class ProcessVariableNameValidator extends AbstractBpmn2ElementValidator<BaseElement> {

		/**
		 * @param ctx
		 */
		public ProcessVariableNameValidator(IValidationContext ctx) {
			super(ctx);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.eclipse.bpmn2.modeler.core.validation.validators.
		 * AbstractBpmn2ElementValidator#validate(org.eclipse.bpmn2.BaseElement)
		 */
		@Override
		public IStatus validate(BaseElement object) {
			String id = null;
			String featureName = null;
			if (object instanceof GlobalType) {
				id = ((GlobalType) object).getIdentifier();
				featureName = "identifier";
			}
			else if (object instanceof BaseElement) {
				id = ((BaseElement) object).getId();
				featureName = "id";
			}

			if (isEmpty(id)) {
				addStatus(object, featureName, Status.ERROR, "The {0} ID can not be empty", object.eClass().getName());
			}
			else if (!SyntaxCheckerUtils.isNCName(id)) {
				addStatus(object, featureName, Status.ERROR, "The {0} ID is invalid: {1}", object.eClass().getName(), id);
			}

			Definitions definitions = ModelUtil.getDefinitions(object);
			TreeIterator<EObject> iter = definitions.eAllContents();
			while (iter.hasNext()) {
				EObject o2 = iter.next();
				if (o2 instanceof BaseElement && object!=o2) {
					String id2;
					if (o2 instanceof GlobalType)
						id2 = ((GlobalType) o2).getIdentifier();
					else
						id2 = ((BaseElement) o2).getId();
					if (id != null && id2 != null) {
						if (id.equals(id2)) {
							addStatus(object, featureName, Status.ERROR,
									Messages.ProcessVariableNameConstraint_Duplicate_ID,
									ExtendedPropertiesProvider.getLabel(object)
											+ " " + ExtendedPropertiesProvider.getTextValue(object), //$NON-NLS-1$
									ExtendedPropertiesProvider.getLabel(o2)
											+ " " + ExtendedPropertiesProvider.getTextValue(o2)); //$NON-NLS-1$
						}
					}
				}
			}
			return getResult();
		}
	}
	
	public boolean doLiveValidation() {
		return true;
	}
}

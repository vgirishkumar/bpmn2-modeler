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

import java.util.HashSet;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.modeler.core.validation.SyntaxCheckerUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.validation.IValidationContext;

/**
 *
 */
public class BaseElementValidator extends AbstractBpmn2ElementValidator<BaseElement> {

	protected static HashSet<EClass> idOptional = new HashSet<EClass>();
	static {
		idOptional.add(Bpmn2Package.eINSTANCE.getDefinitions());
		// TODO: are there other BPMN2 object types whose IDs are optional?
	}

	/**
	 * @param ctx
	 */
	public BaseElementValidator(IValidationContext ctx) {
		super(ctx);
	}

	/**
	 * @param other
	 */
	@SuppressWarnings("rawtypes")
	public BaseElementValidator(AbstractBpmn2ElementValidator other) {
		super(other);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.validation.validators.AbstractBpmn2ElementValidator#validate(org.eclipse.bpmn2.BaseElement)
	 */
	@Override
	public IStatus validate(BaseElement object) {
		if (!idOptional.contains(object.eClass())) {
			// In the case of batch mode.
			String id = object.getId();
			if (isEmpty(id)) {
				addStatus(object, "id", Status.ERROR, "The {0} ID can not be empty", object.eClass().getName());
			}
			else if (!SyntaxCheckerUtils.isNCName(id)) {
				addStatus(object, "id", Status.ERROR, "The {0} ID is invalid", object.eClass().getName());
			}
		}
//		addStatus(object, Status.INFO, "Validated {0}", object.eClass().getName());
		
		return getResult();
	}

}

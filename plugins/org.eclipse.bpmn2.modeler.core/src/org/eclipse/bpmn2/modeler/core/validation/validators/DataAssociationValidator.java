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

import org.eclipse.bpmn2.DataAssociation;
import org.eclipse.bpmn2.DataInputAssociation;
import org.eclipse.bpmn2.DataOutputAssociation;
import org.eclipse.bpmn2.Event;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.validation.IValidationContext;

/**
 *
 */
public class DataAssociationValidator extends AbstractBpmn2ElementValidator<DataAssociation> {

	/**
	 * Construct a BPMN2 Element Validator from a Validation Context.
	 *
	 * @param ctx
	 */
	public DataAssociationValidator(IValidationContext ctx) {
		super(ctx);
	}

	/**
	 * Construct a BPMN2 Element Validator with the given Validator as the parent.
	 * The parent is responsible for collecting all of the validation Status objects
	 * and reporting them back to the Validation Constraint.
	 *
	 * @param parent a parent Validator class
	 */
	@SuppressWarnings("rawtypes")
	public DataAssociationValidator(AbstractBpmn2ElementValidator parent) {
		super(parent);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.validation.validators.AbstractBpmn2ElementValidator#validate(org.eclipse.bpmn2.BaseElement)
	 */
	@Override
	public IStatus validate(DataAssociation object) {
		if (!(object.eContainer() instanceof Event)) {
			// Note that missing source/target of Data Associations for Events
			// is already handled in EventDefinitionValidator
			int severity = ProcessValidator.isContainingProcessExecutable(object) ? Status.ERROR : Status.WARNING;
			if (object instanceof DataOutputAssociation) {
				if (isEmpty(object.getTargetRef()) && object.getAssignment().size()==0 && object.getTransformation()==null)
					addMissingFeatureStatus(object,"targetRef",severity);
			}
			else if (object instanceof DataInputAssociation) {
				if (isEmpty(object.getSourceRef()) && object.getAssignment().size()==0 && object.getTransformation()==null)
					addMissingFeatureStatus(object,"sourceRef",severity);
			}
		}
		return getResult();
	}

}


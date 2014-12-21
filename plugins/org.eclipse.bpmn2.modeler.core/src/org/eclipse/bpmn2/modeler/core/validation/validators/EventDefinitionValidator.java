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

import org.eclipse.bpmn2.CompensateEventDefinition;
import org.eclipse.bpmn2.ConditionalEventDefinition;
import org.eclipse.bpmn2.ErrorEventDefinition;
import org.eclipse.bpmn2.EscalationEventDefinition;
import org.eclipse.bpmn2.EventDefinition;
import org.eclipse.bpmn2.MessageEventDefinition;
import org.eclipse.bpmn2.SignalEventDefinition;
import org.eclipse.bpmn2.TimerEventDefinition;
import org.eclipse.bpmn2.modeler.core.validation.Messages;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.validation.IValidationContext;

/**
 *
 */
public class EventDefinitionValidator extends AbstractBpmn2ElementValidator<EventDefinition> {

	/**
	 * Construct a BPMN2 Element Validator from a Validation Context.
	 *
	 * @param ctx
	 */
	public EventDefinitionValidator(IValidationContext ctx) {
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
	public EventDefinitionValidator(AbstractBpmn2ElementValidator parent) {
		super(parent);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.validation.validators.AbstractBpmn2ElementValidator#validate(org.eclipse.bpmn2.BaseElement)
	 */
	@Override
	public IStatus validate(EventDefinition ed) {
		if (ed instanceof TimerEventDefinition) {
			TimerEventDefinition ted = (TimerEventDefinition) ed;
			if (ted.getTimeDate() == null && ted.getTimeDuration() == null && ted.getTimeCycle() == null) {
				addStatus(ed,Status.ERROR,"timeCycle","Timer Event has no Timer definition"); //$NON-NLS-1$
			}
		} else if (ed instanceof SignalEventDefinition) {
			new SignalValidator(this).validate(((SignalEventDefinition) ed).getSignalRef());
		} else if (ed instanceof ErrorEventDefinition) {
			new ErrorValidator(this).validate(((ErrorEventDefinition) ed).getErrorRef());
		} else if (ed instanceof ConditionalEventDefinition) {
			new ExpressionValidator(this).validate(((ConditionalEventDefinition) ed).getCondition());
		} else if (ed instanceof EscalationEventDefinition) {
			new EscalationValidator(this).validate(((EscalationEventDefinition) ed).getEscalationRef());
		} else if (ed instanceof MessageEventDefinition) {
			new MessageValidator(this).validate(((MessageEventDefinition) ed).getMessageRef());
		} else if (ed instanceof CompensateEventDefinition) {
			new ActivityValidator(this).validate(((CompensateEventDefinition) ed).getActivityRef());
		}
		return getResult();
	}

}


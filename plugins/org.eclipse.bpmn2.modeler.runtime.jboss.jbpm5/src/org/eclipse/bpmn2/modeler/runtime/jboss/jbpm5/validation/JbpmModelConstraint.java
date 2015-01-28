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

import org.eclipse.bpmn2.BusinessRuleTask;
import org.eclipse.bpmn2.CallActivity;
import org.eclipse.bpmn2.DataAssociation;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.Escalation;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.Signal;
import org.eclipse.bpmn2.UserTask;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.GlobalType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.validation.validators.BusinessRuleTaskValidator;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.validation.validators.CallActivityValidator;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.validation.validators.DataAssociationValidator;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.validation.validators.DefinitionsValidator;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.validation.validators.EscalationValidator;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.validation.validators.GlobalTypeValidator;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.validation.validators.ProcessValidator;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.validation.validators.SignalValidator;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.validation.validators.UserTaskValidator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

public class JbpmModelConstraint extends AbstractModelConstraint {

	@Override
	public IStatus validate(IValidationContext ctx) {
		EObject object = ctx.getTarget();
		if (object instanceof BusinessRuleTask) {
			return new BusinessRuleTaskValidator(ctx).validate((BusinessRuleTask) object);
		}
		if (object instanceof CallActivity) {
			return new CallActivityValidator(ctx).validate((CallActivity) object);
		}
		if (object instanceof UserTask) {
			return new UserTaskValidator(ctx).validate((UserTask)object);
		}	
		if (object instanceof Process) {
			return new ProcessValidator(ctx).validate((Process)object);
		}	
		if (object instanceof Signal) {
			return new SignalValidator(ctx).validate((Signal)object);
		}	
		if (object instanceof Escalation) {
			return new EscalationValidator(ctx).validate((Escalation)object);
		}	
		if (object instanceof GlobalType) {
			return new GlobalTypeValidator(ctx).validate((GlobalType)object);
		}	
		if (object instanceof DataAssociation) {
			return new DataAssociationValidator(ctx).validate((DataAssociation)object);
		}	
		if (object instanceof Definitions) {
			return new DefinitionsValidator(ctx).validate((Definitions)object);
		}	
		return ctx.createSuccessStatus();
	}

}

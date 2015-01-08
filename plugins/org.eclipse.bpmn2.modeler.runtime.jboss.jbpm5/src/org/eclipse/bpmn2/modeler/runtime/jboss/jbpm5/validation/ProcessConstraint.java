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

import java.util.List;

import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.StartEvent;
import org.eclipse.bpmn2.modeler.core.model.ModelDecorator;
import org.eclipse.bpmn2.modeler.core.validation.validators.AbstractBpmn2ElementValidator;
import org.eclipse.bpmn2.modeler.core.validation.validators.BaseElementValidator;
import org.eclipse.bpmn2.modeler.core.validation.validators.FlowElementsContainerValidator;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.IValidationContext;

public class ProcessConstraint extends AbstractModelConstraint {

	@Override
	public IStatus validate(IValidationContext ctx) {
		EObject object = ctx.getTarget();
		if (object instanceof Process) {
			return new ProcessValidator(ctx).validate((Process)object);
		}	
		return ctx.createSuccessStatus();
	}
	
	public class ProcessValidator extends AbstractBpmn2ElementValidator<Process> {

		/**
		 * @param ctx
		 */
		public ProcessValidator(IValidationContext ctx) {
			super(ctx);
		}

		/* (non-Javadoc)
		 * @see org.eclipse.bpmn2.modeler.core.validation.validators.AbstractBpmn2ElementValidator#validate(org.eclipse.bpmn2.BaseElement)
		 */
		@Override
		public IStatus validate(Process process) {
			boolean foundStartEvent = false;
			boolean foundEndEvent = false;
			List<FlowElement> flowElements = process.getFlowElements();
			for (FlowElement fe : flowElements) {
				if (fe instanceof StartEvent) {
					foundStartEvent = true;
				}
				if (fe instanceof EndEvent) {
					foundEndEvent = true;
				}
			}
			if (!foundStartEvent) {
				addStatus(process, Status.WARNING, "Process has no Start Event");
			}
			if (!foundEndEvent) {
				addStatus(process, Status.WARNING, "Process has no End Event");
			}
			
			if (isEmpty(process.getName())) {
				addStatus(process, "name", Status.WARNING, "Process {0} has no name", process.getId());
			}
			
			EStructuralFeature feature;
			feature = ModelDecorator.getAnyAttribute(process, "packageName"); //$NON-NLS-1$
			String name = null;
			if (feature!=null) {
				name = (String) process.eGet(feature);
			}
			if (name==null || name.isEmpty()) {
				if (feature!=null)
					ctx.addResult(feature);
				return ctx.createFailureStatus(Messages.ProcessConstraint_No_Package_Name);
			}
	
			name = process.getName();
			if (name==null || name.isEmpty()) {
				ctx.addResult(process.eClass().getEStructuralFeature("name")); //$NON-NLS-1$
				return ctx.createFailureStatus(Messages.ProcessConstraint_No_Process_Name);
			}
			
			feature = ModelDecorator.getAnyAttribute(process, "adHoc"); //$NON-NLS-1$
			if (feature!=null) {
				Boolean adHoc = (Boolean) process.eGet(feature);
				if (!adHoc.booleanValue()) {
					// This is not an ad-hoc process:
					// need to make sure all nodes are connected,
					// same as core BPMN2
					new FlowElementsContainerValidator(this).validate(process);
				}
			}
			else {
				// Default value for missing "adHoc" attribute is "false"
				new FlowElementsContainerValidator(this).validate(process);
			}
			
			new BaseElementValidator(this).validate(process);
			
			return getResult();
		}
	}	
}

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

import java.util.List;

import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.StartEvent;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;

/**
 *
 */
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
		
		new FlowElementsContainerValidator(this).validate(process);
		
		new BaseElementValidator(this).validate(process);
		
		return getResult();
	}
	
	public static boolean isContainingProcessExecutable(EObject object) {
		Process process = (Process) ModelUtil.findNearestAncestor(object, new Class[] { Process.class });
		return process!=null && process.isIsExecutable();
	}
}

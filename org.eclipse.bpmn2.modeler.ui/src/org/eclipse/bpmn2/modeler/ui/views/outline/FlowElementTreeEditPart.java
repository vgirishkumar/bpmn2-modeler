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
package org.eclipse.bpmn2.modeler.ui.views.outline;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.SubProcess;

public class FlowElementTreeEditPart extends AbstractGraphicsTreeEditPart {
	
	public FlowElementTreeEditPart(DiagramTreeEditPart dep, FlowElement flowElement) {
		super(dep, flowElement);
	}

	public FlowElement getFlowElement() {
		return (FlowElement) getModel();
	}

	// ======================= overwriteable behaviour ========================

	/**
	 * Creates the EditPolicies of this EditPart. Subclasses often overwrite
	 * this method to change the behaviour of the editpart.
	 */
	@Override
	protected void createEditPolicies() {
	}

	@Override
	protected List<Object> getModelChildren() {
		List<Object> retList = new ArrayList<Object>();
		FlowElement elem = getFlowElement();
		if (elem instanceof SubProcess) {
			SubProcess subProcess = (SubProcess)elem;
			retList.addAll(subProcess.getFlowElements());
		}
		return retList;
	}
}
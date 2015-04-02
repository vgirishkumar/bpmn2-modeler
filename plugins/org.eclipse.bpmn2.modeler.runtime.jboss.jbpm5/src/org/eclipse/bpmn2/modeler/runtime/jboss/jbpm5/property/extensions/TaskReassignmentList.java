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

package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.extensions;

import org.eclipse.emf.common.util.BasicEList;

/*
 *
 */
@SuppressWarnings("serial")
public class TaskReassignmentList extends BasicEList<TaskReassignment> {

	public TaskReassignmentList() {
		super();
	}

	public void add(String type, String string) {
		TaskReassignment re = TaskExtensionsFactory.eINSTANCE.createTaskReassignment();
		re.setType(ReassignmentType.get(type));
		add(re);
		String tail = re.fromString(string);
		while (tail!=null) {
			re = TaskExtensionsFactory.eINSTANCE.createTaskReassignment();
			re.setType(ReassignmentType.get(type));
			add(re);
			tail = re.fromString(tail);
		}

	}

	public String toString(String type) {
		String result = "";
		for (int i=0; i<size(); ++i) {
			TaskReassignment re = get(i);
			if (re.getType().getLiteral().equals(type)) {
				if (!result.isEmpty())
					result += "^";
				result += re.toString();
			}
		}
		return result;
	}
}
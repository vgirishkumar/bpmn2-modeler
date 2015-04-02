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

import java.util.Collection;

import org.eclipse.bpmn2.Task;
import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.xml.type.impl.AnyTypeImpl;

public class TaskReassignment extends AnyTypeImpl {
	
	Task task;
	ReassignmentType type;
	EList<String> users = new BasicEList<String>();
	EList<String> groups = new BasicEList<String>();
	String expiresAt = "";

	EStructuralFeature typeFeature;
	EStructuralFeature usersFeature;
	EStructuralFeature groupsFeature;
	EStructuralFeature expiresAtFeature;

	public TaskReassignment() {
		super();
		// cache these:
		typeFeature = TaskExtensionsFactory.eINSTANCE.getTaskReassignment_Type();
		usersFeature = TaskExtensionsFactory.eINSTANCE.getTaskReassignment_Users();
		groupsFeature = TaskExtensionsFactory.eINSTANCE.getTaskReassignment_Groups();
		expiresAtFeature = TaskExtensionsFactory.eINSTANCE.getTaskReassignment_ExpiresAt();
	}

	@Override
	public EClass eClass() {
		return TaskExtensionsFactory.eINSTANCE.getTaskReassignment();
	}

	@Override
	public Object eGet(EStructuralFeature eFeature) {
		if (eFeature == typeFeature)
			return getType();
		if (eFeature == usersFeature)
			return getUsers();
		if (eFeature == groupsFeature)
			return getGroups();
		if (eFeature == expiresAtFeature)
			return getExpiresAt();
		return super.eGet(eFeature);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void eSet(EStructuralFeature eFeature, Object newValue) {
		if (eFeature == typeFeature) {
			setType((ReassignmentType) newValue);
		}
		else if (eFeature == usersFeature) {
			getUsers().clear();
			getUsers().addAll((Collection<? extends String>) newValue);
		}
		else if (eFeature == groupsFeature) {
			getGroups().clear();
			getGroups().addAll((Collection<? extends String>) newValue);
		}
		else if (eFeature == expiresAtFeature) {
			setExpiresAt((String) newValue);
		}
		else
			super.eSet(eFeature, newValue);
	}

	public void setTask(Task task) {
		this.task = task;
	}
	
	public Task getTask() {
		return task;
	}
	
	public ReassignmentType getType() {
		return type;
	}

	public void setType(ReassignmentType type) {
		this.type = type;
	}

	public EList<String> getUsers() {
		return users;
	}

	public EList<String> getGroups() {
		return groups;
	}

	public String getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(String expiresAt) {
		this.expiresAt = expiresAt;
	}

	@Override
	public String toString() {
		String result = "[users:";
		for (int i = 0; i < users.size(); ++i) {
			result += users.get(i);
			if (i + 1 < users.size())
				result += ",";
		}
		result += "|groups:";
		for (int i = 0; i < groups.size(); ++i) {
			result += groups.get(i);
			if (i + 1 < groups.size())
				result += ",";
		}
		result += "]@[" + expiresAt + "]";

		return result;
	}

	public String fromString(String string) {
		String tail = null;
		users.clear();
		groups.clear();
		expiresAt = "";
		int segment = 0;
		for (int i = 0; i < string.length() - 1; ++i) {
			char c = string.charAt(i);
			if (c == '[' || c == '|') {
				if (segment == 0) {
					// begin a Users segment
					c = string.charAt(i + 1);
					if (c == 'u') {
						while (++i < string.length() - 1) {
							c = string.charAt(i);
							if (c == ':')
								break;
						}
						int start = i + 1;
						while (++i < string.length() - 1) {
							c = string.charAt(i);
							if (c == ',' || c == '|') {
								String user = string.substring(start, i);
								users.add(user);
								start = i + 1;
							}
							if (c == '|' || c == ']') {
								--i;
								break;
							}
						}
					} else if (c == 'g') {
						while (++i < string.length() - 1) {
							c = string.charAt(i);
							if (c == ':')
								break;
						}
						int start = i + 1;
						while (++i < string.length() - 1) {
							c = string.charAt(i);
							if (c == ',' || c == '|') {
								String group = string.substring(start, i);
								groups.add(group);
								start = i + 1;
							}
							if (c == '|' || c == ']') {
								--i;
								break;
							}
						}
					}
				} else {
					// begin a "Expires At" segment
					int start = i + 1;
					while (++i < string.length()) {
						c = string.charAt(i);
						if (c == ']') {
							--i;
							break;
						}
					}
					expiresAt = string.substring(start, i + 1);
				}
			} else if (c == ']') {
				c = string.charAt(i + 1);
				if (c == '@') {
					++i;
					// expect the "Expires At" segment next
					segment = 1;
				} else if (c == '^') {
					// finished this expression, expect another to follow
					tail = string.substring(i + 2);
					break;
				}
			}
		}
		return tail;
	}
}

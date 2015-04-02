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

import org.eclipse.bpmn2.modeler.core.model.ModelDecorator;
import org.eclipse.bpmn2.modeler.core.model.ModelDecoratorAdapter;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DroolsPackage;
import org.eclipse.emf.common.util.Enumerator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

/*
 * Notifications:
 * [from:from1|tousers:user1,user2|togroups:group1,group2|replyTo:reply1|subject:subject1|body:body1<br />Hello World<br />Some more text]@[exp1]^[from:from3|tousers:user5|togroups:group5|replyTo:reply3|subject:subject3|body:body3]@[exp3]
 * Reassignments:
 * [users:user1,user2|groups:group1,group2]@[exp1]^[users:user5|groups:group5]@[exp3]
 */

public class TaskExtensionsFactory {
	
	public final static String TASKREASSIGNMENT_CLASS_NAME = "Reassignment";
	public final static String TASKREASSIGNMENT_TYPE_FEATURE_NAME = "type";
	public final static String TASKREASSIGNMENT_USERS_FEATURE_NAME = "users";
	public final static String TASKREASSIGNMENT_GROUPS_FEATURE_NAME = "groups";
	public final static String TASKREASSIGNMENT_EXPIRES_AT_FEATURE_NAME = "expiresAt";

	ModelDecorator modelDecorator = ModelDecoratorAdapter.getModelDecorator(DroolsPackage.eINSTANCE);
	
	public static TaskExtensionsFactory eINSTANCE = new TaskExtensionsFactory();
	
	private TaskExtensionsFactory() {
		modelDecorator.createEClass(TASKREASSIGNMENT_CLASS_NAME, TaskReassignment.class);
		modelDecorator.createEAttribute(TASKREASSIGNMENT_TYPE_FEATURE_NAME,
				"ReassignmentType:EEnum", TASKREASSIGNMENT_CLASS_NAME,
				null, new Enumerator[] {
					ReassignmentType.NOT_STARTED_REASSIGN,
					ReassignmentType.NOT_COMPLETED_REASSIGN
				}
		);
		modelDecorator.createEAttribute(TASKREASSIGNMENT_EXPIRES_AT_FEATURE_NAME, "EString",
				TASKREASSIGNMENT_CLASS_NAME, "");
		modelDecorator.createEReference(TASKREASSIGNMENT_USERS_FEATURE_NAME, "EString",
				TASKREASSIGNMENT_CLASS_NAME, true, true);
		modelDecorator.createEReference(TASKREASSIGNMENT_GROUPS_FEATURE_NAME, "EString",
				TASKREASSIGNMENT_CLASS_NAME, true, true);
	}
	
	public EClass getTaskReassignment() {
		return modelDecorator.getEClass(TASKREASSIGNMENT_CLASS_NAME);
	}
	
	public EStructuralFeature getTaskReassignment_Type() {
		return getTaskReassignment().getEStructuralFeature(TASKREASSIGNMENT_TYPE_FEATURE_NAME);
	}
	
	public EStructuralFeature getTaskReassignment_ExpiresAt() {
		return getTaskReassignment().getEStructuralFeature("expiresAt");
	}
	
	public EStructuralFeature getTaskReassignment_Users() {
		return getTaskReassignment().getEStructuralFeature(TASKREASSIGNMENT_USERS_FEATURE_NAME);
	}
	
	public EStructuralFeature getTaskReassignment_Groups() {
		return getTaskReassignment().getEStructuralFeature(TASKREASSIGNMENT_GROUPS_FEATURE_NAME);
	}
	
	public TaskReassignment createTaskReassignment() {
		EClass eClass = getTaskReassignment();
		EObject object = eClass.getEPackage().getEFactoryInstance().create(eClass);
		return (TaskReassignment)object;
	}
}
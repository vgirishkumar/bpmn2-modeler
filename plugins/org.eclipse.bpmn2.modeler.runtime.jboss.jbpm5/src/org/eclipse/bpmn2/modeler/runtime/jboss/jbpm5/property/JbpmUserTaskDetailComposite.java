/*******************************************************************************
 * Copyright (c) 2011, 2012 Red Hat, Inc.
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

package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property;


import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.DataInput;
import org.eclipse.bpmn2.DataInputAssociation;
import org.eclipse.bpmn2.FormalExpression;
import org.eclipse.bpmn2.Task;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractListComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultListComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.ListCompositeColumnProvider;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.ListCompositeContentProvider;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.TableColumn;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.extensions.ReassignmentType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.extensions.TaskExtensionsFactory;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.extensions.TaskReassignment;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.extensions.TaskReassignmentList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Bob Brodt
 *
 */
public class JbpmUserTaskDetailComposite extends JbpmTaskDetailComposite {

	final static String NOT_STARTED_NOTIFY = "NotStartedNotify";
	final static String NOT_COMPLETED_NOTIFY = "NotCompletedNotify";

	public JbpmUserTaskDetailComposite(AbstractBpmn2PropertySection section) {
		super(section);
	}

	public JbpmUserTaskDetailComposite(Composite parent, int style) {
		super(parent, style);
	}


	@Override
	public void createBindings(EObject be) {
		super.createBindings(be);

//		NotificationsListComposite notificationsList = new NotificationsListComposite(getAttributesParent());
//		notificationsList.bindList(be, PACKAGE.getActivity_DataInputAssociations());
//		notificationsList.setTitle("Notifications");

//		ReassignmentsListComposite reassignmentsList = new ReassignmentsListComposite(getAttributesParent());
//		reassignmentsList.bindList(be, PACKAGE.getActivity_DataInputAssociations());
//		reassignmentsList.setTitle("Reassignments");
	}

	class NotificationsListComposite extends DefaultListComposite {

		class NotificationTypeTableColumn extends TableColumn {
			public NotificationTypeTableColumn(EObject object) {
				super(object, PACKAGE.getFormalExpression_Body());
				setHeaderText("Type");
				setEditable(false);
			}
		}

		class NotificationExpiresAtTableColumn extends TableColumn {
			public NotificationExpiresAtTableColumn(EObject object) {
				super(object, PACKAGE.getFormalExpression_Body());
				setHeaderText("Expires At");
				setEditable(false);
			}
		}

		class NotificationFromTableColumn extends TableColumn {
			public NotificationFromTableColumn(EObject object) {
				super(object, PACKAGE.getFormalExpression_Body());
				setHeaderText("From");
				setEditable(false);
			}
		}

		class NotificationToUsersTableColumn extends TableColumn {
			public NotificationToUsersTableColumn(EObject object) {
				super(object, PACKAGE.getFormalExpression_Body());
				setHeaderText("To Users");
				setEditable(false);
			}
		}

		class NotificationToGroupsTableColumn extends TableColumn {
			public NotificationToGroupsTableColumn(EObject object) {
				super(object, PACKAGE.getFormalExpression_Body());
				setHeaderText("To Groups");
				setEditable(false);
			}
		}

		class NotificationReplyToTableColumn extends TableColumn {
			public NotificationReplyToTableColumn(EObject object) {
				super(object, PACKAGE.getFormalExpression_Body());
				setHeaderText("Reply To");
				setEditable(false);
			}
		}

		class NotificationSubjectTableColumn extends TableColumn {
			public NotificationSubjectTableColumn(EObject object) {
				super(object, PACKAGE.getFormalExpression_Body());
				setHeaderText("Subject");
				setEditable(false);
			}
		}

		class NotificationBodyTableColumn extends TableColumn {
			public NotificationBodyTableColumn(EObject object) {
				super(object, PACKAGE.getFormalExpression_Body());
				setHeaderText("Body");
				setEditable(false);
			}
		}

		public NotificationsListComposite(Composite parent) {
			super(parent, AbstractListComposite.DEFAULT_STYLE);
		}

		@Override
		public EClass getListItemClass(EObject object, EStructuralFeature feature) {
			return PACKAGE.getFormalExpression();
		}

		@Override
		public ListCompositeColumnProvider getColumnProvider(EObject object, EStructuralFeature feature) {
			if (columnProvider==null) {
				columnProvider = new ListCompositeColumnProvider(this);
				columnProvider.add( new NotificationTypeTableColumn(object) );
				columnProvider.add( new NotificationExpiresAtTableColumn(object) );
				columnProvider.add( new NotificationFromTableColumn(object) );
				columnProvider.add( new NotificationToUsersTableColumn(object) );
				columnProvider.add( new NotificationToGroupsTableColumn(object) );
				columnProvider.add( new NotificationReplyToTableColumn(object) );
				columnProvider.add( new NotificationSubjectTableColumn(object) );
				columnProvider.add( new NotificationBodyTableColumn(object) );
			}
			return columnProvider;
		}

	}

	class ReassignmentsListComposite extends DefaultListComposite {

		protected TaskReassignmentList mylist;
		
		class ReassignmentTypeTableColumn extends TableColumn {
			public ReassignmentTypeTableColumn(EObject object) {
				super(object, TaskExtensionsFactory.eINSTANCE.getTaskReassignment_Type());
				setHeaderText("Type");
				setEditable(true);
			}

			@Override
			public String getText(Object element) {
				return ((TaskReassignment)element).getType().getLiteral();
			}
		}

		class ReassignmentExpiresAtTableColumn extends TableColumn {
			public ReassignmentExpiresAtTableColumn(EObject object) {
				super(object, TaskExtensionsFactory.eINSTANCE.getTaskReassignment_ExpiresAt());
				setHeaderText("Expires At");
				setEditable(true);
			}

			@Override
			public String getText(Object element) {
				return ((TaskReassignment)element).getExpiresAt();
			}
		}

		class ReassignmentUsersTableColumn extends TableColumn {
			public ReassignmentUsersTableColumn(EObject object) {
				super(object, TaskExtensionsFactory.eINSTANCE.getTaskReassignment_Users());
				setHeaderText("Users");
				setEditable(false);
			}

			@Override
			public String getText(Object element) {
				String text = "";
				TaskReassignment re = (TaskReassignment)element;
				for (int i=0; i<re.getUsers().size(); ++i) {
					text += re.getUsers().get(i);
					if (i+1<re.getUsers().size())
						text += ",";
				}
				return text;
			}
		}

		class ReassignmentGroupsTableColumn extends TableColumn {
			public ReassignmentGroupsTableColumn(EObject object) {
				super(object, TaskExtensionsFactory.eINSTANCE.getTaskReassignment_Groups());
				setHeaderText("Groups");
				setEditable(false);
			}

			@Override
			public String getText(Object element) {
				String text = "";
				TaskReassignment re = (TaskReassignment)element;
				for (int i=0; i<re.getGroups().size(); ++i) {
					text += re.getGroups().get(i);
					if (i+1<re.getGroups().size())
						text += ",";
				}
				return text;
			}
		}

		public ReassignmentsListComposite(Composite parent) {
			super(parent, AbstractListComposite.DEFAULT_STYLE);
		}

		@Override
		public EClass getListItemClass(EObject object, EStructuralFeature feature) {
			return TaskExtensionsFactory.eINSTANCE.getTaskReassignment();
		}

		@Override
		protected int createColumnProvider(EObject object, EStructuralFeature feature) {
			if (columnProvider==null) {
				columnProvider = new ListCompositeColumnProvider(this);
				columnProvider.addRaw( new ReassignmentTypeTableColumn(object));
				columnProvider.addRaw( new ReassignmentExpiresAtTableColumn(object));
				columnProvider.addRaw( new ReassignmentUsersTableColumn(object));
				columnProvider.addRaw( new ReassignmentGroupsTableColumn(object));
			}
			return columnProvider.getColumns().size();
		}

		@Override
		public ListCompositeContentProvider getContentProvider(EObject object, EStructuralFeature feature, EList<EObject>list) {
			if (contentProvider==null) {
				contentProvider = new ListCompositeContentProvider(this, object, feature, list) {

					@Override
					public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
						if (newInput instanceof EList) {
							mylist = new TaskReassignmentList();
							Task task = (Task)object;
							for (DataInputAssociation dia : task.getDataInputAssociations()) {
								DataInput di = (DataInput)dia.getTargetRef();
								String type = di.getName();
								if (ReassignmentType.get(type) != null) {
									FormalExpression exp = (FormalExpression)dia.getAssignment().get(0).getFrom();
									mylist.add(type, exp.getBody());
								}
							}
							object = listComposite.getBusinessObject();
						}
					}

					@Override
					public Object[] getElements(Object inputElement) {
						return mylist.toArray();
					}
				};
			}
			return contentProvider;
		}

		public AbstractDetailComposite createDetailComposite(Class eClass, Composite parent, int style) {
			AbstractDetailComposite composite = new DefaultDetailComposite(parent, style) {
			};
			return composite;
		}
		
		protected EObject addListItem(EObject object, EStructuralFeature feature) {
			// Here we need to examine the current list DataInputs on the Task and,
			// if there is already a DataInputAssociation for a NotStartedReassign or
			// NotCompletedReassign DataInput, append a new expression.
			// Otherwise create a new DataInput and DataInputAssociation.
			TaskReassignment newItem = TaskExtensionsFactory.eINSTANCE.createTaskReassignment();
			
			return newItem;
		}
		
		protected Object removeListItem(EObject object, EStructuralFeature feature, int index) {
			return null;
		}

		@Override
		protected Object moveListItemUp(EObject object, EStructuralFeature feature, int index) {
			return null;
		}

		@Override
		protected Object moveListItemDown(EObject object, EStructuralFeature feature, int index) {
			return null;
		}

	}
}

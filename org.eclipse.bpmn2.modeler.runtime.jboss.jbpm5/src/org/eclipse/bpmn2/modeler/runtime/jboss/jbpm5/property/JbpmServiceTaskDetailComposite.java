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


import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.InputOutputSpecification;
import org.eclipse.bpmn2.Operation;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.core.model.Bpmn2ModelerFactory;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.ui.property.tasks.DataAssociationDetailComposite.MapType;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.swt.widgets.Composite;

/**
 * @author Bob Brodt
 *
 */
public class JbpmServiceTaskDetailComposite extends JbpmTaskDetailComposite {

	public JbpmServiceTaskDetailComposite(AbstractBpmn2PropertySection section) {
		super(section);
	}

	public JbpmServiceTaskDetailComposite(Composite parent, int style) {
		super(parent, style);
	}
	
	@Override
	protected void createMessageAssociations(final Composite container, final Activity serviceTask, final EReference reference, final Operation operation) {
		super.createMessageAssociations(container, serviceTask, reference, operation);
		final InputOutputSpecification ioSpec = serviceTask.getIoSpecification();

		TransactionalEditingDomain domain = getDiagramEditor().getEditingDomain();
		if (ioSpec!=null) {
			if (!ioSpec.getDataInputs().isEmpty()) {
				if (!"Parameter".equals(ioSpec.getDataInputs().get(0).getName())) {
					domain.getCommandStack().execute(new RecordingCommand(domain) {
						@Override
						protected void doExecute() {
							ioSpec.getDataInputs().get(0).setName("Parameter");
						}
					});
				}
			}
			if (!ioSpec.getDataOutputs().isEmpty()) {
				if (!"Result".equals(ioSpec.getDataOutputs().get(0).getName())) {
					domain.getCommandStack().execute(new RecordingCommand(domain) {
						@Override
						protected void doExecute() {
							ioSpec.getDataOutputs().get(0).setName("Result");
						}
					});
				}
			}
		}
		inputComposite.setAllowedMapTypes(MapType.Property.getValue());
		outputComposite.setAllowedMapTypes(MapType.Property.getValue() | MapType.SingleAssignment.getValue());
	}
	
}

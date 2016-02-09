package com.odcgroup.bpmn2.modeler.extensions.property;

import org.eclipse.bpmn2.InputOutputSpecification;
import org.eclipse.bpmn2.ReceiveTask;
import org.eclipse.bpmn2.SendTask;
import org.eclipse.bpmn2.UserTask;
import org.eclipse.bpmn2.modeler.core.adapters.InsertionAdapter;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.ObjectEditor;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.TextObjectEditor;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.JbpmIoParametersDetailComposite;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.JbpmIoParametersListComposite;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.Messages;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.Section;

/**
 *
 * @author phanikumark
 *
 */
public class T24IoParametersDetailComposite extends JbpmIoParametersDetailComposite {
    
    protected Section outputSection = null;
    protected Composite outputComposite = null; 

    public T24IoParametersDetailComposite(AbstractBpmn2PropertySection section) {
        super(section);
    }

    public T24IoParametersDetailComposite(Composite parent, int style) {
        super(parent, style);
    }

    @Override
    public void createBindings(final EObject be) {
        final EStructuralFeature ioSpecificationFeature = be.eClass().getEStructuralFeature("ioSpecification"); //$NON-NLS-1$
        if (ioSpecificationFeature != null) {
            // the control parameter must be an Activity or CallableElement (i.e. a Process or GlobalTask)
            InputOutputSpecification ioSpecification = (InputOutputSpecification)be.eGet(ioSpecificationFeature);
            if (ioSpecification==null) {
                ioSpecification = createModelObject(InputOutputSpecification.class);
                InsertionAdapter.add(be, ioSpecificationFeature, ioSpecification);
            }
            
            if (!(be instanceof ReceiveTask)) {
                EStructuralFeature dataInputsFeature = getFeature(ioSpecification, "dataInputs"); //$NON-NLS-1$
                if (isModelObjectEnabled(ioSpecification.eClass(),dataInputsFeature)) {
                    dataInputsTable = new JbpmIoParametersListComposite(this, be, ioSpecification, dataInputsFeature);
                    dataInputsTable.bindList(ioSpecification, dataInputsFeature);
                    dataInputsTable.setTitle(Messages.JbpmIoParametersDetailComposite_Input_Mapping_Title);
                }
            }
            
            if (!(be instanceof SendTask)) {
                if (be instanceof UserTask) {
                    EStructuralFeature attribute = getFeature(ioSpecification, "dataOutputs");
                    ObjectEditor editor = new T24ResultMappingEditor(this, ioSpecification, attribute, (UserTask) be, this.editingDomain);
                    ((TextObjectEditor) editor).setMultiLine(true);
                    ((TextObjectEditor) editor).setEditable(false);
                    editor.createControl(getOutputDataMappingParent(), "");
                } else {
                    EStructuralFeature dataOutputsFeature = getFeature(ioSpecification, "dataOutputs"); //$NON-NLS-1$
                    if (isModelObjectEnabled(ioSpecification.eClass(),dataOutputsFeature)) {
                        dataOutputsTable = new JbpmIoParametersListComposite(this, be, ioSpecification, dataOutputsFeature);
                        dataOutputsTable.bindList(ioSpecification, dataOutputsFeature);
                        dataOutputsTable.setTitle(Messages.JbpmIoParametersDetailComposite_Output_Mapping_Title);
                    }
                }
            }
        }
    }
    
    public Composite getOutputDataMappingParent() {
        if (getParent() instanceof Section)
            return this;
        
        if (outputSection==null || outputSection.isDisposed()) {
            outputSection = createSection(this, "Output Data Mapping", true);
            outputSection.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, true, 3, 1));
            outputComposite = toolkit.createComposite(outputSection);
            outputSection.setClient(outputComposite);
            outputComposite.setLayout(new GridLayout(3,false));
        }
        return outputComposite;
    }

}

package com.odcgroup.bpmn2.modeler.extensions.property;

import java.util.List;

import org.eclipse.bpmn2.Assignment;
import org.eclipse.bpmn2.DataAssociation;
import org.eclipse.bpmn2.DataInput;
import org.eclipse.bpmn2.DataInputAssociation;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.FormalExpression;
import org.eclipse.bpmn2.InputOutputSpecification;
import org.eclipse.bpmn2.InputSet;
import org.eclipse.bpmn2.ItemDefinition;
import org.eclipse.bpmn2.ItemKind;
import org.eclipse.bpmn2.Task;
import org.eclipse.bpmn2.UserTask;
import org.eclipse.bpmn2.modeler.core.adapters.InsertionAdapter;
import org.eclipse.bpmn2.modeler.core.features.CustomElementFeatureContainer;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.BooleanObjectEditor;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.IntObjectEditor;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.NCNameObjectEditor;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.ObjectEditor;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.TextAndButtonObjectEditor;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.TextObjectEditor;
import org.eclipse.bpmn2.modeler.core.runtime.CustomTaskDescriptor;
import org.eclipse.bpmn2.modeler.core.runtime.ModelExtensionDescriptor;
import org.eclipse.bpmn2.modeler.core.runtime.ModelExtensionDescriptor.ModelExtensionAdapter;
import org.eclipse.bpmn2.modeler.core.runtime.ModelExtensionDescriptor.Property;
import org.eclipse.bpmn2.modeler.core.runtime.TargetRuntime;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.JbpmUserTaskDetailComposite;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.features.context.impl.AreaContext;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;

import com.odcgroup.jbpm.extension.flow.ruleflow.properties.HumanTaskCommentDialog;

/**
 * Composite that replaces the default widget of Comment field of UserTask with that of
 * a custom widget that does T24 mappings
 *
 * @author phanikumark
 *
 */
@SuppressWarnings("deprecation")
public class T24UserTaskDetailComposite extends JbpmUserTaskDetailComposite {

    public T24UserTaskDetailComposite(AbstractBpmn2PropertySection section) {
        super(section);
    }

    public T24UserTaskDetailComposite(Composite parent, int style) {
        super(parent, style);
    }
    
    /**
     * copied most of it from {code}org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.JbpmTaskDetailComposite{code} 
     * as there is no other way to override the desired behavior
     */
    @Override
    protected void createInputParameterBindings(final Task task) {
        ModelExtensionAdapter adapter = ModelExtensionDescriptor.getModelExtensionAdapter(task);
        if (adapter == null) {
            AddContext context = new AddContext(new AreaContext(), task);
            String id = CustomElementFeatureContainer.getId(context);
            if (id != null) {
                TargetRuntime rt = TargetRuntime.getCurrentRuntime();
                CustomTaskDescriptor ctd = rt.getCustomTask(id);
                ctd.populateObject(task, task.eResource(), true);
                adapter = ModelExtensionDescriptor.getModelExtensionAdapter(task);
            }
        }

        if (adapter != null) {
            List<Property> props = adapter.getProperties("ioSpecification/dataInputs/name"); //$NON-NLS-1$
            InputOutputSpecification ioSpec = task.getIoSpecification();
            if (ioSpec == null) {
                ioSpec = createModelObject(InputOutputSpecification.class);
                InsertionAdapter.add(task, PACKAGE.getActivity_IoSpecification(), ioSpec);
            }

            Definitions definitions = ModelUtil.getDefinitions(task);
            for (Property property : props) {

                // this will become the label for the Object Editor
                final String name = property.getFirstStringValue();

                // the input parameter
                DataInput parameter = null;
                // the DataInputAssociation
                DataAssociation association = null;
                for (DataInput di : ioSpec.getDataInputs()) {
                    if (name.equals(di.getName())) {
                        // this is the one!
                        parameter = di;
                        for (DataAssociation da : task.getDataInputAssociations()) {
                            if (da.getTargetRef() == di) {
                                association = da;
                                break;
                            }
                        }
                        break;
                    }
                }

                // create the DataInput element (the parameter) if needed
                if (parameter == null) {
                    ItemDefinition itemDef = createModelObject(ItemDefinition.class);
                    itemDef.setItemKind(ItemKind.INFORMATION);
                    itemDef.setStructureRef(ModelUtil.createStringWrapper("Object"));
                    InsertionAdapter.add(definitions, PACKAGE.getDefinitions_RootElements(), itemDef);

                    parameter = createModelObject(DataInput.class);
                    parameter.setName(name);
                    parameter.setItemSubjectRef(itemDef);
                    InsertionAdapter.add(ioSpec, PACKAGE.getInputOutputSpecification_DataInputs(), parameter);

                    // create the InputSet if needed
                    InputSet inputSet = null;
                    if (ioSpec.getInputSets().size() == 0) {
                        inputSet = createModelObject(InputSet.class);
                        InsertionAdapter.add(ioSpec, PACKAGE.getInputOutputSpecification_InputSets(), inputSet);
                    } else
                        inputSet = ioSpec.getInputSets().get(0);
                    // add the parameter to the InputSet also
                    InsertionAdapter.add(inputSet, PACKAGE.getInputSet_DataInputRefs(), parameter);
                }

                // create the DataInputAssociation if needed
                if (association == null) {
                    association = createModelObject(DataInputAssociation.class);
                    association.setTargetRef(parameter);
                    InsertionAdapter.add(task, PACKAGE.getActivity_DataInputAssociations(), association);
                }

                // create an MultipleAssignments and FormalExpression if needed
                // the "To" expression is the input parameter,
                // the "From" expression body is the target of the Object Editor
                FormalExpression fromExpression = null;
                Assignment assignment = null;
                if (association.getAssignment().size() == 1) {
                    assignment = (Assignment) association.getAssignment().get(0);
                    fromExpression = (FormalExpression) assignment.getFrom();
                }
                if (assignment == null) {
                    assignment = createModelObject(Assignment.class);
                    FormalExpression toExpression = createModelObject(FormalExpression.class);
                    toExpression.setBody(parameter.getId());
                    assignment.setTo(toExpression);
                    InsertionAdapter.add(association, PACKAGE.getDataAssociation_Assignment(), assignment);
                }
                if (fromExpression == null) {
                    fromExpression = createModelObject(FormalExpression.class);
                    InsertionAdapter.add(assignment, PACKAGE.getAssignment_From(), fromExpression);
                }

                // create the Object Editor for the "From" expression body:
                // the data type is obtained from the DataInput <property>
                // element from plugin.xml
                EAttribute attribute = PACKAGE.getFormalExpression_Body();
                String dataType = property.type;
                ObjectEditor editor = null;
                if ("EInt".equals(dataType)) { //$NON-NLS-1$
                    editor = new IntObjectEditor(this, fromExpression, attribute);
                } else if ("EBoolean".equals(dataType)) { //$NON-NLS-1$
                    editor = new BooleanObjectEditor(this, fromExpression, attribute) {
                        @Override
                        public Boolean getValue() {
                            if (task instanceof UserTask && "Skippable".equals(name)) {
                                UserTask ut = (UserTask) task;
                                for (DataInput di : ut.getIoSpecification().getDataInputs()) {
                                    if ("Skippable".equals(di.getName())) {
                                        for (DataInputAssociation dia : ut.getDataInputAssociations()) {
                                            if (dia.getTargetRef() == di) {
                                                if (dia.getAssignment().size() == 0) {
                                                    return Boolean.TRUE;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            return super.getValue();
                        }
                    };
                } else if ("ID".equals(dataType)) { //$NON-NLS-1$
                    editor = new NCNameObjectEditor(this, fromExpression, attribute);
                } else {
                    if (!name.equals("Comment")) {
                        editor = new TextObjectEditor(this, fromExpression, attribute);
                        boolean isCDATA = "CDATA".equals(dataType);
                        ((TextObjectEditor) editor).setMultiLine(isCDATA);
                    } else {
                        // if the property is "Comment" hava a custom editor instead
                        editor = new TextAndButtonObjectEditor(this,fromExpression,attribute) {                            
                            @Override
                            protected void buttonClicked(int arg0) {
                                HumanTaskCommentDialog dialog = new HumanTaskCommentDialog(getShell(),
                                        "T24 Human Task Configuration") {
                                    @Override
                                    protected List<String> getProcessVariables() {
                                        return T24MappingDialogHelper.getProcessVariablesByTask((UserTask) task, true);
                                    }
                                    @Override
                                    protected String getTaskGroupID() {
                                        return T24MappingDialogHelper.getTaskGroupIDByTask((UserTask) task);
                                    }
                                };
                                String value = (String) getValue();
                                if (value != null) {
                                    dialog.setValue(value);
                                }
                                int result = dialog.open();
                                if (result != Window.CANCEL) {
                                    setValue(dialog.getValue());
                                }
                            }
                        };
                        ((TextObjectEditor) editor).setMultiLine(false);
                        ((TextObjectEditor) editor).setEditable(true);
                    }
                }
                // replace the name if it is a Comment
                String label = (name.equals("Comment"))? "T24 Mappings" : name;
                editor.createControl(getAttributesParent(), ModelUtil.toCanonicalString(label));            
            }
        }
    }

}

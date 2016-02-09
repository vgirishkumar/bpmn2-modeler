package com.odcgroup.bpmn2.modeler.extensions.property;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.DataOutput;
import org.eclipse.bpmn2.DataOutputAssociation;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.InputOutputSpecification;
import org.eclipse.bpmn2.ItemAwareElement;
import org.eclipse.bpmn2.ItemDefinition;
import org.eclipse.bpmn2.ItemKind;
import org.eclipse.bpmn2.Property;
import org.eclipse.bpmn2.RootElement;
import org.eclipse.bpmn2.UserTask;
import org.eclipse.bpmn2.modeler.core.adapters.InsertionAdapter;
import org.eclipse.bpmn2.modeler.core.adapters.ObjectPropertyProvider;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.TextAndButtonObjectEditor;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;

import com.odcgroup.jbpm.extension.flow.ruleflow.properties.HumanTaskResultMappingDialog;
import com.odcgroup.jbpm.extension.flow.ruleflow.properties.IFModelUtil;

/**
 *
 * @author phanikumark
 *
 */
public class T24ResultMappingEditor extends TextAndButtonObjectEditor {
    
    private UserTask task;
    public final static Bpmn2Package PACKAGE = Bpmn2Package.eINSTANCE;
    private TransactionalEditingDomain editingDomain = null;
    
    public T24ResultMappingEditor(AbstractDetailComposite parent, EObject object, EStructuralFeature feature, UserTask task, TransactionalEditingDomain editingDomain) {
        super(parent, object, feature);
        this.task = task;
        this.editingDomain = editingDomain;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void buttonClicked(int buttonId) {
        final String[] versionMode = T24MappingDialogHelper.getVersionAndMode((UserTask) task);
        IFModelUtil ifModelUtil = new IFModelUtil(versionMode);
        Set<String> keys = ifModelUtil.getIFSchemaMap().keySet();
        if (!keys.isEmpty()) {
            HumanTaskResultMappingDialog dialog = new HumanTaskResultMappingDialog(getControl().getShell(), "Result Mapping") {
                @Override
                protected List<String> getProcessVariables() {
                    return T24MappingDialogHelper.getProcessVariablesByTask((UserTask) task, false);
                }
                @Override
                protected String[] getVersionAndMode() {
                    return versionMode;
                }
            };
            Object value = getValue();
            if (value != null) {
                dialog.setValue((Map<String, String>) value);
            }
            int result = dialog.open();
            if (result != Window.CANCEL) {
                setValue(dialog.getValue());
            }
        } else {
            MessageDialog.openInformation(getControl().getShell(), "No Schemas found",
                    "There are no schemas available for result mapping. \n"
                  + "You don't have any schemas defined for the version used by this task.");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected String getText() {
        List<DataOutput> douts = (List<DataOutput>) getBusinessObjectDelegate().getValue(object, feature);
        Map<String, String> mappings = getMappings(douts);
        return mappings.isEmpty() ? "" : mappings.toString();
    }
    
    @SuppressWarnings("unchecked")
    private List<String> getRemovedMappings(Map<String, String> newMappings) {
        List<String> list = new ArrayList<String>();
        Map<String, String> oldMappings = (Map<String, String>) getValue();
        for(String key : oldMappings.keySet()) {
            if (!newMappings.containsKey(key)) {
                list.add(key);
            }
        }
        return list;
    }

    @SuppressWarnings("unchecked")
    @Override
    public boolean setValue(Object result) {
        if (result != null) {
            Map<String, String> mappings = (Map<String, String>) result;
            DataOutputAssociation assoc = null;
            Definitions definitions = ModelUtil.getDefinitions(task);
            for(String key : mappings.keySet()) {
                assoc = T24MappingDialogHelper.getOutAssociation(task, key);
                String value = mappings.get(key);
                if (assoc != null) {
                    Property property  = T24MappingDialogHelper.getProperty(task, value);
                    if (assoc.getTargetRef() == null) {
                        if (property == null) {
                            ItemDefinition itemDef = createModelObject(ItemDefinition.class);
                            itemDef.setItemKind(ItemKind.INFORMATION);
                            itemDef.setStructureRef(ModelUtil.createStringWrapper("String"));
                            InsertionAdapter.add(definitions, PACKAGE.getDefinitions_RootElements(), itemDef);
                            
                            property = createModelObject(Property.class);
                            property.setItemSubjectRef(itemDef);
                            property.setName(value);
                            String id = value.contains("-") ? value.replace('-', '_') : value;
                            property.setId(id);
                            InsertionAdapter.add(task.eContainer(), PACKAGE.getProcess_Properties(), property);
                        }
                        getBusinessObjectDelegate().setValue(assoc, PACKAGE.getDataAssociation_TargetRef(), property);
                    } else {
                        String id = ((Property)assoc.getTargetRef()).getName();
                        if (!id.equals(value)) {
                            getBusinessObjectDelegate().setValue(assoc, PACKAGE.getDataAssociation_TargetRef(), property);
                        }                        
                    }
                } else {
                    InputOutputSpecification iospec = task.getIoSpecification();
                    //source
                    DataOutput parameter = T24MappingDialogHelper.getOutput(task, key);
                    if (parameter == null) {
                        ItemDefinition sitemDef = createModelObject(ItemDefinition.class);
                        sitemDef.setItemKind(ItemKind.INFORMATION);
                        sitemDef.setStructureRef(ModelUtil.createStringWrapper("Object"));
                        InsertionAdapter.add(definitions, PACKAGE.getDefinitions_RootElements(), sitemDef);
                        
                        parameter = createModelObject(DataOutput.class);
                        parameter.setName(key);
                        parameter.setItemSubjectRef(sitemDef);
                        InsertionAdapter.add(iospec, PACKAGE.getInputOutputSpecification_DataOutputs(), parameter);
                    }
                    
                    //target
                    Property property  = T24MappingDialogHelper.getProperty(task, value);
                    if (property == null) {
                        ItemDefinition itemDef = createModelObject(ItemDefinition.class);
                        itemDef.setItemKind(ItemKind.INFORMATION);
                        itemDef.setStructureRef(ModelUtil.createStringWrapper("String"));
                        InsertionAdapter.add(definitions, PACKAGE.getDefinitions_RootElements(), itemDef);
                        
                        property = createModelObject(Property.class);
                        property.setItemSubjectRef(itemDef);
                        property.setName(value);
                        String id = value.contains("-") ? value.replace('-', '_') : value;
                        property.setId(id);
                        InsertionAdapter.add(task.eContainer(), PACKAGE.getProcess_Properties(), property);
                    }
                    
                    assoc = createModelObject(DataOutputAssociation.class);
                    assoc.getSourceRef().add(parameter);
                    assoc.setTargetRef(property);
                    InsertionAdapter.add(task, PACKAGE.getActivity_DataOutputAssociations(), assoc);
                    
                    getBusinessObjectDelegate().setValue(task, PACKAGE.getActivity_DataOutputAssociations(), assoc, task.getDataOutputAssociations().size());
                }
            }
            // handle removed mappings
            
            final List<String> removedMappings = getRemovedMappings(mappings);
            if (!removedMappings.isEmpty()) {
                editingDomain.getCommandStack().execute(new RecordingCommand(editingDomain) {
                    @Override
                    protected void doExecute() {
                        DataOutputAssociation assocToRemove = null;
                        List<DataOutputAssociation> doas = task.getDataOutputAssociations();
                        List<DataOutput> outs = task.getIoSpecification().getDataOutputs();
                        Definitions defs = ModelUtil.getDefinitions(task);
                        List<RootElement> roots = defs.getRootElements();
                        for (String key : removedMappings) {
                            assocToRemove = T24MappingDialogHelper.getOutAssociation(task, key);
                            if (assocToRemove != null) {
                                ItemAwareElement element = assocToRemove.getSourceRef().get(0);
                                ItemDefinition def = element.getItemSubjectRef();
                                if (roots.contains(def)) {
                                    roots.remove(def);
                                }
                                if (outs.contains(element)) {
                                    outs.remove(element);
                                }
                                if (doas.contains(assocToRemove)) {
                                    doas.remove(assocToRemove);
                                }
                            }
                        }                        
                    }
                });
            }
            return true;
        }
        return false;
    }
    
    @SuppressWarnings({"rawtypes"})
    protected <T extends EObject> T createModelObject(Class clazz) {
        T object = null;
        object = getBusinessObjectDelegate().createObject(clazz);
        ModelUtil.setID(object, ObjectPropertyProvider.getResource(task));
        return object;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object getValue() {
        List<DataOutput> douts = (List<DataOutput>) getBusinessObjectDelegate().getValue(object, feature);
        return getMappings(douts);
    }
    
    private Map<String, String> getMappings(List<DataOutput> douts) {
        Map<String, String> mappings = new LinkedHashMap<String, String>();
        for (DataOutput dot : douts) {
            for(DataOutputAssociation assoc : task.getDataOutputAssociations()) {
                if (!assoc.getSourceRef().isEmpty() && assoc.getSourceRef().get(0) == dot) {
                    String value = (assoc.getTargetRef() != null) ? ((Property) assoc.getTargetRef()).getName() : "";
                    mappings.put(dot.getName(), value);
                }
            };
        }
        return mappings;        
    }

}

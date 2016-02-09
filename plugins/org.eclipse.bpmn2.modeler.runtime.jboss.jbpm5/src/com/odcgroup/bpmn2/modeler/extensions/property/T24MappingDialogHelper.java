package com.odcgroup.bpmn2.modeler.extensions.property;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.bpmn2.Assignment;
import org.eclipse.bpmn2.DataAssociation;
import org.eclipse.bpmn2.DataInput;
import org.eclipse.bpmn2.DataOutput;
import org.eclipse.bpmn2.DataOutputAssociation;
import org.eclipse.bpmn2.FormalExpression;
import org.eclipse.bpmn2.InputOutputSpecification;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.Property;
import org.eclipse.bpmn2.UserTask;

import com.google.common.collect.Lists;

/**
 *
 * @author phanikumark
 *
 */
public class T24MappingDialogHelper {    

    
    public static List<String> getProcessVariablesByTask(UserTask task, boolean format) {
        List<String> list = Lists.newArrayList();
        Process process = (Process) task.eContainer();
        List<Property> processProperties = process.getProperties();
        for (Property property : processProperties) {
            if(format){
                list.add("#{"+property.getName()+"}");
            } else {
                list.add(property.getName());
            }
        }
        return list;
    }
    
    public static String getTaskGroupIDByTask(UserTask task) {
        InputOutputSpecification ioSpec = task.getIoSpecification();
        if (ioSpec != null) {
            DataInput input = getDataInput(ioSpec, "GroupId");
            if (input != null) {
                DataAssociation association = getDataAssociation(task, input);
                if (association != null) {
                    if (association.getAssignment().size() == 1) {
                        Assignment assignment = (Assignment) association.getAssignment().get(0);
                        FormalExpression fromExpression = (FormalExpression) assignment.getFrom();
                        return fromExpression.getBody();
                    }
                }
            }
        }
        return "";
    }
    
    public static String getTaskComment(UserTask task) {
        InputOutputSpecification ioSpec = task.getIoSpecification();
        if (ioSpec != null) {
            DataInput input = getDataInput(ioSpec, "Comment");
            if (input != null) {
                DataAssociation association = getDataAssociation(task, input);
                if (association != null) {
                    if (association.getAssignment().size() == 1) {
                        Assignment assignment = (Assignment) association.getAssignment().get(0);
                        FormalExpression fromExpression = (FormalExpression) assignment.getFrom();
                        return fromExpression.getBody();
                    }
                }
            }
        }
        return "";        
    }
    
    public static String[] getVersionAndMode(UserTask task){
        String comment = getTaskComment(task);
        Pattern pattern = Pattern.compile(", [AI] ");
        Matcher m = pattern.matcher(comment);
        if(m.find()==false){
            comment = comment.replace(", ", ",");
        }
        return comment.split("[ ]+");
    }
    
    private static DataInput getDataInput(InputOutputSpecification ioSpec, String name) {
        DataInput parameter = null;
        for (DataInput di : ioSpec.getDataInputs()) {
            if (name.equals(di.getName())) {
                parameter = di;
                break;
            }
        }
        return parameter;
    }
    
    private static DataAssociation getDataAssociation(UserTask task, DataInput di) {
        DataAssociation association = null;
        for(DataAssociation da : task.getDataInputAssociations()) {
            if (da.getTargetRef() == di) {
                association = da;
                break;
            }
        }
        return association;
    }
    
    public static DataOutput getOutput(UserTask task, String name) {
        List<DataOutput> outputs = task.getIoSpecification().getDataOutputs();
        for (DataOutput output : outputs) {
            if (name.equals(output.getName())) {
                return output;
            }
        }
        return null;
    }
    
    public static Property getProperty(UserTask task, String name) {
        List<Property> properties = ((Process)task.eContainer()).getProperties();
        for (Property property : properties) {
            if (name.equals(property.getName())) {
                return property;
            }
        }
        return null;
    }
    
    public static DataOutputAssociation getOutAssociation(UserTask task, String key) {
        for(DataOutputAssociation assoc : task.getDataOutputAssociations()) {
            DataOutput out = (DataOutput) assoc.getSourceRef().get(0);
            if (key.equals(out.getName())) {
                return assoc;
            }
        }
        return null;
    }

}

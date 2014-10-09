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
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.DataInput;
import org.eclipse.bpmn2.DataObject;
import org.eclipse.bpmn2.DataOutput;
import org.eclipse.bpmn2.Error;
import org.eclipse.bpmn2.Escalation;
import org.eclipse.bpmn2.Event;
import org.eclipse.bpmn2.Gateway;
import org.eclipse.bpmn2.Interface;
import org.eclipse.bpmn2.ItemDefinition;
import org.eclipse.bpmn2.ManualTask;
import org.eclipse.bpmn2.Message;
import org.eclipse.bpmn2.MultiInstanceLoopCharacteristics;
import org.eclipse.bpmn2.ReceiveTask;
import org.eclipse.bpmn2.ScriptTask;
import org.eclipse.bpmn2.SendTask;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.Signal;
import org.eclipse.bpmn2.Task;
import org.eclipse.bpmn2.modeler.core.IBpmn2RuntimeExtension;
import org.eclipse.bpmn2.modeler.core.LifecycleEvent;
import org.eclipse.bpmn2.modeler.core.LifecycleEvent.EventType;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.PropertiesCompositeFactory;
import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.runtime.CustomTaskDescriptor;
import org.eclipse.bpmn2.modeler.core.runtime.CustomTaskImageProvider;
import org.eclipse.bpmn2.modeler.core.runtime.ModelExtensionDescriptor.Property;
import org.eclipse.bpmn2.modeler.core.runtime.TargetRuntime;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil.Bpmn2DiagramType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.features.JbpmCustomTaskFeatureContainer;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.GlobalType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.ImportType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.JbpmActivityDetailComposite;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.JbpmCommonEventDetailComposite;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.JbpmDataAssociationDetailComposite;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.JbpmDataItemsPropertySection.GlobalTypeDetailComposite;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.JbpmDefinitionsPropertySection.JbpmMessageDetailComposite;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.JbpmDefinitionsPropertySection.JbpmMessageListComposite;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.JbpmGatewayDetailComposite;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.JbpmImportTypeDetailComposite;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.JbpmInterfaceDetailComposite;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.JbpmItemDefinitionDetailComposite;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.JbpmItemDefinitionListComposite;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.JbpmManualTaskDetailComposite;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.JbpmMultiInstanceDetailComposite;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.JbpmReceiveTaskDetailComposite;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.JbpmScriptTaskDetailComposite;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.JbpmSendTaskDetailComposite;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.JbpmSequenceFlowDetailComposite;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.JbpmTaskDetailComposite;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.wid.WIDException;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.wid.WIDHandler;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.wid.WorkItemDefinition;
import org.eclipse.bpmn2.modeler.ui.DefaultBpmn2RuntimeExtension.RootElementParser;
import org.eclipse.bpmn2.modeler.ui.wizards.FileService;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IEditorInput;
import org.xml.sax.InputSource;

public class JBPM5RuntimeExtension implements IBpmn2RuntimeExtension {
	
	public final static String JBPM5_RUNTIME_ID = "org.jboss.runtime.jbpm5"; //$NON-NLS-1$
	
	private static final String DROOLS_NAMESPACE = "http://www.jboss.org/drools"; //$NON-NLS-1$
	private List<WorkItemDefinition> workItemDefinitions;
	
	/* (non-Javadoc)
	 * Check if the given input file is a drools-generated (jBPM) process file.
	 * 
	 * @see org.eclipse.bpmn2.modeler.core.IBpmn2RuntimeExtension#isContentForRuntime(org.eclipse.core.resources.IFile)
	 */
	@Override
	public boolean isContentForRuntime(IEditorInput input) {
		InputSource source = new InputSource( FileService.getInputContents(input) );
		RootElementParser parser = new RootElementParser(DROOLS_NAMESPACE);
		parser.parse(source);
		return parser.getResult();
	}

	public String getTargetNamespace(Bpmn2DiagramType diagramType){
		return DROOLS_NAMESPACE;
	}

	public List<WorkItemDefinition> getWorkItemDefinitions() {
		if (workItemDefinitions==null)
			workItemDefinitions = new ArrayList<WorkItemDefinition>();
		return workItemDefinitions;
	}

	public WorkItemDefinition getWorkItemDefinition(String taskName) {
		List<WorkItemDefinition> wids = getWorkItemDefinitions();
		for (WorkItemDefinition wid : wids) {
			if (taskName.equals(wid.getName())) {
				return wid;
			}
		}
		return null;
	}
	
	@Override
	public void notify(LifecycleEvent event) {
		if (event.eventType == EventType.EDITOR_INITIALIZED) {
			// Register all of our Property Tab Detail overrides here. 
	        PropertiesCompositeFactory.register(Activity.class, JbpmActivityDetailComposite.class);
	        PropertiesCompositeFactory.register(DataInput.class, JbpmDataAssociationDetailComposite.class);
	        PropertiesCompositeFactory.register(DataOutput.class, JbpmDataAssociationDetailComposite.class);
	        PropertiesCompositeFactory.register(Event.class, JbpmCommonEventDetailComposite.class);
	        PropertiesCompositeFactory.register(Gateway.class, JbpmGatewayDetailComposite.class);
	        PropertiesCompositeFactory.register(GlobalType.class, GlobalTypeDetailComposite.class);
	        PropertiesCompositeFactory.register(ImportType.class, JbpmImportTypeDetailComposite.class);
	        PropertiesCompositeFactory.register(ItemDefinition.class, JbpmItemDefinitionListComposite.class);
	        PropertiesCompositeFactory.register(ManualTask.class, JbpmManualTaskDetailComposite.class);
	        PropertiesCompositeFactory.register(Message.class, JbpmMessageDetailComposite.class);
	        PropertiesCompositeFactory.register(Message.class, JbpmMessageListComposite.class);
	        PropertiesCompositeFactory.register(MultiInstanceLoopCharacteristics.class, JbpmMultiInstanceDetailComposite.class);
	        PropertiesCompositeFactory.register(ReceiveTask.class, JbpmReceiveTaskDetailComposite.class);
	        PropertiesCompositeFactory.register(ScriptTask.class, JbpmScriptTaskDetailComposite.class);
	        PropertiesCompositeFactory.register(SendTask.class, JbpmSendTaskDetailComposite.class);
	        PropertiesCompositeFactory.register(SequenceFlow.class, JbpmSequenceFlowDetailComposite.class);
	        PropertiesCompositeFactory.register(Task.class, JbpmTaskDetailComposite.class);
			PropertiesCompositeFactory.register(ItemDefinition.class, JbpmItemDefinitionDetailComposite.class);
			PropertiesCompositeFactory.register(Interface.class, JbpmInterfaceDetailComposite.class);
			
			// TODO: if file was opened from a Guvnor Repository view (or git in jBPM 6)
			// we may want to explicitly make the editor read-only
	
			IProject project = Bpmn2Preferences.getActiveProject();
			if (project != null) {
				getWorkItemDefinitions();
				workItemDefinitions.clear();
				try {
					final WIDResourceVisitor visitor = new WIDResourceVisitor();
					project.accept(visitor, IResource.DEPTH_INFINITE, false);
					if (visitor.getWIDFiles().size() > 0) {
						Iterator<IFile> fileIter = visitor.getWIDFiles().iterator();
						while (fileIter.hasNext()) {
							IFile file = fileIter.next();
							HashMap<String, WorkItemDefinition> widMap = 
									new LinkedHashMap<String, WorkItemDefinition>();
							WIDHandler.evaluateWorkDefinitions(widMap, file);
							workItemDefinitions.addAll(widMap.values());
						}
					}
					if (!workItemDefinitions.isEmpty()) {
						List<CustomTaskDescriptor> removed = new ArrayList<CustomTaskDescriptor>();
						for (CustomTaskDescriptor d : TargetRuntime.getCurrentRuntime().getCustomTaskDescriptors()) {
							if (!d.isPermanent())
								removed.add(d);
						}
						TargetRuntime.getCurrentRuntime().getCustomTaskDescriptors().removeAll(removed);
					
						java.util.Iterator<WorkItemDefinition> widIterator = workItemDefinitions.iterator();
						while(widIterator.hasNext()) {
							final WorkItemDefinition wid = widIterator.next();
							final CustomTaskDescriptor ctd = convertWIDtoCT(wid);
							if (ctd != null) {
								if (TargetRuntime.getCurrentRuntime().customTaskExists(ctd.getId())) {
									Display.getDefault().asyncExec( new Runnable() {
										@Override
										public void run() {
											MessageDialog.openError(Display.getDefault().getActiveShell(),
													Messages.JBPM5RuntimeExtension_Duplicate_Task_Title,
													Messages.JBPM5RuntimeExtension_Duplicate_Task_Message+
													ctd.getId()+
													"' was already defined.\n"+ //$NON-NLS-1$
													"The new Custom Task defined in the file: "+ //$NON-NLS-1$
													wid.getDefinitionFile().getFullPath().toString()+"\n"+ //$NON-NLS-1$
													"will be ignored."); //$NON-NLS-1$
										}
									});
								}
								else
									TargetRuntime.getCurrentRuntime().addCustomTask(ctd);
							}
						}
					}
				} catch (CoreException e) {
					e.printStackTrace();
				} catch (WIDException e) {
					e.printStackTrace();
				}
			}
		}
		else if (event.eventType == EventType.BUSINESSOBJECT_CREATED) {
			EObject object = (EObject) event.target;
			// Add a name change adapter to every one of these objects.
			// See my rant in ProcessVariableNameChangeAdapter...
			if (object instanceof org.eclipse.bpmn2.Property ||
					object instanceof DataObject ||
					object instanceof Message ||
					object instanceof Signal ||
					object instanceof Error ||
					object instanceof Escalation ||
					object instanceof GlobalType ||
					object instanceof DataInput) {
				boolean found = false;
				for (Adapter a : ((EObject)object).eAdapters()) {
					if (a instanceof ProcessVariableNameChangeAdapter) {
						found = true;
						break;
					}
				}
				if (!found) {
					ProcessVariableNameChangeAdapter a = new ProcessVariableNameChangeAdapter();
					object.eAdapters().add(a);
				}
			}
		}
	}
	
	/*
	 * Convert a WID to a CustomTaskDescriptor
	 * @param wid
	 * @return
	 */
	private CustomTaskDescriptor convertWIDtoCT ( WorkItemDefinition wid ) {
		if (wid != null) {
			String id = wid.getName();
			String name = wid.getDisplayName();
			CustomTaskDescriptor ct = new CustomTaskDescriptor(id,name);
			ct.setType("Task"); //$NON-NLS-1$
			ct.setDescription(wid.getDescription());
			ct.setCategory(wid.getCategory());
			ct.setFeatureContainer(new JbpmCustomTaskFeatureContainer());
			ct.getFeatureContainer().setCustomTaskDescriptor(ct);
			ct.getFeatureContainer().setId(id);
			
			// process basic properties here
			setBasicProps ( ct, wid);
			
			// push the icon into the image registry
			IProject project = Bpmn2Preferences.getActiveProject();
			String iconPath = getWIDPropertyValue("icon", wid); //$NON-NLS-1$
			if (iconPath != null) {
				Path tempPath = new Path(iconPath);
				String iconName = tempPath.lastSegment();
				IconResourceVisitor visitor = new IconResourceVisitor(iconName);
				try {
					project.accept(visitor, IResource.DEPTH_INFINITE, false);
					if (visitor.getIconResources() != null && visitor.getIconResources().size() > 0) {
						ArrayList<IResource> icons = visitor.getIconResources();
						IResource icon = icons.get(0);
						URL url = icon.getLocationURI().toURL();
						ImageDescriptor image = ImageDescriptor.createFromURL(url);
						CustomTaskImageProvider.registerImage(iconPath, image);
					}
				} catch (CoreException e1) {
					e1.printStackTrace();
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
			
			// process xml properties here - i.e. task variables
			Property ioSpecification = createIOSpecificationSection(ct, wid);
			createDataAssociations(ioSpecification, ct);
			
			return ct;
		}
		return null;
	}
	
	/*
	 * Process the high-level props
	 * @param propName
	 * @param wid
	 * @return
	 */
	private String getWIDPropertyValue ( String propName, WorkItemDefinition wid) {
		if (propName.equalsIgnoreCase("taskname")) { //$NON-NLS-1$
			return wid.getName();
		}
		if (propName.equalsIgnoreCase("displayName")) { //$NON-NLS-1$
			return wid.getDisplayName();
		}
		if (propName.equalsIgnoreCase("icon")) { //$NON-NLS-1$
			return wid.getIcon();
		}
		if (propName.equalsIgnoreCase("customEditor")) { //$NON-NLS-1$
			return wid.getCustomEditor();
		}
		if (propName.equalsIgnoreCase("eclipse:customEditor")) { //$NON-NLS-1$
			return wid.getEclipseCustomEditor();
		}
		return null;
	}
	
	/*
	 * Get the high-level prop from the WID
	 * @param propName
	 * @param wid
	 * @return
	 */
	private Property getPropertyFromWID ( String propName, WorkItemDefinition wid ) {
		String name = propName;
		String value = getWIDPropertyValue(propName, wid);
		String description = null;
		String type = "EString"; //$NON-NLS-1$
		Property prop = new Property(null, name, description);
		prop.type = type;
		if (value == null && propName.equalsIgnoreCase("icon")) { //$NON-NLS-1$
			value = "task.png"; //$NON-NLS-1$
		}
		if (value!=null)
			prop.getValues().add(value);
		return prop;
	}
	
	/*
	 * Create the input and output data associations
	 * @param ioSpecification
	 * @param ct
	 */
	private void createDataAssociations ( Property ioSpecification, CustomTaskDescriptor ct) {
		Object[] values = ioSpecification.getValues().toArray();
		int inputCounter = -1;
//		int outputCounter = -1;
		for (int i = 0; i < values.length; i++) {
			if (values[i] instanceof Property) {
				Property prop = (Property) values[i];
				if (prop.name.equals("dataInputs")) { //$NON-NLS-1$
					inputCounter++;
					Property dataInputAssociations = new Property (prop, "dataInputAssociations", null); //$NON-NLS-1$
					Property targetRef = new Property (dataInputAssociations, "targetRef", null); //$NON-NLS-1$
					targetRef.ref = "ioSpecification/dataInputs#" + inputCounter; //$NON-NLS-1$
					dataInputAssociations.getValues().add(targetRef);
					ct.getProperties().add(dataInputAssociations);
				}
//				} else 	if (prop.name.equals("dataOutputs")) {
//					outputCounter++;
//					Property dataOutputAssociations = new Property ( "dataOutputAssociations", null);
//					Property sourceRef = new Property ("sourceRef", null);
//					sourceRef.ref = "ioSpecification/dataOutputs#" + outputCounter;
//					dataOutputAssociations.getValues().add(sourceRef);
////					Property targetRef = new Property ("targetRef", null);
////					dataOutputAssociations.getValues().add(targetRef);
//					ct.getProperties().add(dataOutputAssociations);
//				}

			}
		}
	}
	
	/*
	 * Handle creating the ioSpecification from the WID/CT
	 * @param ct
	 * @param wid
	 */
	private Property createIOSpecificationSection ( CustomTaskDescriptor ct, WorkItemDefinition wid ) {
		Property ioSpecification = new Property (null,"ioSpecification", null); //$NON-NLS-1$
		
		for (Entry<String, String> entry : wid.getParameters().entrySet()) {
			Property dataInputs = new Property(ioSpecification,"dataInputs", null); //$NON-NLS-1$
			Property dataInputsName = new Property(dataInputs,"name", null); //$NON-NLS-1$
			dataInputsName.getValues().add(entry.getKey());
			dataInputs.getValues().add(dataInputsName);
			ioSpecification.getValues().add(dataInputs);
		}

		// this code if enabled will create a default output variable
		
//		if (wid.getResults().isEmpty()) {
//			Property dataOutputs = new Property("dataOutputs", null);
//			Property dataOutputsName = new Property("name", null);
//			dataOutputsName.getValues().add("result");
//			dataOutputs.getValues().add(dataOutputsName);
//			ioSpecification.getValues().add(dataOutputs);
//		} else {
			for (Entry<String, String> entry : wid.getResults().entrySet()) {
				Property dataOutputs = new Property(ioSpecification,"dataOutputs", null); //$NON-NLS-1$
				Property dataOutputsName = new Property(dataOutputs,"name", null); //$NON-NLS-1$
				dataOutputsName.getValues().add(entry.getKey());
				dataOutputs.getValues().add(dataOutputsName);
				ioSpecification.getValues().add(dataOutputs);
			}
//		}

		Object[] values = ioSpecification.getValues().toArray();
		int inputCounter = -1;
		int outputCounter = -1;
		Property inputSets = new Property(ioSpecification,"inputSets", null); //$NON-NLS-1$
		Property outputSets = new Property(ioSpecification,"outputSets", null); //$NON-NLS-1$
		for (int i = 0; i < values.length; i++) {
			if (values[i] instanceof Property) {
				Property prop = (Property) values[i];
				if (prop.name.equals("dataInputs")) { //$NON-NLS-1$
					inputCounter++;
					Property inputSetsRef = new Property (inputSets,"dataInputRefs", null); //$NON-NLS-1$
					inputSetsRef.ref = "ioSpecification/dataInputs#" + inputCounter; //$NON-NLS-1$
					inputSets.getValues().add(inputSetsRef);
				} else 	if (prop.name.equals("dataOutputs")) { //$NON-NLS-1$
					outputCounter++;
					Property outputSetsRef = new Property (outputSets,"dataOutputRefs", null); //$NON-NLS-1$
					outputSetsRef.ref = "ioSpecification/dataOutputs#" + outputCounter; //$NON-NLS-1$
					outputSets.getValues().add(outputSetsRef);
				}
			}
		}
		if (inputSets.getValues().size() > 0) 
			ioSpecification.getValues().add(inputSets);
		if (outputSets.getValues().size() > 0) 
			ioSpecification.getValues().add(outputSets);
		
		ct.getProperties().add(ioSpecification);
		return ioSpecification;
	}
	
	/*
	 * Handle the top-level props
	 * @param ct
	 * @param wid
	 */
	private void setBasicProps ( CustomTaskDescriptor ct, WorkItemDefinition wid) {
		String[] basicProps = new String[] { "taskName", "displayName", "icon" }; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
		for (int i = 0; i < basicProps.length; i++) {
			Property prop = getPropertyFromWID(basicProps[i], wid);
			ct.getProperties().add(prop);
		}
	}

	/*
	 * Class: Visits each file in the project to see if it's a *.conf/*.wid
	 * @author bfitzpat
	 *
	 */
	private class WIDResourceVisitor implements IResourceVisitor {
		
		private ArrayList<IFile> widFiles = new ArrayList<IFile>();
		
		public boolean visit (IResource resource) throws CoreException {
			if (resource.getType() == IResource.FILE) {
				if ("conf".equalsIgnoreCase(((IFile)resource).getFileExtension()) || //$NON-NLS-1$
						"wid".equalsIgnoreCase(((IFile)resource).getFileExtension())) { //$NON-NLS-1$
					widFiles.add((IFile)resource);
					return true;
				}
			}
			else if (resource.getType() == IResource.FOLDER) {
				// skip over "bin" and "target" folders
				String name = resource.getName();
				if ("bin".equals(name) || "target".equals(name)) //$NON-NLS-1$ //$NON-NLS-2$
					return false;
			}
			return true;
		}
		
		public ArrayList<IFile> getWIDFiles() {
			return widFiles;
		}
	}
	
	/*
	 * Class: Visits each file in the project looking for the icon
	 * @author bfitzpat
	 *
	 */
	private class IconResourceVisitor implements IResourceVisitor {
		
		private ArrayList<IResource> iconResources = new ArrayList<IResource>();
		private String iconName;
		
		public IconResourceVisitor ( String iconName ) {
			this.iconName = iconName;
		}
		
		public boolean visit (IResource resource) throws CoreException {
			if (resource.getType() == IResource.FILE) {
				if (((IFile)resource).getName().equalsIgnoreCase(iconName)) {
					iconResources.add(resource);
					return true;
				}
			}
			return true;
		}
		
		public ArrayList<IResource> getIconResources() {
			return iconResources;
		}
	}
}

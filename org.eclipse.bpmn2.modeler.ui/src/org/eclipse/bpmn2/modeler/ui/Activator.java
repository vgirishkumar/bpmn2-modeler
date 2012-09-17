/******************************************************************************* 
 * Copyright (c) 2011 Red Hat, Inc. 
 *  All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 *
 * @author Innar Made
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.ui;

import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.bpel.wsil.model.inspection.InspectionPackage;
import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.Assignment;
import org.eclipse.bpmn2.BoundaryEvent;
import org.eclipse.bpmn2.CallActivity;
import org.eclipse.bpmn2.CallChoreography;
import org.eclipse.bpmn2.CatchEvent;
import org.eclipse.bpmn2.Category;
import org.eclipse.bpmn2.CompensateEventDefinition;
import org.eclipse.bpmn2.ConditionalEventDefinition;
import org.eclipse.bpmn2.DataInput;
import org.eclipse.bpmn2.DataObject;
import org.eclipse.bpmn2.DataObjectReference;
import org.eclipse.bpmn2.DataOutput;
import org.eclipse.bpmn2.DataStore;
import org.eclipse.bpmn2.DataStoreReference;
import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.ErrorEventDefinition;
import org.eclipse.bpmn2.EscalationEventDefinition;
import org.eclipse.bpmn2.Event;
import org.eclipse.bpmn2.Expression;
import org.eclipse.bpmn2.FormalExpression;
import org.eclipse.bpmn2.Gateway;
import org.eclipse.bpmn2.GlobalBusinessRuleTask;
import org.eclipse.bpmn2.GlobalManualTask;
import org.eclipse.bpmn2.GlobalScriptTask;
import org.eclipse.bpmn2.GlobalTask;
import org.eclipse.bpmn2.GlobalUserTask;
import org.eclipse.bpmn2.HumanPerformer;
import org.eclipse.bpmn2.Import;
import org.eclipse.bpmn2.InputOutputSpecification;
import org.eclipse.bpmn2.Interface;
import org.eclipse.bpmn2.ItemDefinition;
import org.eclipse.bpmn2.LinkEventDefinition;
import org.eclipse.bpmn2.ManualTask;
import org.eclipse.bpmn2.Message;
import org.eclipse.bpmn2.MessageEventDefinition;
import org.eclipse.bpmn2.MessageFlow;
import org.eclipse.bpmn2.MultiInstanceLoopCharacteristics;
import org.eclipse.bpmn2.Performer;
import org.eclipse.bpmn2.PotentialOwner;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.Property;
import org.eclipse.bpmn2.ResourceAssignmentExpression;
import org.eclipse.bpmn2.ResourceParameterBinding;
import org.eclipse.bpmn2.ResourceRole;
import org.eclipse.bpmn2.ScriptTask;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.SignalEventDefinition;
import org.eclipse.bpmn2.StandardLoopCharacteristics;
import org.eclipse.bpmn2.StartEvent;
import org.eclipse.bpmn2.SubProcess;
import org.eclipse.bpmn2.Task;
import org.eclipse.bpmn2.TextAnnotation;
import org.eclipse.bpmn2.ThrowEvent;
import org.eclipse.bpmn2.TimerEventDefinition;
import org.eclipse.bpmn2.di.impl.BpmnDiPackageImpl;
import org.eclipse.bpmn2.impl.Bpmn2PackageImpl;
import org.eclipse.bpmn2.modeler.core.adapters.AdapterRegistry;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultDialogComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultListComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.PropertiesCompositeFactory;
import org.eclipse.bpmn2.modeler.core.runtime.TargetRuntime;
import org.eclipse.bpmn2.modeler.ui.adapters.Bpmn2EditorDiItemProviderAdapterFactory;
import org.eclipse.bpmn2.modeler.ui.adapters.Bpmn2EditorItemProviderAdapterFactory;
import org.eclipse.bpmn2.modeler.ui.adapters.Bpmn2WSDLAdapterFactory;
import org.eclipse.bpmn2.modeler.ui.adapters.Bpmn2WSILAdapterFactory;
import org.eclipse.bpmn2.modeler.ui.adapters.Bpmn2XSDAdapterFactory;
import org.eclipse.bpmn2.modeler.ui.property.artifact.CategoryDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.artifact.TextAnnotationDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.connectors.MessageFlowDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.connectors.SequenceFlowDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.data.ConditionalEventDefinitionDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.data.DataAssignmentDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.data.ExpressionDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.data.InterfaceDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.data.ItemAwareElementDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.data.MessageDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.data.MessageListComposite;
import org.eclipse.bpmn2.modeler.ui.property.data.ResourceAssignmentExpressionDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.data.ResourceParameterBindingDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.data.ResourceRoleDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.data.DataObjectPropertySection.DataObjectDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.data.DataObjectReferencePropertySection.DataObjectReferenceDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.data.DataStorePropertySection.DataStoreDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.data.DataStoreReferencePropertySection.DataStoreReferenceDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.diagrams.ItemDefinitionDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.diagrams.ItemDefinitionListComposite;
import org.eclipse.bpmn2.modeler.ui.property.diagrams.ProcessDiagramPropertyComposite;
import org.eclipse.bpmn2.modeler.ui.property.diagrams.PropertyListComposite;
import org.eclipse.bpmn2.modeler.ui.property.diagrams.ResourceRoleListComposite;
import org.eclipse.bpmn2.modeler.ui.property.diagrams.DefinitionsPropertyComposite.ImportDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.events.BoundaryEventDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.events.CatchEventDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.events.CommonEventDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.events.EndEventDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.events.StartEventDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.events.ThrowEventDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.events.TimerEventDefinitionDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.events.CommonEventPropertySection.EventDefinitionDialogComposite;
import org.eclipse.bpmn2.modeler.ui.property.gateways.GatewayDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.tasks.ActivityDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.tasks.ActivityInputDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.tasks.ActivityOutputDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.tasks.DataAssociationDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.tasks.IoParametersDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.tasks.ManualTaskDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.tasks.MultiInstanceLoopCharacteristicsDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.tasks.ScriptTaskDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.tasks.StandardLoopCharacteristicsDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.tasks.TaskDetailComposite;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.ISaveContext;
import org.eclipse.core.resources.ISaveParticipant;
import org.eclipse.core.resources.ISavedState;
import org.eclipse.core.resources.IWorkspace;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.wst.wsdl.WSDLPackage;
import org.eclipse.xsd.XSDPackage;
import org.osgi.framework.BundleContext;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	// The plug-in ID
	public static final String PLUGIN_ID = "org.eclipse.bpmn2.modeler.ui"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	static {
		TargetRuntime.getAllRuntimes();
	}
	
	// Adapter Factory registration
	static {
		AdapterRegistry.INSTANCE.registerAdapterFactory(
			    WSDLPackage.eINSTANCE, Bpmn2WSDLAdapterFactory.getInstance());
		
		AdapterRegistry.INSTANCE.registerAdapterFactory(
			    XSDPackage.eINSTANCE, Bpmn2XSDAdapterFactory.getInstance());
		
		AdapterRegistry.INSTANCE.registerAdapterFactory(
			    InspectionPackage.eINSTANCE, Bpmn2WSILAdapterFactory.getInstance() );
		
		// BPMN2 metamodel adapter factories
		AdapterRegistry.BPMN2_ADAPTER_FACTORIES.addAdapterFactory(
				AdapterRegistry.INSTANCE.registerFactory(Bpmn2PackageImpl.eINSTANCE, new Bpmn2EditorItemProviderAdapterFactory()));
		AdapterRegistry.BPMN2_ADAPTER_FACTORIES.addAdapterFactory(
				AdapterRegistry.INSTANCE.registerFactory(BpmnDiPackageImpl.eINSTANCE, new Bpmn2EditorDiItemProviderAdapterFactory()));
	}

	// Register the Default List and Detail Composites as fallback for rendering EObject
	static {
		PropertiesCompositeFactory.register(EObject.class, DefaultDetailComposite.class);
		PropertiesCompositeFactory.register(EObject.class, DefaultListComposite.class);
		PropertiesCompositeFactory.register(EObject.class, DefaultDialogComposite.class);
		PropertiesCompositeFactory.register(Message.class, MessageDetailComposite.class);
		PropertiesCompositeFactory.register(Message.class, MessageListComposite.class);
		PropertiesCompositeFactory.register(MessageFlow.class, MessageFlowDetailComposite.class);
		PropertiesCompositeFactory.register(Property.class, ItemAwareElementDetailComposite.class);
		PropertiesCompositeFactory.register(CallActivity.class, ActivityDetailComposite.class);
		PropertiesCompositeFactory.register(GlobalTask.class, ActivityDetailComposite.class);
		PropertiesCompositeFactory.register(GlobalBusinessRuleTask.class, ActivityDetailComposite.class);
		PropertiesCompositeFactory.register(GlobalManualTask.class, ActivityDetailComposite.class);
		PropertiesCompositeFactory.register(GlobalScriptTask.class, ActivityDetailComposite.class);
		PropertiesCompositeFactory.register(GlobalUserTask.class, ActivityDetailComposite.class);
		PropertiesCompositeFactory.register(Import.class, ImportDetailComposite.class);
		PropertiesCompositeFactory.register(Category.class, CategoryDetailComposite.class);
		PropertiesCompositeFactory.register(TextAnnotation.class, TextAnnotationDetailComposite.class);
		PropertiesCompositeFactory.register(SequenceFlow.class, SequenceFlowDetailComposite.class);
		PropertiesCompositeFactory.register(DataObject.class, DataObjectDetailComposite.class);
		PropertiesCompositeFactory.register(DataObjectReference.class, DataObjectDetailComposite.class);
		PropertiesCompositeFactory.register(Assignment.class, DataAssignmentDetailComposite.class);
		PropertiesCompositeFactory.register(Expression.class, ExpressionDetailComposite.class);
		PropertiesCompositeFactory.register(FormalExpression.class, ExpressionDetailComposite.class);
		PropertiesCompositeFactory.register(ResourceAssignmentExpression.class, ResourceAssignmentExpressionDetailComposite.class);
		PropertiesCompositeFactory.register(ResourceParameterBinding.class, ResourceParameterBindingDetailComposite.class);
		PropertiesCompositeFactory.register(PotentialOwner.class, ResourceRoleDetailComposite.class);
		PropertiesCompositeFactory.register(HumanPerformer.class, ResourceRoleDetailComposite.class);
		PropertiesCompositeFactory.register(Performer.class, ResourceRoleDetailComposite.class);
		PropertiesCompositeFactory.register(DataObjectReference.class, DataObjectReferenceDetailComposite.class);
		PropertiesCompositeFactory.register(DataStore.class, DataStoreDetailComposite.class);
		PropertiesCompositeFactory.register(DataStoreReference.class, DataStoreReferenceDetailComposite.class);
		PropertiesCompositeFactory.register(Interface.class, InterfaceDetailComposite.class);
		PropertiesCompositeFactory.register(ItemDefinition.class, ItemDefinitionDetailComposite.class);
		PropertiesCompositeFactory.register(ItemDefinition.class, ItemDefinitionListComposite.class);
		PropertiesCompositeFactory.register(Property.class, PropertyListComposite.class);
		PropertiesCompositeFactory.register(ResourceRole.class, ResourceRoleListComposite.class);
		PropertiesCompositeFactory.register(Event.class, CommonEventDetailComposite.class);
		PropertiesCompositeFactory.register(StartEvent.class, StartEventDetailComposite.class);
		PropertiesCompositeFactory.register(EndEvent.class, EndEventDetailComposite.class);
		PropertiesCompositeFactory.register(CatchEvent.class, CatchEventDetailComposite.class);
		PropertiesCompositeFactory.register(ThrowEvent.class, ThrowEventDetailComposite.class);
		PropertiesCompositeFactory.register(BoundaryEvent.class, BoundaryEventDetailComposite.class);
		PropertiesCompositeFactory.register(TimerEventDefinition.class, TimerEventDefinitionDetailComposite.class);
		PropertiesCompositeFactory.register(ConditionalEventDefinition.class, ConditionalEventDefinitionDetailComposite.class);
		PropertiesCompositeFactory.register(CompensateEventDefinition.class, EventDefinitionDialogComposite.class);
		PropertiesCompositeFactory.register(ConditionalEventDefinition.class, EventDefinitionDialogComposite.class);
		PropertiesCompositeFactory.register(ErrorEventDefinition.class, EventDefinitionDialogComposite.class);
		PropertiesCompositeFactory.register(EscalationEventDefinition.class, EventDefinitionDialogComposite.class);
		PropertiesCompositeFactory.register(LinkEventDefinition.class, EventDefinitionDialogComposite.class);
		PropertiesCompositeFactory.register(MessageEventDefinition.class, EventDefinitionDialogComposite.class);
		PropertiesCompositeFactory.register(SignalEventDefinition.class, EventDefinitionDialogComposite.class);
		PropertiesCompositeFactory.register(TimerEventDefinition.class, EventDefinitionDialogComposite.class);
		PropertiesCompositeFactory.register(Process.class, ProcessDiagramPropertyComposite.class);
		PropertiesCompositeFactory.register(EndEvent.class, EndEventDetailComposite.class);
		PropertiesCompositeFactory.register(StartEvent.class, StartEventDetailComposite.class);
		PropertiesCompositeFactory.register(ThrowEvent.class, ThrowEventDetailComposite.class);
		PropertiesCompositeFactory.register(StandardLoopCharacteristics.class, StandardLoopCharacteristicsDetailComposite.class);
		PropertiesCompositeFactory.register(MultiInstanceLoopCharacteristics.class, MultiInstanceLoopCharacteristicsDetailComposite.class);
		PropertiesCompositeFactory.register(Gateway.class, GatewayDetailComposite.class);
		PropertiesCompositeFactory.register(Activity.class, ActivityInputDetailComposite.class);
		PropertiesCompositeFactory.register(InputOutputSpecification.class, ActivityInputDetailComposite.class);
		PropertiesCompositeFactory.register(Activity.class, ActivityOutputDetailComposite.class);
		PropertiesCompositeFactory.register(CallChoreography.class, ActivityDetailComposite.class);
		PropertiesCompositeFactory.register(InputOutputSpecification.class, IoParametersDetailComposite.class);
		PropertiesCompositeFactory.register(DataInput.class, DataAssociationDetailComposite.class);
		PropertiesCompositeFactory.register(DataOutput.class, DataAssociationDetailComposite.class);
		PropertiesCompositeFactory.register(ManualTask.class, ManualTaskDetailComposite.class);
		PropertiesCompositeFactory.register(ScriptTask.class, ScriptTaskDetailComposite.class);
		PropertiesCompositeFactory.register(SubProcess.class, ActivityDetailComposite.class);
		PropertiesCompositeFactory.register(Task.class, TaskDetailComposite.class);
	}

	/**
	 * The constructor
	 */
	public Activator() {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;

		initializeResourceChangeListener();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
		
		IWorkspace workspace = ResourcesPlugin.getWorkspace();
		if (workspace != null) {
			workspace.removeResourceChangeListener(this.resourceChangeListener);
		}
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

	public static void logStatus(IStatus status) {
		Platform.getLog(plugin.getBundle()).log(status);
	}

	public static void logError(Exception e) {
		logStatus(createStatus(e));
	}

	private static Status createStatus(Exception e) {
		return new Status(IStatus.ERROR, Activator.PLUGIN_ID, e.getMessage(), e);
	}

	public static void showErrorWithLogging(Exception e){
		Status s = createStatus(e);
		logStatus(s);
		ErrorDialog.openError(PlatformUI.getWorkbench().getDisplay().getActiveShell(), "An error occured", null, s);
	}

	/**
	 * Return the dialog settings for a given object. The object may be a string
	 * or any other java object. In that case, the object's class name will be used
	 * to retrieve that section name.
	 * 
	 * @param object 
	 * @return the dialog settings for that object
	 * 
	 */
	public IDialogSettings getDialogSettingsFor ( Object object ) 
	{
	    String name = object.getClass().getName();
	    if (object instanceof String) {
	        name = (String) object;
	    }
	    
	    IDialogSettings main = getDialogSettings();	    
	    IDialogSettings settings = main.getSection( name );
	    if (settings == null) {
	        settings = main.addNewSection(name);
	    }
	    return settings;
	}

	/**
	 * @return
	 */
	public String getID() {
		return getBundle().getSymbolicName();
	}
	
	/**
	 * Initializes the table of images used in this plugin.
	 */
	@Override
	protected ImageRegistry createImageRegistry() {
		ImageRegistry registry = super.createImageRegistry();
		URL baseURL = getBundle().getEntry("/"); //$NON-NLS-1$

		// A little reflection magic ... so that we don't
		// have to add the createImageDescriptor every time
		// we add it to the IConstants ..
		Field fields[] = IConstants.class.getFields();	
		for(int i=0; i < fields.length; i++) {
			Field f = fields[i];
			if (f.getType() != String.class) { 
				continue;
			}
			String name = f.getName();
			if (name.startsWith("ICON_") || name.startsWith("CURSOR_") || name.startsWith("IMAGE_")) {   //$NON-NLS-1$ //$NON-NLS-2$
				try {
					String value = (String) f.get(null);
					createImageDescriptor(registry, value, baseURL);
				} catch (Exception e) {
					logError(e);
				}
			}			
		}
		return registry;
	}

	/**
	 * Creates an image descriptor and places it in the image registry.
	 */
	private void createImageDescriptor(ImageRegistry registry, String id, URL baseURL) {
		URL url = null;
		try {
			url = new URL(baseURL, IConstants.ICON_PATH + id);
		} catch (MalformedURLException e) {
			logError(e);
		}
		ImageDescriptor desc = ImageDescriptor.createFromURL(url);
		registry.put(id, desc);
	}

	public Image getImage(String id) {
		return getImageRegistry().get(id);
	}
	
    public ImageDescriptor getImageDescriptor(String id) {
		return getImageRegistry().getDescriptor(id);
    }

	/**
	 * Installs the IResourceChangeListener for this Plugin. Also
	 * checks if there were any changes to bpmn files while the plug-in
	 * was not active.
	 */
	private void initializeResourceChangeListener() throws CoreException {
		this.resourceChangeListener = new BPMN2ResourceChangeListener();
		// Add the save participant in a separate thread
		// to make sure that it doesn't block the UI thread and potentially cause
		// deadlocks with the code that caused our plugin to be started.
		Thread initSaveParticipantThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					IWorkspace workspace = ResourcesPlugin.getWorkspace();
					workspace.addResourceChangeListener(Activator.this.resourceChangeListener, IResourceChangeEvent.POST_BUILD);
					ISavedState savedState = workspace.addSaveParticipant("BPMN2 Modeler", getSaveParticipant());
					if (savedState != null) {
						savedState.processResourceChangeEvents(Activator.this.resourceChangeListener);
					}
				} catch (CoreException e) {
					throw new RuntimeException(e);
				}
			}
		});
		initSaveParticipantThread.setName("BPMN2 Modeler plugin init"); //$NON-NLS-1$
		initSaveParticipantThread.start();
	}

	/**
	 * We are only interested in the resource delta while the plugin was
	 * not active and don't really care about the plug-in save lifecycle.
	 */
	private ISaveParticipant getSaveParticipant() {
		if (this.saveParticipant == null) {
			this.saveParticipant = new ISaveParticipant() {
				@Override
				public void doneSaving(ISaveContext context) {
				}
				@Override
				public void prepareToSave(ISaveContext context) throws CoreException {
				}
				@Override
				public void rollback(ISaveContext context) {
				}
				@Override
				public void saving(ISaveContext context) throws CoreException {
					context.needDelta();
				}
			};
		}
		return this.saveParticipant;
	}

	/**
	 * Returns the resource change listener.
	 */
	public BPMN2ResourceChangeListener getResourceChangeListener() {
		return this.resourceChangeListener;
	}

}

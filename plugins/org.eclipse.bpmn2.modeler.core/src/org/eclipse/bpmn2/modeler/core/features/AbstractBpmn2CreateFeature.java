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

package org.eclipse.bpmn2.modeler.core.features;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.modeler.core.LifecycleEvent;
import org.eclipse.bpmn2.modeler.core.LifecycleEvent.EventType;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.bpmn2.modeler.core.di.DIImport;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.ObjectEditingDialog;
import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.preferences.ModelEnablements;
import org.eclipse.bpmn2.modeler.core.runtime.TargetRuntime;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.IExecutionInfo;
import org.eclipse.graphiti.features.IFeatureAndContext;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.osgi.util.NLS;

/**
 * This is the Graphiti CreateFeature base class for all BPMN2 model elements which are considered "shapes"
 * e.g. {@link org.eclipse.bpmn2.Activity}, {@link org.eclipse.bpmn2.DataObject}, {@link org.eclipse.bpmn2.Gateway},
 * {@link org.eclipse.bpmn2.Event}, etc.
 * 
 * The Type Parameter "T" is the BPMN2 element class.
 *
 * @param <T> the generic type of the BPMN2 element 
 */
public abstract class AbstractBpmn2CreateFeature<T extends BaseElement>
		extends AbstractCreateFeature
		implements IBpmn2CreateFeature<T, ICreateContext> {

	/** The changes done. */
	protected boolean changesDone = true;

	/**
	 * Default constructor for this Create Feature.
	 *
	 * @param fp the BPMN2 Modeler Feature Provider
	 *            {@link org.eclipse.bpmn2.modeler.ui.diagram.BPMN2FeatureProvider}
	 * @param name the name of the type of object being created
	 * @param description a verbose description of the object being created
	 */
	public AbstractBpmn2CreateFeature(IFeatureProvider fp, String name, String description) {
		super(fp, name, description);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.features.impl.AbstractFeature#isAvailable(org.eclipse.graphiti.features.context.IContext)
	 * Returns true if this type of shape is available in the tool palette and context menus. 
	 */
	@Override
	public boolean isAvailable(IContext context) {
		return isModelObjectEnabled();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.features.impl.AbstractCreateFeature#getCreateDescription()
	 * This is displayed in the Edit -> Undo/Redo menu 
	 */
	@Override
	public String getCreateDescription() {
		return NLS.bind(Messages.AbstractBpmn2CreateFeature_Create, ModelUtil.toCanonicalString(getBusinessObjectClass().getName()));
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.features.IBpmn2CreateFeature#createBusinessObject(org.eclipse.graphiti.features.context.IContext)
	 * Creates the business object, i.e. the BPMN2 element
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public T createBusinessObject(ICreateContext context) {
		Shape shape = context.getTargetContainer();
		EObject container = BusinessObjectUtil.getBusinessObjectForPictogramElement(shape);
		Resource resource = container.eResource();
		EClass eclass = getBusinessObjectClass();
		ExtendedPropertiesAdapter adapter = ExtendedPropertiesAdapter.adapt(eclass);
		String id = (String)context.getProperty(ICustomElementFeatureContainer.CUSTOM_ELEMENT_ID);
		if (id!=null) {
			adapter.setProperty(ICustomElementFeatureContainer.CUSTOM_ELEMENT_ID, id);
		}
		T businessObject = (T)adapter.getObjectDescriptor().createObject(resource,eclass);
		putBusinessObject(context, businessObject);
		changesDone = true;
		return businessObject;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.features.IBpmn2CreateFeature#getBusinessObject(org.eclipse.graphiti.features.context.IContext)
	 * Fetches the business object from the Create Context
	 */
	@SuppressWarnings("unchecked")
	public T getBusinessObject(ICreateContext context) {
		return (T) context.getProperty(ContextConstants.BUSINESS_OBJECT);
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.features.IBpmn2CreateFeature#putBusinessObject(org.eclipse.graphiti.features.context.IContext, org.eclipse.emf.ecore.EObject)
	 * Saves the business object in the Create Context.
	 * If the object is a Custom Element, it is initialized as defined in the extension plugin's plugin.xml
	 */
	public void putBusinessObject(ICreateContext context, T businessObject) {
		context.putProperty(ContextConstants.BUSINESS_OBJECT, businessObject);
		
		TargetRuntime.getCurrentRuntime().notify(new LifecycleEvent(EventType.BUSINESSOBJECT_INITIALIZED,
				getFeatureProvider(), context, businessObject));
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.features.IBpmn2CreateFeature#postExecute(org.eclipse.graphiti.IExecutionInfo)
	 * Invoked after the graphic has been created to display an optional configuration dialog.
	 * The configuration dialog popup is enabled/disabled in the user Preferences for BPMN2 Editor.
	 */
	public void postExecute(IExecutionInfo executionInfo) {
		for (IFeatureAndContext fc : executionInfo.getExecutionList()) {
			IContext context = fc.getContext();
			if (context instanceof ICreateContext) {
				ICreateContext cc = (ICreateContext)context;
				T businessObject = getBusinessObject(cc);
				Bpmn2Preferences prefs = (Bpmn2Preferences) ((DiagramEditor) getDiagramEditor()).getAdapter(Bpmn2Preferences.class);
				if (prefs!=null && prefs.getShowPopupConfigDialog(businessObject)) {
					ObjectEditingDialog dialog =
							new ObjectEditingDialog((DiagramEditor)getDiagramEditor(), businessObject);
					dialog.open();
				}
			}
		}
	}

	/**
	 * Creates and prepares a new AddContext from a CreateContext.
	 *
	 * @param context the original CreateContext
	 * @param newObject the new object, a BPMN2 model object.
	 * @return the new AddContext
	 */
	protected AddContext createAddContext(ICreateContext context, Object newObject) {
		AddContext newContext = new AddContext(context, newObject);
		
		// copy properties into the new context
		Object value = context.getProperty(ICustomElementFeatureContainer.CUSTOM_ELEMENT_ID);
		newContext.putProperty(ICustomElementFeatureContainer.CUSTOM_ELEMENT_ID, value);
		value = context.getProperty(DIImport.IMPORT_PROPERTY);
		newContext.putProperty(DIImport.IMPORT_PROPERTY, value);
		value = context.getProperty(ContextConstants.BUSINESS_OBJECT);
		newContext.putProperty(ContextConstants.BUSINESS_OBJECT, value);
		return newContext;
	}
	
	/**
	 * Convenience method to check if a model object was disabled in the extension plugin.
	 * 
	 * @return true/false depending on if the model object is enabled or disabled.
	 * If disabled, the object will not be available and will not appear in the tool palette
	 * or context menus.
	 */
	protected boolean isModelObjectEnabled() {
		ModelEnablements me = getModelEnablements();
		if (me!=null)
			return me.isEnabled(getBusinessObjectClass());
		return false;
	}
	
	/**
	 * Checks if model object is enabled.
	 *
	 * @param object the object
	 * @return true, if model object is enabled
	 */
	protected boolean isModelObjectEnabled(EObject object) {
		ModelEnablements me = getModelEnablements();
		if (me!=null)
			return me.isEnabled(object.eClass());
		return false;
	}
	
	/**
	 * Gets the model enablements.
	 *
	 * @return the model enablements
	 */
	protected ModelEnablements getModelEnablements() {
		DiagramEditor editor = (DiagramEditor) getDiagramEditor();
		return (ModelEnablements) editor.getAdapter(ModelEnablements.class);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.features.impl.AbstractFeature#hasDoneChanges()
	 */
	@Override
	public boolean hasDoneChanges() {
		return changesDone;
	}
}

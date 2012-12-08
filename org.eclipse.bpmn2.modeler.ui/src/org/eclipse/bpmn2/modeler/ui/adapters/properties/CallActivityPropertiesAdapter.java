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

package org.eclipse.bpmn2.modeler.ui.adapters.properties;

import java.util.Hashtable;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.CallActivity;
import org.eclipse.bpmn2.CallableElement;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.DocumentRoot;
import org.eclipse.bpmn2.Import;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.RootElement;
import org.eclipse.bpmn2.modeler.core.utils.ImportUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;

/**
 * @author Bob Brodt
 *
 */
public class CallActivityPropertiesAdapter extends ActivityPropertiesAdapter<CallActivity> {

	/**
	 * @param adapterFactory
	 * @param object
	 */
	public CallActivityPropertiesAdapter(AdapterFactory adapterFactory, CallActivity object) {
		super(adapterFactory, object);

    	EStructuralFeature ce = Bpmn2Package.eINSTANCE.getCallActivity_CalledElementRef();
    	setProperty(ce, UI_CAN_CREATE_NEW, Boolean.TRUE);
    	setProperty(ce, UI_IS_MULTI_CHOICE, Boolean.TRUE);

    	setFeatureDescriptor(ce,
			new RootElementRefFeatureDescriptor<CallActivity>(adapterFactory,object,ce) {
				@Override
				public String getLabel(Object context) {
					return "Called Activity";
				}
				
				@Override
				public String getDisplayName(Object context) {
					CallActivity object = adopt(context);
					CallableElement ce = object.getCalledElementRef();
					if (ce!=null && ce.eIsProxy()) {
						URI uri = ((InternalEObject)ce).eProxyURI();
						if (uri.hasFragment())
							return uri.fragment();
						return uri.lastSegment();
					}
					return super.getDisplayName(context);
				}
				
				@Override
				public Hashtable<String, Object> getChoiceOfValues(Object context) {
					final CallActivity activity = adopt(context);
					Hashtable<String, Object> choices = super.getChoiceOfValues(activity);
					// add processes defined in imports
					ImportUtil importer = new ImportUtil();
					
					Definitions defs = ModelUtil.getDefinitions(object);
					for (Import imp : defs.getImports()) {
						if (ImportUtil.IMPORT_TYPE_BPMN2.equals(imp.getImportType())) {
							// load the process file and look for <process> elements
							Object object = importer.loadImport(imp);
							if (object instanceof DocumentRoot) {
								defs = ((DocumentRoot)object).getDefinitions();
								for (RootElement elem : defs.getRootElements()) {
									if (elem instanceof Process) {
										String label = ModelUtil.getDisplayName(elem) + " (" + imp.getLocation() + ")";
										choices.put(label, elem);
									}
								}
							}
						}
					}
					return choices;
				}
			}
    	);
	}

}

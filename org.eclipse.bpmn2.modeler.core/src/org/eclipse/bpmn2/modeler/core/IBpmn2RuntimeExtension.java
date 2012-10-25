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
package org.eclipse.bpmn2.modeler.core;

import java.util.Hashtable;
import java.util.List;

import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil.Bpmn2DiagramType;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;

public interface IBpmn2RuntimeExtension {

	/**
	 * Check if the given input file is specific to the runtime environment.
	 * The implementation should check for specific extensions and namespaces that identify
	 * the file for this runtime.
	 *  
	 * @param input
	 * @return true if the file is targeted for this runtime, false if the file is generic BPMN 2.0
	 */
	public boolean isContentForRuntime(IEditorInput input);
	
	/**
	 * Return the target namespace defined by this runtime for the given diagram type.
	 * 
	 * @param diagramType
	 * @return a targetNamespace URI
	 */
	public String getTargetNamespace(Bpmn2DiagramType diagramType);
	
	/**
	 * Return the default data type supported by this runtime. This is the data type assumed for
	 * ItemDefinitions that do not reference an Import. ItemDefinitions can override this data
	 * type by referencing an Import where the structure as well as the type URI are defined.
	 * 
	 * @return the data type language URI
	 */
	public String getTypeLanguage();
	
	/**
	 * Return a string array of expression languages supported by this runtime. The first
	 * entry in the array is used as the default expression language for the process, i.e. it
	 * is used in the <definitions expressionLanguage="..."/> declaration.
	 * 
	 * @return an array of String pairs for the list of supported expression languages;
	 * the first string is the expression language URI, the second string is a descriptive
	 * name used in the UI.
	 */
	public String[] getExpressionLanguages();
	
	/**
	 * @param editor
	 */
	public void initialize(DiagramEditor editor);
	
	public Composite getPreferencesComposite(Composite parent, Bpmn2Preferences preferences);
}

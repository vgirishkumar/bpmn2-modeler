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

import org.eclipse.bpmn2.modeler.core.utils.ModelUtil.Bpmn2DiagramType;
import org.eclipse.graphiti.ui.editor.DiagramEditor;
import org.eclipse.ui.IEditorInput;

/**
 * Interface that defines certain behavior of a Target Runtime specialization that must be
 * implemented by a contributing plug-in.  
 */
public interface IBpmn2RuntimeExtension {

	/**
	 * Check if the given input file is specific to the Target Runtime.
	 * The implementation should check for specific extensions and namespaces that identify
	 * the file for this runtime.
	 *  
	 * @param input
	 * @return true if the file is targeted for this runtime, false if the file is generic BPMN 2.0
	 */
	public boolean isContentForRuntime(IEditorInput input);
	
	/**
	 * Return the target namespace defined by this Target Runtime for the given diagram type.
	 * 
	 * @param diagramType
	 * @return a targetNamespace URI
	 */
	public String getTargetNamespace(Bpmn2DiagramType diagramType);
	
	/**
	 * Return the default data type language URIs supported by this Target Runtime.
	 * The first entry in the array is used as the default data type assumed for ItemDefinitions
	 * that do not reference an Import element. ItemDefinitions can override this data type by
	 * referencing an Import where the structure as well as the type URI are defined.
	 * 
	 * @return an array of String pairs for the list of supported type languages;
	 * the first string is the type language URI, the second string is a descriptive
	 * name used in the UI.
	 */
	public String[] getTypeLanguages();
	
	/**
	 * Return a string array of expression languages supported by this Target Runtime. The first
	 * entry in the array is used as the default expression language for the process, i.e. it
	 * is used in the <definitions expressionLanguage="..."/> declaration.
	 * 
	 * @return an array of String pairs for the list of supported expression languages;
	 * the first string is the expression language URI, the second string is a descriptive
	 * name used in the UI.
	 */
	public String[] getExpressionLanguages();
	
	/**
	 * This method is invoked by the BPMN2 Diagram editor during startup, immediately after a 
	 * bpmn2 resource has been loaded, and just before the graphical elements have been realized.
	 *  
	 * @param editor
	 */
	public void initialize(DiagramEditor editor);
}

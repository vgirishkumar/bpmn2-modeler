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
 * @author Bob Brodt
 ******************************************************************************/

package org.eclipse.bpmn2.modeler.ui.editor;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.workspace.util.WorkspaceSynchronizer;
import org.eclipse.graphiti.ui.editor.DiagramEditor;

public class BPMN2EditorWorkspaceSynchronizerDelegate implements WorkspaceSynchronizer.Delegate {

	private BPMN2Editor bpmnEditor;

	/**
	 * The DiagramEditorBehavior reacts on a setResourceChanged(true) if he gets
	 * activated.
	 */
	public BPMN2EditorWorkspaceSynchronizerDelegate(DiagramEditor diagramEditor) {
		this.bpmnEditor = (BPMN2Editor)diagramEditor;
	}

	public void dispose() { 
		bpmnEditor = null;
	}

	public boolean handleResourceChanged(Resource resource) {
		return bpmnEditor.handleResourceChanged(resource);
	}

	public boolean handleResourceDeleted(Resource resource) {
		return bpmnEditor.handleResourceDeleted(resource);
	}

	public boolean handleResourceMoved(Resource resource, URI newURI) {
		bpmnEditor.handleResourceMoved(resource, newURI);
		bpmnEditor.refreshContent();
		return true;
	}

}
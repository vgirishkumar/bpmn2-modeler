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

package org.eclipse.bpmn2.modeler.ui.property.dialogs;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.DocumentRoot;
import org.eclipse.bpmn2.modeler.core.IBpmn2RuntimeExtension;
import org.eclipse.bpmn2.modeler.core.model.Bpmn2ModelerResourceSetImpl;
import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.runtime.TargetRuntime;
import org.eclipse.bpmn2.modeler.core.utils.JavaProjectClassLoader;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.ui.Activator;
import org.eclipse.bpmn2.modeler.ui.Messages;
import org.eclipse.bpmn2.modeler.ui.editor.BPMN2Editor;
import org.eclipse.bpmn2.modeler.ui.property.providers.BPMN2DefinitionsTreeContentProvider;
import org.eclipse.bpmn2.modeler.ui.property.providers.JavaTreeContentProvider;
import org.eclipse.bpmn2.modeler.ui.property.providers.ModelLabelProvider;
import org.eclipse.bpmn2.modeler.ui.property.providers.ModelTreeLabelProvider;
import org.eclipse.bpmn2.modeler.ui.property.providers.ServiceTreeContentProvider;
import org.eclipse.bpmn2.modeler.ui.property.providers.TreeNode;
import org.eclipse.bpmn2.modeler.ui.property.providers.VariableTypeTreeContentProvider;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.jface.viewers.ViewerFilter;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.dialogs.SelectionStatusDialog;
import org.eclipse.ui.part.DrillDownComposite;
import org.eclipse.wst.wsdl.Definition;
import org.eclipse.xsd.XSDSchema;

/**
 * Browse for complex/simple types available in the process and choose that
 * simple type.
 * 
 */

public class SchemaImportDialog {
	public static final String UI_EXTENSION_ID = "org.eclipse.bpmn2.modeler.ui";

	// resource type flags for configuring this dialog:
	public final static int ALLOW_XSD   = (1 << 0);
	public final static int ALLOW_WSDL  = (1 << 1);
	public final static int ALLOW_BPMN2 = (1 << 2);
	public final static int ALLOW_JAVA  = (1 << 3);

	SelectionStatusDialog delegate = null;
	
	/**
	 * Create a brand new shiny Schema Import Dialog.
	 * 
	 * @param parent
	 */
	public SchemaImportDialog(Shell parent, int allowedResourceTypes) {
		
		// Get the SchemaImportDialog class for this Target Runtime from contributing plugins;
		// if none found, use a default
		IConfigurationElement[] config = Platform.getExtensionRegistry().getConfigurationElementsFor(
				UI_EXTENSION_ID);
		try {
			for (IConfigurationElement e : config) {
				if (e.getName().equals("importDialog")) {
					String id = e.getAttribute("id");
					String runtimeId = e.getAttribute("runtimeId");
					TargetRuntime rt = TargetRuntime.getCurrentRuntime();
					if (rt!=null && rt.getId().equals(runtimeId)) {
						delegate = (SelectionStatusDialog)e.createExecutableExtension("class");
						break;
					}
				}
			}
		}
		catch (Exception ex) {
			Activator.logError(ex);
		}
		if (delegate==null)
			delegate = new DefaultSchemaImportDialog(parent,allowedResourceTypes);
	}
	
	public SchemaImportDialog(Shell parent) {
		this(parent, -1);
	}

	public int open() {
		return delegate.open();
	}

	public Object[] getResult() {
		return delegate.getResult();
	}
}

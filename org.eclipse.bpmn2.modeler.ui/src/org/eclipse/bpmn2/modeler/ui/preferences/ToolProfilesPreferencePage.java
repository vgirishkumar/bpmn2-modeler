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
 * @author Innar Made
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.ui.preferences;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.preferences.ModelEnablementTreeEntry;
import org.eclipse.bpmn2.modeler.core.preferences.ModelEnablements;
import org.eclipse.bpmn2.modeler.core.preferences.ToolProfilesPreferencesHelper;
import org.eclipse.bpmn2.modeler.core.runtime.ModelEnablementDescriptor;
import org.eclipse.bpmn2.modeler.core.runtime.TargetRuntime;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil.Bpmn2DiagramType;
import org.eclipse.bpmn2.modeler.ui.Activator;
import org.eclipse.core.databinding.DataBindingContext;
import org.eclipse.core.databinding.observable.list.WritableList;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.jface.viewers.CheckStateChangedEvent;
import org.eclipse.jface.viewers.CheckboxTreeViewer;
import org.eclipse.jface.viewers.ICheckStateListener;
import org.eclipse.jface.viewers.ICheckStateProvider;
import org.eclipse.jface.viewers.IElementComparer;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPreferencePage;
import org.eclipse.ui.PlatformUI;
import org.osgi.service.prefs.BackingStoreException;

public class ToolProfilesPreferencePage extends PreferencePage implements IWorkbenchPreferencePage {

	private Bpmn2Preferences preferences;
	private TargetRuntime currentRuntime;
	private Bpmn2DiagramType currentDiagramType;
	private String currentProfile;
	private final List<ModelEnablementTreeEntry> bpmnEntries = new ArrayList<ModelEnablementTreeEntry>();
	private final List<ModelEnablementTreeEntry> extensionEntries = new ArrayList<ModelEnablementTreeEntry>();
	
	private Combo cboRuntimes;
	private Combo cboDiagramTypes;
	private Combo cboProfiles;
	private Button btnUseAsDefaultProfile;
	private Button btnCreateProfile;
	private Button btnDeleteProfile;
	private CheckboxTreeViewer bpmnTreeViewer;
	private Tree bpmnTree;
	private CheckboxTreeViewer extensionTreeViewer;
	private Tree extensionTree;

	private Hashtable<TargetRuntime,
				Hashtable<Bpmn2DiagramType,
					Hashtable<String, ToolProfilesPreferencesHelper>>> helpers =
		new Hashtable<TargetRuntime,
				Hashtable<Bpmn2DiagramType,
					Hashtable<String,ToolProfilesPreferencesHelper>>>();

	/**
	 * Create the property page.
	 */
	public ToolProfilesPreferencePage() {
		setPreferenceStore(Activator.getDefault().getPreferenceStore());
	}

	@Override
	public void init(IWorkbench workbench) {
		preferences = Bpmn2Preferences.getInstance();
	}

	private ToolProfilesPreferencesHelper getHelper(TargetRuntime rt, Bpmn2DiagramType diagramType, String profile) {
		Hashtable<Bpmn2DiagramType, Hashtable<String,ToolProfilesPreferencesHelper>> map1 = helpers.get(rt);
		
		if (map1==null) {
			map1 = new Hashtable<Bpmn2DiagramType, Hashtable<String,ToolProfilesPreferencesHelper>>();
			helpers.put(rt, map1);
		}
		
		Hashtable<String,ToolProfilesPreferencesHelper> map2 = map1.get(diagramType);
		if (map2==null) {
			map2 = new Hashtable<String,ToolProfilesPreferencesHelper>();
			map1.put(diagramType, map2);
		}
		
		ToolProfilesPreferencesHelper helper = map2.get(profile);
		if (helper==null) {
			helper = new ToolProfilesPreferencesHelper(rt, diagramType, profile);
			ModelEnablements me = preferences.getModelEnablements(rt, diagramType, profile);
			helper.setModelEnablements(me);
			map2.put(profile, helper);
		}


		return helper;
	}

	private boolean hasHelper(TargetRuntime rt, Bpmn2DiagramType diagramType, String profile) {
		Hashtable<Bpmn2DiagramType, Hashtable<String,ToolProfilesPreferencesHelper>> map1 = helpers.get(rt);
		
		if (map1==null) {
			map1 = new Hashtable<Bpmn2DiagramType, Hashtable<String,ToolProfilesPreferencesHelper>>();
			helpers.put(rt, map1);
		}
		
		Hashtable<String,ToolProfilesPreferencesHelper> map2 = map1.get(diagramType);
		if (map2==null) {
			map2 = new Hashtable<String,ToolProfilesPreferencesHelper>();
			map1.put(diagramType, map2);
		}
		
		ToolProfilesPreferencesHelper helper = map2.get(profile);
		if (helper==null) {
			return false;
		}


		return true;
	}
	
	/**
	 * Create contents of the property page.
	 * 
	 * @param parent
	 */
	@Override
	public Control createContents(final Composite parent) {
		
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout(4, false));
		container.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, true, false, 1, 1));

		currentRuntime = TargetRuntime.getCurrentRuntime();
		currentDiagramType = Bpmn2DiagramType.NONE;
		currentProfile = "";
		
		final Label lblRuntime = new Label(container, SWT.NONE);
		lblRuntime.setText("Target Runtime");
		lblRuntime.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));

		cboRuntimes = new Combo(container, SWT.READ_ONLY);
		cboRuntimes.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 3, 1));
		cboRuntimes.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String s = cboRuntimes.getText();
				currentRuntime = (TargetRuntime) cboRuntimes.getData(s);
				fillProfilesCombo();
				fillModelEnablementTrees();
			}
		});
		fillRuntimesCombo();

		final Label lblDiagramType = new Label(container, SWT.NONE);
		lblDiagramType.setText("Diagram Type");
		lblDiagramType.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 2, 1));

		cboDiagramTypes = new Combo(container, SWT.READ_ONLY);
		cboDiagramTypes.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 2, 1));
		cboDiagramTypes.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				String s = cboDiagramTypes.getText();
				currentDiagramType = (Bpmn2DiagramType) cboDiagramTypes.getData(s);
				fillProfilesCombo();
				fillModelEnablementTrees();
			}
		});
		fillDiagramTypesCombo();

		final Label lblProfile = new Label(container, SWT.NONE);
		lblProfile.setText("Tool Profile");
		lblProfile.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 3, 1));

		cboProfiles = new Combo(container, SWT.READ_ONLY);
		cboProfiles.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		cboProfiles.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				currentProfile = cboProfiles.getText();
				fillModelEnablementTrees();
			}
		});
		
		
		Composite buttonContainer = new Composite(container, SWT.NULL);
		buttonContainer.setLayout(new GridLayout(4, false));
		buttonContainer.setLayoutData(new GridData(SWT.FILL, SWT.LEFT, true, false, 4, 1));
		
		btnUseAsDefaultProfile = new Button(buttonContainer, SWT.CHECK);
		btnUseAsDefaultProfile.setText("Use as default profile");
		btnUseAsDefaultProfile.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));
		
		final Label lblFiller = new Label(buttonContainer, SWT.NONE);
		lblFiller.setText("");
		lblFiller.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		btnCreateProfile = new Button(buttonContainer, SWT.PUSH);
		btnCreateProfile.setText("Create Profile");
		btnCreateProfile.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnCreateProfile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				CreateProfileDialog dlg = new CreateProfileDialog(parent.getShell());
				if (dlg.open() == Window.OK) {
					if (dlg.getCopyRuntime()!=null) {
						TargetRuntime saveRuntime = currentRuntime;
						Bpmn2DiagramType saveDiagramType = currentDiagramType;
						currentRuntime = dlg.getCopyRuntime();
						currentDiagramType = dlg.getCopyDiagramType();
						currentProfile = dlg.getCopyProfile();
						fillModelEnablementTrees();
						currentRuntime = saveRuntime;
						currentDiagramType = saveDiagramType;
					}					
					currentProfile = dlg.getValue();
					preferences.createToolProfile(currentRuntime, currentDiagramType, currentProfile);
					preferences.setDefaultToolProfile(currentRuntime, currentDiagramType, currentProfile);
					fillProfilesCombo();
					loadModelEnablements();
				}
			}
		});
		
		btnDeleteProfile = new Button(buttonContainer, SWT.PUSH);
		btnDeleteProfile.setText("Delete Profile");
		btnDeleteProfile.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		btnDeleteProfile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				preferences.deleteToolProfile(currentRuntime, currentDiagramType, currentProfile);
				fillProfilesCombo();
				loadModelEnablements();
			}
		});

		fillProfilesCombo();

		
		final Composite treesContainer = new Composite(container, SWT.BORDER);
		treesContainer.setLayout(new GridLayout(2, true));
		treesContainer.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 4, 1));
		
		// Create Checkbox Tree Viwers for standard BPMN 2.0 elements and any extension elements
		bpmnTreeViewer = createCheckboxTreeViewer(treesContainer, Messages.ToolProfilePreferencePage_Standard_Elements_Label);
		bpmnTree = bpmnTreeViewer.getTree();

		extensionTreeViewer = createCheckboxTreeViewer(treesContainer, Messages.ToolProfilePreferencePage_Extension_Elements_Label);
		extensionTree = extensionTreeViewer.getTree();

		// adjust height of the tree viewers to fill their container when dialog is resized
		// oddly enough, setting GridData.widthHint still causes the controls to fill available
		// horizontal space, but setting heightHint just keeps them the same height. Probably
		// because a GridLayout has a fixed number of columns, but variable number of rows.
		parent.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				GridData gd = (GridData) bpmnTree.getLayoutData();
				gd.heightHint = 1000;
				gd = (GridData) extensionTree.getLayoutData();
				gd.heightHint = 1000;
				treesContainer.layout();
			}
		});

		Composite importExportButtons = new Composite(container, SWT.NONE);
		importExportButtons.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 4, 1));
		importExportButtons.setLayout(new FillLayout());

		Button btnImportProfile = new Button(importExportButtons, SWT.NONE);
		btnImportProfile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), SWT.NULL);
				String path = dialog.open();
				if (path != null) {
					try {
						bpmnEntries.clear();
						extensionEntries.clear();
						getHelper(currentRuntime, currentDiagramType, currentProfile).importPreferences(path);
						loadModelEnablements();
						bpmnTreeViewer.refresh();
						extensionTreeViewer.refresh();
					} catch (Exception e1) {
						Activator.showErrorWithLogging(e1);
					}
				}
			}
		});
		btnImportProfile.setText(Messages.ToolProfilePreferencePage_Import);

		Button btnExportProfile = new Button(importExportButtons, SWT.NONE);
		btnExportProfile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(PlatformUI.getWorkbench().getDisplay().getActiveShell(), SWT.SAVE);
				String path = dialog.open();
				if (path != null) {
					try {
						getHelper(currentRuntime, currentDiagramType, currentProfile).exportPreferences(path);
					} catch (Exception e1) {
						Activator.showErrorWithLogging(e1);
					}
				}
			}
		});
		btnExportProfile.setText(Messages.ToolProfilePreferencePage_Export);

		fillModelEnablementTrees();
		
		bpmnTree.setEnabled(true);
		extensionTree.setEnabled(true);

		return container;
	}

	private void fillRuntimesCombo() {
		int i = 0;
		for (TargetRuntime r : TargetRuntime.getAllRuntimes()) {
			cboRuntimes.add(r.getName());
			cboRuntimes.setData(r.getName(), r);
			if (r == currentRuntime)
				cboRuntimes.select(i);
			++i;
		}
	}
	
	private void fillDiagramTypesCombo() {
		int i = 0;
		currentDiagramType = Bpmn2DiagramType.PROCESS;

		for (Bpmn2DiagramType t : Bpmn2DiagramType.values()) {
			cboDiagramTypes.add(t.toString());
			cboDiagramTypes.setData(t.toString(), t);
			if (t==currentDiagramType)
				cboDiagramTypes.select(i);
			++i;
		}
	}
	
	private void fillProfilesCombo() {
		if (cboProfiles!=null) {
			int i = 0;
			int iSelected = -1;
			currentProfile = preferences.getDefaultToolProfile(currentRuntime, currentDiagramType);
	
			cboProfiles.removeAll();
			for (String profile : preferences.getAllToolProfiles(currentRuntime, currentDiagramType)) {
				String text = profile;
				if (text==null || text.isEmpty())
					text = Messages.ToolProfilePreferencePage_Unnamed + (i+1);
				cboProfiles.add(text);
				if (iSelected<0 && (currentProfile!=null && currentProfile.equals(profile)))
					cboProfiles.select(iSelected = i);
				++i;
			}
			if (btnDeleteProfile!=null)
				btnDeleteProfile.setEnabled(cboProfiles.getItemCount()>0);

		}
	}
	
	private void fillModelEnablementTrees() {
		loadModelEnablements();
		initDataBindings(bpmnTreeViewer, bpmnEntries);
		initDataBindings(extensionTreeViewer, extensionEntries);

		if (TargetRuntime.DEFAULT_RUNTIME_ID.equals(currentRuntime.getId())) {
			extensionTree.setVisible(false);
			GridData data = (GridData)extensionTree.getLayoutData();
			data.exclude = true;
		}
		else {
			extensionTree.setVisible(true);
			GridData data = (GridData)extensionTree.getLayoutData();
			data.exclude = false;
		}
		extensionTree.getParent().layout();
	}
	
	private CheckboxTreeViewer createCheckboxTreeViewer(Composite parent, String name) {
		
		Composite container = new Composite(parent, SWT.NULL);
		container.setLayout(new GridLayout(1, true));
		container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));

		final Label label = new Label(container, SWT.NONE);
		label.setText(name);
		label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));

		final CheckboxTreeViewer treeViewer = new CheckboxTreeViewer(container, SWT.BORDER);
		final Tree tree = treeViewer.getTree();

		GridData data = new GridData(SWT.FILL, SWT.TOP, true, true, 1, 1);
		data.heightHint = 200;
		data.widthHint = 50;
		tree.setLayoutData(data);
		treeViewer.setCheckStateProvider(new ICheckStateProvider() {
			@Override
			public boolean isChecked(Object element) {
				if (element instanceof ModelEnablementTreeEntry) {
					ModelEnablementTreeEntry modelEnablementTreeEntry = (ModelEnablementTreeEntry)element;
					if (modelEnablementTreeEntry.getChildren().size()>0) {
						for (ModelEnablementTreeEntry child : modelEnablementTreeEntry.getChildren()) {
							if (child.getEnabled())
								return true;
						}
						return false;
					}
					return modelEnablementTreeEntry.getEnabled();
				}
				return false;
			}

			@Override
			public boolean isGrayed(Object element) {
				if (element instanceof ModelEnablementTreeEntry) {
					ModelEnablementTreeEntry modelEnablementTreeEntry = (ModelEnablementTreeEntry)element;
					int countEnabled = 0;
					for (ModelEnablementTreeEntry child : modelEnablementTreeEntry.getChildren()) {
						if (child.getEnabled())
							++countEnabled;
					}
					return countEnabled>0 && countEnabled != modelEnablementTreeEntry.getChildren().size();
				}
				return false;
			}
			
		});
		
		treeViewer.addCheckStateListener(new ICheckStateListener() {
			@Override
			public void checkStateChanged(CheckStateChangedEvent event) {
				boolean checked = event.getChecked();
				Object element = event.getElement();
				if (element instanceof ModelEnablementTreeEntry) {
					ModelEnablementTreeEntry modelEnablementTreeEntry = (ModelEnablementTreeEntry)element;
					updateDescendents(modelEnablementTreeEntry, checked);
					updateAncestors(modelEnablementTreeEntry.getParent(), checked);
					
					ToolProfilesPreferencesHelper helper = getHelper(currentRuntime, currentDiagramType, currentProfile);
					helper.setEnabled(modelEnablementTreeEntry, checked);
				}
			}
			
			void updateDescendents(ModelEnablementTreeEntry modelEnablementTreeEntry, boolean checked) {
				for (ModelEnablementTreeEntry child : modelEnablementTreeEntry.getChildren()) {
					updateDescendents(child,checked);
				}
				modelEnablementTreeEntry.setSubtreeEnabled(checked);
				treeViewer.setSubtreeChecked(modelEnablementTreeEntry, checked);
				
				treeViewer.setChecked(modelEnablementTreeEntry, checked);
				treeViewer.setGrayed(modelEnablementTreeEntry, false);
				for (ModelEnablementTreeEntry friend : modelEnablementTreeEntry.getFriends()) {
					updateAncestors(friend, checked);
					if (friend.getParent()!=null)
						updateAncestors(friend.getParent(), checked);
				}
				for (ModelEnablementTreeEntry child : modelEnablementTreeEntry.getChildren()) {
					for (ModelEnablementTreeEntry friend : child.getFriends()) {
						if (child.getParent()!=null)
							updateAncestors(child.getParent(), checked);
						updateAncestors(friend, checked);
					}
				}
			}
			
			void updateAncestors(ModelEnablementTreeEntry parent, boolean checked) {
				while (parent!=null) {
					int enabled = parent.getSubtreeEnabledCount();
					int size = parent.getSubtreeEnabledCount();
					if (enabled==0) {
						treeViewer.setChecked(parent, false);
						parent.setEnabled(false);
						checked = true;
					}
					else if (enabled==size) {
						treeViewer.setChecked(parent, true);
						treeViewer.setGrayed(parent, false);
						parent.setEnabled(true);
					}
					else {
						treeViewer.setGrayChecked(parent, true);
						parent.setEnabled(true);
					}
					
					for (ModelEnablementTreeEntry friend : parent.getFriends()) {
						updateAncestors(friend, checked);
					}
					bpmnTreeViewer.refresh(parent);
					extensionTreeViewer.refresh(parent);
					parent = parent.getParent();
				}
			}
		});

		treeViewer.setComparer(new IElementComparer() {

			@Override
			public boolean equals(Object a, Object b) {
				return a == b;
			}

			@Override
			public int hashCode(Object element) {
				return System.identityHashCode(element);
			}
		});
		treeViewer.setUseHashlookup(true);
		
		return treeViewer;
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		String path = Bpmn2Preferences.getToolProfilePath(currentRuntime, currentDiagramType);
		preferences.setToDefault(path);
		path = Bpmn2Preferences.getModelEnablementsPath(currentRuntime, currentDiagramType, currentProfile);
		preferences.setToDefault(path);
		fillProfilesCombo();
		fillModelEnablementTrees();
	}

	private void loadModelEnablements() {
		ToolProfilesPreferencesHelper helper = getHelper(currentRuntime, currentDiagramType, currentProfile);

		loadModelEnablements(helper, bpmnEntries, null, null);
		ModelEnablementDescriptor med = currentRuntime.getModelEnablements(currentDiagramType, currentProfile);
		if (med!=null)
			loadModelEnablements(helper, extensionEntries, bpmnEntries, med);
	}
	
	private Object[] loadModelEnablements(ToolProfilesPreferencesHelper helper, List<ModelEnablementTreeEntry> entries, List<ModelEnablementTreeEntry> bpmnEntries, ModelEnablementDescriptor med) {
		entries.clear();
		if (med!=null)
			entries.addAll(helper.getAllExtensionElements(med, bpmnEntries));
		else
			entries.addAll(helper.getAllElements());

		ArrayList<ModelEnablementTreeEntry> enabled = new ArrayList<ModelEnablementTreeEntry>();
		for (ModelEnablementTreeEntry entry : entries) {
			if (entry.getEnabled()) {
				enabled.add(entry);
			}
			ArrayList<ModelEnablementTreeEntry> children = entry.getChildren();
			for (ModelEnablementTreeEntry t : children) {
				if (t.getEnabled()) {
					enabled.add(t);
				}
			}
		}
		return enabled.toArray();
	}

	@Override
	public boolean performOk() {
		setErrorMessage(null);
		try {
			for (TargetRuntime rt : TargetRuntime.getAllRuntimes()) {
				for (Bpmn2DiagramType diagramType : Bpmn2DiagramType.values()) {
					for (String profile : preferences.getAllToolProfiles(rt, diagramType)) {
						if (hasHelper(rt, diagramType, profile)) {
							ToolProfilesPreferencesHelper helper = getHelper(rt, diagramType, profile);
							preferences.setModelEnablements(rt, diagramType, profile, helper.getModelEnablements());
						}
					}
				}
			}

			if (btnUseAsDefaultProfile.getSelection())
				preferences.setDefaultToolProfile(currentRuntime, currentDiagramType, currentProfile);
			
			preferences.save();
		} catch (BackingStoreException e) {
			Activator.showErrorWithLogging(e);
		}
		return true;
	}

	protected DataBindingContext initDataBindings(CheckboxTreeViewer treeViewer, List<ModelEnablementTreeEntry> entries) {
		if (treeViewer==null || entries==null)
			return null;
		
		DataBindingContext bindingContext = new DataBindingContext();
		//
		treeViewer.setContentProvider(new ITreeContentProvider() {

			@Override
			public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
			}

			@Override
			public void dispose() {
			}

			@Override
			public boolean hasChildren(Object element) {
				if (element instanceof ModelEnablementTreeEntry) {
					return !((ModelEnablementTreeEntry) element).getChildren().isEmpty();
				}
				return false;
			}

			@Override
			public Object getParent(Object element) {
				if (element instanceof ModelEnablementTreeEntry) {
					return ((ModelEnablementTreeEntry) element).getParent();
				}
				return null;
			}

			@Override
			public Object[] getElements(Object inputElement) {
				if (inputElement instanceof WritableList) {
					return ((WritableList) inputElement).toArray();
				}
				return null;
			}

			@Override
			public Object[] getChildren(Object parentElement) {
				if (parentElement instanceof ModelEnablementTreeEntry) {
					return ((ModelEnablementTreeEntry) parentElement).getChildren().toArray();
				}
				return null;
			}
		});

		treeViewer.setLabelProvider(new ILabelProvider() {
			@Override
			public void removeListener(ILabelProviderListener listener) {
			}

			@Override
			public boolean isLabelProperty(Object element, String property) {
				return false;
			}

			@Override
			public void dispose() {

			}

			@Override
			public void addListener(ILabelProviderListener listener) {
			}

			@Override
			public Image getImage(Object element) {
				return null;
			}

			@Override
			public String getText(Object element) {
				if (element instanceof ModelEnablementTreeEntry) {
					return ((ModelEnablementTreeEntry) element).getName();
				}
				return null;
			}
		});
		WritableList writableList = new WritableList(entries, ModelEnablementTreeEntry.class);
		treeViewer.setInput(writableList);
		//
		return bindingContext;
	}
	
	private class CreateProfileDialog extends InputDialog {

		private String copySelection = null;
		private TargetRuntime copyRuntime = null;
		
		public CreateProfileDialog(Shell parentShell) {
			super(parentShell, "Create New Profile", "Enter a profile name", "", new IInputValidator() {

				@Override
				public String isValid(String newText) {
					if (newText==null || newText.isEmpty())
						return "Profile name can not be empty";
					for (String p : cboProfiles.getItems()) {
						if (newText.equals(p))
							return "Profile "+p+" is already defined";
					}
					return null;
				}
				
			});
		}

		public String getCopyProfile() {
			if (copySelection!=null) {
				String a[] = copySelection.split("/");
				return a[2];
			}
			return null;
		}

		public Bpmn2DiagramType getCopyDiagramType() {
			if (copySelection!=null) {
				String a[] = copySelection.split("/");
				return Bpmn2DiagramType.fromString(a[1]);
			}
			return null;
		}
		
		public TargetRuntime getCopyRuntime() {
			return copyRuntime;
		}
		
		@Override
	    protected Control createDialogArea(Composite parent) {
			Composite composite = (Composite) super.createDialogArea(parent);

			Composite container = new Composite(composite, SWT.NULL);
			container.setLayout(new GridLayout(2, false));
			container.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 1, 1));
			
			final Button btnCopy = new Button(container, SWT.CHECK);
			btnCopy.setText("Copy settings from this profile:");
			btnCopy.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1));

			final Combo cboCopy = new Combo(container, SWT.READ_ONLY);
			cboCopy.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, true, false, 1, 1));
			cboCopy.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					copySelection = cboCopy.getText();
					copyRuntime = (TargetRuntime) cboCopy.getData(copySelection);
				}
			});
			
			for (TargetRuntime rt : TargetRuntime.getAllRuntimes()) {
				for (Bpmn2DiagramType diagramType : Bpmn2DiagramType.values()) {
					for (String profile : preferences.getAllToolProfiles(rt, diagramType)) {
						String key = rt.getName() + "/" + diagramType + "/" + profile;
						cboCopy.add(key);
						cboCopy.setData(key, rt);
					}
				}
			}
			cboCopy.setEnabled(false);
			
			btnCopy.setEnabled(cboCopy.getItemCount()>0);
			btnCopy.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent e) {
					cboCopy.setEnabled(btnCopy.getSelection());
					if (!btnCopy.getSelection())
						copySelection = null;
				}
			});
			
			return composite;
		}
	}
}

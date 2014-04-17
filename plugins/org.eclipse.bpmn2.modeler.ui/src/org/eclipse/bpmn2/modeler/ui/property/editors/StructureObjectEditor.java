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

package org.eclipse.bpmn2.modeler.ui.property.editors;

import org.eclipse.bpmn2.Import;
import org.eclipse.bpmn2.ItemDefinition;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.ComboObjectEditor;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.ObjectEditor;
import org.eclipse.bpmn2.modeler.ui.property.dialogs.SchemaSelectionDialog;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.jface.window.Window;

/**
 * This class implements a Data Structure editor which consists of an editable
 * Combobox field and a "Browse" button. The button allows for selection of a BPMN2
 * Import and schema element defined in the Import. The button uses the
 * {@link SchemaSelectionDialog} which can be used to either select an existing
 * Import or add a new Import to the BPMN2 file.
 * <p>
 * The ItemDefinition which is the object of this {@link ObjectEditor} will be
 * populated with the structureRef (the selected schema element) and the
 * import selected in the {@link SchemaSelectionDialog}
 */
public class StructureObjectEditor extends ComboObjectEditor {

	/**
	 * @param parent
	 * @param object
	 * @param feature
	 */
	public StructureObjectEditor(AbstractDetailComposite parent, EObject object, EStructuralFeature feature) {
		super(parent, object, feature);
	}

	@Override
	protected boolean canEdit() {
		return true;
	}

	@Override
	protected boolean canCreateNew() {
		return true;
	}

	@Override
	protected boolean canSetNull() {
		return true;
	}
	
	@Override
	protected void buttonClicked(int buttonId) {
		if (buttonId==ID_CREATE_BUTTON) {
			Object[] result = SchemaObjectEditor.showSchemaSelectionDialog(parent, object);
			if (result.length==2) {
				setValue((String)result[0]);
				if (object instanceof ItemDefinition) {
					((ItemDefinition)object).setImport((Import)result[1]);
				}
				fillCombo();
			}
		}
		else if (buttonId==ID_EDIT_BUTTON) {
			IInputValidator validator = new IInputValidator() {

				@Override
				public String isValid(String newText) {
					if (newText==null || newText.isEmpty())
						return "Data Type may not be empty";
					return null;
				}
				
			};
			StructuredSelection sel = (StructuredSelection)comboViewer.getSelection();
			String value = (String) sel.getFirstElement();

			InputDialog dialog = new InputDialog(
					parent.getShell(),
					"Edit Data Type",
					"Enter new data type:",
					value,
					validator);
			if (dialog.open()==Window.OK){
				setValue(dialog.getValue());
				if (object instanceof ItemDefinition) {
					((ItemDefinition)object).setImport(null);
				}
				fillCombo();
			}
		}
	}
}

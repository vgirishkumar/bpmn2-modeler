/*******************************************************************************
 * Copyright (c) 2011, 2012 Red Hat, Inc. 
 * All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 *
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 *******************************************************************************/
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property;

import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.TextAndButtonObjectEditor;
import org.eclipse.bpmn2.modeler.core.validation.SyntaxCheckerUtils;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.ImportType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.util.JbpmModelUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jdt.core.IType;
import org.eclipse.osgi.util.NLS;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class JbpmImportObjectEditor extends TextAndButtonObjectEditor {

	public JbpmImportObjectEditor(AbstractDetailComposite parent, EObject object, EStructuralFeature feature) {
		super(parent, object, feature);
	}
	
	@Override
	protected Control createControl(Composite composite, String label, int style) {
		super.createControl(composite, label, style);
		// the Text field should be editable
		text.setEditable(true);
		text.addVerifyListener(new VerifyListener() {

			/**
			 * taken from
			 * http://dev.eclipse.org/viewcvs/viewvc.cgi/org.eclipse.swt.snippets/src/org/eclipse/swt/snippets
			 * /Snippet19.java?view=co
			 */
			@Override
			public void verifyText(VerifyEvent e) {
				if (Character.isISOControl(e.character)) {
					if (e.text==null || e.text.isEmpty())
						return;
				}
				String s = getValue() + e.text;
				e.doit = SyntaxCheckerUtils.isJavaPackageName(s);
				if (!e.doit) {
					showErrorMessage(NLS.bind(Messages.JbpmImportObjectEditor_Invalid_Character, e.text));
				}
			}
		});
		
		// and change the "Edit" button to a "Browse" to make it clear that
		// an XML type can be selected from the imports 
		defaultButton.setText(Messages.JbpmImportObjectEditor_Browse);
		return text;
	}

	@Override
	protected void buttonClicked(int buttonId) {
	    IType type = JbpmModelUtil.showImportDialog(object);
		ImportType imp = JbpmModelUtil.addImport(type, object);
		if (imp!=null)
			setText(imp.getName());
	}
}

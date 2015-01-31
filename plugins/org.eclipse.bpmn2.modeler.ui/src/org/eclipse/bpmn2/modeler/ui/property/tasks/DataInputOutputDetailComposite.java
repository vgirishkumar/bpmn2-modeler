/*******************************************************************************
 * Copyright (c) 2011, 2012, 2013, 2014 Red Hat, Inc.
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

package org.eclipse.bpmn2.modeler.ui.property.tasks;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.DataAssociation;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.ComboObjectEditor;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.ObjectEditor;
import org.eclipse.bpmn2.modeler.ui.property.data.ItemAwareElementDetailComposite;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.swt.widgets.Composite;

public class DataInputOutputDetailComposite extends ItemAwareElementDetailComposite {

	private List<DataAssociation> associations = null;

	public DataInputOutputDetailComposite(Composite parent, int style) {
		super(parent, style);
	}
	
	public Composite getAttributesParent() {
		return this;
	}

	@Override
	protected void bindReference(Composite parent, EObject object, EReference reference) {
		if (reference.getName().equals("itemSubjectRef")) { //$NON-NLS-1$
			String displayName = getBusinessObjectDelegate().getLabel(object, reference);
			ObjectEditor editor = null;
			editor = new ComboObjectEditor(this,object,reference) {

				@Override
				protected boolean setValue(Object result) {
					if (super.setValue(result)) {
						return true;
					}
					return false;
				}
			};
			editor.createControl(parent,displayName);
		}
		else
			super.bindReference(parent, object, reference);
	}
	
	public void setAssociations(List<? extends DataAssociation> associations) {
		if (associations!=null && !associations.isEmpty()) {
			if (this.associations==null)
				this.associations = new ArrayList<DataAssociation>();
			this.associations.addAll(associations);
		}
	}
}
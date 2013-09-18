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
package org.eclipse.bpmn2.modeler.ui.property.events;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.BoundaryEvent;
import org.eclipse.bpmn2.CompensateEventDefinition;
import org.eclipse.bpmn2.ErrorEventDefinition;
import org.eclipse.bpmn2.Event;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultListComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.ListCompositeColumnProvider;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.TableColumn;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.ModelSubclassSelectionDialog;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Composite;

public class EventDefinitionsListComposite extends DefaultListComposite {
	
	protected Event event;
	
	public EventDefinitionsListComposite(Composite parent, Event event) {
		super(parent, DEFAULT_STYLE);
		this.event = event;
	}
	
	protected int createColumnProvider(EObject theobject, EStructuralFeature thefeature) {
		if (columnProvider==null) {
			getColumnProvider(theobject,thefeature);
		}
		return columnProvider.getColumns().size();
	}
	
	@Override
	protected EObject addListItem(EObject object, EStructuralFeature feature) {
		EObject newItem = super.addListItem(object, feature);
		// see https://bugs.eclipse.org/bugs/show_bug.cgi?id=417207
		// the Cancel Activity checkbox should always be TRUE
		// if the Boundary Event contains a Error Event Definition,
		// and should be hidden when it contains a Compensate Event Definition.
		if (object instanceof BoundaryEvent) {
			BoundaryEvent be = (BoundaryEvent) object;
			if (newItem instanceof ErrorEventDefinition) {
				be.setCancelActivity(true);
				((AbstractDetailComposite)getParent()).refresh();
			}
			else if (newItem instanceof CompensateEventDefinition) {
				be.setCancelActivity(false);
				((AbstractDetailComposite)getParent()).refresh();
			}
		}
		return newItem;
	}

	@Override
	protected Object removeListItem(EObject object, EStructuralFeature feature, int index) {
		Object oldItem = getListItem(object,feature,index);
		Object newItem = super.removeListItem(object, feature, index);
		if (object instanceof BoundaryEvent) {
			BoundaryEvent be = (BoundaryEvent) object;
			if (oldItem instanceof ErrorEventDefinition) {
				be.setCancelActivity(true);
				((AbstractDetailComposite)getParent()).refresh();
			}
			else if (oldItem instanceof CompensateEventDefinition) {
				be.setCancelActivity(false);
				((AbstractDetailComposite)getParent()).refresh();
			}
		}
		return newItem;
	}

	@Override
	public ListCompositeColumnProvider getColumnProvider(EObject object, EStructuralFeature feature) {
		columnProvider = super.getColumnProvider(object,feature);
		columnProvider.add(
				new TableColumn(object,feature) {
					public String getText(Object element) {
						EObject o = (EObject)element;
						return o.eClass().getName().replace("EventDefinition", "");
					}

					@Override
					public String getHeaderText() {
						return "Event Type";
					}
					
					@Override
					public CellEditor createCellEditor (Composite parent) {
						// need to override this to avoid any problems
						return super.createCellEditor(parent);
					}
				}
			).setEditable(false);
		return columnProvider;
	}
	
	@Override
	public EClass getListItemClassToAdd(EClass listItemClass) {
		EClass eclass = null;
		ModelSubclassSelectionDialog dialog = new ModelSubclassSelectionDialog(getDiagramEditor(), businessObject, feature) {
			@Override
			protected void filterList(List<EClass> items) {
				List<EClass> filteredItems = new ArrayList<EClass>();
				List<EClass> allowedItems = FeatureSupport.getAllowedEventDefinitions(event);
				for (EClass eclass : items) {
					if (allowedItems.contains(eclass))
						filteredItems.add(eclass);
				}
				items.clear();
				items.addAll(filteredItems);
			}
		};
		
		if (dialog.open()==Window.OK){
			eclass = (EClass)dialog.getResult()[0];
		}
		return eclass;
	}
}
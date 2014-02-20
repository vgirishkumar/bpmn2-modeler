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
package org.eclipse.bpmn2.modeler.core;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Composite;

/**
 * A simple interface that defines a provider for an EditControl.
 * An EditControl is an SWT Composite that can be embedded in a Viewer or Dialog,
 * just like any other Composite.
 * 
 * EditControl must implement setValue() and getValue() methods which are used to
 * initialize the EditControl's widget with data, and fetch data from the widget.
 * 
 * Listeners are used to notify the client when the widget's data has changed, therefore
 * the implementation must add "this" as a widget Selection Listener in the createControl()
 * method.
 */
public interface EditControlProvider {
	
	public abstract class EditControl extends Composite implements SelectionListener {
	    protected List<SelectionListener> listeners;
		public EditControl(Composite parent, int style) {
			super(parent, style);
		}
		public abstract Object getValue();
		public abstract boolean setValue(Object value);
	    
	    public void addSelectionListener(SelectionListener listener) {
	    	if (listeners==null)
	    		listeners = new ArrayList<SelectionListener>();
	    	listeners.add(listener);
	    }
	    
	    public void removeSelectionListener(SelectionListener listener) {
	    	if (listeners==null)
	    		return;
	    	listeners.remove(listener);
	    	if (listeners.size()==0)
	    		listeners = null;
	    }
	    
		@Override
		public void widgetSelected(SelectionEvent e) {
			if (listeners!=null) {
				for (SelectionListener listener : listeners)
					listener.widgetSelected(e);
			}
		}

		@Override
		public void widgetDefaultSelected(SelectionEvent e) {
		}
	}
	
	public EditControl createControl(Composite parent, int style);
}

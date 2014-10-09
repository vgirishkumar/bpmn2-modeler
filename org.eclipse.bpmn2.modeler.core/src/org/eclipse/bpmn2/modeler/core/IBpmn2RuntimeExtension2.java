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

/*
 * Bridge between kepler and luna versions of the editor. This interface is used only
 * for "Business Object Created" LifecycleEvent notifications to allow the jBPM5 plugin
 * to attach an adapter early during the object's creation.
 * 
 * No other LifecycleEvents are supported at this time.
 */
public interface IBpmn2RuntimeExtension2 {

	/**
	 * Used to notify the Target Runtime of BPMN2 editor lifecycle events.
	 *
	 * @param event
	 *            an event object sent by the BPMN2 editor framework.
	 */
	public void notify(LifecycleEvent event);
}

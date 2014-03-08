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

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;

/**
 * BPMN2 Editor Lifecycle Event object. These events are sent to the Target Runtime Extension
 * implementation class during the life of the editor.
 * 
 * Event notifications include Editor Events, Business Object Events, Graphiti PictogramElement
 * Events, Editing Domain Transaction Events and Command Stack Events.
 * Additional information will be available, depending on the event type, as follows:
 * 
 *  Editor Events:
 *  	target = Graphiti DiagramEditor
 *  Business Object Events:
 *  	target = EObject
 *  Pictogram Element Events:
 *  	target = the PictogramElement
 *  	featureProvider = the Graphiti Feature Provider
 *  	context = the Graphiti operation context. Depending on the event type,
 *  		this will be an IAddContext, IUpdateContext, IDeleteContext, etc.
 *  	doit = used by all of the PICTOGRAMELEMENT_CAN_ events. If the client sets
 *  		this to false, the Pictogram Element Event will be cancelled.
 *  Transaction Events:
 *  	target = an EMF model Transaction
 *  Command Stack Events:
 *  	target = an EMF Command Operation
 */
public class LifecycleEvent {

	public enum EventType {
		//  Editor Events:
		EDITOR_STARTUP,
		EDITOR_INITIALIZED,
		EDITOR_SHUTDOWN,
		// Business Object Events:
		BUSINESSOBJECT_CREATED,
		BUSINESSOBJECT_DELETED,
		// Pictogram Element Events:
		PICTOGRAMELEMENT_CAN_ADD,
		PICTOGRAMELEMENT_ADDED,
		PICTOGRAMELEMENT_UPDATE_NEEDED,
		PICTOGRAMELEMENT_CAN_UPDATE,
		PICTOGRAMELEMENT_UPDATE,
		PICTOGRAMELEMENT_CAN_LAYOUT,
		PICTOGRAMELEMENT_LAYOUT,
		PICTOGRAMELEMENT_CAN_MOVE,
		PICTOGRAMELEMENT_PRE_MOVE,
		PICTOGRAMELEMENT_POST_MOVE,
		PICTOGRAMELEMENT_CAN_RESIZE,
		PICTOGRAMELEMENT_PRE_RESIZE,
		PICTOGRAMELEMENT_POST_RESIZE,
		PICTOGRAMELEMENT_CAN_DELETE,
		PICTOGRAMELEMENT_DELETED,
		// Transaction Events:
		TRANSACTION_STARTING,
		TRANSACTION_INTERRUPTED,
		TRANSACTION_CLOSED,
		// Command Stack Events:
		COMMAND_UNDO,
		COMMAND_REDO,
	};
	
	public EventType eventType;
	public Object target;
	public IContext context;
	public IFeatureProvider featureProvider;
	public boolean doit = true;
	
	public LifecycleEvent(EventType eventType, Object target) {
		this.eventType = eventType;
		this.target = target;
	}
	
	public LifecycleEvent(EventType eventType, IFeatureProvider featureProvider, IContext context, Object target) {
		this.eventType = eventType;
		this.featureProvider = featureProvider;
		this.context = context;
		this.target = target;
	}
}

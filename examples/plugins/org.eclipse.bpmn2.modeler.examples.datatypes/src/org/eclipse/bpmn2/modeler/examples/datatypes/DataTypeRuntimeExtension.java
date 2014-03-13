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

package org.eclipse.bpmn2.modeler.examples.datatypes;

import org.eclipse.bpmn2.modeler.core.LifecycleEvent;
import org.eclipse.bpmn2.modeler.core.LifecycleEvent.EventType;
import org.eclipse.bpmn2.modeler.ui.DefaultBpmn2RuntimeExtension;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;

/**
 *
 */
public class DataTypeRuntimeExtension extends DefaultBpmn2RuntimeExtension {

	@Override
	public void notify(LifecycleEvent event) {
		if (event.eventType.equals(EventType.PICTOGRAMELEMENT_ADDED) && event.target instanceof ContainerShape) {
			ColorChangeAdapter.adapt((ContainerShape) event.target);
		}
	}
}

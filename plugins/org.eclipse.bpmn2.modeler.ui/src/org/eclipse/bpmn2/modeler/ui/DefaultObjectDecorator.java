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

package org.eclipse.bpmn2.modeler.ui;

import org.eclipse.bpmn2.modeler.core.runtime.IObjectDecorator;
import org.eclipse.bpmn2.modeler.ui.diagram.Bpmn2FeatureMap;
import org.eclipse.emf.ecore.EObject;

/**
 *
 */
public class DefaultObjectDecorator implements IObjectDecorator {

	/**
	 * 
	 */
	public DefaultObjectDecorator() {
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.runtime.IObjectDecorator#canApplyTo(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public boolean canApplyTo(EObject object) {
		return Bpmn2FeatureMap.ALL_SHAPES.contains(object.eClass().getInstanceClass());
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.runtime.IObjectDecorator#applyTo(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	public boolean applyTo(EObject object) {
		return false;
	}

}

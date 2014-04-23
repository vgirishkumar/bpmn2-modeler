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

package org.eclipse.bpmn2.modeler.core.features;

import org.eclipse.bpmn2.modeler.core.di.DIImport;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.impl.AbstractUpdateFeature;
import org.eclipse.graphiti.features.impl.Reason;

/**
 *
 */
public abstract class AbstractBpmn2UpdateFeature extends AbstractUpdateFeature {

	/**
	 * @param fp
	 */
	public AbstractBpmn2UpdateFeature(IFeatureProvider fp) {
		super(fp);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.func.IUpdate#updateNeeded(org.eclipse.graphiti.features.context.IUpdateContext)
	 */
	@Override
	public IReason updateNeeded(IUpdateContext context) {
		if (context.getProperty(ContextConstants.BUSINESS_OBJECT) instanceof EObject) {
			// if the UpdateContext has a "businessObject" property, then this update is needed
			// as part of the the CreateFeature ("businessObject" is only set in the CreateFeature)
			return Reason.createTrueReason("Initial update"); //$NON-NLS-1$
		}
		if (context.getProperty(DIImport.IMPORT_PROPERTY) != null) {
			return Reason.createTrueReason("Initial update"); //$NON-NLS-1$
		}
		return Reason.createFalseReason();
	}
}

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

package org.eclipse.bpmn2.modeler.core.validation.validators;

import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.Import;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;

/**
 *
 */
public class DefinitionsValidator extends AbstractBpmn2ElementValidator<Definitions> {

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.validation.validators.AbstractBpmn2ElementValidator#validate(org.eclipse.bpmn2.BaseElement)
	 */
	@Override
	public IStatus validate(Definitions object) {
		if (object.getTargetNamespace()==null || object.getTargetNamespace().isEmpty()) {
			addStatus(object, Status.ERROR, "No targetNamespace defined");
		}
		for (Import elem : object.getImports()) {
			if (isEmpty(elem.getLocation())) {
				addMissingFeatureStatus(elem,"location",Status.ERROR); //$NON-NLS-1$
			}
			if (isEmpty(elem.getNamespace())) {
				addMissingFeatureStatus(elem,"namespace",Status.ERROR); //$NON-NLS-1$
			}
			if (isEmpty(elem.getImportType())) {
				addMissingFeatureStatus(elem,"importType",Status.ERROR); //$NON-NLS-1$
			}
		}
		
		return getResult();
	}

}

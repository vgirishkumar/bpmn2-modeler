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

import java.util.HashSet;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.core.validation.SyntaxCheckerUtils;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.IValidationContext;

/**
 *
 */
public class BaseElementValidator extends AbstractBpmn2ElementValidator<BaseElement> {

	protected static HashSet<EClass> idOptional = new HashSet<EClass>();
	static {
		idOptional.add(Bpmn2Package.eINSTANCE.getDefinitions());
		// TODO: are there other BPMN2 object types whose IDs are optional?
	}

	/**
	 * @param ctx
	 */
	public BaseElementValidator(IValidationContext ctx) {
		super(ctx);
	}

	/**
	 * @param other
	 */
	@SuppressWarnings("rawtypes")
	public BaseElementValidator(AbstractBpmn2ElementValidator other) {
		super(other);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.validation.validators.AbstractBpmn2ElementValidator#validate(org.eclipse.bpmn2.BaseElement)
	 */
	@Override
	public IStatus validate(BaseElement object) {
		if (!idOptional.contains(object.eClass())) {
			// In the case of batch mode.
			String id = object.getId();
			if (isEmpty(id)) {
				addStatus(object, "id", Status.ERROR, "The {0} ID can not be empty", object.eClass().getName());
			}
			else if (!SyntaxCheckerUtils.isNCName(id)) {
				addStatus(object, "id", Status.ERROR, "The {0} ID is invalid: {1}", object.eClass().getName(), id);
			}
			
			Definitions definitions = ModelUtil.getDefinitions(object);
			TreeIterator<EObject> iter = definitions.eAllContents();
			while (iter.hasNext()) {
				EObject o2 = iter.next();
				if (o2 instanceof BaseElement && object!=o2) {
					String id2;
					id2 = ((BaseElement)o2).getId();
					if (id!=null && id2!=null) {
						if (id.equals(id2)) {
							addStatus(object, Status.ERROR,
								"{0} and {1} have the same ID",
								getLabel(object)+" "+getName(object), //$NON-NLS-1$
								getLabel(o2)+" "+getName(o2) //$NON-NLS-1$
							);
						}
					}
				}
			}

		}
		
		return getResult();
	}
	
	public boolean doLiveValidation() {
		return true;
	}

}

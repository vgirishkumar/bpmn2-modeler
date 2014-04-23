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
 * @author Ivar Meikas
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.core.features.activity;

import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.modeler.core.features.AbstractBpmn2UpdateFeature;
import org.eclipse.bpmn2.modeler.core.features.ContextConstants;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IPeService;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractUpdateMarkerFeature.
 *
 * @param <T> the generic type
 */
public abstract class AbstractUpdateMarkerFeature<T extends FlowElement> extends AbstractBpmn2UpdateFeature {

	/**
	 * Instantiates a new abstract update marker feature.
	 *
	 * @param fp the fp
	 */
	public AbstractUpdateMarkerFeature(IFeatureProvider fp) {
	    super(fp);
    }

	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.func.IUpdate#canUpdate(org.eclipse.graphiti.features.context.IUpdateContext)
	 */
	@Override
    public boolean canUpdate(IUpdateContext context) {
		Object bo = getBusinessObjectForPictogramElement(context.getPictogramElement());
		return bo != null && bo instanceof Activity && context.getPictogramElement() instanceof ContainerShape;
    }

	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.func.IUpdate#updateNeeded(org.eclipse.graphiti.features.context.IUpdateContext)
	 */
	@Override
    public IReason updateNeeded(IUpdateContext context) {
		IReason reason = super.updateNeeded(context);
		if (reason.toBoolean())
			return reason;

		IPeService peService = Graphiti.getPeService();
		PictogramElement element = context.getPictogramElement();
		String property = peService.getPropertyValue(element, getPropertyKey());
		if(property == null) {
			return Reason.createFalseReason();
		}
		T activity = (T) getBusinessObjectForPictogramElement(context.getPictogramElement());
		boolean changed = isPropertyChanged(activity, property);
		return changed ? Reason.createTrueReason("Marker changed") : Reason.createFalseReason(); //$NON-NLS-1$
    }

	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.func.IUpdate#update(org.eclipse.graphiti.features.context.IUpdateContext)
	 */
	@Override
    public boolean update(IUpdateContext context) {
		IPeService peService = Graphiti.getPeService();
		ContainerShape container = (ContainerShape) context.getPictogramElement();
		T element = (T) getBusinessObjectForPictogramElement(context.getPictogramElement());

		doUpdate(element, container);
		peService.setPropertyValue(container, getPropertyKey(), convertPropertyToString(element));
		return true;
    }
	
	/**
	 * Gets the property key.
	 *
	 * @return the property key
	 */
	protected abstract String getPropertyKey();
	
	/**
	 * Checks if is property changed.
	 *
	 * @param element the element
	 * @param propertyValue the property value
	 * @return true, if is property changed
	 */
	protected abstract boolean isPropertyChanged(T element, String propertyValue);

	/**
	 * Do update.
	 *
	 * @param element the element
	 * @param markerContainer the marker container
	 */
	protected abstract void doUpdate(T element, ContainerShape markerContainer);
	
	/**
	 * Convert property to string.
	 *
	 * @param element the element
	 * @return the string
	 */
	protected abstract String convertPropertyToString(T element);
}

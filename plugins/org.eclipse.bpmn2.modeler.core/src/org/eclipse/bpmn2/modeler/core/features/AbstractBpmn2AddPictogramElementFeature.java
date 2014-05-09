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

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.modeler.core.utils.StyleUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.impl.LayoutContext;
import org.eclipse.graphiti.features.context.impl.UpdateContext;
import org.eclipse.graphiti.features.impl.AbstractAddPictogramElementFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

/**
 *
 */
public abstract class AbstractBpmn2AddPictogramElementFeature extends AbstractAddPictogramElementFeature {

	/**
	 * @param fp
	 */
	public AbstractBpmn2AddPictogramElementFeature(IFeatureProvider fp) {
		super(fp);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.func.IAdd#canAdd(org.eclipse.graphiti.features.context.IAddContext)
	 */
	@Override
	public boolean canAdd(IAddContext context) {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.func.IAdd#add(org.eclipse.graphiti.features.context.IAddContext)
	 */
	@Override
	public PictogramElement add(IAddContext context) {
		return null;
	}
	/**
	 * Update the given PictogramElement. A Graphiti UpdateContext is constructed by copying
	 * the properties from the given AddContext.
	 * 
	 * @param addContext the Graphiti AddContext that was used to add the PE to the Diagram
	 * @param pe the PictogramElement
	 * @return a reason code indicating whether or not an update is needed.
	 */
	protected IReason updatePictogramElement(IAddContext addContext, PictogramElement pe) {
		UpdateContext updateContext = new UpdateContext(pe);
		for (Object key : addContext.getPropertyKeys()) {
			Object value = addContext.getProperty(key);
			updateContext.putProperty(key, value);
		}
		return getFeatureProvider().updateIfPossible(updateContext);
	}

	/**
	 * Layout the given PictogramElement. A Graphiti LayoutContext is constructed by copying
	 * the properties from the given AddContext.
	 * 
	 * @param addContext the Graphiti AddContext that was used to add the PE to the Diagram
	 * @param pe the PictogramElement
	 * @return a reason code indicating whether or not a layout is needed.
	 */
	protected IReason layoutPictogramElement(IAddContext addContext, PictogramElement pe) {
		LayoutContext layoutContext = new LayoutContext(pe);
		for (Object key : addContext.getPropertyKeys()) {
			Object value = addContext.getProperty(key);
			layoutContext.putProperty(key, value);
		}
		return getFeatureProvider().layoutIfPossible(layoutContext);
	}
}

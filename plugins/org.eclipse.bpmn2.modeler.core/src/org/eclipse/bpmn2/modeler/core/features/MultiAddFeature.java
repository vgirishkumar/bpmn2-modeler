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
package org.eclipse.bpmn2.modeler.core.features;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.features.context.impl.UpdateContext;
import org.eclipse.graphiti.features.impl.AbstractAddFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;

// TODO: Auto-generated Javadoc
/**
 * The Class MultIAddFeature.
 */
public class MultiAddFeature extends AbstractAddFeature {

	/** The list of features that will be invoked. */
	protected List<IAddFeature> features = new ArrayList<IAddFeature>();

	/**
	 * Instantiates a new multi add feature.
	 *
	 * @param fp the Feature Provider
	 */
	public MultiAddFeature(IFeatureProvider fp) {
		super(fp);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.func.IAdd#canAdd(org.eclipse.graphiti.features.context.IAddContext)
	 */
	@Override
	public boolean canAdd(IAddContext context) {
		for (IAddFeature p : features) {
			if (!p.canAdd(context)) {
				return false;
			}
		}
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.func.IAdd#add(org.eclipse.graphiti.features.context.IAddContext)
	 */
	@Override
	public PictogramElement add(IAddContext context) {
		PictogramElement parentPE = null;
		List<PictogramElement> pes = new ArrayList<PictogramElement>();

		context.putProperty(GraphitiConstants.PICTOGRAM_ELEMENTS, pes);
		for (IAddFeature p : features) {
			PictogramElement pe = p.add(context);
			pes.add(pe);
			if (parentPE==null)
				parentPE = pe;
		}

		UpdateContext updateContext = new UpdateContext(parentPE);
		for (Object key : context.getPropertyKeys()) {
			Object value = context.getProperty(key);
			updateContext.putProperty(key, value);
		}
		if (getFeatureProvider().updateNeeded(updateContext).toBoolean())
			getFeatureProvider().updateIfPossible(updateContext);

		layoutPictogramElement(parentPE);

		return parentPE;
	}

	/**
	 * Adds the Add Feature to our list of features.
	 *
	 * @param feature the feature
	 */
	public void addFeature(IAddFeature feature) {
		if (feature != null) {
			features.add(feature);
		}
	}
	
	/**
	 * Get the list of individual Add Features that will be evaluated.
	 * 
	 * @return
	 */
	public List<IAddFeature> getFeatures() {
		return features;
	}
}
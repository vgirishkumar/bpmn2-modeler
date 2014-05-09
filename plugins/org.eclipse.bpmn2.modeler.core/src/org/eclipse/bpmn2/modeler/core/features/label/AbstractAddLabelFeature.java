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

package org.eclipse.bpmn2.modeler.core.features.label;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.modeler.core.features.AbstractBpmn2AddPictogramElementFeature;
import org.eclipse.bpmn2.modeler.core.features.GraphitiConstants;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.core.utils.StyleUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.MultiText;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeService;

/**
 *
 */
abstract public class AbstractAddLabelFeature extends AbstractBpmn2AddPictogramElementFeature {

	protected final IGaService gaService = Graphiti.getGaService();
	protected final IPeService peService = Graphiti.getPeService();

	/**
	 * @param fp
	 */
	public AbstractAddLabelFeature(IFeatureProvider fp) {
		super(fp);
	}
	
	@Override
	public boolean canAdd(IAddContext context) {
		return true;
	}

	abstract public PictogramElement add(IAddContext context);

	protected MultiText createText(PictogramElement labelOwner, Shape labelShape, BaseElement businessObject) {
		MultiText text = gaService.createDefaultMultiText(getDiagram(), labelShape, getLabelString(businessObject));
		applyStyle(text, businessObject);
		peService.setPropertyValue(labelShape, GraphitiConstants.LABEL_SHAPE, Boolean.toString(true));
		
		link(labelShape, new Object[] {businessObject, labelOwner});
		link(labelOwner, new Object[] {labelShape});
		
		return text;
	}
	
	public String getLabelString(BaseElement element) {
		return ModelUtil.getName(element);
	}

	public void applyStyle(GraphicsAlgorithm ga, BaseElement be) {
		StyleUtil.applyStyle(ga, be);
	}
}

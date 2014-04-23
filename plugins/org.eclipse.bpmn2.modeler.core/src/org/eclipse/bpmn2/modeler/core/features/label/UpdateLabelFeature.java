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
package org.eclipse.bpmn2.modeler.core.features.label;

import static org.eclipse.bpmn2.modeler.core.utils.FeatureSupport.getChildElementOfType;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.DataState;
import org.eclipse.bpmn2.ItemAwareElement;
import org.eclipse.bpmn2.modeler.core.features.AbstractBpmn2UpdateFeature;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.AbstractText;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;

public class UpdateLabelFeature extends AbstractBpmn2UpdateFeature {

	public static final String TEXT_ELEMENT = "baseelement.text"; //$NON-NLS-1$

	public UpdateLabelFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canUpdate(IUpdateContext context) {
		BaseElement element = (BaseElement) BusinessObjectUtil.getFirstElementOfType(context.getPictogramElement(),
		        BaseElement.class);
		if (element == null) {
			return false;
		}
		return hasLabel(element);
	}

	@Override
	public IReason updateNeeded(IUpdateContext context) {
		IReason reason = super.updateNeeded(context);
		if (reason.toBoolean())
			return reason;

		PictogramElement container = context.getPictogramElement();

		BaseElement element = (BaseElement) BusinessObjectUtil.getFirstElementOfType(container,
		        BaseElement.class);

		Shape textShape = getChildElementOfType(container, TEXT_ELEMENT, Boolean.toString(true), Shape.class);
		if (textShape!=null) {
			String oldLabel = getLabel(element);
			if (oldLabel==null || oldLabel.isEmpty())
				oldLabel = ""; //$NON-NLS-1$
			String newLabel = ""; //$NON-NLS-1$
			if (textShape.getGraphicsAlgorithm() instanceof AbstractText) {
				AbstractText text = (AbstractText) textShape.getGraphicsAlgorithm();
				newLabel = text.getValue();
			}
			if (newLabel==null || newLabel.isEmpty())
				newLabel = ""; //$NON-NLS-1$
			
			if (!oldLabel.equals(newLabel))
				return Reason.createTrueReason(Messages.UpdateLabelFeature_Label); 
		}
		return Reason.createFalseReason();
	}

	@Override
	public boolean update(IUpdateContext context) {
		IGaService gaService = Graphiti.getGaService();
		PictogramElement pe = (PictogramElement) context.getPictogramElement();
		BaseElement element = (BaseElement) BusinessObjectUtil.getFirstElementOfType(context.getPictogramElement(),
		        BaseElement.class);
		Shape textShape = getChildElementOfType(pe, TEXT_ELEMENT, Boolean.toString(true), Shape.class);
		if (textShape!=null) {
			AbstractText textGA = (AbstractText) textShape.getGraphicsAlgorithm();
			String label = getLabel(element);
			if (label == null) {
				label = ""; //$NON-NLS-1$
			}
			textGA.setValue(label);
			
			ContainerShape containerShape = (ContainerShape)textShape.eContainer();
			GraphicsAlgorithm containerGA = containerShape.getGraphicsAlgorithm();
			
			int oldWidth = containerGA.getWidth() - GraphicsUtil.SHAPE_PADDING;
			int x = containerGA.getX() + ((oldWidth + GraphicsUtil.SHAPE_PADDING) / 2);
			int y = containerGA.getY();
			
			if (label.isEmpty()) {
				gaService.setLocationAndSize(containerGA, x, y, 0, 0);
				gaService.setLocationAndSize(textGA, 0, 0, 0, 0);
				containerShape.setVisible(false);
			} else {
				int newWidth = GraphicsUtil.getLabelWidth(textGA);
				int newHeight = GraphicsUtil.getLabelHeight(textGA);
				x = x - ((newWidth + GraphicsUtil.SHAPE_PADDING) / 2);
				gaService.setLocationAndSize(containerGA, x, y, newWidth + GraphicsUtil.SHAPE_PADDING, newHeight + GraphicsUtil.SHAPE_PADDING);
				gaService.setLocationAndSize(textGA, 0, 0, newWidth + GraphicsUtil.TEXT_PADDING, newHeight + GraphicsUtil.TEXT_PADDING);
				containerShape.setVisible(true);
			}
		}
		
		return true;
	}
	
	protected boolean hasLabel(BaseElement element) {
		return  ModelUtil.hasName(element);
	}
	
	protected String getLabel(BaseElement element) {
		// Unfortunately this needs to be aware of ItemAwareElements, which have a
		// Data State (the Data State needs to appear below the element's label in [])
		// The UpdateLabelFeature is checked in BPMN2FeatureProvider AFTER the Update
		// Feature for Data Objects is executed - this wipes out the Label provided by
		// ItemAwareElementUpdateFeature.
		String label = ModelUtil.getName(element);
		if (element instanceof ItemAwareElement) {
			DataState state = ((ItemAwareElement)element).getDataState();
			if (state!=null && state.getName()!=null) {
				return label + "\n[" + state.getName() + "]";
			}
		}
		return label;
	}
}
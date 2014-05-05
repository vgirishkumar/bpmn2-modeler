/*******************************************************************************
 * Copyright (c) 2011, 2012 Red Hat, Inc. 
 * All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 *
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 *******************************************************************************/
package org.eclipse.bpmn2.modeler.core.features.label;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.BoundaryEvent;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesProvider;
import org.eclipse.bpmn2.modeler.core.di.DIImport;
import org.eclipse.bpmn2.modeler.core.features.AbstractBpmn2AddPictogramElementFeature;
import org.eclipse.bpmn2.modeler.core.features.GraphitiConstants;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.StyleUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddShapeFeature;
import org.eclipse.graphiti.mm.algorithms.MultiText;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeService;

public class AddLabelFeature extends AbstractBpmn2AddPictogramElementFeature {

	public AddLabelFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canAdd(IAddContext context) {
		return FeatureSupport.isValidFlowElementTarget(context);
	}

	@Override
	public PictogramElement add(IAddContext context) {
		IGaService gaService = Graphiti.getGaService();
		IPeService peService = Graphiti.getPeService();
		
		boolean isImporting = DIImport.isImporting(context);
		int width = (Integer) context.getProperty(GraphitiConstants.WIDTH);
		int height = (Integer) context.getProperty(GraphitiConstants.HEIGHT);
		
		int x = context.getX();
		int y = context.getY();
		
		BaseElement baseElement = (BaseElement) context.getProperty(GraphitiConstants.BUSINESS_OBJECT);
		
		ContainerShape targetContainer = getTargetContainer(context);
		PictogramElement labelOwner = LabelFeatureContainer.getLabelOwner(context);
		final ContainerShape textContainerShape = peService.createContainerShape(targetContainer, true);
		gaService.createInvisibleRectangle(textContainerShape);
		
		Shape textShape = peService.createShape(textContainerShape, false);
		peService.setPropertyValue(textShape, GraphitiConstants.TEXT_ELEMENT, Boolean.toString(true));
		String name = ExtendedPropertiesProvider.getTextValue(baseElement);
		MultiText text = gaService.createDefaultMultiText(getDiagram(), textShape, name);
		StyleUtil.applyStyle(text, baseElement);
		text.setHorizontalAlignment(Orientation.ALIGNMENT_CENTER);
		text.setVerticalAlignment(Orientation.ALIGNMENT_TOP);
		link(textShape, baseElement);
		
		// Boundary events get a different add context, so use the context coordinates relative
		if ( (baseElement instanceof BoundaryEvent) && !isImporting ){
			x = context.getTargetContainer().getGraphicsAlgorithm().getX() + context.getX()-width/2;
			y = context.getTargetContainer().getGraphicsAlgorithm().getY() + context.getY()-height/2;
		}
		
//		GraphicsUtil.alignWithShape(targetContainer);//text, textContainerShape, width, height, x, y, 0, 0);
		
		// if this label has an owner, link the two to each other
		if (labelOwner!=null) {
			link(labelOwner, new Object[] {textContainerShape});
			link(textContainerShape, new Object[] {baseElement, labelOwner});
		}
		else {
			link(textContainerShape, baseElement);
		}
		
		Graphiti.getPeService().setPropertyValue(textContainerShape, GraphitiConstants.LABEL_PROPERTY, "true"); //$NON-NLS-1$
		
		updatePictogramElement(context, textContainerShape);
		layoutPictogramElement(context, textContainerShape);
		
		LabelFeatureContainer.adjustLabelLocation(labelOwner, isImporting,
				GraphicsUtil.createPoint(context.getX(), context.getY()));

		return textContainerShape;
	}
	
	/**
	 * Get the correct target control, boundary events need special handling, because we need to find a parent,
	 * where the label is visible.
	 * 
	 * @param context
	 * @return the target control for the current context
	 */
	ContainerShape getTargetContainer(IAddContext context) {
		boolean isBoundary = context.getProperty(GraphitiConstants.BUSINESS_OBJECT) instanceof BoundaryEvent;
		
		if ( isBoundary && !DIImport.isImporting(context) ){
			if (context.getTargetContainer()!=null){
				return context.getTargetContainer().getContainer();
			}
		}
		return context.getTargetContainer();
	}

}

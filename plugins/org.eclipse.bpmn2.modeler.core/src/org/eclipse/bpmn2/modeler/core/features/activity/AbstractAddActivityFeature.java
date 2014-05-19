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
import org.eclipse.bpmn2.modeler.core.features.AbstractBpmn2AddFeature;
import org.eclipse.bpmn2.modeler.core.features.GraphitiConstants;
import org.eclipse.bpmn2.modeler.core.features.IFeatureContainer;
import org.eclipse.bpmn2.modeler.core.features.activity.UpdateActivityLoopAndMultiInstanceMarkerFeature.LoopCharacteristicType;
import org.eclipse.bpmn2.modeler.core.features.label.AddShapeLabelFeature;
import org.eclipse.bpmn2.modeler.core.features.label.LabelFeatureContainer;
import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.core.utils.StyleUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeService;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractAddActivityFeature.
 *
 * @param <T> the generic type
 */
public abstract class AbstractAddActivityFeature<T extends Activity>
	extends AbstractBpmn2AddFeature<T> {

	/** The ga service. */
	protected final IGaService gaService = Graphiti.getGaService();
	
	/** The pe service. */
	protected final IPeService peService = Graphiti.getPeService();

	/** The preferences. */
	protected Bpmn2Preferences preferences;
	
	/**
	 * Instantiates a new abstract add activity feature.
	 *
	 * @param fp the fp
	 */
	public AbstractAddActivityFeature(IFeatureProvider fp) {
		super(fp);
	}

	public IAddFeature getAddLabelFeature(IFeatureProvider fp) {
		return new AddShapeLabelFeature(fp);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.func.IAdd#canAdd(org.eclipse.graphiti.features.context.IAddContext)
	 */
	@Override
	public boolean canAdd(IAddContext context) {
		/*
		 * TODO: rethink this: it's causing all kinds of DI import problems
		 * also see AbstractCreateFlowElementFeature
		 * 
		if (intoParticipant) {
			// don't allow flow elements to be added to a Pool if it is a "whitebox"
			// (i.e. if it has its own BPMNDiagram page.) Flow elements should be added
			// to the Process page instead.
			Participant participant = FeatureSupport.getTargetParticipant(context);
			if (FeatureSupport.hasBpmnDiagram(participant))
				return false;
		}
		else if (intoFlowElementContainer) {
			FlowElementsContainer flowElementsContainer = FeatureSupport.getTargetFlowElementsContainer(context);
			if (FeatureSupport.hasBpmnDiagram(flowElementsContainer))
				return false;
		}
		*/
		return FeatureSupport.isValidFlowElementTarget(context);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.func.IAdd#add(org.eclipse.graphiti.features.context.IAddContext)
	 */
	@Override
	public PictogramElement add(IAddContext context) {
		preferences = Bpmn2Preferences.getInstance((EObject)context.getNewObject());

		T businessObject = getBusinessObject(context);

		int width = context.getWidth() > 0 ? context.getWidth() : this.getWidth();
		int height = context.getHeight() > 0 ? context.getHeight() : this.getHeight();
		
		GraphicsAlgorithm targetAlgorithm = context.getTargetContainer().getGraphicsAlgorithm();
		
		if (targetAlgorithm != null) {
			width = Math.min(targetAlgorithm.getWidth(), width);
			height = Math.min(targetAlgorithm.getHeight(), height);
		}
		
		adjustLocation(context,width,height);
		
		int x = context.getX();
		int y = context.getY();

		ContainerShape containerShape = peService.createContainerShape(context.getTargetContainer(), true);
		Rectangle invisibleRect = gaService.createInvisibleRectangle(containerShape);
		gaService.setLocationAndSize(invisibleRect, x, y, width, height);

		Shape rectShape = peService.createShape(containerShape, false);
		RoundedRectangle rect = gaService.createRoundedRectangle(rectShape, 5, 5);
		StyleUtil.applyStyle(rect, businessObject);
		gaService.setLocationAndSize(rect, 0, 0, width, height);
		link(rectShape, businessObject);
		
		boolean isImport = context.getProperty(GraphitiConstants.IMPORT_PROPERTY) != null;
		createDIShape(containerShape, businessObject, !isImport);

		Graphiti.getPeService().setPropertyValue(containerShape, GraphitiConstants.IS_COMPENSATE_PROPERTY, Boolean.toString(false));
		Graphiti.getPeService().setPropertyValue(containerShape, GraphitiConstants.IS_LOOP_OR_MULTI_INSTANCE, LoopCharacteristicType.NULL.getName());

		// set a property on the decorators so we can distinguish them from the real children (i.e. tasks, etc.)
		for (PictogramElement pe : containerShape.getChildren()) {
			Graphiti.getPeService().setPropertyValue(pe, GraphitiConstants.ACTIVITY_DECORATOR, "true"); //$NON-NLS-1$
		}

		// hook for subclasses to inject extra code
		((AddContext)context).setWidth(width);
		((AddContext)context).setHeight(height);
		decorateShape(context, containerShape, businessObject);
		
		peService.createChopboxAnchor(containerShape);
		AnchorUtil.addFixedPointAnchors(containerShape, rect);

		// set a property on the decorators so we can distinguish them from the real children (i.e. tasks, etc.)
		for (PictogramElement pe : containerShape.getChildren()) {
			Graphiti.getPeService().setPropertyValue(pe, GraphitiConstants.ACTIVITY_DECORATOR, "true"); //$NON-NLS-1$
		}
		splitConnection(context, containerShape);
		
		return containerShape;
	}

	/**
	 * Gets the marker container offset.
	 *
	 * @return the marker container offset
	 */
	protected int getMarkerContainerOffset() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.features.AbstractBpmn2AddFeature#getWidth()
	 */
	public abstract int getWidth();

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.features.AbstractBpmn2AddFeature#getHeight()
	 */
	public abstract int getHeight();
}
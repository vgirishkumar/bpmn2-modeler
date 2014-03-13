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
package org.eclipse.bpmn2.modeler.core.features;

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.impl.AbstractLayoutFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

// TODO: Auto-generated Javadoc
/**
 * The Class DefaultLayoutBPMNShapeFeature.
 */
public class DefaultLayoutBPMNShapeFeature extends AbstractLayoutFeature {

	/**
	 * Instantiates a new default layout bpmn shape feature.
	 *
	 * @param fp the fp
	 */
	public DefaultLayoutBPMNShapeFeature(IFeatureProvider fp) {
		super(fp);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.func.ILayout#canLayout(org.eclipse.graphiti.features.context.ILayoutContext)
	 */
	@Override
	public boolean canLayout(ILayoutContext context) {
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.func.ILayout#layout(org.eclipse.graphiti.features.context.ILayoutContext)
	 */
	@Override
	public boolean layout(ILayoutContext context) {
		layoutConnections(context.getPictogramElement());
		return true;
	}
	
	/**
	 * Layout connections.
	 *
	 * @param shape the shape
	 */
	public void layoutConnections(PictogramElement shape) {
//		Diagram diagram = getDiagram();
//		if (diagram!=null) {
//			if (shape.getLink()!=null) {
//				for (Object object : shape.getLink().getBusinessObjects()) {
//					if (object instanceof BPMNShape || object instanceof BPMNEdge) {
//						AnchorUtil.reConnect((DiagramElement) object, diagram);
//					}
//				}
//			}
//			
//			if (shape instanceof ContainerShape) {
//				for (PictogramElement pe : FeatureSupport.getContainerChildren((ContainerShape)shape)) {
//					if (pe.getLink()!=null) {
//						for (Object object : pe.getLink().getBusinessObjects()) {
//							if (object instanceof BPMNShape || object instanceof BPMNEdge) {
//								AnchorUtil.reConnect((DiagramElement) object, diagram);
//							}
//						}
//					}
//				}
//			}
//		}
	}
}

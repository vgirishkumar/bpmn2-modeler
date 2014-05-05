/*******************************************************************************
 * Copyright (c) 2011, 2012, 2013 Red Hat, Inc.
 * All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * 	Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.core.features;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.impl.AbstractAddPictogramElementFeature;
import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Shape;

/**
 * This is the Graphiti AddFeature base class for all BPMN2 model objects.
 * <p>
 * The main purpose of this class is to provide a common method for locating the
 * {@link GraphicsAlgorithm} for a graphical element (a shape on the diagram).
 * The {@code GraphicsAlgorithm} is the Graphiti object that is actually visible
 * on the diagram. Colors, gradients, shading, fonts and line drawing styles can
 * be applied to this {@code GraphicsAlgorithm} to change the graphical
 * element's appearance.
 *
 * @param <T> the generic type, a subclass of {@link BaseElement}
 */
public abstract class AbstractBpmn2AddFeature<T extends BaseElement>
	extends AbstractBpmn2AddPictogramElementFeature
	implements IBpmn2AddFeature<T> {
	
	/**
	 * Instantiates a new AddFeature.
	 *
	 * @param fp the fp
	 */
	public AbstractBpmn2AddFeature(IFeatureProvider fp) {
		super(fp);
	}

	/**
	 * Helper function to return the GraphicsAlgorithm for a ContainerShape created by
	 * one of the BPMN2 Modeler's Add features. This can be used by subclasses to decorate
	 * the figure on the diagram.
	 *
	 * @param containerShape the container shape
	 * @return the graphics algorithm
	 */
	protected static GraphicsAlgorithmContainer getGraphicsAlgorithm(ContainerShape containerShape) {
		if (containerShape.getGraphicsAlgorithm() instanceof RoundedRectangle)
			return containerShape.getGraphicsAlgorithm();
		if (containerShape.getChildren().size()>0) {
			Shape shape = containerShape.getChildren().get(0);
			return shape.getGraphicsAlgorithm();
		}
		return null;
	}
	
	/**
	 * Decorate connection. This is a placeholder for the hook function invoked
	 * when the connection is added to the diagram. Implementations can override
	 * this to change the appearance of the connection.
	 *
	 * @param context the Add Context
	 * @param connection the connection being added
	 * @param businessObject the business object, a {@code BaseElement} subclass.
	 */
	protected void decorateConnection(IAddConnectionContext context, Connection connection, T businessObject) {
	}

	/**
	 * Decorate shape. This is a placeholder for the hook function invoked when
	 * the shape is added to the diagram. Implementations can override this to
	 * change the appearance of the shape.
	 *
	 * @param context the Add Context
	 * @param containerShape the container shape being added
	 * @param businessObject the business object, a {@code BaseElement} subclass.
	 */
	protected void decorateShape(IAddContext context, ContainerShape containerShape, T businessObject) {
	}
}

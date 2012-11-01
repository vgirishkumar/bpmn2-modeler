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
 * @author Bob Brodt
 ******************************************************************************/

package org.eclipse.bpmn2.modeler.runtime.example;

import java.util.List;

import org.eclipse.bpmn2.TextAnnotation;
import org.eclipse.bpmn2.modeler.core.features.FeatureContainer;
import org.eclipse.bpmn2.modeler.core.features.artifact.AddTextAnnotationFeature;
import org.eclipse.bpmn2.modeler.core.features.artifact.UpdateTextAnnotationFeature;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.core.utils.StyleUtil;
import org.eclipse.bpmn2.modeler.ui.features.activity.task.CustomTaskFeatureContainer;
import org.eclipse.bpmn2.modeler.ui.features.artifact.CreateTextAnnotationFeature;
import org.eclipse.bpmn2.modeler.ui.features.artifact.TextAnnotationFeatureContainer;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.mm.algorithms.MultiText;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeService;
import org.eclipse.bpmn2.modeler.runtime.example.SampleImageProvider.IconSize;

/**
 * Example implementation of a Custom Task feature container. The main things to consider
 * here are:
 * 
 * createFeatureContainer() - creates the Feature Container that is responsible for
 *   building the "custom task". This can be a subclass of an existing Feature Container
 *   from the editor core, or a new one. Typically, this should be a subclass of the
 *   Feature Container for the type of bpmn2 element defined in the "type" attribute
 *   of this Custom Task extension point.
 * 
 * If your Feature Container extends one of the existing classes from editor core, you should
 * override the following methods:
 * 
 * getAddFeature() - this should override the Add Feature from the chosen Feature Container
 *   base class (see above). Typically you will want to override the decorateShape() method
 *   which allows you to customize the graphical representation of this Custom Task figure.
 * getCreateFeature() - this MUST be overridden if you intend to add extension attributes to
 *   your business object (bpmn2 element) - see the code example below. You will also want to
 *   provide your own images for the tool palette by overriding getCreateImageId() and
 *   getCreateLargeImageId() in your Create Feature.
 * 
 * @author Bob Brodt
 */
public class SampleCustomTaskFeatureContainer extends CustomTaskFeatureContainer {
	
	@Override
	protected FeatureContainer createFeatureContainer(IFeatureProvider fp) {
		return new TextAnnotationFeatureContainer() {

			@Override
			public IAddFeature getAddFeature(IFeatureProvider fp) {
					
				return new AddTextAnnotationFeature(fp) {
					@Override
					protected void decorateShape(IAddContext context, ContainerShape containerShape, TextAnnotation businessObject) {
						IGaService gaService = Graphiti.getGaService();
						IPeService peService = Graphiti.getPeService();
						// Change the size of the default TextAnnotation selection rectangle
						Rectangle selectionRect = (Rectangle)containerShape.getGraphicsAlgorithm();
						int width = 140;
						int height = 60;
						selectionRect.setWidth(width);
						selectionRect.setHeight(height);
						
						// Remove the "bracket" polygon that is the visual for TextAnnotation... 
						peService.deletePictogramElement(containerShape.getChildren().get(0));
						// ...and replace it with a RoundedRectangle
						Shape rectShape = peService.createShape(containerShape, false);
						peService.sendToBack(rectShape);
						RoundedRectangle roundedRect = gaService.createRoundedRectangle(rectShape, 5, 5);
						// apply the same styling as TextAnnotation
						StyleUtil.applyStyle(roundedRect, businessObject);
						gaService.setLocationAndSize(roundedRect, 0, 0, width, height);
						
						// add an image to the top-left corner of the rectangle
						Image img = SampleImageProvider.createImage(roundedRect, customTaskDescriptor, 38, 38);
						Graphiti.getGaService().setLocation(img, 2, 2);
						
						// change location of the MultiText pictogram so it doesn't overlap the image
						for (PictogramElement pe : containerShape.getChildren()) {
							GraphicsAlgorithm ga = pe.getGraphicsAlgorithm();
							if (ga instanceof MultiText) {
								Graphiti.getGaService().setLocationAndSize(ga, 40, 2, width-42, height-2);
							}
						}
					}
				};
			}

			@Override
			public ICreateFeature getCreateFeature(IFeatureProvider fp) {
				return new CreateTextAnnotationFeature(fp) {

					@Override
					public TextAnnotation createBusinessObject(ICreateContext context) {
						TextAnnotation businessObject = super.createBusinessObject(context);
						customTaskDescriptor.populateObject(businessObject, true);
						return businessObject;
					}

					@Override
					public String getCreateImageId() {
						return SampleImageProvider.getImageId(customTaskDescriptor, IconSize.SMALL);
					}

					@Override
					public String getCreateLargeImageId() {
						return SampleImageProvider.getImageId(customTaskDescriptor, IconSize.LARGE);
					}
				};
			}

			@Override
			public IDirectEditingFeature getDirectEditingFeature(IFeatureProvider fp) {
				return null;
			}

			@Override
			public IResizeShapeFeature getResizeFeature(IFeatureProvider fp) {
				return null;
			}
		};
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.ui.features.activity.task.CustomTaskFeatureContainer#getId(org.eclipse.emf.ecore.EObject)
	 * 
	 * This method is called by the Feature Provider when it needs to find the Feature Container that will be handling the
	 * creation of a new object. @see org.eclipse.bpmn2.modeler.ui.diagram.BPMNFeatureProvider.getAddFeature(IAddContext).
	 * This method should inspect the object (which will be a bpmn2 element) and determine whether it is responsible for
	 * managing this object's lifecycle, typically by examining extension attributes, as shown in this example.
	 */
	public String getId(EObject object) {
		if (object==null)
			return null;
		List<EStructuralFeature> features = ModelUtil.getAnyAttributes(object);
		for (EStructuralFeature f : features) {
			if ("sampleCustomTaskId".equals(f.getName())) {
				Object attrValue = object.eGet(f);
				return (String)attrValue;
			}
		}
		return null;
	}
}

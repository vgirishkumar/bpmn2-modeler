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
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.ui.features.activity.task.CustomTaskFeatureContainer;
import org.eclipse.bpmn2.modeler.ui.features.artifact.CreateTextAnnotationFeature;
import org.eclipse.bpmn2.modeler.ui.features.artifact.TextAnnotationFeatureContainer;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.services.Graphiti;

/**
 * @author Bob Brodt
 *
 */
public class SampleCustomTaskFeatureContainer extends CustomTaskFeatureContainer {
	
	@Override
	protected FeatureContainer getFeatureContainer(IFeatureProvider fp) {
		if (featureContainerDelegate==null) {
			featureContainerDelegate = new TextAnnotationFeatureContainer() {

				@Override
				public boolean canApplyTo(Object o) {
					if (super.canApplyTo(o)) {
						TextAnnotation ta = (TextAnnotation)o;
						if (ModelUtil.getAnyAttribute(ta, "sampleCustomTaskId") != null){
							return true;
						}
					}
					return false;
				}

				@Override
				public IAddFeature getAddFeature(IFeatureProvider fp) {
						
					return new AddTextAnnotationFeature(fp) {
						@Override
						protected void decorateShape(IAddContext context, ContainerShape containerShape, TextAnnotation businessObject) {
							GraphicsAlgorithmContainer ga = getGraphicsAlgorithm(containerShape);
							Image img = SampleImageProvider.createImage(ga, customTaskDescriptor, 40, 40);
							if (img!=null)
								Graphiti.getGaService().setLocation(img, 2, 2);
						}
					};
				}

				@Override
				public ICreateFeature getCreateFeature(IFeatureProvider fp) {
					return new CreateTextAnnotationFeature(fp) {

						@Override
						public Object[] create(ICreateContext context) {
							Object[] result =  super.create(context);
							TextAnnotation ta = (TextAnnotation)result[0];

							customTaskDescriptor.populateObject(ta, true);
							return result;
						}
						
					};
				}
				
			};
		}
		return featureContainerDelegate;
	}
	
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

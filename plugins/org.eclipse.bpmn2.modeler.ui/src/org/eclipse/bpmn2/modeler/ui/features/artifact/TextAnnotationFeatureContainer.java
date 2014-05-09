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
 * @author Innar Made
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.ui.features.artifact;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.TextAnnotation;
import org.eclipse.bpmn2.modeler.core.features.DefaultMoveBPMNShapeFeature;
import org.eclipse.bpmn2.modeler.core.features.DefaultResizeBPMNShapeFeature;
import org.eclipse.bpmn2.modeler.core.features.MultiAddFeature;
import org.eclipse.bpmn2.modeler.core.features.MultiUpdateFeature;
import org.eclipse.bpmn2.modeler.core.features.ShowDocumentationFeature;
import org.eclipse.bpmn2.modeler.core.features.ShowPropertiesFeature;
import org.eclipse.bpmn2.modeler.core.features.activity.UpdateActivityCompensateMarkerFeature;
import org.eclipse.bpmn2.modeler.core.features.activity.UpdateActivityLoopAndMultiInstanceMarkerFeature;
import org.eclipse.bpmn2.modeler.core.features.artifact.AddTextAnnotationFeature;
import org.eclipse.bpmn2.modeler.core.features.artifact.DirectEditTextAnnotationFeature;
import org.eclipse.bpmn2.modeler.core.features.artifact.LayoutTextAnnotationFeature;
import org.eclipse.bpmn2.modeler.core.features.artifact.UpdateTextAnnotationFeature;
import org.eclipse.bpmn2.modeler.core.features.label.AddShapeLabelFeature;
import org.eclipse.bpmn2.modeler.core.features.label.LabelFeatureContainer;
import org.eclipse.bpmn2.modeler.core.features.label.UpdateLabelFeature;
import org.eclipse.bpmn2.modeler.core.preferences.ShapeStyle.LabelPosition;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.ui.features.AbstractDefaultDeleteFeature;
import org.eclipse.graphiti.datatypes.ILocation;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.IMoveShapeContext;
import org.eclipse.graphiti.features.context.IResizeShapeContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.context.impl.MoveShapeContext;
import org.eclipse.graphiti.features.context.impl.ResizeShapeContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.algorithms.AbstractText;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.styles.Orientation;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

public class TextAnnotationFeatureContainer extends LabelFeatureContainer {

	@Override
	public Object getApplyObject(IContext context) {
		return BusinessObjectUtil.getBusinessObject(context, TextAnnotation.class);
	}
	
	@Override
	public boolean canApplyTo(Object o) {
		return super.canApplyTo(o) && o instanceof TextAnnotation;
	}

	@Override
	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new CreateTextAnnotationFeature(fp);
	}

	@Override
	public IAddFeature getAddFeature(IFeatureProvider fp) {
		MultiAddFeature multiAdd = new MultiAddFeature(fp);
		multiAdd.addFeature(new AddTextAnnotationFeature(fp));
		multiAdd.addFeature(new AddShapeLabelFeature(fp) {

			@Override
			public void applyStyle(GraphicsAlgorithm ga, BaseElement be) {
				super.applyStyle(ga, be);
				if (ga instanceof AbstractText) {
					((AbstractText)ga).setHorizontalAlignment(Orientation.ALIGNMENT_LEFT);
				}
			}

			public String getLabelString(BaseElement element) {
				if (element instanceof TextAnnotation)
					return ((TextAnnotation)element).getText();
				return "";
			}

		});
		return multiAdd;
	}

	@Override
	public IUpdateFeature getUpdateFeature(IFeatureProvider fp) {
		MultiUpdateFeature multiUpdate = new MultiUpdateFeature(fp);
		multiUpdate.addFeature(new UpdateTextAnnotationFeature(fp));
		multiUpdate.addFeature(new UpdateLabelFeature(fp) {
			
			@Override
			protected boolean hasLabel(BaseElement element) {
				return element instanceof TextAnnotation;
			}

			@Override
			protected String getLabelString(BaseElement element) {
				if (element instanceof TextAnnotation)
					return ((TextAnnotation)element).getText();
				return "";
			}
			@Override
			protected ILocation getLabelLocation(PictogramElement pe, boolean isAddingLabel, Point offset) {
				ILocation loc = super.getLabelLocation(pe, isAddingLabel, offset);
				if (loc!=null && !isAddingLabel)
					loc.setY( loc.getY() - LabelFeatureContainer.SHAPE_PADDING/2);
				return loc;
			}

			@Override
			protected int getLabelWidth(AbstractText text) {
				PictogramElement pe = FeatureSupport.getLabelOwner(text);
				return pe.getGraphicsAlgorithm().getWidth() - LabelFeatureContainer.SHAPE_PADDING;
			}

			@Override
			protected int getLabelHeight(AbstractText text) {
				PictogramElement pe = FeatureSupport.getLabelOwner(text);
				return pe.getGraphicsAlgorithm().getHeight() - LabelFeatureContainer.SHAPE_PADDING;
			}

			@Override
			protected LabelPosition getLabelPosition(BaseElement element) {
				return LabelPosition.TOP;
			}
		});
		return multiUpdate;
	}

	@Override
	public IDirectEditingFeature getDirectEditingFeature(IFeatureProvider fp) {
		return new DirectEditTextAnnotationFeature(fp);
	}

	@Override
	public ILayoutFeature getLayoutFeature(IFeatureProvider fp) {
		return new LayoutTextAnnotationFeature(fp);
	}

	@Override
	public IMoveShapeFeature getMoveFeature(IFeatureProvider fp) {
		return new DefaultMoveBPMNShapeFeature(fp) {
			@Override
			public boolean canMoveShape(IMoveShapeContext context) {
				return FeatureSupport.isValidArtifactTarget(context);
			}

			@Override
			protected void internalMove(IMoveShapeContext context) {
				if (FeatureSupport.isLabelShape(context.getPictogramElement())) {
					PictogramElement pe = FeatureSupport.getLabelOwner(context);
					MoveShapeContext newContext = new MoveShapeContext((Shape)pe);
					newContext.setDeltaX(context.getDeltaX());
					newContext.setDeltaY(context.getDeltaY());
					newContext.setX(context.getX());
					newContext.setY(context.getY());
					newContext.setSourceContainer(context.getSourceContainer());
					newContext.setTargetContainer(context.getTargetContainer());
					context = newContext;
				}
				super.internalMove(context);
			}

		};
	}

	@Override
	public IResizeShapeFeature getResizeFeature(IFeatureProvider fp) {
		return new DefaultResizeBPMNShapeFeature(fp) {
			public void resizeShape(IResizeShapeContext context) {
				if (FeatureSupport.isLabelShape(context.getPictogramElement())) {
					PictogramElement pe = FeatureSupport.getLabelOwner(context);
					ResizeShapeContext newContext = new ResizeShapeContext((Shape)pe);
					newContext.setDirection(context.getDirection());
					newContext.setHeight(context.getHeight());
					newContext.setWidth(context.getWidth());
					newContext.setX(context.getX());
					newContext.setY(context.getY());
					context = newContext;
				}
				super.resizeShape(context);
			}
		};
	}

	@Override
	public IDeleteFeature getDeleteFeature(IFeatureProvider fp) {
		return new AbstractDefaultDeleteFeature(fp);
	}
	
	@Override
	public ICustomFeature[] getCustomFeatures(IFeatureProvider fp) {
//		return null;
		return new ICustomFeature[] {
				new ShowDocumentationFeature(fp),
				new ShowPropertiesFeature(fp)
			};
	}

}
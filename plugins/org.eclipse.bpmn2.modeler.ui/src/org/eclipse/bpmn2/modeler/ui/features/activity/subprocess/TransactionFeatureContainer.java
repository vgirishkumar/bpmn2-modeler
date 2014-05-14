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
package org.eclipse.bpmn2.modeler.ui.features.activity.subprocess;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.DataInput;
import org.eclipse.bpmn2.Transaction;
import org.eclipse.bpmn2.modeler.core.features.MultiAddFeature;
import org.eclipse.bpmn2.modeler.core.features.MultiUpdateFeature;
import org.eclipse.bpmn2.modeler.core.features.activity.AbstractCreateExpandableFlowNodeFeature;
import org.eclipse.bpmn2.modeler.core.features.activity.UpdateActivityCompensateMarkerFeature;
import org.eclipse.bpmn2.modeler.core.features.activity.UpdateActivityLoopAndMultiInstanceMarkerFeature;
import org.eclipse.bpmn2.modeler.core.features.label.AddShapeLabelFeature;
import org.eclipse.bpmn2.modeler.core.features.label.UpdateLabelFeature;
import org.eclipse.bpmn2.modeler.core.model.Bpmn2ModelerFactory;
import org.eclipse.bpmn2.modeler.core.preferences.ShapeStyle.LabelPosition;
import org.eclipse.bpmn2.modeler.core.utils.StyleUtil;
import org.eclipse.bpmn2.modeler.ui.ImageProvider;
import org.eclipse.bpmn2.modeler.ui.features.activity.task.BusinessRuleTaskFeatureContainer.AddBusinessRuleTask;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
import org.eclipse.graphiti.mm.algorithms.AbstractText;
import org.eclipse.graphiti.mm.algorithms.RoundedRectangle;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;

public class TransactionFeatureContainer extends AbstractExpandableActivityFeatureContainer {

	private static final int offset = 3;

	@Override
	public boolean canApplyTo(Object o) {
		return super.canApplyTo(o) && o instanceof Transaction;
	}

	@Override
	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new CreateTransactionFeature(fp);
	}

	@Override
	public IAddFeature getAddFeature(IFeatureProvider fp) {
		MultiAddFeature multiAdd = new MultiAddFeature(fp);
		multiAdd.addFeature(new AddExpandableActivityFeature<Transaction>(fp) {

			@Override
			protected void decorateShape(IAddContext context, ContainerShape containerShape, Transaction businessObject) {
				super.decorateShape(context, containerShape, businessObject);
				RoundedRectangle rect = (RoundedRectangle)getGraphicsAlgorithm(containerShape);
				IGaService gaService = Graphiti.getGaService();
				RoundedRectangle innerRect = gaService.createRoundedRectangle(rect, 5, 5);
				innerRect.setFilled(false);
				innerRect.setForeground(manageColor(StyleUtil.CLASS_FOREGROUND));
				gaService.setLocationAndSize(innerRect, offset, offset, rect.getWidth() - (2 * offset),
						rect.getHeight() - (2 * offset));
			}

			@Override
			protected int getMarkerContainerOffset() {
				return offset;
			}
		});
		multiAdd.addFeature(new AddShapeLabelFeature(fp));
		return multiAdd;
	}

	@Override
	public IUpdateFeature getUpdateFeature(IFeatureProvider fp) {
		MultiUpdateFeature multiUpdate = new MultiUpdateFeature(fp);
		multiUpdate.addFeature(super.getUpdateFeature(fp));
		multiUpdate.addFeature(new UpdateLabelFeature(fp) {
			
			@Override
			public boolean canUpdate(IUpdateContext context) {
				Object bo = getBusinessObjectForPictogramElement(context.getPictogramElement());
				return bo != null && bo instanceof BaseElement && canApplyTo((BaseElement) bo);
			}

			@Override
			protected LabelPosition getLabelPosition(AbstractText text) {
				if (isElementExpanded(text)) {
					return LabelPosition.TOP;
				}
				return LabelPosition.CENTER;
			}
		});
		return multiUpdate;
	}

	@Override
	public ILayoutFeature getLayoutFeature(IFeatureProvider fp) {
		return new LayoutExpandableActivityFeature(fp) {
			@Override
			protected void layoutInRectangle(RoundedRectangle rect) {
				IGaService gaService = Graphiti.getGaService();
				RoundedRectangle innerRect = (RoundedRectangle) rect.getGraphicsAlgorithmChildren().get(0);
				gaService.setSize(innerRect, rect.getWidth() - 6, rect.getHeight() - 6);
			}

			@Override
			protected int getMarkerContainerOffset() {
				return offset;
			}
		};
	}

	public static class CreateTransactionFeature extends AbstractCreateExpandableFlowNodeFeature<Transaction> {

		public CreateTransactionFeature(IFeatureProvider fp) {
			super(fp, Messages.TransactionFeatureContainer_Name, Messages.TransactionFeatureContainer_Description);		}

		@Override
		protected String getStencilImageId() {
			return ImageProvider.IMG_16_TRANSACTION;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.bpmn2.modeler.core.features.AbstractCreateFlowElementFeature#getFlowElementClass()
		 */
		@Override
		public EClass getBusinessObjectClass() {
			return Bpmn2Package.eINSTANCE.getTransaction();
		}
	}
}
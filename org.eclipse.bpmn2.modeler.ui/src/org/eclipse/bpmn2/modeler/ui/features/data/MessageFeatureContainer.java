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
package org.eclipse.bpmn2.modeler.ui.features.data;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.Message;
import org.eclipse.bpmn2.RootElement;
import org.eclipse.bpmn2.modeler.core.di.DIImport;
import org.eclipse.bpmn2.modeler.core.features.AbstractAddBPMNShapeFeature;
import org.eclipse.bpmn2.modeler.core.features.BaseElementFeatureContainer;
import org.eclipse.bpmn2.modeler.core.features.DefaultMoveBPMNShapeFeature;
import org.eclipse.bpmn2.modeler.core.features.MultiUpdateFeature;
import org.eclipse.bpmn2.modeler.core.features.UpdateBaseElementNameFeature;
import org.eclipse.bpmn2.modeler.core.features.data.AbstractCreateRootElementFeature;
import org.eclipse.bpmn2.modeler.core.model.Bpmn2ModelerFactory;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil.Envelope;
import org.eclipse.bpmn2.modeler.core.utils.StyleUtil;
import org.eclipse.bpmn2.modeler.ui.ImageProvider;
import org.eclipse.bpmn2.modeler.ui.features.LayoutBaseElementTextFeature;
import org.eclipse.bpmn2.modeler.ui.features.choreography.UpdateChoreographyMessageFlowFeature;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.IResizeShapeContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.features.impl.DefaultMoveShapeFeature;
import org.eclipse.graphiti.features.impl.DefaultResizeShapeFeature;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeService;

public class MessageFeatureContainer extends BaseElementFeatureContainer {

	public static final int ENVELOPE_WIDTH = 30;
	public static final int ENVELOPE_HEIGHT = 20;

	@Override
	public boolean canApplyTo(Object o) {
		return super.canApplyTo(o) && o instanceof Message;
	}

	@Override
	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new CreateMessageFeature(fp);
	}

	@Override
	public IAddFeature getAddFeature(IFeatureProvider fp) {
		return new AddMessageFeature(fp);
	}

	@Override
	public IUpdateFeature getUpdateFeature(IFeatureProvider fp) {
		// because ChoreographyTasks have an associated Message visual,
		// we need to allow these to update themselves also.
		MultiUpdateFeature multiUpdate = new MultiUpdateFeature(fp);
		multiUpdate.addUpdateFeature(new UpdateBaseElementNameFeature(fp));
		multiUpdate.addUpdateFeature(new UpdateChoreographyMessageFlowFeature(fp));
		return multiUpdate;
	}

	@Override
	public IDirectEditingFeature getDirectEditingFeature(IFeatureProvider fp) {
		return null;
	}

	@Override
	public ILayoutFeature getLayoutFeature(IFeatureProvider fp) {
		return new LayoutBaseElementTextFeature(fp) {
			@Override
			public int getMinimumWidth() {
				return 30;
			}
		};
	}

	@Override
	public IMoveShapeFeature getMoveFeature(IFeatureProvider fp) {
		return new DefaultMoveBPMNShapeFeature(fp);
	}

	@Override
	public IResizeShapeFeature getResizeFeature(IFeatureProvider fp) {
		return new DefaultResizeShapeFeature(fp) {
			@Override
			public boolean canResizeShape(IResizeShapeContext context) {
				return false;
			}
		};
	}

	public class AddMessageFeature extends AbstractAddBPMNShapeFeature<Message> {
		public AddMessageFeature(IFeatureProvider fp) {
			super(fp);
		}

		@Override
		public boolean canAdd(IAddContext context) {
			return true;
		}

		@Override
		public PictogramElement add(IAddContext context) {
			IGaService gaService = Graphiti.getGaService();
			IPeService peService = Graphiti.getPeService();
			Message businessObject = getBusinessObject(context);

			int width = this.getWidth();
			int height = this.getHeight();

			ContainerShape containerShape = peService.createContainerShape(context.getTargetContainer(), true);
			Rectangle invisibleRect = gaService.createInvisibleRectangle(containerShape);
			gaService.setLocationAndSize(invisibleRect, context.getX(), context.getY(), width, height);

			Envelope envelope = GraphicsUtil.createEnvelope(invisibleRect, 0, 0, width, height);
			envelope.rect.setFilled(true);
			StyleUtil.applyStyle(envelope.rect, businessObject);
			envelope.line.setForeground(manageColor(StyleUtil.CLASS_FOREGROUND));

			peService.createChopboxAnchor(containerShape);
			AnchorUtil.addFixedPointAnchors(containerShape, invisibleRect);

			boolean isImport = context.getProperty(DIImport.IMPORT_PROPERTY) != null;
			createDIShape(containerShape, businessObject, !isImport);

			// hook for subclasses to inject extra code
			((AddContext)context).setWidth(width);
			((AddContext)context).setHeight(height);
			decorateShape(context, containerShape, businessObject);
			
			layoutPictogramElement(containerShape);
			
			// change the AddContext and prepare it to add a label below the figure
			this.prepareAddContext(context, width, height);
			this.getFeatureProvider().getAddFeature(context).add(context);
			
			return containerShape;
		}

		@Override
		public int getHeight() {
			return ENVELOPE_HEIGHT;
		}

		@Override
		public int getWidth() {
			return ENVELOPE_WIDTH;
		}
	}

	public static class CreateMessageFeature extends AbstractCreateRootElementFeature<Message> {

		public CreateMessageFeature(IFeatureProvider fp) {
			super(fp, "Message", "Represents the content of a communication between two Participants");
		}

		@Override
		public String getStencilImageId() {
			return ImageProvider.IMG_16_MESSAGE;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.bpmn2.modeler.core.features.AbstractBpmn2CreateFeature#getBusinessObjectClass()
		 */
		@Override
		public EClass getBusinessObjectClass() {
			return Bpmn2Package.eINSTANCE.getMessage();
		}
	}

	@Override
	public IDeleteFeature getDeleteFeature(IFeatureProvider context) {
		return null;
	}
}
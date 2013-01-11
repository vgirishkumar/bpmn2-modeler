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
package org.eclipse.bpmn2.modeler.ui.features.flow;

import java.io.IOException;
import java.util.List;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.EventDefinition;
import org.eclipse.bpmn2.InteractionNode;
import org.eclipse.bpmn2.Message;
import org.eclipse.bpmn2.MessageEventDefinition;
import org.eclipse.bpmn2.MessageFlow;
import org.eclipse.bpmn2.Participant;
import org.eclipse.bpmn2.StartEvent;
import org.eclipse.bpmn2.modeler.core.Activator;
import org.eclipse.bpmn2.modeler.core.ModelHandler;
import org.eclipse.bpmn2.modeler.core.features.BaseElementConnectionFeatureContainer;
import org.eclipse.bpmn2.modeler.core.features.UpdateLabelFeature;
import org.eclipse.bpmn2.modeler.core.features.flow.AbstractAddFlowFeature;
import org.eclipse.bpmn2.modeler.core.features.flow.AbstractCreateFlowFeature;
import org.eclipse.bpmn2.modeler.core.features.flow.AbstractReconnectFlowFeature;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.StyleUtil;
import org.eclipse.bpmn2.modeler.ui.ImageProvider;
import org.eclipse.bpmn2.modeler.ui.features.choreography.ChoreographyUtil;
import org.eclipse.bpmn2.modeler.ui.features.data.MessageFeatureContainer;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.datatypes.ILocation;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.IReconnectionFeature;
import org.eclipse.graphiti.features.IRemoveFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.IRemoveContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.features.context.impl.AreaContext;
import org.eclipse.graphiti.features.context.impl.MoveShapeContext;
import org.eclipse.graphiti.features.context.impl.RemoveContext;
import org.eclipse.graphiti.features.impl.DefaultRemoveFeature;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.Ellipse;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.styles.LineStyle;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeService;
import org.eclipse.graphiti.util.IColorConstant;

public class MessageFlowFeatureContainer extends BaseElementConnectionFeatureContainer {

	public final static String MESSAGE_REF = "message.ref";
	IPeService peService = Graphiti.getPeService();
	IGaService gaService = Graphiti.getGaService();

	@Override
	public boolean canApplyTo(Object o) {
		return super.canApplyTo(o) && o instanceof MessageFlow;
	}

	@Override
	public IAddFeature getAddFeature(IFeatureProvider fp) {
		return new AddMessageFlowFeature(fp);
	}

	@Override
	public ICreateConnectionFeature getCreateConnectionFeature(IFeatureProvider fp) {
		return new CreateMessageFlowFeature(fp);
	}

	@Override
	public IRemoveFeature getRemoveFeature(final IFeatureProvider fp) {
		return new DefaultRemoveFeature(fp) {

			public void remove(IRemoveContext context) {
				Connection connection = (Connection) context.getPictogramElement();
				removeMessageDecorator(fp, connection);
				super.remove(context);
			}
		};
	}

	@Override
	public IUpdateFeature getUpdateFeature(IFeatureProvider fp) {
		return new UpdateLabelFeature(fp) {

			@Override
			public IReason updateNeeded(IUpdateContext context) {
				if (context.getPictogramElement() instanceof Connection) {
					Connection connection = (Connection) context.getPictogramElement();
					MessageFlow messageFlow = (MessageFlow) BusinessObjectUtil.getFirstBaseElement(connection);
					
					String oldMessageRef = peService.getPropertyValue(connection, MESSAGE_REF);
					if (oldMessageRef==null)
						oldMessageRef = "";
					
					String newMessageRef = messageToString(messageFlow.getMessageRef());
					
					if (!oldMessageRef.equals(newMessageRef)) {
						return Reason.createTrueReason("Message Ref Changed");
					}
					
					// check if connection has been moved or reconnected
					if (messageDecoratorMoved(connection))
						return Reason.createTrueReason("Message Decorator Moved");
				}
				return super.updateNeeded(context);
			}
			
			@Override
			public boolean update(IUpdateContext context) {
				Connection connection = (Connection) context.getPictogramElement();
				MessageFlow messageFlow = (MessageFlow) BusinessObjectUtil.getFirstBaseElement(connection);
				Message message = messageFlow.getMessageRef();
				String oldMessageRef = peService.getPropertyValue(connection, MESSAGE_REF);
				if (oldMessageRef==null)
					oldMessageRef = "";
				
				String newMessageRef = messageToString(messageFlow.getMessageRef());
				
				if (!oldMessageRef.equals(newMessageRef)) {
					removeMessageDecorator(getFeatureProvider(), connection);
					if (message!=null) {
						addMessageDecorator(getFeatureProvider(), connection, message);
					}
					peService.setPropertyValue(connection, MESSAGE_REF, newMessageRef);
				}
				else {
					// move the message decorator
					adjustMessageDecorator(getFeatureProvider(), connection);
				}

				return super.update(context);
			}
		};
	}
	
	@Override
	public IReconnectionFeature getReconnectionFeature(IFeatureProvider fp) {
		return new ReconnectMessageFlowFeature(fp);
	}
	
	public class AddMessageFlowFeature extends AbstractAddFlowFeature<MessageFlow> {
		public AddMessageFlowFeature(IFeatureProvider fp) {
			super(fp);
		}

		@Override
		public boolean canAdd(IAddContext context) {
			if (context instanceof IAddConnectionContext) {
				IAddConnectionContext acc = (IAddConnectionContext) context;
				if (acc.getSourceAnchor() != null) {
					Object obj = BusinessObjectUtil.getFirstElementOfType(acc.getSourceAnchor().getParent(),
							BaseElement.class);
					if (obj instanceof StartEvent) {
						return false;
					}
				}
			}
			return super.canAdd(context);
		}

		@Override
		protected Polyline createConnectionLine(final Connection connection) {
			MessageFlow messageFlow = (MessageFlow) BusinessObjectUtil.getFirstBaseElement(connection);

			Polyline connectionLine = super.createConnectionLine(connection);
			connectionLine.setLineStyle(LineStyle.DASH);
			connectionLine.setLineWidth(2);

			ConnectionDecorator endDecorator = peService.createConnectionDecorator(connection, false, 1.0, true);
			ConnectionDecorator startDecorator = peService.createConnectionDecorator(connection, false, 0, true);
			
			int w = 5;
			int l = 10;
			
			Polyline arrowhead = gaService.createPolygon(endDecorator, new int[] { -l, w, 0, 0, -l, -w, -l, w });
			StyleUtil.applyStyle(arrowhead, messageFlow);
			arrowhead.setBackground(manageColor(IColorConstant.WHITE));

			Ellipse circle = gaService.createEllipse(startDecorator);
			gaService.setSize(circle, 10, 10);
			StyleUtil.applyStyle(circle, messageFlow);
			circle.setBackground(manageColor(IColorConstant.WHITE));
			
			return connectionLine;
		}

		@Override
		protected Class<? extends BaseElement> getBoClass() {
			return MessageFlow.class;
		}
	}

	protected String messageToString(Message message) {
		if (message==null)
			return "";
		return message.getId();
	}

	protected ConnectionDecorator findMessageDecorator(Connection connection) {
		for (ConnectionDecorator d : connection.getConnectionDecorators()) {
			if (peService.getPropertyValue(d, MESSAGE_REF) != null) {
				return d;
			}
		}
		return null;
	}
	
	protected boolean messageDecoratorMoved(Connection connection) {
		ConnectionDecorator decorator = findMessageDecorator(connection);
		if (decorator!=null) {
			ILocation loc = peService.getConnectionMidpoint(connection, 0.25);
			PictogramElement messageShape = (PictogramElement) decorator.getLink().getBusinessObjects().get(1);
			int w = MessageFeatureContainer.ENVELOPE_WIDTH;
			int h = MessageFeatureContainer.ENVELOPE_HEIGHT;
			ILocation shapeLoc = peService.getLocationRelativeToDiagram((Shape)messageShape);
			return shapeLoc.getX() + w/2 != loc.getX() || shapeLoc.getY() + h/2 != loc.getY();
		}
		return false;
	}
	
	protected void adjustMessageDecorator(IFeatureProvider fp, Connection connection) {
		ConnectionDecorator decorator = findMessageDecorator(connection);
		if (decorator!=null) {
			// calculate new location: this will be 1/4 of the distance from start of the connection line
			ILocation loc = peService.getConnectionMidpoint(connection, 0.25);
			Shape messageShape = (Shape) decorator.getLink().getBusinessObjects().get(1);
			int w = MessageFeatureContainer.ENVELOPE_WIDTH;
			int h = MessageFeatureContainer.ENVELOPE_HEIGHT;
			int x = loc.getX() - w/2;
			int y = loc.getY() - h/2;
			MoveShapeContext moveContext = new MoveShapeContext(messageShape);
			moveContext.setX(x);
			moveContext.setY(y);
			IMoveShapeFeature moveFeature = fp.getMoveShapeFeature(moveContext);
			moveFeature.moveShape(moveContext);
		}
	}

	protected void addMessageDecorator(IFeatureProvider fp, Connection connection, Message message) {
		ILocation loc = peService.getConnectionMidpoint(connection, 0.25);
		Diagram diagram = peService.getDiagramForPictogramElement(connection);
		ConnectionDecorator decorator = peService.createConnectionDecorator(connection, true, 0.25, true);

		int w = MessageFeatureContainer.ENVELOPE_WIDTH;
		int h = MessageFeatureContainer.ENVELOPE_HEIGHT;
		AddContext addContext = new AddContext(new AreaContext(), message);
		addContext.putProperty(MessageFeatureContainer.IS_REFERENCE, Boolean.TRUE);
		addContext.setX(loc.getX() - w/2);
		addContext.setY(loc.getY() - h/2);
		addContext.setTargetContainer(diagram);
		PictogramElement messageShape = fp.addIfPossible(addContext);
		fp.link(decorator, new Object[] {message, messageShape});
		peService.setPropertyValue(decorator, MESSAGE_REF, "true");
	}
	
	protected void removeMessageDecorator(IFeatureProvider fp, Connection connection) {
		ConnectionDecorator decorator = findMessageDecorator(connection);
		if (decorator!=null) {
			PictogramElement messageShape = (PictogramElement) decorator.getLink().getBusinessObjects().get(1);
			RemoveContext removeContext = new RemoveContext(messageShape);
			IRemoveFeature removeFeature = fp.getRemoveFeature(removeContext);
			removeFeature.remove(removeContext);
			
			peService.deletePictogramElement(decorator);
		}
	}

	public static class CreateMessageFlowFeature extends AbstractCreateFlowFeature<MessageFlow, InteractionNode, InteractionNode> {

		public CreateMessageFlowFeature(IFeatureProvider fp) {
			super(fp, "Message Flow", "Represents message between two participants");
		}

		@Override
		public boolean isAvailable(IContext context) {
			if (context instanceof ICreateConnectionContext) {
				ICreateConnectionContext ccc = (ICreateConnectionContext) context;
				if (ccc.getSourcePictogramElement() != null) {
					Object obj = BusinessObjectUtil.getFirstElementOfType(
							ccc.getSourcePictogramElement(), BaseElement.class);
					if (obj instanceof EndEvent) {
						List<EventDefinition> eventDefinitions = ((EndEvent) obj)
								.getEventDefinitions();
						for (EventDefinition eventDefinition : eventDefinitions) {
							if (eventDefinition instanceof MessageEventDefinition) {
								return true;
							}
						}
					}
					else if (obj instanceof StartEvent){
						return false;
					}
				}
			}
			return super.isAvailable(context);
		}

		@Override
		public boolean canStartConnection(ICreateConnectionContext context) {
			if (ChoreographyUtil.isChoreographyParticipantBand(context.getSourcePictogramElement()))
				return false;
			return true;
		}

		@Override
		public boolean canCreate(ICreateConnectionContext context) {
			if (ChoreographyUtil.isChoreographyParticipantBand(context.getSourcePictogramElement()))
				return false;
			if (context.getTargetPictogramElement()!=null) {
				if (ChoreographyUtil.isChoreographyParticipantBand(context.getTargetPictogramElement()))
					return false;
			}
			InteractionNode source = getSourceBo(context);
			InteractionNode target = getTargetBo(context);
			return super.canCreate(context) && isDifferentParticipants(source, target);
		}

		@Override
		protected String getStencilImageId() {
			return ImageProvider.IMG_16_MESSAGE_FLOW;
		}

		@Override
		public MessageFlow createBusinessObject(ICreateConnectionContext context) {
			MessageFlow bo = null;
			try {
				ModelHandler mh = ModelHandler.getInstance(getDiagram());
				InteractionNode source = getSourceBo(context);
				InteractionNode target = getTargetBo(context);
				bo = mh.createMessageFlow(source, target);
				bo.setName("");
				putBusinessObject(context, bo);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return bo;
		}

		@Override
		protected Class<InteractionNode> getSourceClass() {
			return InteractionNode.class;
		}

		@Override
		protected Class<InteractionNode> getTargetClass() {
			return InteractionNode.class;
		}

		private boolean isDifferentParticipants(InteractionNode source, InteractionNode target) {
			if (source == null || target == null) {
				return true;
			}
			boolean different = false;
			try {
				ModelHandler handler = ModelHandler.getInstance(getDiagram());
				Participant sourceParticipant = handler.getParticipant(source);
				Participant targetParticipant = handler.getParticipant(target);
				if (sourceParticipant==null) {
					if (targetParticipant==null)
						return true;
					return false;
				}
				different = !sourceParticipant.equals(targetParticipant);
			} catch (IOException e) {
				Activator.logError(e);
			}
			return different;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.bpmn2.modeler.core.features.AbstractBpmn2CreateConnectionFeature#getBusinessObjectClass()
		 */
		@Override
		public EClass getBusinessObjectClass() {
			return Bpmn2Package.eINSTANCE.getMessageFlow();
		}
	}
	
	public static class ReconnectMessageFlowFeature extends AbstractReconnectFlowFeature {

		public ReconnectMessageFlowFeature(IFeatureProvider fp) {
			super(fp);
		}

		@Override
		protected Class<? extends EObject> getTargetClass() {
			return InteractionNode.class;
		}

		@Override
		protected Class<? extends EObject> getSourceClass() {
			return InteractionNode.class;
		}
	} 
}
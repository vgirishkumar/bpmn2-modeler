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
package org.eclipse.bpmn2.modeler.core.features.choreography;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.ChoreographyActivity;
import org.eclipse.bpmn2.ChoreographyTask;
import org.eclipse.bpmn2.Message;
import org.eclipse.bpmn2.MessageFlow;
import org.eclipse.bpmn2.Participant;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.di.ParticipantBandKind;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesProvider;
import org.eclipse.bpmn2.modeler.core.model.Bpmn2ModelerFactory;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil.AnchorLocation;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil.BoundaryAnchor;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil.Envelope;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.core.utils.StyleUtil;
import org.eclipse.bpmn2.modeler.core.utils.Tuple;
import org.eclipse.dd.dc.Bounds;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.context.impl.LayoutContext;
import org.eclipse.graphiti.features.context.impl.UpdateContext;
import org.eclipse.graphiti.mm.PropertyContainer;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.algorithms.Text;
import org.eclipse.graphiti.mm.algorithms.styles.LineStyle;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeService;
import org.eclipse.graphiti.ui.services.GraphitiUi;
import org.eclipse.graphiti.util.IColorConstant;

/**
 * FIXME: Clean this mess up. These should be in their appropriate Features, not a utility class.
 */
public class ChoreographyUtil implements ChoreographyProperties {

	private static IGaService gaService = Graphiti.getGaService();
	private static IPeService peService = Graphiti.getPeService();

	public static List<BPMNShape> getParicipantBandBpmnShapes(ContainerShape choreographyActivityContainerShape) {
		List<BPMNShape> bpmnShapes = new ArrayList<BPMNShape>();
		List<ContainerShape> containers = FeatureSupport.getParticipantBandContainerShapes(choreographyActivityContainerShape);
		for (ContainerShape container : containers) {
			BPMNShape bpmnShape = BusinessObjectUtil.getFirstElementOfType(container, BPMNShape.class);
			bpmnShapes.add(bpmnShape);
		}
		return bpmnShapes;
	}

	public static boolean isChoreographyParticipantBand(PictogramElement element) {
		EObject container = element.eContainer();
		if (container instanceof PictogramElement) {
			PictogramElement containerElem = (PictogramElement) container;
			Object bo = Graphiti.getLinkService().getBusinessObjectForLinkedPictogramElement(containerElem);
			if (bo instanceof ChoreographyActivity) {
				return true;
			}
		}
		return false;
	}

	public static boolean isChoreographyMessageLink(PictogramElement pe) {
		EObject o = BusinessObjectUtil.getFirstElementOfType(pe, BaseElement.class);
		if (o instanceof MessageFlow && pe instanceof Connection) {
			Connection c = (Connection)pe;
			if (c.getStart()!=null && peService.getPropertyValue(c.getStart().getParent(),MESSAGE_LINK) != null)
				return true;
			if (c.getEnd()!=null && peService.getPropertyValue(c.getEnd().getParent(),MESSAGE_LINK) != null)
				return true;
		}
		return false;
	}

	public static boolean isChoreographyMessage(PictogramElement pe) {
		EObject o = BusinessObjectUtil.getFirstElementOfType(pe, BaseElement.class);
		if (o instanceof Message && pe instanceof ContainerShape) {
			if (peService.getPropertyValue(pe,MESSAGE_LINK) != null)
				return true;
		}
		return false;
	}
	
	public static boolean removeChoreographyMessageLink(PictogramElement pe) {
		if (isChoreographyMessageLink(pe)) {
			Connection connection = (Connection)pe;
			// remove the Message figure
			peService.deletePictogramElement( connection.getEnd().getParent() );
			// remove the connection
			peService.deletePictogramElement(connection);
			return true;
		}
		return false;
	}

	public static String getMessageRefIds(ChoreographyTask choreography) {
		if (choreography.getMessageFlowRef() == null) {
			return new String();
		}
		Iterator<MessageFlow> iterator = choreography.getMessageFlowRef().iterator();
		String delim = ":"; //$NON-NLS-1$
		StringBuilder sb = new StringBuilder();
		while (iterator.hasNext()) {
			MessageFlow message = iterator.next();
			sb.append(message.getId());
			if (iterator.hasNext()) {
				sb.append(delim);
			}
		}
		return sb.toString();
	}

	private static ContainerShape drawMessageLink(String name, BoundaryAnchor boundaryAnchor, int x, int y, boolean filled) {
		Diagram diagram = peService.getDiagramForAnchor(boundaryAnchor.anchor);

		FreeFormConnection connection = peService.createFreeFormConnection(diagram);
		Polyline connectionLine = gaService.createPolyline(connection);
		connectionLine.setForeground(gaService.manageColor(diagram, StyleUtil.CLASS_FOREGROUND));
		connectionLine.setLineStyle(LineStyle.DOT);
		connectionLine.setLineWidth(2);

		ContainerShape envelope = peService.createContainerShape(diagram, true);
		Rectangle invisibleRectangle = gaService.createInvisibleRectangle(envelope);
		gaService.setLocation(invisibleRectangle, x, y);
		gaService.setSize(invisibleRectangle, ENV_W + 50, ENV_H);

		Shape envelopeShape = peService.createShape(envelope, false);
		Envelope envelopeGa = GraphicsUtil.createEnvelope(envelopeShape, 0, 0, ENV_W, ENV_H);
		IColorConstant color = filled ? IColorConstant.LIGHT_GRAY : IColorConstant.WHITE;
		envelopeGa.rect.setFilled(true);
		envelopeGa.rect.setBackground(gaService.manageColor(diagram, color));
		envelopeGa.rect.setForeground(gaService.manageColor(diagram, StyleUtil.CLASS_FOREGROUND));
		envelopeGa.line.setForeground(gaService.manageColor(diagram, StyleUtil.CLASS_FOREGROUND));
		AnchorUtil.addFixedPointAnchors(envelope, envelopeGa.rect);

		Shape textShape = peService.createShape(envelope, false);
		Text text = gaService.createDefaultText(diagram, textShape);
		IDimension size = GraphitiUi.getUiLayoutService().calculateTextSize(name, text.getFont());
		gaService.setLocationAndSize(text, ENV_W + 3, 3, size.getWidth(), size.getHeight());
		text.setValue(name);

		gaService.setSize(invisibleRectangle, ENV_W + size.getWidth() + 3, ENV_H);

		AnchorLocation envelopeAnchorLoc = null;
		if (boundaryAnchor.locationType == AnchorLocation.TOP) {
			envelopeAnchorLoc = AnchorLocation.BOTTOM;
		} else {
			envelopeAnchorLoc = AnchorLocation.TOP;
		}

		connection.setStart(boundaryAnchor.anchor);
		connection.setEnd(AnchorUtil.getBoundaryAnchors(envelope).get(envelopeAnchorLoc).anchor);
		peService.setPropertyValue(envelope, MESSAGE_LINK, Boolean.toString(true));
		return envelope;
	}

	public static void drawMessageLinks(IFeatureProvider fp, ContainerShape choreographyContainer) {

		List<MessageFlow> messageFlows = new ArrayList<MessageFlow>();
		ChoreographyTask choreography = BusinessObjectUtil.getFirstElementOfType(choreographyContainer,
				ChoreographyTask.class);
		if (choreography != null) {
			messageFlows.addAll(choreography.getMessageFlowRef());
		}

		List<ContainerShape> bandContainers = FeatureSupport.getParticipantBandContainerShapes(choreographyContainer);
		Tuple<List<ContainerShape>, List<ContainerShape>> topAndBottom = FeatureSupport.getTopAndBottomBands(bandContainers);
		List<ContainerShape> shapesWithVisibleMessages = new ArrayList<ContainerShape>();

		Map<AnchorLocation, BoundaryAnchor> boundaryAnchors = AnchorUtil.getBoundaryAnchors(choreographyContainer);
		BoundaryAnchor topBoundaryAnchor = boundaryAnchors.get(AnchorLocation.TOP);
		BoundaryAnchor bottomBoundaryAnchor = boundaryAnchors.get(AnchorLocation.BOTTOM);
		int topConnectionIndex = 0;
		int bottomConnectionIndex = 0;

		boolean hasTopMessage = false;
		EList<Connection> topConnections = topBoundaryAnchor.anchor.getOutgoingConnections();
		for (int i = 0; i < topConnections.size(); i++) {
			Connection connection = topConnections.get(i);
			EObject container = connection.getEnd().eContainer();
			if (container instanceof PropertyContainer) {
				String property = peService.getPropertyValue((PropertyContainer) container, MESSAGE_LINK);
				if (Boolean.parseBoolean(property)) {
					topConnectionIndex = i;
					hasTopMessage = true;
					break;
				}
			}
		}

		boolean hasBottomMessage = false;
		EList<Connection> bottomConnections = bottomBoundaryAnchor.anchor.getOutgoingConnections();
		for (int i = 0; i < bottomConnections.size(); i++) {
			Connection connection = bottomConnections.get(i);
			EObject container = connection.getEnd().eContainer();
			if (container instanceof PropertyContainer) {
				String property = peService.getPropertyValue((PropertyContainer) container, MESSAGE_LINK);
				if (Boolean.parseBoolean(property)) {
					bottomConnectionIndex = i;
					hasBottomMessage = true;
					break;
				}
			}
		}

		Iterator<ContainerShape> iterator = bandContainers.iterator();
		while (iterator.hasNext()) {
			ContainerShape bandContainer = iterator.next();
			BPMNShape bpmnShape = BusinessObjectUtil.getFirstElementOfType(bandContainer, BPMNShape.class);
			if (bpmnShape.isIsMessageVisible()) {
				shapesWithVisibleMessages.add(bandContainer);
			}
		}

		boolean shouldDrawTopMessage = !Collections.disjoint(topAndBottom.getFirst(), shapesWithVisibleMessages);
		boolean shouldDrawBottomMessage = !Collections.disjoint(topAndBottom.getSecond(), shapesWithVisibleMessages);
		ContainerShape envelope;
		
		String topMessageName = null;
		String bottomMessageName = null;
		Message topMessage = null;
		Message bottomMessage = null;

		if (shouldDrawTopMessage) {
			topMessage = getMessage(messageFlows, topAndBottom.getFirst(), false);
			topMessageName = getMessageName(messageFlows, topAndBottom.getFirst());
		}
		if (topMessageName == null) {
			topMessageName = new String();
		}

		if (shouldDrawBottomMessage) {
			bottomMessage = getMessage(messageFlows, topAndBottom.getSecond(), false);
			bottomMessageName = getMessageName(messageFlows, topAndBottom.getSecond());
		}
		if (bottomMessageName == null) {
			bottomMessageName = new String();
		}

		BPMNShape bpmnShape = BusinessObjectUtil.getFirstElementOfType(choreographyContainer, BPMNShape.class);
		Bounds bounds = bpmnShape.getBounds();
		int x = (int) ((bounds.getX() + bounds.getWidth() / 2) - (ENV_W / 2));

		MessageFlow flow = getMessageFlow(messageFlows, topAndBottom.getFirst());
		envelope = null;
		if (!hasTopMessage && shouldDrawTopMessage && flow!=null) {
			int y = (int) (bounds.getY() - ENVELOPE_HEIGHT_MODIFIER - ENV_H);
			envelope = drawMessageLink(topMessageName, topBoundaryAnchor, x, y, isFilled(topAndBottom.getFirst()));
			if (topMessage!=null)
				fp.link(envelope, topMessage);
			peService.setPropertyValue(envelope, MESSAGE_NAME, topMessageName);
		} else if (hasTopMessage && !shouldDrawTopMessage) {
			envelope = (ContainerShape) topConnections.get(topConnectionIndex).getEnd().eContainer();
			peService.deletePictogramElement(topConnections.get(topConnectionIndex));
			peService.deletePictogramElement(envelope);
			envelope = null;
		} else if (hasTopMessage && shouldDrawTopMessage && flow!=null) {
			envelope = (ContainerShape) topConnections.get(topConnectionIndex).getEnd().eContainer();
			setMessageLabel(topMessageName, envelope);
		}
		if (envelope!=null) {
			// link up the message flow
			linkMessageFlow(fp, flow, envelope);
		}

		envelope = null;
		flow = getMessageFlow(messageFlows, topAndBottom.getSecond());
		if (!hasBottomMessage && shouldDrawBottomMessage && flow!=null) {
			int y = (int) (bounds.getY() + bounds.getHeight() + ENVELOPE_HEIGHT_MODIFIER);
			envelope = drawMessageLink(bottomMessageName, bottomBoundaryAnchor, x, y, isFilled(topAndBottom.getSecond()));
			if (bottomMessage!=null)
				fp.link(envelope, bottomMessage);
			peService.setPropertyValue(envelope, MESSAGE_NAME, bottomMessageName);
		} else if (hasBottomMessage && !shouldDrawBottomMessage) {
			envelope = (ContainerShape) bottomConnections.get(bottomConnectionIndex).getEnd()
					.eContainer();
			peService.deletePictogramElement(bottomConnections.get(bottomConnectionIndex));
			peService.deletePictogramElement(envelope);
			envelope = null;
		} else if (hasBottomMessage && shouldDrawBottomMessage && flow!=null) {
			envelope = (ContainerShape) bottomConnections.get(bottomConnectionIndex).getEnd()
					.eContainer();
			setMessageLabel(bottomMessageName, envelope);
		}
		if (envelope!=null) {
			// link up the message flow
			linkMessageFlow(fp, flow, envelope);
		}
		
		return;
	}

	private static void linkMessageFlow(IFeatureProvider fp, MessageFlow flow,ContainerShape envelope) {
		for (Anchor a : envelope.getAnchors()) {
			for (Connection c : a.getIncomingConnections()) {
				fp.link(c, flow);
			}
			for (Connection c : a.getOutgoingConnections()) {
				fp.link(c, flow);
			}
		}
	}
	
	private static boolean isFilled(List<ContainerShape> bands) {
		boolean filled = true;
		for (ContainerShape band : bands) {
			BPMNShape bpmnShape = BusinessObjectUtil.getFirstElementOfType(band, BPMNShape.class);
			if (!bpmnShape.isIsMessageVisible()) {
				continue;
			}
			ParticipantBandKind bandKind = bpmnShape.getParticipantBandKind();
			if (bandKind == ParticipantBandKind.TOP_INITIATING || bandKind == ParticipantBandKind.BOTTOM_INITIATING
					|| bandKind == ParticipantBandKind.MIDDLE_INITIATING) {
				filled = false;
				break;
			}
		}
		return filled;
	}

	private static void setMessageLabel(String label, PictogramElement message) {
		ContainerShape containerShape = (ContainerShape) message;
		Iterator<Shape> iterator = peService.getAllContainedShapes(containerShape).iterator();
		while (iterator.hasNext()) {
			Shape shape = iterator.next();
			if (shape.getGraphicsAlgorithm() instanceof Text) {
				Text text = (Text) shape.getGraphicsAlgorithm();
				text.setValue(label);
				IDimension size = GraphitiUi.getUiLayoutService().calculateTextSize(label, text.getFont());
				gaService.setSize(containerShape.getGraphicsAlgorithm(), ENV_W + size.getWidth() + 3, ENV_H);
				gaService.setSize(text, size.getWidth(), size.getHeight());
				peService.setPropertyValue(containerShape, MESSAGE_NAME, label);
				break;
			}
		}
	}

	private static String getMessageName(List<MessageFlow> messageFlows, List<ContainerShape> bands) {
		for (ContainerShape band : bands) {
			Participant participant = BusinessObjectUtil.getFirstElementOfType(band, Participant.class);
			BPMNShape bpmnShape = BusinessObjectUtil.getFirstElementOfType(band, BPMNShape.class);
			if (bpmnShape.isIsMessageVisible()) {
				for (MessageFlow flow : messageFlows) {
					if (flow.getSourceRef().equals(participant)) {
						return getMessageFlowName(flow);
					}
				}
			}
		}
		return null;
	}

	private static MessageFlow getMessageFlow(List<MessageFlow> messageFlows, List<ContainerShape> bands) {
		for (ContainerShape band : bands) {
			Participant participant = BusinessObjectUtil.getFirstElementOfType(band, Participant.class);
			BPMNShape bpmnShape = BusinessObjectUtil.getFirstElementOfType(band, BPMNShape.class);
			if (bpmnShape.isIsMessageVisible()) {
				for (MessageFlow flow : messageFlows) {
					if (flow.getSourceRef().equals(participant)) {
						return flow;
					}
				}
			}
		}
		return null;
	}

	private static Message getMessage(List<MessageFlow> messageFlows, List<ContainerShape> bands, boolean create) {
		MessageFlow flow = getMessageFlow(messageFlows, bands);
		if (flow!=null) {
			if (flow.getMessageRef()==null && create) {
				Message msg = Bpmn2ModelerFactory.create(Message.class);
				msg.setName(Messages.ChoreographyUtil_Undefined_Message);
				ModelUtil.getDefinitions(flow).getRootElements().add(msg);
				flow.setMessageRef(msg);
				ModelUtil.setID(msg);
			}
			return flow.getMessageRef();
		}
		return null;
	}
	
	public static String getMessageFlowName(MessageFlow flow) {
		if (flow.getMessageRef() == null) {
			return flow.getName();
		} else if (flow.getMessageRef().getItemRef()==null ||
				flow.getMessageRef().getItemRef().getStructureRef()==null) {
			return flow.getMessageRef().getName();
		} else {
			String messageName = flow.getMessageRef().getName();
			String itemDefinitionName = ExtendedPropertiesProvider.getTextValue(flow.getMessageRef().getItemRef());
			String text = itemDefinitionName;
			if (messageName!=null && !messageName.isEmpty())
				text += "/" + messageName; //$NON-NLS-1$
			text = messageName;
			return text;
		}
	}
	
	public static String getMessageName(Message mesg) {
		if (mesg.getItemRef()==null ||
				mesg.getItemRef().getStructureRef()==null) {
			if (mesg.getName()==null)
				return mesg.getId();
			return mesg.getName();
		} else {
			String type = "(" + ExtendedPropertiesProvider.getTextValue(mesg.getItemRef()) +")"; //$NON-NLS-1$ //$NON-NLS-2$
			if (mesg.getName()==null)
				return type; 
			return mesg.getName() + type;
		}
	}
	
	public static void updateChoreographyMessageLinks(IFeatureProvider fp, PictogramElement pe) {
		if (pe instanceof ContainerShape) {
			ContainerShape choreographyTaskShape = (ContainerShape) pe;
			BPMNShape bpmnShape = BusinessObjectUtil.getFirstElementOfType(choreographyTaskShape, BPMNShape.class);
			Bounds bounds = bpmnShape.getBounds();
			int x = (int) ((bounds.getX() + bounds.getWidth() / 2) - (ENV_W / 2));
	
			Map<AnchorLocation, BoundaryAnchor> boundaryAnchors = AnchorUtil.getBoundaryAnchors(choreographyTaskShape);
			BoundaryAnchor topBoundaryAnchor = boundaryAnchors.get(AnchorLocation.TOP);
			BoundaryAnchor bottomBoundaryAnchor = boundaryAnchors.get(AnchorLocation.BOTTOM);
	
			for (Connection connection : topBoundaryAnchor.anchor.getOutgoingConnections()) {
				EObject container = connection.getEnd().eContainer();
				if (container instanceof PropertyContainer) {
					String property = peService.getPropertyValue((PropertyContainer) container, MESSAGE_LINK);
					if (property != null && new Boolean(property)) {
						int y = (int) (bounds.getY() - ENVELOPE_HEIGHT_MODIFIER - ENV_H);
						gaService.setLocation(((ContainerShape) container).getGraphicsAlgorithm(), x, y);
						break;
					}
				}
			}
	
			for (Connection connection : bottomBoundaryAnchor.anchor.getOutgoingConnections()) {
				EObject container = connection.getEnd().eContainer();
				if (container instanceof PropertyContainer) {
					String property = peService.getPropertyValue((PropertyContainer) container, MESSAGE_LINK);
					if (property != null && new Boolean(property)) {
						int y = (int) (bounds.getY() + bounds.getHeight() + ENVELOPE_HEIGHT_MODIFIER);
						gaService.setLocation(((ContainerShape) container).getGraphicsAlgorithm(), x, y);
						break;
					}
				}
			}
		}
	}

	/**
	 * Update the Choreography Activity Participant Bands and force a layout
	 * of the Choreography Activity such that its Label and any Loop markers
	 * are correctly placed inside the Choreography Activity shape
	 * 
	 * @param fp the Feature Provider
	 * @param pe the Choreography Activity Container Shape
	 */
	public static void updateParticipantBands(IFeatureProvider fp, PictogramElement pe) {
		IUpdateContext updateContext = new UpdateContext(pe);
		IUpdateFeature updateFeature = new UpdateChoreographyParticipantRefsFeature(fp);
		updateFeature.update(updateContext);

		ILayoutContext layoutContext = new LayoutContext(pe);
		ILayoutFeature layoutFeature = fp.getLayoutFeature(layoutContext);
		layoutFeature.layout(layoutContext);
	}
}

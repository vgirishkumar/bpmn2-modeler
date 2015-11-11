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

package org.eclipse.bpmn2.modeler.ui.features.activity.subprocess;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.FlowElementsContainer;
import org.eclipse.bpmn2.Participant;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.di.BPMNPlane;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.core.features.choreography.ChoreographyUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.ui.ImageProvider;
import org.eclipse.dd.di.DiagramElement;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.osgi.util.NLS;

/**
 * @author Bob Brodt
 *
 */
public class PullupFeature extends AbstractPushPullFeature {

	protected String description;
	protected ContainerShape containerShape;
	protected FlowElementsContainer businessObject;
	protected Diagram oldDiagram;
	protected Diagram newDiagram;
	protected BPMNDiagram oldBpmnDiagram;
	protected BPMNDiagram newBpmnDiagram;
	protected BPMNShape bpmnShape;
	protected Rectangle boundingRectangle = null;
	protected List<DiagramElement> diagramElements = new ArrayList<DiagramElement>();
	protected List<Shape> childShapes = new ArrayList<Shape>();
	protected List<Connection> childConnections = new ArrayList<Connection>();

	/**
	 * @param fp
	 */
	public PullupFeature(IFeatureProvider fp) {
		super(fp);
	}
	
	@Override
	public String getName() {
	    return Messages.PullupFeature_Name;
	}
	
	@Override
	public String getDescription() {
		if (description==null)
			description = Messages.PullupFeature_Description;
		return description;
	}

	@Override
	public String getImageId() {
		return ImageProvider.IMG_16_PULLUP;
	}

	@Override
	public boolean isAvailable(IContext context) {
		if (context instanceof ICustomContext) {
			PictogramElement[] pes = ((ICustomContext)context).getPictogramElements();
			if (pes != null && pes.length == 1) {
				PictogramElement pe = pes[0];
				if (!ChoreographyUtil.isChoreographyParticipantBand(pe)) {
					return true;
				}
			}
		}
		return false;
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		PictogramElement[] pes = context.getPictogramElements();
		if (pes != null && pes.length == 1 && pes[0] instanceof ContainerShape) {
			containerShape = (ContainerShape) pes[0];
			if (ChoreographyUtil.isChoreographyParticipantBand(containerShape))
				return false;
			
			Object bo = getBusinessObjectForPictogramElement(containerShape);
			description = NLS.bind(
				Messages.PullupFeature_Description_1,
				ModelUtil.getLabel(bo)
			);
			
			if (bo instanceof Participant) {
				Diagram diagram = Graphiti.getPeService().getDiagramForShape(containerShape);
				boolean isReference = FeatureSupport.isParticipantReference(diagram, (Participant)bo);
				if (isReference)
					return false;
				bo = ((Participant)bo).getProcessRef();
			}
			if (bo instanceof FlowElementsContainer) {
				return FeatureSupport.hasBpmnDiagram((FlowElementsContainer)bo);
			}
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.features.custom.ICustomFeature#execute(org.eclipse.graphiti.features.context.ICustomContext)
	 */
	@Override
	public void execute(ICustomContext context) {
		// we already know there's one and only one PE element in canExecute() and that it's
		// a ContainerShape for an expandable activity
		containerShape = (ContainerShape)context.getPictogramElements()[0];

		// get the BPMNDiagram that this container lives in..
		newBpmnDiagram = DIUtils.findBPMNDiagram(containerShape);
		// ...and its Graphiti Diagram parent
		newDiagram = Graphiti.getPeService().getDiagramForShape(containerShape);
		
		// get the BPMNShape for the container
		bpmnShape = null;
		Object bo = getBusinessObjectForPictogramElement(containerShape);
		if (bo instanceof Participant) {
			bpmnShape = DIUtils.findBPMNShape(newBpmnDiagram, (Participant)bo);
			businessObject = ((Participant)bo).getProcessRef();
		}
		else if (bo instanceof FlowElementsContainer) {
			bpmnShape = DIUtils.findBPMNShape(newBpmnDiagram, (FlowElementsContainer)bo);
			businessObject = (FlowElementsContainer)bo;
		}
		
		// find the BPMNDiagram that contains the Process being pulled up...
		oldBpmnDiagram = DIUtils.findBPMNDiagram(businessObject);
		// ...and its Graphiti Diagram
		oldDiagram = DIUtils.findDiagram(getDiagramBehavior(), oldBpmnDiagram);

		// copy the elements into the same plane as the container
		collectDiagramElements(businessObject, oldBpmnDiagram, diagramElements);
		moveDiagramElements(diagramElements, oldBpmnDiagram, newBpmnDiagram);
		
		// collect all Shapes from old Diagram...
		collectShapes(oldDiagram, childShapes);
		// ...calculate a bounding rectangle which defines the size of the target container...
		boundingRectangle = calculateBoundingRectangle(containerShape, childShapes);
		// ...and move shapes from diagram into container
		moveShapes(childShapes, oldDiagram, containerShape, boundingRectangle.x, boundingRectangle.y);
		// update bounding rectangle after the move
//		boundingRectangle = GraphicsUtil.getBoundingRectangle(childShapes);

		// collect all internal Connections from old Diagram and move to new Diagram
		collectConnections(oldDiagram, childConnections);
		moveConnections(childConnections, oldDiagram, containerShape);
	
		// move associated Graphiti data structures
		moveGraphitiData(childShapes, childConnections, oldDiagram, newDiagram);
		
		// get rid of the old BPMNDiagram and its Graphiti Diagram
		DIUtils.deleteDiagram(getDiagramBehavior(), oldBpmnDiagram);
		
		// tell feature provider to reload the active diagram
		getFeatureProvider().getDiagramTypeProvider().resourceReloaded(newDiagram);
		
		FeatureSupport.updateConnections(getFeatureProvider(), childConnections, true);

		// expand the sub process
		if (FeatureSupport.isExpandableElement(businessObject)) {
			bpmnShape.setIsExpanded(false);
			ExpandFlowNodeFeature expandFeature = new ExpandFlowNodeFeature(getFeatureProvider());
			expandFeature.execute(context);
		}
	}

	protected void collectDiagramElements(FlowElementsContainer businessObject, BPMNDiagram source, List<DiagramElement> diagramElements) {
		diagramElements.addAll(source.getPlane().getPlaneElement());
	}

	protected void collectShapes(ContainerShape source, List<Shape> shapes) {
		shapes.addAll(source.getChildren());
	}

	protected void collectConnections(ContainerShape source, List<Connection> connections) {
		Diagram sourceDiagram = Graphiti.getPeService().getDiagramForShape(source);
		connections.addAll(sourceDiagram.getConnections());
	}

	protected void moveGraphitiData(List<Shape> shapes, List<Connection> connections, Diagram source, Diagram target) {
		target.getPictogramLinks().addAll(source.getPictogramLinks());
		target.getColors().addAll(source.getColors());
		target.getFonts().addAll(source.getFonts());
		target.getStyles().addAll(source.getStyles());
	}

	protected Rectangle calculateBoundingRectangle(ContainerShape containerShape, List<Shape> childShapes) {
		// calculate bounding rectangle for all children shapes
		Rectangle rect = GraphicsUtil.getBoundingRectangle(childShapes);
		// Shapes added to Pools will have different offsets depending on
		// whether the Pool is horizontal or vertical
		Point offset = getChildOffset(containerShape);
		rect.x -= MARGIN + offset.getX();
		rect.y -= MARGIN + offset.getY();
		rect.width += 2 * MARGIN + offset.getX();
		rect.height += 2 * MARGIN + offset.getY();
		return rect;
	}
}

package org.eclipse.bpmn2.modeler.ui.features.activity.subprocess;

import java.util.List;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.FlowElementsContainer;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.di.BPMNEdge;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.dd.di.DiagramElement;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.graphiti.datatypes.ILocation;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.context.impl.LayoutContext;
import org.eclipse.graphiti.features.context.impl.MoveShapeContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;

public abstract class AbstractPushPullFeature extends AbstractCustomFeature {

	public final static int MARGIN = 20;

	public AbstractPushPullFeature(IFeatureProvider fp) {
		super(fp);
	}

	abstract protected void collectDiagramElements(FlowElementsContainer businessObject, BPMNDiagram source, List<DiagramElement> diagramElements);
	abstract protected void collectShapes(ContainerShape source, List<Shape> shapes);
	abstract protected void collectConnections(ContainerShape source, List<Connection> connections);

	protected void moveDiagramElements(List<DiagramElement> diagramElements, BPMNDiagram source, BPMNDiagram target) {
		for (DiagramElement sourceDe : diagramElements) {
			BaseElement be = null;
			if (sourceDe instanceof BPMNEdge) {
				be = ((BPMNEdge)sourceDe).getBpmnElement();
			}
			else if (sourceDe instanceof BPMNShape) {
				be = ((BPMNShape)sourceDe).getBpmnElement();
			}
			
			boolean moveIt = true;
			// is there an element on the target diagram that has the same business object?
			for (DiagramElement targetDe : target.getPlane().getPlaneElement()) {
				if (sourceDe instanceof BPMNEdge && targetDe instanceof BPMNEdge) {
					if (be == ((BPMNEdge)targetDe).getBpmnElement()) {
						moveIt = false;
					}
				}
				else if (sourceDe instanceof BPMNShape && targetDe instanceof BPMNShape) {
					if (be == ((BPMNShape)targetDe).getBpmnElement()) {
						moveIt = false;
					}
				}
			}
			if (moveIt)
				target.getPlane().getPlaneElement().add(sourceDe);
		}
	}

	protected void moveShapes(List<Shape> children, ContainerShape source, ContainerShape target, int xOffset, int yOffset) {
		for (Shape s : children) {
			if (s instanceof ContainerShape && FeatureSupport.hasBPMNShape((ContainerShape)s)) {
				ILocation loc = Graphiti.getPeService().getLocationRelativeToDiagram(s);
				int x = loc.getX() - xOffset;
				int y = loc.getY() - yOffset;
				MoveShapeContext moveContext = new MoveShapeContext(s);
				moveContext.setSourceContainer(source);
				moveContext.setTargetContainer(target);
				moveContext.setLocation(x, y);
				
				IMoveShapeFeature moveFeature = getFeatureProvider().getMoveShapeFeature(moveContext);
				moveFeature.moveShape(moveContext);
				layoutIfNecessary((ContainerShape)s);
			}
			else {
				target.getChildren().add(s);
			}
		}
	}

	protected void moveConnections(List<Connection> connections, ContainerShape source, ContainerShape target) {
		Diagram targetDiagram = Graphiti.getPeService().getDiagramForShape(target);
		targetDiagram.getConnections().addAll(connections);
	}
	
	protected abstract void moveGraphitiData(List<Shape> shapes, List<Connection> connections, Diagram source, Diagram target);
	
	protected abstract Rectangle calculateBoundingRectangle(ContainerShape containerShape, List<Shape> childShapes);
	
	protected Point getChildOffset(ContainerShape targetContainerShape) {
		return GraphicsUtil.createPoint(0, 0);
	}

	protected boolean isDescendant(ContainerShape containerShape, PictogramElement shape) {
		while (shape!=null && !(shape instanceof Diagram)) {
			if (shape==containerShape)
				return true;
			if (shape.eContainer() instanceof ContainerShape)
				shape = (ContainerShape) shape.eContainer();
			else
				break;
		}
		return shape==containerShape;
	}
	
	protected boolean isInternalConnection(ContainerShape containerShape, AnchorContainer shape, Connection c) {
		AnchorContainer otherShape = null;
		if (c.getStart().getParent()==shape) {
			otherShape = c.getEnd().getParent();
		}
		else if (c.getEnd().getParent()==shape) {
			otherShape = c.getStart().getParent();
		}
		if (otherShape instanceof AnchorContainer) {
			AnchorContainer otherContainerShape = (AnchorContainer)otherShape;
			while (!(otherContainerShape instanceof Diagram)) {
				if (otherContainerShape == containerShape)
					return true;
				if (otherContainerShape.eContainer() instanceof AnchorContainer)
					otherContainerShape = (AnchorContainer)otherContainerShape.eContainer();
				else
					break;
			}
		}
		return false;
	}

	protected void layoutIfNecessary(ContainerShape shape) {
		FlowElementsContainer fec = BusinessObjectUtil.getFirstElementOfType(shape, FlowElementsContainer.class);
		if (fec!=null) {
			BPMNDiagram bpmnDiagram = DIUtils.findBPMNDiagram(shape);
			if (bpmnDiagram!=null) {
				BPMNShape bpmnShape = DIUtils.findBPMNShape(bpmnDiagram, fec);
				if (bpmnShape!=null) {
					LayoutContext layoutContext = new LayoutContext(shape);
					ILayoutFeature layoutFeature = getFeatureProvider().getLayoutFeature(layoutContext);
					layoutFeature.layout(layoutContext);
				}
			}
		}
	}
}

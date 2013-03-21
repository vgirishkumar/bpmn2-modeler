package org.eclipse.bpmn2.modeler.core.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.FlowElementsContainer;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.Lane;
import org.eclipse.bpmn2.LaneSet;
import org.eclipse.bpmn2.Participant;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.dd.dc.Bounds;
import org.eclipse.dd.dc.DcFactory;
import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.context.impl.MoveShapeContext;
import org.eclipse.graphiti.features.context.impl.ResizeContext;
import org.eclipse.graphiti.features.context.impl.ResizeShapeContext;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.editor.DiagramEditor;

public class ShapeLayoutManager {

	private static final int HORZ_PADDING = 50;
	private static final int VERT_PADDING = 50;
	private DiagramEditor editor;
	
	public ShapeLayoutManager(DiagramEditor editor) {
		this.editor = editor;
	}

	public void layout(BaseElement container) {
		layout( getContainerShape(container) );
	}
	
	public void layout(ContainerShape container) {
		
		List<ContainerShape> childShapes = new ArrayList<ContainerShape>();
		for (PictogramElement pe : container.getChildren()) {
			if (pe instanceof ContainerShape && !GraphicsUtil.isLabelShape((Shape)pe)) {
				ContainerShape childContainer = (ContainerShape)pe;
				boolean hasChildren = false;
				for (Shape shape : childContainer.getChildren()) {
					if (shape instanceof ContainerShape) {
						hasChildren = true;
						break;
					}
				}
				if (hasChildren)
					layout(childContainer);
				childShapes.add((ContainerShape)pe);
			}
		}

		// layout child shapes from right to left;
		// shapes are sorted into bins according to the number of incoming
		// and ougtoing SequenceFlow connections:
		// 1. shapes that have only outgoing connections are added to the startShapes bin
		// 2. shapes with only incoming connections are tossed into the endShapes bin
		// 3. shapes with both incoming and outgoing connections are in the middleShapes bin
		// 4. shapes with no connections are in the unconnectShapes bin
		List<ContainerShape> startShapes = new ArrayList<ContainerShape>();
		List<ContainerShape> unconnectedShapes = new ArrayList<ContainerShape>();
		List<ContainerShape> middleShapes = new ArrayList<ContainerShape>();
		List<ContainerShape> endShapes = new ArrayList<ContainerShape>();
		for (ContainerShape child : childShapes) {
			List<SequenceFlow> incomingFlows = getIncomingSequenceFlows(child);
			List<SequenceFlow> outgoingFlows = getOutgoingSequenceFlows(child);
			int incomingCount = 0;
			int outgoingCount = 0;

			// this may be a start or end shape depending on whether ALL of the incoming
			// our outgoing flows are from/to shapes that are in this container.
			for (SequenceFlow sf : incomingFlows) {
				ContainerShape shape = getContainerShape(sf.getSourceRef());
				if (childShapes.contains(shape) && shape!=child)
					++incomingCount;
			}
			for (SequenceFlow sf : outgoingFlows) {
				ContainerShape shape = getContainerShape(sf.getTargetRef());
				if (childShapes.contains(shape) && shape!=child)
					++outgoingCount;
			}

			if (incomingCount==0) {
				if (outgoingCount==0)
					unconnectedShapes.add(child);
				else
					startShapes.add(child);
			}
			else if (outgoingCount==0) {
				endShapes.add(child);
			}
			else {
				middleShapes.add(child);
			}
		}
		
		// now build threads of sequence flows starting with all of the startShapes
		List<List<ContainerShape[]>> threads = new ArrayList<List<ContainerShape[]>>();
		if (startShapes.size()>0) {
			for (ContainerShape child : startShapes) {
				List<ContainerShape[]> thread = new ArrayList<ContainerShape[]>();
				thread.add(new ContainerShape[] {child});
				buildThread(child, childShapes, thread);
				threads.add(thread);
			}
		}
		
		// arrange the threads
		int x = HORZ_PADDING;
		int y = VERT_PADDING;
		int maxHeight = VERT_PADDING;
		int maxWidth = 0;
		
		for (List<ContainerShape[]> thread : threads) {
			// stack the threads on top of each other
			x = HORZ_PADDING;
			int maxThreadHeight = 0;
			for (ContainerShape[] shapes : thread) {
				for (ContainerShape shape : shapes) {
					moveShape(container, shape, x, y);
					IDimension size = GraphicsUtil.calculateSize(shape);
					if (size.getHeight() > maxThreadHeight)
						maxThreadHeight = size.getHeight();
					x += size.getWidth() + HORZ_PADDING;
					if (x > maxWidth)
						maxWidth = x;
				}
			}
			y += maxThreadHeight + VERT_PADDING;
			maxHeight += maxThreadHeight + VERT_PADDING;
		}
		
		// now resize the container so that all children are visible
		if (!(container instanceof Diagram)) {
			resizeShape(container, maxWidth, maxHeight);
		}

		stackShapes(container, unconnectedShapes);
		if (startShapes.size()==0 && endShapes.size()==0 && middleShapes.size()>0)
			stackShapes(container, middleShapes);
	}
	
	private void stackShapes(ContainerShape container, List<ContainerShape> unconnectedShapes) {
		// stack any unconnected shapes on top of each other
		// first stack shapes that are NOT containers (like DataObject, DataStore, etc.)
		int maxWidth = 0;
		int maxHeight = 0;
		int x = HORZ_PADDING;
		int y = VERT_PADDING;
		if (unconnectedShapes.size()>0) {
			for (ContainerShape shape : unconnectedShapes) {
				if (getChildren(shape).size()==0) {
					moveShape(container, shape, x, y);
					IDimension size = GraphicsUtil.calculateSize(shape);
					y += size.getHeight() + VERT_PADDING;
					if (size.getWidth() > maxWidth)
						maxWidth = size.getWidth();
				}
			}
			if (y>maxHeight)
				maxHeight = y;
		
			// now handle all containers (Lane, SubProcess, Pool, etc.)
			x += maxWidth + HORZ_PADDING;
			y = VERT_PADDING;
			for (ContainerShape shape : unconnectedShapes) {
				if (getChildren(shape).size()!=0) {
					moveShape(container, shape, x, y);
					IDimension size = GraphicsUtil.calculateSize(shape);
					y += size.getHeight() + VERT_PADDING;
					if (size.getWidth() > maxWidth)
						maxWidth = size.getWidth();
				}
			}
			if (y>maxHeight)
				maxHeight = y;
		}
		
//		if (!(container instanceof Diagram)) {
//			resizeShape(container, maxWidth, maxHeight);
//		}
	}
	
	private void moveShape(ContainerShape container, ContainerShape shape, int x, int y) {
		MoveShapeContext context = new MoveShapeContext(shape);
		context.setLocation(x, y);
		context.setSourceContainer(container);
		context.setTargetContainer(container);
		IMoveShapeFeature moveFeature = editor.getDiagramTypeProvider().getFeatureProvider().getMoveShapeFeature(context);
		if (moveFeature.canMoveShape(context)) {
			moveFeature.moveShape(context);
		}
	}
	
	private void resizeShape(ContainerShape container, int width, int height) {
		ResizeShapeContext context = new ResizeShapeContext(container);
		int x = container.getGraphicsAlgorithm().getX();
		int y = container.getGraphicsAlgorithm().getY();
		context.setLocation(x, y);
		context.setSize(width, height);
		IResizeShapeFeature resizeFeature = editor.getDiagramTypeProvider().getFeatureProvider().getResizeShapeFeature(context);
		if (resizeFeature.canResizeShape(context)) {
			resizeFeature.resizeShape(context);
		}
	}
	
	private boolean threadContains(List<ContainerShape[]> thread, ContainerShape shape) {
		for (ContainerShape[] shapes : thread) {
			for (ContainerShape s : shapes) {
				if (s==shape)
					return true;
			}
		}
		return false;
	}
	
	private void buildThread(ContainerShape shape, List<ContainerShape> childShapes, List<ContainerShape[]> thread) {
		List<ContainerShape> bin = new ArrayList<ContainerShape>();
		List<SequenceFlow> flows = getOutgoingSequenceFlows(shape);
		for (SequenceFlow flow : flows) {
			FlowNode target = flow.getTargetRef();
			// make sure the target shape is also a child of this container
			// in case a SequenceFlow crosses the container boundary
			ContainerShape targetShape = getContainerShape(target);
			if (childShapes.contains(targetShape) && !threadContains(thread, targetShape)) {
				bin.add(targetShape);
			}
		}
		if (!bin.isEmpty()) {
			thread.add(bin.toArray(new ContainerShape[bin.size()]));
			for (ContainerShape nextShape : bin) {
				buildThread(nextShape, childShapes, thread);
			}
		}
	}
	
	private List<SequenceFlow> getIncomingSequenceFlows(ContainerShape shape) {
		List<SequenceFlow> flows = new ArrayList<SequenceFlow>();
		for (Anchor a : shape.getAnchors()) {
			for (Connection c : a.getIncomingConnections()) {
				BaseElement be = BusinessObjectUtil.getFirstBaseElement(c);
				if (be instanceof SequenceFlow) {
					flows.add((SequenceFlow)be);
				}
			}
		}
		return flows;
	}
	
	private List<SequenceFlow> getOutgoingSequenceFlows(ContainerShape shape) {
		List<SequenceFlow> flows = new ArrayList<SequenceFlow>();
		for (Anchor a : shape.getAnchors()) {
			for (Connection c : a.getOutgoingConnections()) {
				BaseElement be = BusinessObjectUtil.getFirstBaseElement(c);
				if (be instanceof SequenceFlow) {
					flows.add((SequenceFlow)be);
				}
			}
		}
		return flows;
	}
	
	private ContainerShape getContainerShape(BaseElement be) {
		Diagram diagram = null;
		BPMNDiagram bpmnDiagram = DIUtils.findBPMNDiagram(editor, be, true);
		if (bpmnDiagram != null) {
			diagram = DIUtils.findDiagram(editor, bpmnDiagram);
		}
		if (diagram!=null) {
			List<PictogramElement> list = Graphiti.getLinkService().getPictogramElements(diagram, be);
			for (PictogramElement pe : list) {
				if (pe instanceof ContainerShape && !GraphicsUtil.isLabelShape((Shape)pe)) {
					if (BusinessObjectUtil.getFirstBaseElement(pe) == be)
						return (ContainerShape)pe;
				}
			}
			
			// maybe the BaseElement is a root element (like a Process or Choreography)?
			if (bpmnDiagram.getPlane().getBpmnElement() == be)
				return diagram;
		}
		return null;
	}

	private List<ContainerShape> getChildren(ContainerShape container) {
		
		List<ContainerShape> childShapes = new ArrayList<ContainerShape>();
		for (PictogramElement pe : container.getChildren()) {
			if (pe instanceof ContainerShape && !GraphicsUtil.isLabelShape((Shape)pe)) {
				childShapes.add((ContainerShape)pe);
			}
		}
		
		return childShapes;
	}
}

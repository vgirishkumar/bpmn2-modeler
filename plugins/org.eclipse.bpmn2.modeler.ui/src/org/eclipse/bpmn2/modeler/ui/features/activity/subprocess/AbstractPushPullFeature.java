package org.eclipse.bpmn2.modeler.ui.features.activity.subprocess;

import java.util.List;

import org.eclipse.bpmn2.FlowElementsContainer;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.graphiti.datatypes.ILocation;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.context.impl.LayoutContext;
import org.eclipse.graphiti.features.context.impl.MoveShapeContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;

public abstract class AbstractPushPullFeature extends AbstractCustomFeature {

	public final static int MARGIN = 20;

	public AbstractPushPullFeature(IFeatureProvider fp) {
		super(fp);
	}

	protected void moveChildren(List<Shape> children, ContainerShape oldContainer, ContainerShape newContainer, int xOffset, int yOffset) {
		for (Shape s : children) {
			if (s instanceof ContainerShape && FeatureSupport.hasBPMNShape((ContainerShape)s)) {
				ILocation loc = Graphiti.getPeService().getLocationRelativeToDiagram(s);
				int x = loc.getX() - xOffset;
				int y = loc.getY() - yOffset;
				MoveShapeContext moveContext = new MoveShapeContext(s);
				moveContext.setSourceContainer(oldContainer);
				moveContext.setTargetContainer(newContainer);
				moveContext.setLocation(x, y);
				
				IMoveShapeFeature moveFeature = getFeatureProvider().getMoveShapeFeature(moveContext);
				moveFeature.moveShape(moveContext);
				layoutIfNecessary((ContainerShape)s);
			}
			else {
				newContainer.getChildren().add(s);
			}
		}
	}

	private void layoutIfNecessary(ContainerShape shape) {
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
	
//	private void moveChildAndAncestors(ContainerShape shape, ContainerShape oldContainer, ContainerShape newContainer, int xOffset, int yOffset) {
//		GraphicsUtil.dump("  Container ", shape);
//		ILocation loc = Graphiti.getPeService().getLocationRelativeToDiagram(shape);
//		int x = loc.getX() - xOffset;
//		int y = loc.getY() - yOffset;
//		MoveShapeContext moveContext = new MoveShapeContext(shape);
//		moveContext.setSourceContainer(oldContainer);
//		moveContext.setTargetContainer(newContainer);
//		moveContext.setLocation(x, y);
//		
//		IMoveShapeFeature moveFeature = getFeatureProvider().getMoveShapeFeature(moveContext);
//		moveFeature.moveShape(moveContext);
//		
//		List<Shape> copies = new ArrayList<Shape>();
//		copies.addAll(shape.getChildren());
//
//		for (Shape s : copies) {
//			if (FeatureSupport.hasBPMNShape(s)) {
//				moveChildAndAncestors((ContainerShape)s, newContainer, newContainer, 0, 0);
//			}
//		}
//	}
	
	protected Point getChildOffset(ContainerShape targetContainerShape) {
		return GraphicsUtil.createPoint(0, 0);
	}

}

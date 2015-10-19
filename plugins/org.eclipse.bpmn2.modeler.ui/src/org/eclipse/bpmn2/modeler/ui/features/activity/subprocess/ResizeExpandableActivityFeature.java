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

import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.modeler.core.Activator;
import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.core.features.DefaultResizeBPMNShapeFeature;
import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.preferences.ShapeStyle;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.graphiti.datatypes.ILocation;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IResizeShapeContext;
import org.eclipse.graphiti.features.context.impl.ResizeShapeContext;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;

public class ResizeExpandableActivityFeature extends DefaultResizeBPMNShapeFeature {
	public final static int MARGIN = 20;
	
	protected Bpmn2Preferences preferences;
	
	public ResizeExpandableActivityFeature(IFeatureProvider fp) {
		super(fp);
		preferences = Bpmn2Preferences.getInstance(getDiagram());
	}
	
	@Override
	public void resizeShape(IResizeShapeContext context) {

		ResizeShapeContext resizeShapeContext = (ResizeShapeContext)context;

		ContainerShape containerShape = (ContainerShape) context.getPictogramElement();
		Activity activity = BusinessObjectUtil.getFirstElementOfType(containerShape, Activity.class);
		ShapeStyle ss = preferences.getShapeStyle(activity);
		
		List<AnchorContainer> movedChildren = new ArrayList<AnchorContainer>();
		List<PictogramElement> containerChildren = FeatureSupport.getContainerChildren(containerShape);
		try {
			BPMNDiagram bpmnDiagram = DIUtils.findBPMNDiagram(containerShape);
			BPMNShape shape = DIUtils.findBPMNShape(bpmnDiagram, activity);
			
			if (shape.isIsExpanded()) {

				// SubProcess is expanded
				
				SizeCalculator sizeCalc = new SizeCalculator(resizeShapeContext);
				int deltaX = sizeCalc.deltaX;
				int deltaY = sizeCalc.deltaY;
				
				if (deltaX != 0) {
					for (PictogramElement pe : containerChildren) {
						GraphicsAlgorithm childGa = pe.getGraphicsAlgorithm();
						if (childGa!=null) {
							int x = childGa.getX() - deltaX;
							childGa.setX(x);
							if (pe instanceof ContainerShape)
								movedChildren.add((ContainerShape)pe);
						}
					}
				}
				
				if (deltaY != 0) {
					for (PictogramElement pe : containerChildren) {
						GraphicsAlgorithm childGa = pe.getGraphicsAlgorithm();
						if (childGa!=null) {
							int y = childGa.getY() - deltaY;
							childGa.setY(y);
							if (!movedChildren.contains(pe) && pe instanceof ContainerShape)
								movedChildren.add((ContainerShape)pe);
						}
					}
				}
			}
			else {
				
				// SubProcess is collapsed
				
				for (PictogramElement pe : FeatureSupport.getContainerDecorators(containerShape)) {
					GraphicsAlgorithm childGa = pe.getGraphicsAlgorithm();
					if (childGa!=null) {
						childGa.setWidth(context.getWidth());
						childGa.setHeight(context.getHeight());
					}
				}
			}
			
		} catch (Exception e) {
			Activator.logError(e);
		}
		
		super.resizeShape(context);

		FeatureSupport.updateConnections(getFeatureProvider(), movedChildren);
	}
	
	public static class SizeCalculator {
		
		int deltaX;
		int deltaY;
		int deltaWidth;
		int deltaHeight;
		int minWidth;
		int minHeight;
		ContainerShape containerShape;
		ResizeShapeContext context;
		ShapeStyle ss;
		
		public SizeCalculator(ResizeShapeContext context) {
			this.context = context;
			setShape((ContainerShape) context.getPictogramElement());
		}
		
		private void setShape(ContainerShape containerShape) {
			this.containerShape = containerShape;
			Bpmn2Preferences preferences = Bpmn2Preferences.getInstance(containerShape.eResource());
			ss = preferences.getShapeStyle(BusinessObjectUtil.getFirstBaseElement(containerShape));
			calculate();
		}
		
		private ILocation getLocationRelativeToContainer(ContainerShape parent, ContainerShape child) {
			ILocation loc = Graphiti.getPeService().getLocationRelativeToDiagram(child);
			ContainerShape container = parent.getContainer();
			if (container instanceof ContainerShape && !(container instanceof Diagram)) {
				ILocation containerLoc = Graphiti.getPeService().getLocationRelativeToDiagram(container);
				loc.setX(loc.getX() - containerLoc.getX());
				loc.setY(loc.getY() - containerLoc.getY());
			}
			return loc;
		}
		
		private void calculate() {
			int minX = Integer.MAX_VALUE;
			int minY = Integer.MAX_VALUE;
			int minChildX = Integer.MAX_VALUE;
			int minChildY = Integer.MAX_VALUE;
			minWidth = 0;
			minHeight = 0;
			List<PictogramElement> containerChildren = FeatureSupport.getContainerChildren(containerShape);
			GraphicsAlgorithm ga;

			for (PictogramElement pe : containerChildren) {
				ga = pe.getGraphicsAlgorithm();
				if (ga!=null) {
					if (ga.getX() < minChildX)
						minChildX = ga.getX();
					if (ga.getY() < minChildY)
						minChildY = ga.getY();
					ILocation loc = getLocationRelativeToContainer(containerShape, (ContainerShape)pe);
					int x = loc.getX();
					int y = loc.getY();
					if (x < minX)
						minX = x;
					if (y < minY)
						minY = y;
				}
			}
			
			for (PictogramElement pe : containerChildren) {
				ga = pe.getGraphicsAlgorithm();
				if (ga!=null) {
					ILocation loc = getLocationRelativeToContainer(containerShape, (ContainerShape)pe);
					int w = loc.getX() - minX + ga.getWidth();
					int h = loc.getY() - minY + ga.getHeight();
					if (w > minWidth)
						minWidth = w;
					if (h > minHeight)
						minHeight = h;
				}
			}
			
			if (minWidth<=0)
				minWidth = ss.getDefaultWidth();
			if (minHeight<=0)
				minHeight = ss.getDefaultHeight();
			
			minX -= MARGIN;
			minY -= MARGIN;
			minWidth += 2*MARGIN;
			minHeight += 2*MARGIN;
			
			ga = containerShape.getGraphicsAlgorithm();

			if (context.getX()>minX) {
				int dx0 = context.getX() - ga.getX();
				context.setX(minX);
				int dx1 = context.getX() - ga.getX();
				context.setWidth(context.getWidth() - (dx1 - dx0));
			}
			if (context.getY()>minY) {
				int dy0 = context.getY() - ga.getY();
				context.setY(minY);
				int dy1 = context.getY() - ga.getY();
				context.setHeight(context.getHeight() - (dy1 - dy0));
			}
			
			if (context.getX() != ga.getX())
				deltaX = context.getX() - ga.getX();
			else if (context.getWidth()<minWidth + minChildX - MARGIN)
				context.setWidth(minWidth + minChildX - MARGIN);
			if (context.getY() != ga.getY())
				deltaY = context.getY() - ga.getY();
			else if (context.getHeight()<minHeight + minChildY - MARGIN)
				context.setHeight(minHeight + minChildY - MARGIN);
		}
		
		public int getWidth() {
			return minWidth;
		}
		
		public int getHeight() {
			return minHeight;
		}
	}
}

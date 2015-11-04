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

import java.util.List;

import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.FlowElementsContainer;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.di.BPMNPlane;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.core.features.activity.LayoutActivityFeature;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.dd.di.DiagramElement;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.context.impl.LayoutContext;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

public class LayoutExpandableActivityFeature extends LayoutActivityFeature {

	public LayoutExpandableActivityFeature(IFeatureProvider fp) {
		super(fp);
	}
	
	@Override
	public boolean layout(ILayoutContext context) {
		ContainerShape containerShape = (ContainerShape) context.getPictogramElement();
		Activity activity = BusinessObjectUtil.getFirstElementOfType(containerShape, Activity.class);
		try {
			BPMNDiagram bpmnDiagram = DIUtils.findBPMNDiagram(containerShape);
			BPMNShape bpmnShape = DIUtils.findBPMNShape(bpmnDiagram, activity);
			
			boolean setChildrenVisible = bpmnShape.isIsExpanded();
			if (activity instanceof FlowElementsContainer) {
				FlowElementsContainer container = (FlowElementsContainer) activity;
				for (FlowElement fe : container.getFlowElements()) {
					DiagramElement de = DIUtils.findDiagramElement(fe);
					if (de!=null) {
						BPMNPlane plane = (BPMNPlane) de.eContainer();
						if (bpmnDiagram != plane.eContainer()) {
							setChildrenVisible = true;
							break;
						}
					}
				}
			}

			FeatureSupport.setContainerChildrenVisible(getFeatureProvider(), containerShape, setChildrenVisible);
		} catch (Exception e) {
			// It's OK, I've played a programmer before...
			// e.printStackTrace();
		}
		
		return super.layout(context);
	}
}

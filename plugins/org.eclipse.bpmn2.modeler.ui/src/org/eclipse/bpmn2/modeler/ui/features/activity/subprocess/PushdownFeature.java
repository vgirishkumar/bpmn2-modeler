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

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.FlowElementsContainer;
import org.eclipse.bpmn2.Lane;
import org.eclipse.bpmn2.LaneSet;
import org.eclipse.bpmn2.Participant;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.di.BPMNEdge;
import org.eclipse.bpmn2.di.BPMNPlane;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.core.features.choreography.ChoreographyUtil;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.ui.ImageProvider;
import org.eclipse.dd.di.DiagramElement;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.impl.LayoutContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.PictogramLink;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.osgi.util.NLS;

/**
 * @author Bob Brodt
 *
 */
public class PushdownFeature extends AbstractCustomFeature {

	protected String description;
	protected ContainerShape containerShape;
	protected FlowElementsContainer businessObject;
	protected BPMNDiagram bpmnDiagram;
	protected BPMNShape bpmnShape;
	protected List<BaseElement> childElements = new ArrayList<BaseElement>();

	
	/**
	 * @param fp
	 */
	public PushdownFeature(IFeatureProvider fp) {
		super(fp);
	}
	
	@Override
	public String getName() {
	    return Messages.PushdownFeature_Name;
	}
	
	@Override
	public String getDescription() {
		if (description==null)
			description = Messages.PushdownFeature_Description;
		return description;
	}

	@Override
	public String getImageId() {
		return ImageProvider.IMG_16_PUSHDOWN;
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
		if (pes != null && pes.length == 1) {
			PictogramElement pe = pes[0];
			if (ChoreographyUtil.isChoreographyParticipantBand(pe))
				return false;
			Object bo = getBusinessObjectForPictogramElement(pe);
			description = NLS.bind(Messages.PushdownFeature_Description_1,ModelUtil.getLabel(bo));
			
			if (bo instanceof Participant) {
				if (FeatureSupport.isParticipantReference(getDiagram(), (Participant)bo))
					return false;
				bo = ((Participant)bo).getProcessRef();
			}
			if (bo instanceof FlowElementsContainer) {
				return DIUtils.findBPMNDiagram((BaseElement)bo) == null;
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
		Object bo = getBusinessObjectForPictogramElement(containerShape);
		bpmnDiagram = DIUtils.findBPMNDiagram(containerShape);
		bpmnShape = null;
		if (bo instanceof Participant) {
			bpmnShape = DIUtils.findBPMNShape(bpmnDiagram, (Participant)bo);
			businessObject = ((Participant)bo).getProcessRef();
		}
		else if (bo instanceof FlowElementsContainer) {
			businessObject = (FlowElementsContainer)bo;
			bpmnShape = DIUtils.findBPMNShape(bpmnDiagram, businessObject);
		}
		Definitions definitions = ModelUtil.getDefinitions(businessObject);
		
		BPMNDiagram oldBpmnDiagram = DIUtils.getBPMNDiagram(bpmnShape);
		Diagram oldDiagram = DIUtils.findDiagram(getDiagramBehavior(), oldBpmnDiagram);
		
		// the contents of this expandable element is in the flowElements list 
        BPMNDiagram newBpmnDiagram = DIUtils.createBPMNDiagram(definitions, businessObject);
		BPMNPlane newPlane = newBpmnDiagram.getPlane();

		Diagram newDiagram = DIUtils.getOrCreateDiagram(getDiagramBehavior(), newBpmnDiagram);
		List <EObject> removedObjects = new ArrayList<EObject>();
		
		collectChildElements(businessObject, childElements);

		List<Shape> shapes = new ArrayList<Shape>();
		List<Connection> connections = new ArrayList<Connection>();
		
		for (BaseElement be : childElements) {
			DiagramElement de = DIUtils.findDiagramElement(bpmnDiagram, be);
			if (de==null)
				continue; // Diagram Element does not exist
			
			newPlane.getPlaneElement().add(de);
			
			List <PictogramElement> pes = Graphiti.getLinkService().getPictogramElements(oldDiagram, be);
			for (PictogramElement pe : pes) {
				PictogramElement pictogramElement = null;
				if (pe instanceof ConnectionDecorator) {
					// this will be moved as part of the connection
					continue;
				}
				else if (pe instanceof Shape) {
					if (BusinessObjectUtil.getFirstElementOfType(pe, BPMNShape.class)!=null) {
						if (((Shape) pe).getContainer() == containerShape)
							newDiagram.getChildren().add((Shape)pe);
						pictogramElement = pe;
						shapes.add((Shape)pe);
					}
					else if (FeatureSupport.isLabelShape(pe)) {
						if (((Shape) pe).getContainer() == containerShape)
							newDiagram.getChildren().add((Shape)pe);
						pictogramElement = pe;
					}
				}
				else if (pe instanceof Connection) {
					if (BusinessObjectUtil.getFirstElementOfType(pe, BPMNEdge.class)!=null) {
						newDiagram.getConnections().add((Connection)pe);
						pictogramElement = pe;
						connections.add((Connection)pe);
					}
				}
				if (pictogramElement!=null) {
					TreeIterator<EObject> iter = pictogramElement.eAllContents();
					while (iter.hasNext()) {
						EObject o = iter.next();
						if (o instanceof PictogramLink) {
							newDiagram.getPictogramLinks().add((PictogramLink)o);
							removedObjects.add(o);
						}
//						else if (o instanceof Color) {
//							newDiagram.getColors().add((Color)o);
//							moved.add(o);
//						}
//						else if (o instanceof Font) {
//							newDiagram.getFonts().add((Font)o);
//							moved.add(o);
//						}
//						else if (o instanceof Style) {
//							newDiagram.getStyles().add((Style)o);
//							moved.add(o);
//						}
						
						pictogramElement.setVisible(true);
					}
				}
			}
		}
		oldDiagram.getPictogramLinks().removeAll(removedObjects);
//		oldDiagram.getColors().removeAll(moved);
//		oldDiagram.getFonts().removeAll(moved);
//		oldDiagram.getStyles().removeAll(moved);
		
		// collapse the sub process
		if (FeatureSupport.isExpandableElement(businessObject)) {
			bpmnShape.setIsExpanded(true);
			CollapseFlowNodeFeature collapseFeature = new CollapseFlowNodeFeature(getFeatureProvider());
			collapseFeature.execute(context);
		}

		// let the feature provider know there's a new diagram now
		getFeatureProvider().getDiagramTypeProvider().resourceReloaded(newDiagram);

		for (PictogramElement s : shapes) {
			LayoutContext layoutContext = new LayoutContext(s);
			ILayoutFeature layoutFeature = getFeatureProvider().getLayoutFeature(layoutContext);
			if (layoutFeature!=null && layoutFeature.canLayout(layoutContext)) {
				layoutFeature.layout(layoutContext);
				
			}
		}		
		
		for (Connection c : connections) {
			if (c instanceof FreeFormConnection) {
				FreeFormConnection ffc = (FreeFormConnection)c;
				ffc.getBendpoints().clear();
			}
		}
		for (Connection c : connections) {
			FeatureSupport.updateConnection(getFeatureProvider(), c, true);
		}
	}
	
	protected void collectChildElements(FlowElementsContainer container, List<BaseElement> children) {
		for (FlowElement fe : container.getFlowElements()) {
			children.add(fe);
			if (fe instanceof FlowElementsContainer) {
				collectChildElements((FlowElementsContainer)fe, children);
			}
		}
	}
}

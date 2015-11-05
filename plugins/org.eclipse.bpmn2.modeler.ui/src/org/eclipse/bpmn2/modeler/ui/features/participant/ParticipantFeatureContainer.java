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
package org.eclipse.bpmn2.modeler.ui.features.participant;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.FlowElementsContainer;
import org.eclipse.bpmn2.LaneSet;
import org.eclipse.bpmn2.MessageFlow;
import org.eclipse.bpmn2.Participant;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.modeler.core.features.BaseElementFeatureContainer;
import org.eclipse.bpmn2.modeler.core.features.CreateShapeReferenceFeature;
import org.eclipse.bpmn2.modeler.core.features.GraphitiConstants;
import org.eclipse.bpmn2.modeler.core.features.MultiUpdateFeature;
import org.eclipse.bpmn2.modeler.core.features.containers.LayoutContainerFeature;
import org.eclipse.bpmn2.modeler.core.features.containers.UpdateContainerLabelFeature;
import org.eclipse.bpmn2.modeler.core.features.containers.participant.AddParticipantFeature;
import org.eclipse.bpmn2.modeler.core.features.containers.participant.CreateParticipantReferenceFeature;
import org.eclipse.bpmn2.modeler.core.features.containers.participant.DirectEditParticipantFeature;
import org.eclipse.bpmn2.modeler.core.features.containers.participant.ResizeParticipantFeature;
import org.eclipse.bpmn2.modeler.core.features.containers.participant.UpdateParticipantFeature;
import org.eclipse.bpmn2.modeler.core.features.containers.participant.UpdateParticipantMultiplicityFeature;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.ui.features.activity.subprocess.PullupFeature;
import org.eclipse.bpmn2.modeler.ui.features.activity.subprocess.PushdownFeature;
import org.eclipse.bpmn2.modeler.ui.features.choreography.AddChoreographyMessageFeature;
import org.eclipse.bpmn2.modeler.ui.features.choreography.BlackboxFeature;
import org.eclipse.bpmn2.modeler.ui.features.choreography.RemoveChoreographyMessageFeature;
import org.eclipse.bpmn2.modeler.ui.features.choreography.RemoveChoreographyParticipantFeature;
import org.eclipse.bpmn2.modeler.ui.features.choreography.ShowDiagramPageFeature;
import org.eclipse.bpmn2.modeler.ui.features.choreography.WhiteboxFeature;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.datatypes.ILocation;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.IRemoveFeature;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.context.IMoveContext;
import org.eclipse.graphiti.features.context.IPictogramElementContext;
import org.eclipse.graphiti.features.context.IResizeContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.context.impl.CustomContext;
import org.eclipse.graphiti.features.context.impl.LayoutContext;
import org.eclipse.graphiti.features.context.impl.ResizeShapeContext;
import org.eclipse.graphiti.features.context.impl.UpdateContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;

public class ParticipantFeatureContainer extends BaseElementFeatureContainer {

	@Override
	public Object getApplyObject(IContext context) {
		if (
				context instanceof IUpdateContext ||
				context instanceof ILayoutContext ||
				context instanceof IMoveContext ||
				context instanceof IResizeContext
				) {
			PictogramElement pe = ((IPictogramElementContext)context).getPictogramElement();
			if (FeatureSupport.isLabelShape(pe))
				pe = (PictogramElement) pe.eContainer();
			if (FeatureSupport.isChoreographyParticipantBand(pe))
				return null;
		}
		Object o = super.getApplyObject(context);
		
		return o;
	}

	@Override
	public boolean canApplyTo(Object o) {
		return super.canApplyTo(o) && o instanceof Participant;
	}

	@Override
	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return new CreateParticipantFeature(fp);
	}

	@Override
	public IAddFeature getAddFeature(IFeatureProvider fp) {
		return new AddParticipantFeature(fp);
	}

	@Override
	public IUpdateFeature getUpdateFeature(IFeatureProvider fp) {
		MultiUpdateFeature multiUpdate = new MultiUpdateFeature(fp);
		multiUpdate.addFeature(new UpdateParticipantFeature(fp));
		multiUpdate.addFeature(new UpdateParticipantMultiplicityFeature(fp));
//		multiUpdate.addFeature(new UpdateChoreographyMessageLinkFeature(fp));
		multiUpdate.addFeature(new UpdateContainerLabelFeature(fp));
		return multiUpdate;
	}

	@Override
	public IDirectEditingFeature getDirectEditingFeature(IFeatureProvider fp) {
		return new DirectEditParticipantFeature(fp);
	}

	@Override
	public ILayoutFeature getLayoutFeature(IFeatureProvider fp) {
		return new LayoutContainerFeature(fp);
	}

	@Override
	public IMoveShapeFeature getMoveFeature(IFeatureProvider fp) {
		return new MoveParticipantFeature(fp);
	}

	@Override
	public IResizeShapeFeature getResizeFeature(IFeatureProvider fp) {
		return new ResizeParticipantFeature(fp);
	}

	@Override
	public IDeleteFeature getDeleteFeature(IFeatureProvider fp) {
		return new DeleteParticipantFeature(fp);
	}

	@Override
	public IRemoveFeature getRemoveFeature(IFeatureProvider fp) {
		return new RemoveChoreographyParticipantFeature(fp);
	}

	@Override
	public ICustomFeature[] getCustomFeatures(IFeatureProvider fp) {
		ICustomFeature[] superFeatures = super.getCustomFeatures(fp);
		ICustomFeature[] thisFeatures = new ICustomFeature[8 + superFeatures.length];
		thisFeatures[0] = new ShowDiagramPageFeature(fp);
		int i;
		for (i=0; i<superFeatures.length; ++i)
			thisFeatures[i+1] = superFeatures[i];
		thisFeatures[++i] = new AddChoreographyMessageFeature(fp);
		thisFeatures[++i] = new RemoveChoreographyMessageFeature(fp);
		thisFeatures[++i] = new RotatePoolFeature(fp);
		thisFeatures[++i] = new WhiteboxFeature(fp);
		thisFeatures[++i] = new BlackboxFeature(fp);
		thisFeatures[++i] = new ParticipantPushdownFeature(fp);
		thisFeatures[++i] = new ParticipantPullupFeature(fp);
		return thisFeatures;
	}
	
	public static class ParticipantPushdownFeature extends PushdownFeature {

		public ParticipantPushdownFeature(IFeatureProvider fp) {
			super(fp);
		}
		
		private ContainerShape getExternalContainer(ContainerShape ac) {
			// the external container can only be a Participant or a Diagram
			EObject parent = ac.eContainer();
			while (parent!=null && !(parent instanceof Diagram)) {
				if (parent==containerShape) {
					return null;
				}
				if (parent instanceof ContainerShape) {
					BaseElement be = BusinessObjectUtil.getFirstBaseElement((PictogramElement)parent);
					if (be instanceof Participant)
						return (ContainerShape) parent;
				}
				parent = parent.eContainer();
			}
			if (parent instanceof Diagram)
				return (Diagram) parent;
			return null;
		}
		
		private void collectExternalConnections(Connection c, Hashtable<Connection, ContainerShape> externalConnections) {
			AnchorContainer ac;
			ac = c.getStart().getParent();
			ContainerShape startShape = null;
			ContainerShape endShape = null;
			ContainerShape startContainer = null;
			ContainerShape endContainer = null;
			if (ac instanceof ContainerShape) {
				startShape = (ContainerShape) ac;
				startContainer = getExternalContainer(startShape);
			}
			
			ac = c.getEnd().getParent();
			if (ac instanceof ContainerShape) {
				endShape = (ContainerShape) ac;
				endContainer = getExternalContainer(endShape);
			}
			if (startContainer!=null && endContainer==null)
				externalConnections.put(c, startShape);
			else if (endContainer!=null && startContainer==null)
				externalConnections.put(c, endShape);
		}
		
		@Override
		public void execute(ICustomContext context) {
			// If necessary create dummy Pools that represent the other
			// Participants in this collaboration, or Data Inputs/Outputs by
			// following all incoming and outgoing Message Flows and Data Associations.
			containerShape = (ContainerShape)context.getPictogramElements()[0];

			// Follow all connections that start or end outside of this pool.
			Hashtable<Connection, ContainerShape> externalConnections = new Hashtable<Connection, ContainerShape>();

			AnchorContainer ac;
			for (Connection c : FeatureSupport.getConnections(containerShape)) {
				collectExternalConnections(c, externalConnections);
			}
			
			// Follow all external connections of ancestor shapes, keeping track
			// of the external shape.
			TreeIterator<EObject> iter = containerShape.eAllContents();
			while (iter.hasNext()) {
				EObject o = iter.next();
				if (o instanceof ContainerShape) {
					for (Connection c : FeatureSupport.getConnections((ContainerShape)o)) {
						collectExternalConnections(c, externalConnections);
					}
				}
			}
			
			// Push the contents of this Pool into a new diagram.
			super.execute(context);
			
			// Create dummy Pools and Data Inputs/Outputs and reconnect all external connections
			Hashtable<ContainerShape, ContainerShape> externalContainers = new Hashtable<ContainerShape, ContainerShape>();
			for (Entry<Connection, ContainerShape> e : externalConnections.entrySet()) {
				Connection externalConnection = e.getKey();
				ContainerShape externalShape = e.getValue();
				ContainerShape externalContainer = getExternalContainer(externalShape);
				ContainerShape localContainer = externalContainers.get(externalContainer);
				if (localContainer==null) {
					// the local Pool or DataInput/Output does not exist yet
					BaseElement be = BusinessObjectUtil.getFirstBaseElement(externalContainer);
					BPMNShape bs = BusinessObjectUtil.getFirstElementOfType(externalContainer, BPMNShape.class);
					if (be instanceof Participant) {
						CustomContext customContext = new CustomContext(new PictogramElement[] {newDiagram});
						customContext.setX(100);
						customContext.setY(500);
						ICustomFeature f = new CreateParticipantReferenceFeature(getFeatureProvider(),bs, (Participant) be);
						f.execute(customContext);
						Object o = customContext.getProperty(GraphitiConstants.PICTOGRAM_ELEMENT);
						if (o instanceof ContainerShape) {
							localContainer = (ContainerShape) o;
							externalContainers.put(externalContainer, localContainer);
						}
					}
					else {
						// external container must be a Diagram
					}
				}
				
				if (localContainer!=null) {
					BaseElement be = BusinessObjectUtil.getFirstBaseElement(externalConnection);
					if (be instanceof MessageFlow) {
//						CreateReferenceFeature cf = new CreateReferenceFeature<MessageFlow>(getFeatureProvider(), externalConnection, (MessageFlow)mf);
					}
				}
			}
			updateParticipant(getFeatureProvider(), containerShape);
		}
		
		@Override
		protected void collectChildElements(FlowElementsContainer container, List<BaseElement> children) {
			for (LaneSet ls : container.getLaneSets()) {
				children.addAll(ls.getLanes());
			}
			super.collectChildElements(container, children);
		}
	}
	
	public static class ParticipantPullupFeature extends PullupFeature {

		public ParticipantPullupFeature(IFeatureProvider fp) {
			super(fp);
		}
		
		@Override
		public void execute(ICustomContext context) {
			containerShape = (ContainerShape)context.getPictogramElements()[0];

			super.execute(context);
			

			int laneCount = 0;
			for (LaneSet ls : businessObject.getLaneSets()) {
				laneCount += ls.getLanes().size();
			}
			if (laneCount==0) {
				// the Pool has no Lanes, only individual flow elements so
				// we need to resize the Pool to the bounding rectangle
				// of all the flow elements.
				ResizeShapeContext resizeContext = new ResizeShapeContext(containerShape);
				resizeContext.setHeight(boundingRectangle.height);
				resizeContext.setWidth(boundingRectangle.width);
				ILocation loc = Graphiti.getPeService().getLocationRelativeToDiagram(containerShape);
				resizeContext.setLocation(loc.getX(), loc.getY());

				IResizeShapeFeature resizeFeature = getFeatureProvider().getResizeShapeFeature(resizeContext);
				resizeFeature.resizeShape(resizeContext);
			}
			
			updateParticipant(getFeatureProvider(), containerShape);
		}

		@Override
		protected Point getChildOffset(ContainerShape targetContainerShape) {
			boolean horz = FeatureSupport.isHorizontal(targetContainerShape);
			if (horz)
				return GraphicsUtil.createPoint(AddParticipantFeature.TITLE_HEIGHT, 0);
			return GraphicsUtil.createPoint(0, AddParticipantFeature.TITLE_HEIGHT);
		}

		@Override
		protected void collectChildShapes(ContainerShape containerShape, List<Shape> shapes) {
			super.collectChildShapes(containerShape, shapes);
			List<Shape> removed = new ArrayList<Shape>();
			for (Shape s : shapes) {
				if (FeatureSupport.isParticipant(s)) {
					removed.add(s);
					s = FeatureSupport.getLabelShape(s);
					if (s!=null)
						removed.add(s);
				}
			}
			shapes.removeAll(removed);
		}
	}
	
	protected static void updateParticipant(IFeatureProvider fp, ContainerShape containerShape) {
		// update the Participant
		UpdateContext updateContext = new UpdateContext(containerShape);
		updateContext.putProperty(GraphitiConstants.FORCE_UPDATE_ALL, Boolean.TRUE);
		IUpdateFeature updateFeature = fp.getUpdateFeature(updateContext);
		updateFeature.update(updateContext);
		
		LayoutContext layoutContext = new LayoutContext(containerShape);
		ILayoutFeature layoutFeature = fp.getLayoutFeature(layoutContext);
		layoutFeature.layout(layoutContext);
	}
}
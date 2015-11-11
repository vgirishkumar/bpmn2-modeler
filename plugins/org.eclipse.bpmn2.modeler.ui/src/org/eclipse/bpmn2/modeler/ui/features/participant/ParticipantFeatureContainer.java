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
import org.eclipse.bpmn2.Lane;
import org.eclipse.bpmn2.LaneSet;
import org.eclipse.bpmn2.Participant;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.di.BPMNEdge;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.core.features.BaseElementFeatureContainer;
import org.eclipse.bpmn2.modeler.core.features.CreateConnectionReferenceFeature;
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
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
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
import org.eclipse.dd.di.DiagramElement;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.datatypes.ILocation;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.IReconnectionFeature;
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
import org.eclipse.graphiti.features.context.impl.CreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.CustomContext;
import org.eclipse.graphiti.features.context.impl.LayoutContext;
import org.eclipse.graphiti.features.context.impl.ReconnectionContext;
import org.eclipse.graphiti.features.context.impl.ResizeShapeContext;
import org.eclipse.graphiti.features.context.impl.UpdateContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.Anchor;
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
		
		protected class SourceTarget {
			ContainerShape localShape;
			ContainerShape source;
			ContainerShape target;
			public SourceTarget(ContainerShape source, ContainerShape target, ContainerShape localShape) {
				this.source = source;
				this.target = target;
				this.localShape = localShape;
			}
		}

		public ParticipantPushdownFeature(IFeatureProvider fp) {
			super(fp);
		}
		
		private ContainerShape getExternalContainer(ContainerShape ac) {
			// the external container can only be a Participant or a Diagram
			EObject parent = ac;
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

		private void collectExternalConnections(Connection c, Hashtable<Connection, SourceTarget> externalConnections) {
			AnchorContainer ac;
			ContainerShape startShape = null;
			ContainerShape endShape = null;
			ContainerShape startContainer = null;
			ContainerShape endContainer = null;

			ac = c.getStart().getParent();
			if (ac instanceof ContainerShape) {
				startShape = (ContainerShape) ac;
				startContainer = getExternalContainer(startShape);
			}
			
			ac = c.getEnd().getParent();
			if (ac instanceof ContainerShape) {
				endShape = (ContainerShape) ac;
				endContainer = getExternalContainer(endShape);
			}
			if (startContainer!=null && endContainer==null) {
				// the  startContainer is the external Pool, endShape is the local shape
				// to which this connection attached.
				externalConnections.put(c, new SourceTarget(startContainer,endShape,endShape));
			}
			else if (endContainer!=null && startContainer==null) {
				// the startShape is the local shape, endContainer is external Pool
				externalConnections.put(c, new SourceTarget(startShape,endContainer,startShape));
			}
		}
		
		Hashtable<Connection, SourceTarget> externalConnections = new Hashtable<Connection, SourceTarget>();
		
		@Override
		public void execute(ICustomContext context) {

			// Push the contents of this Pool into a new diagram.
			super.execute(context);

			updateParticipant(getFeatureProvider(), containerShape);
		}
		
		@Override
		protected void collectConnections(ContainerShape source, List<Connection> connections) {
			// Follow all external connections of ancestor shapes, keeping track
			// of the external shape that is connected to the local shape.
			for (Shape s : source.getChildren()) {
				if (s instanceof ContainerShape) {
					for (Connection c : FeatureSupport.getConnections(s)) {
						if (isInternalConnection(containerShape, s, c)) {
							if (!connections.contains(c))
								connections.add(c);
						}
						else
							collectExternalConnections(c, externalConnections);
					}
					BaseElement be = BusinessObjectUtil.getFirstBaseElement(s);
					if (be instanceof FlowElementsContainer) {
						collectConnections((ContainerShape)s, connections);
					}
					else if (be instanceof Lane) {
						collectConnections((ContainerShape)s, connections);
					}
				}
			}
		}
		
		@Override
		protected void moveConnections(List<Connection> connections, ContainerShape source, ContainerShape target) {
			
			// disconnect external connections from the shape that will be
			// pushed to the new diagram, and reconnect it to the soon to be
			// empty Pool shape on the original diagram.
			ILocation loc = Graphiti.getPeService().getLocationRelativeToDiagram(containerShape);
			for (Entry<Connection, SourceTarget> e : externalConnections.entrySet()) {
				Connection c = e.getKey();
				SourceTarget st = e.getValue();
				if (st.localShape==c.getStart().getParent()) {
					// reconnect source to the Pool on the original diagram
					Anchor anchor = AnchorUtil.createAnchor(containerShape, GraphicsUtil.getShapeCenter(containerShape));
					ReconnectionContext rc = new ReconnectionContext(c, c.getStart(), anchor, loc);
					rc.setReconnectType(ReconnectionContext.RECONNECT_SOURCE);
					rc.setTargetPictogramElement(containerShape);
					IReconnectionFeature rf = getFeatureProvider().getReconnectionFeature(rc);
					rf.reconnect(rc);
				}
				else if (st.localShape==c.getEnd().getParent()) {
					// reconnect target
					Anchor anchor = AnchorUtil.createAnchor(containerShape, GraphicsUtil.getShapeCenter(containerShape));
					ReconnectionContext rc = new ReconnectionContext(c, c.getEnd(), anchor, loc);
					rc.setReconnectType(ReconnectionContext.RECONNECT_TARGET);
					rc.setTargetPictogramElement(containerShape);
					IReconnectionFeature rf = getFeatureProvider().getReconnectionFeature(rc);
					rf.reconnect(rc);
				}
				FeatureSupport.updateConnection(getFeatureProvider(), c, true);
			}

			super.moveConnections(connections, source, target);
			
			// Create dummy Pools and Data Inputs/Outputs and reconnect all external connections
			boolean isHorz = bpmnShape.isIsHorizontal();
			int nextX = 0;
			int nextY = 0;
			if (isHorz) {
				nextX = boundingRectangle.x;
				nextY = boundingRectangle.y + boundingRectangle.height + 4*MARGIN;
			}
			else {
				nextX = boundingRectangle.x + boundingRectangle.width + 4*MARGIN;
				nextY = boundingRectangle.y;
			}
			
			Hashtable<ContainerShape, ContainerShape> externalContainers = new Hashtable<ContainerShape, ContainerShape>();
			
			for (Entry<Connection, SourceTarget> e : externalConnections.entrySet()) {
				SourceTarget st = e.getValue();
				Connection externalConnection = e.getKey();
				ContainerShape externalContainer = null;
				ContainerShape localShape = st.localShape;
				if (st.localShape==st.source) {
					externalContainer = st.target;
				}
				else if (st.localShape==st.target) {
					externalContainer = st.source;
				}
				ContainerShape localContainer = externalContainers.get(externalContainer);
				if (localContainer==null) {
					// the local Pool or DataInput/Output does not exist yet, create it
					BaseElement be = BusinessObjectUtil.getFirstBaseElement(externalContainer);
					BPMNShape bs = BusinessObjectUtil.getFirstElementOfType(externalContainer, BPMNShape.class);
					if (be instanceof Participant) {
						CustomContext customContext = new CustomContext(new PictogramElement[] {newDiagram});
						customContext.setX(nextX);
						customContext.setY(nextY);
						ICustomFeature f = new CreateParticipantReferenceFeature(getFeatureProvider(),bs, (Participant) be);
						f.execute(customContext);
						Object o = customContext.getProperty(GraphitiConstants.PICTOGRAM_ELEMENT);
						if (o instanceof ContainerShape) {
							localContainer = (ContainerShape) o;
							// keep track of this in case more than one
							// connection is attached to this container
							externalContainers.put(externalContainer, localContainer);
							IDimension size = GraphicsUtil.calculateSize(localContainer);
							if (isHorz) {
								nextX += size.getWidth() + 2*MARGIN;
							}
							else {
								nextY += size.getHeight() + 2*MARGIN;
							}
						}
					}
					else {
						// external container must be a Diagram
					}
				}
				
				if (localContainer!=null) {
					Connection localConnection = null;
					BaseElement be = BusinessObjectUtil.getFirstBaseElement(externalConnection);
					BPMNEdge bpmnEdge = DIUtils.findBPMNEdge(oldBpmnDiagram, be);
					CreateConnectionReferenceFeature ccf = new CreateConnectionReferenceFeature(
							getFeatureProvider(), bpmnEdge, be);
					AnchorContainer sourceShape = null;
					AnchorContainer targetShape = null;
					if (st.localShape==st.source) {
						// outgoing connection: connect the source to the local Shape
						// and target to the reference Pool shape.
						sourceShape = localShape;
						targetShape = localContainer;
						
					}					
					else {
						sourceShape = localContainer;
						targetShape = localShape;
					}					

					if (sourceShape!=null && targetShape!=null) {
						Anchor sourceAnchor = AnchorUtil.createAnchor(sourceShape, GraphicsUtil.getShapeCenter(sourceShape));
						Anchor targetAnchor = AnchorUtil.createAnchor(targetShape, GraphicsUtil.getShapeCenter(targetShape));
						CreateConnectionContext ccc = new CreateConnectionContext();
						ccc.setSourcePictogramElement(sourceShape);
						ccc.setTargetPictogramElement(targetShape);
						ccc.setSourceAnchor(sourceAnchor);
						ccc.setTargetAnchor(targetAnchor);

						localConnection = ccf.create(ccc);
						if (localConnection!=null) {
							childConnections.add(localConnection);
						}
					}
				}
			}
		}
	}
	
	public static class ParticipantPullupFeature extends PullupFeature {

		public ParticipantPullupFeature(IFeatureProvider fp) {
			super(fp);
		}
		
		@Override
		public void execute(ICustomContext context) {

			super.execute(context);
			

			int laneCount = 0;
			for (LaneSet ls : businessObject.getLaneSets()) {
				laneCount += ls.getLanes().size();
			}
			if (laneCount==0) {
				// the Pool has no Lanes, only individual flow elements so
				// we need to resize the Pool to the bounding rectangle
				// of all the flow elements.
				IDimension dim = GraphicsUtil.calculateSize(containerShape);
				boolean resizeNeeded = false;
				if (dim.getWidth()<boundingRectangle.width) {
					resizeNeeded = true;
					dim.setWidth(boundingRectangle.width);
				}
				if (dim.getHeight()<boundingRectangle.height) {
					resizeNeeded = true;
					dim.setHeight(boundingRectangle.height);
				}
				if (resizeNeeded) {
					ResizeShapeContext resizeContext = new ResizeShapeContext(containerShape);
					resizeContext.setHeight(dim.getHeight());
					resizeContext.setWidth(dim.getWidth());
					ILocation loc = Graphiti.getPeService().getLocationRelativeToDiagram(containerShape);
					resizeContext.setLocation(loc.getX(), loc.getY());
	
					IResizeShapeFeature resizeFeature = getFeatureProvider().getResizeShapeFeature(resizeContext);
					resizeFeature.resizeShape(resizeContext);
				}
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
		protected void collectShapes(ContainerShape source, List<Shape> shapes) {
			List <PictogramElement> deleted = new ArrayList<PictogramElement>();
			shapes.addAll(source.getChildren());
			for (Shape s : shapes) {
				if (s instanceof ContainerShape) {
					Participant p = BusinessObjectUtil.getFirstElementOfType(s, Participant.class);
					if (FeatureSupport.isParticipantReference(oldDiagram, p)) {
						deleted.add(s);
						deleted.add(FeatureSupport.getLabelShape(s));
						continue;
					}
				}
			}
			shapes.removeAll(deleted);
		}
		
		@Override
		protected void collectConnections(ContainerShape source, List<Connection> connections) {
			Diagram diagram = Graphiti.getPeService().getDiagramForShape(source);
			List <Connection> deleted = new ArrayList<Connection>();
			
			for (Connection c : diagram.getConnections()) {
				AnchorContainer sc = c.getStart().getParent();
				AnchorContainer tc = c.getEnd().getParent();
//				String sourceName = GraphicsUtil.getDebugText(sc);
//				String targetName = GraphicsUtil.getDebugText(tc);
//				BaseElement be = BusinessObjectUtil.getFirstBaseElement(c);
//				String text = "";
//				if (be!=null)
//					text = be.eClass().getName() + ": ";
//				else
//					text = "Unknown Flow: ";
//				text += sourceName + " -> " + targetName;
//				Graphiti.getPeService().setPropertyValue(c, "description", text);

				boolean addit = true;
				Object sourceObject = BusinessObjectUtil.getFirstBaseElement(sc);
				if (sourceObject instanceof Participant) {
					if (FeatureSupport.isParticipantReference(diagram, (Participant)sourceObject))
						addit = false;
				}
				Object targetObject = BusinessObjectUtil.getFirstBaseElement(tc);
				if (targetObject instanceof Participant) {
					if (FeatureSupport.isParticipantReference(diagram, (Participant)targetObject))
						addit = false;
				}
				if (addit)
					connections.add(c);
				else {
					deleted.add(c);
				}
			}
		}
		
		@Override
		protected void moveConnections(List<Connection> connections, ContainerShape source, ContainerShape target) {
			Diagram targetDiagram = Graphiti.getPeService().getDiagramForShape(target);
			for (Connection c : connections) {
				BaseElement be = BusinessObjectUtil.getFirstBaseElement(c);
				for (Connection c2 : targetDiagram.getConnections()) {
					BaseElement be2 = BusinessObjectUtil.getFirstBaseElement(c2);
					if (be == be2) {
						// this one needs to be reconnected:
						// take the source or target from the object being moved
					}
				}
			}
		}
	}
	
	protected static void updateParticipant(IFeatureProvider fp, ContainerShape containerShape) {
		UpdateContext updateContext = new UpdateContext(containerShape);
		updateContext.putProperty(GraphitiConstants.FORCE_UPDATE_ALL, Boolean.TRUE);
		IUpdateFeature updateFeature = fp.getUpdateFeature(updateContext);
		updateFeature.update(updateContext);
		
		LayoutContext layoutContext = new LayoutContext(containerShape);
		ILayoutFeature layoutFeature = fp.getLayoutFeature(layoutContext);
		layoutFeature.layout(layoutContext);
	}
}
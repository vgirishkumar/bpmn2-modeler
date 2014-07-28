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
package org.eclipse.bpmn2.modeler.core.features;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.BoundaryEvent;
import org.eclipse.bpmn2.FlowElementsContainer;
import org.eclipse.bpmn2.Lane;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.modeler.core.utils.AnchorSite;
import org.eclipse.bpmn2.modeler.core.utils.AnchorType;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.BoundaryEventPositionHelper;
import org.eclipse.bpmn2.modeler.core.utils.BoundaryEventPositionHelper.PositionOnLine;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.datatypes.ILocation;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.IDeleteContext;
import org.eclipse.graphiti.features.context.impl.DeleteContext;
import org.eclipse.graphiti.features.impl.AbstractAddShapeFeature;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.styles.LineStyle;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.FixPointAnchor;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.features.DefaultDeleteFeature;
import org.eclipse.graphiti.util.ColorConstant;
import org.eclipse.graphiti.util.IColorConstant;

/**
 * Router for straight-line connections from source to target. Currently this
 * does nothing but serves as a container for common fields and methods.
 */
public class DefaultConnectionRouter extends AbstractConnectionRouter {

	/** The all shapes. */
	protected List<ContainerShape> allShapes;
	
	/** The connection. */
	protected Connection connection;
	
	/** The source. */
	protected Shape source;
	
	/** The target. */
	protected Shape target;
	
	/** The target anchor. */
	protected FixPointAnchor sourceAnchor, targetAnchor;

	AnchorSite sourceAnchorSites[];

	AnchorSite targetAnchorSites[];
	
	/**
	 * Instantiates a new default connection router.
	 *
	 * @param fp the fp
	 */
	public DefaultConnectionRouter(IFeatureProvider fp) {
		super(fp);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.features.AbstractConnectionRouter#route(org.eclipse.graphiti.mm.pictograms.Connection)
	 */
	@Override
	public boolean route(Connection connection) {
		this.connection = connection;
		this.sourceAnchor = (FixPointAnchor) connection.getStart();
		this.targetAnchor = (FixPointAnchor) connection.getEnd();
		this.source = (Shape) AnchorUtil.getAnchorContainer(sourceAnchor);
		this.target = (Shape) AnchorUtil.getAnchorContainer(targetAnchor);
		return false;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.features.IConnectionRouter#canRoute(org.eclipse.graphiti.mm.pictograms.Connection)
	 */
	@Override
	public boolean canRoute(Connection connection) {
		// don't touch Choreography Task Message Links.
		AnchorContainer ac = AnchorUtil.getAnchorContainer(connection.getStart());
		if (AnchorType.getType(ac) == AnchorType.MESSAGELINK)
			return false;
		ac = AnchorUtil.getAnchorContainer(connection.getEnd());
		if (AnchorType.getType(ac) == AnchorType.MESSAGELINK)
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.features.IConnectionRouter#needsUpdate(org.eclipse.graphiti.mm.pictograms.Connection)
	 */
	@Override
	public boolean routingNeeded(Connection connection) {
		return false;
	}

	/**
	 * Initialize.
	 */
	protected void initialize() {
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.features.AbstractConnectionRouter#dispose()
	 */
	@Override
	public void dispose() {
		// be sure to clean up the routing info
		removeRoutingInfo(connection);
	}

	/**
	 * Check if the connection's source and target nodes are identical.
	 * 
	 * @return true if connection source == target
	 */
	protected boolean isSelfConnection() {
		if (source != target)
			return false;
		return true;
	}

	/**
	 * Find all shapes.
	 *
	 * @return the list
	 */
	protected List<ContainerShape> findAllShapes() {
		allShapes = new ArrayList<ContainerShape>();
		Diagram diagram = fp.getDiagramTypeProvider().getDiagram();
		TreeIterator<EObject> iter = diagram.eAllContents();
		while (iter.hasNext()) {
			EObject o = iter.next();
			if (o instanceof ContainerShape) {
				// this is a potential collision shape
				ContainerShape shape = (ContainerShape)o;
				BPMNShape bpmnShape = BusinessObjectUtil.getFirstElementOfType(shape, BPMNShape.class);
				if (bpmnShape==null)
					continue;
//				if (shape==source || shape==target)
//					continue;
				// ignore containers (like Lane, SubProcess, etc.) if the source
				// or target shapes are children of the container's hierarchy
				if (shape==source.eContainer() || shape==target.eContainer())
					continue;
				
				// ignore some containers altogether
				BaseElement be = bpmnShape.getBpmnElement();
				if (be instanceof Lane)
					continue;
				// TODO: other criteria here?
	
				allShapes.add(shape);
			}
		}
		GraphicsUtil.dump("All Shapes", allShapes); //$NON-NLS-1$
		return allShapes;
	}

	/**
	 * Gets the collision edge.
	 *
	 * @param p1 the p1
	 * @param p2 the p2
	 * @return the collision edge
	 */
	protected GraphicsUtil.LineSegment getCollisionEdge(Point p1, Point p2) {
		ContainerShape shape = getCollision(p1, p2);
		if (shape!=null) {
			return GraphicsUtil.findNearestEdge(shape, p1);
		}
		return null;
	}

	/**
	 * Gets the collision.
	 *
	 * @param p1 the p1
	 * @param p2 the p2
	 * @return the collision
	 */
	protected ContainerShape getCollision(Point p1, Point p2) {
		List<ContainerShape> collisions = findCollisions(p1, p2);
		if (collisions.size()==0)
			return null;
		if (collisions.size()==1)
			return collisions.get(0);
		sortCollisions(collisions, p2);
		return collisions.get(0);
	}
	
	/**
	 * Find collisions.
	 *
	 * @param p1 the p1
	 * @param p2 the p2
	 * @return the list
	 */
	protected List<ContainerShape> findCollisions(Point p1, Point p2) {
		List<ContainerShape> collisions = new ArrayList<ContainerShape>();
		if (allShapes==null)
			findAllShapes();
		for (ContainerShape shape : allShapes) {
			if (!FeatureSupport.isGroupShape(shape) && !FeatureSupport.isLabelShape(shape) && !FeatureSupport.isParticipant(shape)) {
				EObject bo = BusinessObjectUtil.getBusinessObjectForPictogramElement(shape);
				if (bo instanceof FlowElementsContainer) {
					// it's not a collision if the shape is a SubProcess and
					// both source and target connection points lie inside the SubProcess
					if (GraphicsUtil.contains(shape, p1) || GraphicsUtil.contains(shape, p2))
						continue;
				}
				
				if (GraphicsUtil.intersectsLine(shape, p1, p2))
					collisions.add(shape);
			}
		}
//		if (collisions.size()>0)
//			GraphicsUtil.dump("Collisions with line ["+p1.getX()+", "+p1.getY()+"]"+" ["+p2.getX()+", "+p2.getY()+"]", collisions);
		return collisions;
	}

	/**
	 * Sort collisions.
	 *
	 * @param collisions the collisions
	 * @param p the p
	 */
	protected void sortCollisions(List<ContainerShape> collisions, final Point p) {
		Collections.sort(collisions, new Comparator<ContainerShape>() {
	
			@Override
			public int compare(ContainerShape s1, ContainerShape s2) {
				GraphicsUtil.LineSegment seg1 = GraphicsUtil.findNearestEdge(s1, p);
				double d1 = seg1.getDistance(p);
				GraphicsUtil.LineSegment seg2 = GraphicsUtil.findNearestEdge(s2, p);
				double d2 = seg2.getDistance(p);
				return (int) (d2 - d1);
			}
		});
	}
	
	/**
	 * Find Connection line crossings. This will return a list of all
	 * Connections on the Diagram that intersect any line segment of the given
	 * Connection. Connections that are attached to the given Connection are
	 * ignored.
	 *
	 * @param connection the Connection to test.
	 * @param start the starting point of a line segment on the connection.
	 * @param end the ending point of the line segment.
	 * @return a list of Connections that cross over the line segment.
	 */
	protected List<Connection> findCrossings(Connection connection, Point start, Point end) {
		// TODO: figure out why this isn't working!
		List<Connection> crossings = new ArrayList<Connection>();
		List<Connection> allConnections = fp.getDiagramTypeProvider().getDiagram().getConnections();
		List<FixPointAnchor> connectionAnchors = AnchorUtil.getAnchors(connection);
		for (Connection c : allConnections) {
			if (Graphiti.getPeService().getProperty(c, RoutingNet.CONNECTION)!=null) {
				continue;
			}
			if (connectionAnchors.contains(c.getStart()) || connectionAnchors.contains(c.getEnd()))
				continue;
			Point p1 = GraphicsUtil.createPoint(c.getStart());
			Point p3 = GraphicsUtil.createPoint(c.getEnd());
			if (c instanceof FreeFormConnection) {
				FreeFormConnection ffc = (FreeFormConnection) c;
				Point p2 = p1;
				for (Point p : ffc.getBendpoints()) {
					if (GraphicsUtil.intersects(start, end, p1, p)) {
						crossings.add(c);
						break;
					}
					p2 = p1 = p;
				}
				if (GraphicsUtil.intersects(start, end, p2, p3)) {
					crossings.add(c);
				}
			}
			else if (GraphicsUtil.intersects(start, end, p1, p3)) {
				crossings.add(c);
			}
		}
		return crossings;
	}

	/**
	 * Length.
	 *
	 * @param p1 the p1
	 * @param p2 the p2
	 * @return the double
	 */
	protected static double length(Point p1, Point p2) {
		return GraphicsUtil.getLength(p1, p2);
	}

	/**
	 * Draw connection routes.
	 *
	 * @param allRoutes the all routes
	 */
	protected void drawConnectionRoutes(List<ConnectionRoute> allRoutes) {
		if (GraphicsUtil.debug) {

			DeleteRoutingConnectionFeature deleteFeature = new DeleteRoutingConnectionFeature(fp);
			deleteFeature.delete();

			Diagram diagram = fp.getDiagramTypeProvider().getDiagram();
			for (int i=0; i<allRoutes.size(); ++i) {
				ConnectionRoute r = allRoutes.get(i);
//				Anchor sa = AnchorUtil.createFixedAnchor(source, r.get(0));
//				Anchor ta = AnchorUtil.createFixedAnchor(target, r.get( r.size()-1 ));
//				AddConnectionContext context = new AddConnectionContext(sa, ta);
//				context.setTargetContainer(diagram);
//				context.setNewObject(r);
//				AddRoutingConnectionFeature feature = new AddRoutingConnectionFeature(fp);
//				feature.add(context);
				
				GraphicsUtil.dump(r.toString());
			}
		}
	}
	
	protected void calculateAllowedAnchorSites() {
		
		EObject bo = BusinessObjectUtil.getBusinessObjectForPictogramElement(source);
		if (bo instanceof BoundaryEvent) {
			sourceAnchorSites = calculateBoundaryEventAnchorSites(source);
		}
		bo = BusinessObjectUtil.getBusinessObjectForPictogramElement(target);
		if (bo instanceof BoundaryEvent) {
			targetAnchorSites = calculateBoundaryEventAnchorSites(target);
		}
		if (AnchorType.getType(source) == AnchorType.CONNECTION) {
			sourceAnchorSites = new AnchorSite[1];
			sourceAnchorSites[0] = AnchorSite.CENTER;
		}
		if (AnchorType.getType(target) == AnchorType.CONNECTION) {
			targetAnchorSites = new AnchorSite[1];
			targetAnchorSites[0] = AnchorSite.CENTER;
		}
		ILocation sPos = Graphiti.getPeService().getLocationRelativeToDiagram(source);
		IDimension sSize = GraphicsUtil.calculateSize(source);
		ILocation tPos = Graphiti.getPeService().getLocationRelativeToDiagram(target);
		IDimension tSize = GraphicsUtil.calculateSize(target);
		// find relative locations
		if (sPos.getX()+sSize.getWidth() < tPos.getX()) {
			// source shape is to left of target
			if (sPos.getY()+sSize.getHeight() < tPos.getY()) {
				// source shape is to left and above target:
				// omit the two opposite sides of both source and target
				if (sourceAnchorSites==null) {
					sourceAnchorSites = new AnchorSite[2];
					sourceAnchorSites[0] = AnchorSite.RIGHT;
					sourceAnchorSites[1] = AnchorSite.BOTTOM;
				}
				if (targetAnchorSites==null) {
					targetAnchorSites = new AnchorSite[2];
					targetAnchorSites[0] = AnchorSite.LEFT;
					targetAnchorSites[1] = AnchorSite.TOP;
				}
			}
			else if(sPos.getY() > tPos.getY()+tSize.getHeight()) {
				// source shape is to left and below target
				if (sourceAnchorSites==null) {
					sourceAnchorSites = new AnchorSite[2];
					sourceAnchorSites[0] = AnchorSite.RIGHT;
					sourceAnchorSites[1] = AnchorSite.TOP;
				}
				if (targetAnchorSites==null) {
					targetAnchorSites = new AnchorSite[2];
					targetAnchorSites[0] = AnchorSite.LEFT;
					targetAnchorSites[1] = AnchorSite.BOTTOM;
				}
			}
			else {
				if (sourceAnchorSites==null) {
					sourceAnchorSites = new AnchorSite[3];
					sourceAnchorSites[0] = AnchorSite.RIGHT;
					sourceAnchorSites[1] = AnchorSite.TOP;
					sourceAnchorSites[2] = AnchorSite.BOTTOM;
				}
				if (targetAnchorSites==null) {
					targetAnchorSites = new AnchorSite[3];
					targetAnchorSites[0] = AnchorSite.LEFT;
					targetAnchorSites[1] = AnchorSite.TOP;
					targetAnchorSites[2] = AnchorSite.BOTTOM;
				}
			}
		}
		else if (sPos.getX() > tPos.getX()+tSize.getWidth()) {
			// source shape is to right of target
			if (sPos.getY()+sSize.getHeight() < tPos.getY()) {
				// source shape is to right and above target
				if (sourceAnchorSites==null) {
					sourceAnchorSites = new AnchorSite[2];
					sourceAnchorSites[0] = AnchorSite.LEFT;
					sourceAnchorSites[1] = AnchorSite.BOTTOM;
				}
				if (targetAnchorSites==null) {
					targetAnchorSites = new AnchorSite[2];
					targetAnchorSites[0] = AnchorSite.RIGHT;
					targetAnchorSites[1] = AnchorSite.TOP;
				}
			}
			else if(sPos.getY() > tPos.getY()+tSize.getHeight()) {
				// source shape is to right and below target
				if (sourceAnchorSites==null) {
					sourceAnchorSites = new AnchorSite[2];
					sourceAnchorSites[0] = AnchorSite.LEFT;
					sourceAnchorSites[1] = AnchorSite.TOP;
				}
				if (targetAnchorSites==null) {
					targetAnchorSites = new AnchorSite[2];
					targetAnchorSites[0] = AnchorSite.RIGHT;
					targetAnchorSites[1] = AnchorSite.BOTTOM;
				}
			}
			else {
				if (sourceAnchorSites==null) {
					sourceAnchorSites = new AnchorSite[3];
					sourceAnchorSites[0] = AnchorSite.LEFT;
					sourceAnchorSites[1] = AnchorSite.TOP;
					sourceAnchorSites[2] = AnchorSite.BOTTOM;
				}
				if (targetAnchorSites==null) {
					targetAnchorSites = new AnchorSite[3];
					targetAnchorSites[0] = AnchorSite.RIGHT;
					targetAnchorSites[1] = AnchorSite.TOP;
					targetAnchorSites[2] = AnchorSite.BOTTOM;
				}
			}
		}
		else if (sPos.getY()+sSize.getHeight() < tPos.getY()) {
			// source shape is above target
			if (sourceAnchorSites==null) {
				sourceAnchorSites = new AnchorSite[3];
				sourceAnchorSites[0] = AnchorSite.LEFT;
				sourceAnchorSites[1] = AnchorSite.RIGHT;
				sourceAnchorSites[2] = AnchorSite.BOTTOM;
			}
			if (targetAnchorSites==null) {
				targetAnchorSites = new AnchorSite[3];
				targetAnchorSites[0] = AnchorSite.LEFT;
				targetAnchorSites[1] = AnchorSite.RIGHT;
				targetAnchorSites[2] = AnchorSite.TOP;
			}
		}
		else if(sPos.getY() > tPos.getY()+tSize.getHeight()) {
			// source shape is below target
			if (sourceAnchorSites==null) {
				sourceAnchorSites = new AnchorSite[3];
				sourceAnchorSites[0] = AnchorSite.LEFT;
				sourceAnchorSites[1] = AnchorSite.RIGHT;
				sourceAnchorSites[2] = AnchorSite.TOP;
			}
			if (targetAnchorSites==null) {
				targetAnchorSites = new AnchorSite[3];
				targetAnchorSites[0] = AnchorSite.LEFT;
				targetAnchorSites[1] = AnchorSite.RIGHT;
				targetAnchorSites[2] = AnchorSite.BOTTOM;
			}
		}
		else {
			// source and target overlap
			if (sourceAnchorSites==null) {
				sourceAnchorSites = new AnchorSite[4];
				sourceAnchorSites[0] = AnchorSite.LEFT;
				sourceAnchorSites[1] = AnchorSite.RIGHT;
				sourceAnchorSites[2] = AnchorSite.TOP;
				sourceAnchorSites[3] = AnchorSite.BOTTOM;
			}
			if (targetAnchorSites==null) {
				targetAnchorSites = new AnchorSite[4];
				targetAnchorSites[0] = AnchorSite.LEFT;
				targetAnchorSites[1] = AnchorSite.RIGHT;
				targetAnchorSites[2] = AnchorSite.TOP;
				targetAnchorSites[3] = AnchorSite.BOTTOM;
			}
		}
	}

	protected AnchorSite[] calculateBoundaryEventAnchorSites(Shape shape) {
		AnchorSite sites[];
		PositionOnLine pol = BoundaryEventPositionHelper.getPositionOnLineProperty(shape);
		switch (pol.getLocationType()) {
		case BOTTOM:
			sites = new AnchorSite[1];
			sites[0] = AnchorSite.BOTTOM;
			break;
		case BOTTOM_LEFT:
			sites = new AnchorSite[2];
			sites[0] = AnchorSite.BOTTOM;
			sites[1] = AnchorSite.LEFT;
			break;
		case BOTTOM_RIGHT:
			sites = new AnchorSite[2];
			sites[0] = AnchorSite.BOTTOM;
			sites[1] = AnchorSite.RIGHT;
			break;
		case LEFT:
			sites = new AnchorSite[1];
			sites[0] = AnchorSite.LEFT;
			break;
		case RIGHT:
			sites = new AnchorSite[1];
			sites[0] = AnchorSite.RIGHT;
			break;
		case TOP:
			sites = new AnchorSite[1];
			sites[0] = AnchorSite.TOP;
			break;
		case TOP_LEFT:
			sites = new AnchorSite[2];
			sites[0] = AnchorSite.TOP;
			sites[1] = AnchorSite.LEFT;
			break;
		case TOP_RIGHT:
			sites = new AnchorSite[2];
			sites[0] = AnchorSite.TOP;
			sites[1] = AnchorSite.RIGHT;
			break;
		default:
			sites = new AnchorSite[4];
			sites[0] = AnchorSite.TOP;
			sites[1] = AnchorSite.LEFT;
			sites[2] = AnchorSite.BOTTOM;
			sites[3] = AnchorSite.RIGHT;
			break;
		}
		return sites;
	}

	protected boolean shouldCalculate(AnchorSite sourceSite, AnchorSite targetSite) {
		for (int i=0; i<sourceAnchorSites.length; ++i) {
			if (sourceSite == sourceAnchorSites[i]) {
				for (int j=0; j<targetAnchorSites.length; ++j) {
					if (targetSite == targetAnchorSites[j]) {
						return true;
					}
				}				
			}
		}
		return false;
	}

	/**
	 * The Class AddRoutingConnectionFeature.
	 */
	protected class AddRoutingConnectionFeature extends AbstractAddShapeFeature {
		
		/** The Constant CONNECTION. */
		public static final String CONNECTION = "ROUTING_NET_CONNECTION"; //$NON-NLS-1$

		/**
		 * Instantiates a new adds the routing connection feature.
		 *
		 * @param fp the fp
		 */
		public AddRoutingConnectionFeature(IFeatureProvider fp) {
			super(fp);
		}

		/* (non-Javadoc)
		 * @see org.eclipse.graphiti.func.IAdd#canAdd(org.eclipse.graphiti.features.context.IAddContext)
		 */
		@Override
		public boolean canAdd(IAddContext ac) {
			return true;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.graphiti.func.IAdd#add(org.eclipse.graphiti.features.context.IAddContext)
		 */
		@Override
		public PictogramElement add(IAddContext ac) {
			IAddConnectionContext context = (IAddConnectionContext) ac;
			Anchor sourceAnchor = context.getSourceAnchor();
			Anchor targetAnchor = context.getTargetAnchor();
			ConnectionRoute route = (ConnectionRoute) context.getNewObject();

			Diagram diagram = getDiagram();
			FreeFormConnection connection = peService
					.createFreeFormConnection(diagram);
			connection.setStart(sourceAnchor);
			connection.setEnd(targetAnchor);
			for (int i = 1; i < route.size() - 1; ++i) {
				connection.getBendpoints().add(route.get(i));
			}

			peService.setPropertyValue(connection, CONNECTION, "" + route.getId()); //$NON-NLS-1$

			Polyline connectionLine = Graphiti.getGaService().createPolyline(
					connection);

			connectionLine.setLineWidth(1);
			connectionLine.setLineStyle(LineStyle.DASH);

			IColorConstant foreground = new ColorConstant(255, 120, 255);

			int w = 3;
			int l = 15;

			ConnectionDecorator decorator = peService
					.createConnectionDecorator(connection, false, 1.0, true);
			Polyline arrowhead = gaService.createPolygon(decorator, new int[] {
					-l, w, 0, 0, -l, -w, -l, w });
			arrowhead.setForeground(gaService.manageColor(diagram, foreground));
			connectionLine.setForeground(gaService.manageColor(diagram,
					foreground));

			FeatureSupport.setToolTip(connection.getGraphicsAlgorithm(), route.toString());

			return connection;
		}
	}
	
	/**
	 * The Class DeleteRoutingConnectionFeature.
	 */
	protected class DeleteRoutingConnectionFeature extends DefaultDeleteFeature {

		/**
		 * Instantiates a new delete routing connection feature.
		 *
		 * @param fp the fp
		 */
		public DeleteRoutingConnectionFeature(IFeatureProvider fp) {
			super(fp);
		}

		/* (non-Javadoc)
		 * @see org.eclipse.graphiti.ui.features.DefaultDeleteFeature#canDelete(org.eclipse.graphiti.features.context.IDeleteContext)
		 */
		@Override
		public boolean canDelete(IDeleteContext context) {
			return true;
		}

		/**
		 * Delete.
		 */
		public void delete() {
			delete(null);
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.graphiti.ui.features.DefaultDeleteFeature#delete(org.eclipse.graphiti.features.context.IDeleteContext)
		 */
		@Override
		public void delete(IDeleteContext context) {
			List<Connection> deleted = new ArrayList<Connection>();
			deleted.addAll(getDiagram().getConnections());
			
			for (Connection connection : deleted) {
				if (Graphiti.getPeService().getProperty(connection, RoutingNet.CONNECTION)!=null) {
					context = new DeleteContext(connection);
					super.delete(context);
				}
			}
		}
		
	}
}

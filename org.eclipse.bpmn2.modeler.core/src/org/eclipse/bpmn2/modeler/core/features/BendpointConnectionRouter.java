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
import java.util.List;
import java.util.Map;

import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil.AnchorLocation;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil.BoundaryAnchor;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.datatypes.ILocation;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.Shape;

/**
 * Router for connections from source to target that can have user-settable bendpoints.
 */
public class BendpointConnectionRouter extends DefaultConnectionRouter {

	/**
	 * Calculates detour points for a given shape. These points surround the shape at each of the corners
	 * of the shape's bounding rectangle, but "just outside" the shape.
	 */
	public class DetourPoints {
		public int leftMargin = 10;
		public int rightMargin = 10;
		public int topMargin = 10;
		public int bottomMargin = 10;
		public Point topLeft;
		public Point topRight;
		public Point bottomLeft;
		public Point bottomRight;
		
		public DetourPoints(ContainerShape shape) {
			calculate(shape);
		}
		
		public DetourPoints(ContainerShape shape, int margin) {
			this(shape,margin,margin,margin,margin);
		}
		
		public DetourPoints(ContainerShape shape, int leftMargin, int rightMargin, int topMargin, int bottomMargin) {
			this.leftMargin = leftMargin;
			this.rightMargin = rightMargin;
			this.topMargin = topMargin;
			this.bottomMargin = bottomMargin;
			calculate(shape);
		}
		
		protected void calculate(Shape shape) {
			ILocation loc = peService.getLocationRelativeToDiagram(shape);
			IDimension size = GraphicsUtil.calculateSize(shape);
			topLeft = GraphicsUtil.createPoint(loc.getX() - leftMargin, loc.getY() - topMargin);
			topRight = GraphicsUtil.createPoint(loc.getX() + size.getWidth() + rightMargin, loc.getY() - topMargin);
			bottomLeft = GraphicsUtil.createPoint(loc.getX() - leftMargin, loc.getY() + size.getHeight() + bottomMargin);
			bottomRight = GraphicsUtil.createPoint(loc.getX() + size.getWidth() + leftMargin, loc.getY() + size.getHeight() + bottomMargin);
		}

		public boolean intersects(DetourPoints d2) {
			return GraphicsUtil.intersects(
					this.topLeft.getX(), this.topLeft.getY(), this.topRight.getX() - this.topLeft.getX(), this.bottomLeft.getY() - this.topLeft.getY(),
					d2.topLeft.getX(), d2.topLeft.getY(), d2.topRight.getX() - d2.topLeft.getX(), d2.bottomLeft.getY() - d2.topLeft.getY()
			);
		}
		
		public boolean contains(DetourPoints d2) {
			return	this.topLeft.getX()<=d2.topLeft.getX() &&
					this.topRight.getX()>=d2.topRight.getX() &&
					this.topLeft.getY()<=d2.topLeft.getY() && 
					this.bottomLeft.getY()>=d2.bottomLeft.getY(); 
		}
		
		public void merge(DetourPoints d2) {
			this.topLeft.setX( Math.min(this.topLeft.getX(), d2.topLeft.getX()) );
			this.topLeft.setY( Math.min(this.topLeft.getY(), d2.topLeft.getY()) );
			this.topRight.setX( Math.max(this.topRight.getX(), d2.topRight.getX()) );
			this.topRight.setY( Math.min(this.topRight.getY(), d2.topRight.getY()) );
			this.bottomLeft.setX( Math.min(this.bottomLeft.getX(), d2.bottomLeft.getX()) );
			this.bottomLeft.setY( Math.max(this.bottomLeft.getY(), d2.bottomLeft.getY()) );
			this.bottomRight.setX( Math.max(this.bottomRight.getX(), d2.bottomRight.getX()) );
			this.bottomRight.setY( Math.max(this.bottomRight.getY(), d2.bottomRight.getY()) );
		}
	}

	// The Connection passed in to route(), cast as a FreeFormConnection for convenience
	protected FreeFormConnection ffc;
	// The moved or added bendpoint (if any)
	protected Point movedBendpoint;
	protected Point removedBendpoint;
	// The list of old connection points (including the end points) for determining if a route has changed
	protected List<Point> oldPoints;
	protected Anchor sourceAdHocAnchor, targetAdHocAnchor;
	
	public BendpointConnectionRouter(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean route(Connection connection) {
		super.route(connection);
		
		if (connection instanceof FreeFormConnection)
			ffc = (FreeFormConnection)connection;
		else
			return false;
		
		initialize();
		ConnectionRoute route = calculateRoute();
		boolean changed = isRouteChanged(route);
		if (changed) {
			applyRoute(route);
		}
		dispose();

		return changed;
	}
	
	/**
	 * Initialize the newPoints list and set the new start and end anchors
	 * 
	 * @param ffc - the FreeFormConnection
	 */
	@Override
	protected void initialize() {
		movedBendpoint = getMovedBendpoint(ffc);
		if (movedBendpoint==null)
			movedBendpoint = getAddedBendpoint(ffc);
		removedBendpoint = getRemovedBendpoint(ffc);
	
		/**
		 * Save the connection's start/end anchors, and their locations as well as
		 * the bendpoints. This is used to compare against the new ConnectionRoute
		 */
		oldPoints = new ArrayList<Point>();
		oldPoints.add(GraphicsUtil.createPoint(ffc.getStart()));
		for (Point p : ffc.getBendpoints()) {
			oldPoints.add(GraphicsUtil.createPoint(p));
		}
		oldPoints.add(GraphicsUtil.createPoint(ffc.getEnd()));

		if (AnchorUtil.useAdHocAnchors(source, ffc) && AnchorUtil.isAdHocAnchor(ffc.getStart()))
			sourceAdHocAnchor = ffc.getStart();
		else
			sourceAdHocAnchor = null;
		if (AnchorUtil.useAdHocAnchors(target, ffc) && AnchorUtil.isAdHocAnchor(ffc.getEnd()))
			targetAdHocAnchor = ffc.getEnd();
		else
			targetAdHocAnchor = null;
	}
	
	protected ConnectionRoute calculateRoute() {
		if (isSelfConnection()) {
			return calculateSelfConnectionRoute();
		}

		ConnectionRoute route = new ConnectionRoute(this, 1, source, target);
		for (Point p : oldPoints) {
			route.add(p);
		}
		return route;
	}
	
	/**
	 * Route connections whose source and target are the same. This only reroutes the
	 * connection if there are currently no bendpoints in the connection - we don't
	 * want to reroute a connection that may have already been manually rerouted.
	 * 
	 * @param connection - the connection to be routed
	 * @return true if the router has done any work
	 */
	protected ConnectionRoute calculateSelfConnectionRoute() {
		if (!isSelfConnection())
			return null;
		
		if (movedBendpoint==null) {
			if (ffc.getStart() != ffc.getEnd() && ffc.getBendpoints().size()>0) {
				// this connection starts and ends at the same node but it has different
				// anchor points and at least one bendpoint, which makes it likely that
				// this connection was already routed previously and the self-connection
				// is how the user wants it. But, check if the user wants to force routing.
				if (!forceRouting(ffc))
					return null;
			}
		}
		
		Map<AnchorLocation, BoundaryAnchor> targetBoundaryAnchors = AnchorUtil.getBoundaryAnchors(target);
		BoundaryAnchor targetTop = targetBoundaryAnchors.get(AnchorLocation.TOP);
		BoundaryAnchor targetRight = targetBoundaryAnchors.get(AnchorLocation.RIGHT);

		// create the bendpoints that loop the connection around the top-right corner of the figure
		ILocation loc = peService.getLocationRelativeToDiagram((Shape)target);
		IDimension size = GraphicsUtil.calculateSize(target);
		int x1 = loc.getX() + size.getWidth() + 20;
		int y1 = loc.getY() + size.getHeight() / 2;
		int x2 = loc.getX() + size.getWidth() / 2;
		int y2 = loc.getY() - 20;
		Point right = gaService.createPoint(x1, y1); // the point to the right of the node
		Point corner = gaService.createPoint(x1, y2); // point above the top-right corner 
		Point top = gaService.createPoint(x2, y2); // point above the node
		
		// adjust these points to the moved or added bendpoint if possible
		Point p = movedBendpoint;
		if (p!=null) {
			int x = p.getX();
			int y = p.getY();
			if (x > loc.getX() + size.getWidth() + 2) {
				right.setX(x);
				corner.setX(x);
			}
			if (y < loc.getY() - 2) {
				top.setY(y);
				corner.setY(y);
			}
		}

		// and add them to the new Route
		ConnectionRoute route = new ConnectionRoute(this,1,source,target);
		route.add(GraphicsUtil.createPoint(targetRight.anchor));
		route.add(right);
		route.add(corner);
		route.add(top);
		route.add(GraphicsUtil.createPoint(targetTop.anchor));

		return route;
	}
	
	/**
	 * Compare the connection's original start/end locations and all of its bendpoints
	 * with the newly calculated points.
	 * 
	 * @return true if the connection is different from the newly calculated points
	 */
	protected boolean isRouteChanged(ConnectionRoute route) {
		if (route==null || route.size()==0)
			return false;
		if (oldPoints.size()!=route.size()) {
			return true;
		}
		for (int i=0; i<oldPoints.size(); ++i) {
			Point p1 = oldPoints.get(i);
			Point p2 = route.get(i);
			if (!GraphicsUtil.pointsEqual(p1, p2)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Set the connection's new start/end point anchors and the newly calculated bendpoints.
	 */
	protected void applyRoute(ConnectionRoute route) {
		route.apply(ffc, sourceAdHocAnchor, targetAdHocAnchor);
		DIUtils.updateDIEdge(ffc);
	}

	/**
	 * Set a property in the given FreeFormConnection that represents the index of an existing
	 * bendpoint that has been moved by the user. This bendpoint is taken into consideration
	 * in the new routing calculations.
	 * 
	 * @param connection - FreeFormConnection to check
	 * @param index - index of a bendpoint. If this value is out of range, the property will be
	 * remmoved from the connection
	 */
	public static void setMovedBendpoint(Connection connection, int index) {
		setInterestingBendpoint(connection, "moved.", index);
	}

	public static void setAddedBendpoint(Connection connection, int index) {
		setInterestingBendpoint(connection, "added.", index);
	}

	public static void setRemovedBendpoint(Connection connection, int index) {
		setInterestingBendpoint(connection, "removed.", index);
	}

	public static void setFixedBendpoint(Connection connection, int index) {
		setInterestingBendpoint(connection, "fixed."+index+".", index);
	}

	protected static void setInterestingBendpoint(Connection connection, String type, int index) {
		if (connection instanceof FreeFormConnection) {
			int size = ((FreeFormConnection)connection).getBendpoints().size();
			if (index>=0 && size>0) {
				if (index>=size)
					index = size-1;
				AbstractConnectionRouter.setRoutingInfoInt(connection, type+ROUTING_INFO_BENDPOINT, index);
			}
			else
				AbstractConnectionRouter.removeRoutingInfo(connection, type+ROUTING_INFO_BENDPOINT);
		}
	}

	/**
	 * Return the "moved bendpoint" property that was previously set in the FreeFormConnection
	 * by setMovedBendpoint()
	 *  
	 * @param connection - FreeFormConnection to check
	 * @return a Graphiti Point in Diagram-relative coordinates, or null if the property is not set
	 */
	public static Point getMovedBendpoint(Connection connection) {
		return getInterestingBendpoint(connection, "moved.");
	}
	
	public static Point getAddedBendpoint(Connection connection) {
		return getInterestingBendpoint(connection, "added.");
	}
	
	public static Point getRemovedBendpoint(Connection connection) {
		return getInterestingBendpoint(connection, "removed.");
	}
	
	public static Point getFixedBendpoint(Connection connection, int index) {
		return getInterestingBendpoint(connection, "fixed."+index+".");
	}
	
	protected static Point getInterestingBendpoint(Connection connection, String type) {
		try {
			int index = AbstractConnectionRouter.getRoutingInfoInt(connection, type+ROUTING_INFO_BENDPOINT);
			return ((FreeFormConnection)connection).getBendpoints().get(index);
		}
		catch (Exception e) {
		}
		return null;
	}
}

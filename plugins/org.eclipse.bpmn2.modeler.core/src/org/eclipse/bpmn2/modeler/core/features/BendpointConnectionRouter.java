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

import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.core.utils.AnchorSite;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.datatypes.ILocation;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.FixPointAnchor;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.services.Graphiti;

/**
 * Router for connections that can have user-settable bendpoints.
 */
public class BendpointConnectionRouter extends DefaultConnectionRouter {

	/** The connection, must be a {@code FreeFormConnection}. */
	protected FreeFormConnection ffc;
	/** The moved or added bendpoint (if any). */
	protected Point movedBendpoint;
	/** The removed bendpoint. */
	protected Point removedBendpoint;
	/** The list of old connection cuts (including the end cuts) for determining if a route has changed */
	protected List<Point> oldPoints;
	
	/**
	 * Instantiates a new bendpoint connection router.
	 *
	 * @param fp the Feature Provider
	 */
	public BendpointConnectionRouter(IFeatureProvider fp) {
		super(fp);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.features.DefaultConnectionRouter#route(org.eclipse.graphiti.mm.pictograms.Connection)
	 */
	@Override
	public boolean route(Connection connection) {
		super.route(connection);
		
		boolean changed = false;
		if (connection instanceof FreeFormConnection) {
			ffc = (FreeFormConnection)connection;
			initialize();
			ConnectionRoute route = calculateRoute();
			if (route!=null) {
				changed = isRouteChanged(route);
				applyRoute(route);
			}
			dispose();
		}
		
		return changed;
	}

	/**
	 * Initialize the newPoints list and set the new start and end anchors.
	 */
	@Override
	protected void initialize() {
		super.initialize();
		
		movedBendpoint = getMovedBendpoint(ffc);
		if (movedBendpoint==null)
			movedBendpoint = getAddedBendpoint(ffc);
		removedBendpoint = getRemovedBendpoint(ffc);

		findAllShapes();
		if (movedBendpoint!=null) {
			for (ContainerShape shape : allShapes) {
				if (GraphicsUtil.contains(shape, movedBendpoint)) {
					movedBendpoint = null;
					break;
				}
			}
		}

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
		calculateAllowedAnchorSites();
	}
	
	/**
	 * Calculate route.
	 *
	 * @return the connection route
	 */
	protected ConnectionRoute calculateRoute() {
		if (isSelfConnection()) {
			return calculateSelfConnectionRoute();
		}

		ConnectionRoute route = new ConnectionRoute(this, 1, source, target);
		
		// relocate the source and target anchors for closest proximity to their
		// opposite shapes' centers
		AnchorUtil.moveAnchor(sourceAnchor, GraphicsUtil.getShapeCenter(target));
		AnchorUtil.moveAnchor(targetAnchor, GraphicsUtil.getShapeCenter(source));
		
		Point start = GraphicsUtil.createPoint(sourceAnchor);
		Point end = GraphicsUtil.createPoint(targetAnchor);

		route.add(start);
		for (int i=1; i<oldPoints.size() -1; ++i) {
			route.add(oldPoints.get(i));
		}
		route.add(end);
		
		oldPoints.clear();
		
		return route;
	}
	
	/**
	 * Route connections whose source and target are the same. This only
	 * reroutes the connection if there are currently no bendpoints in the
	 * connection - we don't want to reroute a connection that may have already
	 * been manually rerouted.
	 *
	 * @return true if the router has done any work
	 */
	protected ConnectionRoute calculateSelfConnectionRoute() {
		if (!isSelfConnection())
			return null;
		
		if (movedBendpoint==null) {
			if (ffc.getStart() != ffc.getEnd() && ffc.getBendpoints().size()>0) {
				// this connection starts and ends at the same node but it has different
				// anchor cuts and at least one bendpoint, which makes it likely that
				// this connection was already routed previously and the self-connection
				// is how the user wants it. But, check if the user wants to force routing.
				if (!forceRouting(ffc))
					return null;
			}
		}
		
		ILocation loc = peService.getLocationRelativeToDiagram(target);
		IDimension size = GraphicsUtil.calculateSize(target);
		Point p;
		
		p = Graphiti.getCreateService().createPoint(loc.getX()+size.getWidth()/2, loc.getY());
		FixPointAnchor topAnchor = (FixPointAnchor) ffc.getEnd();
		AnchorUtil.moveAnchor(topAnchor, p);

		p = Graphiti.getCreateService().createPoint(loc.getX()+size.getWidth(), loc.getY()+size.getHeight()/2);
		FixPointAnchor rightAnchor = (FixPointAnchor) ffc.getStart();
		AnchorUtil.moveAnchor(rightAnchor, p);

		// create the bendpoints that loop the connection around the top-right corner of the figure
		Point right = GraphicsUtil.createPoint(rightAnchor); // the point to the right of the node
		right.setX(right.getX() + 20);
		
		Point top = GraphicsUtil.createPoint(topAnchor); // point above the node
		top.setY(top.getY() - 20);

		Point corner = Graphiti.getCreateService().createPoint(right.getX(), top.getY()); // point above the top-right corner 
		
		// adjust these cuts to the moved or added bendpoint if possible
		p = movedBendpoint;
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
		route.add(GraphicsUtil.createPoint(rightAnchor));
		route.add(right);
		route.add(corner);
		route.add(top);
		route.add(GraphicsUtil.createPoint(topAnchor));

		return route;
	}
	
	/**
	 * Compare the connection's original start/end locations and all of its
	 * bendpoints with the newly calculated route.
	 *
	 * @param route the route
	 * @return true if the connection is different from the newly calculated
	 *         route
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
	 * Set the connection's new start/end point anchors and the newly calculated
	 * bendpoints.
	 *
	 * @param route the route
	 */
	protected void applyRoute(ConnectionRoute route) {
		route.apply(ffc, sourceAnchor, targetAnchor);
		DIUtils.updateDIEdge(ffc);
	}

	protected AnchorSite getNextAnchorSite(AnchorSite site) {
		switch (site) {
		case BOTTOM:
			return AnchorSite.LEFT;
		case LEFT:
			return AnchorSite.RIGHT;
		case RIGHT:
			return AnchorSite.TOP;
		case TOP:
			return AnchorSite.BOTTOM;
		case CENTER:
			return AnchorSite.CENTER;
		}
		return site;
	}

	/**
	 * Set a property in the given FreeFormConnection that represents the index
	 * of an existing bendpoint that has been moved by the user. This bendpoint
	 * is taken into consideration in the new routing calculations.
	 * 
	 * @param connection - FreeFormConnection to check
	 * @param index - index of a bendpoint. If this value is out of range, the
	 *            property will be remmoved from the connection
	 */
	public static void setMovedBendpoint(Connection connection, int index) {
		setInterestingBendpoint(connection, "moved.", index); //$NON-NLS-1$
	}

	/**
	 * Sets the added bendpoint.
	 *
	 * @param connection the connection
	 * @param index the index
	 */
	public static void setAddedBendpoint(Connection connection, int index) {
		setInterestingBendpoint(connection, "added.", index); //$NON-NLS-1$
	}

	/**
	 * Sets the removed bendpoint.
	 *
	 * @param connection the connection
	 * @param index the index
	 */
	public static void setRemovedBendpoint(Connection connection, int index) {
		setInterestingBendpoint(connection, "removed.", index); //$NON-NLS-1$
	}

	/**
	 * Sets the fixed bendpoint.
	 *
	 * @param connection the connection
	 * @param index the index
	 */
	public static void setFixedBendpoint(Connection connection, int index) {
		setInterestingBendpoint(connection, "fixed."+index+".", index); //$NON-NLS-1$ //$NON-NLS-2$
	}

	/**
	 * Sets the interesting bendpoint.
	 *
	 * @param connection the connection
	 * @param type the type
	 * @param index the index
	 */
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
	 * Return the "moved bendpoint" property that was previously set in the
	 * FreeFormConnection by setMovedBendpoint()
	 * 
	 * @param connection - FreeFormConnection to check
	 * @return a Graphiti Point in Diagram-relative coordinates, or null if the
	 *         property is not set
	 */
	public static Point getMovedBendpoint(Connection connection) {
		return getInterestingBendpoint(connection, "moved."); //$NON-NLS-1$
	}
	
	/**
	 * Gets the added bendpoint.
	 *
	 * @param connection the connection
	 * @return the added bendpoint
	 */
	public static Point getAddedBendpoint(Connection connection) {
		return getInterestingBendpoint(connection, "added."); //$NON-NLS-1$
	}
	
	/**
	 * Gets the removed bendpoint.
	 *
	 * @param connection the connection
	 * @return the removed bendpoint
	 */
	public static Point getRemovedBendpoint(Connection connection) {
		return getInterestingBendpoint(connection, "removed."); //$NON-NLS-1$
	}
	
	/**
	 * Gets the fixed bendpoint.
	 *
	 * @param connection the connection
	 * @param index the index
	 * @return the fixed bendpoint
	 */
	public static Point getFixedBendpoint(Connection connection, int index) {
		return getInterestingBendpoint(connection, "fixed."+index+"."); //$NON-NLS-1$ //$NON-NLS-2$
	}
	
	/**
	 * Gets the interesting bendpoint.
	 *
	 * @param connection the connection
	 * @param type the type
	 * @return the interesting bendpoint
	 */
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

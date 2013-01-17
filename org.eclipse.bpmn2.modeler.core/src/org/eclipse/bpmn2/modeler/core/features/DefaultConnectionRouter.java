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

import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil.AnchorLocation;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil.BoundaryAnchor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.datatypes.ILocation;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeService;

/**
 * A base class for Connection Routers. This router only ensures that self-connections
 * (connections whose source and target are the same) are visible by inserting bendpoints
 * in the FreeFormConnection to route it along the top-right edge of the source/target node.
 * 
 * This base class is also a container for common utility functions related to routing.
 */
public class DefaultConnectionRouter implements IConnectionRouter {
	
	protected static final IPeService peService = Graphiti.getPeService();
	protected static final IGaService gaService = Graphiti.getGaService();

	public enum Direction { UP, DOWN, LEFT, RIGHT };
	IFeatureProvider fp;

	public DefaultConnectionRouter(IFeatureProvider fp) {
		this.fp = fp;
	}

	@Override
	public boolean route(Connection connection) {
		return routeSelfConnection(connection);
	}

	/**
	 * Route connections whose source and target are the same. This only reroutes the
	 * connection if there are currently no bendpoints in the connection - we don't
	 * want to reroute a connection that may have already been manually rerouted.
	 * 
	 * @param connection - the connection to be routed
	 * @return true if the router has done any work
	 */
	protected boolean routeSelfConnection(Connection connection) {
		if (!(connection instanceof FreeFormConnection))
			return false;
		
		FreeFormConnection ffc = (FreeFormConnection) connection;
		AnchorContainer source = connection.getStart().getParent();
		AnchorContainer target = connection.getEnd().getParent();
		
		if (source != target)
			return false;
		
		if (connection.getStart() != connection.getEnd() && ffc.getBendpoints().size()>0)
			return false;
		
		// if the source and target figure are the same, create the necessary bendpoints so that
		// the connection loops from the figure's right edge to the its top edge
		ILocation loc = peService.getLocationRelativeToDiagram((Shape)target);
		IDimension size = GraphicsUtil.calculateSize(target);
		int x1 = loc.getX() + size.getWidth() + 20;
		int y1 = loc.getY() + size.getHeight() / 2;
		int x2 = loc.getX() + size.getWidth() / 2;
		int y2 = loc.getY() - 20;
		Point p1 = gaService.createPoint(x1, y1);
		Point p2 = gaService.createPoint(x1, y2);
		Point p3 = gaService.createPoint(x2, y2);
		EList<Point> bendpoints = ffc.getBendpoints();
		bendpoints.add(p1);
		bendpoints.add(p2);
		bendpoints.add(p3);

		Map<AnchorLocation, BoundaryAnchor> targetBoundaryAnchors = AnchorUtil.getBoundaryAnchors(target);
		BoundaryAnchor targetTop = targetBoundaryAnchors.get(AnchorLocation.TOP);
		BoundaryAnchor targetRight = targetBoundaryAnchors.get(AnchorLocation.RIGHT);
		ffc.setStart(targetRight.anchor);
		ffc.setEnd(targetTop.anchor);

		return true;
	}

	/**
	 * Check if the line segment defined by the two Points is horizontal.
	 * 
	 * @param p1
	 * @param p2
	 * @return true if the line segment is horizontal
	 */
	protected final boolean isHorizontal(Point p1, Point p2) {
		return Math.abs(p1.getY() - p2.getY()) <= 2;
	}

	/**
	 * Check if the line segment defined by the two Points is vertical.
	 * 
	 * @param p1
	 * @param p2
	 * @return true if the line segment is vertical
	 */
	protected final boolean isVertical(Point p1, Point p2) {
		return Math.abs(p1.getX() - p2.getX()) <= 2;
	}

	/**
	 * Check if the line segment defined by the two Points is neither horizontal nor vertical.
	 * 
	 * @param p1
	 * @param p2
	 * @return true if the line segment is slanted
	 */
	protected final boolean isSlanted(Point p1, Point p2) {
		return !isHorizontal(p1, p2) && !isVertical(p1,p2);
	}
	
	/**
	 * Given a new list of bendpoints for a FreeFormConnection, calculate the direction (UP, DOWN, LEFT or RIGHT)
	 * from at a given bendpoint that is orthogonal to the line segment before the bendpoint. If the given bendpoint
	 * is at the beginning or end of the list, use the orientation of the source/target figure to determine an
	 * orthogonal direction.
	 *  
	 * @param ffc - the FreeFormConnection
	 * @param bp - a list of Points that will eventually be used as the new bendpoints on the connection
	 * @param index - index into the above list of the point to check
	 * @return - a Direction: UP, DOWN, LEFT or RIGHT
	 * 
	 * @throws Exception if an orthogonal can not be determined.
	 */
	protected final Direction getDirection(FreeFormConnection ffc, List<Point> bp, int index) throws Exception {
		Point p1 = bp.get(index);
		
		if (index==0) {
			AnchorContainer source = ffc.getStart().getParent();
			switch (AnchorUtil.findNearestEdge((Shape)source, p1)) {
			case TOP:
				return Direction.UP;
			case BOTTOM:
				return Direction.DOWN;
			case LEFT:
				return Direction.LEFT;
			case RIGHT:
				return Direction.RIGHT;
			}
		}
		
		if (index==bp.size()-1) {
			AnchorContainer target = ffc.getEnd().getParent();
			switch (AnchorUtil.findNearestEdge((Shape)target, p1)) {
			case TOP:
				return Direction.DOWN;
			case BOTTOM:
				return Direction.UP;
			case LEFT:
				return Direction.RIGHT;
			case RIGHT:
				return Direction.LEFT;
			}
		}
		
		Point p2 = bp.get(index+1);
		Point p0 = bp.get(index-1);
		
		// [p0,p1] should form either a vertical or horizontal line because a
		// bendpoint was already added to ensure this condition. The new direction
		// should be orthogonal to [p0,p1] so we just need to determine positive or negative.
		if (isHorizontal(p0, p1)) {
			if (p2.getY() > p1.getY())
				return Direction.DOWN;
			else
				return Direction.UP;
		}
		else if (isVertical(p0,p1)) {
			if (p2.getX() > p1.getX())
				return Direction.RIGHT;
			else
				return Direction.LEFT;
		}
		if (index>0)
			return getDirection(ffc, bp, index-1);
		throw new Exception("getDirection: consecutive points are not orthogonal");
	}

	/**
	 * Check if the given connection's bendpoints are identical to the list of given points.
	 * This will also return TRUE if the connection's bendpoints list is in reverse order.
	 * 
	 * @param bp1 - a list of Points to compare against
	 * @param c - the FreeFormConnection whose bendpoints are to be tested for equality
	 * @return true if the connection's bendpoints is identical to the list of points in bp1
	 */
	protected final boolean bendpointsEqual(List<Point> bp1, Connection c) {
		if (!(c instanceof FreeFormConnection))
			return false;
		
		FreeFormConnection ffc2 = (FreeFormConnection)c;
		List<Point> bp2 = new ArrayList<Point>();
		bp2.add(GraphicsUtil.createPoint(ffc2.getStart()));
		bp2.addAll(ffc2.getBendpoints());
		bp2.add(GraphicsUtil.createPoint(ffc2.getEnd()));
		int size = bp1.size();
		if (bp2.size() != size)
			return false;
	
		int reverse = -1;
		Point p1 = bp1.get(0);
		Point p2 = bp2.get(0);
		if (GraphicsUtil.pointsEqual(p1,p2)) {
			reverse = 0;
		}
		else {
			// the connections may still be stacked on top of each other,
			// but their bendpoints may simply be reversed, i.e. their
			// directions are reversed
			p2 = bp2.get(size-1);
			if (GraphicsUtil.pointsEqual(p1,p2)) {
				reverse = size-1;
			}
		}
		if (reverse>=0 ) {
			for (int i=0; i<size; ++i) {
				p1 = bp1.get(i);
				if (reverse==0)
					p2 = bp2.get(i);
				else
					p2 = bp2.get(reverse-i);
				if (!GraphicsUtil.pointsEqual(p1,p2))
					return false;
			}
		}
		return true;
	}

	/**
	 * Check the newly calculated set of bendpoints for the given FreeFormConnection to see
	 * if they overlap with any other connections that are connected to the same endpoints.
	 * If this connection is stacked on top of another one, adjust the new bendpoints so they
	 * are offset horizontally and vertically so they can be easily be distinguished.
	 * 
	 * @param ffc - the connection for which the new bendpoints are being calculated
	 * @param bp - the proposed set of bendpoints
	 */
	protected void offsetStackedConnections(FreeFormConnection ffc, List<Point>bp) {
		int offset = 0;
		Anchor sourceAnchor = ffc.getStart();
		List<Connection> connections = new ArrayList<Connection>();
		connections.addAll(sourceAnchor.getOutgoingConnections());
		connections.addAll(sourceAnchor.getIncomingConnections());
		for (Connection c : connections) {
			if (c!=ffc) {
				if (bendpointsEqual(bp,c)) {
					// here's one
					offset += 10;
				}
			}
		}
		
		if (offset!=0) {
			try {
				Direction d1 = getDirection(ffc, bp, 0);
				offsetBendpoints(bp, d1, offset);
			}
			catch (Exception e) {
			}
		}
	}
	
	/**
	 * Offset the calculated list of bendpoints by the given amount and insert new
	 * bendpoints to shift the entire connection horizontally and vertically.
	 * 
	 * @param bp - the proposed set of bendpoints
	 * @param d - the shift direction
	 * @param offset - the shift amount
	 */
	protected void offsetBendpoints(List<Point> bp, Direction d, int offset) {
		Point p0 = bp.get(0);
		Point p1 = GraphicsUtil.createPoint(bp.get(1));
		Point p2 = GraphicsUtil.createPoint(bp.get( bp.size()-2 ));
		Point p3 = bp.get( bp.size()-1 );
		Point p;
		int x, y;
		
		// offset all of the intermediate points first
		for (int i=1; i<bp.size()-1; ++i) {
			p = bp.get(i);
			switch (d) {
			case UP:
			case DOWN:
				p.setX(p.getX() + offset);
				p.setY(p.getY() + offset);
				break;
			case LEFT:
			case RIGHT:
				p.setX(p.getX() - offset);
				p.setY(p.getY() + offset);
				break;
			}
		}
		switch (d) {
		case UP:
		case DOWN:
			// offset is horizontal
			x = p0.getX();
			y = p0.getY() + (p1.getY() - p0.getY()) / 3; 
			p = GraphicsUtil.createPoint(x, y);
			bp.add(1,p);
			x += offset;
			p = GraphicsUtil.createPoint(x, y);
			bp.add(2,p);
			x = p3.getX();
			y = p3.getY() + (p2.getY() - p3.getY()) / 3; 
			p = GraphicsUtil.createPoint(x, y);
			bp.add(bp.size()-1,p);
			x += offset;
			p = GraphicsUtil.createPoint(x, y);
			bp.add(bp.size()-2,p);
			break;
		case LEFT:
		case RIGHT:
			// offset is vertical
			x = p0.getX() + (p1.getX() - p0.getX()) / 3;
			y = p0.getY(); 
			p = GraphicsUtil.createPoint(x, y);
			bp.add(1,p);
			y += offset;
			p = GraphicsUtil.createPoint(x, y);
			bp.add(2,p);
			x = p3.getX() + (p2.getX() - p3.getX()) / 3;
			y = p3.getY(); 
			p = GraphicsUtil.createPoint(x, y);
			bp.add(bp.size()-1,p);
			y += offset;
			p = GraphicsUtil.createPoint(x, y);
			bp.add(bp.size()-2,p);
			break;
		}
		
	}
}

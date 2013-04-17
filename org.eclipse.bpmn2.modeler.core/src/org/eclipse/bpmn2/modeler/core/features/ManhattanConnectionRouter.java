/*******************************************************************************
 * Copyright (c) 2011, 2012 Red Hat, Inc.
 *  All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v20.html
 *
 * Contributors:
 * Red Hat, Inc. - initial API and implementation
 *
 * @author Bob Brodt
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.core.features;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil.AnchorLocation;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil.BoundaryAnchor;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil.LineSegment;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Shape;

/**
 * A Connection Router that constrains all line segments of a connection to be either
 * horizontal or vertical; thus, diagonal lines are split into two segments that are
 * horizontal and vertical.
 * 
 * This is a final class because it needs to ensure the routing info for
 * the connection is cleaned up when it's done, so we don't want to allow
 * this class to be subclassed.
 */
public final class ManhattanConnectionRouter extends BendpointConnectionRouter {

	protected LineSegment sourceTopEdge;
	protected LineSegment sourceBottomEdge;
	protected LineSegment sourceLeftEdge;
	protected LineSegment sourceRightEdge;

	protected LineSegment targetTopEdge;
	protected LineSegment targetBottomEdge;
	protected LineSegment targetLeftEdge;
	protected LineSegment targetRightEdge;
	
	enum Orientation {
		HORIZONTAL, VERTICAL, NONE
	};
	
	public ManhattanConnectionRouter(IFeatureProvider fp) {
		super(fp);
	}
	
	@Override
	protected ConnectionRoute calculateRoute() {
		
		if (isSelfConnection())
			return super.calculateRoute();
		
		LineSegment sourceEdges[] = GraphicsUtil.getEdges(source);
		sourceTopEdge = sourceEdges[0];
		sourceBottomEdge = sourceEdges[1];
		sourceLeftEdge = sourceEdges[2];
		sourceRightEdge = sourceEdges[3];

		LineSegment targetEdges[] = GraphicsUtil.getEdges(target);
		targetTopEdge = targetEdges[0];
		targetBottomEdge = targetEdges[1];
		targetLeftEdge = targetEdges[2];
		targetRightEdge = targetEdges[3];
		
		Point start;
		Point end = GraphicsUtil.createPoint(ffc.getEnd());
		Point middle = null;
		if (movedBendpoint!=null) {
			middle = movedBendpoint;
			findAllShapes();
			for (ContainerShape shape : allShapes) {
				if (GraphicsUtil.contains(shape, middle)) {
					middle = null;
					break;
				}
			}
		}
		
		findAllShapes();
		RouteSolver solver = new RouteSolver(fp, allShapes, source, target);
		if (solver.solve())
			return null;
		
		// The list of all possible routes. The shortest will be used.
		List<ConnectionRoute> allRoutes = new ArrayList<ConnectionRoute>();
		Map<AnchorLocation, BoundaryAnchor> sourceBoundaryAnchors = AnchorUtil.getBoundaryAnchors(source);
		Map<AnchorLocation, BoundaryAnchor> targetBoundaryAnchors = AnchorUtil.getBoundaryAnchors(target);
		
		if (sourceAnchor!=null) {
			// use ad-hoc anchor for source:
			// the connection's source location will remain fixed.
			start = GraphicsUtil.createPoint(sourceAnchor);
			if (targetAnchor!=null) {
				// use ad-hoc anchor for target:
				// the connection's target location will also remain fixed
				end = GraphicsUtil.createPoint(targetAnchor);
				calculateRoute(allRoutes, source,start,middle,target,end, Orientation.HORIZONTAL);
				calculateRoute(allRoutes, source,start,middle,target,end, Orientation.VERTICAL);
			}
			else {
				// use boundary anchors for target:
				// calculate 4 possible routes to the target,
				// ending at each of the 4 boundary anchors
				for (Entry<AnchorLocation, BoundaryAnchor> targetEntry : targetBoundaryAnchors.entrySet()) {
					end = GraphicsUtil.createPoint(targetEntry.getValue().anchor);
					calculateRoute(allRoutes, source,start,middle,target,end, Orientation.HORIZONTAL);
					calculateRoute(allRoutes, source,start,middle,target,end, Orientation.VERTICAL);
				}
			}
		}
		else {
			// use boundary anchors for source:
			// calculate 4 possible routes from the source,
			// starting at each of the 4 boundary anchors
			for (Entry<AnchorLocation, BoundaryAnchor> sourceEntry : sourceBoundaryAnchors.entrySet()) {
				start = GraphicsUtil.createPoint(sourceEntry.getValue().anchor);
				if (targetAnchor!=null) {
					// use ad-hoc anchor for target:
					// the connection's target location will also remain fixed
					end = GraphicsUtil.createPoint(targetAnchor);
					calculateRoute(allRoutes, source,start,middle,target,end, Orientation.HORIZONTAL);
					calculateRoute(allRoutes, source,start,middle,target,end, Orientation.VERTICAL);
				}
				else {
					// use boundary anchors for target:
					// calculate 4 possible routes to the target,
					// ending at each of the 4 boundary anchors
					for (Entry<AnchorLocation, BoundaryAnchor> targetEntry : targetBoundaryAnchors.entrySet()) {
						end = GraphicsUtil.createPoint(targetEntry.getValue().anchor);
						calculateRoute(allRoutes, source,start,middle,target,end, Orientation.HORIZONTAL);
						calculateRoute(allRoutes, source,start,middle,target,end, Orientation.VERTICAL);
					}
				}
			}
		}
		
		// pick the shortest route
		ConnectionRoute route = null;
		if (allRoutes.size()==1) {
			route = allRoutes.get(0);
			GraphicsUtil.dump("Only one valid route: "+route.toString());
		}
		else if (allRoutes.size()>1) {
			GraphicsUtil.dump("Optimizing Routes:\n------------------");
			for (ConnectionRoute r : allRoutes) {
				r.optimize();
			}

			GraphicsUtil.dump("Calculating Crossings:\n------------------");
			// Connection crossings only participate in determining the best route,
			// we don't actually try to correct a route crossing a connection.
			for (ConnectionRoute r : allRoutes) {
				if (r.points.size()>1) {
					Point p1 = r.points.get(0);
					for (int i=1; i<r.points.size(); ++i) {
						Point p2 = r.points.get(i);
						List<Connection> crossings = findCrossings(p1, p2);
						for (Connection c : crossings) {
							if (c!=this.connection)
								r.addCrossing(c, p1, p2);
						}
						ContainerShape shape = getCollision(p1, p2);
						if (shape!=null) {
							r.addCollision(shape, p1, p2);
						}
						
						p1 = p2;
					}

				}
				GraphicsUtil.dump("    "+r.toString());
			}

			GraphicsUtil.dump("Sorting Routes:\n------------------");
			Collections.sort(allRoutes);

			for (int i=0; i<allRoutes.size(); ++i) {
				ConnectionRoute r = allRoutes.get(i);
				GraphicsUtil.dump("    "+r.toString());
			}
			
			route = allRoutes.get(0);
		}
		
		return route;
	}
	
	protected ConnectionRoute calculateRoute(List<ConnectionRoute> allRoutes, Shape source, Point start, Point middle, Shape target, Point end, Orientation orientation) {
		
		ConnectionRoute route = new ConnectionRoute(this, allRoutes.size()+1, source,target);
		GraphicsUtil.dump("Calculating Route "+route.id);			

		if (middle!=null) {
			List<Point> departure = calculateDeparture(source, start, middle);
			List<Point> approach = calculateApproach(middle, target, end);

			route.points.addAll(departure);
			calculateEnroute(route, departure.get(departure.size()-1), middle, orientation);
			route.add(middle);
			calculateEnroute(route, middle,approach.get(0),orientation);
			route.points.addAll(approach);
		}
		else {
			List<Point> departure = calculateDeparture(source, start, end);
			List<Point> approach = calculateApproach(start, target, end);
			route.points.addAll(departure);
			calculateEnroute(route, departure.get(departure.size()-1), approach.get(0), orientation);
			route.points.addAll(approach);
		}
		
//		if (route.isValid())
			allRoutes.add(route);
		
		return route;
	}

//	protected List<Point> calculateDeparture(Shape source, Point start, Point end) {
//		AnchorLocation sourceEdge = AnchorUtil.findNearestEdge(source, start);
//		List<Point> cuts = new ArrayList<Point>();
//		
//		Point p = GraphicsUtil.createPoint(start);
//		Point m;
//		ContainerShape shape;
//		
//		switch (sourceEdge) {
//		case TOP:
//		case BOTTOM:
//			m = GraphicsUtil.getVertMidpoint(start,end,0.5);
//			shape = getCollision(start,m);
//			if (shape!=null) {
//				LineSegment seg = GraphicsUtil.findNearestEdge(shape, start);
//				m = GraphicsUtil.getVertMidpoint(start,seg.getStart(),0.75);
//			}
//			p.setY( m.getY() );
//			break;
//		case LEFT:
//		case RIGHT:
//			m = GraphicsUtil.getHorzMidpoint(start,end,0.5);
//			shape = getCollision(start,m);
//			if (shape!=null) {
//				LineSegment seg = GraphicsUtil.findNearestEdge(shape, start);
//				m = GraphicsUtil.getHorzMidpoint(start,seg.getStart(),0.75);
//			}
//			p.setX( m.getX() );
//			break;
//		default:
//			return cuts;
//		}
//		
//		cuts.add(start);
//		cuts.add(p);
//		
//		return cuts;
//	}
//	
//	protected List<Point> calculateApproach(Point start, Shape target, Point end) {
//		AnchorLocation targetEdge = AnchorUtil.findNearestEdge(target, end);
//		List<Point> cuts = new ArrayList<Point>();
//		
//		Point p = GraphicsUtil.createPoint(end);
//		Point m;
//		ContainerShape shape;
//		
//		switch (targetEdge) {
//		case TOP:
//		case BOTTOM:
//			m = GraphicsUtil.getVertMidpoint(start,end,0.5);
//			shape = getCollision(m,end);
//			if (shape!=null) {
//				LineSegment seg = GraphicsUtil.findNearestEdge(shape, start);
//				m = GraphicsUtil.getVertMidpoint(m,seg.getStart(),0.75);
//			}
//			p.setY( m.getY() );
//			break;
//		case LEFT:
//		case RIGHT:
//			m = GraphicsUtil.getHorzMidpoint(start,end,0.5);
//			shape = getCollision(m,end);
//			if (shape==null) {
//				LineSegment seg = GraphicsUtil.findNearestEdge(shape, start);
//				m = GraphicsUtil.getHorzMidpoint(m,seg.getStart(),0.75);
//			}
//			p.setX( m.getX() );
//			break;
//		default:
//			return cuts;
//		}
//		
//		cuts.add(p);
//		cuts.add(end);
//		
//		return cuts;
//	}
	
	protected List<Point> calculateDeparture(Shape source, Point start, Point end) {
		AnchorLocation sourceEdge = AnchorUtil.findNearestEdge(source, start);
		List<Point> route = new ArrayList<Point>();
		
		Point p = GraphicsUtil.createPoint(start);
		
		route.add(start);
		switch (sourceEdge) {
		case TOP:
			p.setY( start.getY() - margin );
			break;
		case BOTTOM:
			p.setY( start.getY() + margin );
			break;
		case LEFT:
			p.setX( start.getX() - margin );
			break;
		case RIGHT:
			p.setX( start.getX() + margin );
			break;
		default:
			return route;
		}
		route.add(p);
		
		return route;
	}
	
	protected List<Point> calculateApproach(Point start, Shape target, Point end) {
		AnchorLocation targetEdge = AnchorUtil.findNearestEdge(target, end);
		List<Point> route = new ArrayList<Point>();
		
		Point p = GraphicsUtil.createPoint(end);
		
		switch (targetEdge) {
		case TOP:
			p.setY( end.getY() - margin );
			break;
		case BOTTOM:
			p.setY( end.getY() + margin );
			break;
		case LEFT:
			p.setX( end.getX() - margin );
			break;
		case RIGHT:
			p.setX( end.getX() + margin );
			break;
		default:
			return route;
		}
		route.add(p);
		route.add(end);
		
		return route;
	}

	Point createPoint(int x, int y) {
		return GraphicsUtil.createPoint(x, y); 
	}
	
		protected boolean calculateEnroute(ConnectionRoute route, Point start, Point end, Orientation orientation) {
			if (GraphicsUtil.pointsEqual(start, end))
				return false;
			
			Point p;
			ContainerShape shape;
			
			// special case: if start and end can be connected with a horizontal or vertical line
			// and there's nothing in the way, we are done.
			if (!GraphicsUtil.isSlanted(start,end)) {
				shape = getCollision(start,end);
				if (shape==null) {
					return true;
				}
			}
			
			Direction dir = getNextDirection(orientation,start,end);
			
			switch (dir) {
			case LEFT:
				p = createPoint(end.getX(), start.getY());
				shape = getCollision(start,p);
				if (shape!=null) {
					if (shape==source || shape==target)
						return false;
					GraphicsUtil.dump("Route "+route.id+": LEFT collision=", shape);
					DetourPoints detour = getDetourPoints(shape);
					p = createPoint(detour.topRight.getX(), start.getY());
				}
				route.add(p);
				break;
			case RIGHT:
				p = createPoint(end.getX(), start.getY());
				shape = getCollision(start,p);
				if (shape!=null) {
					if (shape==source || shape==target)
						return false;
					GraphicsUtil.dump("Route "+route.id+": RIGHT: collision=", shape);
					DetourPoints detour = getDetourPoints(shape);
					p = createPoint(detour.topLeft.getX(), start.getY());
				}
				route.add(p);
				break;
			case UP:
				p = createPoint(start.getX(), end.getY());
				shape = getCollision(start,p);
				if (shape!=null) {
					if (shape==source || shape==target)
						return false;
					GraphicsUtil.dump("Route "+route.id+": UP: collision=", shape);
					DetourPoints detour = getDetourPoints(shape);
					p = createPoint(start.getX(), detour.bottomLeft.getY());
				}
				route.add(p);
				break;
			case DOWN:
				p = createPoint(start.getX(), end.getY());
				shape = getCollision(start,p);
				if (shape!=null) {
					if (shape==source || shape==target)
						return false;
					GraphicsUtil.dump("Route "+route.id+": DOWN: collision=", shape);
					DetourPoints detour = getDetourPoints(shape);
					p = createPoint(start.getX(), detour.topLeft.getY());
				}
				route.add(p);
				break;
			default:
				return false;
			}
			
			if (route.isValid() && !GraphicsUtil.pointsEqual(p, end)) {
				orientation = (dir == Direction.LEFT || dir == Direction.RIGHT) ?
						Orientation.HORIZONTAL : Orientation.VERTICAL;
				calculateEnroute(route,p,end,orientation);
			}
	/*
			int dx = Math.abs(end.getX() - start.getX());
			int dy = Math.abs(end.getY() - start.getY());
			if (orientation==Orientation.NONE) {
				if (dx>dy) {
					orientation = Orientation.HORIZONTAL;
				}
				else {
					orientation = Orientation.VERTICAL;
				}
			}
			
			if (orientation == Orientation.HORIZONTAL) {
				p = createPoint(end.getX(), start.getY());
				ContainerShape shape = getCollision(start,p);
				if (shape!=null) {
					DetourPoints detour = getDetourPoints(shape);
					// this should be a vertical segment - navigate around the shape
					// go up or down from here?
					boolean detourUp = end.getY() - start.getY() < 0;
					int dyTop = Math.abs(p.getY() - detour.topLeft.getY());
					int dyBottom = Math.abs(p.getY() - detour.bottomLeft.getY());
					if (dy<dyTop || dy<dyBottom)
						detourUp = dyTop < dyBottom;
					
					if (p.getX() > start.getX()) {
						p.setX( detour.topLeft.getX() );
						route.add(p);
						if (detourUp) {
							route.add(detour.topLeft);
							route.add(detour.topRight);
						}
						else {
							route.add(detour.bottomLeft);
							route.add(detour.bottomRight);
						}
	//					p = createPoint(detour.topRight.getX(), p.getY());
	//					route.add(p);
					}
					else {
						p.setX( detour.topRight.getX() );
						route.add(p);
						if (detourUp) {
							route.add(detour.topRight);
							route.add(detour.topLeft);
						}
						else {
							route.add(detour.bottomRight);
							route.add(detour.bottomLeft);
						}
	//					p = createPoint(detour.topLeft.getX(), p.getY());
	//					route.add(p);
					}
					p = route.get(route.size()-1);
				}
				else
					route.add(p);
			}
			else {
				p = createPoint(start.getX(), end.getY());
				ContainerShape shape = getCollision(start,p);
				if (shape!=null) {
					DetourPoints detour = getDetourPoints(shape);
					// this should be a horizontal segment - navigate around the shape
					// go left or right from here?
					boolean detourLeft = end.getX() - start.getX() < 0;
					int dxLeft = Math.abs(p.getX() - detour.topLeft.getX());
					int dxRight = Math.abs(p.getX() - detour.topRight.getX());
					if (dx<dxLeft || dx<dxRight)
						detourLeft = dxLeft < dxRight;
	
					if (p.getY() > start.getY()) {
						p.setY( detour.topLeft.getY() );
						route.add(p);
						if (detourLeft) {
							// go around to the left
							route.add(detour.topLeft);
							route.add(detour.bottomLeft);
						}
						else {
							// go around to the right
							route.add(detour.topRight);
							route.add(detour.bottomRight);
						}
	//					p = createPoint(p.getX(), detour.bottomLeft.getY());
	//					route.add(p);
					}
					else {
						p.setY( detour.bottomLeft.getY() );
						route.add(p);
						if (detourLeft) {
							route.add(detour.bottomLeft);
							route.add(detour.topLeft);
						}
						else {
							route.add(detour.bottomRight);
							route.add(detour.topRight);
						}
	//					p = createPoint(p.getX(), detour.topLeft.getY());
	//					route.add(p);
					}
					p = route.get(route.size()-1);
				}
				else
					route.add(p);
			}
			
			if (route.isValid())
				calculateEnroute(route,p,end,Orientation.NONE);
			*/
			return route.isValid();
		}
	
	protected Point getNextPoint(Direction dir, Point start, Point end) {
		Orientation orientation = getOrientation(dir);
		dir = getNextDirection(orientation,start,end);
		
		List<Point> points = new ArrayList<Point>();
		Point p = null;
		ContainerShape shape;
		
		if (getOrientation(dir) == Orientation.HORIZONTAL) {
			p = createPoint(end.getX(), start.getY());
		}
		else {
			p = createPoint(start.getX(), end.getY());
		}
		
		GraphicsUtil.dump("  Moving "+dir);
		shape = getCollision(start,p);
		if (shape!=null) {
			if (shape==source)
				return null;
			
			GraphicsUtil.dump("   collision=", shape);
		
			DetourPoints detour = getDetourPoints(shape);
			switch (dir) {
			case LEFT:
				p = createPoint(detour.topRight.getX(), start.getY());
				points.add(p);
				dir = getNextDirection(dir, start, p);
				switch (dir) {
				case UP:
					points.add(detour.topRight);
					break;
				case DOWN:
					points.add(detour.bottomRight);
					break;
				}
				break;
			case RIGHT:
				p = createPoint(detour.topLeft.getX(), start.getY());
				break;
			case UP:
				p = createPoint(start.getX(), detour.bottomLeft.getY());
				break;
			case DOWN:
				p = createPoint(start.getX(), detour.topLeft.getY());
				break;
			default:
				return null;
			}
		}
		else
			points.add(p);
		
		if (GraphicsUtil.pointsEqual(p, start))
			return null;
		
		return p;
	}
	
	protected Orientation getOrientation(Direction direction) {
		return (direction == Direction.LEFT || direction == Direction.RIGHT) ?
				Orientation.HORIZONTAL : Orientation.VERTICAL;
	}
	
	protected Direction getNextDirection(Direction previousDirection, Point p1, Point p2) {
		return getNextDirection( getOrientation(previousDirection), p1, p2);
	}
	
	protected Direction getNextDirection(Orientation previousOrientation, Point p1, Point p2) {
		int dx = p2.getX() - p1.getX();
		int dy = p2.getY() - p1.getY();
		Orientation preferredOrientation = (Math.abs(dx) >= Math.abs(dy)) ? Orientation.HORIZONTAL : Orientation.VERTICAL;
		
		if (dx>0) {
			// right
			if (dy>0) {
				// down
				if (preferredOrientation==Orientation.HORIZONTAL) {
					if (previousOrientation==Orientation.HORIZONTAL)
						return Direction.DOWN;
					return Direction.RIGHT;
				}
				else {
					if (previousOrientation==Orientation.VERTICAL)
						return Direction.RIGHT;
					return Direction.DOWN;
				}
			}
			else {
				// up
				if (preferredOrientation==Orientation.HORIZONTAL) {
					if (previousOrientation==Orientation.HORIZONTAL)
						return Direction.UP;
					return Direction.RIGHT;
				}
				else {
					if (previousOrientation==Orientation.VERTICAL)
						return Direction.RIGHT;
					return Direction.UP;
				}
			}
		}
		else {
			// left
			if (dy>0) {
				// down
				if (preferredOrientation==Orientation.HORIZONTAL) {
					if (previousOrientation==Orientation.HORIZONTAL)
						return Direction.DOWN;
					return Direction.LEFT;
				}
				else {
					if (previousOrientation==Orientation.VERTICAL)
						return Direction.LEFT;
					return Direction.DOWN;
				}
			}
			else {
				// up
				if (preferredOrientation==Orientation.HORIZONTAL) {
					if (previousOrientation==Orientation.HORIZONTAL)
						return Direction.UP;
					return Direction.LEFT;
				}
				else {
					if (previousOrientation==Orientation.VERTICAL)
						return Direction.LEFT;
					return Direction.UP;
				}
			}
		}
	}
	
	protected void updateConnection() {
		DIUtils.updateDIEdge(ffc);
	}
}

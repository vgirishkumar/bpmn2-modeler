/*******************************************************************************
 * Copyright (c) offset11, offset12 Red Hat, Inc.
 *  All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-voffset.html
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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil.AnchorLocation;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil.BoundaryAnchor;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil.LineSegment;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.core.utils.Tuple;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.FixPointAnchor;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
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
	
	static final int offset = 10;
	
	enum Orientation {
		HORIZONTAL, VERTICAL, NONE
	};
	
	class Route implements Comparable<Route>, Comparator<Route> {
		class Collision {
			Shape shape;
			Point start;
			Point end;
			
			public Collision(Shape shape, Point start, Point end) {
				this.shape = shape;
				this.start = start;
				this.end = end;
			}
			
			public String toString() {
				Object o = BusinessObjectUtil.getFirstBaseElement(shape);
				LineSegment ls = GraphicsUtil.findNearestEdge(shape, start);
				return ModelUtil.getDisplayName(o);
			}
		};
		int id;
		List<Point> points = new ArrayList<Point>();
		List<Collision> collisions = new ArrayList<Collision>();
		Shape source;
		Shape target;
		boolean valid = true;
		
		public Route(int id, Shape source, Shape target) {
			this.id = id;
			this.source = source;
			this.target = target;
		}

		public String toString() {
			String text;
			if (isValid()) {
				BoundaryAnchor sa = AnchorUtil.findNearestBoundaryAnchor(source, get(0));
				BoundaryAnchor ta = AnchorUtil.findNearestBoundaryAnchor(target, get(size()-1));
				text = id+": length="+getLength()+" # of points="+points.size()+
						" source anchor="+sa.locationType+" target anchor="+ta.locationType;
				if (collisions.size()>0) {
					text += " collisions=";
					Iterator<Collision> iter=collisions.iterator();
					while (iter.hasNext()) {
						Collision c = iter.next();
						text += "'" + c.toString() + "'";
						if (iter.hasNext())
							text += ", ";
					}
				}
			}
			else
				text = "not valid";
			return text;
		}
		
		public boolean add(Point newPoint) {
			for (Point p : points) {
				if (GraphicsUtil.pointsEqual(newPoint, p)) {
					valid = false;
					return false;
				}
			}
			points.add(newPoint);
			return true;
		}
		
		public Point get(int index) {
			return points.get(index);
		}
		
		public int size() {
			return points.size();
		}
		
		public void addCollision(Shape shape, Point start, Point end) {
			collisions.add( new Collision(shape, start, end) );
		}
		
		public List<Collision> getCollisions() {
			return collisions;
		}
		
		public boolean isValid() {
			if (valid)
				return getLength() < Integer.MAX_VALUE;
			return false;
		}
		
		public int getLength() {
			int length = 0;
			if (points.size()>1) {
				Point p1 = points.get(0);
				for (int i=1; i<points.size(); ++i) {
					Point p2 = points.get(i);
//					if (isHorizontal(p1,p2) || isVertical(p1,p2))
						length += (int)GraphicsUtil.getLength(p1, p2);
//					else 
//						return Integer.MAX_VALUE;
					p1 = p2;
				}
			}
			else {
				// this route could not be calculated
				return Integer.MAX_VALUE;
			}
			return length;
		}

		@Override
		public int compareTo(Route arg0) {
			return compare(this,arg0);
		}

		@Override
		public int compare(Route o1, Route o2) {
			if (o1.isValid()) {
				if (o2.isValid()) {
					List<Collision> c1 = o1.getCollisions();
					List<Collision> c2 = o2.getCollisions();
					int i = c1.size() - c2.size();
					if (i==0) {
						i = o1.points.size() - o2.points.size();
						if (i==0) {
							int l1 = o1.getLength();
							int l2 = o2.getLength();
							i = l1 - l2;
						}
						return i;
					}
					return i;
				}
				return -1;
			}
			else if (!o2.isValid())
				return 0;
			return 1;
		}
		
		public boolean optimize() {
			boolean changed = false;
			Point movedBendpoint = getMovedBendpoint(ffc);
			Point addedBendpoint = getAddedBendpoint(ffc);

			Point p1 = points.get(0);
			for (int i=1; i<points.size()-1; ++i) {
				Point p2 = points.get(i);
				if (i+1 < points.size()) {
					// remove unnecessary bendpoints: two consecutive
					// horizontal or vertical line segments, but not
					// the "added" or "removed" bendpoint
					if (movedBendpoint!=null && GraphicsUtil.pointsEqual(p2, movedBendpoint))
						continue;
					if (addedBendpoint!=null && GraphicsUtil.pointsEqual(p2, addedBendpoint))
						continue;
					
					Point p3 = points.get(i+1);
					if ((isVertical(p1,p2) && isVertical(p2,p3)) ||
							(isHorizontal(p1,p2) && isHorizontal(p2,p3))) {
						changed = true;
						points.remove(i);
						// look at these set of points again
						--i;
					}
				}
				p1 = p2;
			}
			return changed;
		}
	}

	public ManhattanConnectionRouter(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	protected void initialize() {
		Point movedBendpoint = getMovedBendpoint(ffc);
		Point addedBendpoint = getAddedBendpoint(ffc);

		if (addedBendpoint!=null || movedBendpoint!=null) {
			List<Point> points = new ArrayList<Point>();
			if (movedBendpoint!=null)
				points.add(movedBendpoint);
			if (addedBendpoint!=null)
				points.add(addedBendpoint);
//			for (Point p : ffc.getBendpoints()) {
//				points.add(p);
//				if (p==addedBendpoint || p==movedBendpoint)
//					break;
//			}
			createNewPoints(newStart, newEnd, points);
		}
		else {
			// calculate new start and end anchors by using the boundary anchors nearest to
			// the source and target shapes.
			Tuple<FixPointAnchor, FixPointAnchor> anchors = AnchorUtil.getSourceAndTargetBoundaryAnchors(
					newStart.getParent(), newEnd.getParent(), connection);
			newStart = anchors.getFirst();
			newEnd = anchors.getSecond();
			createNewPoints(newStart, newEnd, null);
		}
	}

	@Override
	public boolean route(Connection connection) {
		this.connection = connection;

		if (connection instanceof FreeFormConnection)
			ffc = (FreeFormConnection)connection;
		else
			return false;
		
		if (isSelfConnection())
			return routeSelfConnection();
		
		boolean changed = calculateRoute();
		updateConnection();
		finalizeConnection();
		return changed;
	}
	
	@Override
	protected boolean calculateRoute() {
		
		Point movedBendpoint = getMovedBendpoint(ffc);
		Point addedBendpoint = getAddedBendpoint(ffc);

		Shape source = (Shape) ffc.getStart().getParent();
		Shape target = (Shape) ffc.getEnd().getParent();

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
		if (movedBendpoint!=null)
			middle = movedBendpoint;
		else if (addedBendpoint!=null)
			middle = addedBendpoint;
		
		// The list of all possible routes. The shortest will be used.
		List<Route> allRoutes = new ArrayList<Route>();
		Map<AnchorLocation, BoundaryAnchor> sourceBoundaryAnchors = AnchorUtil.getBoundaryAnchors(source);
		Map<AnchorLocation, BoundaryAnchor> targetBoundaryAnchors = AnchorUtil.getBoundaryAnchors(target);
		Anchor sourceAnchor = null;
		if (AnchorUtil.useAdHocAnchors(source, ffc) && AnchorUtil.isAdHocAnchor(ffc.getStart()))
			sourceAnchor = ffc.getStart();
		Anchor targetAnchor = null;
		if (AnchorUtil.useAdHocAnchors(target, ffc) && AnchorUtil.isAdHocAnchor(ffc.getEnd()))
			targetAnchor = ffc.getEnd();
		
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
		Route route = null;
		if (allRoutes.size()==1) {
			route = allRoutes.get(0);
			GraphicsUtil.dump("Only one valid route: "+route.toString());
		}
		else {
			GraphicsUtil.dump("Optimizing Routes:\n------------------");
			for (Route r : allRoutes) {
				r.optimize();
				GraphicsUtil.dump("    "+r.toString());
			}

			GraphicsUtil.dump("Sorting Routes:\n------------------");
			Collections.sort(allRoutes);

			for (int i=0; i<allRoutes.size(); ++i) {
				Route r = allRoutes.get(i);
				GraphicsUtil.dump("    "+r.toString());
			}
			route = allRoutes.get(0);
		}
		
		if (route==null)
			return false;
		
		// set connection's source and target anchors if they are Boundary Anchors
		if (sourceAnchor==null) {
			BoundaryAnchor ba = AnchorUtil.findNearestBoundaryAnchor(source, route.get(0));
			sourceAnchor = ba.anchor;
			ffc.setStart(sourceAnchor);
		}
		
		if (targetAnchor==null) {
			// A route with only a starting point indicates that it could not be calculated.
			// In this case, make the connection a straight line from source to target.
			Point p;
			int last = route.size() - 1;
			if (last>0)
				p = route.get(last);
			else
				p = end;
			BoundaryAnchor ba = AnchorUtil.findNearestBoundaryAnchor(target, p);
			targetAnchor = ba.anchor;
			ffc.setEnd(targetAnchor);
		}
		
		// add the bendpoints
		ffc.getBendpoints().clear();
		for (int i=1; i<route.size()-1; ++i) {
			ffc.getBendpoints().add(route.get(i));
		}
		
		newPoints = route.points;
		
		return true;
	}
	
	protected Route calculateRoute(List<Route> allRoutes, Shape source, Point start, Point middle, Shape target, Point end, Orientation orientation) {
		
		Route route = new Route(allRoutes.size()+1, source,target);

		if (middle!=null) {
			List<Point> departure = calculateDeparture(source, start, middle);
			List<Point> approach = calculateApproach(middle, target, end);

			route.points.addAll(departure);
			calculateEnroute(route, departure.get(departure.size()-1), middle, orientation);
			route.points.add(middle);
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
		
		if (route.isValid())
			allRoutes.add(route);
		
		return route;
	}
	
	private Point getVertMidpoint(Point start, Point end) {
		Point m = GraphicsUtil.createPoint(start);
		int d = (end.getY() - start.getY()) / 2;
		m.setY(start.getY()+d);
		return m;
	}
	
	private Point getHorzMidpoint(Point start, Point end) {
		Point m = GraphicsUtil.createPoint(start);
		int d = (end.getX() - start.getX()) / 2;
		m.setX(start.getX()+d);
		return m;
	}
	
	protected List<Point> calculateDeparture(Shape source, Point start, Point end) {
		AnchorLocation sourceEdge = AnchorUtil.findNearestEdge(source, start);
		List<Point> route = new ArrayList<Point>();
		
		Point p = GraphicsUtil.createPoint(start);
		
		route.add(start);
		switch (sourceEdge) {
		case TOP:
			p.setY( start.getY() - offset );
			break;
		case BOTTOM:
			p.setY( start.getY() + offset );
			break;
		case LEFT:
			p.setX( start.getX() - offset );
			break;
		case RIGHT:
			p.setX( start.getX() + offset );
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
			p.setY( end.getY() - offset );
			break;
		case BOTTOM:
			p.setY( end.getY() + offset );
			break;
		case LEFT:
			p.setX( end.getX() - offset );
			break;
		case RIGHT:
			p.setX( end.getX() + offset );
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
	
	protected boolean calculateEnroute(Route route, Point start, Point end, Orientation orientation) {
		if (GraphicsUtil.pointsEqual(start, end))
			return false;
		
		Point p;
		
		// special case: if start and end can be connected with a horizontal or vertical line
		// check if there's a collision in the way. If so, we need to navigate around it.
		if (!isSlanted(start,end)) {
			ContainerShape shape = getCollision(start,end);
			if (shape==null) {
				return true;
			}
		}

//		Point horzPoint = createPoint(end.getX(), start.getY());
//		ContainerShape horzCollision = getCollision(start,horzPoint);
//		Point vertPoint = createPoint(start.getX(), end.getY());
//		ContainerShape vertCollision = getCollision(start,vertPoint);
		int dx = Math.abs(end.getX() - start.getX());
		int dy = Math.abs(end.getY() - start.getY());
		if (orientation==Orientation.NONE) {
			if (dx>dy) {
				orientation = Orientation.HORIZONTAL;
//				if (horzCollision!=null && vertCollision==null)
//					orientation = Orientation.VERTICAL;
			}
			else {
				orientation = Orientation.VERTICAL;
//				if (vertCollision!=null && horzCollision==null)
//					orientation = Orientation.HORIZONTAL;
			}
		}
		
		if (orientation == Orientation.HORIZONTAL) {
			p = createPoint(end.getX(), start.getY());
			ContainerShape shape = getCollision(start,p);
			if (shape!=null) {
				route.addCollision(shape, start, p);
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
				route.addCollision(shape, start, p);
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
		
		return route.isValid();
	}
	
	private boolean intersects(DetourPoints d1, DetourPoints d2) {
		return GraphicsUtil.intersects(
				d1.topLeft.getX(), d1.topLeft.getY(), d1.topRight.getX() - d1.topLeft.getX(), d1.bottomLeft.getY() - d1.topLeft.getY(),
				d2.topLeft.getX(), d2.topLeft.getY(), d2.topRight.getX() - d2.topLeft.getX(), d2.bottomLeft.getY() - d2.topLeft.getY()
		);
	}
	
	private boolean contains(DetourPoints d1, DetourPoints d2) {
		return	d1.topLeft.getX()<=d2.topLeft.getX() &&
				d1.topRight.getX()>=d2.topRight.getX() &&
				d1.topLeft.getY()<=d2.topLeft.getY() && 
				d1.bottomLeft.getY()>=d2.bottomLeft.getY(); 
	}
	
	private void merge(DetourPoints d1, DetourPoints d2) {
		d1.topLeft.setX( Math.min(d1.topLeft.getX(), d2.topLeft.getX()) );
		d1.topLeft.setY( Math.min(d1.topLeft.getY(), d2.topLeft.getY()) );
		d1.topRight.setX( Math.max(d1.topRight.getX(), d2.topRight.getX()) );
		d1.topRight.setY( Math.min(d1.topRight.getY(), d2.topRight.getY()) );
		d1.bottomLeft.setX( Math.min(d1.bottomLeft.getX(), d2.bottomLeft.getX()) );
		d1.bottomLeft.setY( Math.max(d1.bottomLeft.getY(), d2.bottomLeft.getY()) );
		d1.bottomRight.setX( Math.max(d1.bottomRight.getX(), d2.bottomRight.getX()) );
		d1.bottomRight.setY( Math.max(d1.bottomRight.getY(), d2.bottomRight.getY()) );
	}
	
	protected DetourPoints getDetourPoints(ContainerShape shape) {
		DetourPoints detour = new DetourPoints(shape, offset);
		if (allShapes==null)
			findAllShapes();

		for (int i=0; i<allShapes.size(); ++i) {
			ContainerShape s = allShapes.get(i);
			if (shape==s)
				continue;
			DetourPoints d = new DetourPoints(s, offset);
			if (intersects(detour, d) && !contains(detour,d)) {
				merge(detour, d);
				i = -1;
			}
		}

		return detour;
	}
	
	protected void finalizeConnection() {
	}
	
	protected boolean fixCollisions() {
		return false;
	}
	
	protected boolean calculateAnchors() {
		return false;
	}
	protected void updateConnection() {
		DIUtils.updateDIEdge(ffc);
	}
}

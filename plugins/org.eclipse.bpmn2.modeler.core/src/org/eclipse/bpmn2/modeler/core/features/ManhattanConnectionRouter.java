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
import java.util.List;

import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences.BPMNDIAttributeDefault;
import org.eclipse.bpmn2.modeler.core.utils.AnchorSite;
import org.eclipse.bpmn2.modeler.core.utils.AnchorType;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil.LineSegment;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
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
public class ManhattanConnectionRouter extends BendpointConnectionRouter {
	
	/** The Constant offset. */
	static final int offset = 10;
	
	/** The test route solver. */
	static boolean testRouteSolver = false;
	
	Orientation orientation;
	/**
	 * The Orientation of next line segment being calculated.
	 */
	enum Orientation {
		HORIZONTAL, 
		VERTICAL, 
		NONE
	};
	
	/**
	 * Instantiates a new manhattan connection router.
	 *
	 * @param fp the fp
	 */
	public ManhattanConnectionRouter(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	protected void initialize() {
		super.initialize();

		// prefer HORIZONTAL or VERTICAL layout?
		Bpmn2Preferences preferences = Bpmn2Preferences.getInstance(ffc);
		orientation = (preferences.getIsHorizontal() == BPMNDIAttributeDefault.ALWAYS_FALSE) ?
				Orientation.VERTICAL :
				Orientation.HORIZONTAL;
	}
	
	@Override
	public boolean routingNeeded(Connection connection) {
		if (connection instanceof FreeFormConnection) {
			FreeFormConnection ffc = (FreeFormConnection) connection;
			
			Point p0 = GraphicsUtil.createPoint(ffc.getStart());
			Point p1;
			for (int i=0; i<ffc.getBendpoints().size(); ++i) {
				p1 = ffc.getBendpoints().get(i);
				if (!(GraphicsUtil.isHorizontal(p0, p1) || GraphicsUtil.isVertical(p0, p1))) {
					return true;
				}
				p0 = p1;
			}
			p1 = GraphicsUtil.createPoint(ffc.getEnd());
			if (!(GraphicsUtil.isHorizontal(p0, p1) || GraphicsUtil.isVertical(p0, p1))) {
				return true;
			}
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.features.BendpointConnectionRouter#calculateRoute()
	 */
	@Override
	protected ConnectionRoute calculateRoute() {
		
		if (isSelfConnection())
			return super.calculateRoute();
		
GraphicsUtil.debug = false;
		
		boolean initialUpdate = (peService.getPropertyValue(ffc, GraphitiConstants.INITIAL_UPDATE) != null);
		Point start = null;
		Point end = null;
		Point middle = null;
		if (movedBendpoint!=null) {
			middle = movedBendpoint;
		}

		if (testRouteSolver) {
			RouteSolver solver = new RouteSolver(fp, allShapes);
			boolean b = solver.solve(source, target);
//			if (b) return null;
		}
		
		// The list of all possible routes. The shortest will be used.
		List<ConnectionRoute> allRoutes = new ArrayList<ConnectionRoute>();
		
		// Calculate all possible routes: this iterates over every permutation
		// of 4 sides for both source and target shape
		AnchorSite sourceSite = AnchorSite.getSite(sourceAnchor);
		AnchorSite targetSite = AnchorSite.getSite(targetAnchor);
		AnchorSite initialSourceSite = sourceSite;
		AnchorSite initialTargetSite = targetSite;
		Point initialSourceLocation = GraphicsUtil.createPoint(sourceAnchor);
		Point initialTargetLocation = GraphicsUtil.createPoint(targetAnchor);
		for (int i=0; i<16; ++i) {
			if (shouldCalculate(sourceSite, targetSite)) {
				AnchorUtil.moveAnchor(sourceAnchor, initialSourceLocation);
				AnchorUtil.moveAnchor(targetAnchor, initialTargetLocation);
				AnchorSite.setSite(sourceAnchor, sourceSite);
				AnchorUtil.adjustAnchors(source);
				AnchorSite.setSite(targetAnchor, targetSite);
				AnchorUtil.adjustAnchors(target);
				
				ConnectionRoute route = new ConnectionRoute(this, allRoutes.size()+1, source,target);

				// Introduce some hysteresis by favoring routes that do not have
				// to change the Anchor Site. Changing Anchor Sites from
				// one edge of an Activity shape to another may cause a relocation
				// of existing anchors on the Activity which may result in having
				// to recalculate the route for those connections.
//				if (sourceSite!=initialSourceSite) {
//					if (targetSite!=initialTargetSite)
//						route.setRank(1);
//					else
//						route.setRank(1);
//				}
//				else if (targetSite!=initialTargetSite) {
//					route.setRank(1);
//				}
				
				// Get the starting and ending points on the (possibly relocated)
				// source and target anchors.
				start = GraphicsUtil.createPoint(sourceAnchor);
				end = GraphicsUtil.createPoint(targetAnchor);
				
				// If either the source or target anchor is a "Pool" anchor
				// (i.e. attached to a Pool) then try to move it so it lines
				// up either vertically or horizontally with the other anchor.
				// This is only done for these conditions:
				// 1. this is an initial update, i.e. the Connection has just been created
				// 2. the Connection was manually moved
				// 3. the edge to which the Connection was attached has changed
				if (initialUpdate || middle!=null ||
						sourceSite!=initialSourceSite || targetSite!=initialTargetSite) {
					int rank = 0;
					if (AnchorType.getType(targetAnchor) == AnchorType.POOL) {
						if (middle!=null)
							AnchorUtil.moveAnchor(targetAnchor, middle);
						else
							AnchorUtil.moveAnchor(targetAnchor, sourceAnchor);
						end = GraphicsUtil.createPoint(targetAnchor);
						if (targetSite!=initialTargetSite)
							++rank;
					}
					if (AnchorType.getType(sourceAnchor) == AnchorType.POOL) {
						if (middle!=null)
							AnchorUtil.moveAnchor(sourceAnchor, middle);
						else
							AnchorUtil.moveAnchor(sourceAnchor, targetAnchor);
						start = GraphicsUtil.createPoint(sourceAnchor);
						if (sourceSite!=initialSourceSite)
							++rank;
					}
					route.setRank(rank);
				}
				route.setSourceAnchor(sourceAnchor);
				route.setTargetAnchor(targetAnchor);

				calculateRoute(route, source,start,middle,target,end, orientation);

				allRoutes.add(route);
			}

			if ((i % 4)==0) {
				sourceSite = getNextAnchorSite(sourceSite);
			}
			else {
				targetSite = getNextAnchorSite(targetSite);
			}
		}
		
//		System.out.println("# Routes="+allRoutes.size());
		
		// pick the shortest route
		ConnectionRoute route = null;
		if (allRoutes.size()==1) {
			route = allRoutes.get(0);
			optimize(route);
			GraphicsUtil.dump("Only one valid route: "+route.toString()); //$NON-NLS-1$
		}
		else if (allRoutes.size()>1) {
			GraphicsUtil.dump("Optimizing Routes:\n------------------"); //$NON-NLS-1$
			for (ConnectionRoute r : allRoutes) {
				optimize(r);
			}

			GraphicsUtil.dump("Calculating Crossings:\n------------------"); //$NON-NLS-1$
			// Connection crossings only participate in determining the best route,
			// we don't actually try to correct a route crossing a connection.
			for (ConnectionRoute r : allRoutes) {
				if (r.getPoints().size()>1) {
					Point p1 = r.getPoints().get(0);
					for (int i=1; i<r.getPoints().size(); ++i) {
						Point p2 = r.getPoints().get(i);
						List<Connection> crossings = findCrossings(connection, p1, p2);
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
				GraphicsUtil.dump("    "+r.toString()); //$NON-NLS-1$
			}

			GraphicsUtil.dump("Sorting Routes:\n------------------"); //$NON-NLS-1$
			Collections.sort(allRoutes);
			
			drawConnectionRoutes(allRoutes);

			route = allRoutes.get(0);
		}
		
		return route;
	}
	
	/**
	 * Calculate route.
	 *
	 * @param allRoutes the all routes
	 * @param source the source
	 * @param start the start
	 * @param middle the middle
	 * @param target the target
	 * @param end the end
	 * @param orientation the orientation
	 * @return the connection route
	 */
	protected ConnectionRoute calculateRoute(ConnectionRoute route, Shape source, Point start, Point middle, Shape target, Point end, Orientation orientation) {
		
		if (middle!=null) {
			List<Point> departure = calculateDeparture(source, start, middle);
			List<Point> approach = calculateApproach(middle, target, end);

			route.getPoints().addAll(departure);
			if (calculateEnroute(route, departure.get(departure.size()-1), middle, orientation)) {
				route.getPoints().add(middle);
				if (calculateEnroute(route, middle,approach.get(0),orientation)) {
					route.getPoints().addAll(approach);
				}
				else
					route.getPoints().add(end);
			}
			else
				route.getPoints().add(end);
		}
		else {
			List<Point> departure = calculateDeparture(source, start, end);
			List<Point> approach = calculateApproach(start, target, end);
			if (departure.size()==2 && approach.size()==2 && 
					GraphicsUtil.pointsEqual(departure.get(1), approach.get(0))) {
				route.getPoints().add(start);
				route.getPoints().add(end);
			}
			else {
				route.getPoints().addAll(departure);
				calculateEnroute(route, departure.get(departure.size()-1), approach.get(0), orientation);
				route.getPoints().addAll(approach);
			}
		}
		
		return route;
	}
	
	private Point getVertMidpoint(Point start, Point end, double fract) {
		Point m = GraphicsUtil.createPoint(start);
		int d = (int)(fract * (double)(end.getY() - start.getY()));
		m.setY(start.getY()+d);
		return m;
	}
	
	private Point getHorzMidpoint(Point start, Point end, double fract) {
		Point m = GraphicsUtil.createPoint(start);
		int d = (int)(fract * (double)(end.getX() - start.getX()));
		m.setX(start.getX()+d);
		return m;
	}

	/**
	 * Calculate departure.
	 *
	 * @param source the source
	 * @param start the start
	 * @param end the end
	 * @return the list
	 */
	protected List<Point> calculateDeparture(Shape source, Point start, Point end) {
		AnchorSite sourceEdge = AnchorSite.getNearestEdge(source, start, end);
		List<Point> points = new ArrayList<Point>();
		
		Point p = GraphicsUtil.createPoint(start);
		Point m = end;
		ContainerShape shape;
		
		switch (sourceEdge) {
		case TOP:
		case BOTTOM:
			for (;;) {
				m = getVertMidpoint(start,m,0.45);
				shape = getCollision(start,m);
				if (shape==null || Math.abs(m.getY()-start.getY())<=offset) {
					if (shape!=null) {
						// still collision?
						if (sourceEdge==AnchorSite.BOTTOM)
							m.setY(start.getY() + offset);
						else
							m.setY(start.getY() - offset);
					}
					break;
				}
			}
			p.setY( m.getY() );
			break;
		case LEFT:
		case RIGHT:
			for (;;) {
				m = getHorzMidpoint(start,m,0.45);
				shape = getCollision(start,m);
				if (shape==null || Math.abs(m.getX()-start.getX())<=offset) {
					if (shape!=null) {
						// still collision?
						if (sourceEdge==AnchorSite.RIGHT)
							m.setX(start.getX() + offset);
						else
							m.setX(start.getX() - offset);
					}
					break;
				}
			}
			p.setX( m.getX() );
			break;
		default:
			return points;
		}
		
		points.add(start);
		points.add(p);
		
		return points;
	}
	
	/**
	 * Calculate approach.
	 *
	 * @param start the start
	 * @param target the target
	 * @param end the end
	 * @return the list
	 */
	protected List<Point> calculateApproach(Point start, Shape target, Point end) {
		AnchorSite targetSite = AnchorSite.getNearestEdge(target, start, end);
		List<Point> points = new ArrayList<Point>();
		
		Point p = GraphicsUtil.createPoint(end);
		Point m = start;

		switch (targetSite) {
		case TOP:
		case BOTTOM:
			for (;;) {
				m = getVertMidpoint(m,end,0.45);
				ContainerShape shape = getCollision(m,end);
				if (shape==null || shape==target || Math.abs(m.getY()-end.getY())<=offset) {
					if (shape!=null) {
						// still collision?
						if (targetSite==AnchorSite.BOTTOM)
							m.setY(end.getY() + offset);
						else
							m.setY(end.getY() - offset);
					}
					break;
				}
			}
			p.setY( m.getY() );
			break;
		case LEFT:
		case RIGHT:
			for (;;) {
				m = getHorzMidpoint(m,end,0.45);
				ContainerShape shape = getCollision(m,end);
				if (shape==null || shape==target || Math.abs(m.getX()-end.getX())<=offset) {
					if (shape!=null) {
						// still collision?
						if (targetSite==AnchorSite.RIGHT)
							m.setX(end.getX() + offset);
						else
							m.setX(end.getX() - offset);
					}
					break;
				}
			}
			p.setX( m.getX() );
			break;
		default:
			points.add(p);
			return points;
		}
		
		points.add(p);
		points.add(end);
		
		return points;
	}

	/**
	 * Creates the point.
	 *
	 * @param x the x
	 * @param y the y
	 * @return the point
	 */
	Point createPoint(int x, int y) {
		return GraphicsUtil.createPoint(x, y); 
	}
	
	/**
	 * Calculate enroute.
	 *
	 * @param route the route
	 * @param start the start
	 * @param end the end
	 * @param orientation the orientation
	 * @return true, if successful
	 */
	protected boolean calculateEnroute(ConnectionRoute route, Point start, Point end, Orientation orientation) {
		if (GraphicsUtil.pointsEqual(start, end))
			return false;
		
		Point p;
		
		// special case: if start and end can be connected with a horizontal or vertical line
		// check if there's a collision in the way. If so, we need to navigate around it.
		if (!GraphicsUtil.isSlanted(start,end)) {
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
//				route.addCollision(shape, start, p);
				DetourPoints detour = getDetourPoints(shape);
				// this should be a vertical segment - navigate around the shape
				// go up or down from here?
				boolean detourUp = end.getY() - start.getY() < 0;
//				int dyTop = Math.abs(p.getY() - detour.topLeft.getY());
//				int dyBottom = Math.abs(p.getY() - detour.bottomLeft.getY());
//				if (dy<dyTop || dy<dyBottom)
//					detourUp = dyTop < dyBottom;
				
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
//				route.addCollision(shape, start, p);
				DetourPoints detour = getDetourPoints(shape);
				// this should be a horizontal segment - navigate around the shape
				// go left or right from here?
				boolean detourLeft = end.getX() - start.getX() < 0;
//				int dxLeft = Math.abs(p.getX() - detour.topLeft.getX());
//				int dxRight = Math.abs(p.getX() - detour.topRight.getX());
//				if (dx<dxLeft || dx<dxRight)
//					detourLeft = dxLeft < dxRight;

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
		
		if (route.isValid()){
			if (!calculateEnroute(route,p,end,orientation))
				return false;
		}
		
		return route.isValid();
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
			if (detour.intersects(d) && !detour.contains(d)) {
				detour.merge(d);
				i = -1;
			}
		}

		return detour;
	}
	
	protected void optimize(ConnectionRoute route) {

		route.addSpecial(movedBendpoint);
		
		route.optimize();
		
		int size = route.getPoints().size();
		if (size>1) {
			// Discourage routes containing starting or ending segments that touch
			// the source or target shape - it just looks ugly.
			LineSegment sourceEdges[] = GraphicsUtil.getEdges(source);
			Point p0 = route.getPoints().get(0);
			Point p1 = route.getPoints().get(1);
			AnchorSite sourceSite = route.getSourceAnchorSite();
			if (sourceSite==AnchorSite.LEFT || sourceSite==AnchorSite.RIGHT) {
				int x = sourceEdges[sourceSite.ordinal()].getStart().getX();
				if (GraphicsUtil.isVertical(p0, p1) && p0.getX()==x)
					route.setRank(3);
			}
			else if (sourceSite==AnchorSite.TOP || sourceSite==AnchorSite.BOTTOM) {
				int y = sourceEdges[sourceSite.ordinal()].getStart().getY();
				if (GraphicsUtil.isHorizontal(p0, p1) && p0.getY()==y)
					route.setRank(3);
			}
			if (size>2) {
				if (GraphicsUtil.getLength(p0, p1) < offset) {
					p0 = route.getPoints().get(1);
					p1 = route.getPoints().get(2);
					if (sourceSite==AnchorSite.LEFT || sourceSite==AnchorSite.RIGHT) {
						if (GraphicsUtil.isVertical(p0, p1))
							route.setRank(3);
					}
					else if (sourceSite==AnchorSite.TOP || sourceSite==AnchorSite.BOTTOM) {
						if (GraphicsUtil.isHorizontal(p0, p1))
							route.setRank(3);
					}
				}
				// Same as above, but for the target shape
				LineSegment targetEdges[] = GraphicsUtil.getEdges(target);
				p0 = route.getPoints().get(size-2);
				p1 = route.getPoints().get(size-1);
				AnchorSite targetSite = route.getTargetAnchorSite();
				if (targetSite==AnchorSite.LEFT || targetSite==AnchorSite.RIGHT) {
					int x = targetEdges[targetSite.ordinal()].getStart().getX();
					if (GraphicsUtil.isVertical(p0, p1) && p0.getX()==x)
						route.setRank(3);
				}
				else if (targetSite==AnchorSite.TOP || targetSite==AnchorSite.BOTTOM) {
					int y = targetEdges[targetSite.ordinal()].getStart().getY();
					if (GraphicsUtil.isHorizontal(p0, p1) && p0.getY()==y)
						route.setRank(3);
				}
				if (GraphicsUtil.getLength(p0, p1) < offset) {
					p0 = route.getPoints().get(size-3);
					p1 = route.getPoints().get(size-2);
					if (targetSite==AnchorSite.LEFT || targetSite==AnchorSite.RIGHT) {
						if (GraphicsUtil.isVertical(p0, p1))
							route.setRank(3);
					}
					else if (targetSite==AnchorSite.TOP || targetSite==AnchorSite.BOTTOM) {
						if (GraphicsUtil.isHorizontal(p0, p1))
							route.setRank(3);
					}
				}
			}
		}
	}
}

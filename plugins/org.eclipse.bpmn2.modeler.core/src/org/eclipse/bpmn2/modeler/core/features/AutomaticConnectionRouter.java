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
import java.util.List;

import org.eclipse.bpmn2.modeler.core.utils.AnchorLocation;
import org.eclipse.bpmn2.modeler.core.utils.AnchorType;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;

/**
 * Router for connections that can have user-settable bendpoints. The route is
 * calculated such that it is the most direct line between source and target,
 * but avoids collisions by navigating around shapes.
 */
public class AutomaticConnectionRouter extends BendpointConnectionRouter {

	/** The minimum distance between a bendpoint and a shape when rerouting to avoid collisions. */
	protected static final int margin = 10;
	
	/**
	 * Instantiates a new bendpoint connection router.
	 *
	 * @param fp the Feature Provider
	 */
	public AutomaticConnectionRouter(IFeatureProvider fp) {
		super(fp);
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
		
		Point start = null;
		Point end = null;
		Point middle = null;
		if (movedBendpoint!=null) {
			middle = movedBendpoint;
		}
		
		// The list of all possible routes. The shortest will be used.
		List<ConnectionRoute> allRoutes = new ArrayList<ConnectionRoute>();
		
		// Calculate all possible routes: this iterates over every permutation
		// of 4 sides for both source and target shape
		for (int i=0; i<16; ++i) {
			ConnectionRoute route = new ConnectionRoute(this, allRoutes.size()+1, source,target);
			route.setSourceAnchor(sourceAnchor);
			route.setTargetAnchor(targetAnchor);

			start = GraphicsUtil.createPoint(sourceAnchor);
			end = GraphicsUtil.createPoint(targetAnchor);
			
			// if either the source or target anchor is a "Pool" anchor (i.e. attached to a Pool)
			// then try to move it so it lines up either vertically or horizontally with the other
			// anchor.
			if (AnchorType.getType(sourceAnchor) == AnchorType.POOL) {
				AnchorUtil.moveAnchor(sourceAnchor, middle==null ? end : middle);
				start = GraphicsUtil.createPoint(sourceAnchor);
			}
			else if (AnchorType.getType(targetAnchor) == AnchorType.POOL) {
				AnchorUtil.moveAnchor(targetAnchor, start);
				end = GraphicsUtil.createPoint(targetAnchor);
			}
			
			calculateRoute(route, start,middle,end);
			allRoutes.add(route);
			
			if ((i % 4)==0) {
				switch (AnchorLocation.getLocation(sourceAnchor)) {
				case BOTTOM:
					AnchorLocation.setLocation(sourceAnchor, AnchorLocation.LEFT);
					break;
				case CENTER:
					break;
				case LEFT:
					AnchorLocation.setLocation(sourceAnchor, AnchorLocation.RIGHT);
					break;
				case RIGHT:
					AnchorLocation.setLocation(sourceAnchor, AnchorLocation.TOP);
					break;
				case TOP:
					AnchorLocation.setLocation(sourceAnchor, AnchorLocation.BOTTOM);
					break;
				default:
					break;
				}
				AnchorUtil.relocateAnchors(source);
			}
			else {
				switch (AnchorLocation.getLocation(targetAnchor)) {
				case BOTTOM:
					AnchorLocation.setLocation(targetAnchor, AnchorLocation.LEFT);
					break;
				case CENTER:
					break;
				case LEFT:
					AnchorLocation.setLocation(targetAnchor, AnchorLocation.RIGHT);
					break;
				case RIGHT:
					AnchorLocation.setLocation(targetAnchor, AnchorLocation.TOP);
					break;
				case TOP:
					AnchorLocation.setLocation(targetAnchor, AnchorLocation.BOTTOM);
					break;
				default:
					break;
				}
				AnchorUtil.relocateAnchors(target);
			}
		}
		
		// pick the shortest route
		ConnectionRoute route = null;
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
			GraphicsUtil.dump("    "+r.toString()); //$NON-NLS-1$
		}

		GraphicsUtil.dump("Sorting Routes:\n------------------"); //$NON-NLS-1$
		Collections.sort(allRoutes);
		
		drawConnectionRoutes(allRoutes);

		route = allRoutes.get(0);
		
		return route;
	}
	
	protected ConnectionRoute calculateRoute(ConnectionRoute route, Point start, Point middle, Point end) {
		if (middle!=null) {
			calculateRoute(route, start, middle);
			calculateRoute(route, middle, end);
		}
		else {
			calculateRoute(route, start, end);
		}
		route.add(end);
		
		return route;
	}
	
	protected ConnectionRoute calculateRoute(ConnectionRoute route, Point start, Point end) {
		
		route.add(start);
		
		Point p1 = start;
		Point p2 = end;
		while (true) {
			ContainerShape shape = getCollision(p1,p2);
			if (shape==null || shape==target || shape==source) {
				break;
			}
			// navigate around this shape
			DetourPoints detour = new DetourPoints(shape, margin);
			for (Point d : detour.calculateDetour(p1, p2)) {
				route.add(d);
			}
			p1 = route.get(route.size() - 1);
		}
		
		return route;
	}

	protected void optimize(ConnectionRoute route) {
		route.addSpecial(movedBendpoint);
		route.optimize();
	}
}

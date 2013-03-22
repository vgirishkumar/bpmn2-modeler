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

import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.Tuple;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.FixPointAnchor;

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
	
	public ManhattanConnectionRouter(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	protected void initialize() {
		Point movedBendpoint = getMovedBendpoint(ffc);
		Point addedBendpoint = getAddedBendpoint(ffc);

		if (addedBendpoint!=null || movedBendpoint!=null) {
//			createNewPoints(newStart, newEnd, ffc.getBendpoints());
			List<Point> points = new ArrayList<Point>();
			for (Point p : ffc.getBendpoints()) {
				points.add(p);
				if (p==addedBendpoint || p==movedBendpoint)
					break;
			}
			createNewPoints(newStart, newEnd, points);
		}
		else {
			// calculate new start and end anchors by using the boundary anchors nearest to
			// the source and target shapes.
			Tuple<FixPointAnchor, FixPointAnchor> anchors = AnchorUtil.getSourceAndTargetBoundaryAnchors(
					newStart.getParent(), newEnd.getParent(), null);
			newStart = anchors.getFirst();
			newEnd = anchors.getSecond();
			createNewPoints(newStart, newEnd, null);
		}
	}
	
	@Override
	protected boolean calculateRoute() {
		
		Point movedBendpoint = getMovedBendpoint(ffc);
		Point addedBendpoint = getAddedBendpoint(ffc);

		// let BendpointRouter do its thing
		boolean changed = super.calculateRoute();
		
		Point p;
		Point p0 = null;
		Point p1 = null;
		Point p2 = null;
		Direction d1, d2;
		final int offset = 20;
		
		for (int tries=0; tries<4; ++tries) {
			for (int i=0; i<newPoints.size()-1; ++i) {
				p0 = i>0 ? newPoints.get(i-1) : null;
				p1 = newPoints.get(i);
				p2 = newPoints.get(i+1);
	
				if (isSlanted(p1, p2)) {
					Point m = GraphicsUtil.getMidpoint(p1, p2);
					try {
						d1 = getDirection(i); 
						d2 = getDirection(i+1);
					}
					catch (Exception e) {
						break;
					}
					switch (d1) {
					case UP:
					case DOWN:
						if (p0!=null) {
							if (i+2==newPoints.size()) {
								// this is the last point in the array, so if p2's direction is orthogonal
								// to p1 we need to insert a couple of bendpoints here before the end
								if (d2==Direction.LEFT || d2==Direction.RIGHT){
									insertPoint(++i, m.getX(),p0.getY());
									insertPoint(++i, m.getX(),p2.getY());
									break;
								}
							}
							p1.setX(p2.getX());
							p1.setY(p0.getY());
						}
						else {
							switch (d2) {
							case LEFT:
							case RIGHT:
								if (d1==Direction.DOWN && p2.getY() < p1.getY()) {
									insertPoint(i+1, p1.getX(),p1.getY() + offset);
									insertPoint(i+2, m.getX(),p1.getY() + offset);
									break;
								}
								if (d1==Direction.UP && p2.getY() > p1.getY()) {
									insertPoint(i+1, p1.getX(),p1.getY() - offset);
									insertPoint(i+2, m.getX(),p1.getY() - offset);
									break;
								}
								insertPoint(i+1, p1.getX(),p2.getY());
								break;
							case UP:
								if (d1==Direction.DOWN) {
									insertPoint(i+1, p1.getX(),p1.getY() + offset);
									insertPoint(i+2, m.getX(),p1.getY() + offset);
									break;
								}
							case DOWN:
								insertPoint(++i, p1.getX(),m.getY());
								insertPoint(++i, p2.getX(),m.getY());
								break;
							}
						}
						break;
					case LEFT:
					case RIGHT:
						if (p0!=null) {
							if (i+2==newPoints.size()) {
								// this is the last point in the array, so if p2's direction is orthogonal
								// to p1 we need to insert a couple of bendpoints here before the end
								if (d2==Direction.UP || d2==Direction.DOWN){
									insertPoint(++i, p0.getX(),m.getY());
									insertPoint(++i, p2.getX(),m.getY());
									break;
								}
							}
							p1.setX(p0.getX());
							p1.setY(p2.getY());
						}
						else {
							switch (d2) {
							case UP:
							case DOWN:
								if (d1==Direction.RIGHT && p2.getX() < p1.getX()) {
									insertPoint(i+1, p1.getX() + offset,p1.getY());
									insertPoint(i+2, p1.getX() + offset,m.getY());
									break;
								}
								if (d1==Direction.LEFT && p2.getX() > p1.getX()) {
									insertPoint(i+1, p1.getX() + offset,p1.getY());
									insertPoint(i+1, p1.getX() + offset,m.getY());
									break;
								}
								insertPoint(i+1, p2.getX(),p1.getY());
								break;
							case LEFT:
								if (d1==Direction.RIGHT) {
									insertPoint(i+1, p1.getX() + offset,p1.getY());
									insertPoint(i+2, p1.getX() + offset,m.getY());
									break;
								}
							case RIGHT:
								insertPoint(++i, m.getX(),p1.getY());
								insertPoint(++i, m.getX(),p2.getY());
								break;
							}
						}
						break;
					}
					changed = true;
				}
			}
			
			// handle the special case of multiple connections with the same bendpoints
			// by offsetting this connection either horizontally or vertically by a few pixels
			updateConnection();
			if (offsetStackedConnections())
				changed = true;
			
			// make sure everything is still OK with the BendpointRouter
			if (changed) {
				updateConnection();
				if (super.calculateRoute() == false) {
					// super hasn't made any changes so we're done here
					// otherwise we would have to do our routing again
					break;
				}
			}
		}
		
		return changed;
	}
	
	protected boolean fixCollisions() {
		return false;
//		detours = null;
//		boolean changed = false;
//		for (int i=0; i<newPoints.size()-1; ++i) {
//			Point p0 = i>0 ? newPoints.get(i-1) : null;
//			Point p1 = newPoints.get(i);
//			Point p2 = newPoints.get(i+1);
//			List<ContainerShape> collisions = findCollisions(p1, p2);
//			sortCollisions(collisions, p1);
//
//			for (ContainerShape shape : collisions) {
//				DetourPoints detour = new DetourPoints(shape);
//				// fix it!
//				try {
//					// insert a couple of bendpoints to navigate around the shape
//					Direction d0 = i>0 ? getDirection(i-1) : null;
//					Direction d1 = getDirection(i);
//					Direction d2 = getDirection(i+1);
//					switch (d1) {
//					case UP:
//						switch (d2) {
//						case UP:
//							if (length(detour.bottomLeft,p1) < length(detour.bottomRight,p1)) {
//								insertDetour(i+1, detour.bottomLeft);
//								insertDetour(i+2, detour.topLeft);
//							}
//							else {
//								insertDetour(i+1, detour.bottomRight);
//								insertDetour(i+2, detour.topRight);
//							}
//							i += 2;
//							break;
//						case DOWN:
//							break;
//						case LEFT:
//							insertDetour(i+1, detour.bottomLeft);
//							insertDetour(i+2, detour.topLeft);
//							i += 2;
//							break;
//						case RIGHT:
//							insertDetour(i+1, detour.bottomRight);
//							insertDetour(i+2, detour.topRight);
//							i += 2;
//							break;
//						}
//						break;
//					case DOWN:
//						switch (d2) {
//						case UP:
//							break;
//						case DOWN:
//							if (length(detour.topLeft,p1) < length(detour.topRight,p1)) {
//								insertDetour(i+1, detour.topLeft);
//								insertDetour(i+2, detour.bottomLeft);
//							}
//							else {
//								insertDetour(i+1, detour.topRight);
//								insertDetour(i+2, detour.bottomRight);
//							}
//							i += 2;
//							break;
//						case LEFT:
//							insertDetour(i+1, detour.topLeft);
//							insertDetour(i+2, detour.bottomLeft);
//							i += 2;
//							break;
//						case RIGHT:
//							insertDetour(i+1, detour.topRight);
//							insertDetour(i+2, detour.bottomRight);
//							i += 2;
//							break;
//						}
//						break;
//					case LEFT:
//						switch (d2) {
//						case UP:
//							insertDetour(i+1, detour.topRight);
//							insertDetour(i+2, detour.topLeft);
//							i += 2;
//							break;
//						case DOWN:
//							insertDetour(i+1, detour.bottomRight);
//							insertDetour(i+2, detour.bottomLeft);
//							i += 2;
//							break;
//						case LEFT:
//							if (length(detour.topRight,p1) < length(detour.bottomRight,p1)) {
//								insertDetour(i+1, detour.topRight);
//								insertDetour(i+2, detour.topLeft);
//							}
//							else {
//								insertDetour(i+1, detour.bottomRight);
//								insertDetour(i+2, detour.bottomLeft);
//							}
//							i += 2;
//							break;
//						case RIGHT:
//							break;
//						}
//						break;
//					case RIGHT:
//						switch (d2) {
//						case UP:
//							insertDetour(i+1, detour.topLeft);
//							insertDetour(i+2, detour.topRight);
//							i += 2;
//							break;
//						case DOWN:
//							insertDetour(i+1, detour.bottomLeft);
//							insertDetour(i+2, detour.bottomRight);
//							i += 2;
//							break;
//						case LEFT:
//							break;
//						case RIGHT:
//							if (length(detour.topLeft,p1) < length(detour.bottomLeft,p1)) {
//								insertDetour(i+1, detour.topLeft);
//								insertDetour(i+2, detour.topRight);
//							}
//							else {
//								insertDetour(i+1, detour.bottomLeft);
//								insertDetour(i+2, detour.bottomRight);
//							}
//							i += 2;
//							break;
//						}
//						break;
//					}
//					changed = true;
//				}
//				catch (Exception e) {
//				}
//			}
//		}
//		return changed;
	}
}

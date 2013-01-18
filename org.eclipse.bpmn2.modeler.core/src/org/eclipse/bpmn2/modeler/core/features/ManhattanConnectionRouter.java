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
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.Tuple;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;

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
			createNewPoints(newStart, newEnd, ffc.getBendpoints());
		}
		else {
			createNewPoints(newStart, newEnd, null);
		}
	}
	
	@Override
	protected boolean calculateRoute() {
		
		Point movedBendpoint = getMovedBendpoint(ffc);

		// let BendpointRouter do its thing
		boolean changed = super.calculateRoute();
		
		Point p;
		Point p0 = null;
		Point p1 = null;
		Point p2 = null;
		Direction d1, d2;
		
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
					if (p2 == movedBendpoint) {
						// the second point is movable - adjust it so that it is directly above or below p1
						p2.setX(p1.getX());
					}
					else if (p0!=null) {
						if (i+2==newPoints.size()) {
							// this is the last point in the array, so if p2's direction is orthogonal
							// to p1 we need to insert a couple of bendpoint here before the end
							if (d2==Direction.LEFT || d2==Direction.RIGHT){
								p = GraphicsUtil.createPoint(m.getX(),p0.getY());
								newPoints.add(++i,p);
								p = GraphicsUtil.createPoint(m.getX(),p2.getY());
								newPoints.add(++i,p);
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
							p = GraphicsUtil.createPoint(p1.getX(),p2.getY());
							newPoints.add(i+1,p);
							break;
						case UP:
						case DOWN:
							p = GraphicsUtil.createPoint(p1.getX(),m.getY());
							newPoints.add(++i,p);
							p = GraphicsUtil.createPoint(p2.getX(),m.getY());
							newPoints.add(++i,p);
							break;
						}
					}
					break;
				case LEFT:
				case RIGHT:
					if (p2 == movedBendpoint) {
						// the second point is movable - adjust it so that it is directly right or left of p1
						p2.setY(p1.getY());
					}
					else if (p0!=null) {
						if (i+2==newPoints.size()) {
							// this is the last point in the array, so if p2's direction is orthogonal
							// to p1 we need to insert a couple of bendpoint here before the end
							if (d2==Direction.UP || d2==Direction.DOWN){
								p = GraphicsUtil.createPoint(p0.getX(),m.getY());
								newPoints.add(++i,p);
								p = GraphicsUtil.createPoint(p2.getX(),m.getY());
								newPoints.add(++i,p);
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
							p = GraphicsUtil.createPoint(p2.getX(),p1.getY());
							newPoints.add(i+1,p);
							break;
						case LEFT:
						case RIGHT:
							p = GraphicsUtil.createPoint(m.getX(),p1.getY());
							newPoints.add(++i,p);
							p = GraphicsUtil.createPoint(m.getX(),p2.getY());
							newPoints.add(++i,p);
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
		if (offsetStackedConnections())
			changed = true;
		
		// make sure everything is still OK with the BendpointRouter
		if (changed)
			super.calculateRoute();

		return changed;
	}
}

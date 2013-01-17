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
import org.eclipse.bpmn2.modeler.core.features.bendpoint.MoveBendpointFeature;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;

/**
 * A Connection Router that constrains all line segments of a connection to be either
 * horizontal or vertical; thus, diagonal lines are split into two segments that are
 * horizontal and vertical.
 */
public class ManhattanConnectionRouter extends DefaultConnectionRouter {

	
	public ManhattanConnectionRouter(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean route(Connection connection) {
		if (!(connection instanceof FreeFormConnection))
			return false;
		
		boolean thisHasDoneChanges = false;
		boolean superHasDoneChanges = super.route(connection);
		
		FreeFormConnection ffc = (FreeFormConnection) connection;
		Point p;
		Point p0 = null;
		Point p1 = null;
		Point p2 = null;
		Point p3 = null;
		Direction d1, d2;
		List<Point> bp = new ArrayList<Point>();
		List<Point> originalBendpoints = new ArrayList<Point>();
		bp.add(GraphicsUtil.createPoint(ffc.getStart()));
		bp.addAll(ffc.getBendpoints());
		bp.add(GraphicsUtil.createPoint(ffc.getEnd()));
		for (Point op : bp) {
			originalBendpoints.add(gaService.createPoint(op.getX(), op.getY()));
		}
		Point movableBendpoint = MoveBendpointFeature.getMovableBendpoint(ffc);
		
		for (int i=0; i<bp.size()-1; ++i) {
			p0 = i>0 ? bp.get(i-1) : null;
			p1 = bp.get(i);
			p2 = bp.get(i+1);

			if (isSlanted(p1, p2)) {
				Point m = GraphicsUtil.getMidpoint(p1, p2);
				try {
					d1 = getDirection(ffc, bp, i); 
					d2 = getDirection(ffc, bp, i+1);
				}
				catch (Exception e) {
					bp.clear();
					break;
				}
				switch (d1) {
				case UP:
				case DOWN:
					if (p2 == movableBendpoint) {
						// the second point is movable - adjust it so that it is directly above or below p1
						p2.setX(p1.getX());
					}
					else if (p0!=null) {
						p1.setX(p2.getX());
						p1.setY(p0.getY());
					}
					else {
						switch (d2) {
						case LEFT:
						case RIGHT:
							p = GraphicsUtil.createPoint(p1.getX(),p2.getY());
							bp.add(i+1,p);
							break;
						case UP:
						case DOWN:
							p = GraphicsUtil.createPoint(p1.getX(),m.getY());
							bp.add(++i,p);
							p = GraphicsUtil.createPoint(p2.getX(),m.getY());
							bp.add(++i,p);
							break;
						}
					}
					break;
				case LEFT:
				case RIGHT:
					if (p2 == movableBendpoint) {
						// the second point is movable - adjust it so that it is directly right or left of p1
						p2.setY(p1.getY());
					}
					else if (p0!=null) {
						p1.setX(p0.getX());
						p1.setY(p2.getY());
					}
					else {
						switch (d2) {
						case UP:
						case DOWN:
							p = GraphicsUtil.createPoint(p2.getX(),p1.getY());
							bp.add(i+1,p);
							break;
						case LEFT:
						case RIGHT:
							p = GraphicsUtil.createPoint(m.getX(),p1.getY());
							bp.add(++i,p);
							p = GraphicsUtil.createPoint(m.getX(),p2.getY());
							bp.add(++i,p);
							break;
						}
					}
					break;
				}
			}
		}
		
		// handle the special case of multiple connections with the same bendpoints
		// by offsetting this connection either horizontally or vertically by a few pixels
		offsetStackedConnections(ffc, bp);
		
		// check if anything has changed
		if (originalBendpoints.size()!=bp.size())
			thisHasDoneChanges = true;
		else {
			for (int i=0; i<originalBendpoints.size(); ++i) {
				p1 = originalBendpoints.get(i);
				p2 = bp.get(i);
				if (!GraphicsUtil.pointsEqual(p1, p2)) {
					thisHasDoneChanges = true;
					break;
				}
			}
		}
		
		if (thisHasDoneChanges) {
			ffc.getBendpoints().clear();
			p1 = bp.get(0);
			for (int i=1; i<bp.size()-1; ++i) {
				p2 = bp.get(i);
				if (i+1 < bp.size()) {
					// remove unnecessary bendpoints
					p3 = bp.get(i+1);
					if ((isVertical(p1,p2) && isVertical(p2,p3)) ||
							(isHorizontal(p1,p2) && isHorizontal(p2,p3))) {
						continue;
					}
				}
				ffc.getBendpoints().add( GraphicsUtil.createPoint(p2.getX(), p2.getY()));
				p1 = p2;
			}
			
			// remove the movable bendpoint so that it will not be used next time
			MoveBendpointFeature.setMovableBendpoint(ffc,null);
			
			DIUtils.updateDIEdge(ffc);
		}
		
		return superHasDoneChanges || thisHasDoneChanges;
	}
}

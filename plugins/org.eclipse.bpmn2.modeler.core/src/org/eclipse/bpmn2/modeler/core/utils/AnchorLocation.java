/*******************************************************************************
 * Copyright (c) 2011, 2012, 2013, 2014 Red Hat, Inc.
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

package org.eclipse.bpmn2.modeler.core.utils;

import org.eclipse.bpmn2.modeler.core.features.GraphitiConstants;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil.LineSegment;
import org.eclipse.graphiti.datatypes.ILocation;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.FixPointAnchor;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;

public enum AnchorLocation {
	TOP("anchor.top"), BOTTOM("anchor.bottom"), LEFT("anchor.left"), RIGHT("anchor.right"), CENTER("anchor.center"); //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$ //$NON-NLS-4$ //$NON-NLS-5$

	private final String key;

	private AnchorLocation(String key) {
		this.key = key;
	}

	public String getKey() {
		return key;
	}

	public static AnchorLocation getLocation(String key) {
		for (AnchorLocation l : values()) {
			if (l.getKey().equals(key)) {
				return l;
			}
		}
		return null;
	}
	
	public static AnchorLocation getLocation(FixPointAnchor anchor) {
		return getLocation(Graphiti.getPeService().getPropertyValue(anchor, GraphitiConstants.ANCHOR_LOCATION));
	}
	
	public static void setLocation(FixPointAnchor anchor, AnchorLocation al) {
		Graphiti.getPeService().setPropertyValue(anchor, GraphitiConstants.ANCHOR_LOCATION, al.getKey());
		AnchorUtil.adjustAnchors(anchor.getParent());

	}
	
	/**
	 * Return the AnchorLocation value of the shape's edge that is nearest
	 * to the given point.
	 * 
	 * @param shape
	 * @param p
	 * @return
	 */
	public static AnchorLocation getNearestEdge(Shape shape, Point p) {
		LineSegment edge = GraphicsUtil.findNearestEdge(shape, p);
		ILocation loc = Graphiti.getPeService().getLocationRelativeToDiagram(shape);
		AnchorLocation al;
		if (edge.isHorizontal()) {
			int y = edge.getStart().getY();
			if (y==loc.getY())
				al = TOP;
			else
				al = BOTTOM;
		}
		else {
			int x = edge.getStart().getX();
			if (x==loc.getX())
				al = LEFT;
			else
				al = RIGHT;
		}
		return al;
	}
}
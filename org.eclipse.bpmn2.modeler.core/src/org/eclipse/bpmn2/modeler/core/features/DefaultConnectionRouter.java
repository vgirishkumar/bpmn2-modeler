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
import java.util.Comparator;
import java.util.List;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Lane;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil.LineSegment;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;

/**
 * Router for straight-line connections from source to target.
 * Currently this does nothing but serves as a container for common
 * fields and methods.
 */
public class DefaultConnectionRouter extends AbstractConnectionRouter {

	protected List<ContainerShape> allShapes;
	Connection connection;
	ContainerShape source;
	ContainerShape target;
	
	public DefaultConnectionRouter(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean route(Connection connection) {
		this.connection = connection;
		this.source = (ContainerShape)connection.getStart().getParent();
		this.target = (ContainerShape)connection.getEnd().getParent();
		return false;
	}
	
	protected void initialize() {
	}
	
	@Override
	public void dispose() {
		// be sure to clean up the routing info
		removeRoutingInfo(connection);
	}

	/**
	 * Check if the connection's source and target nodes are identical.
	 * 
	 * @return true if connection source == target
	 */
	protected boolean isSelfConnection() {
		if (source != target)
			return false;
		return true;
	}

	protected List<ContainerShape> findAllShapes() {
		allShapes = new ArrayList<ContainerShape>();
		Diagram diagram = fp.getDiagramTypeProvider().getDiagram();
		TreeIterator<EObject> iter = diagram.eAllContents();
		while (iter.hasNext()) {
			EObject o = iter.next();
			if (o instanceof ContainerShape) {
				// this is a potential collision shape
				ContainerShape shape = (ContainerShape)o;
				BPMNShape bpmnShape = BusinessObjectUtil.getFirstElementOfType(shape, BPMNShape.class);
				if (bpmnShape==null)
					continue;
//				if (shape==source || shape==target)
//					continue;
				// ignore containers (like Lane, SubProcess, etc.) if the source
				// or target shapes are children of the container's hierarchy
				if (shape==source.eContainer() || shape==target.eContainer())
					continue;
				
				// ignore some containers altogether
				BaseElement be = bpmnShape.getBpmnElement();
				if (be instanceof Lane)
					continue;
				// TODO: other criteria here?
	
				allShapes.add(shape);
			}
		}
		GraphicsUtil.dump("All Shapes", allShapes);
		return allShapes;
	}

	protected LineSegment getCollisionEdge(Point p1, Point p2) {
		ContainerShape shape = getCollision(p1, p2);
		if (shape!=null) {
			return GraphicsUtil.findNearestEdge(shape, p1);
		}
		return null;
	}

	protected ContainerShape getCollision(Point p1, Point p2) {
		List<ContainerShape> collisions = findCollisions(p1, p2);
		if (collisions.size()==0)
			return null;
		if (collisions.size()==1)
			return collisions.get(0);
		sortCollisions(collisions, p2);
		return collisions.get(0);
	}
	
	protected List<ContainerShape> findCollisions(Point p1, Point p2) {
		List<ContainerShape> collisions = new ArrayList<ContainerShape>();
		if (allShapes==null)
			findAllShapes();
		for (ContainerShape shape : allShapes) {
			if (GraphicsUtil.intersectsLine(shape, p1, p2))
				collisions.add(shape);
		}
		if (collisions.size()>0)
			GraphicsUtil.dump("Collisions with line ["+p1.getX()+", "+p1.getY()+"]"+" ["+p2.getX()+", "+p2.getY()+"]", collisions);
		return collisions;
	}

	protected void sortCollisions(List<ContainerShape> collisions, final Point p) {
		Collections.sort(collisions, new Comparator<ContainerShape>() {
	
			@Override
			public int compare(ContainerShape s1, ContainerShape s2) {
				LineSegment seg1 = GraphicsUtil.findNearestEdge(s1, p);
				double d1 = seg1.getDistance(p);
				LineSegment seg2 = GraphicsUtil.findNearestEdge(s2, p);
				double d2 = seg2.getDistance(p);
				return (int) (d2 - d1);
			}
		});
	}
	
	protected List<Connection> findCrossings(Point start, Point end) {
		List<Connection> crossings = new ArrayList<Connection>();
		List<Connection> allConnections = fp.getDiagramTypeProvider().getDiagram().getConnections();
		for (Connection connection : allConnections) {
			Point p1 = GraphicsUtil.createPoint(connection.getStart());
			Point p3 = GraphicsUtil.createPoint(connection.getEnd());
			if (connection instanceof FreeFormConnection) {
				FreeFormConnection ffc = (FreeFormConnection) connection;
				Point p2 = p1;
				for (Point p : ffc.getBendpoints()) {
					if (GraphicsUtil.intersects(start, end, p1, p)) {
						crossings.add(connection);
						break;
					}
					p2 = p1 = p;
				}
				if (GraphicsUtil.intersects(start, end, p2, p3)) {
					crossings.add(connection);
				}
			}
			else if (GraphicsUtil.intersects(start, end, p1, p3)) {
				crossings.add(connection);
			}
		}
		return crossings;
	}

	protected static double length(Point p1, Point p2) {
		return GraphicsUtil.getLength(p1, p2);
	}
}

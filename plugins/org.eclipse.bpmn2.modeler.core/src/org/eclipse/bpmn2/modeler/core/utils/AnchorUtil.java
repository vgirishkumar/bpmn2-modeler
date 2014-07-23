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
 * @author Ivar Meikas
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.core.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.di.BPMNEdge;
import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.core.features.GraphitiConstants;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil.LineSegment;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.datatypes.ILocation;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.algorithms.Ellipse;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.FixPointAnchor;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.ICreateService;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.ILayoutService;
import org.eclipse.graphiti.services.IPeService;

public class AnchorUtil {

	public static final int CONNECTION_POINT_SIZE = 4;

	private static final IPeService peService = Graphiti.getPeService();
	private static final IGaService gaService = Graphiti.getGaService();
	private static final ICreateService createService = Graphiti.getCreateService();
	private static final ILayoutService layoutService = Graphiti.getLayoutService();

	public static FixPointAnchor createAnchor(AnchorContainer ac, int x, int y, AnchorType at) {
		return createAnchor(ac, gaService.createPoint(x, y), at);
	}
	
	public static FixPointAnchor createAnchor(AnchorContainer ac, Point p, AnchorType at) {
		FixPointAnchor anchor = createBoundaryAnchor(ac, p);
		AnchorType.setType(anchor, at);
		relocateAnchors(ac);
		return anchor;
	}
	
	public static FixPointAnchor createAnchor(AnchorContainer ac, int x, int y) {
		return createAnchor(ac, gaService.createPoint(x, y));
	}
	
	public static FixPointAnchor createAnchor(AnchorContainer ac, Point p) {
		FixPointAnchor anchor = createBoundaryAnchor(ac, p);
		AnchorType at = AnchorType.getType(ac);
		AnchorType.setType(anchor, at);
		relocateAnchors(ac);
		return anchor;
	}
	
	public static FixPointAnchor createBoundaryAnchor(AnchorContainer ac, Point p) {
		FixPointAnchor anchor = peService.createFixPointAnchor(ac);
		moveAnchor(anchor, p);
		gaService.createInvisibleRectangle(anchor);

		return anchor;
	}
	
	public static List<FixPointAnchor> getBoundaryAnchors(AnchorContainer ac, AnchorLocation al) {
		List<FixPointAnchor> result = new ArrayList<FixPointAnchor>();
		for (Anchor anchor : ac.getAnchors()) {
			if (peService.getPropertyValue(anchor, GraphitiConstants.ANCHOR_TYPE)!=null &&
				AnchorLocation.getLocation((FixPointAnchor)anchor)==al)
				result.add((FixPointAnchor)anchor);
		}
		return result;
	}

	public static void moveAnchor(FixPointAnchor anchor, Point p) {
		AnchorContainer ac = anchor.getParent();
		if (ac instanceof Shape) {
			p = GraphicsUtil.createPoint(p); // make a copy
			ILocation loc = peService.getLocationRelativeToDiagram((Shape)ac);
			LineSegment edge = GraphicsUtil.findNearestEdge((Shape)ac, p);
			AnchorLocation al;
			if (edge.isHorizontal()) {
				int y = edge.getStart().getY();
				if (y==loc.getY())
					al = AnchorLocation.TOP;
				else
					al = AnchorLocation.BOTTOM;
				p.setY(y - loc.getY());
				int x = p.getX();
				if (x < edge.getStart().getX())
					x = edge.getStart().getX();
				if (x > edge.getEnd().getX())
					x = edge.getEnd().getX();
				p.setX(x - loc.getX());
			}
			else {
				int x = edge.getStart().getX();
				if (x==loc.getX())
					al = AnchorLocation.LEFT;
				else
					al = AnchorLocation.RIGHT;
				p.setX(x - loc.getX());
				int y = p.getY();
				if (y < edge.getStart().getY())
					y = edge.getStart().getY();
				if (y > edge.getEnd().getY())
					y = edge.getEnd().getY();
				p.setY(y - loc.getY());
			}
			AnchorLocation.setLocation(anchor, al);
		}
		anchor.setLocation(p);
	}
	
	public static void relocateAnchors(AnchorContainer ac) {
		IDimension size = GraphicsUtil.calculateSize(ac);
		relocateAnchors(ac, size.getWidth(), size.getHeight());
	}
	
	public static void relocateAnchors(AnchorContainer ac, int w, int h) {

		if (ac==null)
			return;

		// These are the number of Activity Anchors along each
		// edge of the Activity. These anchors are distributed
		// evenly along an edge of the shape
		int topCount = 1;
		int bottomCount = 1;
		int leftCount = 1;
		int rightCount = 1;
		// Calculated offsets for each Activity Anchor
		int topOffset = 0;
		int bottomOffset = 0;
		int leftOffset = 0;
		int rightOffset = 0;
		for (Anchor a : ac.getAnchors()) {
			AnchorType at = AnchorType.getType(a);
			if (at == AnchorType.ACTIVITY) {
				// Count all of the Activity Anchors 
				FixPointAnchor anchor = (FixPointAnchor) a;
				AnchorLocation al = AnchorLocation.getLocation(anchor);
				switch (al) {
				case BOTTOM:
					++bottomCount;
					break;
				case CENTER:
					break;
				case LEFT:
					++leftCount;
					break;
				case RIGHT:
					++rightCount;
					break;
				case TOP:
					++topCount;
					break;
				default:
					break;
				
				}
			}
		}
		
		for (Anchor a : ac.getAnchors()) {
			AnchorType at = AnchorType.getType(a);
			if (at == AnchorType.ACTIVITY) {
				// adjust the Activity Anchors
				FixPointAnchor anchor = (FixPointAnchor) a;
				AnchorLocation al = AnchorLocation.getLocation(anchor);
				switch (al) {
				case BOTTOM:
					bottomOffset += w/bottomCount;
					anchor.setLocation(gaService.createPoint(bottomOffset, h));
					break;
				case CENTER:
					break;
				case LEFT:
					leftOffset += h/leftCount;
					anchor.setLocation(gaService.createPoint(0, leftOffset));
					break;
				case RIGHT:
					rightOffset += h/rightCount;
					anchor.setLocation(gaService.createPoint(w, rightOffset));
					break;
				case TOP:
					topOffset += w/topCount;
					anchor.setLocation(gaService.createPoint(topOffset, 0));
					break;
				default:
					break;
				}
			}
			else if (at == AnchorType.GATEWAY) {
				// adjust the Gateway and Event Anchors: all of these
				// are attached to the middle of an edge
				FixPointAnchor anchor = (FixPointAnchor) a;
				AnchorLocation al = AnchorLocation.getLocation(anchor);
				switch (al) {
				case BOTTOM:
					anchor.setLocation(gaService.createPoint(w/2, h));
					break;
				case CENTER:
					break;
				case LEFT:
					anchor.setLocation(gaService.createPoint(0, h/2));
					break;
				case RIGHT:
					anchor.setLocation(gaService.createPoint(w, h/2));
					break;
				case TOP:
					anchor.setLocation(gaService.createPoint(w/2, 0));
					break;
				default:
					break;
				}
			}
			else if (at == AnchorType.POOL) {
				// adjust Pool Anchors: these are placed by the user at a specific
				// point on an edge of the Pool. The position of these anchors may
				// be adjusted by the Connection Routers for optimal routing.
				FixPointAnchor anchor = (FixPointAnchor) a;
				Point p = anchor.getLocation();
				if (p.getX() > w)
					p.setX(w);
				if (p.getY() > h)
					p.setY(h);
				AnchorLocation al = AnchorLocation.getLocation(anchor);
				switch (al) {
				case BOTTOM:
					anchor.setLocation(gaService.createPoint(p.getX(), h));
					break;
				case CENTER:
					break;
				case LEFT:
					anchor.setLocation(gaService.createPoint(0, p.getY()));
					break;
				case RIGHT:
					anchor.setLocation(gaService.createPoint(w, p.getY()));
					break;
				case TOP:
					anchor.setLocation(gaService.createPoint(p.getX(), 0));
					break;
				default:
					break;
				
				}
			}
		}
	}
	
	public static int countAnchors(AnchorContainer ac, AnchorLocation al) {
		int count = 0;
		for (Anchor a : ac.getAnchors()) {
			if (peService.getPropertyValue(a, GraphitiConstants.ANCHOR_TYPE)!=null)
				if (al==AnchorLocation.getLocation((FixPointAnchor)a))
					++count;
		}
		return count;
	}
	
	public static List<Connection> getConnections(AnchorContainer ac, AnchorLocation al) {
		List<Connection> connections = new ArrayList<Connection>();
		for (Anchor a : ac.getAnchors()) {
			if (peService.getPropertyValue(a, GraphitiConstants.ANCHOR_TYPE)!=null)
				if (al==AnchorLocation.getLocation((FixPointAnchor)a)) {
					connections.addAll(a.getIncomingConnections());
					connections.addAll(a.getOutgoingConnections());
				}
		}
		return connections;
	}
	
//	public static void rotateMovableAnchors(AnchorContainer ac, AnchorLocation al) {
//		for (Anchor a : ac.getAnchors()) {
//			String property = peService.getPropertyValue(a, GraphitiConstants.BOUNDARY_ANCHOR);
//			if (GraphitiConstants.ACTIVITY_ANCHOR.equals(property)) {
//				if (al==AnchorLocation.getLocation((FixPointAnchor)a)) {
//					ac.getAnchors().remove(a);
//					ac.getAnchors().add(a);
//					break;
//				}
//			}
//		}
//	}

	/**
	 * @param parent
	 */
	private static void deleteUnusedAnchors(AnchorContainer ac) {
		if (ac!=null && !AnchorUtil.isConnectionPoint(ac)) {
			List<Anchor> deleted = new ArrayList<Anchor>();
	
			for (Anchor a : ac.getAnchors()) {
				String property = peService.getPropertyValue(a, GraphitiConstants.ANCHOR_TYPE);
				if ( property!=null &&
						a.getIncomingConnections().isEmpty() &&
						a.getOutgoingConnections().isEmpty()) {
					deleted.add(a);
				}
			}
	
			for (Anchor a : deleted) {
				peService.deletePictogramElement(a);
			}
		}
	}

	public static void adjustAnchors(AnchorContainer ac) {
		deleteUnusedAnchors(ac);
		relocateAnchors(ac);
	}

	// Connection points allow creation of anchors on FreeFormConnections
	
	private static class ConnectionPointShapeAdapter extends AdapterImpl {
		Connection connection;
		Shape shape;
		boolean deleting = false;
		double midpoint  = 0.5;
		
		public static ConnectionPointShapeAdapter adapt(Connection connection, Shape shape) {
			return new ConnectionPointShapeAdapter(connection, shape);
		}
		
		private ConnectionPointShapeAdapter(Connection connection, Shape shape) {
			this.connection = connection;
			this.shape = shape;
			connection.eAdapters().add(this);
			shape.eAdapters().add(this);
			shape.getAnchors().get(0).eAdapters().add(this);

			setTarget(connection);
			int x = shape.getGraphicsAlgorithm().getX();
			int y = shape.getGraphicsAlgorithm().getY();
			int dx = Integer.MAX_VALUE;
			int dy = Integer.MAX_VALUE;
			for (double d=0; d<=1.0; d += 0.05) {
				ILocation loc = Graphiti.getPeService().getConnectionMidpoint(connection, d);
				if (Math.abs(x - loc.getX()) < dx || Math.abs(y - loc.getY()) < dy) {
					dx = Math.abs(x - loc.getX());
					dy = Math.abs(y - loc.getY());
					midpoint = d;
				}
			}
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.emf.common.notify.Adapter#notifyChanged(org.eclipse.emf.common.notify.Notification)
		 */
		@Override
		public void notifyChanged(Notification notification) {
			// if the connection is no longer connected to this shape
			// then delete the Connection Point
			if (!deleting) {
				Anchor a = shape.getAnchors().get(0);
				if (notification.getNotifier() == a) {
					// something changed in the anchor
					if (a.getIncomingConnections().isEmpty() && a.getOutgoingConnections().isEmpty()) {
						// the anchor has no connections, so we need to delete it
						deleting = true;
						deleteConnectionPoint(shape);
						return;
					}
				}
				else 
				{
					ILocation loc = Graphiti.getPeService().getConnectionMidpoint(connection, midpoint);
					Graphiti.getGaService().setLocation(shape.getGraphicsAlgorithm(), loc.getX(), loc.getY());
				}
			}
		}
	}
	
	public static void deleteConnectionPoint(AnchorContainer shape) {
		if (isConnectionPoint(shape)) {
			Connection connection = getConnectionPointOwner(shape);
			if (connection!=null) {
				for (Adapter a : shape.eAdapters()) {
					if (a instanceof ConnectionPointShapeAdapter) {
						connection.eAdapters().remove(a);
					}
				}
				connection.getLink().getBusinessObjects().remove(shape);
			}
			Graphiti.getPeService().deletePictogramElement(shape);
		}
	}
	
	public static Shape createConnectionPoint(IFeatureProvider fp, Connection connection, ILocation location) {

		Shape connectionPointShape = null;

		Diagram diagram = fp.getDiagramTypeProvider().getDiagram();
		connectionPointShape = createConnectionPoint(location, diagram);
		fp.link(connectionPointShape, connection);
		connection.getLink().getBusinessObjects().add(connectionPointShape);
		BaseElement be = BusinessObjectUtil.getFirstBaseElement(connection);
		BPMNEdge bpmnEdge = DIUtils.findBPMNEdge(be);
		if (bpmnEdge!=null)
			fp.link(connectionPointShape, bpmnEdge);
		
		ConnectionPointShapeAdapter.adapt(connection, connectionPointShape);
		
		return connectionPointShape;
	}

	public static Shape createConnectionPoint(ILocation location, ContainerShape cs) {
		
		// create a circle for the connection point shape
		Shape connectionPointShape = createService.createShape(cs, true);
		peService.setPropertyValue(connectionPointShape, GraphitiConstants.CONNECTION_POINT_KEY, GraphitiConstants.CONNECTION_POINT);
		Ellipse ellipse = createService.createEllipse(connectionPointShape);
		int x = 0, y = 0;
		if (location != null) {
			x = location.getX();
			y = location.getY();
		}
		ellipse.setFilled(true);
		Diagram diagram = peService.getDiagramForPictogramElement(connectionPointShape);
		ellipse.setForeground(Graphiti.getGaService().manageColor(diagram, StyleUtil.CLASS_FOREGROUND));
		ellipse.setWidth(CONNECTION_POINT_SIZE);
		ellipse.setHeight(CONNECTION_POINT_SIZE);
		
		// create the anchor
		getConnectionPointAnchor(connectionPointShape);
		
		// set the location
		setConnectionPointLocation(connectionPointShape, x, y);
	
		return connectionPointShape;
	}
	
	public static FixPointAnchor getConnectionPointAnchor(Shape connectionPointShape) {
		if (connectionPointShape.getAnchors().size()==0) {
			FixPointAnchor anchor = createService.createFixPointAnchor(connectionPointShape);
			peService.setPropertyValue(anchor, GraphitiConstants.CONNECTION_POINT_KEY, GraphitiConstants.CONNECTION_POINT);
			
			// if the anchor doesn't have a GraphicsAlgorithm, GEF will throw a fit
			// so create an invisible rectangle for it
			createService.createInvisibleRectangle(anchor);
		}		
		return (FixPointAnchor)connectionPointShape.getAnchors().get(0);
	}

	public static ILocation getConnectionPointLocation(Shape connectionPointShape) {
		ILocation location = ShapeDecoratorUtil.peService.getLocationRelativeToDiagram(connectionPointShape);
		int x = location.getX() + CONNECTION_POINT_SIZE / 2;
		int y = location.getY() + CONNECTION_POINT_SIZE / 2;
		location.setX(x);
		location.setY(y);
		return location;
	}
	
	public static void setConnectionPointLocation(Shape connectionPointShape, int x, int y) {
		
		if (connectionPointShape.getAnchors().size()==0) {
			// anchor has not been created yet - need to set both location AND size
			layoutService.setLocationAndSize(
					connectionPointShape.getGraphicsAlgorithm(),
					x - CONNECTION_POINT_SIZE / 2, y - CONNECTION_POINT_SIZE / 2,
					CONNECTION_POINT_SIZE, CONNECTION_POINT_SIZE);
		}
		else {
			// already created - just set the location
			layoutService.setLocation(
					connectionPointShape.getGraphicsAlgorithm(),
					x - CONNECTION_POINT_SIZE / 2, y - CONNECTION_POINT_SIZE / 2);
		}
		
		FixPointAnchor anchor = getConnectionPointAnchor(connectionPointShape);
		anchor.setLocation( Graphiti.getCreateService().createPoint(CONNECTION_POINT_SIZE / 2,CONNECTION_POINT_SIZE / 2) );
		layoutService.setLocation(
				anchor.getGraphicsAlgorithm(), 
				CONNECTION_POINT_SIZE / 2,CONNECTION_POINT_SIZE / 2);
	}
	
	public static List<Shape> getConnectionPoints(Connection connection) {
		ArrayList<Shape> list = new ArrayList<Shape>();
		if (connection.getLink()!=null) {
			for (Object o : connection.getLink().getBusinessObjects()) {
				if (o instanceof Shape && isConnectionPoint((Shape)o)) {
					list.add((Shape)o);
				}
			}
		}
		return list;
	}

	public static boolean isConnectionPoint(PictogramElement pe) {
		if (pe!=null) {
			String value =peService.getPropertyValue(pe, GraphitiConstants.CONNECTION_POINT_KEY);
			return GraphitiConstants.CONNECTION_POINT.equals(value);
		}
		return false;
	}
	
	public static Connection getConnectionPointOwner(AnchorContainer connectionPointShape) {
		if (isConnectionPoint(connectionPointShape) && connectionPointShape.getLink()!=null) {
			for (Object o : connectionPointShape.getLink().getBusinessObjects()) {
				if (o instanceof Connection)
					return (Connection) o;
			}
		}
		return null;
	}
}
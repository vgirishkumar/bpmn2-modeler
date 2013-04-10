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

import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeService;

/**
 * Abstract base class for Connection Routers. This is a container for common utility functions
 * related to routing.
 */
public abstract class AbstractConnectionRouter implements IConnectionRouter {
	
	protected static final IPeService peService = Graphiti.getPeService();
	protected static final IGaService gaService = Graphiti.getGaService();
	
	public enum Direction { UP, DOWN, LEFT, RIGHT, NONE };

	IFeatureProvider fp;

	public AbstractConnectionRouter(IFeatureProvider fp) {
		this.fp = fp;
	}

	@Override
	public abstract boolean route(Connection connection);
	
	@Override
	public abstract void dispose();
	
	public static String addRoutingInfo(Connection connection, String info) {
		assert(info!=null && !info.isEmpty());
		String newInfo = getRoutingInfo(connection);
		if (!newInfo.isEmpty())
			newInfo += ",";
		newInfo += info;
		
		peService.setPropertyValue(connection, ROUTING_INFO, newInfo);
		return newInfo;
	}
	
	public static void removeRoutingInfo(Connection connection) {
		removeRoutingInfo(connection, getRoutingInfo(connection));
	}
	
	public static String removeRoutingInfo(Connection connection, String info) {
		assert(info!=null && !info.isEmpty());
		String newInfo = getRoutingInfo(connection);
		String a[] = newInfo.split(",");
		String b[] = info.split(",");
		for (int i=0; i<a.length; ++i) {
			for (String sb : b) {
				if (a[i].startsWith(sb)) {
					a[i] = null;
					break;
				}
			}
		}
		newInfo = "";
		for (int i=0; i<a.length; ++i) {
			if (a[i]!=null && !a[i].isEmpty()) {
				if (!newInfo.isEmpty())
					newInfo += ",";
				newInfo += a[i];
			}
		}
		if (newInfo.isEmpty())
			peService.removeProperty(connection, ROUTING_INFO);
		else
			peService.setPropertyValue(connection, ROUTING_INFO, newInfo);
		return newInfo;
	}
	
	public static String getRoutingInfo(Connection connection) {
		String info = peService.getPropertyValue(connection, ROUTING_INFO);
		if (info==null || info.isEmpty())
			return "";
		return info;
	}

	public static String setRoutingInfoInt(Connection connection, String info, int value) {
		removeRoutingInfo(connection, info+"=");
		return addRoutingInfo(connection, info+"="+value);
	}

	public static int getRoutingInfoInt(Connection connection, String info) {
		String oldInfo = getRoutingInfo(connection);
		String a[] = oldInfo.split(",");
		for (String s : a) {
			if (oldInfo.startsWith(info+"=")) {
				try {
					String b[] = s.split("=");
					return Integer.parseInt(b[1]);
				}
				catch (Exception e) {
				}
			}
		}
		return -1;
	}

	public static void setForceRouting(Connection connection, boolean force) {
		if (force)
			addRoutingInfo(connection, ROUTING_INFO_FORCE);
		else
			removeRoutingInfo(connection, ROUTING_INFO_FORCE);
	}
	
	public static boolean forceRouting(Connection connection) {
		return getRoutingInfo(connection).contains(ROUTING_INFO_FORCE);
	}
	
	/**
	 * Check if the line segment defined by the two Points is horizontal.
	 * 
	 * @param p1
	 * @param p2
	 * @return true if the line segment is horizontal
	 */
	protected final static boolean isHorizontal(Point p1, Point p2) {
		return Math.abs(p1.getY() - p2.getY()) <= 2;
	}

	/**
	 * Check if the line segment defined by the two Points is vertical.
	 * 
	 * @param p1
	 * @param p2
	 * @return true if the line segment is vertical
	 */
	protected final static boolean isVertical(Point p1, Point p2) {
		return Math.abs(p1.getX() - p2.getX()) <= 2;
	}

	/**
	 * Check if the line segment defined by the two Points is neither horizontal nor vertical.
	 * 
	 * @param p1
	 * @param p2
	 * @return true if the line segment is slanted
	 */
	protected final boolean isSlanted(Point p1, Point p2) {
		return !isHorizontal(p1, p2) && !isVertical(p1,p2);
	}
}

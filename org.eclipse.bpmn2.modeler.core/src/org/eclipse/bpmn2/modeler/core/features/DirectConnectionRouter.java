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

import java.util.Map;

import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil.AnchorLocation;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil.BoundaryAnchor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.datatypes.ILocation;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.Shape;

/**
 * Router for straight-line connections from source to target.
 * Currently this does nothing but call the super class router
 * which handles self-connections (those whose source and target are the same).
 */
public class DirectConnectionRouter extends DefaultConnectionRouter {

	public DirectConnectionRouter(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean route(Connection connection) {
		return super.route(connection);
	}
}

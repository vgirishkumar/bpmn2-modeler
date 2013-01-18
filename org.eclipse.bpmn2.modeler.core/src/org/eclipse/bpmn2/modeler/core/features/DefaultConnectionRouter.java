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
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.Connection;

/**
 * Router for straight-line connections from source to target.
 * Currently this does nothing but serves as a container for common
 * fields and methods.
 */
public class DefaultConnectionRouter extends AbstractConnectionRouter {

	Connection connection;
	
	public DefaultConnectionRouter(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean route(Connection connection) {
		this.connection = connection;
		return false;
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
		AnchorContainer source = connection.getStart().getParent();
		AnchorContainer target = connection.getEnd().getParent();
		if (source != target)
			return false;
		return true;
	}
}

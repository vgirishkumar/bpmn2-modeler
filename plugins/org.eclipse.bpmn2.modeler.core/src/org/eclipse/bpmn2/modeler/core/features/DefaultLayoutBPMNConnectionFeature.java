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

import java.util.Hashtable;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.modeler.core.LifecycleEvent;
import org.eclipse.bpmn2.modeler.core.LifecycleEvent.EventType;
import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.preferences.ShapeStyle;
import org.eclipse.bpmn2.modeler.core.preferences.ShapeStyle.RoutingStyle;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.context.IPictogramElementContext;
import org.eclipse.graphiti.features.context.impl.UpdateContext;
import org.eclipse.graphiti.features.impl.AbstractLayoutFeature;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;

/**
 * Default Graphiti {@code LayoutFeature} class for Connections.
 * <p>
 * This simply invokes one of the Connection Routers depending on the user
 * preferences for each type of connection: SequenceFlow, MessageFlow,
 * Association and Conversation. See the Bpmn2EditorPreferencePage for details.
 */
public class DefaultLayoutBPMNConnectionFeature extends AbstractLayoutFeature {

	/** True if changes were made by this feature. */
	boolean hasDoneChanges = false;
	Diagram diagram;
	Hashtable<Connection, IConnectionRouter> routers = new Hashtable<Connection, IConnectionRouter>();

	/**
	 * Instantiates a new default layout bpmn connection feature.
	 *
	 * @param fp the fp
	 */
	public DefaultLayoutBPMNConnectionFeature(IFeatureProvider fp) {
		super(fp);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.func.ILayout#canLayout(org.eclipse.graphiti.features.context.ILayoutContext)
	 */
	@Override
	public boolean canLayout(ILayoutContext context) {
		// Check if automatic routing has been disabled by the user.
		if (context.getPictogramElement() instanceof Connection) {
			Connection connection = (Connection) context.getPictogramElement();
			BaseElement be = BusinessObjectUtil.getFirstBaseElement(connection);
			Bpmn2Preferences prefs = Bpmn2Preferences.getInstance(be);
			return prefs.getEnableConnectionRouting();
		}
		return false;
	}

	@Override
	public void execute(IContext context) {
		PictogramElement pe = ((IPictogramElementContext)context).getPictogramElement();
		LifecycleEvent.notify(new LifecycleEvent(EventType.PICTOGRAMELEMENT_LAYOUT, getFeatureProvider(), context, pe));
		super.execute(context);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.features.impl.AbstractFeature#hasDoneChanges()
	 */
	@Override
	public boolean hasDoneChanges() {
		return hasDoneChanges;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.func.ILayout#layout(org.eclipse.graphiti.features.context.ILayoutContext)
	 */
	@Override
	public boolean layout(ILayoutContext context) {
		if (canLayout(context)) {
			Connection connection = (Connection) context.getPictogramElement();
			if (context.getProperty(GraphitiConstants.INITIAL_UPDATE) == Boolean.TRUE)
				Graphiti.getPeService().setPropertyValue(connection, GraphitiConstants.INITIAL_UPDATE, Boolean.TRUE.toString());
			diagram = getFeatureProvider().getDiagramTypeProvider().getDiagram();
			// limit the number of iterations for recalculating other connections
			int iterations = 0;
			boolean repeat;
			do {
				repeat = false;
				IConnectionRouter router = getRouter(connection);
				hasDoneChanges |= router.route(connection);

				UpdateContext uc = new UpdateContext(connection);
				getFeatureProvider().updateIfPossible(uc);
				for (Connection c : diagram.getConnections()) {
					router = getRouter(c);
					if (router.canRoute(c) && router.routingNeeded(c)) {
						router.route(c);

						uc = new UpdateContext(c);
						getFeatureProvider().updateIfPossible(uc);

						repeat = true;
					}
				}
			}
			while (repeat && ++iterations < 3);
			Graphiti.getPeService().removeProperty(connection, GraphitiConstants.INITIAL_UPDATE);
		}
		return hasDoneChanges;
	}
	
	private IConnectionRouter getRouter(Connection connection) {
		if (routers.containsKey(connection))
			return routers.get(connection);
		
		IConnectionRouter router = null;
		IFeatureProvider fp = getFeatureProvider();
		BaseElement be = BusinessObjectUtil.getFirstBaseElement(connection);
		if (be!=null) {
			// get the user preference of routing style for this connection
			ShapeStyle ss = ShapeStyle.getShapeStyle(be);
			if (ss!=null) {
				if (ss.getRoutingStyle() == RoutingStyle.MANHATTAN)
					router = new ManhattanConnectionRouter(fp);
				else if (ss.getRoutingStyle() == RoutingStyle.MANUAL) {
					router = new BendpointConnectionRouter(fp);
				}
				else if (ss.getRoutingStyle() == RoutingStyle.AUTOMATIC) {
					router = new AutomaticConnectionRouter(fp);
				}
			}
		}
		if (router==null)
			router = new BendpointConnectionRouter(fp);
		routers.put(connection, router);
		return router;
	}
}

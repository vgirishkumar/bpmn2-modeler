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

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.modeler.core.LifecycleEvent;
import org.eclipse.bpmn2.modeler.core.LifecycleEvent.EventType;
import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.preferences.ShapeStyle;
import org.eclipse.bpmn2.modeler.core.preferences.ShapeStyle.RoutingStyle;
import org.eclipse.bpmn2.modeler.core.runtime.TargetRuntime;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ILayoutContext;
import org.eclipse.graphiti.features.context.IPictogramElementContext;
import org.eclipse.graphiti.features.impl.AbstractLayoutFeature;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

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
		TargetRuntime rt = TargetRuntime.getCurrentRuntime();
		PictogramElement pe = ((IPictogramElementContext)context).getPictogramElement();
		rt.notify(new LifecycleEvent(EventType.PICTOGRAMELEMENT_LAYOUT, getFeatureProvider(), context, pe));
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
			doLayout(connection);
		}
		return hasDoneChanges;
	}
	
	private int recursion = 0;
	
	private synchronized void doLayout(Connection connection) {
		if (++recursion < 10) {
			BaseElement be = BusinessObjectUtil.getFirstBaseElement(connection);
			if (be!=null) {
				// get the user preference of routing style for this connection
				ShapeStyle ss = ShapeStyle.getShapeStyle(be);
				if (ss!=null) {
					IFeatureProvider fp = getFeatureProvider();
					IConnectionRouter router = null;
					if (ss.getRoutingStyle() == RoutingStyle.MANHATTAN)
						router = new ManhattanConnectionRouter(fp);
					else if (ss.getRoutingStyle() == RoutingStyle.MANUAL) {
						router = new BendpointConnectionRouter(fp);
					}
					else if (ss.getRoutingStyle() == RoutingStyle.AUTOMATIC) {
						router = new AutomaticConnectionRouter(fp);
					}
	
					if (router!=null) {
						hasDoneChanges = router.route(connection);
	
						Diagram diagram = fp.getDiagramTypeProvider().getDiagram();
						for (Connection c : diagram.getConnections()) {
							if (needsLayout(c)) {
								doLayout(c);
							}
						}
	
						router.dispose();
						
					}
				}
			}
			--recursion;
		}
	}
	
	private boolean needsLayout(Connection connection) {
		BaseElement be = BusinessObjectUtil.getFirstBaseElement(connection);
		if (be!=null) {
			// get the user preference of routing style for this connection
			ShapeStyle ss = ShapeStyle.getShapeStyle(be);
			if (ss!=null) {
				IFeatureProvider fp = getFeatureProvider();
				IConnectionRouter router = null;
				if (ss.getRoutingStyle() == RoutingStyle.MANHATTAN)
					router = new ManhattanConnectionRouter(fp);
				else if (ss.getRoutingStyle() == RoutingStyle.MANUAL) {
					router = new BendpointConnectionRouter(fp);
				}
				else if (ss.getRoutingStyle() == RoutingStyle.AUTOMATIC) {
					router = new AutomaticConnectionRouter(fp);
				}
		
				if (router!=null) {
					return router.needsLayout(connection);
				}
			}
		}
		return false;
	}
}

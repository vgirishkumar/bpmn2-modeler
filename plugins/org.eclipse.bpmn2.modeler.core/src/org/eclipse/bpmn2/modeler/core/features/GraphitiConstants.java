/*******************************************************************************
 * Copyright (c) 2011, 2012 Red Hat, Inc. 
 * All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 *
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 *******************************************************************************/
package org.eclipse.bpmn2.modeler.core.features;

import org.eclipse.graphiti.features.context.IContext;

/**
 * Keys used to store Graphiti Shape and Context properties.
 * <p>
 * Some figure construction and updating operations need to pass information from one
 * Graphiti Feature to another (e.g. during a CreateFeature the AddFeature, UpdateFeature
 * and LayouFeatures are invoked). This is done through the Shape property list, and the
 * {@link IContext} property list. Note that Shape properties are String only, whereas IContext
 * properties are Objects, allowing greater flexibility when passing information from Feature
 * to Feature. Shape properties must be encoded into Strings if we need to handle Objects.  
 */
public interface GraphitiConstants {

	/** The Constant LABEL_CONTEXT. */
	public static final String LABEL_CONTEXT = "labelContext"; //$NON-NLS-1$
	
	/** The Constant WIDTH. */
	public static final String WIDTH = "width"; //$NON-NLS-1$
	
	/** The Constant HEIGHT. */
	public static final String HEIGHT = "height"; //$NON-NLS-1$
	
	/** The Constant BUSINESS_OBJECT. */
	public static final String BUSINESS_OBJECT = "businessObject"; //$NON-NLS-1$
	
	/** The Constant LABEL_OWNER. */
	public static final String LABEL_OWNER = "labelOwner"; //$NON-NLS-1$

	public static final String LABEL_PROPERTY = "label"; //$NON-NLS-1$
	public static final String LABEL_LOCATION = "label.location"; //$NON-NLS-1$

	public static final String IMPORT_PROPERTY = "is.importing"; //$NON-NLS-1$

	public final static String DI_ELEMENT_HAS_LABEL = "di.element.has.label";

	/** The Constant ACTIVITY_DECORATOR. */
	public static final String ACTIVITY_DECORATOR = "activity-decorator"; //$NON-NLS-1$

	/** The Constant IS_ACTIVITY. */
	public static final String IS_ACTIVITY = "activity"; //$NON-NLS-1$

	/** The Constant ACTIVITY_MOVE_PROPERTY. */
	public static final String ACTIVITY_MOVE_PROPERTY = "activity.move"; //$NON-NLS-1$

	/** The Constant SELECTION_MOVE_PROPERTY. */
	public static final String SELECTION_MOVE_PROPERTY = "selection.move"; //$NON-NLS-1$

	/** The is compensate property. */
	public final static String IS_COMPENSATE_PROPERTY = "marker.compensate"; //$NON-NLS-1$

	/** The is loop or multi instance. */
	public final static String IS_LOOP_OR_MULTI_INSTANCE = "marker.loop.or.multi"; //$NON-NLS-1$

	public final static String COMMAND_HINT = "command.hint";  //$NON-NLS-1$

	public static final String COLLECTION_PROPERTY = "isCollection"; //$NON-NLS-1$

	public static final String HIDEABLE_PROPERTY = "hideable"; //$NON-NLS-1$

	public static final String DATASTATE_PROPERTY = "datastate"; //$NON-NLS-1$

	public static final String IS_HORIZONTAL_PROPERTY = "isHorizontal"; //$NON-NLS-1$

	public static final String TOOLTIP_PROPERTY = "tooltip"; //$NON-NLS-1$

	public final static String EVENT_DEFINITION_SHAPE = "event.definition.shape";

	public static final String EVENT_ELEMENT = "event.graphics.element"; //$NON-NLS-1$

	public static final String EVENT_CIRCLE = "event.graphics.element.circle"; //$NON-NLS-1$

	public static final String ACTIVITY_MARKER_CONTAINER = "activity.marker.container"; //$NON-NLS-1$

	public static final String ACTIVITY_MARKER_COMPENSATE = "activity.marker.compensate"; //$NON-NLS-1$

	public static final String ACTIVITY_MARKER_LC_STANDARD = "activity.marker.lc.standard"; //$NON-NLS-1$

	public static final String ACTIVITY_MARKER_LC_MULTI_SEQUENTIAL = "activity.marker.lc.multi.sequential"; //$NON-NLS-1$

	public static final String ACTIVITY_MARKER_LC_MULTI_PARALLEL = "activity.marker.lc.multi.parallel"; //$NON-NLS-1$

	public static final String ACTIVITY_MARKER_AD_HOC = "activity.marker.adhoc"; //$NON-NLS-1$

	public static final String ACTIVITY_MARKER_EXPAND = "activity.marker.expand"; //$NON-NLS-1$

	public static final String ACTIVITY_MARKER_OFFSET = "activity.marker.offset"; //$NON-NLS-1$

	public static final String EVENT_MARKER_CONTAINER = "event.marker.container"; //$NON-NLS-1$

	public static final String TEXT_ELEMENT = "baseelement.text"; //$NON-NLS-1$

	public static final String LANE_RESIZE_PROPERTY = "lane.resize"; //$NON-NLS-1$

	public static final String MULTIPLICITY = "multiplicity"; //$NON-NLS-1$

	public static final String POOL_RESIZE_PROPERTY = "pool.resize"; //$NON-NLS-1$

	public static final String RESIZE_FIRST_LANE = "resize.first.lane"; //$NON-NLS-1$

	public static final String MULTIPLICITY_MARKER = "multiplicity.marker"; //$NON-NLS-1$

	public final static String PARENT_CONTAINER = "parent.container";

	/**
	 * The key used to store the copied shape in the Paste Context.
	 * This is copied to the AddContext and picked up by the AddFeature
	 * which duplicates the copied shape's size and other attributes.
	 */
	public static final String COPIED_BPMN_SHAPE = "copied.bpmn.shape"; //$NON-NLS-1$

	/** The key used to store the copied business object in the Paste Context. */
	public static final String COPIED_BPMN_OBJECT = "copied.bpmn.object"; //$NON-NLS-1$

	/** The Constant COPY_FROM_CONTEXT. */
	public static final String COPY_FROM_CONTEXT = "copy.from.context"; //$NON-NLS-1$

	/** The Constant CUSTOM_ELEMENT_ID. */
	public final static String CUSTOM_ELEMENT_ID = "custom.element.id"; //$NON-NLS-1$

	/** The Constant FORCE_UPDATE_ALL. */
	public final static String FORCE_UPDATE_ALL = "force.update.all"; //$NON-NLS-1$

	public static final String BOUNDARY_EVENT_RELATIVE_POS = "boundary.event.relative.pos"; //$NON-NLS-1$

	public static final String BOUNDARY_FIXPOINT_ANCHOR = "boundary.fixpoint.anchor"; //$NON-NLS-1$

	public static final String BOUNDARY_ADHOC_ANCHOR = "boundary.adhoc.anchor"; //$NON-NLS-1$

	public static final String CONNECTION_SOURCE_LOCATION = "connection.source.location"; //$NON-NLS-1$

	public static final String CONNECTION_TARGET_LOCATION = "connection.target.location"; //$NON-NLS-1$

	public static final String CONNECTION_CREATED = "connection.created"; //$NON-NLS-1$

	// values for connection points
	public static final String CONNECTION_POINT = "connection.point"; //$NON-NLS-1$

	public static final String CONNECTION_POINT_KEY = "connection.point.key"; //$NON-NLS-1$

}

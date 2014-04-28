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
package org.eclipse.bpmn2.modeler.core.features.activity;

import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.LoopCharacteristics;
import org.eclipse.bpmn2.MultiInstanceLoopCharacteristics;
import org.eclipse.bpmn2.StandardLoopCharacteristics;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;

// TODO: Auto-generated Javadoc
/**
 * The Class UpdateActivityLoopAndMultiInstanceMarkerFeature.
 */
public class UpdateActivityLoopAndMultiInstanceMarkerFeature extends AbstractUpdateMarkerFeature<Activity> {

	/** The is loop or multi instance. */
	public static String IS_LOOP_OR_MULTI_INSTANCE = "marker.loop.or.multi"; //$NON-NLS-1$

	/**
	 * The Enum LoopCharacteristicType.
	 */
	enum LoopCharacteristicType {
		
		/** The null. */
		NULL("null"),  //$NON-NLS-1$
		
		/** The loop. */
  LOOP(StandardLoopCharacteristics.class.getSimpleName()), 
		
		/** The multi parallel. */
		MULTI_PARALLEL(MultiInstanceLoopCharacteristics.class.getSimpleName() + ":parallel"),  //$NON-NLS-1$
		
		/** The multi sequential. */
  MULTI_SEQUENTIAL(MultiInstanceLoopCharacteristics.class.getSimpleName() + ":sequential"); //$NON-NLS-1$

		private String name;

		private LoopCharacteristicType(String name) {
			this.name = name;
		}

		/**
		 * Gets the name.
		 *
		 * @return the name
		 */
		String getName() {
			return name;
		}
	}

	/**
	 * Instantiates a new update activity loop and multi instance marker feature.
	 *
	 * @param fp the fp
	 */
	public UpdateActivityLoopAndMultiInstanceMarkerFeature(IFeatureProvider fp) {
		super(fp);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.features.activity.AbstractUpdateMarkerFeature#getPropertyKey()
	 */
	@Override
	protected String getPropertyKey() {
		return IS_LOOP_OR_MULTI_INSTANCE;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.features.activity.AbstractUpdateMarkerFeature#isPropertyChanged(org.eclipse.bpmn2.FlowElement, java.lang.String)
	 */
	@Override
	protected boolean isPropertyChanged(Activity activity, String propertyValue) {
		return !getLoopCharacteristicsValue(activity).getName().equals(propertyValue);
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.features.activity.AbstractUpdateMarkerFeature#doUpdate(org.eclipse.bpmn2.FlowElement, org.eclipse.graphiti.mm.pictograms.ContainerShape)
	 */
	@Override
	protected void doUpdate(Activity activity, ContainerShape markerContainer) {
		switch (getLoopCharacteristicsValue(activity)) {
		case LOOP:
			GraphicsUtil.showActivityMarker(markerContainer, GraphicsUtil.ACTIVITY_MARKER_LC_STANDARD);
			GraphicsUtil.hideActivityMarker(markerContainer, GraphicsUtil.ACTIVITY_MARKER_LC_MULTI_PARALLEL);
			GraphicsUtil.hideActivityMarker(markerContainer, GraphicsUtil.ACTIVITY_MARKER_LC_MULTI_SEQUENTIAL);
			break;
		case MULTI_PARALLEL:
			GraphicsUtil.hideActivityMarker(markerContainer, GraphicsUtil.ACTIVITY_MARKER_LC_STANDARD);
			GraphicsUtil.showActivityMarker(markerContainer, GraphicsUtil.ACTIVITY_MARKER_LC_MULTI_PARALLEL);
			GraphicsUtil.hideActivityMarker(markerContainer, GraphicsUtil.ACTIVITY_MARKER_LC_MULTI_SEQUENTIAL);
			break;
		case MULTI_SEQUENTIAL:
			GraphicsUtil.hideActivityMarker(markerContainer, GraphicsUtil.ACTIVITY_MARKER_LC_STANDARD);
			GraphicsUtil.hideActivityMarker(markerContainer, GraphicsUtil.ACTIVITY_MARKER_LC_MULTI_PARALLEL);
			GraphicsUtil.showActivityMarker(markerContainer, GraphicsUtil.ACTIVITY_MARKER_LC_MULTI_SEQUENTIAL);
			break;
		default:
			GraphicsUtil.hideActivityMarker(markerContainer, GraphicsUtil.ACTIVITY_MARKER_LC_STANDARD);
			GraphicsUtil.hideActivityMarker(markerContainer, GraphicsUtil.ACTIVITY_MARKER_LC_MULTI_PARALLEL);
			GraphicsUtil.hideActivityMarker(markerContainer, GraphicsUtil.ACTIVITY_MARKER_LC_MULTI_SEQUENTIAL);
		}
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.features.activity.AbstractUpdateMarkerFeature#convertPropertyToString(org.eclipse.bpmn2.FlowElement)
	 */
	@Override
	protected String convertPropertyToString(Activity activity) {
		return getLoopCharacteristicsValue(activity).getName();
	}

	/**
	 * Gets the loop characteristics value.
	 *
	 * @param activity the activity
	 * @return the loop characteristics value
	 */
	public static LoopCharacteristicType getLoopCharacteristicsValue(Activity activity) {
		LoopCharacteristics loopCharacteristics = activity.getLoopCharacteristics();
		LoopCharacteristicType type = LoopCharacteristicType.NULL;

		if (loopCharacteristics != null) {
			if (loopCharacteristics instanceof MultiInstanceLoopCharacteristics) {
				MultiInstanceLoopCharacteristics multi = (MultiInstanceLoopCharacteristics) loopCharacteristics;
				type = multi.isIsSequential() ? LoopCharacteristicType.MULTI_SEQUENTIAL
				        : LoopCharacteristicType.MULTI_PARALLEL;
			} else {
				type = LoopCharacteristicType.LOOP;
			}
		}

		return type;
	}
}
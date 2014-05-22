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

package org.eclipse.bpmn2.modeler.core.features.choreography;

import org.eclipse.bpmn2.modeler.core.features.label.UpdateLabelFeature;
import org.eclipse.bpmn2.modeler.core.preferences.ShapeStyle.LabelPosition;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.mm.algorithms.AbstractText;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

public class UpdateChoreographyLabelFeature extends UpdateLabelFeature {

	/**
	 * @param fp
	 */
	public UpdateChoreographyLabelFeature(IFeatureProvider fp) {
		super(fp);
	}
	
	protected LabelPosition getHorizontalLabelPosition(AbstractText text) {
		PictogramElement pe = FeatureSupport.getLabelOwner(text);
		Object bo = getBusinessObjectForPictogramElement(pe);
		if (FeatureSupport.isElementExpanded(bo)) {
			return LabelPosition.LEFT;
		}
		return LabelPosition.CENTER;
	}
	
	protected LabelPosition getVerticalLabelPosition(AbstractText text) {
		PictogramElement pe = FeatureSupport.getLabelOwner(text);
		Object bo = getBusinessObjectForPictogramElement(pe);
		if (FeatureSupport.isElementExpanded(bo)) {
			return LabelPosition.TOP;
		}
		return LabelPosition.CENTER;
	}

	@Override
	protected ContainerShape getTargetContainer(PictogramElement ownerPE) {
		return (ContainerShape) ownerPE;
	}
}
/*******************************************************************************
 * Copyright (c) 2011, 2012, 2013, 2014 Red Hat, Inc.
 * All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.core.features.label;

import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IMoveShapeContext;
import org.eclipse.graphiti.features.impl.DefaultMoveShapeFeature;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;

public class MoveLabelFeature extends DefaultMoveShapeFeature {

	public MoveLabelFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public void moveShape(IMoveShapeContext context) {
		// if this Label Shape is part of a multiselection, and if its owner is included
		// in that multiselection, then do not move the label. Moving the label is
		// already handled by the label's owner.
		PictogramElement pes[] = getFeatureProvider().getDiagramTypeProvider().
				getDiagramBehavior().getDiagramContainer().getSelectedPictogramElements();
		Shape shape = context.getShape();
		for (PictogramElement pe : pes) {
			ContainerShape s = BusinessObjectUtil.getFirstElementOfType(pe, ContainerShape.class);
			if (s==shape)
				return;
		}
		super.moveShape(context);
	}

}

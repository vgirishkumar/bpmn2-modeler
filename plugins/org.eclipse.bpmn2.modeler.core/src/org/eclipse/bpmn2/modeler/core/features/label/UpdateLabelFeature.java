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
package org.eclipse.bpmn2.modeler.core.features.label;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.modeler.core.di.DIImport;
import org.eclipse.bpmn2.modeler.core.features.AbstractBpmn2UpdateFeature;
import org.eclipse.bpmn2.modeler.core.features.GraphitiConstants;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.AbstractText;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;

public class UpdateLabelFeature extends AbstractBpmn2UpdateFeature {

	public UpdateLabelFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canUpdate(IUpdateContext context) {
		BaseElement element = (BaseElement) BusinessObjectUtil.getFirstElementOfType(context.getPictogramElement(),
		        BaseElement.class);
		if (element == null) {
			return false;
		}
		return hasLabel(element);
	}

	@Override
	public IReason updateNeeded(IUpdateContext context) {
		IReason reason = super.updateNeeded(context);
		if (reason.toBoolean())
			return reason;

		PictogramElement container = context.getPictogramElement();

		BaseElement element = (BaseElement) BusinessObjectUtil.getFirstElementOfType(container,
		        BaseElement.class);

		Shape textShape = FeatureSupport.getChildElementOfType(container, GraphitiConstants.TEXT_ELEMENT, Boolean.toString(true), Shape.class);
		if (textShape!=null) {
			String oldLabel = LabelFeatureContainer.getLabelString(element);
			if (oldLabel==null || oldLabel.isEmpty())
				oldLabel = ""; //$NON-NLS-1$
			String newLabel = ""; //$NON-NLS-1$
			if (textShape.getGraphicsAlgorithm() instanceof AbstractText) {
				AbstractText text = (AbstractText) textShape.getGraphicsAlgorithm();
				newLabel = text.getValue();
			}
			if (newLabel==null || newLabel.isEmpty())
				newLabel = ""; //$NON-NLS-1$
			
			if (!oldLabel.equals(newLabel))
				return Reason.createTrueReason(Messages.UpdateLabelFeature_Label);
			
			String oldLoc = Graphiti.getPeService().getPropertyValue(container, GraphitiConstants.LABEL_LOCATION);
			if (oldLoc==null)
				oldLoc = "";
			String newLoc = LabelFeatureContainer.getLabelLocationAsString(textShape, DIImport.isImporting(context));
			if (!newLoc.equals(oldLoc))
				return Reason.createTrueReason(Messages.UpdateLabelFeature_Label);
		}
		return Reason.createFalseReason();
	}

	@Override
	public boolean update(IUpdateContext context) {
		PictogramElement pe = LabelFeatureContainer.getLabelOwner(context);
		if (pe==null)
			pe = context.getPictogramElement();
		LabelFeatureContainer.adjustLabelLocation(pe, DIImport.isImporting(context), null);
		return true;
	}
	
	protected boolean hasLabel(BaseElement element) {
		return  ModelUtil.hasName(element);
	}
}
/*******************************************************************************
 * Copyright (c) 2011, 2012, 2013 Red Hat, Inc.
 * All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 * 	Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.features;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.ui.features.activity.subprocess.CollapseFlowNodeFeature;
import org.eclipse.bpmn2.modeler.ui.features.activity.subprocess.ExpandFlowNodeFeature;
import org.eclipse.bpmn2.modeler.ui.features.activity.subprocess.PullupFeature;
import org.eclipse.bpmn2.modeler.ui.features.activity.subprocess.PushdownFeature;
import org.eclipse.bpmn2.modeler.ui.features.activity.subprocess.AdHocSubProcessFeatureContainer;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

public class JbpmAdHocSubProcessFeatureContainer extends AdHocSubProcessFeatureContainer {

	@Override
	public IAddFeature getAddFeature(IFeatureProvider fp) {
		return new AddAdHocSubProcessFeature(fp) {

			@Override
			public PictogramElement add(IAddContext context) {
				PictogramElement pe = super.add(context);
				BPMNShape bpmnShape = BusinessObjectUtil.getFirstElementOfType(pe, BPMNShape.class);
				bpmnShape.setIsExpanded(true);
				return pe;
			}
		};
	}

	@Override
	public ICustomFeature[] getCustomFeatures(IFeatureProvider fp) {
		ICustomFeature[] superFeatures = super.getCustomFeatures(fp);
		List<ICustomFeature> thisFeatures = new ArrayList<ICustomFeature>();
		for (ICustomFeature f : superFeatures) {
			if (f instanceof ExpandFlowNodeFeature ||
					f instanceof CollapseFlowNodeFeature ||
					f instanceof PushdownFeature ||
					f instanceof PullupFeature)
				continue;
			thisFeatures.add(f);
		}
		return thisFeatures.toArray(new ICustomFeature[thisFeatures.size()]);
	}
}

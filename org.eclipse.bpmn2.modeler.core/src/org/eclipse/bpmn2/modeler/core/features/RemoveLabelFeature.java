package org.eclipse.bpmn2.modeler.core.features;

import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IRemoveContext;
import org.eclipse.graphiti.features.context.impl.RemoveContext;
import org.eclipse.graphiti.features.impl.DefaultRemoveFeature;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;

public class RemoveLabelFeature extends DefaultRemoveFeature {

	public RemoveLabelFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public void remove(IRemoveContext context) {
		PictogramElement removedElement = context.getPictogramElement();
		ContainerShape labelShape = BusinessObjectUtil.getFirstElementOfType(removedElement, ContainerShape.class);
		if (labelShape!=null) {
			// this is the label ContainerShape that belongs to the PE being removed
			RemoveContext removeContext = new RemoveContext(labelShape);
			super.remove(removeContext);
		}
		
		super.remove(context);
	}

}

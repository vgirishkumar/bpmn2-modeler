package org.eclipse.bpmn2.modeler.examples.customtask;

import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.ui.features.activity.task.CustomTaskFeatureContainer;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class MyTaskFeatureContainer extends CustomTaskFeatureContainer {

	public MyTaskFeatureContainer() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getId(EObject object) {
		EStructuralFeature f = ModelUtil.getAnyAttribute(object, "type");
		if (f!=null) {
			return this.id;
		}
			
		return null;
	}

}

package org.eclipse.bpmn2.modeler.examples.customtask;

import org.eclipse.bpmn2.BoundaryEvent;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.ui.features.activity.task.CustomTaskFeatureContainer;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;

public class MyBoundaryEventFeatureContainer extends CustomTaskFeatureContainer {

	public MyBoundaryEventFeatureContainer() {
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

	@Override
	public boolean canApplyTo(Object o) {
		boolean b1 =  o instanceof BoundaryEvent;
		boolean b2 = o.getClass().isAssignableFrom(BoundaryEvent.class);
		return b1 || b2;
	}

}

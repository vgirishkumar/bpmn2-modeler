package org.eclipse.bpmn2.modeler.ui.property;

import java.util.ArrayList;

import org.eclipse.bpmn2.modeler.core.runtime.PropertyTabDescriptor;

public class TabDescriptorList extends ArrayList<PropertyTabDescriptor> {
	private static final long serialVersionUID = -296768469891312674L;

	@Override
	public PropertyTabDescriptor[] toArray() {
		return this.toArray(new PropertyTabDescriptor[this.size()]);
	}
}
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property;

import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.data.InterfacePropertySection;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;

public class JbpmInterfacePropertySection extends InterfacePropertySection {

	public JbpmInterfacePropertySection() {
		super();
	}

	@Override
	protected AbstractDetailComposite createSectionRoot() {
		return new JbpmInterfaceSectionRoot(this);
	}

	public class JbpmInterfaceSectionRoot extends InterfaceSectionRoot {

		public JbpmInterfaceSectionRoot(Composite parent, int style) {
			super(parent, style);
		}

		public JbpmInterfaceSectionRoot(AbstractBpmn2PropertySection section) {
			super(section);
		}
		
		@Override
		public void createBindings(EObject be) {
			definedInterfacesTable = new DefinedInterfaceListComposite(this);
			definedInterfacesTable.bindList(be);
		}
		
	}
}

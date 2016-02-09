package com.odcgroup.bpmn2.modeler.extensions.property;

import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property.JbpmUserTaskPropertySection;
import org.eclipse.swt.widgets.Composite;

/**
 *
 * @author phanikumark
 *
 */
public class UserTaskSection extends JbpmUserTaskPropertySection {

    /* (non-Javadoc)
     * @see org.eclipse.bpmn2.modeler.ui.property.AbstractBpmn2PropertySection#createSectionRoot()
     */
    @Override
    protected AbstractDetailComposite createSectionRoot() {
        return new T24UserTaskDetailComposite(this);
    }

    @Override
    public AbstractDetailComposite createSectionRoot(Composite parent, int style) {
        return new T24UserTaskDetailComposite(parent,style);
    }

}

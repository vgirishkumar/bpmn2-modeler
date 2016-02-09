package com.odcgroup.bpmn2.modeler.extensions.property;

import org.eclipse.bpmn2.ScriptTask;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.ui.property.tasks.IoParametersPropertySection;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.widgets.Composite;

/**
 *
 * @author phanikumark
 *
 */
public class T24UserTaskIoParametersPropertySection extends IoParametersPropertySection {
    
    @Override
    protected AbstractDetailComposite createSectionRoot() {
        return new T24IoParametersDetailComposite(this);
    }

    @Override
    public AbstractDetailComposite createSectionRoot(Composite parent, int style) {
        return new T24IoParametersDetailComposite(parent,style);
    }

    @Override
    public EObject getBusinessObjectForSelection(ISelection selection) {
        EObject be = super.getBusinessObjectForSelection(selection);
        if (be instanceof ScriptTask || be==null)
            return null;
        return be;
    }

}

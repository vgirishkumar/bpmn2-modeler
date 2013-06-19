package org.eclipse.bpmn2.modeler.core.features;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICopyContext;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.features.AbstractCopyFeature;

public class DefaultCopyBPMNElementFeature extends AbstractCopyFeature {

	public DefaultCopyBPMNElementFeature(IFeatureProvider fp) {
		super(fp);
	}
	
	@Override
	public void copy(ICopyContext context) {
		// TODO: COPY-PASTE
        // get the business-objects for all pictogram-elements
        // we already verified that all business-objets are valid
        PictogramElement[] pes = context.getPictogramElements();
        Object[] bos = new Object[pes.length];
        for (int i = 0; i < pes.length; i++) {
            PictogramElement pe = pes[i];
            bos[i] = BusinessObjectUtil.getFirstBaseElement(pe);
        }
        // put all business objects to the clipboard
        putToClipboard(bos);
	}

	@Override
	public boolean canCopy(ICopyContext context) {
        final PictogramElement[] pes = context.getPictogramElements();
        if (pes == null || pes.length == 0) {  // nothing selected
            return false;
        }
       
        // return true, if all selected elements are a EClasses
        for (PictogramElement pe : pes) {
            final Object bo = BusinessObjectUtil.getFirstBaseElement(pe);
            if (!(bo instanceof BaseElement)) {
                return false;
            }
        }
        return true;
	}
}

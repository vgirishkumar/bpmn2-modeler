package org.eclipse.bpmn2.modeler.core.features;

import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.di.BPMNEdge;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IPasteContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.features.AbstractPasteFeature;

public class DefaultPasteBPMNElementFeature extends AbstractPasteFeature {

	public DefaultPasteBPMNElementFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public void paste(IPasteContext context) {
		// TODO: COPY-PASTE
        // we already verified, that we paste directly in the diagram
        PictogramElement[] pes = context.getPictogramElements();
        Diagram diagram = (Diagram) pes[0];
        // get the BaseElements from the clipboard without copying them
        // (only copy the pictogram element, not the business object)
        // then create new pictogram elements using the add feature
        Object[] objects = getFromClipboard();
        for (Object object : objects) {
            AddContext ac = new AddContext();
            if (object instanceof BaseElement) {
            	
            	BPMNShape bpmnShape = DIUtils.findBPMNShape((BaseElement)object);
            	if (bpmnShape==null) {
            		BPMNEdge bpmnEdge = DIUtils.findBPMNEdge((BaseElement)object);
            	}
            }
            ac.setLocation(0, 0); // for simplicity paste at (0, 0)
            ac.setTargetContainer(diagram);
            addGraphicalRepresentation(ac, object);
        }
	}

	@Override
	public boolean canPaste(IPasteContext context) {
        // only support pasting directly in the diagram (nothing else selected)
        PictogramElement[] pes = context.getPictogramElements();
        if (pes.length != 1 || !(pes[0] instanceof Diagram)) {
            return false;
        }
 
        // can paste, if all objects on the clipboard are BaseElements
        Object[] fromClipboard = getFromClipboard();
        if (fromClipboard == null || fromClipboard.length == 0) {
            return false;
        }
        for (Object object : fromClipboard) {
            if (!(object instanceof BaseElement)) {
                return false;
            }
        }

        return true;
	}
}

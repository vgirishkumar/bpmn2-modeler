package org.eclipse.bpmn2.modeler.ui.views.outline;

import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.gef.ui.parts.TreeViewer;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.ui.editor.DiagramEditor;

public class BPMN2EditorOutlineTreeViewer extends TreeViewer {

	protected DiagramEditor diagramEditor;
	
	public BPMN2EditorOutlineTreeViewer(DiagramEditor diagramEditor) {
		this.diagramEditor = diagramEditor;
	}
	
	public EditPart convert(EditPart part) {
		Object model = part.getModel();
		if (model instanceof PictogramElement) {
			PictogramElement pe = (PictogramElement)model;
			EObject baseElement = BusinessObjectUtil.getFirstBaseElement(pe);
			return (EditPart)getEditPartRegistry().get(baseElement);
		}
		return part;
	}
	
	public static EditPart convert(GraphicalViewer viewer, AbstractGraphicsTreeEditPart part) {
		Object pe = part.getAdapter(PictogramElement.class);
		return (EditPart) viewer.getEditPartRegistry().get(pe);
	}
}

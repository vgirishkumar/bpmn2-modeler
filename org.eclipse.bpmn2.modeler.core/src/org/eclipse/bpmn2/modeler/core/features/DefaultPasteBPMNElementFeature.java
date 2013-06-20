package org.eclipse.bpmn2.modeler.core.features;

import java.util.Hashtable;
import java.util.Map;

import org.eclipse.bpmn2.Association;
import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.ConversationLink;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.FlowElementsContainer;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.InteractionNode;
import org.eclipse.bpmn2.MessageFlow;
import org.eclipse.bpmn2.Participant;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil.AnchorLocation;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil.BoundaryAnchor;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.datatypes.ILocation;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IPasteContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.features.context.impl.AreaContext;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.features.AbstractPasteFeature;

public class DefaultPasteBPMNElementFeature extends AbstractPasteFeature {

	private Resource resource;
	
	public DefaultPasteBPMNElementFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public void paste(IPasteContext context) {
		// TODO: COPY-PASTE
        PictogramElement[] pes = context.getPictogramElements();
        ContainerShape targetContainerShape = (ContainerShape) pes[0];
        EObject bo = BusinessObjectUtil.getBusinessObjectForPictogramElement(targetContainerShape);
        if (bo instanceof BPMNDiagram) {
        	bo = ((BPMNDiagram)bo).getPlane().getBpmnElement();
        }
    	if (bo instanceof Participant) {
    		bo = ((Participant)bo).getProcessRef();
    	}
    	FlowElementsContainer targetContainerObject = (FlowElementsContainer)bo;
        resource = targetContainerObject.eResource();

        Object[] fromClipboard = getFromClipboard();
        int x0 = 0;
        int y0 = 0;
        for (Object object : fromClipboard) {
	        if (object instanceof ContainerShape) {
	        	ILocation loc = Graphiti.getLayoutService().getLocationRelativeToDiagram((ContainerShape)object);
	        	x0 = loc.getX();
	        	y0 = loc.getY();
	        	break;
	        }
        }
       	int x = context.getX();
       	int y = context.getY();
       	if (!(targetContainerShape instanceof Diagram)) {
       		ILocation loc = Graphiti.getLayoutService().getLocationRelativeToDiagram(targetContainerShape);
       		x -= loc.getX();
       		y -= loc.getY();
       	}

       	// First create all shapes
       	Hashtable <ContainerShape,ContainerShape> shapeMap = new Hashtable<ContainerShape,ContainerShape>();
        for (Object object : fromClipboard) {
        	
	        if (object instanceof ContainerShape) {
	        	ContainerShape oldShape = (ContainerShape)object;
	        	BaseElement oldObject = BusinessObjectUtil.getFirstBaseElement(oldShape);
	        	BaseElement newObject = createNewObject(oldObject, targetContainerObject);

	        	AddContext ac = new AddContext(new AreaContext(), newObject);
	        	ILocation loc = Graphiti.getLayoutService().getLocationRelativeToDiagram(oldShape);
	        	IDimension size = GraphicsUtil.calculateSize(oldShape);
	        	// The default Add BPMN Shape feature will position the new shape so its center is
	        	// at the target location; for copy/paste we want to use the top-left corner instead.
	        	// so that copied connection bendpoints (if any) line up properly.
	        	int deltaX = loc.getX() - x0 + size.getWidth()/2;
	        	int deltaY = loc.getY() - y0 + size.getHeight()/2;
	            ac.setLocation(x + deltaX, y + deltaY);
	        	ac.setSize(size.getWidth(), size.getHeight());
	            ac.setTargetContainer(targetContainerShape);

	            IAddFeature af = getFeatureProvider().getAddFeature(ac);
	           	ContainerShape newShape = (ContainerShape)af.add(ac);
	            shapeMap.put(oldShape, newShape);
	        }
        }
        
        // Handle connections now that we know all shapes have been created
        for (Object object : fromClipboard) {
        	
	        if (object instanceof Connection) {
	        	Connection oldConnection = (Connection)object;
	        	BaseElement oldObject = BusinessObjectUtil.getFirstBaseElement(oldConnection);
	        	BaseElement newObject = createNewObject(oldObject, targetContainerObject);

	        	Anchor oldStart = oldConnection.getStart();
	        	Anchor oldEnd = oldConnection.getEnd();
	        	ContainerShape newSource = shapeMap.get(oldStart.getParent());
	        	ContainerShape newTarget = shapeMap.get(oldEnd.getParent());
	        	Anchor newStart;
	        	Anchor newEnd;
	        	if (AnchorUtil.isBoundaryAnchor(oldStart)) {
	        		AnchorLocation al = AnchorUtil.getBoundaryAnchorLocation(oldStart);
	        		Map<AnchorLocation, BoundaryAnchor> bas = AnchorUtil.getBoundaryAnchors(newSource);
	        		newStart = bas.get(al).anchor;
	        	}
	        	else {
	        		ILocation loc = Graphiti.getLayoutService().getLocationRelativeToDiagram(oldStart);
	        		newStart = AnchorUtil.createAdHocAnchor(newSource, loc.getX(), loc.getY());
	        	}
	        	if (AnchorUtil.isBoundaryAnchor(oldEnd)) {
	        		AnchorLocation al = AnchorUtil.getBoundaryAnchorLocation(oldEnd);
	        		Map<AnchorLocation, BoundaryAnchor> bas = AnchorUtil.getBoundaryAnchors(newTarget);
	        		newEnd = bas.get(al).anchor;
	        	}
	        	else {
	        		ILocation loc = Graphiti.getLayoutService().getLocationRelativeToDiagram(oldEnd);
	        		newEnd = AnchorUtil.createAdHocAnchor(newTarget, loc.getX(), loc.getY());
	        	}
	        	
        		BaseElement newSourceObject = BusinessObjectUtil.getFirstBaseElement(newSource);
        		BaseElement newTargetObject = BusinessObjectUtil.getFirstBaseElement(newTarget);
	        	if (newObject instanceof SequenceFlow) {
	        		((SequenceFlow)newObject).setSourceRef((FlowNode)newSourceObject);
	        		((SequenceFlow)newObject).setTargetRef((FlowNode)newTargetObject);
	        	}
	        	else if (newObject instanceof Association) {
	        		((Association)newObject).setSourceRef((FlowNode)newSourceObject);
	        		((Association)newObject).setTargetRef((FlowNode)newTargetObject);
	        	}
	        	else if (newObject instanceof MessageFlow) {
	        		((MessageFlow)newObject).setSourceRef((InteractionNode)newSourceObject);
	        		((MessageFlow)newObject).setTargetRef((InteractionNode)newTargetObject);
	        	}
	        	else if (newObject instanceof ConversationLink) {
	        		((ConversationLink)newObject).setSourceRef((InteractionNode)newSourceObject);
	        		((ConversationLink)newObject).setTargetRef((InteractionNode)newTargetObject);
	        	}
	    		AddConnectionContext acc = new AddConnectionContext(newStart,newEnd);
	    		acc.setNewObject(newObject);

	           	IAddFeature af = getFeatureProvider().getAddFeature(acc);
	    		Connection newConnection = (Connection) af.add(acc);
	    		
				if (oldConnection instanceof FreeFormConnection && newConnection instanceof FreeFormConnection) {
					for (Point p : ((FreeFormConnection)oldConnection).getBendpoints()) {
			        	int deltaX = p.getX() - x0;
			        	int deltaY = p.getY() - y0;
			        	Point newPoint = GraphicsUtil.createPoint(context.getX() + deltaX, context.getY() + deltaY);
						((FreeFormConnection)newConnection).getBendpoints().add(newPoint);
					}
				}
	        }
        }
	}

	@Override
	public boolean canPaste(IPasteContext context) {
        PictogramElement[] pes = context.getPictogramElements();
        if (pes.length != 1 || !(pes[0] instanceof ContainerShape)) {
            return false;
        }
        // target must be a FlowElementsContainer (Process, etc.)
        EObject targetBusinessObject = BusinessObjectUtil.getBusinessObjectForPictogramElement(pes[0]);
        if (targetBusinessObject instanceof BPMNDiagram) {
        	targetBusinessObject = ((BPMNDiagram)targetBusinessObject).getPlane().getBpmnElement();
        }
    	if (targetBusinessObject instanceof Participant) {
    		targetBusinessObject = ((Participant)targetBusinessObject).getProcessRef();
    	}
    	if (!(targetBusinessObject instanceof FlowElementsContainer))
    		return false;

        // can paste, if all objects on the clipboard are BaseElements
        Object[] fromClipboard = getFromClipboard();
        if (fromClipboard == null || fromClipboard.length == 0) {
            return false;
        }
        for (Object object : fromClipboard) {
            if (!(object instanceof PictogramElement)) {
                return false;
            }
           	PictogramElement pe = (PictogramElement)object;
        	BaseElement be = BusinessObjectUtil.getFirstBaseElement(pe);
        	if (!(be instanceof FlowElement)) {
        		return false;
        	}
        }

        return true;
	}
	
	protected BaseElement createNewObject(BaseElement oldObject, FlowElementsContainer targetContainerObject) {
    	BaseElement newObject = (BaseElement)ModelUtil.createObject(resource, oldObject.eClass()); 
    	targetContainerObject.getFlowElements().add((FlowElement)newObject);
        copyFeatures(oldObject, newObject);
        return newObject;
	}
	
	protected void copyFeatures(BaseElement oldObject, BaseElement newObject) {
		EStructuralFeature f;
		f = oldObject.eClass().getEStructuralFeature("name");
		if (f!=null) {
			Object oldValue = oldObject.eGet(f);
			newObject.eSet(f, oldValue);
		}
	}
}

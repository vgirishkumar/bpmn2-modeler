/******************************************************************************* 
 * Copyright (c) 2011, 2012 Red Hat, Inc. 
 *  All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 * 
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 *
 * @author Ivar Meikas
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.core.features.data;

import org.eclipse.bpmn2.DataInput;
import org.eclipse.bpmn2.DataOutput;
import org.eclipse.bpmn2.ItemAwareElement;
import org.eclipse.bpmn2.SubProcess;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.modeler.core.di.DIImport;
import org.eclipse.bpmn2.modeler.core.features.AbstractAddBPMNShapeFeature;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.StyleUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.mm.algorithms.Polygon;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.Rectangle;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeService;

public abstract class AddDataFeature<T extends ItemAwareElement> extends AbstractAddBPMNShapeFeature<T> {

	public AddDataFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canAdd(IAddContext context) {
		Object containerBO = BusinessObjectUtil.getBusinessObjectForPictogramElement( context.getTargetContainer() );
		Object newObject = context.getNewObject();
		boolean intoDiagram = containerBO instanceof BPMNDiagram;
		boolean intoSubProcess = containerBO instanceof SubProcess;
		if (newObject instanceof DataInput || newObject instanceof DataOutput) {
			// SubProcess are not allowed to define their own DataInputs or DataOutputs
			if (intoSubProcess)
				return false;
			if (intoDiagram) {
				// check if the SubProcess is on its own BPMNDiagram
				BPMNDiagram bpmnDiagram = (BPMNDiagram) containerBO;
				if (bpmnDiagram.getPlane().getBpmnElement() instanceof SubProcess)
					return false;
			}
		}
		if (intoSubProcess || intoDiagram)
			return true;
		if (FeatureSupport.isTargetLane(context) && FeatureSupport.isTargetLaneOnTop(context))
			return true;
		if (FeatureSupport.isTargetParticipant(context))
			return true;
		return false;
	}

	@Override
	public PictogramElement add(IAddContext context) {
		IGaService gaService = Graphiti.getGaService();
		IPeService peService = Graphiti.getPeService();
		@SuppressWarnings("unchecked")
		T businessObject = getBusinessObject(context);
 
		int width = this.getWidth();
		int height = this.getHeight();
		int e = 10;
		int textArea = 15;
		
		ContainerShape containerShape = peService.createContainerShape(context.getTargetContainer(), true);
		Rectangle invisibleRect = gaService.createInvisibleRectangle(containerShape);
		gaService.setLocationAndSize(invisibleRect, context.getX(), context.getY(), width, height + textArea);

		Shape rectShape = peService.createShape(containerShape, false);
		Polygon rect = gaService.createPolygon(rectShape, new int[] { 0, 0, width - e, 0, width, e, width, height, 0,
				height });
		rect.setLineWidth(1);
		StyleUtil.applyStyle(rect,businessObject);

		int p = width - e - 1;
		Polyline edge = gaService.createPolyline(rect, new int[] { p, 0, p, e + 1, width, e + 1 });
		edge.setForeground(manageColor(StyleUtil.CLASS_FOREGROUND));
		edge.setLineWidth(1);

		if (isSupportCollectionMarkers()) {
			int whalf = width / 2;
			createCollectionShape(containerShape, new int[] { whalf - 2, height - 8, whalf - 2, height });
			createCollectionShape(containerShape, new int[] { whalf, height - 8, whalf, height });
			createCollectionShape(containerShape, new int[] { whalf + 2, height - 8, whalf + 2, height });

			String value = "false";
			EStructuralFeature feature = ((EObject)businessObject).eClass().getEStructuralFeature("isCollection");
			if (feature!=null && businessObject.eGet(feature)!=null)
				value = ((Boolean)businessObject.eGet(feature)).toString();

			Graphiti.getPeService().setPropertyValue(containerShape, Properties.COLLECTION_PROPERTY, value);
		}
		boolean isImport = context.getProperty(DIImport.IMPORT_PROPERTY) != null;
		createDIShape(containerShape, businessObject, !isImport);

		// hook for subclasses to inject extra code
		((AddContext)context).setWidth(width);
		((AddContext)context).setHeight(height);
		decorateShape(context, containerShape, businessObject);

		peService.createChopboxAnchor(containerShape);
		AnchorUtil.addFixedPointAnchors(containerShape, invisibleRect);

		layoutPictogramElement(containerShape);
		this.prepareAddContext(context, width, height);
		this.getFeatureProvider().getAddFeature(context).add(context);
		
		return containerShape;
	}

	private Shape createCollectionShape(ContainerShape container, int[] xy) {
		IPeService peService = Graphiti.getPeService();
		IGaService gaService = Graphiti.getGaService();
		Shape collectionShape = peService.createShape(container, false);
		Polyline line = gaService.createPolyline(collectionShape, xy);
		line.setForeground(manageColor(StyleUtil.CLASS_FOREGROUND));
		line.setLineWidth(1);
		line.setLineVisible(false);
		peService.setPropertyValue(collectionShape, Properties.HIDEABLE_PROPERTY, Boolean.toString(true));
		return collectionShape;
	}

	@Override
	public int getHeight() {
		return GraphicsUtil.DATA_HEIGHT;
	}

	@Override
	public int getWidth() {
		return GraphicsUtil.DATA_WIDTH;
	}

	protected boolean isSupportCollectionMarkers() {
		return true;
	}
	
	public abstract String getName(T t);
}
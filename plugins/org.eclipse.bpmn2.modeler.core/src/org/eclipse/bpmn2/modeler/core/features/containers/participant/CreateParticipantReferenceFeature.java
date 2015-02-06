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
 * @author Bob Brodt
 ******************************************************************************/

package org.eclipse.bpmn2.modeler.core.features.containers.participant;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.Participant;
import org.eclipse.bpmn2.SubProcess;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.core.features.AbstractBpmn2CreateFeature;
import org.eclipse.bpmn2.modeler.core.features.GraphitiConstants;
import org.eclipse.bpmn2.modeler.core.features.SubMenuCustomFeature;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.dd.di.DiagramElement;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.impl.CreateContext;
import org.eclipse.graphiti.features.custom.AbstractCustomFeature;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.tb.ContextMenuEntry;
import org.eclipse.graphiti.tb.IContextMenuEntry;

/**
 * @author Bob Brodt
 *
 */
public class CreateParticipantReferenceFeature extends AbstractCustomFeature {
	
	protected boolean changesDone = false;

	private static class CreateReferenceFeature extends AbstractBpmn2CreateFeature<Participant> {

		Participant pool;
		BPMNShape shape;
		
		private CreateReferenceFeature(IFeatureProvider fp, BPMNShape shape, Participant pool) {
		    this(fp);
		    this.shape = shape;
		    this.pool = pool;
	    }
		
		private CreateReferenceFeature(IFeatureProvider fp) {
		    super(fp);
	    }

		@Override
	    public boolean canCreate(ICreateContext context) {
			return true;
	    }

		@Override
		public Object[] create(ICreateContext context) {
			PictogramElement pe = addGraphicalRepresentation(context, pool);
			return new Object[] { pe };
		}

		/* (non-Javadoc)
		 * @see org.eclipse.bpmn2.modeler.core.features.AbstractBpmn2CreateFeature#getBusinessObjectClass()
		 */
		@Override
		public EClass getBusinessObjectClass() {
			return Bpmn2Package.eINSTANCE.getParticipant();
		}
		
		public Participant getPool() {
			return pool;
		}
		
		public BPMNShape getShape() {
			return shape;
		}
	}

	/**
	 * @param fp
	 */
	public CreateParticipantReferenceFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public String getDescription() {
		return Messages.CreateParticipantReferenceFeature_Create_Pool_Reference_In_Main_Diagram;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return Messages.CreateParticipantReferenceFeature_Create_Pool_Reference;
	}

	@Override
	public boolean canExecute(ICustomContext context) {
		CreateContext createContext = prepareCreateContext(context);
		if (createContext==null)
			return false;
		
		// build submenu features
		BPMNDiagram thisDiagram = getBPMNDiagram(context);
		
//		List<Participant> existingPools = new ArrayList<Participant>();
//		for (DiagramElement de : thisDiagram.getPlane().getPlaneElement()) {
//			if (de instanceof BPMNShape) {
//				BaseElement bpmnElement = ((BPMNShape)de).getBpmnElement();
//				if (bpmnElement instanceof Participant) {
//					existingPools.add((Participant)bpmnElement);
//				}
//			}
//		}

		String key = GraphitiConstants.CONTEXT_MENU_ENTRY + this.getName();
		IContextMenuEntry contextMenuEntry = (IContextMenuEntry) context.getProperty(key);
		if (contextMenuEntry!=null) {
			if (contextMenuEntry.getChildren().length == 0) {
				for (BPMNDiagram mainDiagram : DIUtils.getBPMNDiagrams(thisDiagram, 1)) {
					for (DiagramElement de : mainDiagram.getPlane().getPlaneElement()) {
						if (de instanceof BPMNShape) {
							BaseElement bpmnElement = ((BPMNShape)de).getBpmnElement();
							if (bpmnElement instanceof Participant ) { //&& !existingPools.contains(bpmnElement)) {
								Participant pool = (Participant) bpmnElement;
								ICreateFeature feature = new CreateReferenceFeature(getFeatureProvider(), (BPMNShape)de, pool);
								SubMenuCustomFeature submenuFeature = new SubMenuCustomFeature(this, feature);
								ContextMenuEntry cme = new ContextMenuEntry(submenuFeature, context);
								cme.setText(pool.getName());
								contextMenuEntry.add(cme);
							}
						}
					}
				}
			}
			return contextMenuEntry.getChildren().length>0;
		}
		return true;
	}

	@Override
	public boolean isAvailable(IContext context) {
		if (context instanceof ICustomContext) {
			return getBPMNDiagram((ICustomContext)context) != null;
		}
		return false;
	}

	private BPMNDiagram getBPMNDiagram(ICustomContext context) {
		PictogramElement pes[] = context.getPictogramElements();
		if (pes.length==1 && pes[0] instanceof Diagram) {
			BPMNDiagram bpmnDiagram = (BPMNDiagram) BusinessObjectUtil.getBusinessObjectForPictogramElement(pes[0]);
			if (bpmnDiagram.getPlane().getBpmnElement() instanceof SubProcess)
				return bpmnDiagram;
		}
		return null;
	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.graphiti.features.custom.ICustomFeature#execute(org.eclipse.graphiti.features.context.ICustomContext)
	 */
	@Override
	public void execute(ICustomContext context) {
		CreateReferenceFeature createFeature = (CreateReferenceFeature) context.getProperty(GraphitiConstants.CREATE_FEATURE);
		if (createFeature!=null) {
			CreateContext createContext = prepareCreateContext(context);
			if (createFeature.canCreate(createContext)) {
				BPMNShape shape = createFeature.getShape();
				createContext.setWidth((int)shape.getBounds().getWidth());
				createContext.setHeight((int)shape.getBounds().getHeight());

				// if user made a selection, then create the Pool reference
				ContainerShape newShape = (ContainerShape) createFeature.create(createContext)[0];
				// and select it
				getFeatureProvider().
					getDiagramTypeProvider().
					getDiagramBehavior().
					getDiagramContainer().
					setPictogramElementForSelection(newShape);
				
				changesDone = true;
			}
		}
	}

	@Override
	public boolean hasDoneChanges() {
		return changesDone;
	}

	protected static CreateContext prepareCreateContext(ICustomContext context) {
		CreateContext cc = new CreateContext();
		PictogramElement[] pes = context.getPictogramElements();
		if (pes==null || pes.length!=1)
			return null;
		EObject container = pes[0];
		if (!(container instanceof ContainerShape))
			return null;
		
		cc.setTargetContainer((ContainerShape)container);
		cc.setX(context.getX());
		cc.setY(context.getY());
		
		// set the IMPORT flag so that the new shape's location is not adjusted during creation
		cc.putProperty(GraphitiConstants.IMPORT_PROPERTY, Boolean.TRUE);
		return cc;
	}
}

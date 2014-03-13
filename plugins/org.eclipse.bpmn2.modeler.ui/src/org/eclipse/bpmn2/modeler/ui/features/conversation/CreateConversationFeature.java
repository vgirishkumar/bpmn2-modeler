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
 * @author Innar Made
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.ui.features.conversation;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.Conversation;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.modeler.core.features.AbstractBpmn2CreateFeature;
import org.eclipse.bpmn2.modeler.core.model.Bpmn2ModelerFactory;
import org.eclipse.bpmn2.modeler.core.model.ModelHandler;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.ui.ImageProvider;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.ICreateContext;

public class CreateConversationFeature extends AbstractBpmn2CreateFeature<Conversation> {

	public CreateConversationFeature(IFeatureProvider fp) {
		super(fp, Messages.CreateConversationFeature_Name, Messages.CreateConversationFeature_Description);
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		return context.getTargetContainer().equals(getDiagram());
	}

	@Override
	public Object[] create(ICreateContext context) {
		Conversation c = createBusinessObject(context);
		addGraphicalRepresentation(context, c);
		return new Object[] { c };
	}

	@Override
	public String getCreateImageId() {
		return ImageProvider.IMG_16_CONVERSATION;
	}

	@Override
	public String getCreateLargeImageId() {
		return ImageProvider.IMG_16_CONVERSATION;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.bpmn2.modeler.core.features.AbstractBpmn2CreateFeature#getBusinessObjectClass()
	 */
	@Override
	public EClass getBusinessObjectClass() {
		return Bpmn2Package.eINSTANCE.getConversation();
	}

	@Override
	public Conversation createBusinessObject(ICreateContext context) {
		ModelHandler mh = ModelHandler.getInstance(getDiagram());

		Conversation conversation = Bpmn2ModelerFactory.create(Conversation.class);
		conversation.setName(Messages.CreateConversationFeature_Name);
        BPMNDiagram bpmnDiagram = BusinessObjectUtil.getFirstElementOfType(context.getTargetContainer(), BPMNDiagram.class);
        mh.addConversationNode(bpmnDiagram,conversation);
		ModelUtil.setID(conversation);
		putBusinessObject(context, conversation);
		
		return conversation;
	}
}
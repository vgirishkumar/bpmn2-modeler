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

package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.property;

import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.ExtensionAttributeValue;
import org.eclipse.bpmn2.Relationship;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultDetailComposite;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.swt.widgets.Composite;

/**
 * This Property Tab contains the Simulation parameters for each type of 
 * BPMN2 element selected. Simulation parameters are available for the
 * following BPMN2 elements:
 * 
 *   BPMNDiagram
 *   Task
 *   CatchEvent
 *   ThrowEvent
 *   SequenceFlow
 *   
 * The structure of the extension values in the BPMN2 Relationship container
 * is TBD
 * 
 * ScenarioParameters (for BPMNDiagram elements, there may be more than one of these for each diagram):
 *   baseCurrency [text]
 *   baseTimeUnit [milliseconds, seconds, minutes, hours, days, years]
 *   
 * ElementParameters (for each of the flow elements):
 *   Tasks:
 *     costPerTimeUnit
 *     distributionType [see below]
 *   SequenceFlows:
 *     probability
 *   Catch Events:
 *     timeUnit
 *     waitTime
 *   Throw Events:
 *     distributionType [see below]
 *  
 *  Different parameters are required for each of the distribution types
 *   distributionType [normal, uniform, random, poisson]
 *     [random, uniform]:
 *       processingTime(max)
 *       processingTime(min)
 *       timeUnit
 *     [normal]:
 *       processingTime(mean)
 *       standardDeviation
 *       timeUnit
 *     [poisson]:
 *       processingTime(mean)
 *       timeUnit
 */
public class SimulationDetailComposite extends DefaultDetailComposite {

	/**
	 * @param section
	 */
	public SimulationDetailComposite(AbstractBpmn2PropertySection section) {
		super(section);
	}

	public SimulationDetailComposite(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	public void cleanBindings() {
		super.cleanBindings();
	}

	@Override
	public void createBindings(EObject be) {
		setTitle(be.eClass().getName()+" Simulation Parameters");
		if (be instanceof BPMNDiagram) {
			Relationship rel = getOrCreateRelationship(be);
		}
		createLabel(getAttributesParent(), "This is not the property tab you are looking for...move along.");
	}
	
	Relationship getOrCreateRelationship(EObject be) {
		String id = ModelUtil.getID(be);
		if (id==null || id.isEmpty())
			return null;
		
		Definitions definitions = ModelUtil.getDefinitions(be);
		Relationship rel = null;
		for (Relationship r : definitions.getRelationships()) {
			for (ExtensionAttributeValue x : ModelUtil.getExtensionAttributeValues(r)) {
//				System.out.println(x.toString());
			}
		}
		return rel;
	}
}

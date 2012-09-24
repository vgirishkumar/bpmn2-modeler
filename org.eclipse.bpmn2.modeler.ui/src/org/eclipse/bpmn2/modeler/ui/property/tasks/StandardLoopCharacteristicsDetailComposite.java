package org.eclipse.bpmn2.modeler.ui.property.tasks;

import org.eclipse.bpmn2.Expression;
import org.eclipse.bpmn2.StandardLoopCharacteristics;
import org.eclipse.bpmn2.modeler.core.adapters.InsertionAdapter;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.PropertiesCompositeFactory;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;

public class StandardLoopCharacteristicsDetailComposite extends DefaultDetailComposite {

	AbstractDetailComposite loopConditionComposite = null;
	AbstractDetailComposite loopMaximumComposite = null;
	
	public StandardLoopCharacteristicsDetailComposite(Composite parent, int style) {
		super(parent, style);
	}

	public StandardLoopCharacteristicsDetailComposite(AbstractBpmn2PropertySection section) {
		super(section);
	}

	@Override
	protected void cleanBindings() {
		super.cleanBindings();
		loopConditionComposite = null;
		loopMaximumComposite = null;
	}

	public Composite getAttributesParent() {
		return super.getAttributesParent();
	}
	
	public void createBindings(EObject be) {
		getAttributesParent();
		
		bindAttribute(be,"testBefore");
		
		if (be instanceof StandardLoopCharacteristics) {
			
			final StandardLoopCharacteristics lc = (StandardLoopCharacteristics) be;
			
			Expression loopConditionExpression = lc.getLoopCondition();
			if (loopConditionExpression==null) {
				loopConditionExpression = FACTORY.createFormalExpression();
				InsertionAdapter.add(lc, PACKAGE.getStandardLoopCharacteristics_LoopCondition(), loopConditionExpression);
			}
			loopConditionComposite = PropertiesCompositeFactory.createDetailComposite(Expression.class, getAttributesParent(), SWT.NONE);
			loopConditionComposite.setBusinessObject(loopConditionExpression);
			loopConditionComposite.setTitle("Loop Condition");
			
			Expression loopMaximumExpression = lc.getLoopMaximum();
			if (loopMaximumExpression==null) {
				loopMaximumExpression = FACTORY.createFormalExpression();
				InsertionAdapter.add(lc, PACKAGE.getStandardLoopCharacteristics_LoopMaximum(), loopMaximumExpression);
			}
			loopMaximumComposite = PropertiesCompositeFactory.createDetailComposite(Expression.class, getAttributesParent(), SWT.NONE);
			loopMaximumComposite.setBusinessObject(loopMaximumExpression);
			loopMaximumComposite.setTitle("Loop Maximum");
		}
	}
}

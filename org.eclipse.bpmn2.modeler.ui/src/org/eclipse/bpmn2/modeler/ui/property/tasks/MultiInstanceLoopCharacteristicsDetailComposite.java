package org.eclipse.bpmn2.modeler.ui.property.tasks;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.DataInput;
import org.eclipse.bpmn2.MultiInstanceLoopCharacteristics;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.FeatureEditingDialog;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.ObjectEditingDialog;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.TextAndButtonObjectEditor;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.swt.widgets.Composite;

public class MultiInstanceLoopCharacteristicsDetailComposite extends
		DefaultDetailComposite {

	public MultiInstanceLoopCharacteristicsDetailComposite(Composite parent,
			int style) {
		super(parent, style);
	}

	public MultiInstanceLoopCharacteristicsDetailComposite(
			AbstractBpmn2PropertySection section) {
		super(section);
	}

	public void createBindings(EObject be) {
		final MultiInstanceLoopCharacteristics lc = (MultiInstanceLoopCharacteristics)be;

		final EStructuralFeature inputFeature = Bpmn2Package.eINSTANCE.getMultiInstanceLoopCharacteristics_InputDataItem();
		DataIoObjectEditor inputDataItemEditor = new DataIoObjectEditor(be,inputFeature);
		inputDataItemEditor.createControl("Input Paramter");
		
		final EStructuralFeature outputFeature = Bpmn2Package.eINSTANCE.getMultiInstanceLoopCharacteristics_OutputDataItem();
		DataIoObjectEditor outputDataItemEditor = new DataIoObjectEditor(be,outputFeature);
		outputDataItemEditor.createControl("Output Paramter");
	}
	
	private class DataIoObjectEditor extends TextAndButtonObjectEditor {

		public DataIoObjectEditor(EObject object, EStructuralFeature feature) {
			super(MultiInstanceLoopCharacteristicsDetailComposite.this, object, feature);
		}

		@Override
		protected void buttonClicked(int buttonId) {
			if (buttonId==ID_DEFAULT_BUTTON) {
				FeatureEditingDialog dlg = new FeatureEditingDialog(getDiagramEditor(), object, feature);
				dlg.open();
			}
			else if (buttonId==ID_REMOVE_BUTTON) {
				final EObject value = (EObject) object.eGet(feature);
				if (value!=null) {
					TransactionalEditingDomain domain = getDiagramEditor().getEditingDomain();
					domain.getCommandStack().execute(new RecordingCommand(domain) {
						@Override
						protected void doExecute() {
							object.eUnset(feature);
							EcoreUtil.delete(value);
						}
					});
				}
			}
		}
		
		@Override
		protected boolean updateObject(final Object result) {
			return true;
		}

		@Override
		protected boolean canRemove() {
			return true;
		}
	}
}

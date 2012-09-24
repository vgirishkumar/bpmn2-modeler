package org.eclipse.bpmn2.modeler.ui.property.tasks;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.DataInput;
import org.eclipse.bpmn2.MultiInstanceBehavior;
import org.eclipse.bpmn2.MultiInstanceLoopCharacteristics;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractListComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultDetailComposite.AbstractPropertiesProvider;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.ComboObjectEditor;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.FeatureEditingDialog;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.ObjectEditingDialog;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.ObjectEditor;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.TextAndButtonObjectEditor;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class MultiInstanceLoopCharacteristicsDetailComposite extends DefaultDetailComposite {

	ObjectEditor noneBehaviorEditor;
	ObjectEditor oneBehaviorEditor;
	AbstractListComposite complexBehaviorList;
	
	public MultiInstanceLoopCharacteristicsDetailComposite(Composite parent, int style) {
		super(parent, style);
	}

	public MultiInstanceLoopCharacteristicsDetailComposite(AbstractBpmn2PropertySection section) {
		super(section);
	}
	
	@Override
	public AbstractPropertiesProvider getPropertiesProvider(EObject object) {
		if (propertiesProvider==null) {
			propertiesProvider = new AbstractPropertiesProvider(object) {
				String[] properties = new String[] {
						"anyAttribute",
						"isSequential", // attr
						"inputDataItem", // ref
						"outputDataItem", // ref
						"completionCondition", // ref
						"loopCardinality", // ref
						"loopDataInputRef", // ref
						"loopDataOutputRef", // ref
						"behavior", // attr
						"noneBehaviorEventRef", // ref
						"oneBehaviorEventRef", // ref
						"complexBehaviorDefinition", // list
				};
				
				@Override
				public String[] getProperties() {
					return properties; 
				}
			};
		}
		return propertiesProvider;
	}

	public void createBindings(EObject be) {
		super.createBindings(be);
	}
	
	protected void bindAttribute(Composite parent, EObject object, EAttribute attribute, String label) {
		if (attribute.getName().equals("behavior")) {
			ObjectEditor editor = new ComboObjectEditor(this,object,attribute) {

				@Override
				protected boolean updateObject(Object result) {
					MultiInstanceLoopCharacteristics lc = (MultiInstanceLoopCharacteristics)object;
					boolean update = super.updateObject(result);
					if (update) {
						switch (lc.getBehavior()) {
						case ALL:
							if (noneBehaviorEditor!=null) {
								noneBehaviorEditor.setVisible(false);
							}
							if (oneBehaviorEditor!=null) {
								oneBehaviorEditor.setVisible(false);
							}
							if (complexBehaviorList!=null) {
								complexBehaviorList.setVisible(false);
							}
							break;
						case NONE:
							if (noneBehaviorEditor!=null) {
								noneBehaviorEditor.setVisible(true);
							}
							if (oneBehaviorEditor!=null) {
								oneBehaviorEditor.setVisible(false);
							}
							if (complexBehaviorList!=null) {
								complexBehaviorList.setVisible(false);
							}
							break;
						case ONE:
							if (noneBehaviorEditor!=null) {
								noneBehaviorEditor.setVisible(false);
							}
							if (oneBehaviorEditor!=null) {
								oneBehaviorEditor.setVisible(true);
							}
							if (complexBehaviorList!=null) {
								complexBehaviorList.setVisible(false);
							}
							break;
						case COMPLEX:
							if (noneBehaviorEditor!=null) {
								noneBehaviorEditor.setVisible(false);
							}
							if (oneBehaviorEditor!=null) {
								oneBehaviorEditor.setVisible(false);
							}
							if (complexBehaviorList!=null) {
								complexBehaviorList.setVisible(true);
							}
							break;
						}
						redrawPage();
					}
					return update;
				}
			};
			editor.createControl(parent,label);
		}
		else
			super.bindAttribute(parent, object, attribute, label);
	}
	
	protected void bindReference(Composite parent, EObject object, EReference reference) {
		MultiInstanceLoopCharacteristics lc = (MultiInstanceLoopCharacteristics)object;
		EStructuralFeature f;

		if (reference.getName().equals("inputDataItem")) {
			f = Bpmn2Package.eINSTANCE.getMultiInstanceLoopCharacteristics_InputDataItem();
			if (isModelObjectEnabled(lc.eClass(), f)) {
				DataIoObjectEditor inputDataItemEditor = new DataIoObjectEditor(lc,f);
				inputDataItemEditor.createControl(getAttributesParent(), "Input Paramter");
			}
		}
		else if (reference.getName().equals("outputDataItem")) {
			f = Bpmn2Package.eINSTANCE.getMultiInstanceLoopCharacteristics_OutputDataItem();
			if (isModelObjectEnabled(lc.eClass(), f)) {
				DataIoObjectEditor outputDataItemEditor = new DataIoObjectEditor(lc,f);
				outputDataItemEditor.createControl(getAttributesParent(), "Output Paramter");
			}
		}
		else if (reference.getName().equals("noneBehaviorEventRef")) {
			String displayName = ModelUtil.getLabel(object, reference);
			noneBehaviorEditor = new ComboObjectEditor(this,object,reference);
			noneBehaviorEditor.createControl(parent,displayName);
			noneBehaviorEditor.setVisible( lc.getBehavior() == MultiInstanceBehavior.NONE );
				
		}		
		else if (reference.getName().equals("oneBehaviorEventRef")) {
			String displayName = ModelUtil.getLabel(object, reference);
			oneBehaviorEditor = new ComboObjectEditor(this,object,reference);
			oneBehaviorEditor.createControl(parent,displayName);
			oneBehaviorEditor.setVisible( lc.getBehavior() == MultiInstanceBehavior.ONE );
		}		
		else
			super.bindReference(parent, object, reference);
	}
	
	protected AbstractListComposite bindList(EObject object, EStructuralFeature feature, EClass listItemClass) {
		MultiInstanceLoopCharacteristics lc = (MultiInstanceLoopCharacteristics)object;
		if (feature.getName().equals("complexBehaviorDefinition")) {
			complexBehaviorList = super.bindList(object, feature, listItemClass);
			complexBehaviorList.setVisible( lc.getBehavior() == MultiInstanceBehavior.COMPLEX );
			return complexBehaviorList;
		}
		else
			return super.bindList(object, feature, listItemClass);
	}
	
	private class DataIoObjectEditor extends TextAndButtonObjectEditor {

		public DataIoObjectEditor(MultiInstanceLoopCharacteristics object, EStructuralFeature feature) {
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

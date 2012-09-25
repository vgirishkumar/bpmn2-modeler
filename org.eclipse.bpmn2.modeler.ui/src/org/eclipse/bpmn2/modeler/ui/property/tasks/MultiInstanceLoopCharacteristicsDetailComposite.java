package org.eclipse.bpmn2.modeler.ui.property.tasks;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.DataInput;
import org.eclipse.bpmn2.Expression;
import org.eclipse.bpmn2.MultiInstanceBehavior;
import org.eclipse.bpmn2.MultiInstanceLoopCharacteristics;
import org.eclipse.bpmn2.modeler.core.adapters.InsertionAdapter;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractBpmn2PropertySection;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.AbstractListComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.DefaultDetailComposite;
import org.eclipse.bpmn2.modeler.core.merrimac.clad.PropertiesCompositeFactory;
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
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;

public class MultiInstanceLoopCharacteristicsDetailComposite extends DefaultDetailComposite {

	ObjectEditor noneBehaviorEventEditor;
	ObjectEditor oneBehaviorEventEditor;
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
						"isSequential",
						"inputDataItem",
						"outputDataItem",
						"loopDataInputRef",
						"loopDataOutputRef",
						"completionCondition",
						"loopCardinality",
						"behavior",
						"noneBehaviorEventRef",
						"oneBehaviorEventRef",
						"complexBehaviorDefinition",
				};
				
				@Override
				public String[] getProperties() {
					return properties; 
				}
			};
		}
		return propertiesProvider;
	}

	@Override
	protected void cleanBindings() {
		super.cleanBindings();
		noneBehaviorEventEditor = null;
		oneBehaviorEventEditor = null;
		complexBehaviorList = null;
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
					boolean updated = super.updateObject(result);
					if (updated) {
						switch (lc.getBehavior()) {
						case ALL:
							if (noneBehaviorEventEditor!=null) {
								noneBehaviorEventEditor.setVisible(false);
							}
							if (oneBehaviorEventEditor!=null) {
								oneBehaviorEventEditor.setVisible(false);
							}
							if (complexBehaviorList!=null) {
								complexBehaviorList.setVisible(false);
							}
							break;
						case NONE:
							if (noneBehaviorEventEditor!=null) {
								noneBehaviorEventEditor.setVisible(true);
							}
							if (oneBehaviorEventEditor!=null) {
								oneBehaviorEventEditor.setVisible(false);
							}
							if (complexBehaviorList!=null) {
								complexBehaviorList.setVisible(false);
							}
							break;
						case ONE:
							if (noneBehaviorEventEditor!=null) {
								noneBehaviorEventEditor.setVisible(false);
							}
							if (oneBehaviorEventEditor!=null) {
								oneBehaviorEventEditor.setVisible(true);
							}
							if (complexBehaviorList!=null) {
								complexBehaviorList.setVisible(false);
							}
							break;
						case COMPLEX:
							if (noneBehaviorEventEditor!=null) {
								noneBehaviorEventEditor.setVisible(false);
							}
							if (oneBehaviorEventEditor!=null) {
								oneBehaviorEventEditor.setVisible(false);
							}
							if (complexBehaviorList!=null) {
								complexBehaviorList.setVisible(true);
							}
							break;
						}
						Display.getDefault().asyncExec( new Runnable() {

							@Override
							public void run() {
								redrawPage();
							}
							
						});
					}
					return updated;
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

		if (reference == PACKAGE.getMultiInstanceLoopCharacteristics_InputDataItem()) {
			f = Bpmn2Package.eINSTANCE.getMultiInstanceLoopCharacteristics_InputDataItem();
			if (isModelObjectEnabled(lc.eClass(), f)) {
				DataIoObjectEditor inputDataItemEditor = new DataIoObjectEditor(lc,f);
				inputDataItemEditor.createControl(getAttributesParent(), "Input Paramter");
			}
		}
		else if (reference == PACKAGE.getMultiInstanceLoopCharacteristics_OutputDataItem()) {
			f = Bpmn2Package.eINSTANCE.getMultiInstanceLoopCharacteristics_OutputDataItem();
			if (isModelObjectEnabled(lc.eClass(), f)) {
				DataIoObjectEditor outputDataItemEditor = new DataIoObjectEditor(lc,f);
				outputDataItemEditor.createControl(getAttributesParent(), "Output Paramter");
			}
		}
		else if (reference == PACKAGE.getMultiInstanceLoopCharacteristics_NoneBehaviorEventRef()) {
			String displayName = ModelUtil.getLabel(object, reference);
			noneBehaviorEventEditor = new ComboObjectEditor(this,object,reference);
			noneBehaviorEventEditor.createControl(parent,displayName);
			noneBehaviorEventEditor.setVisible( lc.getBehavior() == MultiInstanceBehavior.NONE );
				
		}		
		else if (reference == PACKAGE.getMultiInstanceLoopCharacteristics_OneBehaviorEventRef()) {
			String displayName = ModelUtil.getLabel(object, reference);
			oneBehaviorEventEditor = new ComboObjectEditor(this,object,reference);
			oneBehaviorEventEditor.createControl(parent,displayName);
			oneBehaviorEventEditor.setVisible( lc.getBehavior() == MultiInstanceBehavior.ONE );
		}		
		else
			super.bindReference(parent, object, reference);
	}
	
	protected AbstractListComposite bindList(EObject object, EStructuralFeature feature, EClass listItemClass) {
		MultiInstanceLoopCharacteristics lc = (MultiInstanceLoopCharacteristics)object;
		if (feature.getName().equals("complexBehaviorDefinition")) {
			complexBehaviorList = super.bindList(getAttributesParent(), object, feature, listItemClass);
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

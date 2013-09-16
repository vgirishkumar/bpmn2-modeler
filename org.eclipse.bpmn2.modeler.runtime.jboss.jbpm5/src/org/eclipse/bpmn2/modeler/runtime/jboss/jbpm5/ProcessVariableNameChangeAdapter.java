package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5;

import org.eclipse.bpmn2.modeler.core.validation.SyntaxCheckerUtils;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.GlobalType;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.validation.model.EvaluationMode;
import org.eclipse.emf.validation.service.IValidator;
import org.eclipse.emf.validation.service.ModelValidationService;

public class ProcessVariableNameChangeAdapter implements Adapter {

	@Override
	public void notifyChanged(Notification notification) {
		if (notification.getNotifier() instanceof EObject) {
			EObject object = (EObject)notification.getNotifier();
            if (notification.getEventType()==Notification.SET) {
				Object o = notification.getFeature();
				if (o instanceof EStructuralFeature) {
					EStructuralFeature feature = (EStructuralFeature)o;
                    if ("name".equals(feature.getName())) {
						Object newValue = notification.getNewValue();
						Object oldValue = notification.getOldValue();
						if (newValue!=oldValue && newValue!=null && !newValue.equals(oldValue))
						{
							EStructuralFeature id = object.eClass().getEStructuralFeature("id");
							if (id!=null) {
								newValue = SyntaxCheckerUtils.toNCName((String)newValue);
								boolean deliver = object.eDeliver();
								if (deliver)
									object.eSetDeliver(false);
								object.eSet(id, newValue);
								if (deliver)
									object.eSetDeliver(true);
								
								validate(notification);
							}
						}
					}
                    else if ("id".equals(feature.getName())) {
						Object newValue = notification.getNewValue();
						Object oldValue = notification.getOldValue();
						if (newValue!=oldValue && newValue!=null && !newValue.equals(oldValue)) 
						{
							EStructuralFeature name = object.eClass().getEStructuralFeature("name");
							if (name!=null) {
								boolean deliver = object.eDeliver();
								if (deliver)
									object.eSetDeliver(false);
								object.eSet(name, newValue);
								if (deliver)
									object.eSetDeliver(true);
								
								validate(notification);
							}
						}
					}
                    else if (object instanceof GlobalType) {
						validate(notification);
                    }
				}
            }
		}
	}

	@Override
	public Notifier getTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTarget(Notifier newTarget) {
		if (newTarget instanceof EObject) {
			EObject object = (EObject)newTarget;
			EStructuralFeature feature = object.eClass().getEStructuralFeature("name");
			if (feature!=null) {
				Object oldValue = null;
				Object newValue = object.eGet(feature);
				Notification notification = new ENotificationImpl((InternalEObject)object,
						Notification.SET, feature,
						oldValue, newValue);
				notifyChanged(notification);
			}
		}
	}

	@Override
	public boolean isAdapterForType(Object type) {
		// TODO Auto-generated method stub
		return false;
	}

	private void validate(Notification notification) {
		IValidator<Notification> validator = ModelValidationService.getInstance().newValidator(EvaluationMode.LIVE);
		validator.validate(notification);
	}
}
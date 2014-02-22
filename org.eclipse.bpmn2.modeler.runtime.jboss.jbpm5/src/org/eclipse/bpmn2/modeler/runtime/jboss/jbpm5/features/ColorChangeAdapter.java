/*******************************************************************************
 * Copyright (c) 2011, 2012, 2013, 2014 Red Hat, Inc.
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

package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.features;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.preferences.ShapeStyle;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelDecorator;
import org.eclipse.bpmn2.modeler.core.utils.StyleUtil;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.util.IColorConstant;
import org.eclipse.swt.graphics.RGB;

/**
 *
 */
public class ColorChangeAdapter extends AdapterImpl {
	
	public void adapt(ContainerShape containerShape) {
		setTarget(containerShape);
		EObject be = BusinessObjectUtil.getBusinessObjectForPictogramElement(containerShape);
		be.eAdapters().add(this);
		setTarget(containerShape);
		updateColor();
	}
	
	@Override
	public void notifyChanged(Notification msg) {
		updateColor();
	}
	
	private void updateColor() {
		ContainerShape containerShape = (ContainerShape) getTarget();
		BaseElement be = BusinessObjectUtil.getFirstBaseElement(containerShape);
		EStructuralFeature feature = ModelDecorator.getAnyAttribute(be, "bgcolor");
		if (feature!=null) {
			Object value = be.eGet(feature);
			if (value instanceof RGB) {
				RGB bgcolor = (RGB) value;
				Shape shape = (containerShape).getChildren().get(0);
				GraphicsAlgorithm ga = shape.getGraphicsAlgorithm();
				Bpmn2Preferences pref = Bpmn2Preferences.getInstance(be);
				ShapeStyle ss = new ShapeStyle( pref.getShapeStyle(be) );
				IColorConstant cc = ShapeStyle.RGBToColor(bgcolor);
				ss.setShapeBackground(cc);
				ss.setShapePrimarySelectedColor(ShapeStyle.darker(cc));
				ss.setShapeSecondarySelectedColor(ShapeStyle.lighter(cc));
				StyleUtil.applyStyle(ga, be, ss);
			}
		}
	}
}

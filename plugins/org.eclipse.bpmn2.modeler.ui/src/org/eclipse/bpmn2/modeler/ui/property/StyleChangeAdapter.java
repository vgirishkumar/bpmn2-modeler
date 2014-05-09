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

package org.eclipse.bpmn2.modeler.ui.property;

import java.util.List;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.ExtensionAttributeValue;
import org.eclipse.bpmn2.modeler.core.EDataTypeConversionFactory;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.bpmn2.modeler.core.adapters.InsertionAdapter;
import org.eclipse.bpmn2.modeler.core.features.GraphitiConstants;
import org.eclipse.bpmn2.modeler.core.model.ModelDecorator;
import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.preferences.ShapeStyle;
import org.eclipse.bpmn2.modeler.core.preferences.ShapeStyle.LabelPosition;
import org.eclipse.bpmn2.modeler.core.preferences.ShapeStyle.RoutingStyle;
import org.eclipse.bpmn2.modeler.core.runtime.ModelExtensionDescriptor;
import org.eclipse.bpmn2.modeler.core.runtime.TargetRuntime;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.core.utils.StyleUtil;
import org.eclipse.bpmn2.modeler.ui.editor.BPMN2Editor;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EDataType.Internal.ConversionDelegate;
import org.eclipse.emf.ecore.util.FeatureMap.Entry;
import org.eclipse.emf.ecore.xml.type.AnyType;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.impl.InternalTransactionalEditingDomain;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.Diagram;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.util.IColorConstant;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 *
 */
public class StyleChangeAdapter extends AdapterImpl {
	
	private BaseElement businessObject;
	private PictogramElement pictogramElement;
	private ExtendedPropertiesAdapter adapter;
	
	public static void adapt(PictogramElement pe) {
		EObject be = BusinessObjectUtil.getBusinessObjectForPictogramElement(pe);
		if (be instanceof BaseElement && hasStyle((BaseElement)be)) {
			for (Adapter a : be.eAdapters()) {
				if (a instanceof StyleChangeAdapter)
					return;
			}
			StyleChangeAdapter adapter = new StyleChangeAdapter(pe, (BaseElement)be);
			be.eAdapters().add(adapter);
			adapter.updateStyle();
		}
	}
	
	private StyleChangeAdapter(PictogramElement pe, BaseElement be) {
		this.businessObject = be;
		this.pictogramElement = pe;
		adapter = ExtendedPropertiesAdapter.adapt(be);
		for (ExtensionAttributeValue x : be.getExtensionValues()) {
			for (Entry v : x.getValue()) {
				if (v.getEStructuralFeature().getName().equals("style")) {
					EObject style = (EObject) v.getValue();
					style.eAdapters().add(this);
				}
			}
		}
	}
	
	@Override
	public void notifyChanged(Notification msg) {
		Object newValue = msg.getNewValue();
		if (msg.getEventType()==Notification.ADD && newValue instanceof ExtensionAttributeValue) {
			// an <extensionValue> was added, hook this adapter into its notification chain
			((EObject)newValue).eAdapters().add(this);
			EObject style = getStyleObject();
			if (style!=null) {
				style.eAdapters().add(this);
				updateStyle();
			}
		}
		else if (msg.getEventType()==Notification.ADD && isStyle(newValue)) {
			// a "style" element was added, hook this adapter into that notification chain 
			((EObject)newValue).eAdapters().add(this);
		}
		else if (isStyle(msg.getNotifier())) {
			updateStyle();
		}
	}

	private boolean isStyle(Object object) {
		if (object instanceof AnyType) {
			AnyType at = (AnyType)object;
			EClass ec = at.eClass();
			String name = ec.getName();
			return "ShapeStyle".equals(name);
		}
		return false;
	}
	
	private static boolean hasStyle(BaseElement businessObject) {
		ModelExtensionDescriptor med = TargetRuntime.getDefaultRuntime().getModelExtensionDescriptor(businessObject);
		if (med!=null) {
			ModelDecorator md = med.getModelDecorator();
			EStructuralFeature styleFeature = md.getEStructuralFeature(businessObject, "style");
			if (styleFeature!=null)
				return true;
		}
		return false;
	}
	
	private EObject getStyleObject() {
		ModelExtensionDescriptor med = TargetRuntime.getDefaultRuntime().getModelExtensionDescriptor(businessObject);
		ModelDecorator md = med.getModelDecorator();
		EStructuralFeature styleFeature = md.getEStructuralFeature(businessObject, "style");
		return (EObject)adapter.getFeatureDescriptor(styleFeature).getValue();
	}
	
	public static Object getStyleValue(EObject style, String feature) {
		return style.eGet(style.eClass().getEStructuralFeature(feature));
	}
	
	public static void setStyleValue(EObject style, String feature, Object value) {
		EStructuralFeature f = style.eClass().getEStructuralFeature(feature);
		Object oldValue = style.eGet(f);
		if (value!=null && !value.equals(oldValue))
			style.eSet(f, value);
	}
	
	public static void initStyle(EObject be, EObject style) {
		ModelExtensionDescriptor med = TargetRuntime.getDefaultRuntime().getModelExtensionDescriptor(be);
		ModelDecorator md = med.getModelDecorator();
		Bpmn2Preferences pref = Bpmn2Preferences.getInstance(be);
		ShapeStyle ss = pref.getShapeStyle(be);
		RGB foreground = (RGB) getStyleValue(style,"foreground");
		RGB background = (RGB) getStyleValue(style,"background");
		RGB textColor = (RGB) getStyleValue(style,"textColor");
		Font font = (Font) getStyleValue(style,"font");
		EEnumLiteral labelPosition = (EEnumLiteral) getStyleValue(style,"labelPosition");
		EEnumLiteral routing = (EEnumLiteral) getStyleValue(style,"routing");

		if (foreground==null) {
			RGB rgb = ShapeStyle.colorToRGB(ss.getShapeForeground());
			setStyleValue(style, "foreground", rgb);
		}
		if (background==null) {
			RGB rgb = ShapeStyle.colorToRGB(ss.getShapeBackground());
			setStyleValue(style, "background", rgb);
		}
		if (textColor==null) {
			RGB rgb = ShapeStyle.colorToRGB(ss.getTextColor());
			setStyleValue(style, "textColor", rgb);
		}
		if (font==null) {
			Font f = ShapeStyle.toSwtFont(ss.getTextFont());
			setStyleValue(style, "font", f);
		}
	}

	private void updateStyle() {
		EObject style = getStyleObject();
		if (style==null)
			return;
		RGB foreground = (RGB) getStyleValue(style,"foreground");
		RGB background = (RGB) getStyleValue(style,"background");
		RGB textColor = (RGB) getStyleValue(style,"textColor");
		Font font = (Font) getStyleValue(style,"font");
		EEnumLiteral labelPosition = (EEnumLiteral) getStyleValue(style,"labelPosition");
		EEnumLiteral routing = (EEnumLiteral) getStyleValue(style,"routing");
		
		GraphicsAlgorithm ga = null;
		if (pictogramElement instanceof ContainerShape && ((ContainerShape)pictogramElement).getChildren().size()>0) {
			Shape shape = ((ContainerShape)pictogramElement).getChildren().get(0);
			ga = shape.getGraphicsAlgorithm();
		}
		else
			ga = pictogramElement.getGraphicsAlgorithm();
		
		Bpmn2Preferences pref = Bpmn2Preferences.getInstance(businessObject);
		ShapeStyle ss = pref.getShapeStyle(businessObject); // this makes a copy of the value in Preference Store
		if (background!=null) {
			IColorConstant cc = ShapeStyle.RGBToColor(background);
			ss.setShapeBackground(cc);
			ss.setShapePrimarySelectedColor(ShapeStyle.darker(cc));
			ss.setShapeSecondarySelectedColor(ShapeStyle.lighter(cc));
		}
		if (foreground!=null) {
			IColorConstant cc = ShapeStyle.RGBToColor(foreground);
			ss.setShapeForeground(cc);
		}
		if (textColor!=null) {
			IColorConstant cc = ShapeStyle.RGBToColor(textColor);
			ss.setTextColor(cc);
		}
		if (font!=null) {
			Diagram diagram = BPMN2Editor.getActiveEditor().getDiagramTypeProvider().getDiagram();
			ss.setTextFont(ShapeStyle.toGraphitiFont(diagram, font));
		}
		if (labelPosition!=null) {
			for (LabelPosition p : LabelPosition.values()) {
				if (p.ordinal() == labelPosition.getValue()) {
					ss.setLabelPosition(p);
					break;
				}
			}
		}
		if (routing!=null) {
			for (RoutingStyle p : RoutingStyle.values()) {
				if (p.ordinal() == routing.getValue()) {
					ss.setRoutingStyle(p);
					break;
				}
			}
		}
		StyleUtil.applyStyle(ga, businessObject, ss);
		// also apply to Label
		Shape labelShape = FeatureSupport.getLabelShape(pictogramElement);
		if (labelShape!=null) {
			ga = labelShape.getGraphicsAlgorithm();
			Graphiti.getPeService().setPropertyValue(labelShape, GraphitiConstants.LABEL_CHANGED, "true");
			StyleUtil.applyStyle(ga, businessObject, ss);
		}
	}
}

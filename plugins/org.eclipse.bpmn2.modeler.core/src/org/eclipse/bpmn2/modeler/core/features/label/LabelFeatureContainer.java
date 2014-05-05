/*******************************************************************************
 * Copyright (c) 2011, 2012 Red Hat, Inc. 
 * All rights reserved. 
 * This program is made available under the terms of the 
 * Eclipse Public License v1.0 which accompanies this distribution, 
 * and is available at http://www.eclipse.org/legal/epl-v10.html 
 *
 * Contributors: 
 * Red Hat, Inc. - initial API and implementation 
 *******************************************************************************/
package org.eclipse.bpmn2.modeler.core.features.label;

import static org.eclipse.bpmn2.modeler.core.utils.FeatureSupport.getChildElementOfType;

import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.ConversationLink;
import org.eclipse.bpmn2.DataInput;
import org.eclipse.bpmn2.DataObject;
import org.eclipse.bpmn2.DataObjectReference;
import org.eclipse.bpmn2.DataOutput;
import org.eclipse.bpmn2.DataState;
import org.eclipse.bpmn2.DataStore;
import org.eclipse.bpmn2.DataStoreReference;
import org.eclipse.bpmn2.Event;
import org.eclipse.bpmn2.Gateway;
import org.eclipse.bpmn2.ItemAwareElement;
import org.eclipse.bpmn2.Message;
import org.eclipse.bpmn2.MessageFlow;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.di.BPMNEdge;
import org.eclipse.bpmn2.di.BPMNLabel;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.modeler.core.di.DIImport;
import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.core.features.GraphitiConstants;
import org.eclipse.bpmn2.modeler.core.features.DirectEditBaseElementFeature;
import org.eclipse.bpmn2.modeler.core.features.IConnectionFeatureContainer;
import org.eclipse.bpmn2.modeler.core.features.IShapeFeatureContainer;
import org.eclipse.bpmn2.modeler.core.features.ShowDocumentationFeature;
import org.eclipse.bpmn2.modeler.core.features.ShowPropertiesFeature;
import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.preferences.ShapeStyle;
import org.eclipse.bpmn2.modeler.core.preferences.ShapeStyle.LabelLocation;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.dd.dc.Bounds;
import org.eclipse.dd.di.DiagramElement;
import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.datatypes.ILocation;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IDeleteFeature;
import org.eclipse.graphiti.features.IDirectEditingFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.ILayoutFeature;
import org.eclipse.graphiti.features.IMoveConnectionDecoratorFeature;
import org.eclipse.graphiti.features.IMoveShapeFeature;
import org.eclipse.graphiti.features.IReconnectionFeature;
import org.eclipse.graphiti.features.IRemoveFeature;
import org.eclipse.graphiti.features.IResizeShapeFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICustomContext;
import org.eclipse.graphiti.features.context.IMoveConnectionDecoratorContext;
import org.eclipse.graphiti.features.context.IPictogramElementContext;
import org.eclipse.graphiti.features.custom.ICustomFeature;
import org.eclipse.graphiti.features.impl.DefaultMoveShapeFeature;
import org.eclipse.graphiti.mm.algorithms.AbstractText;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.ui.services.GraphitiUi;

public class LabelFeatureContainer implements IShapeFeatureContainer, IConnectionFeatureContainer {

	private static final IGaService gaService = Graphiti.getGaService();
	
	public static final String LINE_BREAK = "\n"; //$NON-NLS-1$
	public static final int SHAPE_PADDING = 6;
	public static final int TEXT_PADDING = 5;
	
	@Override
	public Object getApplyObject(IContext context) {
		if (context instanceof IAddContext) {
			if (context.getProperty(GraphitiConstants.LABEL_CONTEXT) != null
					&& (Boolean) context.getProperty(GraphitiConstants.LABEL_CONTEXT) == true) {
				IAddContext addContext = (IAddContext) context;
				return addContext.getNewObject();
			}
		}
		else if (context instanceof IPictogramElementContext) {
			IPictogramElementContext peContext = (IPictogramElementContext) context;
			PictogramElement pe = peContext.getPictogramElement();
			if (pe instanceof Shape) {
				if (FeatureSupport.isLabelShape(pe))
					return BusinessObjectUtil.getBusinessObjectForPictogramElement(pe);
				pe = ((Shape) pe).getContainer();
				if (FeatureSupport.isLabelShape(pe))
					return BusinessObjectUtil.getBusinessObjectForPictogramElement(pe);
			}
		}
		else if (context instanceof IMoveConnectionDecoratorContext) {
			IMoveConnectionDecoratorContext mcdContext = (IMoveConnectionDecoratorContext) context;
			PictogramElement pe = mcdContext.getConnectionDecorator();
			if (FeatureSupport.isLabelShape(pe))
				return BusinessObjectUtil.getBusinessObjectForPictogramElement(pe);
		}
		else if (context instanceof ICustomContext) {
			PictogramElement[] pes = ((ICustomContext) context).getPictogramElements();
			if (pes.length==1 && FeatureSupport.isLabelShape(pes[0]))
				return BusinessObjectUtil.getFirstElementOfType(pes[0], BaseElement.class);
		}
		return null;
	}

	@Override
	public boolean canApplyTo(Object o) {
		// these all have Label features
		return o instanceof Gateway ||
				o instanceof Event ||
				o instanceof Message ||
				o instanceof DataInput ||
				o instanceof DataOutput ||
				o instanceof DataObject ||
				o instanceof DataObjectReference ||
				o instanceof DataStore ||
				o instanceof DataStoreReference ||
				o instanceof SequenceFlow ||
				o instanceof MessageFlow |
				o instanceof ConversationLink;
	}
	
	@Override
	public boolean isAvailable(IFeatureProvider fp) {
		return true;
	}

	@Override
	public ICreateFeature getCreateFeature(IFeatureProvider fp) {
		return null;
	}
	
	@Override
	public ICreateConnectionFeature getCreateConnectionFeature(IFeatureProvider fp) {
		return null;
	}

	@Override
	public IAddFeature getAddFeature(IFeatureProvider fp) {
		return new AddLabelFeature(fp);
	}

	@Override
	public IUpdateFeature getUpdateFeature(IFeatureProvider fp) {
		return new UpdateLabelFeature(fp);
	}

	@Override
	public IDirectEditingFeature getDirectEditingFeature(IFeatureProvider fp) {
		return new DirectEditBaseElementFeature(fp);
	}

	@Override
	public ILayoutFeature getLayoutFeature(IFeatureProvider fp) {
		return new LayoutLabelFeature(fp);
	}

	@Override
	public IRemoveFeature getRemoveFeature(IFeatureProvider fp) {
		return new RemoveLabelFeature(fp);
	}

	@Override
	public IMoveShapeFeature getMoveFeature(IFeatureProvider fp) {
		return new MoveShapeLabelFeature(fp);
	}

	@Override
	public IMoveConnectionDecoratorFeature getMoveConnectionDecoratorFeature(IFeatureProvider fp) {
		return new MoveConnectionLabelFeature(fp);
	}

	@Override
	public IResizeShapeFeature getResizeFeature(IFeatureProvider fp) {
		return null;
	}

	@Override
	public IDeleteFeature getDeleteFeature(IFeatureProvider fp) {
		return null;
	}
	
	@Override
	public ICustomFeature[] getCustomFeatures(IFeatureProvider fp) {
		return new ICustomFeature[] {
				new ShowDocumentationFeature(fp),
				new ShowPropertiesFeature(fp)
			};
	}

	@Override
	public IReconnectionFeature getReconnectionFeature(IFeatureProvider fp) {
		// TODO Auto-generated method stub
		return null;
	}

	public static String getLabelString(BaseElement element) {
		// Unfortunately this needs to be aware of ItemAwareElements, which have a
		// Data State (the Data State needs to appear below the element's label in [])
		// The UpdateLabelFeature is checked in BPMN2FeatureProvider AFTER the Update
		// Feature for Data Objects is executed - this wipes out the Label provided by
		// ItemAwareElementUpdateFeature.
		String label = ModelUtil.getName(element);
		if (element instanceof ItemAwareElement) {
			DataState state = ((ItemAwareElement)element).getDataState();
			if (state!=null && state.getName()!=null) {
				return label + "\n[" + state.getName() + "]"; //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		return label;
	}

	// TODO: Think about a maximum-width...
	public static int getLabelWidth(AbstractText text) {
		if (text.getValue() != null && !text.getValue().isEmpty()) {
			String[] strings = text.getValue().split(LINE_BREAK);
			int result = 0;
			for (String string : strings) {
				IDimension dim = GraphitiUi.getUiLayoutService().calculateTextSize(string, text.getFont());
				if (dim.getWidth() > result) {
					result = dim.getWidth();
				}
			}
			return result;
		}
		return 0;
	}

	// TODO: Think about line break in the ui...
	public static int getLabelHeight(AbstractText text) {
		if (text.getValue() != null && !text.getValue().isEmpty()) {
			int height = 14;
			String[] strings = text.getValue().split(LINE_BREAK);
			if (strings.length>0) {
				IDimension dim = GraphitiUi.getUiLayoutService().calculateTextSize(strings[0], text.getFont());
				height = dim.getHeight();
			}
			return strings.length * height;
		}
		return 0;
	}

	public static PictogramElement getLabelOwner(IContext context) {
		Object o = context.getProperty(GraphitiConstants.LABEL_OWNER);
		if (o instanceof PictogramElement)
			return (PictogramElement) o;
		return null;
	}

	public static PictogramElement getLabelOwner(PictogramElement pe) {
		DiagramElement de = BusinessObjectUtil.getFirstElementOfType(pe, DiagramElement.class);
		if (de!=null)
			return pe;
		ContainerShape cs = BusinessObjectUtil.getFirstElementOfType(pe, ContainerShape.class);
		de = BusinessObjectUtil.getFirstElementOfType(cs, DiagramElement.class);
		if (de!=null)
			return cs;
		return null;
	}

	public static Shape getLabelShape(PictogramElement pe) {
		pe = getLabelOwner(pe);
		if (pe instanceof Connection) {
			for (ConnectionDecorator d : ((Connection)pe).getConnectionDecorators()) {
				String value = Graphiti.getPeService().getPropertyValue(d, GraphitiConstants.TEXT_ELEMENT);
				if (value!=null)
					return d;
			}
		}
		ContainerShape cs = BusinessObjectUtil.getFirstElementOfType(pe, ContainerShape.class);
		if (cs!=null) {
			return getChildElementOfType(cs, GraphitiConstants.TEXT_ELEMENT, Boolean.toString(true), Shape.class);
		}
		return null;
	}
	
	public static String getLabelLocationAsString(PictogramElement pe, boolean isImporting) {
		ILocation loc = getLabelLocation(pe, isImporting, null);
		if (loc!=null) {
			return loc.getX() + "," + loc.getY();
		}
		return "";
	}
	
	public static ILocation getLabelLocation(PictogramElement pe, boolean isImporting, Point offset) {

		PictogramElement ownerPE = getLabelOwner(pe);
		Shape labelShape = getLabelShape(pe);
		if (labelShape != null) {
			AbstractText textGA = (AbstractText) labelShape.getGraphicsAlgorithm();
			BaseElement element = (BaseElement) BusinessObjectUtil.getFirstElementOfType(ownerPE, BaseElement.class);
			String text = LabelFeatureContainer.getLabelString(element);
			if (text == null) {
				text = ""; //$NON-NLS-1$
			}

			GraphicsAlgorithm ownerGA = ownerPE.getGraphicsAlgorithm();
			IDimension ownerSize = GraphicsUtil.calculateSize(ownerPE);
			ILocation loc = Graphiti.getPeService().getLocationRelativeToDiagram(labelShape);
			int x = 0;
			int y = 0;
			int w = LabelFeatureContainer.getLabelWidth(textGA);
			int h = LabelFeatureContainer.getLabelHeight(textGA);
			Bpmn2Preferences preferences = Bpmn2Preferences.getInstance(element);		
			ShapeStyle ss = preferences.getShapeStyle(element);
			LabelLocation labelLocation = ss.getLabelLocation();
			
			if (isImporting) {
				BPMNLabel bpmnLabel = null;
				if (ownerPE instanceof Connection) {
					BPMNEdge bpmnEdge = DIUtils.findBPMNEdge(element);
					bpmnLabel = bpmnEdge.getLabel();
				}
				else {
					BPMNShape bpmnShape = DIUtils.findBPMNShape(element);
					bpmnLabel = bpmnShape.getLabel();
				}
				Bounds bounds = bpmnLabel==null ? null : bpmnLabel.getBounds();
				
				if (bounds==null) {
					/*
					 * The edge or shape does not have a BPMNLabel so treat the
					 * label normally, that is adjust its location according to
					 * the User Preferences. In this case force the relative
					 * location of the label to be below the shape or connection
					 * in case User Preferences allow labels to be moved manually.
					 */ 
					isImporting = false;
					if (labelLocation==LabelLocation.MOVABLE)
						labelLocation = LabelLocation.BOTTOM;
				}
				else {
					if (ownerPE instanceof Connection) {
						ILocation m = Graphiti.getPeLayoutService().getConnectionMidpoint((Connection)ownerPE, 0.5);
						x = (int)bounds.getX() - m.getX();
						y = (int)bounds.getY() - m.getY();
					}
					else {
						x = (int)bounds.getX();
						y = (int)bounds.getY();
					}
				}
			}

			if (!isImporting && !text.isEmpty()) {
				if (element instanceof Activity)
					labelLocation = LabelLocation.CENTER;
				
				switch (labelLocation) {
				case TOP:
					x = ownerGA.getX() + (ownerSize.getWidth() - w - LabelFeatureContainer.SHAPE_PADDING)/2;
					y = ownerGA.getY() - (h + LabelFeatureContainer.SHAPE_PADDING);
					break;
				case BOTTOM:
					x = ownerGA.getX() + (ownerSize.getWidth() - w - LabelFeatureContainer.SHAPE_PADDING)/2;
					y = ownerGA.getY() + ownerSize.getHeight();
					break;
				case LEFT:
					x = ownerGA.getX() - w - LabelFeatureContainer.SHAPE_PADDING;
					y = ownerGA.getY() + (ownerSize.getHeight() - h - LabelFeatureContainer.SHAPE_PADDING)/2;
					break;
				case RIGHT:
					x = ownerGA.getX() + ownerSize.getWidth() + LabelFeatureContainer.SHAPE_PADDING - LabelFeatureContainer.SHAPE_PADDING;
					y = ownerGA.getY() + (ownerSize.getHeight() - h - LabelFeatureContainer.SHAPE_PADDING)/2;
					break;
				case CENTER:
					x = ownerGA.getX() + (ownerSize.getWidth() - w - LabelFeatureContainer.SHAPE_PADDING)/2;
					y = ownerGA.getY() + (ownerSize.getHeight() - h - LabelFeatureContainer.SHAPE_PADDING)/2;
					break;
				case MOVABLE:
					if (pe instanceof Shape) {
						x = (int)loc.getX();
						y = (int)loc.getY();
					}
					else if (pe instanceof Connection) {
						ILocation m = Graphiti.getPeLayoutService().getConnectionMidpoint((Connection)pe, 0.5);
						x = (int)loc.getX() - m.getX();
						y = (int)loc.getY() - m.getY();
					}
					if (offset!=null) {
						x += offset.getX();
						y += offset.getY();
					}
					break;
				}
			}
			
			loc.setX(x);
			loc.setY(y);
			
			return loc;
		}
		
		return null;
	}

	public static void adjustLabelLocation(PictogramElement pe, boolean isImporting, Point offset) {

		PictogramElement ownerPE = getLabelOwner(pe);
		Shape labelShape = getLabelShape(pe);
		if (labelShape != null) {
			AbstractText textGA = (AbstractText) labelShape.getGraphicsAlgorithm();
			BaseElement element = (BaseElement) BusinessObjectUtil.getFirstElementOfType(pe, BaseElement.class);
			String text = LabelFeatureContainer.getLabelString(element);
			if (text == null) {
				text = ""; //$NON-NLS-1$
			}
			if (!text.equals(textGA.getValue()))
				textGA.setValue(text);

			Shape textContainerShape = (labelShape instanceof ConnectionDecorator) ? labelShape
					: (ContainerShape) labelShape.eContainer();
			GraphicsAlgorithm textContainerGA = textContainerShape.getGraphicsAlgorithm();

			ILocation loc = getLabelLocation(ownerPE, isImporting, offset);
			int x = loc.getX();
			int y = loc.getY();
			int w = LabelFeatureContainer.getLabelWidth(textGA) + LabelFeatureContainer.SHAPE_PADDING;
			int h = LabelFeatureContainer.getLabelHeight(textGA) + LabelFeatureContainer.SHAPE_PADDING;
			if (w>100) {
				h = h * (w/100) * 2;
				w = 100;
			}
			
			// move and resize the label container and set the new size of the Text GA
			gaService.setLocationAndSize(textContainerGA, x, y, w, h);
			gaService.setSize(textGA, w, h);

			// if the label is owned by a connection, its location will always be relative
			// to the connection midpoint so we have to get the absolute location for the
			// BPMNLabel coordinates.
			loc = Graphiti.getPeService().getLocationRelativeToDiagram(labelShape);
			DIUtils.updateDILabel(ownerPE, loc.getX(), loc.getY(), w, h);
			textContainerShape.setVisible( !text.isEmpty() );
			
			// save the new coordinates in the Text shape property so the connection's
			// Update Feature can determine if the label should be moved.
			String locAsString = LabelFeatureContainer.getLabelLocationAsString(labelShape, isImporting);
			Graphiti.getPeService().setPropertyValue(labelShape, GraphitiConstants.LABEL_LOCATION, locAsString);
		}
	}
}

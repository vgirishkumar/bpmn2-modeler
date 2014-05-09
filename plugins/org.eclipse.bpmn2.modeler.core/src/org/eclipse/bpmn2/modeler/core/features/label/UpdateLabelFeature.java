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
 * @author Ivar Meikas
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.core.features.label;

import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.DataState;
import org.eclipse.bpmn2.ItemAwareElement;
import org.eclipse.bpmn2.di.BPMNEdge;
import org.eclipse.bpmn2.di.BPMNLabel;
import org.eclipse.bpmn2.di.BPMNShape;
import org.eclipse.bpmn2.modeler.core.di.DIImport;
import org.eclipse.bpmn2.modeler.core.di.DIUtils;
import org.eclipse.bpmn2.modeler.core.features.AbstractBpmn2UpdateFeature;
import org.eclipse.bpmn2.modeler.core.features.GraphitiConstants;
import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.preferences.ShapeStyle;
import org.eclipse.bpmn2.modeler.core.preferences.ShapeStyle.LabelPosition;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.FeatureSupport;
import org.eclipse.bpmn2.modeler.core.utils.GraphicsUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.dd.dc.Bounds;
import org.eclipse.graphiti.datatypes.IDimension;
import org.eclipse.graphiti.datatypes.ILocation;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.internal.datatypes.impl.LocationImpl;
import org.eclipse.graphiti.mm.algorithms.AbstractText;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.services.GraphitiUi;

public class UpdateLabelFeature extends AbstractBpmn2UpdateFeature {

	public UpdateLabelFeature(IFeatureProvider fp) {
		super(fp);
	}

	@Override
	public boolean canUpdate(IUpdateContext context) {
		BaseElement element = (BaseElement) BusinessObjectUtil.getFirstElementOfType(context.getPictogramElement(),
				BaseElement.class);
		if (element == null) {
			return false;
		}
		return hasLabel(element);
	}

	@Override
	public IReason updateNeeded(IUpdateContext context) {
		IReason reason = super.updateNeeded(context);
		if (reason.toBoolean())
			return reason;

		PictogramElement ownerPE = context.getPictogramElement();

		BaseElement element = (BaseElement) BusinessObjectUtil.getFirstElementOfType(ownerPE, BaseElement.class);

		Shape labelShape = FeatureSupport.getLabelShape(ownerPE);
		if (labelShape != null) {

			if (Graphiti.getPeService().getPropertyValue(labelShape, GraphitiConstants.LABEL_CHANGED) != null) {
//				return Reason.createTrueReason(Messages.UpdateLabelFeature_LabelChanged);
			}

			String newLabel = getLabelString(element);
			if (newLabel == null || newLabel.isEmpty())
				newLabel = ""; //$NON-NLS-1$
			AbstractText text = (AbstractText) labelShape.getGraphicsAlgorithm();
			String oldLabel = text.getValue();
			if (oldLabel == null || oldLabel.isEmpty())
				oldLabel = ""; //$NON-NLS-1$

			if (!newLabel.equals(oldLabel))
				return Reason.createTrueReason(Messages.UpdateLabelFeature_TextChanged);
			
		}
		return Reason.createFalseReason();
	}

	@Override
	public boolean update(IUpdateContext context) {
		PictogramElement pe = FeatureSupport.getLabelOwner(context);
		Point offset = (Point) context.getProperty(GraphitiConstants.LABEL_OFFSET);
		adjustLabelLocation(pe, isAddingLabel(context), offset);
		return true;
	}

	protected boolean isAddingLabel(IContext context) {
		return context.getProperty(GraphitiConstants.PICTOGRAM_ELEMENTS) != null
				|| context.getProperty(GraphitiConstants.IMPORT_PROPERTY) != null;
	}

	protected boolean hasLabel(BaseElement element) {
		return ModelUtil.hasName(element);
	}

	protected String getLabelString(BaseElement element) {
		/*
		 * Unfortunately this needs to be aware of ItemAwareElements, which have
		 * a Data State (the Data State needs to appear below the element's
		 * label in []) The UpdateLabelFeature is checked in
		 * BPMN2FeatureProvider AFTER the Update Feature for Data Objects is
		 * executed - this wipes out the Label provided by
		 * ItemAwareElementUpdateFeature.
		 */
		String label = ModelUtil.getName(element);
		if (element instanceof ItemAwareElement) {
			DataState state = ((ItemAwareElement) element).getDataState();
			if (state != null && state.getName() != null) {
				return label + "\n[" + state.getName() + "]"; //$NON-NLS-1$ //$NON-NLS-2$
			}
		}
		return label;
	}

	protected ILocation getLabelLocation(PictogramElement pe, boolean isAddingLabel, Point offset) {

		PictogramElement ownerPE = FeatureSupport.getLabelOwner(pe);
		Shape labelShape = FeatureSupport.getLabelShape(pe);
		if (labelShape != null) {
			AbstractText textGA = (AbstractText) labelShape.getGraphicsAlgorithm();
			BaseElement element = (BaseElement) BusinessObjectUtil.getFirstElementOfType(ownerPE, BaseElement.class);
			String text = getLabelString(element);
			if (text == null) {
				text = ""; //$NON-NLS-1$
			}

			// Get the absolute location of the owner. If the owner is a
			// Connection use the Connection midpoint.
			ILocation ownerLoc = ownerPE instanceof Connection ? Graphiti.getPeLayoutService().getConnectionMidpoint(
					(Connection) ownerPE, 0.5) : Graphiti.getPeService().getLocationRelativeToDiagram((Shape) ownerPE);
			IDimension ownerSize = GraphicsUtil.calculateSize(ownerPE);
			ILocation labelLoc = Graphiti.getPeService().getLocationRelativeToDiagram(labelShape);
			int x = 0;
			int y = 0;
			int w = getLabelWidth(textGA);
			int h = getLabelHeight(textGA);
			LabelPosition pos = getLabelPosition(element);

			if (isAddingLabel) {
				BPMNLabel bpmnLabel = null;
				if (ownerPE instanceof Connection) {
					BPMNEdge bpmnEdge = DIUtils.findBPMNEdge(element);
					bpmnLabel = bpmnEdge.getLabel();
				} else {
					BPMNShape bpmnShape = DIUtils.findBPMNShape(element);
					bpmnLabel = bpmnShape.getLabel();
				}
				Bounds bounds = bpmnLabel == null ? null : bpmnLabel.getBounds();

				if (bounds == null) {
					/*
					 * The edge or shape does not have a BPMNLabel so treat the
					 * label normally, that is adjust its location according to
					 * the User Preferences. In this case force the relative
					 * location of the label to be below the shape or connection
					 * in case User Preferences allow labels to be moved
					 * manually.
					 */
					isAddingLabel = false;
					if (pos == LabelPosition.MOVABLE)
						pos = LabelPosition.BELOW;
				} else {
					if (ownerPE instanceof Connection) {
						x = (int) bounds.getX() - ownerLoc.getX();
						y = (int) bounds.getY() - ownerLoc.getY();
					} else {
						x = (int) bounds.getX();
						y = (int) bounds.getY();
					}
				}
			}

			if (!isAddingLabel && !text.isEmpty()) {
				switch (pos) {
				case ABOVE:
					x = ownerLoc.getX() + (ownerSize.getWidth() - w - LabelFeatureContainer.SHAPE_PADDING) / 2;
					y = ownerLoc.getY() - (h + LabelFeatureContainer.SHAPE_PADDING);
					break;
				case BELOW:
					x = ownerLoc.getX() + (ownerSize.getWidth() - w - LabelFeatureContainer.SHAPE_PADDING) / 2;
					y = ownerLoc.getY() + ownerSize.getHeight();
					break;
				case LEFT:
					x = ownerLoc.getX() - w - LabelFeatureContainer.SHAPE_PADDING;
					y = ownerLoc.getY() + (ownerSize.getHeight() - h - LabelFeatureContainer.SHAPE_PADDING) / 2;
					break;
				case RIGHT:
					x = ownerLoc.getX() + ownerSize.getWidth() + LabelFeatureContainer.SHAPE_PADDING
							- LabelFeatureContainer.SHAPE_PADDING;
					y = ownerLoc.getY() + (ownerSize.getHeight() - h - LabelFeatureContainer.SHAPE_PADDING) / 2;
					break;
				case TOP:
					x = ownerLoc.getX() + (ownerSize.getWidth() - w - LabelFeatureContainer.SHAPE_PADDING) / 2;
					y = ownerLoc.getY() + LabelFeatureContainer.SHAPE_PADDING / 2;
					break;
				case CENTER:
					x = ownerLoc.getX() + (ownerSize.getWidth() - w - LabelFeatureContainer.SHAPE_PADDING) / 2;
					y = ownerLoc.getY() + (ownerSize.getHeight() - h - LabelFeatureContainer.SHAPE_PADDING) / 2;
					break;
				case BOTTOM:
					x = ownerLoc.getX() + (ownerSize.getWidth() - w - LabelFeatureContainer.SHAPE_PADDING) / 2;
					y = ownerLoc.getY() + ownerSize.getHeight() - h - LabelFeatureContainer.SHAPE_PADDING / 2;
					break;
				case MOVABLE:
					if (ownerPE instanceof Connection) {
						x = (int) labelLoc.getX() - ownerLoc.getX();
						y = (int) labelLoc.getY() - ownerLoc.getY();
					} else {
						x = (int) labelLoc.getX();
						y = (int) labelLoc.getY();
					}
					if (offset != null) {
						x += offset.getX();
						y += offset.getY();
					}
					break;
				}
			}
			if (textGA.getAngle() == -90) {
				// FIXME: translate x/y according to angle
				int temp = x;
				x = y;
				y = temp;
			}

			labelLoc.setX(x);
			labelLoc.setY(y);
			return labelLoc;
		}

		return null;
	}

	protected void adjustLabelLocation(PictogramElement pe, boolean isAddingLabel, Point offset) {

		PictogramElement ownerPE = FeatureSupport.getLabelOwner(pe);
		Shape labelShape = FeatureSupport.getLabelShape(pe);
		if (labelShape != null) {
			AbstractText textGA = (AbstractText) labelShape.getGraphicsAlgorithm();
			BaseElement element = (BaseElement) BusinessObjectUtil.getFirstElementOfType(pe, BaseElement.class);
			String text = getLabelString(element);
			if (text == null) {
				text = ""; //$NON-NLS-1$
			}
			if (!text.equals(textGA.getValue()))
				textGA.setValue(text);

			GraphicsAlgorithm labelGA = labelShape.getGraphicsAlgorithm();

			ILocation loc = getLabelLocation(labelShape, isAddingLabel, offset);
			int x = loc.getX();
			int y = loc.getY();
			int w = getLabelWidth(textGA);
			int h = getLabelHeight(textGA);

			// move and resize the label container and set the new size of the
			// Text GA
			Graphiti.getGaService().setLocationAndSize(labelGA, x, y, w, h);
			Graphiti.getGaService().setSize(textGA, w, h);
			if (ownerPE instanceof Shape)
				Graphiti.getPeService().sendToFront(labelShape);

			// if the label is owned by a connection, its location will always
			// be relative
			// to the connection midpoint so we have to get the absolute
			// location for the
			// BPMNLabel coordinates.
			ILocation absloc = Graphiti.getPeService().getLocationRelativeToDiagram(labelShape);
			DIUtils.updateDILabel(ownerPE, absloc.getX(), absloc.getY(), w, h);
			labelShape.setVisible(!text.isEmpty());
		}
	}

	protected int getLabelWidth(AbstractText text) {
		if (text.getValue() != null && !text.getValue().isEmpty()) {
			String[] strings = text.getValue().split(LabelFeatureContainer.LINE_BREAK);
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

	protected int getLabelHeight(AbstractText text) {
		if (text.getValue() != null && !text.getValue().isEmpty()) {
			int height = 14;
			String[] strings = text.getValue().split(LabelFeatureContainer.LINE_BREAK);
			if (strings.length > 0) {
				IDimension dim = GraphitiUi.getUiLayoutService().calculateTextSize(strings[0], text.getFont());
				height = dim.getHeight();
			}
			return strings.length * height;
		}
		return 0;
	}

	/**
	 * Get the position of the label relative to its owning figure for the given
	 * BaseElement as defined in the User Preferences.
	 * 
	 * Overrides will provide their own relative positions for, e.g. Tasks and
	 * TextAnnotations.
	 * 
	 * @param element the BaseElement that is represented by the graphical
	 *            figure.
	 * @return a ShapeStyle LabelPosition relative location indicator.
	 */
	protected LabelPosition getLabelPosition(BaseElement element) {
		Bpmn2Preferences preferences = Bpmn2Preferences.getInstance(element);
		ShapeStyle ss = preferences.getShapeStyle(element);
		return ss.getLabelPosition();
	}
}
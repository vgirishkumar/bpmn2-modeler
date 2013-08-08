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
 * @author Innar Made
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.ui.features.flow;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.Activity;
import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.CatchEvent;
import org.eclipse.bpmn2.DataAssociation;
import org.eclipse.bpmn2.DataInput;
import org.eclipse.bpmn2.DataInputAssociation;
import org.eclipse.bpmn2.InputOutputSpecification;
import org.eclipse.bpmn2.InputSet;
import org.eclipse.bpmn2.ItemAwareElement;
import org.eclipse.bpmn2.ThrowEvent;
import org.eclipse.bpmn2.modeler.core.features.BaseElementConnectionFeatureContainer;
import org.eclipse.bpmn2.modeler.core.features.flow.AbstractAddFlowFeature;
import org.eclipse.bpmn2.modeler.core.features.flow.AbstractCreateFlowFeature;
import org.eclipse.bpmn2.modeler.core.features.flow.AbstractReconnectFlowFeature;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.bpmn2.modeler.core.utils.StyleUtil;
import org.eclipse.bpmn2.modeler.ui.ImageProvider;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.graphiti.features.IAddFeature;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.IReason;
import org.eclipse.graphiti.features.IReconnectionFeature;
import org.eclipse.graphiti.features.IUpdateFeature;
import org.eclipse.graphiti.features.context.IAddConnectionContext;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.IReconnectionContext;
import org.eclipse.graphiti.features.context.IUpdateContext;
import org.eclipse.graphiti.features.context.impl.AddConnectionContext;
import org.eclipse.graphiti.features.context.impl.CreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.ReconnectionContext;
import org.eclipse.graphiti.features.impl.AbstractUpdateFeature;
import org.eclipse.graphiti.features.impl.Reason;
import org.eclipse.graphiti.mm.algorithms.Polyline;
import org.eclipse.graphiti.mm.algorithms.styles.LineStyle;
import org.eclipse.graphiti.mm.pictograms.Anchor;
import org.eclipse.graphiti.mm.pictograms.AnchorContainer;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ConnectionDecorator;
import org.eclipse.graphiti.mm.pictograms.FreeFormConnection;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.services.IGaService;
import org.eclipse.graphiti.services.IPeService;
import org.eclipse.graphiti.ui.internal.util.ui.PopupMenu;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

public class DataAssociationFeatureContainer extends BaseElementConnectionFeatureContainer {

	// the property used to store the current Association's direction;
	// the value can be one of the AssociationDirection enumerations (a null
	// or empty string is the same as "None")
	public static final String ASSOCIATION_DIRECTION = "association.direction";
	public static final String ARROWHEAD_DECORATOR = "arrowhead.decorator";
	
	protected CreateConnectionContext createContext;
	
	@Override
	public boolean canApplyTo(Object o) {
		return super.canApplyTo(o) && o instanceof DataAssociation;
	}

	@Override
	public ICreateConnectionFeature getCreateConnectionFeature(IFeatureProvider fp) {
		return new CreateDataAssociationFeature(fp);
	}

	@Override
	public IAddFeature getAddFeature(IFeatureProvider fp) {
		return new AddDataAssociationFeature(fp);
	}

	@Override
	public IUpdateFeature getUpdateFeature(IFeatureProvider fp) {
		return new UpdateDataAssociationFeature(fp);
	}
	
	@Override
	public IReconnectionFeature getReconnectionFeature(IFeatureProvider fp) {
		// TODO Auto-generated method stub
		return new ReconnectDataAssociationFeature(fp);
	}

	private boolean canConnect(BaseElement source, BaseElement target) {
		// Connection rules:
		// either the source or target must be an ItemAwareElement,
		// and the other must be either an Activity or a Catch or Throw Event
		// depending on whether it's the target or the source.
		if ((source instanceof Activity || source instanceof CatchEvent) && target instanceof ItemAwareElement) {
			return true;
		}
		if ((target instanceof Activity || target instanceof ThrowEvent) && source instanceof ItemAwareElement) {
			return true;
		}
		return false;
	}

	
	private static ILabelProvider labelProvider = new ILabelProvider() {

		public void removeListener(ILabelProviderListener listener) {
		}

		public boolean isLabelProperty(Object element, String property) {
			return false;
		}

		public void dispose() {
		}

		public void addListener(ILabelProviderListener listener) {
		}

		public String getText(Object object) {
			ItemAwareElement element = (ItemAwareElement)object;
			EStructuralFeature f = element.eClass().getEStructuralFeature("name");
			if (f!=null) {
				String name = (String) element.eGet(f);
				if (name!=null && !name.isEmpty())
					return name;
			}
			return element.getId();
		}

		public Image getImage(Object element) {
			return null;
		}

	};

	public class CreateDataAssociationFeature extends AbstractCreateFlowFeature<DataAssociation, BaseElement, BaseElement> {

		public CreateDataAssociationFeature(IFeatureProvider fp) {
			super(fp, "Data Association", "Create "+"Data Association");
		}

		@Override
		public boolean isAvailable(IContext context) {
			return true;
		}

		@Override
		public boolean canCreate(ICreateConnectionContext context) {
			if (super.canCreate(context)) {
				BaseElement source = getSourceBo(context);
				BaseElement target = getTargetBo(context);
				return canConnect(source, target);
			}
			return false;
		}

		@Override
		public Connection create(ICreateConnectionContext context) {
			// save the CreateContext because we'll need it in AddFeature
			createContext = (CreateConnectionContext)context;
			Anchor sourceAnchor = createContext.getSourceAnchor();
			Anchor targetAnchor = createContext.getTargetAnchor();
			PictogramElement source = createContext.getSourcePictogramElement();
			PictogramElement target = createContext.getTargetPictogramElement();
			
			if (sourceAnchor==null && source instanceof FreeFormConnection) {
				Shape connectionPointShape = AnchorUtil.createConnectionPoint(getFeatureProvider(),
						(FreeFormConnection)source,
						Graphiti.getPeLayoutService().getConnectionMidpoint((FreeFormConnection)source, 0.5));
				sourceAnchor = AnchorUtil.getConnectionPointAnchor(connectionPointShape);
				createContext.setSourceAnchor(sourceAnchor);
			}
			if (targetAnchor==null && target instanceof FreeFormConnection) {
				Shape connectionPointShape = AnchorUtil.createConnectionPoint(getFeatureProvider(),
						(FreeFormConnection)target,
						Graphiti.getPeLayoutService().getConnectionMidpoint((FreeFormConnection)target, 0.5));
				targetAnchor = AnchorUtil.getConnectionPointAnchor(connectionPointShape);
				createContext.setTargetAnchor(targetAnchor);
			}

			return super.create(context);
		}

		@Override
		protected String getStencilImageId() {
			return ImageProvider.IMG_16_DATA_ASSOCIATION;
		}

		@Override
		protected Class<BaseElement> getSourceClass() {
			return BaseElement.class;
		}

		@Override
		protected Class<BaseElement> getTargetClass() {
			return BaseElement.class;
		}

		@Override
		protected BaseElement getSourceBo(ICreateConnectionContext context) {
			Anchor anchor = context.getSourceAnchor();
			if (anchor != null && anchor.getParent() instanceof Shape) {
				Shape shape = (Shape) anchor.getParent();
				Connection conn = AnchorUtil.getConnectionPointOwner(shape);
				if (conn!=null) {
					return BusinessObjectUtil.getFirstElementOfType(conn, getTargetClass());
				}
				return BusinessObjectUtil.getFirstElementOfType(shape, getTargetClass());
			}
			return null;
		}

		@Override
		protected BaseElement getTargetBo(ICreateConnectionContext context) {
			Anchor anchor = context.getTargetAnchor();
			if (anchor != null && anchor.getParent() instanceof Shape) {
				Shape shape = (Shape) anchor.getParent();
				Connection conn = AnchorUtil.getConnectionPointOwner(shape);
				if (conn!=null) {
					return BusinessObjectUtil.getFirstElementOfType(conn, getTargetClass());
				}
				return BusinessObjectUtil.getFirstElementOfType(shape, getTargetClass());
			}
			return null;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.bpmn2.modeler.core.features.AbstractBpmn2CreateConnectionFeature#getBusinessObjectClass()
		 */
		@Override
		public EClass getBusinessObjectClass() {
			return Bpmn2Package.eINSTANCE.getDataAssociation();
		}

		@Override
		public DataAssociation createBusinessObject(ICreateConnectionContext context) {
			// Instead of creating a new object, we will try to discover a DataAssociation
			// (input or output) already defined within the context of the source or
			// target object. This will be a DataInputAssociation or DataOutputAssociation
			// of an Activity.
			DataAssociation bo = null;
			BaseElement source = getSourceBo(context);
			BaseElement target = getTargetBo(context);
			String doaFeatureName = "dataOutputAssociations";
			if (source instanceof CatchEvent)
				doaFeatureName = "dataOutputAssociation";
			EStructuralFeature doaFeature = source.eClass().getEStructuralFeature(doaFeatureName);
			String diaFeatureName = "dataInputAssociations";
			if (target instanceof ThrowEvent)
				diaFeatureName = "dataInputAssociation";
			EStructuralFeature diaFeature = target.eClass().getEStructuralFeature(diaFeatureName);
			
			if (doaFeature!=null) {
				List<DataAssociation> list = (List<DataAssociation>) source.eGet(doaFeature);
				if (list.size()>0)
					bo = list.get(0);
			}
			else if (diaFeature!=null) {
				// if the target is an Activity, create an ioSpecification if it doesn have one yet
				DataInput dataInput = null;
				DataInputAssociation dataInputAssoc = null;
				if (target instanceof Activity) {
					Activity activity = (Activity) target;
					InputOutputSpecification ioSpec = activity.getIoSpecification();
					if (ioSpec==null) {
						ioSpec = (InputOutputSpecification) ModelUtil.createFeature(activity, "ioSpecification");
					}
					InputSet inputSet = null;
					if (ioSpec.getInputSets().size()==0) {
						inputSet = (InputSet) ModelUtil.createObject(Bpmn2Package.eINSTANCE.getInputSet());
						ioSpec.getInputSets().add(inputSet);
					}
					else {
						// add to first input set we find
						// TODO: support input set selection if there are more than one
						inputSet = ioSpec.getInputSets().get(0);
					}
					// allow user to select a dataInput
					dataInput = (DataInput) ModelUtil.createObject(Bpmn2Package.eINSTANCE.getDataInput());
					String oldName = dataInput.getName();
					dataInput.setName("Create new input parameter");
					DataInput result = dataInput;
					List<DataInput> dataInputs = ioSpec.getDataInputs();
					List<DataInput> list = new ArrayList<DataInput>();
					list.add(dataInput);
					list.addAll(dataInputs);
					if (list.size()>1) {
						PopupMenu popupMenu = new PopupMenu(list, labelProvider);
						boolean b = popupMenu.show(Display.getCurrent().getActiveShell());
						if (b) {
							result = (DataInput) popupMenu.getResult();
						}
					}
					if (result == dataInput) {
						// the new one
						dataInputs.add(dataInput);
						dataInput.setId(null);
						ModelUtil.setID(dataInput);
						dataInput.setName(oldName);
						inputSet.getDataInputRefs().add(dataInput);
						dataInputAssoc = (DataInputAssociation) ModelUtil.createFeature(target, diaFeature);
						dataInputAssoc.setTargetRef(dataInput);
					} else {
						// and existing one
						dataInput = result;
						// find the DataInputAssociation for this DataInput
						for (DataInputAssociation d : activity.getDataInputAssociations()) {
							if (d.getTargetRef() == dataInput) {
								dataInputAssoc = d;
								break;
							}
						}
						if (dataInputAssoc==null) {
							// none found, create a new one
							dataInputAssoc = (DataInputAssociation) ModelUtil.createFeature(target, diaFeature);
							dataInputAssoc.setTargetRef(dataInput);
						}
					}
				}
				else if (target instanceof ThrowEvent) {
					ThrowEvent throwEvent = (ThrowEvent)target;
					InputSet inputSet = throwEvent.getInputSet();
					if (inputSet==null) {
						inputSet = (InputSet) ModelUtil.createObject(Bpmn2Package.eINSTANCE.getInputSet());
						throwEvent.setInputSet(inputSet);
					}
					// allow user to select a dataInput
					dataInput = (DataInput) ModelUtil.createObject(Bpmn2Package.eINSTANCE.getDataInput());
					String oldName = dataInput.getName();
					dataInput.setName("Create new input parameter");
					DataInput result = dataInput;
					List<DataInput> dataInputs = throwEvent.getDataInputs();
					List<DataInput> list = new ArrayList<DataInput>();
					list.add(dataInput);
					list.addAll(dataInputs);
					if (list.size()>1) {
						PopupMenu popupMenu = new PopupMenu(list, labelProvider);
						boolean b = popupMenu.show(Display.getCurrent().getActiveShell());
						if (b) {
							result = (DataInput) popupMenu.getResult();
						}
					}
					if (result == dataInput) {
						// the new one
						dataInputs.add(dataInput);
						dataInput.setId(null);
						ModelUtil.setID(dataInput);
						dataInput.setName(oldName);
						inputSet.getDataInputRefs().add(dataInput);
						dataInputAssoc = (DataInputAssociation) ModelUtil.createFeature(target, diaFeature);
						dataInputAssoc.setTargetRef(dataInput);
					} else {
						// and existing one
						dataInput = result;
						// find the DataInputAssociation for this DataInput
						for (DataInputAssociation d : throwEvent.getDataInputAssociation()) {
							if (d.getTargetRef() == dataInput) {
								dataInputAssoc = d;
								break;
							}
						}
						if (dataInputAssoc==null) {
							// none found, create a new one
							dataInputAssoc = (DataInputAssociation) ModelUtil.createFeature(target, diaFeature);
							dataInputAssoc.setTargetRef(dataInput);
						}
					}
				}
				dataInputAssoc.getSourceRef().clear();
				dataInputAssoc.getSourceRef().add((ItemAwareElement) source);

				bo = dataInputAssoc;
			}
			putBusinessObject(context, bo);
			return bo;
		}
	}

	public class AddDataAssociationFeature extends AbstractAddFlowFeature<DataAssociation> {
		public AddDataAssociationFeature(IFeatureProvider fp) {
			super(fp);
		}

		@Override
		public PictogramElement add(IAddContext context) {
			AddConnectionContext addConContext = (AddConnectionContext)context;
			Anchor sourceAnchor = addConContext.getSourceAnchor();
			Anchor targetAnchor = addConContext.getTargetAnchor();
			PictogramElement source = sourceAnchor==null ? null : sourceAnchor.getParent();
			PictogramElement target = targetAnchor==null ? null : targetAnchor.getParent();
			boolean anchorChanged = false;
			
			if (createContext!=null) {
				if (source==null) {
					source = createContext.getSourcePictogramElement();
					sourceAnchor = createContext.getSourceAnchor();
				}
				if (target==null) {
					target = createContext.getTargetPictogramElement();
					targetAnchor = createContext.getTargetAnchor();
				}
			}
			
			if (sourceAnchor==null && source instanceof FreeFormConnection) {
				Shape connectionPointShape = AnchorUtil.createConnectionPoint(getFeatureProvider(),
						(FreeFormConnection)source,
						Graphiti.getPeLayoutService().getConnectionMidpoint((FreeFormConnection)source, 0.5));
				sourceAnchor = AnchorUtil.getConnectionPointAnchor(connectionPointShape);
				anchorChanged = true;
			}
			if (targetAnchor==null && target instanceof FreeFormConnection) {
				Shape connectionPointShape = AnchorUtil.createConnectionPoint(getFeatureProvider(),
						(FreeFormConnection)target,
						Graphiti.getPeLayoutService().getConnectionMidpoint((FreeFormConnection)target, 0.5));
				targetAnchor = AnchorUtil.getConnectionPointAnchor(connectionPointShape);
				anchorChanged = true;
			}
			
			// this is silly! why are there no setters for sourceAnchor and targetAnchor in AddConnectionContext???
			if (anchorChanged) {
				AddConnectionContext newContext = new AddConnectionContext(sourceAnchor, targetAnchor);
				newContext.setSize(addConContext.getHeight(), addConContext.getWidth());
				newContext.setLocation(addConContext.getX(), addConContext.getY());
				newContext.setNewObject(getBusinessObject(addConContext));
				newContext.setTargetConnection(addConContext.getTargetConnection());
				newContext.setTargetConnectionDecorator(addConContext.getTargetConnectionDecorator());
				newContext.setTargetContainer(addConContext.getTargetContainer());
				
				context = newContext;
			}
			// we're done with this
			createContext = null;
			
			return super.add(context);
		}

		@Override
		protected Polyline createConnectionLine(Connection connection) {
			Polyline connectionLine = super.createConnectionLine(connection);
			connectionLine.setLineWidth(2);
			connectionLine.setLineStyle(LineStyle.DOT);
			return connectionLine;
		}

		@Override
		protected void decorateConnection(IAddConnectionContext context, Connection connection, DataAssociation businessObject) {
			setAssociationDirection(connection, businessObject);
		}

		@Override
		protected Class<? extends BaseElement> getBoClass() {
			return DataAssociation.class;
		}

		@Override
		public int getHeight() {
			return 0;
		}

		@Override
		public int getWidth() {
			return 0;
		}
	}

	
	private static String getDirection(DataAssociation businessObject) {
		return (businessObject instanceof DataInputAssociation) ? "input" : "output";
	}
	
	private static void setAssociationDirection(Connection connection, DataAssociation businessObject) {
		IPeService peService = Graphiti.getPeService();
		IGaService gaService = Graphiti.getGaService();
		String newDirection = getDirection(businessObject);
		String oldDirection = peService.getPropertyValue(connection, ASSOCIATION_DIRECTION);
		if (oldDirection==null || oldDirection.isEmpty())
			oldDirection = "";

		if (!oldDirection.equals(newDirection)) {
			ConnectionDecorator sourceDecorator = null;
			ConnectionDecorator targetDecorator = null;
			for (ConnectionDecorator d : connection.getConnectionDecorators()) {
				String s = peService.getPropertyValue(d, ARROWHEAD_DECORATOR);
				if (s!=null) {
					if (s.equals("source"))
						sourceDecorator = d;
					else if (s.equals("target"))
						targetDecorator = d;
				}
			}
			
			final int w = 7;
			final int l = 13;
			if (sourceDecorator!=null) {
				connection.getConnectionDecorators().remove(sourceDecorator);				
			}

			if (targetDecorator==null) {
				targetDecorator = peService.createConnectionDecorator(connection, false, 1.0, true);
				Polyline arrowhead = gaService.createPolyline(targetDecorator, new int[] { -l, w, 0, 0, -l, -w });
				StyleUtil.applyStyle(arrowhead, businessObject);
				peService.setPropertyValue(targetDecorator, ARROWHEAD_DECORATOR, "target");
			}
		
			// update the property value in the Connection PictogramElement
			peService.setPropertyValue(connection, ASSOCIATION_DIRECTION, newDirection);
		}

	}
	
	public static class UpdateDataAssociationFeature extends AbstractUpdateFeature {

		public UpdateDataAssociationFeature(IFeatureProvider fp) {
			super(fp);
		}

		@Override
		public boolean canUpdate(IUpdateContext context) {
			if (context.getPictogramElement() instanceof Connection) {
				return BusinessObjectUtil.getFirstElementOfType(
						context.getPictogramElement(), DataAssociation.class) != null;
			}
			return false;
		}

		@Override
		public IReason updateNeeded(IUpdateContext context) {
			IPeService peService = Graphiti.getPeService();
			Connection connection = (Connection) context.getPictogramElement();
			DataAssociation businessObject = BusinessObjectUtil.getFirstElementOfType(context.getPictogramElement(),
					DataAssociation.class);
			String newDirection = getDirection(businessObject);
			String oldDirection = peService.getPropertyValue(connection, ASSOCIATION_DIRECTION);
			if (oldDirection==null || oldDirection.isEmpty())
				oldDirection = "";

			if (!oldDirection.equals(newDirection)) {
				return Reason.createTrueReason();
			}
			return Reason.createFalseReason();
		}

		@Override
		public boolean update(IUpdateContext context) {
			Connection connection = (Connection) context.getPictogramElement();
			DataAssociation businessObject = BusinessObjectUtil.getFirstElementOfType(context.getPictogramElement(),
					DataAssociation.class);
			setAssociationDirection(connection, businessObject);
			return true;
		}
	}

	public static class ReconnectDataAssociationFeature extends AbstractReconnectFlowFeature {

		public ReconnectDataAssociationFeature(IFeatureProvider fp) {
			super(fp);
		}

		@Override
		public boolean canReconnect(IReconnectionContext context) {
			DataAssociation businessObject = BusinessObjectUtil.getFirstElementOfType(context.getConnection(),
					DataAssociation.class);
			BaseElement targetElement = BusinessObjectUtil.getFirstElementOfType(context.getTargetPictogramElement(), BaseElement.class);
			if (targetElement instanceof DataAssociation)
				return false;
			PictogramElement targetPictogramElement = context.getTargetPictogramElement();
			if (targetPictogramElement instanceof FreeFormConnection) {
				return true;
			}
			return super.canReconnect(context);
		}

		@Override
		protected Class<? extends EObject> getTargetClass() {
			return BaseElement.class;
		}

		@Override
		protected Class<? extends EObject> getSourceClass() {
			return BaseElement.class;
		}

		@Override
		public void preReconnect(IReconnectionContext context) {
			PictogramElement targetPictogramElement = context.getTargetPictogramElement();
			if (targetPictogramElement instanceof FreeFormConnection) {
				Shape connectionPointShape = AnchorUtil.createConnectionPoint(
						getFeatureProvider(),
						(FreeFormConnection)targetPictogramElement,
						context.getTargetLocation());
				
				if (context instanceof ReconnectionContext) {
					ReconnectionContext rc = (ReconnectionContext) context;
					rc.setNewAnchor(AnchorUtil.getConnectionPointAnchor(connectionPointShape));
					rc.setTargetPictogramElement(connectionPointShape);
				}
			}
			super.preReconnect(context);
		}

		@Override
		public void postReconnect(IReconnectionContext context) {
			Anchor oldAnchor = context.getOldAnchor();
			AnchorContainer oldAnchorContainer = oldAnchor.getParent();
			AnchorUtil.deleteConnectionPointIfPossible(getFeatureProvider(), (Shape) oldAnchorContainer);
			super.postReconnect(context);
		}
	} 
	
}
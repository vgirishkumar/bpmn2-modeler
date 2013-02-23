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
 * @author Bob Brodt
 ******************************************************************************/

package org.eclipse.bpmn2.modeler.core.features;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.modeler.core.adapters.AdapterUtil;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.bpmn2.modeler.core.features.activity.task.ICustomTaskFeatureContainer;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.ObjectEditingDialog;
import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.runtime.CustomTaskDescriptor;
import org.eclipse.bpmn2.modeler.core.runtime.ModelEnablementDescriptor;
import org.eclipse.bpmn2.modeler.core.runtime.TargetRuntime;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.graphiti.IExecutionInfo;
import org.eclipse.graphiti.features.ICreateConnectionFeature;
import org.eclipse.graphiti.features.ICreateFeature;
import org.eclipse.graphiti.features.IFeature;
import org.eclipse.graphiti.features.IFeatureAndContext;
import org.eclipse.graphiti.features.IFeatureProvider;
import org.eclipse.graphiti.features.context.IAddContext;
import org.eclipse.graphiti.features.context.IAreaContext;
import org.eclipse.graphiti.features.context.IContext;
import org.eclipse.graphiti.features.context.ICreateConnectionContext;
import org.eclipse.graphiti.features.context.ICreateContext;
import org.eclipse.graphiti.features.context.impl.AddContext;
import org.eclipse.graphiti.features.context.impl.CreateConnectionContext;
import org.eclipse.graphiti.features.context.impl.CreateContext;
import org.eclipse.graphiti.features.impl.AbstractCreateFeature;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.PictogramElement;
import org.eclipse.graphiti.mm.pictograms.Shape;
import org.eclipse.graphiti.palette.IPaletteCompartmentEntry;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.editor.DiagramEditor;

/**
 * A Create Feature class that can be used to create multiple objects.
 * @author Bob Brodt
 */
public class CompoundCreateFeature<CONTEXT extends IContext>
		extends AbstractCreateFeature
		implements IBpmn2CreateFeature<BaseElement, CONTEXT> {
	
	public class CreateFeatureNode {
		
		IFeature feature;
		List<CreateFeatureNode> children = new ArrayList<CreateFeatureNode>();
		int x = 0;
		int y = 0;
		int width = 0;
		int height = 0;
		PictogramElement sourcePictogramElement = null;
		PictogramElement targetPictogramElement = null;

		public CreateFeatureNode(IFeature feature) {
			this.feature = feature;
		}
		
		public boolean canCreate(IContext context) {
			if (feature instanceof ICreateFeature && context instanceof ICreateContext) {
				if (!((ICreateFeature)feature).canCreate((ICreateContext)context))
					return false;
			}
			else if (feature instanceof ICreateConnectionFeature && context instanceof ICreateConnectionContext) {
				if (!((ICreateConnectionFeature)feature).canCreate((ICreateConnectionContext)context))
					return false;
			}
//			for (CreateFeatureNode ft : children) {
//				if (!ft.canCreate(context))
//					return false;
//			}
			return true;
		}

		public List<Object> create(IContext context) {
			List<Object> objects = new ArrayList<Object>();
			if (feature instanceof ICreateFeature && context instanceof ICreateContext) {
				Object created[] = ((ICreateFeature)feature).create((ICreateContext)context);
				for (Object o : created)
					objects.add(o);
			}
			else if (feature instanceof ICreateConnectionFeature && context instanceof ICreateConnectionContext) {
				objects.add(((ICreateConnectionFeature)feature).create((ICreateConnectionContext)context));
			}

			ContainerShape targetContainer = null;
			for (Object o : objects) {
				if (o instanceof ContainerShape) {
					targetContainer = (ContainerShape)o;
					break;
				}
			}
			
			IContext childContext = null;
			for (CreateFeatureNode ft : children) {
				if (ft.feature instanceof ICreateFeature) {
					childContext = new CreateContext();
					((CreateContext)childContext).setTargetContainer(targetContainer);
					((CreateContext)childContext).setX(x);
					((CreateContext)childContext).setX(y);
					((CreateContext)childContext).setWidth(width);
					((CreateContext)childContext).setHeight(height);
				}
				else if (ft.feature instanceof ICreateConnectionFeature) {
					childContext = new CreateConnectionContext();
					((CreateConnectionContext)childContext).setSourcePictogramElement(sourcePictogramElement);
					((CreateConnectionContext)childContext).setTargetPictogramElement(targetPictogramElement);
				}
				objects.add(ft.create(childContext));
			}
			return objects;
		}
		
		public boolean isAvailable(IContext context) {
			if (feature!=null && !feature.isAvailable(context))
				return false;
			for (CreateFeatureNode ft : children) {
				if (!ft.isAvailable(context))
					return false;
			}
			return true;
		}
		
		public void addChild(IFeature feature) {
			children.add(new CreateFeatureNode(feature));
		}

		public EClass getBusinessObjectClass() {
			EClass eClass = null;
			if (feature instanceof AbstractBpmn2CreateFeature) {
				eClass = ((AbstractBpmn2CreateFeature)feature).getBusinessObjectClass();
			}
			else if (feature instanceof AbstractBpmn2CreateConnectionFeature) {
				eClass = ((AbstractBpmn2CreateConnectionFeature)feature).getBusinessObjectClass();
			}
			
			for (CreateFeatureNode child : children) {
				EClass ec = child.getBusinessObjectClass();
				if (ec!=null) {
					eClass = ec;
					break;
				}
			}
			return eClass;
		}

		public String getCreateName() {
			String createName = null;
			if (feature!=null)
				createName = feature.getName();
			for (CreateFeatureNode child : children) {
				String cn = child.getCreateName();
				if (cn!=null)
					createName = cn;
			}
			return createName;
		}
		public IFeature getFeature() {
			return feature;
		}

		public void setFeature(IFeature feature) {
			this.feature = feature;
		}

		public int getX() {
			return x;
		}

		public void setX(int x) {
			this.x = x;
		}

		public int getY() {
			return y;
		}

		public void setY(int y) {
			this.y = y;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public PictogramElement getSourcePictogramElement() {
			return sourcePictogramElement;
		}

		public void setSourcePictogramElement(PictogramElement sourcePictogramElement) {
			this.sourcePictogramElement = sourcePictogramElement;
		}

		public PictogramElement getTargetPictogramElement() {
			return targetPictogramElement;
		}

		public void setTargetPictogramElement(PictogramElement targetPictogramElement) {
			this.targetPictogramElement = targetPictogramElement;
		}

		public List<CreateFeatureNode> getChildren() {
			return children;
		}
	} // CreateFeatureNode
	
	protected List<CreateFeatureNode> children = new ArrayList<CreateFeatureNode>();
	
	/**
	 * @param fp
	 * @param name
	 * @param description
	 */
	public CompoundCreateFeature(IFeatureProvider fp, String name, String description) {
		super(fp, name, description);
	}
	
	public CompoundCreateFeature(IFeatureProvider fp) {
		super(fp, null, null);
	}
	
	public CreateFeatureNode addChild(IFeature cf) {
		CreateFeatureNode ft = new CreateFeatureNode(cf);
		children.add(ft);
		return ft;
	}

	@Override
	public boolean canCreate(ICreateContext context) {
		for (CreateFeatureNode ft : children) {
			if (ft.canCreate(context)==false)
				return false;
		}
		return true;
	}

	@Override
	public Object[] create(ICreateContext context) {
		List<Object> objects = new ArrayList<Object>();
		for (CreateFeatureNode ft : children) {
			objects.addAll( ft.create(context) );
		}
		return objects.toArray();
	}

	@Override
	public boolean isAvailable(IContext context) {
		for (CreateFeatureNode ft : children) {
			if (ft.isAvailable(context)==false)
				return false;
		}
		return true;
	}

	@Override
	public BaseElement createBusinessObject(CONTEXT context) {
		assert(false);
		return null;
	}

	@Override
	public BaseElement getBusinessObject(CONTEXT context) {
		assert(false);
		return null;
	}

	@Override
	public void putBusinessObject(CONTEXT context, BaseElement businessObject) {
		assert(false);
	}

	@Override
	public EClass getBusinessObjectClass() {
		if (children.size()>0) {
			return children.get(0).getBusinessObjectClass();
		}
		return null;
	}

	@Override
	public void postExecute(IExecutionInfo executionInfo) {
	}

	public List<CreateFeatureNode> getChildren() {
		return children;
	}
}

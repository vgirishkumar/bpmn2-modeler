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
import java.util.Hashtable;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.modeler.core.adapters.AdapterUtil;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.bpmn2.modeler.core.features.activity.task.ICustomTaskFeatureContainer;
import org.eclipse.bpmn2.modeler.core.merrimac.dialogs.ObjectEditingDialog;
import org.eclipse.bpmn2.modeler.core.preferences.Bpmn2Preferences;
import org.eclipse.bpmn2.modeler.core.runtime.CustomTaskDescriptor;
import org.eclipse.bpmn2.modeler.core.runtime.ModelDescriptor;
import org.eclipse.bpmn2.modeler.core.runtime.ModelEnablementDescriptor;
import org.eclipse.bpmn2.modeler.core.runtime.TargetRuntime;
import org.eclipse.bpmn2.modeler.core.utils.AnchorUtil;
import org.eclipse.bpmn2.modeler.core.utils.BusinessObjectUtil;
import org.eclipse.bpmn2.modeler.core.utils.ModelUtil;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
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
import org.eclipse.graphiti.mm.algorithms.styles.Point;
import org.eclipse.graphiti.mm.pictograms.Connection;
import org.eclipse.graphiti.mm.pictograms.ContainerShape;
import org.eclipse.graphiti.mm.pictograms.FixPointAnchor;
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
		implements IBpmn2CreateFeature<BaseElement, CONTEXT>,
		ICreateConnectionFeature {
	
	public class CreateFeatureNode {
		
		IFeature feature;
		List<CreateFeatureNode> children = new ArrayList<CreateFeatureNode>();
		Hashtable<String, String> properties = null;
		
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
			// Create the parent element.
			// For ICreateContext this will result in a BaseElement and a ContainerShape;
			// for ICreateConnectionContext we only get a Graphiti Connection element.
			List<Object> objects = new ArrayList<Object>();
			if (feature instanceof ICreateFeature && context instanceof ICreateContext) {
				Object created[] = ((ICreateFeature)feature).create((ICreateContext)context);
				for (Object o : created)
					objects.add(o);
			}
			else if (feature instanceof ICreateConnectionFeature && context instanceof ICreateConnectionContext) {
				objects.add(((ICreateConnectionFeature)feature).create((ICreateConnectionContext)context));
			}

			BaseElement businessObject = null;
			ContainerShape targetContainer = null;
			Connection connection = null;
			for (Object o : objects) {
				if (o instanceof ContainerShape && targetContainer==null) {
					targetContainer = (ContainerShape)o;
				}
				else if (o instanceof Connection && connection==null) {
					connection = (Connection)o;
				}
				else if (o instanceof BaseElement && businessObject==null) {
					businessObject = (BaseElement)o;
				}
			}
			if (connection!=null) {
				// we need the BaseElement that is linked to this connection
				businessObject = BusinessObjectUtil.getFirstBaseElement(connection);
			}
			// now apply the Business Object properties
			applyBusinessObjectProperties(businessObject);
			
			// Now process the child features
			List<PictogramElement> createdPEs = new ArrayList<PictogramElement>();
			IContext childContext = null;
			String value;
			int x = 0;
			int y = 0;
			int width = 0;
			int height = 0;
			PictogramElement source = null;
			PictogramElement target = null;

			for (int i =0; i<children.size(); ++i) {
				CreateFeatureNode node = children.get(i);
				if (node.feature instanceof ICreateFeature) {
					childContext = new CreateContext();
					((CreateContext)childContext).setTargetContainer(targetContainer);
					value = node.getProperty("x");
					if (value!=null) {
						x = Integer.parseInt(value);
						((CreateContext)childContext).setX(x);
					}
					value = node.getProperty("y");
					if (value!=null) {
						y = Integer.parseInt(value);
						((CreateContext)childContext).setY(y);
					}
					value = node.getProperty("width");
					if (value!=null) {
						width = Integer.parseInt(value);
						((CreateContext)childContext).setWidth(width);
					}
					value = node.getProperty("height");
					if (value!=null) {
						height = Integer.parseInt(value);
						((CreateContext)childContext).setHeight(height);
					}
				}
				else if (node.feature instanceof ICreateConnectionFeature) {
					childContext = new CreateConnectionContext();
					source = createdPEs.get(i-2);
					target = createdPEs.get(i-1);
					Point sp = AnchorUtil.getCenterPoint((Shape)source);
					Point tp = AnchorUtil.getCenterPoint((Shape)target);
					FixPointAnchor sourceAnchor = AnchorUtil.findNearestAnchor((Shape)source, tp);
					FixPointAnchor targetAnchor = AnchorUtil.findNearestAnchor((Shape)target, sp);
					((CreateConnectionContext)childContext).setSourcePictogramElement(source);
					((CreateConnectionContext)childContext).setTargetPictogramElement(target);
					((CreateConnectionContext)childContext).setSourceAnchor(sourceAnchor);
					((CreateConnectionContext)childContext).setTargetAnchor(targetAnchor);
				}
				
				List<Object> result = node.create(childContext);
				PictogramElement pe = null;
				Connection cn = null;
				BaseElement be = null;
				for (Object o : result) {
					if (o instanceof ContainerShape) {
						pe = (ContainerShape)o;
					}
					else if (o instanceof Connection) {
						cn = (Connection)o;
					}
					else if (o instanceof BaseElement) {
						be = (BaseElement)o;
					}
				}
				if (pe!=null)
					createdPEs.add(pe);
				else if (cn!=null) {
					be = BusinessObjectUtil.getFirstBaseElement(cn);
				}
				applyBusinessObjectProperties(be);
				objects.add(result);
			}
			return objects;
		}

		private void applyBusinessObjectProperties(BaseElement be) {
			if (be!=null && properties!=null) {
				ModelDescriptor md = TargetRuntime.getCurrentRuntime().getModelDescriptor();
				for (Entry<String, String> entry : properties.entrySet()) {
					if (entry.getKey().startsWith("$")) {
						String featureName = entry.getKey().substring(1);
						String value = entry.getValue();
						EStructuralFeature f = md.getFeature(be.eClass().getName(), featureName);
						md.setValueFromString(be, f, value, true);
					}
				}
			}

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
		
		public CreateFeatureNode addChild(IFeature feature) {
			CreateFeatureNode node = new CreateFeatureNode(feature);
			children.add(node);
			return node;
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

		public List<CreateFeatureNode> getChildren() {
			return children;
		}

		public void setProperties(Hashtable<String, String> properties) {
			getProperties().putAll(properties);
		}
		
		public Hashtable<String, String> getProperties() {
			if (properties==null)
				properties = new Hashtable<String, String>();
			return properties;
		}
		
		public String getProperty(String name) {
			if (properties==null)
				return null;
			return properties.get(name);
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
	
	public CreateFeatureNode addChild(IFeature feature) {
		CreateFeatureNode node = new CreateFeatureNode(feature);
		children.add(node);
		return node;
	}

	@Override
	public boolean canExecute(IContext context) {
		boolean ret = false;
		if (context instanceof ICreateContext)
			ret = canCreate((ICreateContext) context);
		else if (context instanceof ICreateConnectionContext)
			ret = canCreate((ICreateConnectionContext)context);
		return ret;
	}
	
	@Override
	public void execute(IContext context) {
		if (context instanceof ICreateContext)
			create((ICreateContext) context);
		else if (context instanceof ICreateConnectionContext)
			create((ICreateConnectionContext)context);
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
	public boolean canCreate(ICreateConnectionContext context) {
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
	public Connection create(ICreateConnectionContext context) {
		List<Object> objects = new ArrayList<Object>();
		for (CreateFeatureNode ft : children) {
			objects.addAll( ft.create(context) );
		}
		if (objects.size()>0) {
			Object o = objects.get(0);
			if (o instanceof Connection)
				return (Connection)o;
		}
		return null;
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

	@Override
	public boolean canStartConnection(ICreateConnectionContext context) {
		return true;
	}

	@Override
	public void startConnecting() {
	}

	@Override
	public void endConnecting() {
	}

	@Override
	public void attachedToSource(ICreateConnectionContext context) {
	}

	@Override
	public void canceledAttaching(ICreateConnectionContext context) {
	}
}

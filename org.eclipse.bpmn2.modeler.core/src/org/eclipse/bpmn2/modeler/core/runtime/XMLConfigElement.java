/*******************************************************************************
 * Copyright (c) 2011, 2012, 2013, 2014 Red Hat, Inc.
 * All rights reserved.
 * This program is made available under the terms of the
 * Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *     Red Hat, Inc. - initial API and implementation
 ******************************************************************************/
package org.eclipse.bpmn2.modeler.core.runtime;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IContributor;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IPluginDescriptor;
import org.eclipse.core.runtime.InvalidRegistryObjectException;

public class XMLConfigElement implements IConfigurationElement {

	protected XMLConfigElement parent = null;
	protected String name = null;
	protected String value = null;
	protected boolean valid = true;
	protected Hashtable<String, String> attributes = new Hashtable<String, String>();
	protected List<XMLConfigElement> children = new ArrayList<XMLConfigElement>();
	
	public XMLConfigElement(XMLConfigElement parent) {
		this.parent = parent;
		if (parent!=null) {
			parent.children.add(this);
		}
	}
	
	public XMLConfigElement(XMLConfigElement parent, String name) {
		this.parent = parent;
		if (parent!=null) {
			parent.children.add(this);
		}
		this.name = name;
	}
	
	@Override
	public Object createExecutableExtension(String propertyName) throws CoreException {
		// TODO Auto-generated method stub
		return null;
	}

	public void setAttribute(String name, String value) {
		attributes.put(name, value);
	}
	
	@Override
	public String getAttribute(String name) throws InvalidRegistryObjectException {
		return attributes.get(name);
	}

	@Override
	public String getAttribute(String attrName, String locale) throws InvalidRegistryObjectException {
		return getAttribute(attrName);
	}

	@Override
	public String getAttributeAsIs(String name) throws InvalidRegistryObjectException {
		return getAttribute(name);
	}

	@Override
	public String[] getAttributeNames() throws InvalidRegistryObjectException {
		return attributes.keySet().toArray( new String[attributes.keySet().size()] );
	}

	@Override
	public IConfigurationElement[] getChildren() throws InvalidRegistryObjectException {
		return children.toArray( new XMLConfigElement[children.size()] );
	}

	@Override
	public IConfigurationElement[] getChildren(String name) throws InvalidRegistryObjectException {
		List<XMLConfigElement> result = new ArrayList<XMLConfigElement>();
		for ( XMLConfigElement e : children) {
			if (name.equals(e.getName()))
				result.add(e);
		}
		return result.toArray( new XMLConfigElement[result.size()] );
	}

	@Override
	public IExtension getDeclaringExtension() throws InvalidRegistryObjectException {
		XMLConfigElement root = this;
		while (root.getParent()!=null)
			root = root.parent;
		return new XMLExtension(root);
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public String getName() throws InvalidRegistryObjectException {
		return name;
	}

	@Override
	public Object getParent() throws InvalidRegistryObjectException {
		return parent;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	@Override
	public String getValue() throws InvalidRegistryObjectException {
		return value;
	}

	@Override
	public String getValue(String locale) throws InvalidRegistryObjectException {
		return getValue();
	}

	@Override
	public String getValueAsIs() throws InvalidRegistryObjectException {
		return getValue();
	}

	@Override
	public String getNamespace() throws InvalidRegistryObjectException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getNamespaceIdentifier() throws InvalidRegistryObjectException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IContributor getContributor() throws InvalidRegistryObjectException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isValid() {
		return valid;
	}

	public static class XMLExtension implements IExtension {

		XMLConfigElement root;
		
		public XMLExtension(XMLConfigElement root) {
			this.root = root;
		}

		@Override
		public IConfigurationElement[] getConfigurationElements() throws InvalidRegistryObjectException {
			return root.getChildren();
		}

		@Override
		public String getNamespace() throws InvalidRegistryObjectException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getNamespaceIdentifier() throws InvalidRegistryObjectException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public IContributor getContributor() throws InvalidRegistryObjectException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getExtensionPointUniqueIdentifier() throws InvalidRegistryObjectException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel() throws InvalidRegistryObjectException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getLabel(String locale) throws InvalidRegistryObjectException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getSimpleIdentifier() throws InvalidRegistryObjectException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public String getUniqueIdentifier() throws InvalidRegistryObjectException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean isValid() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public IPluginDescriptor getDeclaringPluginDescriptor() throws InvalidRegistryObjectException {
			// TODO Auto-generated method stub
			return null;
		}
		
	}
}

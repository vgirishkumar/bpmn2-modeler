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
package org.eclipse.bpmn2.modeler.core.runtime;

import java.net.URL;

import org.eclipse.bpmn2.modeler.core.features.activity.task.ICustomTaskFeatureContainer;
import org.eclipse.bpmn2.modeler.core.runtime.CustomTaskImageProvider.IconSize;
import org.eclipse.graphiti.mm.GraphicsAlgorithmContainer;
import org.eclipse.graphiti.mm.algorithms.Image;
import org.eclipse.graphiti.services.Graphiti;
import org.eclipse.graphiti.ui.internal.GraphitiUIPlugin;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;

public class CustomTaskDescriptor extends ModelExtensionDescriptor {

	protected ICustomTaskFeatureContainer featureContainer;
	protected String category;
	protected String icon;
	protected String propertyTabs[];
	
	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	protected boolean permanent;
	
	public CustomTaskDescriptor(String id, String name) {
		super(id,name);
	}
	
	public ICustomTaskFeatureContainer getFeatureContainer() {
		return featureContainer;
	}

	public void setFeatureContainer(ICustomTaskFeatureContainer featureContainer) {
		this.featureContainer = featureContainer;
	}
	
	public boolean isPermanent() {
		return permanent;
	}

	public void setPermanent(boolean permanent) {
		this.permanent = permanent;
	}
	
	public String[] getPropertyTabs() {
		if (propertyTabs==null)
			propertyTabs = new String[0];
		return propertyTabs;
	}

	public String getImageId(String icon, IconSize size) {
		if (icon != null && icon.trim().length() > 0) {
			String prefix = featureContainer.getClass().getPackage().getName();
			return prefix + "." + icon.trim() + "." + size.value;
		}
		return null;
	}
	
	public String getImagePath(String icon, IconSize size) {
		if (icon != null && icon.trim().length() > 0) {
			String prefix = featureContainer.getClass().getPackage().getName();
			return CustomTaskImageProvider.ICONS_FOLDER + size.value + "/" + icon.trim();
		}
		return null;
	}
}
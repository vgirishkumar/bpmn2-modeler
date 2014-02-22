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
package org.eclipse.bpmn2.modeler.core.runtime;

import java.lang.reflect.Constructor;

import org.eclipse.bpmn2.modeler.core.Activator;
import org.eclipse.bpmn2.modeler.core.EDataTypeConversionFactory;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.emf.ecore.EDataType.Internal.ConversionDelegate;

/**
 * Target Runtime Extension Descriptor class for EMF data type definitions.
 * Instances of this class correspond to <dataType> extension elements in the extension's plugin.xml
 * See the description of the "dataType" element in the org.eclipse.bpmn2.modeler.runtime extension point schema.
 */
public class DataTypeDescriptor extends BaseRuntimeExtensionDescriptor {

	public final static String EXTENSION_NAME = "dataType";

	protected String name;
	protected String delegateClassName;
	
	public DataTypeDescriptor(IConfigurationElement e) {
		super(e);
		name = e.getAttribute("name"); //$NON-NLS-1$
		delegateClassName = e.getAttribute("class"); //$NON-NLS-1$
	}
	
	@Override
	public void dispose() {
		super.dispose();
		EDataTypeConversionFactory.unregisterConversionDelegate(name);
	}

	@Override
	public void setRuntime(TargetRuntime targetRuntime) {
		super.setRuntime(targetRuntime);
		ConversionDelegate delegate = getConversionDelegate();
		EDataTypeConversionFactory.registerConversionDelegate(name, delegate.getClass());
	}

	@Override
	public String getExtensionName() {
		return EXTENSION_NAME;
	}

	public String getDataTypeName() {
		return name;
	}
	
	public ConversionDelegate getConversionDelegate() {
		try {
			ClassLoader cl = this.getRuntime().getRuntimeExtension().getClass().getClassLoader();
			Constructor ctor = null;
			Class adapterClass = Class.forName(delegateClassName, true, cl);
			ctor = adapterClass.getConstructor();
			return (ConversionDelegate)ctor.newInstance();
		} catch (Exception e) {
			Activator.logError(e);
		}
		return null;
	}

}

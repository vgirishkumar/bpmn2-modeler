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
package org.eclipse.bpmn2.modeler.core;

import java.util.Hashtable;

import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EDataType.Internal.ConversionDelegate;
import org.eclipse.emf.ecore.EDataType.Internal.ConversionDelegate.Factory;

public class EDataTypeConversionFactory implements Factory {

	public static EDataTypeConversionFactory INSTANCE = new EDataTypeConversionFactory();
	
	// The URI for our EDataType conversion factory. This must be the same as the "uri" specified in
	// the "org.eclipse.emf.ecore.conversion_delegate" extension point in our plugin.xml
	public final static String DATATYPE_CONVERSION_FACTORY_URI = "http://org.eclipse.bpmn2.modeler.EDataTypeConversionFactory";

	// Registry that maps a data type name to a conversion delegate.
	// Clients may register their own types and conversion delegates.
	private static Hashtable<String, Class<? extends ConversionDelegate>> registry =
			new Hashtable<String, Class<? extends ConversionDelegate>>();
//	
//	static {
//		registerConversionDelegate("EColor", EColorConversionDelegate.class);
//	}
	
	/* (non-Javadoc)
	 * @see org.eclipse.emf.ecore.EDataType.Internal.ConversionDelegate.Factory#createConversionDelegate(org.eclipse.emf.ecore.EDataType)
	 * 
	 * Consult our registry for the name of the given data type.
	 */
	@Override
	public ConversionDelegate createConversionDelegate(EDataType eDataType) {
		Class clazz = registry.get(eDataType.getName());
		if (clazz!=null) {
			try {
				return (ConversionDelegate) clazz.getConstructor(null).newInstance(null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

	public static void registerConversionDelegate(String type, Class<? extends ConversionDelegate> delegate) {
		registry.put(type,delegate);
	}

	public static void unregisterConversionDelegate(String type) {
		registry.remove(type);
	}
	
	public static boolean isFactoryFor(String type) {
		return registry.get(type) != null;
	}
}

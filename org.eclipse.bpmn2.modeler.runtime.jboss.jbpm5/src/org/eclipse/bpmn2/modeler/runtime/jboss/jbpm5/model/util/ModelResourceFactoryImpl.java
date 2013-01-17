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
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.util;

import org.eclipse.bpmn2.modeler.core.model.Bpmn2ModelerResourceFactoryImpl;
import org.eclipse.bpmn2.util.OnlyContainmentTypeInfo;
import org.eclipse.bpmn2.util.XmlExtendedMetadata;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.xmi.XMLResource;
import org.eclipse.emf.ecore.xmi.impl.ElementHandlerImpl;
import org.eclipse.emf.ecore.xmi.XMLSave.XMLTypeInfo;


/**
 * <!-- begin-user-doc -->
 * The <b>Resource Factory</b> associated with the package.
 * <!-- end-user-doc -->
 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.util.ModelResourceImpl
 * @generated NOT
 */
public class ModelResourceFactoryImpl extends Bpmn2ModelerResourceFactoryImpl {
	/**
	 * Creates an instance of the resource eFactory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
	public ModelResourceFactoryImpl() {
		super();
	}

	/**
	 * Creates an instance of the resource.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated NOT
	 */
    @Override
    public Resource createResource(URI uri) {
    	ModelResourceImpl result = new ModelResourceImpl(uri);
        ExtendedMetaData extendedMetadata = new XmlExtendedMetadata();
        result.getDefaultSaveOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, extendedMetadata);
        result.getDefaultLoadOptions().put(XMLResource.OPTION_EXTENDED_META_DATA, extendedMetadata);

        result.getDefaultSaveOptions().put(XMLResource.OPTION_SAVE_TYPE_INFORMATION,
        		// this is only a workaround to allow round-tripping with the jbpm web designer.
        		// The Relationship elements which are used to manage simulation parameters in
        		// the web designer are not yet implemented here. Each BPMN2 element should have
        		// corresponding simulation parameter containers in the <relationship> extension
        		// elements container. This still needs to be added to the jbpm extension model.
                new OnlyContainmentTypeInfo() {

					@Override
					public boolean shouldSaveType(EClass objectType,
							EClassifier featureType, EStructuralFeature feature) {
						if ("AnyType".equals(objectType.getName()))
							return false;
						return super.shouldSaveType(objectType, featureType, feature);
					}

					@Override
					public boolean shouldSaveType(EClass objectType,
							EClass featureType, EStructuralFeature feature) {
						if ("AnyType".equals(objectType.getName()))
							return false;
						return super.shouldSaveType(objectType, featureType, feature);
					}
        	
        });

        // allow "href" object resolution
        result.getDefaultSaveOptions().put(XMLResource.OPTION_USE_ENCODED_ATTRIBUTE_STYLE,
                Boolean.FALSE);

        result.getDefaultLoadOptions().put(XMLResource.OPTION_USE_LEXICAL_HANDLER, Boolean.TRUE);

        result.getDefaultSaveOptions().put(XMLResource.OPTION_ELEMENT_HANDLER,
                new ElementHandlerImpl(true));

        result.getDefaultSaveOptions().put(XMLResource.OPTION_ENCODING, "UTF-8");
        
        // save xsi:schemaLocation in Definitions parameter
        result.getDefaultSaveOptions().put(XMLResource.OPTION_SCHEMA_LOCATION, Boolean.TRUE);

        return result;
    }

} //ModelResourceFactoryImpl

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
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model;

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelFactory
 * @model kind="package"
 * @generated
 */
public interface ModelPackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "model";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://www.jboss.org/drools";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "drools";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ModelPackage eINSTANCE = org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl.init();

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ParameterValueImpl <em>Parameter Value</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ParameterValueImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getParameterValue()
	 * @generated
	 */
	int PARAMETER_VALUE = 32;

	/**
	 * The feature id for the '<em><b>Instance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_VALUE__INSTANCE = 0;

	/**
	 * The feature id for the '<em><b>Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_VALUE__RESULT = 1;

	/**
	 * The feature id for the '<em><b>Valid For</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_VALUE__VALID_FOR = 2;

	/**
	 * The number of structural features of the '<em>Parameter Value</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_VALUE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DistributionParameterImpl <em>Distribution Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DistributionParameterImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getDistributionParameter()
	 * @generated
	 */
	int DISTRIBUTION_PARAMETER = 28;

	/**
	 * The feature id for the '<em><b>Instance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DISTRIBUTION_PARAMETER__INSTANCE = PARAMETER_VALUE__INSTANCE;

	/**
	 * The feature id for the '<em><b>Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DISTRIBUTION_PARAMETER__RESULT = PARAMETER_VALUE__RESULT;

	/**
	 * The feature id for the '<em><b>Valid For</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DISTRIBUTION_PARAMETER__VALID_FOR = PARAMETER_VALUE__VALID_FOR;

	/**
	 * The feature id for the '<em><b>Discrete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DISTRIBUTION_PARAMETER__DISCRETE = PARAMETER_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Distribution Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DISTRIBUTION_PARAMETER_FEATURE_COUNT = PARAMETER_VALUE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.BetaDistributionTypeImpl <em>Beta Distribution Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.BetaDistributionTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getBetaDistributionType()
	 * @generated
	 */
	int BETA_DISTRIBUTION_TYPE = 0;

	/**
	 * The feature id for the '<em><b>Instance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BETA_DISTRIBUTION_TYPE__INSTANCE = DISTRIBUTION_PARAMETER__INSTANCE;

	/**
	 * The feature id for the '<em><b>Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BETA_DISTRIBUTION_TYPE__RESULT = DISTRIBUTION_PARAMETER__RESULT;

	/**
	 * The feature id for the '<em><b>Valid For</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BETA_DISTRIBUTION_TYPE__VALID_FOR = DISTRIBUTION_PARAMETER__VALID_FOR;

	/**
	 * The feature id for the '<em><b>Discrete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BETA_DISTRIBUTION_TYPE__DISCRETE = DISTRIBUTION_PARAMETER__DISCRETE;

	/**
	 * The feature id for the '<em><b>Scale</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BETA_DISTRIBUTION_TYPE__SCALE = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Shape</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BETA_DISTRIBUTION_TYPE__SHAPE = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Beta Distribution Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BETA_DISTRIBUTION_TYPE_FEATURE_COUNT = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.BinomialDistributionTypeImpl <em>Binomial Distribution Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.BinomialDistributionTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getBinomialDistributionType()
	 * @generated
	 */
	int BINOMIAL_DISTRIBUTION_TYPE = 1;

	/**
	 * The feature id for the '<em><b>Instance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINOMIAL_DISTRIBUTION_TYPE__INSTANCE = DISTRIBUTION_PARAMETER__INSTANCE;

	/**
	 * The feature id for the '<em><b>Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINOMIAL_DISTRIBUTION_TYPE__RESULT = DISTRIBUTION_PARAMETER__RESULT;

	/**
	 * The feature id for the '<em><b>Valid For</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINOMIAL_DISTRIBUTION_TYPE__VALID_FOR = DISTRIBUTION_PARAMETER__VALID_FOR;

	/**
	 * The feature id for the '<em><b>Discrete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINOMIAL_DISTRIBUTION_TYPE__DISCRETE = DISTRIBUTION_PARAMETER__DISCRETE;

	/**
	 * The feature id for the '<em><b>Probability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINOMIAL_DISTRIBUTION_TYPE__PROBABILITY = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Trials</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINOMIAL_DISTRIBUTION_TYPE__TRIALS = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Binomial Distribution Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BINOMIAL_DISTRIBUTION_TYPE_FEATURE_COUNT = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ConstantParameterImpl <em>Constant Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ConstantParameterImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getConstantParameter()
	 * @generated
	 */
	int CONSTANT_PARAMETER = 7;

	/**
	 * The feature id for the '<em><b>Instance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_PARAMETER__INSTANCE = PARAMETER_VALUE__INSTANCE;

	/**
	 * The feature id for the '<em><b>Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_PARAMETER__RESULT = PARAMETER_VALUE__RESULT;

	/**
	 * The feature id for the '<em><b>Valid For</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_PARAMETER__VALID_FOR = PARAMETER_VALUE__VALID_FOR;

	/**
	 * The number of structural features of the '<em>Constant Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONSTANT_PARAMETER_FEATURE_COUNT = PARAMETER_VALUE_FEATURE_COUNT + 0;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.BooleanParameterTypeImpl <em>Boolean Parameter Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.BooleanParameterTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getBooleanParameterType()
	 * @generated
	 */
	int BOOLEAN_PARAMETER_TYPE = 2;

	/**
	 * The feature id for the '<em><b>Instance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_PARAMETER_TYPE__INSTANCE = CONSTANT_PARAMETER__INSTANCE;

	/**
	 * The feature id for the '<em><b>Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_PARAMETER_TYPE__RESULT = CONSTANT_PARAMETER__RESULT;

	/**
	 * The feature id for the '<em><b>Valid For</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_PARAMETER_TYPE__VALID_FOR = CONSTANT_PARAMETER__VALID_FOR;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_PARAMETER_TYPE__VALUE = CONSTANT_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Boolean Parameter Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int BOOLEAN_PARAMETER_TYPE_FEATURE_COUNT = CONSTANT_PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.CalendarImpl <em>Calendar</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.CalendarImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getCalendar()
	 * @generated
	 */
	int CALENDAR = 3;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALENDAR__VALUE = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALENDAR__ID = 1;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALENDAR__NAME = 2;

	/**
	 * The number of structural features of the '<em>Calendar</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CALENDAR_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl <em>Document Root</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getDocumentRoot()
	 * @generated
	 */
	int DOCUMENT_ROOT = 4;

	/**
	 * The feature id for the '<em><b>Mixed</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MIXED = Bpmn2Package.DOCUMENT_ROOT__MIXED;

	/**
	 * The feature id for the '<em><b>XMLNS Prefix Map</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XMLNS_PREFIX_MAP = Bpmn2Package.DOCUMENT_ROOT__XMLNS_PREFIX_MAP;

	/**
	 * The feature id for the '<em><b>XSI Schema Location</b></em>' map.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__XSI_SCHEMA_LOCATION = Bpmn2Package.DOCUMENT_ROOT__XSI_SCHEMA_LOCATION;

	/**
	 * The feature id for the '<em><b>Activity</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ACTIVITY = Bpmn2Package.DOCUMENT_ROOT__ACTIVITY;

	/**
	 * The feature id for the '<em><b>Ad Hoc Sub Process</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__AD_HOC_SUB_PROCESS = Bpmn2Package.DOCUMENT_ROOT__AD_HOC_SUB_PROCESS;

	/**
	 * The feature id for the '<em><b>Flow Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__FLOW_ELEMENT = Bpmn2Package.DOCUMENT_ROOT__FLOW_ELEMENT;

	/**
	 * The feature id for the '<em><b>Artifact</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ARTIFACT = Bpmn2Package.DOCUMENT_ROOT__ARTIFACT;

	/**
	 * The feature id for the '<em><b>Assignment</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ASSIGNMENT = Bpmn2Package.DOCUMENT_ROOT__ASSIGNMENT;

	/**
	 * The feature id for the '<em><b>Association</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ASSOCIATION = Bpmn2Package.DOCUMENT_ROOT__ASSOCIATION;

	/**
	 * The feature id for the '<em><b>Auditing</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__AUDITING = Bpmn2Package.DOCUMENT_ROOT__AUDITING;

	/**
	 * The feature id for the '<em><b>Base Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BASE_ELEMENT = Bpmn2Package.DOCUMENT_ROOT__BASE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Base Element With Mixed Content</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BASE_ELEMENT_WITH_MIXED_CONTENT = Bpmn2Package.DOCUMENT_ROOT__BASE_ELEMENT_WITH_MIXED_CONTENT;

	/**
	 * The feature id for the '<em><b>Boundary Event</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BOUNDARY_EVENT = Bpmn2Package.DOCUMENT_ROOT__BOUNDARY_EVENT;

	/**
	 * The feature id for the '<em><b>Business Rule Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BUSINESS_RULE_TASK = Bpmn2Package.DOCUMENT_ROOT__BUSINESS_RULE_TASK;

	/**
	 * The feature id for the '<em><b>Callable Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CALLABLE_ELEMENT = Bpmn2Package.DOCUMENT_ROOT__CALLABLE_ELEMENT;

	/**
	 * The feature id for the '<em><b>Call Activity</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CALL_ACTIVITY = Bpmn2Package.DOCUMENT_ROOT__CALL_ACTIVITY;

	/**
	 * The feature id for the '<em><b>Call Choreography</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CALL_CHOREOGRAPHY = Bpmn2Package.DOCUMENT_ROOT__CALL_CHOREOGRAPHY;

	/**
	 * The feature id for the '<em><b>Call Conversation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CALL_CONVERSATION = Bpmn2Package.DOCUMENT_ROOT__CALL_CONVERSATION;

	/**
	 * The feature id for the '<em><b>Conversation Node</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CONVERSATION_NODE = Bpmn2Package.DOCUMENT_ROOT__CONVERSATION_NODE;

	/**
	 * The feature id for the '<em><b>Cancel Event Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CANCEL_EVENT_DEFINITION = Bpmn2Package.DOCUMENT_ROOT__CANCEL_EVENT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Event Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EVENT_DEFINITION = Bpmn2Package.DOCUMENT_ROOT__EVENT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Root Element</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ROOT_ELEMENT = Bpmn2Package.DOCUMENT_ROOT__ROOT_ELEMENT;

	/**
	 * The feature id for the '<em><b>Catch Event</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CATCH_EVENT = Bpmn2Package.DOCUMENT_ROOT__CATCH_EVENT;

	/**
	 * The feature id for the '<em><b>Category</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CATEGORY = Bpmn2Package.DOCUMENT_ROOT__CATEGORY;

	/**
	 * The feature id for the '<em><b>Category Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CATEGORY_VALUE = Bpmn2Package.DOCUMENT_ROOT__CATEGORY_VALUE;

	/**
	 * The feature id for the '<em><b>Choreography</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CHOREOGRAPHY = Bpmn2Package.DOCUMENT_ROOT__CHOREOGRAPHY;

	/**
	 * The feature id for the '<em><b>Collaboration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__COLLABORATION = Bpmn2Package.DOCUMENT_ROOT__COLLABORATION;

	/**
	 * The feature id for the '<em><b>Choreography Activity</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CHOREOGRAPHY_ACTIVITY = Bpmn2Package.DOCUMENT_ROOT__CHOREOGRAPHY_ACTIVITY;

	/**
	 * The feature id for the '<em><b>Choreography Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CHOREOGRAPHY_TASK = Bpmn2Package.DOCUMENT_ROOT__CHOREOGRAPHY_TASK;

	/**
	 * The feature id for the '<em><b>Compensate Event Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__COMPENSATE_EVENT_DEFINITION = Bpmn2Package.DOCUMENT_ROOT__COMPENSATE_EVENT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Complex Behavior Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__COMPLEX_BEHAVIOR_DEFINITION = Bpmn2Package.DOCUMENT_ROOT__COMPLEX_BEHAVIOR_DEFINITION;

	/**
	 * The feature id for the '<em><b>Complex Gateway</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__COMPLEX_GATEWAY = Bpmn2Package.DOCUMENT_ROOT__COMPLEX_GATEWAY;

	/**
	 * The feature id for the '<em><b>Conditional Event Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CONDITIONAL_EVENT_DEFINITION = Bpmn2Package.DOCUMENT_ROOT__CONDITIONAL_EVENT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Conversation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CONVERSATION = Bpmn2Package.DOCUMENT_ROOT__CONVERSATION;

	/**
	 * The feature id for the '<em><b>Conversation Association</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CONVERSATION_ASSOCIATION = Bpmn2Package.DOCUMENT_ROOT__CONVERSATION_ASSOCIATION;

	/**
	 * The feature id for the '<em><b>Conversation Link</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CONVERSATION_LINK = Bpmn2Package.DOCUMENT_ROOT__CONVERSATION_LINK;

	/**
	 * The feature id for the '<em><b>Correlation Key</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CORRELATION_KEY = Bpmn2Package.DOCUMENT_ROOT__CORRELATION_KEY;

	/**
	 * The feature id for the '<em><b>Correlation Property</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CORRELATION_PROPERTY = Bpmn2Package.DOCUMENT_ROOT__CORRELATION_PROPERTY;

	/**
	 * The feature id for the '<em><b>Correlation Property Binding</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CORRELATION_PROPERTY_BINDING = Bpmn2Package.DOCUMENT_ROOT__CORRELATION_PROPERTY_BINDING;

	/**
	 * The feature id for the '<em><b>Correlation Property Retrieval Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CORRELATION_PROPERTY_RETRIEVAL_EXPRESSION = Bpmn2Package.DOCUMENT_ROOT__CORRELATION_PROPERTY_RETRIEVAL_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Correlation Subscription</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__CORRELATION_SUBSCRIPTION = Bpmn2Package.DOCUMENT_ROOT__CORRELATION_SUBSCRIPTION;

	/**
	 * The feature id for the '<em><b>Data Association</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DATA_ASSOCIATION = Bpmn2Package.DOCUMENT_ROOT__DATA_ASSOCIATION;

	/**
	 * The feature id for the '<em><b>Data Input</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DATA_INPUT = Bpmn2Package.DOCUMENT_ROOT__DATA_INPUT;

	/**
	 * The feature id for the '<em><b>Data Input Association</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DATA_INPUT_ASSOCIATION = Bpmn2Package.DOCUMENT_ROOT__DATA_INPUT_ASSOCIATION;

	/**
	 * The feature id for the '<em><b>Data Object</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DATA_OBJECT = Bpmn2Package.DOCUMENT_ROOT__DATA_OBJECT;

	/**
	 * The feature id for the '<em><b>Data Object Reference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DATA_OBJECT_REFERENCE = Bpmn2Package.DOCUMENT_ROOT__DATA_OBJECT_REFERENCE;

	/**
	 * The feature id for the '<em><b>Data Output</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DATA_OUTPUT = Bpmn2Package.DOCUMENT_ROOT__DATA_OUTPUT;

	/**
	 * The feature id for the '<em><b>Data Output Association</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DATA_OUTPUT_ASSOCIATION = Bpmn2Package.DOCUMENT_ROOT__DATA_OUTPUT_ASSOCIATION;

	/**
	 * The feature id for the '<em><b>Data State</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DATA_STATE = Bpmn2Package.DOCUMENT_ROOT__DATA_STATE;

	/**
	 * The feature id for the '<em><b>Data Store</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DATA_STORE = Bpmn2Package.DOCUMENT_ROOT__DATA_STORE;

	/**
	 * The feature id for the '<em><b>Data Store Reference</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DATA_STORE_REFERENCE = Bpmn2Package.DOCUMENT_ROOT__DATA_STORE_REFERENCE;

	/**
	 * The feature id for the '<em><b>Definitions</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DEFINITIONS = Bpmn2Package.DOCUMENT_ROOT__DEFINITIONS;

	/**
	 * The feature id for the '<em><b>Documentation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DOCUMENTATION = Bpmn2Package.DOCUMENT_ROOT__DOCUMENTATION;

	/**
	 * The feature id for the '<em><b>End Event</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__END_EVENT = Bpmn2Package.DOCUMENT_ROOT__END_EVENT;

	/**
	 * The feature id for the '<em><b>End Point</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__END_POINT = Bpmn2Package.DOCUMENT_ROOT__END_POINT;

	/**
	 * The feature id for the '<em><b>Error</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ERROR = Bpmn2Package.DOCUMENT_ROOT__ERROR;

	/**
	 * The feature id for the '<em><b>Error Event Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ERROR_EVENT_DEFINITION = Bpmn2Package.DOCUMENT_ROOT__ERROR_EVENT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Escalation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ESCALATION = Bpmn2Package.DOCUMENT_ROOT__ESCALATION;

	/**
	 * The feature id for the '<em><b>Escalation Event Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ESCALATION_EVENT_DEFINITION = Bpmn2Package.DOCUMENT_ROOT__ESCALATION_EVENT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Event</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EVENT = Bpmn2Package.DOCUMENT_ROOT__EVENT;

	/**
	 * The feature id for the '<em><b>Event Based Gateway</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EVENT_BASED_GATEWAY = Bpmn2Package.DOCUMENT_ROOT__EVENT_BASED_GATEWAY;

	/**
	 * The feature id for the '<em><b>Exclusive Gateway</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EXCLUSIVE_GATEWAY = Bpmn2Package.DOCUMENT_ROOT__EXCLUSIVE_GATEWAY;

	/**
	 * The feature id for the '<em><b>Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EXPRESSION = Bpmn2Package.DOCUMENT_ROOT__EXPRESSION;

	/**
	 * The feature id for the '<em><b>Extension</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EXTENSION = Bpmn2Package.DOCUMENT_ROOT__EXTENSION;

	/**
	 * The feature id for the '<em><b>Extension Elements</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EXTENSION_ELEMENTS = Bpmn2Package.DOCUMENT_ROOT__EXTENSION_ELEMENTS;

	/**
	 * The feature id for the '<em><b>Flow Node</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__FLOW_NODE = Bpmn2Package.DOCUMENT_ROOT__FLOW_NODE;

	/**
	 * The feature id for the '<em><b>Formal Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__FORMAL_EXPRESSION = Bpmn2Package.DOCUMENT_ROOT__FORMAL_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Gateway</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__GATEWAY = Bpmn2Package.DOCUMENT_ROOT__GATEWAY;

	/**
	 * The feature id for the '<em><b>Global Business Rule Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__GLOBAL_BUSINESS_RULE_TASK = Bpmn2Package.DOCUMENT_ROOT__GLOBAL_BUSINESS_RULE_TASK;

	/**
	 * The feature id for the '<em><b>Global Choreography Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__GLOBAL_CHOREOGRAPHY_TASK = Bpmn2Package.DOCUMENT_ROOT__GLOBAL_CHOREOGRAPHY_TASK;

	/**
	 * The feature id for the '<em><b>Global Conversation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__GLOBAL_CONVERSATION = Bpmn2Package.DOCUMENT_ROOT__GLOBAL_CONVERSATION;

	/**
	 * The feature id for the '<em><b>Global Manual Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__GLOBAL_MANUAL_TASK = Bpmn2Package.DOCUMENT_ROOT__GLOBAL_MANUAL_TASK;

	/**
	 * The feature id for the '<em><b>Global Script Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__GLOBAL_SCRIPT_TASK = Bpmn2Package.DOCUMENT_ROOT__GLOBAL_SCRIPT_TASK;

	/**
	 * The feature id for the '<em><b>Global Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__GLOBAL_TASK = Bpmn2Package.DOCUMENT_ROOT__GLOBAL_TASK;

	/**
	 * The feature id for the '<em><b>Global User Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__GLOBAL_USER_TASK = Bpmn2Package.DOCUMENT_ROOT__GLOBAL_USER_TASK;

	/**
	 * The feature id for the '<em><b>Group</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__GROUP = Bpmn2Package.DOCUMENT_ROOT__GROUP;

	/**
	 * The feature id for the '<em><b>Human Performer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__HUMAN_PERFORMER = Bpmn2Package.DOCUMENT_ROOT__HUMAN_PERFORMER;

	/**
	 * The feature id for the '<em><b>Performer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PERFORMER = Bpmn2Package.DOCUMENT_ROOT__PERFORMER;

	/**
	 * The feature id for the '<em><b>Resource Role</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__RESOURCE_ROLE = Bpmn2Package.DOCUMENT_ROOT__RESOURCE_ROLE;

	/**
	 * The feature id for the '<em><b>Implicit Throw Event</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__IMPLICIT_THROW_EVENT = Bpmn2Package.DOCUMENT_ROOT__IMPLICIT_THROW_EVENT;

	/**
	 * The feature id for the '<em><b>Import</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__IMPORT = Bpmn2Package.DOCUMENT_ROOT__IMPORT;

	/**
	 * The feature id for the '<em><b>Inclusive Gateway</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__INCLUSIVE_GATEWAY = Bpmn2Package.DOCUMENT_ROOT__INCLUSIVE_GATEWAY;

	/**
	 * The feature id for the '<em><b>Input Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__INPUT_SET = Bpmn2Package.DOCUMENT_ROOT__INPUT_SET;

	/**
	 * The feature id for the '<em><b>Interface</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__INTERFACE = Bpmn2Package.DOCUMENT_ROOT__INTERFACE;

	/**
	 * The feature id for the '<em><b>Intermediate Catch Event</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__INTERMEDIATE_CATCH_EVENT = Bpmn2Package.DOCUMENT_ROOT__INTERMEDIATE_CATCH_EVENT;

	/**
	 * The feature id for the '<em><b>Intermediate Throw Event</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__INTERMEDIATE_THROW_EVENT = Bpmn2Package.DOCUMENT_ROOT__INTERMEDIATE_THROW_EVENT;

	/**
	 * The feature id for the '<em><b>Io Binding</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__IO_BINDING = Bpmn2Package.DOCUMENT_ROOT__IO_BINDING;

	/**
	 * The feature id for the '<em><b>Io Specification</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__IO_SPECIFICATION = Bpmn2Package.DOCUMENT_ROOT__IO_SPECIFICATION;

	/**
	 * The feature id for the '<em><b>Item Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ITEM_DEFINITION = Bpmn2Package.DOCUMENT_ROOT__ITEM_DEFINITION;

	/**
	 * The feature id for the '<em><b>Lane</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__LANE = Bpmn2Package.DOCUMENT_ROOT__LANE;

	/**
	 * The feature id for the '<em><b>Lane Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__LANE_SET = Bpmn2Package.DOCUMENT_ROOT__LANE_SET;

	/**
	 * The feature id for the '<em><b>Link Event Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__LINK_EVENT_DEFINITION = Bpmn2Package.DOCUMENT_ROOT__LINK_EVENT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Loop Characteristics</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__LOOP_CHARACTERISTICS = Bpmn2Package.DOCUMENT_ROOT__LOOP_CHARACTERISTICS;

	/**
	 * The feature id for the '<em><b>Manual Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MANUAL_TASK = Bpmn2Package.DOCUMENT_ROOT__MANUAL_TASK;

	/**
	 * The feature id for the '<em><b>Message</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MESSAGE = Bpmn2Package.DOCUMENT_ROOT__MESSAGE;

	/**
	 * The feature id for the '<em><b>Message Event Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MESSAGE_EVENT_DEFINITION = Bpmn2Package.DOCUMENT_ROOT__MESSAGE_EVENT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Message Flow</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MESSAGE_FLOW = Bpmn2Package.DOCUMENT_ROOT__MESSAGE_FLOW;

	/**
	 * The feature id for the '<em><b>Message Flow Association</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MESSAGE_FLOW_ASSOCIATION = Bpmn2Package.DOCUMENT_ROOT__MESSAGE_FLOW_ASSOCIATION;

	/**
	 * The feature id for the '<em><b>Monitoring</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MONITORING = Bpmn2Package.DOCUMENT_ROOT__MONITORING;

	/**
	 * The feature id for the '<em><b>Multi Instance Loop Characteristics</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__MULTI_INSTANCE_LOOP_CHARACTERISTICS = Bpmn2Package.DOCUMENT_ROOT__MULTI_INSTANCE_LOOP_CHARACTERISTICS;

	/**
	 * The feature id for the '<em><b>Operation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__OPERATION = Bpmn2Package.DOCUMENT_ROOT__OPERATION;

	/**
	 * The feature id for the '<em><b>Output Set</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__OUTPUT_SET = Bpmn2Package.DOCUMENT_ROOT__OUTPUT_SET;

	/**
	 * The feature id for the '<em><b>Parallel Gateway</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PARALLEL_GATEWAY = Bpmn2Package.DOCUMENT_ROOT__PARALLEL_GATEWAY;

	/**
	 * The feature id for the '<em><b>Participant</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PARTICIPANT = Bpmn2Package.DOCUMENT_ROOT__PARTICIPANT;

	/**
	 * The feature id for the '<em><b>Participant Association</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PARTICIPANT_ASSOCIATION = Bpmn2Package.DOCUMENT_ROOT__PARTICIPANT_ASSOCIATION;

	/**
	 * The feature id for the '<em><b>Participant Multiplicity</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PARTICIPANT_MULTIPLICITY = Bpmn2Package.DOCUMENT_ROOT__PARTICIPANT_MULTIPLICITY;

	/**
	 * The feature id for the '<em><b>Partner Entity</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PARTNER_ENTITY = Bpmn2Package.DOCUMENT_ROOT__PARTNER_ENTITY;

	/**
	 * The feature id for the '<em><b>Partner Role</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PARTNER_ROLE = Bpmn2Package.DOCUMENT_ROOT__PARTNER_ROLE;

	/**
	 * The feature id for the '<em><b>Potential Owner</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__POTENTIAL_OWNER = Bpmn2Package.DOCUMENT_ROOT__POTENTIAL_OWNER;

	/**
	 * The feature id for the '<em><b>Process</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PROCESS = Bpmn2Package.DOCUMENT_ROOT__PROCESS;

	/**
	 * The feature id for the '<em><b>Property</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PROPERTY = Bpmn2Package.DOCUMENT_ROOT__PROPERTY;

	/**
	 * The feature id for the '<em><b>Receive Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__RECEIVE_TASK = Bpmn2Package.DOCUMENT_ROOT__RECEIVE_TASK;

	/**
	 * The feature id for the '<em><b>Relationship</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__RELATIONSHIP = Bpmn2Package.DOCUMENT_ROOT__RELATIONSHIP;

	/**
	 * The feature id for the '<em><b>Rendering</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__RENDERING = Bpmn2Package.DOCUMENT_ROOT__RENDERING;

	/**
	 * The feature id for the '<em><b>Resource</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__RESOURCE = Bpmn2Package.DOCUMENT_ROOT__RESOURCE;

	/**
	 * The feature id for the '<em><b>Resource Assignment Expression</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__RESOURCE_ASSIGNMENT_EXPRESSION = Bpmn2Package.DOCUMENT_ROOT__RESOURCE_ASSIGNMENT_EXPRESSION;

	/**
	 * The feature id for the '<em><b>Resource Parameter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__RESOURCE_PARAMETER = Bpmn2Package.DOCUMENT_ROOT__RESOURCE_PARAMETER;

	/**
	 * The feature id for the '<em><b>Resource Parameter Binding</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__RESOURCE_PARAMETER_BINDING = Bpmn2Package.DOCUMENT_ROOT__RESOURCE_PARAMETER_BINDING;

	/**
	 * The feature id for the '<em><b>Script</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SCRIPT = Bpmn2Package.DOCUMENT_ROOT__SCRIPT;

	/**
	 * The feature id for the '<em><b>Script Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SCRIPT_TASK = Bpmn2Package.DOCUMENT_ROOT__SCRIPT_TASK;

	/**
	 * The feature id for the '<em><b>Send Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SEND_TASK = Bpmn2Package.DOCUMENT_ROOT__SEND_TASK;

	/**
	 * The feature id for the '<em><b>Sequence Flow</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SEQUENCE_FLOW = Bpmn2Package.DOCUMENT_ROOT__SEQUENCE_FLOW;

	/**
	 * The feature id for the '<em><b>Service Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SERVICE_TASK = Bpmn2Package.DOCUMENT_ROOT__SERVICE_TASK;

	/**
	 * The feature id for the '<em><b>Signal</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SIGNAL = Bpmn2Package.DOCUMENT_ROOT__SIGNAL;

	/**
	 * The feature id for the '<em><b>Signal Event Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SIGNAL_EVENT_DEFINITION = Bpmn2Package.DOCUMENT_ROOT__SIGNAL_EVENT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Standard Loop Characteristics</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__STANDARD_LOOP_CHARACTERISTICS = Bpmn2Package.DOCUMENT_ROOT__STANDARD_LOOP_CHARACTERISTICS;

	/**
	 * The feature id for the '<em><b>Start Event</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__START_EVENT = Bpmn2Package.DOCUMENT_ROOT__START_EVENT;

	/**
	 * The feature id for the '<em><b>Sub Choreography</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SUB_CHOREOGRAPHY = Bpmn2Package.DOCUMENT_ROOT__SUB_CHOREOGRAPHY;

	/**
	 * The feature id for the '<em><b>Sub Conversation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SUB_CONVERSATION = Bpmn2Package.DOCUMENT_ROOT__SUB_CONVERSATION;

	/**
	 * The feature id for the '<em><b>Sub Process</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__SUB_PROCESS = Bpmn2Package.DOCUMENT_ROOT__SUB_PROCESS;

	/**
	 * The feature id for the '<em><b>Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TASK = Bpmn2Package.DOCUMENT_ROOT__TASK;

	/**
	 * The feature id for the '<em><b>Terminate Event Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TERMINATE_EVENT_DEFINITION = Bpmn2Package.DOCUMENT_ROOT__TERMINATE_EVENT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Text</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TEXT = Bpmn2Package.DOCUMENT_ROOT__TEXT;

	/**
	 * The feature id for the '<em><b>Text Annotation</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TEXT_ANNOTATION = Bpmn2Package.DOCUMENT_ROOT__TEXT_ANNOTATION;

	/**
	 * The feature id for the '<em><b>Throw Event</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__THROW_EVENT = Bpmn2Package.DOCUMENT_ROOT__THROW_EVENT;

	/**
	 * The feature id for the '<em><b>Timer Event Definition</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TIMER_EVENT_DEFINITION = Bpmn2Package.DOCUMENT_ROOT__TIMER_EVENT_DEFINITION;

	/**
	 * The feature id for the '<em><b>Transaction</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TRANSACTION = Bpmn2Package.DOCUMENT_ROOT__TRANSACTION;

	/**
	 * The feature id for the '<em><b>User Task</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__USER_TASK = Bpmn2Package.DOCUMENT_ROOT__USER_TASK;

	/**
	 * The feature id for the '<em><b>Beta Distribution</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BETA_DISTRIBUTION = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Parameter Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PARAMETER_VALUE = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Binomial Distribution</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BINOMIAL_DISTRIBUTION = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Boolean Parameter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__BOOLEAN_PARAMETER = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 3;

	/**
	 * The feature id for the '<em><b>Date Time Parameter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DATE_TIME_PARAMETER = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 4;

	/**
	 * The feature id for the '<em><b>Decimal Parameter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DECIMAL_PARAMETER = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 5;

	/**
	 * The feature id for the '<em><b>Duration Parameter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__DURATION_PARAMETER = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 6;

	/**
	 * The feature id for the '<em><b>Enum Parameter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ENUM_PARAMETER = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 7;

	/**
	 * The feature id for the '<em><b>Erlang Distribution</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ERLANG_DISTRIBUTION = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 8;

	/**
	 * The feature id for the '<em><b>Expression Parameter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__EXPRESSION_PARAMETER = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 9;

	/**
	 * The feature id for the '<em><b>Floating Parameter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__FLOATING_PARAMETER = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 10;

	/**
	 * The feature id for the '<em><b>Gamma Distribution</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__GAMMA_DISTRIBUTION = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 11;

	/**
	 * The feature id for the '<em><b>Global</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__GLOBAL = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 12;

	/**
	 * The feature id for the '<em><b>Log Normal Distribution</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__LOG_NORMAL_DISTRIBUTION = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 13;

	/**
	 * The feature id for the '<em><b>Metadata</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__METADATA = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 14;

	/**
	 * The feature id for the '<em><b>Metaentry</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__METAENTRY = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 15;

	/**
	 * The feature id for the '<em><b>Negative Exponential Distribution</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__NEGATIVE_EXPONENTIAL_DISTRIBUTION = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 16;

	/**
	 * The feature id for the '<em><b>Normal Distribution</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__NORMAL_DISTRIBUTION = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 17;

	/**
	 * The feature id for the '<em><b>Numeric Parameter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__NUMERIC_PARAMETER = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 18;

	/**
	 * The feature id for the '<em><b>On Entry Script</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ON_ENTRY_SCRIPT = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 19;

	/**
	 * The feature id for the '<em><b>On Exit Script</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__ON_EXIT_SCRIPT = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 20;

	/**
	 * The feature id for the '<em><b>Poisson Distribution</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__POISSON_DISTRIBUTION = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 21;

	/**
	 * The feature id for the '<em><b>Process Analysis Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PROCESS_ANALYSIS_DATA = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 22;

	/**
	 * The feature id for the '<em><b>Random Distribution</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__RANDOM_DISTRIBUTION = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 23;

	/**
	 * The feature id for the '<em><b>String Parameter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__STRING_PARAMETER = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 24;

	/**
	 * The feature id for the '<em><b>Triangular Distribution</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TRIANGULAR_DISTRIBUTION = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 25;

	/**
	 * The feature id for the '<em><b>Truncated Normal Distribution</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TRUNCATED_NORMAL_DISTRIBUTION = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 26;

	/**
	 * The feature id for the '<em><b>Uniform Distribution</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__UNIFORM_DISTRIBUTION = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 27;

	/**
	 * The feature id for the '<em><b>User Distribution</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__USER_DISTRIBUTION = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 28;

	/**
	 * The feature id for the '<em><b>User Distribution Data Point</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__USER_DISTRIBUTION_DATA_POINT = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 29;

	/**
	 * The feature id for the '<em><b>Weibull Distribution</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__WEIBULL_DISTRIBUTION = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 30;

	/**
	 * The feature id for the '<em><b>Package Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PACKAGE_NAME = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 31;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__PRIORITY = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 32;

	/**
	 * The feature id for the '<em><b>Rule Flow Group</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__RULE_FLOW_GROUP = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 33;

	/**
	 * The feature id for the '<em><b>Task Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__TASK_NAME = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 34;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT__VERSION = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 35;

	/**
	 * The number of structural features of the '<em>Document Root</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DOCUMENT_ROOT_FEATURE_COUNT = Bpmn2Package.DOCUMENT_ROOT_FEATURE_COUNT + 36;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.GlobalTypeImpl <em>Global Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.GlobalTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getGlobalType()
	 * @generated
	 */
	int GLOBAL_TYPE = 5;

	/**
	 * The feature id for the '<em><b>Identifier</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_TYPE__IDENTIFIER = 0;

	/**
	 * The feature id for the '<em><b>Type</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_TYPE__TYPE = 1;

	/**
	 * The number of structural features of the '<em>Global Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GLOBAL_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ControlParametersImpl <em>Control Parameters</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ControlParametersImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getControlParameters()
	 * @generated
	 */
	int CONTROL_PARAMETERS = 6;

	/**
	 * The feature id for the '<em><b>Probability</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTROL_PARAMETERS__PROBABILITY = 0;

	/**
	 * The feature id for the '<em><b>Inter Trigger Timer</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTROL_PARAMETERS__INTER_TRIGGER_TIMER = 1;

	/**
	 * The feature id for the '<em><b>Max Trigger Count</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTROL_PARAMETERS__MAX_TRIGGER_COUNT = 2;

	/**
	 * The number of structural features of the '<em>Control Parameters</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int CONTROL_PARAMETERS_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.CostParametersImpl <em>Cost Parameters</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.CostParametersImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getCostParameters()
	 * @generated
	 */
	int COST_PARAMETERS = 8;

	/**
	 * The feature id for the '<em><b>Fixed Cost</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COST_PARAMETERS__FIXED_COST = 0;

	/**
	 * The feature id for the '<em><b>Unit Cost</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COST_PARAMETERS__UNIT_COST = 1;

	/**
	 * The feature id for the '<em><b>Currency Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COST_PARAMETERS__CURRENCY_UNIT = 2;

	/**
	 * The number of structural features of the '<em>Cost Parameters</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COST_PARAMETERS_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ImportTypeImpl <em>Import Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ImportTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getImportType()
	 * @generated
	 */
	int IMPORT_TYPE = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_TYPE__NAME = 0;

	/**
	 * The number of structural features of the '<em>Import Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int IMPORT_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DateTimeParameterTypeImpl <em>Date Time Parameter Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DateTimeParameterTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getDateTimeParameterType()
	 * @generated
	 */
	int DATE_TIME_PARAMETER_TYPE = 10;

	/**
	 * The feature id for the '<em><b>Instance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_PARAMETER_TYPE__INSTANCE = CONSTANT_PARAMETER__INSTANCE;

	/**
	 * The feature id for the '<em><b>Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_PARAMETER_TYPE__RESULT = CONSTANT_PARAMETER__RESULT;

	/**
	 * The feature id for the '<em><b>Valid For</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_PARAMETER_TYPE__VALID_FOR = CONSTANT_PARAMETER__VALID_FOR;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_PARAMETER_TYPE__VALUE = CONSTANT_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Date Time Parameter Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DATE_TIME_PARAMETER_TYPE_FEATURE_COUNT = CONSTANT_PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DecimalParameterTypeImpl <em>Decimal Parameter Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DecimalParameterTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getDecimalParameterType()
	 * @generated
	 */
	int DECIMAL_PARAMETER_TYPE = 11;

	/**
	 * The feature id for the '<em><b>Instance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECIMAL_PARAMETER_TYPE__INSTANCE = CONSTANT_PARAMETER__INSTANCE;

	/**
	 * The feature id for the '<em><b>Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECIMAL_PARAMETER_TYPE__RESULT = CONSTANT_PARAMETER__RESULT;

	/**
	 * The feature id for the '<em><b>Valid For</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECIMAL_PARAMETER_TYPE__VALID_FOR = CONSTANT_PARAMETER__VALID_FOR;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECIMAL_PARAMETER_TYPE__VALUE = CONSTANT_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Decimal Parameter Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DECIMAL_PARAMETER_TYPE_FEATURE_COUNT = CONSTANT_PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.OnEntryScriptTypeImpl <em>On Entry Script Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.OnEntryScriptTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getOnEntryScriptType()
	 * @generated
	 */
	int ON_ENTRY_SCRIPT_TYPE = 12;

	/**
	 * The feature id for the '<em><b>Script</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ON_ENTRY_SCRIPT_TYPE__SCRIPT = 0;

	/**
	 * The feature id for the '<em><b>Script Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ON_ENTRY_SCRIPT_TYPE__SCRIPT_FORMAT = 1;

	/**
	 * The number of structural features of the '<em>On Entry Script Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ON_ENTRY_SCRIPT_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ElementParametersImpl <em>Element Parameters</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ElementParametersImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getElementParameters()
	 * @generated
	 */
	int ELEMENT_PARAMETERS = 13;

	/**
	 * The feature id for the '<em><b>Time Parameters</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_PARAMETERS__TIME_PARAMETERS = 0;

	/**
	 * The feature id for the '<em><b>Control Parameters</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_PARAMETERS__CONTROL_PARAMETERS = 1;

	/**
	 * The feature id for the '<em><b>Resource Parameters</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_PARAMETERS__RESOURCE_PARAMETERS = 2;

	/**
	 * The feature id for the '<em><b>Priority Parameters</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_PARAMETERS__PRIORITY_PARAMETERS = 3;

	/**
	 * The feature id for the '<em><b>Cost Parameters</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_PARAMETERS__COST_PARAMETERS = 4;

	/**
	 * The feature id for the '<em><b>Property Parameters</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_PARAMETERS__PROPERTY_PARAMETERS = 5;

	/**
	 * The feature id for the '<em><b>Vendor Extension</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_PARAMETERS__VENDOR_EXTENSION = 6;

	/**
	 * The feature id for the '<em><b>Element Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_PARAMETERS__ELEMENT_ID = 7;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_PARAMETERS__ID = 8;

	/**
	 * The number of structural features of the '<em>Element Parameters</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ELEMENT_PARAMETERS_FEATURE_COUNT = 9;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.OnExitScriptTypeImpl <em>On Exit Script Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.OnExitScriptTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getOnExitScriptType()
	 * @generated
	 */
	int ON_EXIT_SCRIPT_TYPE = 14;

	/**
	 * The feature id for the '<em><b>Script</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ON_EXIT_SCRIPT_TYPE__SCRIPT = 0;

	/**
	 * The feature id for the '<em><b>Script Format</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ON_EXIT_SCRIPT_TYPE__SCRIPT_FORMAT = 1;

	/**
	 * The number of structural features of the '<em>On Exit Script Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ON_EXIT_SCRIPT_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DurationParameterTypeImpl <em>Duration Parameter Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DurationParameterTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getDurationParameterType()
	 * @generated
	 */
	int DURATION_PARAMETER_TYPE = 15;

	/**
	 * The feature id for the '<em><b>Instance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DURATION_PARAMETER_TYPE__INSTANCE = CONSTANT_PARAMETER__INSTANCE;

	/**
	 * The feature id for the '<em><b>Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DURATION_PARAMETER_TYPE__RESULT = CONSTANT_PARAMETER__RESULT;

	/**
	 * The feature id for the '<em><b>Valid For</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DURATION_PARAMETER_TYPE__VALID_FOR = CONSTANT_PARAMETER__VALID_FOR;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DURATION_PARAMETER_TYPE__VALUE = CONSTANT_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Duration Parameter Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int DURATION_PARAMETER_TYPE_FEATURE_COUNT = CONSTANT_PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.EnumParameterTypeImpl <em>Enum Parameter Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.EnumParameterTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getEnumParameterType()
	 * @generated
	 */
	int ENUM_PARAMETER_TYPE = 16;

	/**
	 * The feature id for the '<em><b>Instance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_PARAMETER_TYPE__INSTANCE = PARAMETER_VALUE__INSTANCE;

	/**
	 * The feature id for the '<em><b>Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_PARAMETER_TYPE__RESULT = PARAMETER_VALUE__RESULT;

	/**
	 * The feature id for the '<em><b>Valid For</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_PARAMETER_TYPE__VALID_FOR = PARAMETER_VALUE__VALID_FOR;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_PARAMETER_TYPE__GROUP = PARAMETER_VALUE_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Parameter Value Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_PARAMETER_TYPE__PARAMETER_VALUE_GROUP = PARAMETER_VALUE_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Parameter Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_PARAMETER_TYPE__PARAMETER_VALUE = PARAMETER_VALUE_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Enum Parameter Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ENUM_PARAMETER_TYPE_FEATURE_COUNT = PARAMETER_VALUE_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ErlangDistributionTypeImpl <em>Erlang Distribution Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ErlangDistributionTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getErlangDistributionType()
	 * @generated
	 */
	int ERLANG_DISTRIBUTION_TYPE = 17;

	/**
	 * The feature id for the '<em><b>Instance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERLANG_DISTRIBUTION_TYPE__INSTANCE = DISTRIBUTION_PARAMETER__INSTANCE;

	/**
	 * The feature id for the '<em><b>Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERLANG_DISTRIBUTION_TYPE__RESULT = DISTRIBUTION_PARAMETER__RESULT;

	/**
	 * The feature id for the '<em><b>Valid For</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERLANG_DISTRIBUTION_TYPE__VALID_FOR = DISTRIBUTION_PARAMETER__VALID_FOR;

	/**
	 * The feature id for the '<em><b>Discrete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERLANG_DISTRIBUTION_TYPE__DISCRETE = DISTRIBUTION_PARAMETER__DISCRETE;

	/**
	 * The feature id for the '<em><b>K</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERLANG_DISTRIBUTION_TYPE__K = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Mean</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERLANG_DISTRIBUTION_TYPE__MEAN = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Erlang Distribution Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int ERLANG_DISTRIBUTION_TYPE_FEATURE_COUNT = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ExpressionParameterTypeImpl <em>Expression Parameter Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ExpressionParameterTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getExpressionParameterType()
	 * @generated
	 */
	int EXPRESSION_PARAMETER_TYPE = 18;

	/**
	 * The feature id for the '<em><b>Instance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_PARAMETER_TYPE__INSTANCE = PARAMETER_VALUE__INSTANCE;

	/**
	 * The feature id for the '<em><b>Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_PARAMETER_TYPE__RESULT = PARAMETER_VALUE__RESULT;

	/**
	 * The feature id for the '<em><b>Valid For</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_PARAMETER_TYPE__VALID_FOR = PARAMETER_VALUE__VALID_FOR;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_PARAMETER_TYPE__VALUE = PARAMETER_VALUE_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Expression Parameter Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int EXPRESSION_PARAMETER_TYPE_FEATURE_COUNT = PARAMETER_VALUE_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.FloatingParameterTypeImpl <em>Floating Parameter Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.FloatingParameterTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getFloatingParameterType()
	 * @generated
	 */
	int FLOATING_PARAMETER_TYPE = 19;

	/**
	 * The feature id for the '<em><b>Instance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOATING_PARAMETER_TYPE__INSTANCE = CONSTANT_PARAMETER__INSTANCE;

	/**
	 * The feature id for the '<em><b>Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOATING_PARAMETER_TYPE__RESULT = CONSTANT_PARAMETER__RESULT;

	/**
	 * The feature id for the '<em><b>Valid For</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOATING_PARAMETER_TYPE__VALID_FOR = CONSTANT_PARAMETER__VALID_FOR;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOATING_PARAMETER_TYPE__VALUE = CONSTANT_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Floating Parameter Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int FLOATING_PARAMETER_TYPE_FEATURE_COUNT = CONSTANT_PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.GammaDistributionTypeImpl <em>Gamma Distribution Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.GammaDistributionTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getGammaDistributionType()
	 * @generated
	 */
	int GAMMA_DISTRIBUTION_TYPE = 20;

	/**
	 * The feature id for the '<em><b>Instance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GAMMA_DISTRIBUTION_TYPE__INSTANCE = DISTRIBUTION_PARAMETER__INSTANCE;

	/**
	 * The feature id for the '<em><b>Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GAMMA_DISTRIBUTION_TYPE__RESULT = DISTRIBUTION_PARAMETER__RESULT;

	/**
	 * The feature id for the '<em><b>Valid For</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GAMMA_DISTRIBUTION_TYPE__VALID_FOR = DISTRIBUTION_PARAMETER__VALID_FOR;

	/**
	 * The feature id for the '<em><b>Discrete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GAMMA_DISTRIBUTION_TYPE__DISCRETE = DISTRIBUTION_PARAMETER__DISCRETE;

	/**
	 * The feature id for the '<em><b>Scale</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GAMMA_DISTRIBUTION_TYPE__SCALE = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Shape</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GAMMA_DISTRIBUTION_TYPE__SHAPE = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Gamma Distribution Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int GAMMA_DISTRIBUTION_TYPE_FEATURE_COUNT = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.LogNormalDistributionTypeImpl <em>Log Normal Distribution Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.LogNormalDistributionTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getLogNormalDistributionType()
	 * @generated
	 */
	int LOG_NORMAL_DISTRIBUTION_TYPE = 21;

	/**
	 * The feature id for the '<em><b>Instance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOG_NORMAL_DISTRIBUTION_TYPE__INSTANCE = DISTRIBUTION_PARAMETER__INSTANCE;

	/**
	 * The feature id for the '<em><b>Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOG_NORMAL_DISTRIBUTION_TYPE__RESULT = DISTRIBUTION_PARAMETER__RESULT;

	/**
	 * The feature id for the '<em><b>Valid For</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOG_NORMAL_DISTRIBUTION_TYPE__VALID_FOR = DISTRIBUTION_PARAMETER__VALID_FOR;

	/**
	 * The feature id for the '<em><b>Discrete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOG_NORMAL_DISTRIBUTION_TYPE__DISCRETE = DISTRIBUTION_PARAMETER__DISCRETE;

	/**
	 * The feature id for the '<em><b>Mean</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOG_NORMAL_DISTRIBUTION_TYPE__MEAN = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Standard Deviation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOG_NORMAL_DISTRIBUTION_TYPE__STANDARD_DEVIATION = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Log Normal Distribution Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int LOG_NORMAL_DISTRIBUTION_TYPE_FEATURE_COUNT = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.MetadataTypeImpl <em>Metadata Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.MetadataTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getMetadataType()
	 * @generated
	 */
	int METADATA_TYPE = 22;

	/**
	 * The feature id for the '<em><b>Metaentry</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA_TYPE__METAENTRY = 0;

	/**
	 * The number of structural features of the '<em>Metadata Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METADATA_TYPE_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.NegativeExponentialDistributionTypeImpl <em>Negative Exponential Distribution Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.NegativeExponentialDistributionTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getNegativeExponentialDistributionType()
	 * @generated
	 */
	int NEGATIVE_EXPONENTIAL_DISTRIBUTION_TYPE = 23;

	/**
	 * The feature id for the '<em><b>Instance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEGATIVE_EXPONENTIAL_DISTRIBUTION_TYPE__INSTANCE = DISTRIBUTION_PARAMETER__INSTANCE;

	/**
	 * The feature id for the '<em><b>Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEGATIVE_EXPONENTIAL_DISTRIBUTION_TYPE__RESULT = DISTRIBUTION_PARAMETER__RESULT;

	/**
	 * The feature id for the '<em><b>Valid For</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEGATIVE_EXPONENTIAL_DISTRIBUTION_TYPE__VALID_FOR = DISTRIBUTION_PARAMETER__VALID_FOR;

	/**
	 * The feature id for the '<em><b>Discrete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEGATIVE_EXPONENTIAL_DISTRIBUTION_TYPE__DISCRETE = DISTRIBUTION_PARAMETER__DISCRETE;

	/**
	 * The feature id for the '<em><b>Mean</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEGATIVE_EXPONENTIAL_DISTRIBUTION_TYPE__MEAN = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Negative Exponential Distribution Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NEGATIVE_EXPONENTIAL_DISTRIBUTION_TYPE_FEATURE_COUNT = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.NormalDistributionTypeImpl <em>Normal Distribution Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.NormalDistributionTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getNormalDistributionType()
	 * @generated
	 */
	int NORMAL_DISTRIBUTION_TYPE = 24;

	/**
	 * The feature id for the '<em><b>Instance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NORMAL_DISTRIBUTION_TYPE__INSTANCE = DISTRIBUTION_PARAMETER__INSTANCE;

	/**
	 * The feature id for the '<em><b>Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NORMAL_DISTRIBUTION_TYPE__RESULT = DISTRIBUTION_PARAMETER__RESULT;

	/**
	 * The feature id for the '<em><b>Valid For</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NORMAL_DISTRIBUTION_TYPE__VALID_FOR = DISTRIBUTION_PARAMETER__VALID_FOR;

	/**
	 * The feature id for the '<em><b>Discrete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NORMAL_DISTRIBUTION_TYPE__DISCRETE = DISTRIBUTION_PARAMETER__DISCRETE;

	/**
	 * The feature id for the '<em><b>Mean</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NORMAL_DISTRIBUTION_TYPE__MEAN = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Standard Deviation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NORMAL_DISTRIBUTION_TYPE__STANDARD_DEVIATION = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Normal Distribution Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NORMAL_DISTRIBUTION_TYPE_FEATURE_COUNT = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ParameterImpl <em>Parameter</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ParameterImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getParameter()
	 * @generated
	 */
	int PARAMETER = 25;

	/**
	 * The feature id for the '<em><b>Result Request</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__RESULT_REQUEST = 0;

	/**
	 * The feature id for the '<em><b>Parameter Value Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__PARAMETER_VALUE_GROUP = 1;

	/**
	 * The feature id for the '<em><b>Parameter Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__PARAMETER_VALUE = 2;

	/**
	 * The feature id for the '<em><b>Kpi</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__KPI = 3;

	/**
	 * The feature id for the '<em><b>Sla</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER__SLA = 4;

	/**
	 * The number of structural features of the '<em>Parameter</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PARAMETER_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.NumericParameterTypeImpl <em>Numeric Parameter Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.NumericParameterTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getNumericParameterType()
	 * @generated
	 */
	int NUMERIC_PARAMETER_TYPE = 26;

	/**
	 * The feature id for the '<em><b>Instance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_PARAMETER_TYPE__INSTANCE = CONSTANT_PARAMETER__INSTANCE;

	/**
	 * The feature id for the '<em><b>Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_PARAMETER_TYPE__RESULT = CONSTANT_PARAMETER__RESULT;

	/**
	 * The feature id for the '<em><b>Valid For</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_PARAMETER_TYPE__VALID_FOR = CONSTANT_PARAMETER__VALID_FOR;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_PARAMETER_TYPE__VALUE = CONSTANT_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Numeric Parameter Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int NUMERIC_PARAMETER_TYPE_FEATURE_COUNT = CONSTANT_PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.MetaentryTypeImpl <em>Metaentry Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.MetaentryTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getMetaentryType()
	 * @generated
	 */
	int METAENTRY_TYPE = 27;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METAENTRY_TYPE__NAME = 0;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METAENTRY_TYPE__VALUE = 1;

	/**
	 * The number of structural features of the '<em>Metaentry Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int METAENTRY_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ProcessAnalysisDataTypeImpl <em>Process Analysis Data Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ProcessAnalysisDataTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getProcessAnalysisDataType()
	 * @generated
	 */
	int PROCESS_ANALYSIS_DATA_TYPE = 29;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ANALYSIS_DATA_TYPE__GROUP = 0;

	/**
	 * The feature id for the '<em><b>Scenario</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ANALYSIS_DATA_TYPE__SCENARIO = 1;

	/**
	 * The number of structural features of the '<em>Process Analysis Data Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROCESS_ANALYSIS_DATA_TYPE_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.PropertyParametersImpl <em>Property Parameters</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.PropertyParametersImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getPropertyParameters()
	 * @generated
	 */
	int PROPERTY_PARAMETERS = 30;

	/**
	 * The feature id for the '<em><b>Property</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_PARAMETERS__PROPERTY = 0;

	/**
	 * The number of structural features of the '<em>Property Parameters</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_PARAMETERS_FEATURE_COUNT = 1;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.RandomDistributionTypeImpl <em>Random Distribution Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.RandomDistributionTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getRandomDistributionType()
	 * @generated
	 */
	int RANDOM_DISTRIBUTION_TYPE = 31;

	/**
	 * The feature id for the '<em><b>Instance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANDOM_DISTRIBUTION_TYPE__INSTANCE = DISTRIBUTION_PARAMETER__INSTANCE;

	/**
	 * The feature id for the '<em><b>Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANDOM_DISTRIBUTION_TYPE__RESULT = DISTRIBUTION_PARAMETER__RESULT;

	/**
	 * The feature id for the '<em><b>Valid For</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANDOM_DISTRIBUTION_TYPE__VALID_FOR = DISTRIBUTION_PARAMETER__VALID_FOR;

	/**
	 * The feature id for the '<em><b>Discrete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANDOM_DISTRIBUTION_TYPE__DISCRETE = DISTRIBUTION_PARAMETER__DISCRETE;

	/**
	 * The feature id for the '<em><b>Max</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANDOM_DISTRIBUTION_TYPE__MAX = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Min</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANDOM_DISTRIBUTION_TYPE__MIN = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Random Distribution Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RANDOM_DISTRIBUTION_TYPE_FEATURE_COUNT = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.PoissonDistributionTypeImpl <em>Poisson Distribution Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.PoissonDistributionTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getPoissonDistributionType()
	 * @generated
	 */
	int POISSON_DISTRIBUTION_TYPE = 33;

	/**
	 * The feature id for the '<em><b>Instance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POISSON_DISTRIBUTION_TYPE__INSTANCE = DISTRIBUTION_PARAMETER__INSTANCE;

	/**
	 * The feature id for the '<em><b>Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POISSON_DISTRIBUTION_TYPE__RESULT = DISTRIBUTION_PARAMETER__RESULT;

	/**
	 * The feature id for the '<em><b>Valid For</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POISSON_DISTRIBUTION_TYPE__VALID_FOR = DISTRIBUTION_PARAMETER__VALID_FOR;

	/**
	 * The feature id for the '<em><b>Discrete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POISSON_DISTRIBUTION_TYPE__DISCRETE = DISTRIBUTION_PARAMETER__DISCRETE;

	/**
	 * The feature id for the '<em><b>Mean</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POISSON_DISTRIBUTION_TYPE__MEAN = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Poisson Distribution Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int POISSON_DISTRIBUTION_TYPE_FEATURE_COUNT = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.PropertyTypeImpl <em>Property Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.PropertyTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getPropertyType()
	 * @generated
	 */
	int PROPERTY_TYPE = 34;

	/**
	 * The feature id for the '<em><b>Result Request</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_TYPE__RESULT_REQUEST = PARAMETER__RESULT_REQUEST;

	/**
	 * The feature id for the '<em><b>Parameter Value Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_TYPE__PARAMETER_VALUE_GROUP = PARAMETER__PARAMETER_VALUE_GROUP;

	/**
	 * The feature id for the '<em><b>Parameter Value</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_TYPE__PARAMETER_VALUE = PARAMETER__PARAMETER_VALUE;

	/**
	 * The feature id for the '<em><b>Kpi</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_TYPE__KPI = PARAMETER__KPI;

	/**
	 * The feature id for the '<em><b>Sla</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_TYPE__SLA = PARAMETER__SLA;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_TYPE__NAME = PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>Property Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PROPERTY_TYPE_FEATURE_COUNT = PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.PriorityParametersImpl <em>Priority Parameters</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.PriorityParametersImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getPriorityParameters()
	 * @generated
	 */
	int PRIORITY_PARAMETERS = 35;

	/**
	 * The feature id for the '<em><b>Interruptible</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIORITY_PARAMETERS__INTERRUPTIBLE = 0;

	/**
	 * The feature id for the '<em><b>Priority</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIORITY_PARAMETERS__PRIORITY = 1;

	/**
	 * The number of structural features of the '<em>Priority Parameters</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int PRIORITY_PARAMETERS_FEATURE_COUNT = 2;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.TimeParametersImpl <em>Time Parameters</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.TimeParametersImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getTimeParameters()
	 * @generated
	 */
	int TIME_PARAMETERS = 36;

	/**
	 * The feature id for the '<em><b>Transfer Time</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_PARAMETERS__TRANSFER_TIME = 0;

	/**
	 * The feature id for the '<em><b>Queue Time</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_PARAMETERS__QUEUE_TIME = 1;

	/**
	 * The feature id for the '<em><b>Wait Time</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_PARAMETERS__WAIT_TIME = 2;

	/**
	 * The feature id for the '<em><b>Set Up Time</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_PARAMETERS__SET_UP_TIME = 3;

	/**
	 * The feature id for the '<em><b>Processing Time</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_PARAMETERS__PROCESSING_TIME = 4;

	/**
	 * The feature id for the '<em><b>Validation Time</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_PARAMETERS__VALIDATION_TIME = 5;

	/**
	 * The feature id for the '<em><b>Rework Time</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_PARAMETERS__REWORK_TIME = 6;

	/**
	 * The feature id for the '<em><b>Time Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_PARAMETERS__TIME_UNIT = 7;

	/**
	 * The number of structural features of the '<em>Time Parameters</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TIME_PARAMETERS_FEATURE_COUNT = 8;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.StringParameterTypeImpl <em>String Parameter Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.StringParameterTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getStringParameterType()
	 * @generated
	 */
	int STRING_PARAMETER_TYPE = 37;

	/**
	 * The feature id for the '<em><b>Instance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_PARAMETER_TYPE__INSTANCE = CONSTANT_PARAMETER__INSTANCE;

	/**
	 * The feature id for the '<em><b>Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_PARAMETER_TYPE__RESULT = CONSTANT_PARAMETER__RESULT;

	/**
	 * The feature id for the '<em><b>Valid For</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_PARAMETER_TYPE__VALID_FOR = CONSTANT_PARAMETER__VALID_FOR;

	/**
	 * The feature id for the '<em><b>Value</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_PARAMETER_TYPE__VALUE = CONSTANT_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The number of structural features of the '<em>String Parameter Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int STRING_PARAMETER_TYPE_FEATURE_COUNT = CONSTANT_PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ScenarioImpl <em>Scenario</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ScenarioImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getScenario()
	 * @generated
	 */
	int SCENARIO = 38;

	/**
	 * The feature id for the '<em><b>Scenario Parameters</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCENARIO__SCENARIO_PARAMETERS = 0;

	/**
	 * The feature id for the '<em><b>Element Parameters</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCENARIO__ELEMENT_PARAMETERS = 1;

	/**
	 * The feature id for the '<em><b>Calendar</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCENARIO__CALENDAR = 2;

	/**
	 * The feature id for the '<em><b>Vendor Extension</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCENARIO__VENDOR_EXTENSION = 3;

	/**
	 * The feature id for the '<em><b>Author</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCENARIO__AUTHOR = 4;

	/**
	 * The feature id for the '<em><b>Created</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCENARIO__CREATED = 5;

	/**
	 * The feature id for the '<em><b>Description</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCENARIO__DESCRIPTION = 6;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCENARIO__ID = 7;

	/**
	 * The feature id for the '<em><b>Inherits</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCENARIO__INHERITS = 8;

	/**
	 * The feature id for the '<em><b>Modified</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCENARIO__MODIFIED = 9;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCENARIO__NAME = 10;

	/**
	 * The feature id for the '<em><b>Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCENARIO__RESULT = 11;

	/**
	 * The feature id for the '<em><b>Vendor</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCENARIO__VENDOR = 12;

	/**
	 * The feature id for the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCENARIO__VERSION = 13;

	/**
	 * The number of structural features of the '<em>Scenario</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCENARIO_FEATURE_COUNT = 14;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ResourceParametersImpl <em>Resource Parameters</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ResourceParametersImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getResourceParameters()
	 * @generated
	 */
	int RESOURCE_PARAMETERS = 39;

	/**
	 * The feature id for the '<em><b>Selection</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_PARAMETERS__SELECTION = 0;

	/**
	 * The feature id for the '<em><b>Availability</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_PARAMETERS__AVAILABILITY = 1;

	/**
	 * The feature id for the '<em><b>Quantity</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_PARAMETERS__QUANTITY = 2;

	/**
	 * The feature id for the '<em><b>Workinghours</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_PARAMETERS__WORKINGHOURS = 3;

	/**
	 * The feature id for the '<em><b>Role</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_PARAMETERS__ROLE = 4;

	/**
	 * The number of structural features of the '<em>Resource Parameters</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int RESOURCE_PARAMETERS_FEATURE_COUNT = 5;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ScenarioParametersImpl <em>Scenario Parameters</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ScenarioParametersImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getScenarioParameters()
	 * @generated
	 */
	int SCENARIO_PARAMETERS = 40;

	/**
	 * The feature id for the '<em><b>Start</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCENARIO_PARAMETERS__START = 0;

	/**
	 * The feature id for the '<em><b>Duration</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCENARIO_PARAMETERS__DURATION = 1;

	/**
	 * The feature id for the '<em><b>Property Parameters</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCENARIO_PARAMETERS__PROPERTY_PARAMETERS = 2;

	/**
	 * The feature id for the '<em><b>Base Currency Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCENARIO_PARAMETERS__BASE_CURRENCY_UNIT = 3;

	/**
	 * The feature id for the '<em><b>Base Time Unit</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCENARIO_PARAMETERS__BASE_TIME_UNIT = 4;

	/**
	 * The feature id for the '<em><b>Replication</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCENARIO_PARAMETERS__REPLICATION = 5;

	/**
	 * The feature id for the '<em><b>Seed</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCENARIO_PARAMETERS__SEED = 6;

	/**
	 * The number of structural features of the '<em>Scenario Parameters</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SCENARIO_PARAMETERS_FEATURE_COUNT = 7;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.VendorExtensionImpl <em>Vendor Extension</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.VendorExtensionImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getVendorExtension()
	 * @generated
	 */
	int VENDOR_EXTENSION = 41;

	/**
	 * The feature id for the '<em><b>Any</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VENDOR_EXTENSION__ANY = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VENDOR_EXTENSION__NAME = 1;

	/**
	 * The feature id for the '<em><b>Any Attribute</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VENDOR_EXTENSION__ANY_ATTRIBUTE = 2;

	/**
	 * The number of structural features of the '<em>Vendor Extension</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int VENDOR_EXTENSION_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.TriangularDistributionTypeImpl <em>Triangular Distribution Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.TriangularDistributionTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getTriangularDistributionType()
	 * @generated
	 */
	int TRIANGULAR_DISTRIBUTION_TYPE = 42;

	/**
	 * The feature id for the '<em><b>Instance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRIANGULAR_DISTRIBUTION_TYPE__INSTANCE = DISTRIBUTION_PARAMETER__INSTANCE;

	/**
	 * The feature id for the '<em><b>Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRIANGULAR_DISTRIBUTION_TYPE__RESULT = DISTRIBUTION_PARAMETER__RESULT;

	/**
	 * The feature id for the '<em><b>Valid For</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRIANGULAR_DISTRIBUTION_TYPE__VALID_FOR = DISTRIBUTION_PARAMETER__VALID_FOR;

	/**
	 * The feature id for the '<em><b>Discrete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRIANGULAR_DISTRIBUTION_TYPE__DISCRETE = DISTRIBUTION_PARAMETER__DISCRETE;

	/**
	 * The feature id for the '<em><b>Max</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRIANGULAR_DISTRIBUTION_TYPE__MAX = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Min</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRIANGULAR_DISTRIBUTION_TYPE__MIN = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Most Likely</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRIANGULAR_DISTRIBUTION_TYPE__MOST_LIKELY = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 2;

	/**
	 * The number of structural features of the '<em>Triangular Distribution Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRIANGULAR_DISTRIBUTION_TYPE_FEATURE_COUNT = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 3;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.TruncatedNormalDistributionTypeImpl <em>Truncated Normal Distribution Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.TruncatedNormalDistributionTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getTruncatedNormalDistributionType()
	 * @generated
	 */
	int TRUNCATED_NORMAL_DISTRIBUTION_TYPE = 43;

	/**
	 * The feature id for the '<em><b>Instance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRUNCATED_NORMAL_DISTRIBUTION_TYPE__INSTANCE = DISTRIBUTION_PARAMETER__INSTANCE;

	/**
	 * The feature id for the '<em><b>Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRUNCATED_NORMAL_DISTRIBUTION_TYPE__RESULT = DISTRIBUTION_PARAMETER__RESULT;

	/**
	 * The feature id for the '<em><b>Valid For</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRUNCATED_NORMAL_DISTRIBUTION_TYPE__VALID_FOR = DISTRIBUTION_PARAMETER__VALID_FOR;

	/**
	 * The feature id for the '<em><b>Discrete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRUNCATED_NORMAL_DISTRIBUTION_TYPE__DISCRETE = DISTRIBUTION_PARAMETER__DISCRETE;

	/**
	 * The feature id for the '<em><b>Max</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRUNCATED_NORMAL_DISTRIBUTION_TYPE__MAX = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Mean</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRUNCATED_NORMAL_DISTRIBUTION_TYPE__MEAN = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The feature id for the '<em><b>Min</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRUNCATED_NORMAL_DISTRIBUTION_TYPE__MIN = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 2;

	/**
	 * The feature id for the '<em><b>Standard Deviation</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRUNCATED_NORMAL_DISTRIBUTION_TYPE__STANDARD_DEVIATION = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 3;

	/**
	 * The number of structural features of the '<em>Truncated Normal Distribution Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int TRUNCATED_NORMAL_DISTRIBUTION_TYPE_FEATURE_COUNT = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 4;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.UniformDistributionTypeImpl <em>Uniform Distribution Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.UniformDistributionTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getUniformDistributionType()
	 * @generated
	 */
	int UNIFORM_DISTRIBUTION_TYPE = 44;

	/**
	 * The feature id for the '<em><b>Instance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIFORM_DISTRIBUTION_TYPE__INSTANCE = DISTRIBUTION_PARAMETER__INSTANCE;

	/**
	 * The feature id for the '<em><b>Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIFORM_DISTRIBUTION_TYPE__RESULT = DISTRIBUTION_PARAMETER__RESULT;

	/**
	 * The feature id for the '<em><b>Valid For</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIFORM_DISTRIBUTION_TYPE__VALID_FOR = DISTRIBUTION_PARAMETER__VALID_FOR;

	/**
	 * The feature id for the '<em><b>Discrete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIFORM_DISTRIBUTION_TYPE__DISCRETE = DISTRIBUTION_PARAMETER__DISCRETE;

	/**
	 * The feature id for the '<em><b>Max</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIFORM_DISTRIBUTION_TYPE__MAX = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Min</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIFORM_DISTRIBUTION_TYPE__MIN = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Uniform Distribution Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int UNIFORM_DISTRIBUTION_TYPE_FEATURE_COUNT = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.UserDistributionDataPointTypeImpl <em>User Distribution Data Point Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.UserDistributionDataPointTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getUserDistributionDataPointType()
	 * @generated
	 */
	int USER_DISTRIBUTION_DATA_POINT_TYPE = 45;

	/**
	 * The feature id for the '<em><b>Parameter Value Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_DISTRIBUTION_DATA_POINT_TYPE__PARAMETER_VALUE_GROUP = 0;

	/**
	 * The feature id for the '<em><b>Parameter Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_DISTRIBUTION_DATA_POINT_TYPE__PARAMETER_VALUE = 1;

	/**
	 * The feature id for the '<em><b>Probability</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_DISTRIBUTION_DATA_POINT_TYPE__PROBABILITY = 2;

	/**
	 * The number of structural features of the '<em>User Distribution Data Point Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_DISTRIBUTION_DATA_POINT_TYPE_FEATURE_COUNT = 3;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.UserDistributionTypeImpl <em>User Distribution Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.UserDistributionTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getUserDistributionType()
	 * @generated
	 */
	int USER_DISTRIBUTION_TYPE = 46;

	/**
	 * The feature id for the '<em><b>Instance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_DISTRIBUTION_TYPE__INSTANCE = DISTRIBUTION_PARAMETER__INSTANCE;

	/**
	 * The feature id for the '<em><b>Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_DISTRIBUTION_TYPE__RESULT = DISTRIBUTION_PARAMETER__RESULT;

	/**
	 * The feature id for the '<em><b>Valid For</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_DISTRIBUTION_TYPE__VALID_FOR = DISTRIBUTION_PARAMETER__VALID_FOR;

	/**
	 * The feature id for the '<em><b>Discrete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_DISTRIBUTION_TYPE__DISCRETE = DISTRIBUTION_PARAMETER__DISCRETE;

	/**
	 * The feature id for the '<em><b>Group</b></em>' attribute list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_DISTRIBUTION_TYPE__GROUP = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>User Distribution Data Point</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_DISTRIBUTION_TYPE__USER_DISTRIBUTION_DATA_POINT = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>User Distribution Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int USER_DISTRIBUTION_TYPE_FEATURE_COUNT = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.WeibullDistributionTypeImpl <em>Weibull Distribution Type</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.WeibullDistributionTypeImpl
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getWeibullDistributionType()
	 * @generated
	 */
	int WEIBULL_DISTRIBUTION_TYPE = 47;

	/**
	 * The feature id for the '<em><b>Instance</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIBULL_DISTRIBUTION_TYPE__INSTANCE = DISTRIBUTION_PARAMETER__INSTANCE;

	/**
	 * The feature id for the '<em><b>Result</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIBULL_DISTRIBUTION_TYPE__RESULT = DISTRIBUTION_PARAMETER__RESULT;

	/**
	 * The feature id for the '<em><b>Valid For</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIBULL_DISTRIBUTION_TYPE__VALID_FOR = DISTRIBUTION_PARAMETER__VALID_FOR;

	/**
	 * The feature id for the '<em><b>Discrete</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIBULL_DISTRIBUTION_TYPE__DISCRETE = DISTRIBUTION_PARAMETER__DISCRETE;

	/**
	 * The feature id for the '<em><b>Scale</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIBULL_DISTRIBUTION_TYPE__SCALE = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 0;

	/**
	 * The feature id for the '<em><b>Shape</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIBULL_DISTRIBUTION_TYPE__SHAPE = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 1;

	/**
	 * The number of structural features of the '<em>Weibull Distribution Type</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int WEIBULL_DISTRIBUTION_TYPE_FEATURE_COUNT = DISTRIBUTION_PARAMETER_FEATURE_COUNT + 2;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ResultType <em>Result Type</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ResultType
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getResultType()
	 * @generated
	 */
	int RESULT_TYPE = 48;

	/**
	 * The meta object id for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TimeUnit <em>Time Unit</em>}' enum.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TimeUnit
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getTimeUnit()
	 * @generated
	 */
	int TIME_UNIT = 49;

	/**
	 * The meta object id for the '<em>Package Name Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getPackageNameType()
	 * @generated
	 */
	int PACKAGE_NAME_TYPE = 50;

	/**
	 * The meta object id for the '<em>Priority Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.math.BigInteger
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getPriorityType()
	 * @generated
	 */
	int PRIORITY_TYPE = 51;

	/**
	 * The meta object id for the '<em>Rule Flow Group Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getRuleFlowGroupType()
	 * @generated
	 */
	int RULE_FLOW_GROUP_TYPE = 52;

	/**
	 * The meta object id for the '<em>Task Name Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getTaskNameType()
	 * @generated
	 */
	int TASK_NAME_TYPE = 53;

	/**
	 * The meta object id for the '<em>Version Type</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see java.lang.String
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getVersionType()
	 * @generated
	 */
	int VERSION_TYPE = 54;


	/**
	 * The meta object id for the '<em>Result Type Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ResultType
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getResultTypeObject()
	 * @generated
	 */
	int RESULT_TYPE_OBJECT = 55;

	/**
	 * The meta object id for the '<em>Time Unit Object</em>' data type.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TimeUnit
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getTimeUnitObject()
	 * @generated
	 */
	int TIME_UNIT_OBJECT = 56;


	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.BetaDistributionType <em>Beta Distribution Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Beta Distribution Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.BetaDistributionType
	 * @generated
	 */
	EClass getBetaDistributionType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.BetaDistributionType#getScale <em>Scale</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Scale</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.BetaDistributionType#getScale()
	 * @see #getBetaDistributionType()
	 * @generated
	 */
	EAttribute getBetaDistributionType_Scale();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.BetaDistributionType#getShape <em>Shape</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Shape</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.BetaDistributionType#getShape()
	 * @see #getBetaDistributionType()
	 * @generated
	 */
	EAttribute getBetaDistributionType_Shape();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.BinomialDistributionType <em>Binomial Distribution Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Binomial Distribution Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.BinomialDistributionType
	 * @generated
	 */
	EClass getBinomialDistributionType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.BinomialDistributionType#getProbability <em>Probability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Probability</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.BinomialDistributionType#getProbability()
	 * @see #getBinomialDistributionType()
	 * @generated
	 */
	EAttribute getBinomialDistributionType_Probability();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.BinomialDistributionType#getTrials <em>Trials</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Trials</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.BinomialDistributionType#getTrials()
	 * @see #getBinomialDistributionType()
	 * @generated
	 */
	EAttribute getBinomialDistributionType_Trials();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.BooleanParameterType <em>Boolean Parameter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Boolean Parameter Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.BooleanParameterType
	 * @generated
	 */
	EClass getBooleanParameterType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.BooleanParameterType#isValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.BooleanParameterType#isValue()
	 * @see #getBooleanParameterType()
	 * @generated
	 */
	EAttribute getBooleanParameterType_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Calendar <em>Calendar</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Calendar</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Calendar
	 * @generated
	 */
	EClass getCalendar();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Calendar#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Calendar#getValue()
	 * @see #getCalendar()
	 * @generated
	 */
	EAttribute getCalendar_Value();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Calendar#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Calendar#getId()
	 * @see #getCalendar()
	 * @generated
	 */
	EAttribute getCalendar_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Calendar#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Calendar#getName()
	 * @see #getCalendar()
	 * @generated
	 */
	EAttribute getCalendar_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot <em>Document Root</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Document Root</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot
	 * @generated
	 */
	EClass getDocumentRoot();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getBetaDistribution <em>Beta Distribution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Beta Distribution</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getBetaDistribution()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_BetaDistribution();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getParameterValue <em>Parameter Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Parameter Value</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getParameterValue()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ParameterValue();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getBinomialDistribution <em>Binomial Distribution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Binomial Distribution</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getBinomialDistribution()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_BinomialDistribution();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getBooleanParameter <em>Boolean Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Boolean Parameter</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getBooleanParameter()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_BooleanParameter();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getDateTimeParameter <em>Date Time Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Date Time Parameter</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getDateTimeParameter()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_DateTimeParameter();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getDecimalParameter <em>Decimal Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Decimal Parameter</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getDecimalParameter()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_DecimalParameter();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getDurationParameter <em>Duration Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Duration Parameter</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getDurationParameter()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_DurationParameter();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getEnumParameter <em>Enum Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Enum Parameter</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getEnumParameter()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_EnumParameter();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getErlangDistribution <em>Erlang Distribution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Erlang Distribution</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getErlangDistribution()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ErlangDistribution();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getExpressionParameter <em>Expression Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Expression Parameter</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getExpressionParameter()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ExpressionParameter();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getFloatingParameter <em>Floating Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Floating Parameter</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getFloatingParameter()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_FloatingParameter();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getGammaDistribution <em>Gamma Distribution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Gamma Distribution</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getGammaDistribution()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_GammaDistribution();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getGlobal <em>Global</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Global</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getGlobal()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Global();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getLogNormalDistribution <em>Log Normal Distribution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Log Normal Distribution</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getLogNormalDistribution()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_LogNormalDistribution();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getMetadata <em>Metadata</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Metadata</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getMetadata()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Metadata();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getMetaentry <em>Metaentry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Metaentry</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getMetaentry()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_Metaentry();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getNegativeExponentialDistribution <em>Negative Exponential Distribution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Negative Exponential Distribution</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getNegativeExponentialDistribution()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_NegativeExponentialDistribution();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getNormalDistribution <em>Normal Distribution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Normal Distribution</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getNormalDistribution()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_NormalDistribution();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getNumericParameter <em>Numeric Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Numeric Parameter</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getNumericParameter()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_NumericParameter();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getOnEntryScript <em>On Entry Script</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>On Entry Script</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getOnEntryScript()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_OnEntryScript();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getOnExitScript <em>On Exit Script</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>On Exit Script</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getOnExitScript()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_OnExitScript();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getPoissonDistribution <em>Poisson Distribution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Poisson Distribution</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getPoissonDistribution()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_PoissonDistribution();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getProcessAnalysisData <em>Process Analysis Data</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Process Analysis Data</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getProcessAnalysisData()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_ProcessAnalysisData();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getRandomDistribution <em>Random Distribution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Random Distribution</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getRandomDistribution()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_RandomDistribution();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getStringParameter <em>String Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>String Parameter</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getStringParameter()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_StringParameter();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getTriangularDistribution <em>Triangular Distribution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Triangular Distribution</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getTriangularDistribution()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_TriangularDistribution();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getTruncatedNormalDistribution <em>Truncated Normal Distribution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Truncated Normal Distribution</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getTruncatedNormalDistribution()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_TruncatedNormalDistribution();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getUniformDistribution <em>Uniform Distribution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Uniform Distribution</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getUniformDistribution()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_UniformDistribution();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getUserDistribution <em>User Distribution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>User Distribution</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getUserDistribution()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_UserDistribution();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getUserDistributionDataPoint <em>User Distribution Data Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>User Distribution Data Point</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getUserDistributionDataPoint()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_UserDistributionDataPoint();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getWeibullDistribution <em>Weibull Distribution</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Weibull Distribution</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getWeibullDistribution()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EReference getDocumentRoot_WeibullDistribution();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getPackageName <em>Package Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Package Name</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getPackageName()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_PackageName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getPriority <em>Priority</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Priority</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getPriority()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Priority();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getRuleFlowGroup <em>Rule Flow Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Rule Flow Group</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getRuleFlowGroup()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_RuleFlowGroup();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getTaskName <em>Task Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Task Name</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getTaskName()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_TaskName();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getVersion()
	 * @see #getDocumentRoot()
	 * @generated
	 */
	EAttribute getDocumentRoot_Version();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.GlobalType <em>Global Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Global Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.GlobalType
	 * @generated
	 */
	EClass getGlobalType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.GlobalType#getIdentifier <em>Identifier</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Identifier</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.GlobalType#getIdentifier()
	 * @see #getGlobalType()
	 * @generated
	 */
	EAttribute getGlobalType_Identifier();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.GlobalType#getType <em>Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.GlobalType#getType()
	 * @see #getGlobalType()
	 * @generated
	 */
	EAttribute getGlobalType_Type();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ControlParameters <em>Control Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Control Parameters</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ControlParameters
	 * @generated
	 */
	EClass getControlParameters();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ControlParameters#getProbability <em>Probability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Probability</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ControlParameters#getProbability()
	 * @see #getControlParameters()
	 * @generated
	 */
	EReference getControlParameters_Probability();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ControlParameters#getInterTriggerTimer <em>Inter Trigger Timer</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Inter Trigger Timer</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ControlParameters#getInterTriggerTimer()
	 * @see #getControlParameters()
	 * @generated
	 */
	EReference getControlParameters_InterTriggerTimer();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ControlParameters#getMaxTriggerCount <em>Max Trigger Count</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Max Trigger Count</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ControlParameters#getMaxTriggerCount()
	 * @see #getControlParameters()
	 * @generated
	 */
	EReference getControlParameters_MaxTriggerCount();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ConstantParameter <em>Constant Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Constant Parameter</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ConstantParameter
	 * @generated
	 */
	EClass getConstantParameter();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.CostParameters <em>Cost Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Cost Parameters</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.CostParameters
	 * @generated
	 */
	EClass getCostParameters();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.CostParameters#getFixedCost <em>Fixed Cost</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Fixed Cost</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.CostParameters#getFixedCost()
	 * @see #getCostParameters()
	 * @generated
	 */
	EReference getCostParameters_FixedCost();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.CostParameters#getUnitCost <em>Unit Cost</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Unit Cost</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.CostParameters#getUnitCost()
	 * @see #getCostParameters()
	 * @generated
	 */
	EReference getCostParameters_UnitCost();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.CostParameters#getCurrencyUnit <em>Currency Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Currency Unit</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.CostParameters#getCurrencyUnit()
	 * @see #getCostParameters()
	 * @generated
	 */
	EAttribute getCostParameters_CurrencyUnit();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ImportType <em>Import Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Import Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ImportType
	 * @generated
	 */
	EClass getImportType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ImportType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ImportType#getName()
	 * @see #getImportType()
	 * @generated
	 */
	EAttribute getImportType_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DateTimeParameterType <em>Date Time Parameter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Date Time Parameter Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DateTimeParameterType
	 * @generated
	 */
	EClass getDateTimeParameterType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DateTimeParameterType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DateTimeParameterType#getValue()
	 * @see #getDateTimeParameterType()
	 * @generated
	 */
	EAttribute getDateTimeParameterType_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DecimalParameterType <em>Decimal Parameter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Decimal Parameter Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DecimalParameterType
	 * @generated
	 */
	EClass getDecimalParameterType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DecimalParameterType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DecimalParameterType#getValue()
	 * @see #getDecimalParameterType()
	 * @generated
	 */
	EAttribute getDecimalParameterType_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.OnEntryScriptType <em>On Entry Script Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>On Entry Script Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.OnEntryScriptType
	 * @generated
	 */
	EClass getOnEntryScriptType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.OnEntryScriptType#getScript <em>Script</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Script</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.OnEntryScriptType#getScript()
	 * @see #getOnEntryScriptType()
	 * @generated
	 */
	EAttribute getOnEntryScriptType_Script();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.OnEntryScriptType#getScriptFormat <em>Script Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Script Format</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.OnEntryScriptType#getScriptFormat()
	 * @see #getOnEntryScriptType()
	 * @generated
	 */
	EAttribute getOnEntryScriptType_ScriptFormat();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ElementParameters <em>Element Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Element Parameters</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ElementParameters
	 * @generated
	 */
	EClass getElementParameters();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ElementParameters#getTimeParameters <em>Time Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Time Parameters</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ElementParameters#getTimeParameters()
	 * @see #getElementParameters()
	 * @generated
	 */
	EReference getElementParameters_TimeParameters();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ElementParameters#getControlParameters <em>Control Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Control Parameters</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ElementParameters#getControlParameters()
	 * @see #getElementParameters()
	 * @generated
	 */
	EReference getElementParameters_ControlParameters();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ElementParameters#getResourceParameters <em>Resource Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Resource Parameters</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ElementParameters#getResourceParameters()
	 * @see #getElementParameters()
	 * @generated
	 */
	EReference getElementParameters_ResourceParameters();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ElementParameters#getPriorityParameters <em>Priority Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Priority Parameters</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ElementParameters#getPriorityParameters()
	 * @see #getElementParameters()
	 * @generated
	 */
	EReference getElementParameters_PriorityParameters();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ElementParameters#getCostParameters <em>Cost Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Cost Parameters</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ElementParameters#getCostParameters()
	 * @see #getElementParameters()
	 * @generated
	 */
	EReference getElementParameters_CostParameters();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ElementParameters#getPropertyParameters <em>Property Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Property Parameters</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ElementParameters#getPropertyParameters()
	 * @see #getElementParameters()
	 * @generated
	 */
	EReference getElementParameters_PropertyParameters();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ElementParameters#getVendorExtension <em>Vendor Extension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Vendor Extension</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ElementParameters#getVendorExtension()
	 * @see #getElementParameters()
	 * @generated
	 */
	EReference getElementParameters_VendorExtension();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ElementParameters#getElementId <em>Element Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Element Id</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ElementParameters#getElementId()
	 * @see #getElementParameters()
	 * @generated
	 */
	EAttribute getElementParameters_ElementId();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ElementParameters#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ElementParameters#getId()
	 * @see #getElementParameters()
	 * @generated
	 */
	EAttribute getElementParameters_Id();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.OnExitScriptType <em>On Exit Script Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>On Exit Script Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.OnExitScriptType
	 * @generated
	 */
	EClass getOnExitScriptType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.OnExitScriptType#getScript <em>Script</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Script</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.OnExitScriptType#getScript()
	 * @see #getOnExitScriptType()
	 * @generated
	 */
	EAttribute getOnExitScriptType_Script();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.OnExitScriptType#getScriptFormat <em>Script Format</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Script Format</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.OnExitScriptType#getScriptFormat()
	 * @see #getOnExitScriptType()
	 * @generated
	 */
	EAttribute getOnExitScriptType_ScriptFormat();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DurationParameterType <em>Duration Parameter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Duration Parameter Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DurationParameterType
	 * @generated
	 */
	EClass getDurationParameterType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DurationParameterType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DurationParameterType#getValue()
	 * @see #getDurationParameterType()
	 * @generated
	 */
	EAttribute getDurationParameterType_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.EnumParameterType <em>Enum Parameter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Enum Parameter Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.EnumParameterType
	 * @generated
	 */
	EClass getEnumParameterType();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.EnumParameterType#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.EnumParameterType#getGroup()
	 * @see #getEnumParameterType()
	 * @generated
	 */
	EAttribute getEnumParameterType_Group();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.EnumParameterType#getParameterValueGroup <em>Parameter Value Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Parameter Value Group</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.EnumParameterType#getParameterValueGroup()
	 * @see #getEnumParameterType()
	 * @generated
	 */
	EAttribute getEnumParameterType_ParameterValueGroup();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.EnumParameterType#getParameterValue <em>Parameter Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameter Value</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.EnumParameterType#getParameterValue()
	 * @see #getEnumParameterType()
	 * @generated
	 */
	EReference getEnumParameterType_ParameterValue();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ErlangDistributionType <em>Erlang Distribution Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Erlang Distribution Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ErlangDistributionType
	 * @generated
	 */
	EClass getErlangDistributionType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ErlangDistributionType#getK <em>K</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>K</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ErlangDistributionType#getK()
	 * @see #getErlangDistributionType()
	 * @generated
	 */
	EAttribute getErlangDistributionType_K();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ErlangDistributionType#getMean <em>Mean</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mean</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ErlangDistributionType#getMean()
	 * @see #getErlangDistributionType()
	 * @generated
	 */
	EAttribute getErlangDistributionType_Mean();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ExpressionParameterType <em>Expression Parameter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Expression Parameter Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ExpressionParameterType
	 * @generated
	 */
	EClass getExpressionParameterType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ExpressionParameterType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ExpressionParameterType#getValue()
	 * @see #getExpressionParameterType()
	 * @generated
	 */
	EAttribute getExpressionParameterType_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.FloatingParameterType <em>Floating Parameter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Floating Parameter Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.FloatingParameterType
	 * @generated
	 */
	EClass getFloatingParameterType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.FloatingParameterType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.FloatingParameterType#getValue()
	 * @see #getFloatingParameterType()
	 * @generated
	 */
	EAttribute getFloatingParameterType_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.GammaDistributionType <em>Gamma Distribution Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Gamma Distribution Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.GammaDistributionType
	 * @generated
	 */
	EClass getGammaDistributionType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.GammaDistributionType#getScale <em>Scale</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Scale</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.GammaDistributionType#getScale()
	 * @see #getGammaDistributionType()
	 * @generated
	 */
	EAttribute getGammaDistributionType_Scale();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.GammaDistributionType#getShape <em>Shape</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Shape</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.GammaDistributionType#getShape()
	 * @see #getGammaDistributionType()
	 * @generated
	 */
	EAttribute getGammaDistributionType_Shape();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.LogNormalDistributionType <em>Log Normal Distribution Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Log Normal Distribution Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.LogNormalDistributionType
	 * @generated
	 */
	EClass getLogNormalDistributionType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.LogNormalDistributionType#getMean <em>Mean</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mean</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.LogNormalDistributionType#getMean()
	 * @see #getLogNormalDistributionType()
	 * @generated
	 */
	EAttribute getLogNormalDistributionType_Mean();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.LogNormalDistributionType#getStandardDeviation <em>Standard Deviation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Standard Deviation</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.LogNormalDistributionType#getStandardDeviation()
	 * @see #getLogNormalDistributionType()
	 * @generated
	 */
	EAttribute getLogNormalDistributionType_StandardDeviation();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.MetadataType <em>Metadata Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metadata Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.MetadataType
	 * @generated
	 */
	EClass getMetadataType();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.MetadataType#getMetaentry <em>Metaentry</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Metaentry</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.MetadataType#getMetaentry()
	 * @see #getMetadataType()
	 * @generated
	 */
	EReference getMetadataType_Metaentry();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.NegativeExponentialDistributionType <em>Negative Exponential Distribution Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Negative Exponential Distribution Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.NegativeExponentialDistributionType
	 * @generated
	 */
	EClass getNegativeExponentialDistributionType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.NegativeExponentialDistributionType#getMean <em>Mean</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mean</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.NegativeExponentialDistributionType#getMean()
	 * @see #getNegativeExponentialDistributionType()
	 * @generated
	 */
	EAttribute getNegativeExponentialDistributionType_Mean();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.NormalDistributionType <em>Normal Distribution Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Normal Distribution Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.NormalDistributionType
	 * @generated
	 */
	EClass getNormalDistributionType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.NormalDistributionType#getMean <em>Mean</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mean</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.NormalDistributionType#getMean()
	 * @see #getNormalDistributionType()
	 * @generated
	 */
	EAttribute getNormalDistributionType_Mean();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.NormalDistributionType#getStandardDeviation <em>Standard Deviation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Standard Deviation</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.NormalDistributionType#getStandardDeviation()
	 * @see #getNormalDistributionType()
	 * @generated
	 */
	EAttribute getNormalDistributionType_StandardDeviation();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Parameter <em>Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Parameter
	 * @generated
	 */
	EClass getParameter();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Parameter#getResultRequest <em>Result Request</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Result Request</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Parameter#getResultRequest()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_ResultRequest();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Parameter#getParameterValueGroup <em>Parameter Value Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Parameter Value Group</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Parameter#getParameterValueGroup()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_ParameterValueGroup();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Parameter#getParameterValue <em>Parameter Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Parameter Value</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Parameter#getParameterValue()
	 * @see #getParameter()
	 * @generated
	 */
	EReference getParameter_ParameterValue();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Parameter#isKpi <em>Kpi</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Kpi</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Parameter#isKpi()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Kpi();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Parameter#isSla <em>Sla</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Sla</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Parameter#isSla()
	 * @see #getParameter()
	 * @generated
	 */
	EAttribute getParameter_Sla();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.NumericParameterType <em>Numeric Parameter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Numeric Parameter Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.NumericParameterType
	 * @generated
	 */
	EClass getNumericParameterType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.NumericParameterType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.NumericParameterType#getValue()
	 * @see #getNumericParameterType()
	 * @generated
	 */
	EAttribute getNumericParameterType_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.MetaentryType <em>Metaentry Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Metaentry Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.MetaentryType
	 * @generated
	 */
	EClass getMetaentryType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.MetaentryType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.MetaentryType#getName()
	 * @see #getMetaentryType()
	 * @generated
	 */
	EAttribute getMetaentryType_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.MetaentryType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.MetaentryType#getValue()
	 * @see #getMetaentryType()
	 * @generated
	 */
	EAttribute getMetaentryType_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DistributionParameter <em>Distribution Parameter</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Distribution Parameter</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DistributionParameter
	 * @generated
	 */
	EClass getDistributionParameter();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DistributionParameter#isDiscrete <em>Discrete</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Discrete</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DistributionParameter#isDiscrete()
	 * @see #getDistributionParameter()
	 * @generated
	 */
	EAttribute getDistributionParameter_Discrete();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ProcessAnalysisDataType <em>Process Analysis Data Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Process Analysis Data Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ProcessAnalysisDataType
	 * @generated
	 */
	EClass getProcessAnalysisDataType();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ProcessAnalysisDataType#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ProcessAnalysisDataType#getGroup()
	 * @see #getProcessAnalysisDataType()
	 * @generated
	 */
	EAttribute getProcessAnalysisDataType_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ProcessAnalysisDataType#getScenario <em>Scenario</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Scenario</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ProcessAnalysisDataType#getScenario()
	 * @see #getProcessAnalysisDataType()
	 * @generated
	 */
	EReference getProcessAnalysisDataType_Scenario();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.PropertyParameters <em>Property Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Parameters</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.PropertyParameters
	 * @generated
	 */
	EClass getPropertyParameters();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.PropertyParameters#getProperty <em>Property</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Property</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.PropertyParameters#getProperty()
	 * @see #getPropertyParameters()
	 * @generated
	 */
	EReference getPropertyParameters_Property();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.RandomDistributionType <em>Random Distribution Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Random Distribution Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.RandomDistributionType
	 * @generated
	 */
	EClass getRandomDistributionType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.RandomDistributionType#getMax <em>Max</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.RandomDistributionType#getMax()
	 * @see #getRandomDistributionType()
	 * @generated
	 */
	EAttribute getRandomDistributionType_Max();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.RandomDistributionType#getMin <em>Min</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.RandomDistributionType#getMin()
	 * @see #getRandomDistributionType()
	 * @generated
	 */
	EAttribute getRandomDistributionType_Min();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ParameterValue <em>Parameter Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Parameter Value</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ParameterValue
	 * @generated
	 */
	EClass getParameterValue();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ParameterValue#getInstance <em>Instance</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Instance</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ParameterValue#getInstance()
	 * @see #getParameterValue()
	 * @generated
	 */
	EAttribute getParameterValue_Instance();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ParameterValue#getResult <em>Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Result</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ParameterValue#getResult()
	 * @see #getParameterValue()
	 * @generated
	 */
	EAttribute getParameterValue_Result();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ParameterValue#getValidFor <em>Valid For</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Valid For</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ParameterValue#getValidFor()
	 * @see #getParameterValue()
	 * @generated
	 */
	EAttribute getParameterValue_ValidFor();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.PoissonDistributionType <em>Poisson Distribution Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Poisson Distribution Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.PoissonDistributionType
	 * @generated
	 */
	EClass getPoissonDistributionType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.PoissonDistributionType#getMean <em>Mean</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mean</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.PoissonDistributionType#getMean()
	 * @see #getPoissonDistributionType()
	 * @generated
	 */
	EAttribute getPoissonDistributionType_Mean();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.PropertyType <em>Property Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Property Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.PropertyType
	 * @generated
	 */
	EClass getPropertyType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.PropertyType#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.PropertyType#getName()
	 * @see #getPropertyType()
	 * @generated
	 */
	EAttribute getPropertyType_Name();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.PriorityParameters <em>Priority Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Priority Parameters</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.PriorityParameters
	 * @generated
	 */
	EClass getPriorityParameters();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.PriorityParameters#getInterruptible <em>Interruptible</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Interruptible</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.PriorityParameters#getInterruptible()
	 * @see #getPriorityParameters()
	 * @generated
	 */
	EReference getPriorityParameters_Interruptible();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.PriorityParameters#getPriority <em>Priority</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Priority</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.PriorityParameters#getPriority()
	 * @see #getPriorityParameters()
	 * @generated
	 */
	EReference getPriorityParameters_Priority();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TimeParameters <em>Time Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Time Parameters</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TimeParameters
	 * @generated
	 */
	EClass getTimeParameters();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TimeParameters#getTransferTime <em>Transfer Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Transfer Time</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TimeParameters#getTransferTime()
	 * @see #getTimeParameters()
	 * @generated
	 */
	EReference getTimeParameters_TransferTime();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TimeParameters#getQueueTime <em>Queue Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Queue Time</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TimeParameters#getQueueTime()
	 * @see #getTimeParameters()
	 * @generated
	 */
	EReference getTimeParameters_QueueTime();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TimeParameters#getWaitTime <em>Wait Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Wait Time</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TimeParameters#getWaitTime()
	 * @see #getTimeParameters()
	 * @generated
	 */
	EReference getTimeParameters_WaitTime();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TimeParameters#getSetUpTime <em>Set Up Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Set Up Time</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TimeParameters#getSetUpTime()
	 * @see #getTimeParameters()
	 * @generated
	 */
	EReference getTimeParameters_SetUpTime();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TimeParameters#getProcessingTime <em>Processing Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Processing Time</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TimeParameters#getProcessingTime()
	 * @see #getTimeParameters()
	 * @generated
	 */
	EReference getTimeParameters_ProcessingTime();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TimeParameters#getValidationTime <em>Validation Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Validation Time</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TimeParameters#getValidationTime()
	 * @see #getTimeParameters()
	 * @generated
	 */
	EReference getTimeParameters_ValidationTime();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TimeParameters#getReworkTime <em>Rework Time</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Rework Time</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TimeParameters#getReworkTime()
	 * @see #getTimeParameters()
	 * @generated
	 */
	EReference getTimeParameters_ReworkTime();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TimeParameters#getTimeUnit <em>Time Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Time Unit</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TimeParameters#getTimeUnit()
	 * @see #getTimeParameters()
	 * @generated
	 */
	EAttribute getTimeParameters_TimeUnit();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.StringParameterType <em>String Parameter Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>String Parameter Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.StringParameterType
	 * @generated
	 */
	EClass getStringParameterType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.StringParameterType#getValue <em>Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Value</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.StringParameterType#getValue()
	 * @see #getStringParameterType()
	 * @generated
	 */
	EAttribute getStringParameterType_Value();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Scenario <em>Scenario</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Scenario</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Scenario
	 * @generated
	 */
	EClass getScenario();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Scenario#getScenarioParameters <em>Scenario Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Scenario Parameters</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Scenario#getScenarioParameters()
	 * @see #getScenario()
	 * @generated
	 */
	EReference getScenario_ScenarioParameters();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Scenario#getElementParameters <em>Element Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Element Parameters</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Scenario#getElementParameters()
	 * @see #getScenario()
	 * @generated
	 */
	EReference getScenario_ElementParameters();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Scenario#getCalendar <em>Calendar</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Calendar</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Scenario#getCalendar()
	 * @see #getScenario()
	 * @generated
	 */
	EReference getScenario_Calendar();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Scenario#getVendorExtension <em>Vendor Extension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Vendor Extension</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Scenario#getVendorExtension()
	 * @see #getScenario()
	 * @generated
	 */
	EReference getScenario_VendorExtension();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Scenario#getAuthor <em>Author</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Author</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Scenario#getAuthor()
	 * @see #getScenario()
	 * @generated
	 */
	EAttribute getScenario_Author();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Scenario#getCreated <em>Created</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Created</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Scenario#getCreated()
	 * @see #getScenario()
	 * @generated
	 */
	EAttribute getScenario_Created();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Scenario#getDescription <em>Description</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Description</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Scenario#getDescription()
	 * @see #getScenario()
	 * @generated
	 */
	EAttribute getScenario_Description();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Scenario#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Scenario#getId()
	 * @see #getScenario()
	 * @generated
	 */
	EAttribute getScenario_Id();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Scenario#getInherits <em>Inherits</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Inherits</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Scenario#getInherits()
	 * @see #getScenario()
	 * @generated
	 */
	EAttribute getScenario_Inherits();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Scenario#getModified <em>Modified</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Modified</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Scenario#getModified()
	 * @see #getScenario()
	 * @generated
	 */
	EAttribute getScenario_Modified();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Scenario#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Scenario#getName()
	 * @see #getScenario()
	 * @generated
	 */
	EAttribute getScenario_Name();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Scenario#getResult <em>Result</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Result</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Scenario#getResult()
	 * @see #getScenario()
	 * @generated
	 */
	EAttribute getScenario_Result();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Scenario#getVendor <em>Vendor</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Vendor</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Scenario#getVendor()
	 * @see #getScenario()
	 * @generated
	 */
	EAttribute getScenario_Vendor();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Scenario#getVersion <em>Version</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Version</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Scenario#getVersion()
	 * @see #getScenario()
	 * @generated
	 */
	EAttribute getScenario_Version();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ResourceParameters <em>Resource Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Resource Parameters</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ResourceParameters
	 * @generated
	 */
	EClass getResourceParameters();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ResourceParameters#getSelection <em>Selection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Selection</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ResourceParameters#getSelection()
	 * @see #getResourceParameters()
	 * @generated
	 */
	EReference getResourceParameters_Selection();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ResourceParameters#getAvailability <em>Availability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Availability</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ResourceParameters#getAvailability()
	 * @see #getResourceParameters()
	 * @generated
	 */
	EReference getResourceParameters_Availability();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ResourceParameters#getQuantity <em>Quantity</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Quantity</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ResourceParameters#getQuantity()
	 * @see #getResourceParameters()
	 * @generated
	 */
	EReference getResourceParameters_Quantity();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ResourceParameters#getWorkinghours <em>Workinghours</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Workinghours</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ResourceParameters#getWorkinghours()
	 * @see #getResourceParameters()
	 * @generated
	 */
	EReference getResourceParameters_Workinghours();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ResourceParameters#getRole <em>Role</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Role</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ResourceParameters#getRole()
	 * @see #getResourceParameters()
	 * @generated
	 */
	EReference getResourceParameters_Role();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ScenarioParameters <em>Scenario Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Scenario Parameters</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ScenarioParameters
	 * @generated
	 */
	EClass getScenarioParameters();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ScenarioParameters#getStart <em>Start</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Start</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ScenarioParameters#getStart()
	 * @see #getScenarioParameters()
	 * @generated
	 */
	EReference getScenarioParameters_Start();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ScenarioParameters#getDuration <em>Duration</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Duration</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ScenarioParameters#getDuration()
	 * @see #getScenarioParameters()
	 * @generated
	 */
	EReference getScenarioParameters_Duration();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ScenarioParameters#getPropertyParameters <em>Property Parameters</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Property Parameters</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ScenarioParameters#getPropertyParameters()
	 * @see #getScenarioParameters()
	 * @generated
	 */
	EReference getScenarioParameters_PropertyParameters();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ScenarioParameters#getBaseCurrencyUnit <em>Base Currency Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Base Currency Unit</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ScenarioParameters#getBaseCurrencyUnit()
	 * @see #getScenarioParameters()
	 * @generated
	 */
	EAttribute getScenarioParameters_BaseCurrencyUnit();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ScenarioParameters#getBaseTimeUnit <em>Base Time Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Base Time Unit</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ScenarioParameters#getBaseTimeUnit()
	 * @see #getScenarioParameters()
	 * @generated
	 */
	EAttribute getScenarioParameters_BaseTimeUnit();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ScenarioParameters#getReplication <em>Replication</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Replication</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ScenarioParameters#getReplication()
	 * @see #getScenarioParameters()
	 * @generated
	 */
	EAttribute getScenarioParameters_Replication();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ScenarioParameters#getSeed <em>Seed</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Seed</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ScenarioParameters#getSeed()
	 * @see #getScenarioParameters()
	 * @generated
	 */
	EAttribute getScenarioParameters_Seed();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.VendorExtension <em>Vendor Extension</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Vendor Extension</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.VendorExtension
	 * @generated
	 */
	EClass getVendorExtension();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.VendorExtension#getAny <em>Any</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.VendorExtension#getAny()
	 * @see #getVendorExtension()
	 * @generated
	 */
	EAttribute getVendorExtension_Any();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.VendorExtension#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.VendorExtension#getName()
	 * @see #getVendorExtension()
	 * @generated
	 */
	EAttribute getVendorExtension_Name();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.VendorExtension#getAnyAttribute <em>Any Attribute</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Any Attribute</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.VendorExtension#getAnyAttribute()
	 * @see #getVendorExtension()
	 * @generated
	 */
	EAttribute getVendorExtension_AnyAttribute();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TriangularDistributionType <em>Triangular Distribution Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Triangular Distribution Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TriangularDistributionType
	 * @generated
	 */
	EClass getTriangularDistributionType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TriangularDistributionType#getMax <em>Max</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TriangularDistributionType#getMax()
	 * @see #getTriangularDistributionType()
	 * @generated
	 */
	EAttribute getTriangularDistributionType_Max();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TriangularDistributionType#getMin <em>Min</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TriangularDistributionType#getMin()
	 * @see #getTriangularDistributionType()
	 * @generated
	 */
	EAttribute getTriangularDistributionType_Min();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TriangularDistributionType#getMostLikely <em>Most Likely</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Most Likely</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TriangularDistributionType#getMostLikely()
	 * @see #getTriangularDistributionType()
	 * @generated
	 */
	EAttribute getTriangularDistributionType_MostLikely();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TruncatedNormalDistributionType <em>Truncated Normal Distribution Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Truncated Normal Distribution Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TruncatedNormalDistributionType
	 * @generated
	 */
	EClass getTruncatedNormalDistributionType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TruncatedNormalDistributionType#getMax <em>Max</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TruncatedNormalDistributionType#getMax()
	 * @see #getTruncatedNormalDistributionType()
	 * @generated
	 */
	EAttribute getTruncatedNormalDistributionType_Max();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TruncatedNormalDistributionType#getMean <em>Mean</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Mean</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TruncatedNormalDistributionType#getMean()
	 * @see #getTruncatedNormalDistributionType()
	 * @generated
	 */
	EAttribute getTruncatedNormalDistributionType_Mean();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TruncatedNormalDistributionType#getMin <em>Min</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TruncatedNormalDistributionType#getMin()
	 * @see #getTruncatedNormalDistributionType()
	 * @generated
	 */
	EAttribute getTruncatedNormalDistributionType_Min();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TruncatedNormalDistributionType#getStandardDeviation <em>Standard Deviation</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Standard Deviation</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TruncatedNormalDistributionType#getStandardDeviation()
	 * @see #getTruncatedNormalDistributionType()
	 * @generated
	 */
	EAttribute getTruncatedNormalDistributionType_StandardDeviation();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.UniformDistributionType <em>Uniform Distribution Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Uniform Distribution Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.UniformDistributionType
	 * @generated
	 */
	EClass getUniformDistributionType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.UniformDistributionType#getMax <em>Max</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Max</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.UniformDistributionType#getMax()
	 * @see #getUniformDistributionType()
	 * @generated
	 */
	EAttribute getUniformDistributionType_Max();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.UniformDistributionType#getMin <em>Min</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Min</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.UniformDistributionType#getMin()
	 * @see #getUniformDistributionType()
	 * @generated
	 */
	EAttribute getUniformDistributionType_Min();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.UserDistributionDataPointType <em>User Distribution Data Point Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>User Distribution Data Point Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.UserDistributionDataPointType
	 * @generated
	 */
	EClass getUserDistributionDataPointType();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.UserDistributionDataPointType#getParameterValueGroup <em>Parameter Value Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Parameter Value Group</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.UserDistributionDataPointType#getParameterValueGroup()
	 * @see #getUserDistributionDataPointType()
	 * @generated
	 */
	EAttribute getUserDistributionDataPointType_ParameterValueGroup();

	/**
	 * Returns the meta object for the containment reference '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.UserDistributionDataPointType#getParameterValue <em>Parameter Value</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference '<em>Parameter Value</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.UserDistributionDataPointType#getParameterValue()
	 * @see #getUserDistributionDataPointType()
	 * @generated
	 */
	EReference getUserDistributionDataPointType_ParameterValue();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.UserDistributionDataPointType#getProbability <em>Probability</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Probability</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.UserDistributionDataPointType#getProbability()
	 * @see #getUserDistributionDataPointType()
	 * @generated
	 */
	EAttribute getUserDistributionDataPointType_Probability();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.UserDistributionType <em>User Distribution Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>User Distribution Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.UserDistributionType
	 * @generated
	 */
	EClass getUserDistributionType();

	/**
	 * Returns the meta object for the attribute list '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.UserDistributionType#getGroup <em>Group</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute list '<em>Group</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.UserDistributionType#getGroup()
	 * @see #getUserDistributionType()
	 * @generated
	 */
	EAttribute getUserDistributionType_Group();

	/**
	 * Returns the meta object for the containment reference list '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.UserDistributionType#getUserDistributionDataPoint <em>User Distribution Data Point</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>User Distribution Data Point</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.UserDistributionType#getUserDistributionDataPoint()
	 * @see #getUserDistributionType()
	 * @generated
	 */
	EReference getUserDistributionType_UserDistributionDataPoint();

	/**
	 * Returns the meta object for class '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.WeibullDistributionType <em>Weibull Distribution Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Weibull Distribution Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.WeibullDistributionType
	 * @generated
	 */
	EClass getWeibullDistributionType();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.WeibullDistributionType#getScale <em>Scale</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Scale</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.WeibullDistributionType#getScale()
	 * @see #getWeibullDistributionType()
	 * @generated
	 */
	EAttribute getWeibullDistributionType_Scale();

	/**
	 * Returns the meta object for the attribute '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.WeibullDistributionType#getShape <em>Shape</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Shape</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.WeibullDistributionType#getShape()
	 * @see #getWeibullDistributionType()
	 * @generated
	 */
	EAttribute getWeibullDistributionType_Shape();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ResultType <em>Result Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Result Type</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ResultType
	 * @generated
	 */
	EEnum getResultType();

	/**
	 * Returns the meta object for enum '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TimeUnit <em>Time Unit</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for enum '<em>Time Unit</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TimeUnit
	 * @generated
	 */
	EEnum getTimeUnit();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Package Name Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Package Name Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='packageName_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'"
	 * @generated
	 */
	EDataType getPackageNameType();

	/**
	 * Returns the meta object for data type '{@link java.math.BigInteger <em>Priority Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Priority Type</em>'.
	 * @see java.math.BigInteger
	 * @model instanceClass="java.math.BigInteger"
	 *        extendedMetaData="name='priority_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#integer' minInclusive='1'"
	 * @generated
	 */
	EDataType getPriorityType();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Rule Flow Group Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Rule Flow Group Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='ruleFlowGroup_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'"
	 * @generated
	 */
	EDataType getRuleFlowGroupType();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Task Name Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Task Name Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='taskName_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'"
	 * @generated
	 */
	EDataType getTaskNameType();

	/**
	 * Returns the meta object for data type '{@link java.lang.String <em>Version Type</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Version Type</em>'.
	 * @see java.lang.String
	 * @model instanceClass="java.lang.String"
	 *        extendedMetaData="name='version_._type' baseType='http://www.eclipse.org/emf/2003/XMLType#string'"
	 * @generated
	 */
	EDataType getVersionType();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ResultType <em>Result Type Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Result Type Object</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ResultType
	 * @model instanceClass="org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ResultType"
	 *        extendedMetaData="name='ResultType:Object' baseType='ResultType'"
	 * @generated
	 */
	EDataType getResultTypeObject();

	/**
	 * Returns the meta object for data type '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TimeUnit <em>Time Unit Object</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for data type '<em>Time Unit Object</em>'.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TimeUnit
	 * @model instanceClass="org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TimeUnit"
	 *        extendedMetaData="name='TimeUnit:Object' baseType='TimeUnit'"
	 * @generated
	 */
	EDataType getTimeUnitObject();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ModelFactory getModelFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.BetaDistributionTypeImpl <em>Beta Distribution Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.BetaDistributionTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getBetaDistributionType()
		 * @generated
		 */
		EClass BETA_DISTRIBUTION_TYPE = eINSTANCE.getBetaDistributionType();

		/**
		 * The meta object literal for the '<em><b>Scale</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BETA_DISTRIBUTION_TYPE__SCALE = eINSTANCE.getBetaDistributionType_Scale();

		/**
		 * The meta object literal for the '<em><b>Shape</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BETA_DISTRIBUTION_TYPE__SHAPE = eINSTANCE.getBetaDistributionType_Shape();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.BinomialDistributionTypeImpl <em>Binomial Distribution Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.BinomialDistributionTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getBinomialDistributionType()
		 * @generated
		 */
		EClass BINOMIAL_DISTRIBUTION_TYPE = eINSTANCE.getBinomialDistributionType();

		/**
		 * The meta object literal for the '<em><b>Probability</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BINOMIAL_DISTRIBUTION_TYPE__PROBABILITY = eINSTANCE.getBinomialDistributionType_Probability();

		/**
		 * The meta object literal for the '<em><b>Trials</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BINOMIAL_DISTRIBUTION_TYPE__TRIALS = eINSTANCE.getBinomialDistributionType_Trials();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.BooleanParameterTypeImpl <em>Boolean Parameter Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.BooleanParameterTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getBooleanParameterType()
		 * @generated
		 */
		EClass BOOLEAN_PARAMETER_TYPE = eINSTANCE.getBooleanParameterType();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute BOOLEAN_PARAMETER_TYPE__VALUE = eINSTANCE.getBooleanParameterType_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.CalendarImpl <em>Calendar</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.CalendarImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getCalendar()
		 * @generated
		 */
		EClass CALENDAR = eINSTANCE.getCalendar();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CALENDAR__VALUE = eINSTANCE.getCalendar_Value();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CALENDAR__ID = eINSTANCE.getCalendar_Id();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute CALENDAR__NAME = eINSTANCE.getCalendar_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl <em>Document Root</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getDocumentRoot()
		 * @generated
		 */
		EClass DOCUMENT_ROOT = eINSTANCE.getDocumentRoot();

		/**
		 * The meta object literal for the '<em><b>Beta Distribution</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__BETA_DISTRIBUTION = eINSTANCE.getDocumentRoot_BetaDistribution();

		/**
		 * The meta object literal for the '<em><b>Parameter Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__PARAMETER_VALUE = eINSTANCE.getDocumentRoot_ParameterValue();

		/**
		 * The meta object literal for the '<em><b>Binomial Distribution</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__BINOMIAL_DISTRIBUTION = eINSTANCE.getDocumentRoot_BinomialDistribution();

		/**
		 * The meta object literal for the '<em><b>Boolean Parameter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__BOOLEAN_PARAMETER = eINSTANCE.getDocumentRoot_BooleanParameter();

		/**
		 * The meta object literal for the '<em><b>Date Time Parameter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__DATE_TIME_PARAMETER = eINSTANCE.getDocumentRoot_DateTimeParameter();

		/**
		 * The meta object literal for the '<em><b>Decimal Parameter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__DECIMAL_PARAMETER = eINSTANCE.getDocumentRoot_DecimalParameter();

		/**
		 * The meta object literal for the '<em><b>Duration Parameter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__DURATION_PARAMETER = eINSTANCE.getDocumentRoot_DurationParameter();

		/**
		 * The meta object literal for the '<em><b>Enum Parameter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ENUM_PARAMETER = eINSTANCE.getDocumentRoot_EnumParameter();

		/**
		 * The meta object literal for the '<em><b>Erlang Distribution</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ERLANG_DISTRIBUTION = eINSTANCE.getDocumentRoot_ErlangDistribution();

		/**
		 * The meta object literal for the '<em><b>Expression Parameter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__EXPRESSION_PARAMETER = eINSTANCE.getDocumentRoot_ExpressionParameter();

		/**
		 * The meta object literal for the '<em><b>Floating Parameter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__FLOATING_PARAMETER = eINSTANCE.getDocumentRoot_FloatingParameter();

		/**
		 * The meta object literal for the '<em><b>Gamma Distribution</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__GAMMA_DISTRIBUTION = eINSTANCE.getDocumentRoot_GammaDistribution();

		/**
		 * The meta object literal for the '<em><b>Global</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__GLOBAL = eINSTANCE.getDocumentRoot_Global();

		/**
		 * The meta object literal for the '<em><b>Log Normal Distribution</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__LOG_NORMAL_DISTRIBUTION = eINSTANCE.getDocumentRoot_LogNormalDistribution();

		/**
		 * The meta object literal for the '<em><b>Metadata</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__METADATA = eINSTANCE.getDocumentRoot_Metadata();

		/**
		 * The meta object literal for the '<em><b>Metaentry</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__METAENTRY = eINSTANCE.getDocumentRoot_Metaentry();

		/**
		 * The meta object literal for the '<em><b>Negative Exponential Distribution</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__NEGATIVE_EXPONENTIAL_DISTRIBUTION = eINSTANCE.getDocumentRoot_NegativeExponentialDistribution();

		/**
		 * The meta object literal for the '<em><b>Normal Distribution</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__NORMAL_DISTRIBUTION = eINSTANCE.getDocumentRoot_NormalDistribution();

		/**
		 * The meta object literal for the '<em><b>Numeric Parameter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__NUMERIC_PARAMETER = eINSTANCE.getDocumentRoot_NumericParameter();

		/**
		 * The meta object literal for the '<em><b>On Entry Script</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ON_ENTRY_SCRIPT = eINSTANCE.getDocumentRoot_OnEntryScript();

		/**
		 * The meta object literal for the '<em><b>On Exit Script</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__ON_EXIT_SCRIPT = eINSTANCE.getDocumentRoot_OnExitScript();

		/**
		 * The meta object literal for the '<em><b>Poisson Distribution</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__POISSON_DISTRIBUTION = eINSTANCE.getDocumentRoot_PoissonDistribution();

		/**
		 * The meta object literal for the '<em><b>Process Analysis Data</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__PROCESS_ANALYSIS_DATA = eINSTANCE.getDocumentRoot_ProcessAnalysisData();

		/**
		 * The meta object literal for the '<em><b>Random Distribution</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__RANDOM_DISTRIBUTION = eINSTANCE.getDocumentRoot_RandomDistribution();

		/**
		 * The meta object literal for the '<em><b>String Parameter</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__STRING_PARAMETER = eINSTANCE.getDocumentRoot_StringParameter();

		/**
		 * The meta object literal for the '<em><b>Triangular Distribution</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__TRIANGULAR_DISTRIBUTION = eINSTANCE.getDocumentRoot_TriangularDistribution();

		/**
		 * The meta object literal for the '<em><b>Truncated Normal Distribution</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__TRUNCATED_NORMAL_DISTRIBUTION = eINSTANCE.getDocumentRoot_TruncatedNormalDistribution();

		/**
		 * The meta object literal for the '<em><b>Uniform Distribution</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__UNIFORM_DISTRIBUTION = eINSTANCE.getDocumentRoot_UniformDistribution();

		/**
		 * The meta object literal for the '<em><b>User Distribution</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__USER_DISTRIBUTION = eINSTANCE.getDocumentRoot_UserDistribution();

		/**
		 * The meta object literal for the '<em><b>User Distribution Data Point</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__USER_DISTRIBUTION_DATA_POINT = eINSTANCE.getDocumentRoot_UserDistributionDataPoint();

		/**
		 * The meta object literal for the '<em><b>Weibull Distribution</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference DOCUMENT_ROOT__WEIBULL_DISTRIBUTION = eINSTANCE.getDocumentRoot_WeibullDistribution();

		/**
		 * The meta object literal for the '<em><b>Package Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__PACKAGE_NAME = eINSTANCE.getDocumentRoot_PackageName();

		/**
		 * The meta object literal for the '<em><b>Priority</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__PRIORITY = eINSTANCE.getDocumentRoot_Priority();

		/**
		 * The meta object literal for the '<em><b>Rule Flow Group</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__RULE_FLOW_GROUP = eINSTANCE.getDocumentRoot_RuleFlowGroup();

		/**
		 * The meta object literal for the '<em><b>Task Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__TASK_NAME = eINSTANCE.getDocumentRoot_TaskName();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DOCUMENT_ROOT__VERSION = eINSTANCE.getDocumentRoot_Version();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.GlobalTypeImpl <em>Global Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.GlobalTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getGlobalType()
		 * @generated
		 */
		EClass GLOBAL_TYPE = eINSTANCE.getGlobalType();

		/**
		 * The meta object literal for the '<em><b>Identifier</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GLOBAL_TYPE__IDENTIFIER = eINSTANCE.getGlobalType_Identifier();

		/**
		 * The meta object literal for the '<em><b>Type</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GLOBAL_TYPE__TYPE = eINSTANCE.getGlobalType_Type();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ControlParametersImpl <em>Control Parameters</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ControlParametersImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getControlParameters()
		 * @generated
		 */
		EClass CONTROL_PARAMETERS = eINSTANCE.getControlParameters();

		/**
		 * The meta object literal for the '<em><b>Probability</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTROL_PARAMETERS__PROBABILITY = eINSTANCE.getControlParameters_Probability();

		/**
		 * The meta object literal for the '<em><b>Inter Trigger Timer</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTROL_PARAMETERS__INTER_TRIGGER_TIMER = eINSTANCE.getControlParameters_InterTriggerTimer();

		/**
		 * The meta object literal for the '<em><b>Max Trigger Count</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference CONTROL_PARAMETERS__MAX_TRIGGER_COUNT = eINSTANCE.getControlParameters_MaxTriggerCount();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ConstantParameterImpl <em>Constant Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ConstantParameterImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getConstantParameter()
		 * @generated
		 */
		EClass CONSTANT_PARAMETER = eINSTANCE.getConstantParameter();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.CostParametersImpl <em>Cost Parameters</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.CostParametersImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getCostParameters()
		 * @generated
		 */
		EClass COST_PARAMETERS = eINSTANCE.getCostParameters();

		/**
		 * The meta object literal for the '<em><b>Fixed Cost</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COST_PARAMETERS__FIXED_COST = eINSTANCE.getCostParameters_FixedCost();

		/**
		 * The meta object literal for the '<em><b>Unit Cost</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COST_PARAMETERS__UNIT_COST = eINSTANCE.getCostParameters_UnitCost();

		/**
		 * The meta object literal for the '<em><b>Currency Unit</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COST_PARAMETERS__CURRENCY_UNIT = eINSTANCE.getCostParameters_CurrencyUnit();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ImportTypeImpl <em>Import Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ImportTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getImportType()
		 * @generated
		 */
		EClass IMPORT_TYPE = eINSTANCE.getImportType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute IMPORT_TYPE__NAME = eINSTANCE.getImportType_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DateTimeParameterTypeImpl <em>Date Time Parameter Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DateTimeParameterTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getDateTimeParameterType()
		 * @generated
		 */
		EClass DATE_TIME_PARAMETER_TYPE = eINSTANCE.getDateTimeParameterType();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DATE_TIME_PARAMETER_TYPE__VALUE = eINSTANCE.getDateTimeParameterType_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DecimalParameterTypeImpl <em>Decimal Parameter Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DecimalParameterTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getDecimalParameterType()
		 * @generated
		 */
		EClass DECIMAL_PARAMETER_TYPE = eINSTANCE.getDecimalParameterType();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DECIMAL_PARAMETER_TYPE__VALUE = eINSTANCE.getDecimalParameterType_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.OnEntryScriptTypeImpl <em>On Entry Script Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.OnEntryScriptTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getOnEntryScriptType()
		 * @generated
		 */
		EClass ON_ENTRY_SCRIPT_TYPE = eINSTANCE.getOnEntryScriptType();

		/**
		 * The meta object literal for the '<em><b>Script</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ON_ENTRY_SCRIPT_TYPE__SCRIPT = eINSTANCE.getOnEntryScriptType_Script();

		/**
		 * The meta object literal for the '<em><b>Script Format</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ON_ENTRY_SCRIPT_TYPE__SCRIPT_FORMAT = eINSTANCE.getOnEntryScriptType_ScriptFormat();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ElementParametersImpl <em>Element Parameters</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ElementParametersImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getElementParameters()
		 * @generated
		 */
		EClass ELEMENT_PARAMETERS = eINSTANCE.getElementParameters();

		/**
		 * The meta object literal for the '<em><b>Time Parameters</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELEMENT_PARAMETERS__TIME_PARAMETERS = eINSTANCE.getElementParameters_TimeParameters();

		/**
		 * The meta object literal for the '<em><b>Control Parameters</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELEMENT_PARAMETERS__CONTROL_PARAMETERS = eINSTANCE.getElementParameters_ControlParameters();

		/**
		 * The meta object literal for the '<em><b>Resource Parameters</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELEMENT_PARAMETERS__RESOURCE_PARAMETERS = eINSTANCE.getElementParameters_ResourceParameters();

		/**
		 * The meta object literal for the '<em><b>Priority Parameters</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELEMENT_PARAMETERS__PRIORITY_PARAMETERS = eINSTANCE.getElementParameters_PriorityParameters();

		/**
		 * The meta object literal for the '<em><b>Cost Parameters</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELEMENT_PARAMETERS__COST_PARAMETERS = eINSTANCE.getElementParameters_CostParameters();

		/**
		 * The meta object literal for the '<em><b>Property Parameters</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELEMENT_PARAMETERS__PROPERTY_PARAMETERS = eINSTANCE.getElementParameters_PropertyParameters();

		/**
		 * The meta object literal for the '<em><b>Vendor Extension</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ELEMENT_PARAMETERS__VENDOR_EXTENSION = eINSTANCE.getElementParameters_VendorExtension();

		/**
		 * The meta object literal for the '<em><b>Element Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELEMENT_PARAMETERS__ELEMENT_ID = eINSTANCE.getElementParameters_ElementId();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ELEMENT_PARAMETERS__ID = eINSTANCE.getElementParameters_Id();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.OnExitScriptTypeImpl <em>On Exit Script Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.OnExitScriptTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getOnExitScriptType()
		 * @generated
		 */
		EClass ON_EXIT_SCRIPT_TYPE = eINSTANCE.getOnExitScriptType();

		/**
		 * The meta object literal for the '<em><b>Script</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ON_EXIT_SCRIPT_TYPE__SCRIPT = eINSTANCE.getOnExitScriptType_Script();

		/**
		 * The meta object literal for the '<em><b>Script Format</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ON_EXIT_SCRIPT_TYPE__SCRIPT_FORMAT = eINSTANCE.getOnExitScriptType_ScriptFormat();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DurationParameterTypeImpl <em>Duration Parameter Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DurationParameterTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getDurationParameterType()
		 * @generated
		 */
		EClass DURATION_PARAMETER_TYPE = eINSTANCE.getDurationParameterType();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DURATION_PARAMETER_TYPE__VALUE = eINSTANCE.getDurationParameterType_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.EnumParameterTypeImpl <em>Enum Parameter Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.EnumParameterTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getEnumParameterType()
		 * @generated
		 */
		EClass ENUM_PARAMETER_TYPE = eINSTANCE.getEnumParameterType();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENUM_PARAMETER_TYPE__GROUP = eINSTANCE.getEnumParameterType_Group();

		/**
		 * The meta object literal for the '<em><b>Parameter Value Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ENUM_PARAMETER_TYPE__PARAMETER_VALUE_GROUP = eINSTANCE.getEnumParameterType_ParameterValueGroup();

		/**
		 * The meta object literal for the '<em><b>Parameter Value</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference ENUM_PARAMETER_TYPE__PARAMETER_VALUE = eINSTANCE.getEnumParameterType_ParameterValue();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ErlangDistributionTypeImpl <em>Erlang Distribution Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ErlangDistributionTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getErlangDistributionType()
		 * @generated
		 */
		EClass ERLANG_DISTRIBUTION_TYPE = eINSTANCE.getErlangDistributionType();

		/**
		 * The meta object literal for the '<em><b>K</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ERLANG_DISTRIBUTION_TYPE__K = eINSTANCE.getErlangDistributionType_K();

		/**
		 * The meta object literal for the '<em><b>Mean</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute ERLANG_DISTRIBUTION_TYPE__MEAN = eINSTANCE.getErlangDistributionType_Mean();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ExpressionParameterTypeImpl <em>Expression Parameter Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ExpressionParameterTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getExpressionParameterType()
		 * @generated
		 */
		EClass EXPRESSION_PARAMETER_TYPE = eINSTANCE.getExpressionParameterType();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute EXPRESSION_PARAMETER_TYPE__VALUE = eINSTANCE.getExpressionParameterType_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.FloatingParameterTypeImpl <em>Floating Parameter Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.FloatingParameterTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getFloatingParameterType()
		 * @generated
		 */
		EClass FLOATING_PARAMETER_TYPE = eINSTANCE.getFloatingParameterType();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute FLOATING_PARAMETER_TYPE__VALUE = eINSTANCE.getFloatingParameterType_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.GammaDistributionTypeImpl <em>Gamma Distribution Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.GammaDistributionTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getGammaDistributionType()
		 * @generated
		 */
		EClass GAMMA_DISTRIBUTION_TYPE = eINSTANCE.getGammaDistributionType();

		/**
		 * The meta object literal for the '<em><b>Scale</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GAMMA_DISTRIBUTION_TYPE__SCALE = eINSTANCE.getGammaDistributionType_Scale();

		/**
		 * The meta object literal for the '<em><b>Shape</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute GAMMA_DISTRIBUTION_TYPE__SHAPE = eINSTANCE.getGammaDistributionType_Shape();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.LogNormalDistributionTypeImpl <em>Log Normal Distribution Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.LogNormalDistributionTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getLogNormalDistributionType()
		 * @generated
		 */
		EClass LOG_NORMAL_DISTRIBUTION_TYPE = eINSTANCE.getLogNormalDistributionType();

		/**
		 * The meta object literal for the '<em><b>Mean</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOG_NORMAL_DISTRIBUTION_TYPE__MEAN = eINSTANCE.getLogNormalDistributionType_Mean();

		/**
		 * The meta object literal for the '<em><b>Standard Deviation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute LOG_NORMAL_DISTRIBUTION_TYPE__STANDARD_DEVIATION = eINSTANCE.getLogNormalDistributionType_StandardDeviation();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.MetadataTypeImpl <em>Metadata Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.MetadataTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getMetadataType()
		 * @generated
		 */
		EClass METADATA_TYPE = eINSTANCE.getMetadataType();

		/**
		 * The meta object literal for the '<em><b>Metaentry</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference METADATA_TYPE__METAENTRY = eINSTANCE.getMetadataType_Metaentry();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.NegativeExponentialDistributionTypeImpl <em>Negative Exponential Distribution Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.NegativeExponentialDistributionTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getNegativeExponentialDistributionType()
		 * @generated
		 */
		EClass NEGATIVE_EXPONENTIAL_DISTRIBUTION_TYPE = eINSTANCE.getNegativeExponentialDistributionType();

		/**
		 * The meta object literal for the '<em><b>Mean</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NEGATIVE_EXPONENTIAL_DISTRIBUTION_TYPE__MEAN = eINSTANCE.getNegativeExponentialDistributionType_Mean();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.NormalDistributionTypeImpl <em>Normal Distribution Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.NormalDistributionTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getNormalDistributionType()
		 * @generated
		 */
		EClass NORMAL_DISTRIBUTION_TYPE = eINSTANCE.getNormalDistributionType();

		/**
		 * The meta object literal for the '<em><b>Mean</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NORMAL_DISTRIBUTION_TYPE__MEAN = eINSTANCE.getNormalDistributionType_Mean();

		/**
		 * The meta object literal for the '<em><b>Standard Deviation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NORMAL_DISTRIBUTION_TYPE__STANDARD_DEVIATION = eINSTANCE.getNormalDistributionType_StandardDeviation();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ParameterImpl <em>Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ParameterImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getParameter()
		 * @generated
		 */
		EClass PARAMETER = eINSTANCE.getParameter();

		/**
		 * The meta object literal for the '<em><b>Result Request</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__RESULT_REQUEST = eINSTANCE.getParameter_ResultRequest();

		/**
		 * The meta object literal for the '<em><b>Parameter Value Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__PARAMETER_VALUE_GROUP = eINSTANCE.getParameter_ParameterValueGroup();

		/**
		 * The meta object literal for the '<em><b>Parameter Value</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PARAMETER__PARAMETER_VALUE = eINSTANCE.getParameter_ParameterValue();

		/**
		 * The meta object literal for the '<em><b>Kpi</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__KPI = eINSTANCE.getParameter_Kpi();

		/**
		 * The meta object literal for the '<em><b>Sla</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER__SLA = eINSTANCE.getParameter_Sla();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.NumericParameterTypeImpl <em>Numeric Parameter Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.NumericParameterTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getNumericParameterType()
		 * @generated
		 */
		EClass NUMERIC_PARAMETER_TYPE = eINSTANCE.getNumericParameterType();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute NUMERIC_PARAMETER_TYPE__VALUE = eINSTANCE.getNumericParameterType_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.MetaentryTypeImpl <em>Metaentry Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.MetaentryTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getMetaentryType()
		 * @generated
		 */
		EClass METAENTRY_TYPE = eINSTANCE.getMetaentryType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METAENTRY_TYPE__NAME = eINSTANCE.getMetaentryType_Name();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute METAENTRY_TYPE__VALUE = eINSTANCE.getMetaentryType_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DistributionParameterImpl <em>Distribution Parameter</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DistributionParameterImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getDistributionParameter()
		 * @generated
		 */
		EClass DISTRIBUTION_PARAMETER = eINSTANCE.getDistributionParameter();

		/**
		 * The meta object literal for the '<em><b>Discrete</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute DISTRIBUTION_PARAMETER__DISCRETE = eINSTANCE.getDistributionParameter_Discrete();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ProcessAnalysisDataTypeImpl <em>Process Analysis Data Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ProcessAnalysisDataTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getProcessAnalysisDataType()
		 * @generated
		 */
		EClass PROCESS_ANALYSIS_DATA_TYPE = eINSTANCE.getProcessAnalysisDataType();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROCESS_ANALYSIS_DATA_TYPE__GROUP = eINSTANCE.getProcessAnalysisDataType_Group();

		/**
		 * The meta object literal for the '<em><b>Scenario</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROCESS_ANALYSIS_DATA_TYPE__SCENARIO = eINSTANCE.getProcessAnalysisDataType_Scenario();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.PropertyParametersImpl <em>Property Parameters</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.PropertyParametersImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getPropertyParameters()
		 * @generated
		 */
		EClass PROPERTY_PARAMETERS = eINSTANCE.getPropertyParameters();

		/**
		 * The meta object literal for the '<em><b>Property</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PROPERTY_PARAMETERS__PROPERTY = eINSTANCE.getPropertyParameters_Property();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.RandomDistributionTypeImpl <em>Random Distribution Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.RandomDistributionTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getRandomDistributionType()
		 * @generated
		 */
		EClass RANDOM_DISTRIBUTION_TYPE = eINSTANCE.getRandomDistributionType();

		/**
		 * The meta object literal for the '<em><b>Max</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RANDOM_DISTRIBUTION_TYPE__MAX = eINSTANCE.getRandomDistributionType_Max();

		/**
		 * The meta object literal for the '<em><b>Min</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute RANDOM_DISTRIBUTION_TYPE__MIN = eINSTANCE.getRandomDistributionType_Min();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ParameterValueImpl <em>Parameter Value</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ParameterValueImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getParameterValue()
		 * @generated
		 */
		EClass PARAMETER_VALUE = eINSTANCE.getParameterValue();

		/**
		 * The meta object literal for the '<em><b>Instance</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER_VALUE__INSTANCE = eINSTANCE.getParameterValue_Instance();

		/**
		 * The meta object literal for the '<em><b>Result</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER_VALUE__RESULT = eINSTANCE.getParameterValue_Result();

		/**
		 * The meta object literal for the '<em><b>Valid For</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PARAMETER_VALUE__VALID_FOR = eINSTANCE.getParameterValue_ValidFor();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.PoissonDistributionTypeImpl <em>Poisson Distribution Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.PoissonDistributionTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getPoissonDistributionType()
		 * @generated
		 */
		EClass POISSON_DISTRIBUTION_TYPE = eINSTANCE.getPoissonDistributionType();

		/**
		 * The meta object literal for the '<em><b>Mean</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute POISSON_DISTRIBUTION_TYPE__MEAN = eINSTANCE.getPoissonDistributionType_Mean();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.PropertyTypeImpl <em>Property Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.PropertyTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getPropertyType()
		 * @generated
		 */
		EClass PROPERTY_TYPE = eINSTANCE.getPropertyType();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute PROPERTY_TYPE__NAME = eINSTANCE.getPropertyType_Name();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.PriorityParametersImpl <em>Priority Parameters</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.PriorityParametersImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getPriorityParameters()
		 * @generated
		 */
		EClass PRIORITY_PARAMETERS = eINSTANCE.getPriorityParameters();

		/**
		 * The meta object literal for the '<em><b>Interruptible</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRIORITY_PARAMETERS__INTERRUPTIBLE = eINSTANCE.getPriorityParameters_Interruptible();

		/**
		 * The meta object literal for the '<em><b>Priority</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference PRIORITY_PARAMETERS__PRIORITY = eINSTANCE.getPriorityParameters_Priority();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.TimeParametersImpl <em>Time Parameters</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.TimeParametersImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getTimeParameters()
		 * @generated
		 */
		EClass TIME_PARAMETERS = eINSTANCE.getTimeParameters();

		/**
		 * The meta object literal for the '<em><b>Transfer Time</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TIME_PARAMETERS__TRANSFER_TIME = eINSTANCE.getTimeParameters_TransferTime();

		/**
		 * The meta object literal for the '<em><b>Queue Time</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TIME_PARAMETERS__QUEUE_TIME = eINSTANCE.getTimeParameters_QueueTime();

		/**
		 * The meta object literal for the '<em><b>Wait Time</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TIME_PARAMETERS__WAIT_TIME = eINSTANCE.getTimeParameters_WaitTime();

		/**
		 * The meta object literal for the '<em><b>Set Up Time</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TIME_PARAMETERS__SET_UP_TIME = eINSTANCE.getTimeParameters_SetUpTime();

		/**
		 * The meta object literal for the '<em><b>Processing Time</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TIME_PARAMETERS__PROCESSING_TIME = eINSTANCE.getTimeParameters_ProcessingTime();

		/**
		 * The meta object literal for the '<em><b>Validation Time</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TIME_PARAMETERS__VALIDATION_TIME = eINSTANCE.getTimeParameters_ValidationTime();

		/**
		 * The meta object literal for the '<em><b>Rework Time</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference TIME_PARAMETERS__REWORK_TIME = eINSTANCE.getTimeParameters_ReworkTime();

		/**
		 * The meta object literal for the '<em><b>Time Unit</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TIME_PARAMETERS__TIME_UNIT = eINSTANCE.getTimeParameters_TimeUnit();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.StringParameterTypeImpl <em>String Parameter Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.StringParameterTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getStringParameterType()
		 * @generated
		 */
		EClass STRING_PARAMETER_TYPE = eINSTANCE.getStringParameterType();

		/**
		 * The meta object literal for the '<em><b>Value</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute STRING_PARAMETER_TYPE__VALUE = eINSTANCE.getStringParameterType_Value();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ScenarioImpl <em>Scenario</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ScenarioImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getScenario()
		 * @generated
		 */
		EClass SCENARIO = eINSTANCE.getScenario();

		/**
		 * The meta object literal for the '<em><b>Scenario Parameters</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCENARIO__SCENARIO_PARAMETERS = eINSTANCE.getScenario_ScenarioParameters();

		/**
		 * The meta object literal for the '<em><b>Element Parameters</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCENARIO__ELEMENT_PARAMETERS = eINSTANCE.getScenario_ElementParameters();

		/**
		 * The meta object literal for the '<em><b>Calendar</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCENARIO__CALENDAR = eINSTANCE.getScenario_Calendar();

		/**
		 * The meta object literal for the '<em><b>Vendor Extension</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCENARIO__VENDOR_EXTENSION = eINSTANCE.getScenario_VendorExtension();

		/**
		 * The meta object literal for the '<em><b>Author</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCENARIO__AUTHOR = eINSTANCE.getScenario_Author();

		/**
		 * The meta object literal for the '<em><b>Created</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCENARIO__CREATED = eINSTANCE.getScenario_Created();

		/**
		 * The meta object literal for the '<em><b>Description</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCENARIO__DESCRIPTION = eINSTANCE.getScenario_Description();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCENARIO__ID = eINSTANCE.getScenario_Id();

		/**
		 * The meta object literal for the '<em><b>Inherits</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCENARIO__INHERITS = eINSTANCE.getScenario_Inherits();

		/**
		 * The meta object literal for the '<em><b>Modified</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCENARIO__MODIFIED = eINSTANCE.getScenario_Modified();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCENARIO__NAME = eINSTANCE.getScenario_Name();

		/**
		 * The meta object literal for the '<em><b>Result</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCENARIO__RESULT = eINSTANCE.getScenario_Result();

		/**
		 * The meta object literal for the '<em><b>Vendor</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCENARIO__VENDOR = eINSTANCE.getScenario_Vendor();

		/**
		 * The meta object literal for the '<em><b>Version</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCENARIO__VERSION = eINSTANCE.getScenario_Version();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ResourceParametersImpl <em>Resource Parameters</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ResourceParametersImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getResourceParameters()
		 * @generated
		 */
		EClass RESOURCE_PARAMETERS = eINSTANCE.getResourceParameters();

		/**
		 * The meta object literal for the '<em><b>Selection</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE_PARAMETERS__SELECTION = eINSTANCE.getResourceParameters_Selection();

		/**
		 * The meta object literal for the '<em><b>Availability</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE_PARAMETERS__AVAILABILITY = eINSTANCE.getResourceParameters_Availability();

		/**
		 * The meta object literal for the '<em><b>Quantity</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE_PARAMETERS__QUANTITY = eINSTANCE.getResourceParameters_Quantity();

		/**
		 * The meta object literal for the '<em><b>Workinghours</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE_PARAMETERS__WORKINGHOURS = eINSTANCE.getResourceParameters_Workinghours();

		/**
		 * The meta object literal for the '<em><b>Role</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference RESOURCE_PARAMETERS__ROLE = eINSTANCE.getResourceParameters_Role();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ScenarioParametersImpl <em>Scenario Parameters</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ScenarioParametersImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getScenarioParameters()
		 * @generated
		 */
		EClass SCENARIO_PARAMETERS = eINSTANCE.getScenarioParameters();

		/**
		 * The meta object literal for the '<em><b>Start</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCENARIO_PARAMETERS__START = eINSTANCE.getScenarioParameters_Start();

		/**
		 * The meta object literal for the '<em><b>Duration</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCENARIO_PARAMETERS__DURATION = eINSTANCE.getScenarioParameters_Duration();

		/**
		 * The meta object literal for the '<em><b>Property Parameters</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference SCENARIO_PARAMETERS__PROPERTY_PARAMETERS = eINSTANCE.getScenarioParameters_PropertyParameters();

		/**
		 * The meta object literal for the '<em><b>Base Currency Unit</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCENARIO_PARAMETERS__BASE_CURRENCY_UNIT = eINSTANCE.getScenarioParameters_BaseCurrencyUnit();

		/**
		 * The meta object literal for the '<em><b>Base Time Unit</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCENARIO_PARAMETERS__BASE_TIME_UNIT = eINSTANCE.getScenarioParameters_BaseTimeUnit();

		/**
		 * The meta object literal for the '<em><b>Replication</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCENARIO_PARAMETERS__REPLICATION = eINSTANCE.getScenarioParameters_Replication();

		/**
		 * The meta object literal for the '<em><b>Seed</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SCENARIO_PARAMETERS__SEED = eINSTANCE.getScenarioParameters_Seed();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.VendorExtensionImpl <em>Vendor Extension</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.VendorExtensionImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getVendorExtension()
		 * @generated
		 */
		EClass VENDOR_EXTENSION = eINSTANCE.getVendorExtension();

		/**
		 * The meta object literal for the '<em><b>Any</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VENDOR_EXTENSION__ANY = eINSTANCE.getVendorExtension_Any();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VENDOR_EXTENSION__NAME = eINSTANCE.getVendorExtension_Name();

		/**
		 * The meta object literal for the '<em><b>Any Attribute</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute VENDOR_EXTENSION__ANY_ATTRIBUTE = eINSTANCE.getVendorExtension_AnyAttribute();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.TriangularDistributionTypeImpl <em>Triangular Distribution Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.TriangularDistributionTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getTriangularDistributionType()
		 * @generated
		 */
		EClass TRIANGULAR_DISTRIBUTION_TYPE = eINSTANCE.getTriangularDistributionType();

		/**
		 * The meta object literal for the '<em><b>Max</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRIANGULAR_DISTRIBUTION_TYPE__MAX = eINSTANCE.getTriangularDistributionType_Max();

		/**
		 * The meta object literal for the '<em><b>Min</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRIANGULAR_DISTRIBUTION_TYPE__MIN = eINSTANCE.getTriangularDistributionType_Min();

		/**
		 * The meta object literal for the '<em><b>Most Likely</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRIANGULAR_DISTRIBUTION_TYPE__MOST_LIKELY = eINSTANCE.getTriangularDistributionType_MostLikely();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.TruncatedNormalDistributionTypeImpl <em>Truncated Normal Distribution Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.TruncatedNormalDistributionTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getTruncatedNormalDistributionType()
		 * @generated
		 */
		EClass TRUNCATED_NORMAL_DISTRIBUTION_TYPE = eINSTANCE.getTruncatedNormalDistributionType();

		/**
		 * The meta object literal for the '<em><b>Max</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRUNCATED_NORMAL_DISTRIBUTION_TYPE__MAX = eINSTANCE.getTruncatedNormalDistributionType_Max();

		/**
		 * The meta object literal for the '<em><b>Mean</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRUNCATED_NORMAL_DISTRIBUTION_TYPE__MEAN = eINSTANCE.getTruncatedNormalDistributionType_Mean();

		/**
		 * The meta object literal for the '<em><b>Min</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRUNCATED_NORMAL_DISTRIBUTION_TYPE__MIN = eINSTANCE.getTruncatedNormalDistributionType_Min();

		/**
		 * The meta object literal for the '<em><b>Standard Deviation</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute TRUNCATED_NORMAL_DISTRIBUTION_TYPE__STANDARD_DEVIATION = eINSTANCE.getTruncatedNormalDistributionType_StandardDeviation();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.UniformDistributionTypeImpl <em>Uniform Distribution Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.UniformDistributionTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getUniformDistributionType()
		 * @generated
		 */
		EClass UNIFORM_DISTRIBUTION_TYPE = eINSTANCE.getUniformDistributionType();

		/**
		 * The meta object literal for the '<em><b>Max</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNIFORM_DISTRIBUTION_TYPE__MAX = eINSTANCE.getUniformDistributionType_Max();

		/**
		 * The meta object literal for the '<em><b>Min</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute UNIFORM_DISTRIBUTION_TYPE__MIN = eINSTANCE.getUniformDistributionType_Min();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.UserDistributionDataPointTypeImpl <em>User Distribution Data Point Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.UserDistributionDataPointTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getUserDistributionDataPointType()
		 * @generated
		 */
		EClass USER_DISTRIBUTION_DATA_POINT_TYPE = eINSTANCE.getUserDistributionDataPointType();

		/**
		 * The meta object literal for the '<em><b>Parameter Value Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER_DISTRIBUTION_DATA_POINT_TYPE__PARAMETER_VALUE_GROUP = eINSTANCE.getUserDistributionDataPointType_ParameterValueGroup();

		/**
		 * The meta object literal for the '<em><b>Parameter Value</b></em>' containment reference feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference USER_DISTRIBUTION_DATA_POINT_TYPE__PARAMETER_VALUE = eINSTANCE.getUserDistributionDataPointType_ParameterValue();

		/**
		 * The meta object literal for the '<em><b>Probability</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER_DISTRIBUTION_DATA_POINT_TYPE__PROBABILITY = eINSTANCE.getUserDistributionDataPointType_Probability();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.UserDistributionTypeImpl <em>User Distribution Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.UserDistributionTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getUserDistributionType()
		 * @generated
		 */
		EClass USER_DISTRIBUTION_TYPE = eINSTANCE.getUserDistributionType();

		/**
		 * The meta object literal for the '<em><b>Group</b></em>' attribute list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute USER_DISTRIBUTION_TYPE__GROUP = eINSTANCE.getUserDistributionType_Group();

		/**
		 * The meta object literal for the '<em><b>User Distribution Data Point</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference USER_DISTRIBUTION_TYPE__USER_DISTRIBUTION_DATA_POINT = eINSTANCE.getUserDistributionType_UserDistributionDataPoint();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.WeibullDistributionTypeImpl <em>Weibull Distribution Type</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.WeibullDistributionTypeImpl
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getWeibullDistributionType()
		 * @generated
		 */
		EClass WEIBULL_DISTRIBUTION_TYPE = eINSTANCE.getWeibullDistributionType();

		/**
		 * The meta object literal for the '<em><b>Scale</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WEIBULL_DISTRIBUTION_TYPE__SCALE = eINSTANCE.getWeibullDistributionType_Scale();

		/**
		 * The meta object literal for the '<em><b>Shape</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute WEIBULL_DISTRIBUTION_TYPE__SHAPE = eINSTANCE.getWeibullDistributionType_Shape();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ResultType <em>Result Type</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ResultType
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getResultType()
		 * @generated
		 */
		EEnum RESULT_TYPE = eINSTANCE.getResultType();

		/**
		 * The meta object literal for the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TimeUnit <em>Time Unit</em>}' enum.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TimeUnit
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getTimeUnit()
		 * @generated
		 */
		EEnum TIME_UNIT = eINSTANCE.getTimeUnit();

		/**
		 * The meta object literal for the '<em>Package Name Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getPackageNameType()
		 * @generated
		 */
		EDataType PACKAGE_NAME_TYPE = eINSTANCE.getPackageNameType();

		/**
		 * The meta object literal for the '<em>Priority Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.math.BigInteger
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getPriorityType()
		 * @generated
		 */
		EDataType PRIORITY_TYPE = eINSTANCE.getPriorityType();

		/**
		 * The meta object literal for the '<em>Rule Flow Group Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getRuleFlowGroupType()
		 * @generated
		 */
		EDataType RULE_FLOW_GROUP_TYPE = eINSTANCE.getRuleFlowGroupType();

		/**
		 * The meta object literal for the '<em>Task Name Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getTaskNameType()
		 * @generated
		 */
		EDataType TASK_NAME_TYPE = eINSTANCE.getTaskNameType();

		/**
		 * The meta object literal for the '<em>Version Type</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see java.lang.String
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getVersionType()
		 * @generated
		 */
		EDataType VERSION_TYPE = eINSTANCE.getVersionType();

		/**
		 * The meta object literal for the '<em>Result Type Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ResultType
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getResultTypeObject()
		 * @generated
		 */
		EDataType RESULT_TYPE_OBJECT = eINSTANCE.getResultTypeObject();

		/**
		 * The meta object literal for the '<em>Time Unit Object</em>' data type.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TimeUnit
		 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.ModelPackageImpl#getTimeUnitObject()
		 * @generated
		 */
		EDataType TIME_UNIT_OBJECT = eINSTANCE.getTimeUnitObject();

	}

} //ModelPackage

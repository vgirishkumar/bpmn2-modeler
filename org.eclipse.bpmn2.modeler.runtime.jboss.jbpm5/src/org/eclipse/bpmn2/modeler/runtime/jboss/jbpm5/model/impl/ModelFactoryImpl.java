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
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl;

import java.math.BigInteger;

import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.*;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.GlobalType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ImportType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelFactory;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.OnEntryScriptType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.OnExitScriptType;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.plugin.EcorePlugin;
import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Factory</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ModelFactoryImpl extends EFactoryImpl implements ModelFactory {
	/**
	 * Creates the default factory implementation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static ModelFactory init() {
		try {
			ModelFactory theModelFactory = (ModelFactory)EPackage.Registry.INSTANCE.getEFactory("http://www.jboss.org/drools"); 
			if (theModelFactory != null) {
				return theModelFactory;
			}
		}
		catch (Exception exception) {
			EcorePlugin.INSTANCE.log(exception);
		}
		return new ModelFactoryImpl();
	}

	/**
	 * Creates an instance of the factory.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelFactoryImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public EObject create(EClass eClass) {
		switch (eClass.getClassifierID()) {
			case ModelPackage.BETA_DISTRIBUTION_TYPE: return createBetaDistributionType();
			case ModelPackage.BINOMIAL_DISTRIBUTION_TYPE: return createBinomialDistributionType();
			case ModelPackage.BOOLEAN_PARAMETER_TYPE: return createBooleanParameterType();
			case ModelPackage.CALENDAR: return createCalendar();
			case ModelPackage.DOCUMENT_ROOT: return createDocumentRoot();
			case ModelPackage.GLOBAL_TYPE: return createGlobalType();
			case ModelPackage.CONTROL_PARAMETERS: return createControlParameters();
			case ModelPackage.CONSTANT_PARAMETER: return createConstantParameter();
			case ModelPackage.COST_PARAMETERS: return createCostParameters();
			case ModelPackage.IMPORT_TYPE: return createImportType();
			case ModelPackage.DATE_TIME_PARAMETER_TYPE: return createDateTimeParameterType();
			case ModelPackage.DECIMAL_PARAMETER_TYPE: return createDecimalParameterType();
			case ModelPackage.ON_ENTRY_SCRIPT_TYPE: return createOnEntryScriptType();
			case ModelPackage.ELEMENT_PARAMETERS: return createElementParameters();
			case ModelPackage.ON_EXIT_SCRIPT_TYPE: return createOnExitScriptType();
			case ModelPackage.DURATION_PARAMETER_TYPE: return createDurationParameterType();
			case ModelPackage.ENUM_PARAMETER_TYPE: return createEnumParameterType();
			case ModelPackage.ERLANG_DISTRIBUTION_TYPE: return createErlangDistributionType();
			case ModelPackage.EXPRESSION_PARAMETER_TYPE: return createExpressionParameterType();
			case ModelPackage.FLOATING_PARAMETER_TYPE: return createFloatingParameterType();
			case ModelPackage.GAMMA_DISTRIBUTION_TYPE: return createGammaDistributionType();
			case ModelPackage.LOG_NORMAL_DISTRIBUTION_TYPE: return createLogNormalDistributionType();
			case ModelPackage.METADATA_TYPE: return createMetadataType();
			case ModelPackage.NEGATIVE_EXPONENTIAL_DISTRIBUTION_TYPE: return createNegativeExponentialDistributionType();
			case ModelPackage.NORMAL_DISTRIBUTION_TYPE: return createNormalDistributionType();
			case ModelPackage.PARAMETER: return createParameter();
			case ModelPackage.NUMERIC_PARAMETER_TYPE: return createNumericParameterType();
			case ModelPackage.METAENTRY_TYPE: return createMetaentryType();
			case ModelPackage.DISTRIBUTION_PARAMETER: return createDistributionParameter();
			case ModelPackage.PROCESS_ANALYSIS_DATA_TYPE: return createProcessAnalysisDataType();
			case ModelPackage.PROPERTY_PARAMETERS: return createPropertyParameters();
			case ModelPackage.RANDOM_DISTRIBUTION_TYPE: return createRandomDistributionType();
			case ModelPackage.PARAMETER_VALUE: return createParameterValue();
			case ModelPackage.POISSON_DISTRIBUTION_TYPE: return createPoissonDistributionType();
			case ModelPackage.PROPERTY_TYPE: return createPropertyType();
			case ModelPackage.PRIORITY_PARAMETERS: return createPriorityParameters();
			case ModelPackage.TIME_PARAMETERS: return createTimeParameters();
			case ModelPackage.STRING_PARAMETER_TYPE: return createStringParameterType();
			case ModelPackage.SCENARIO: return createScenario();
			case ModelPackage.RESOURCE_PARAMETERS: return createResourceParameters();
			case ModelPackage.SCENARIO_PARAMETERS: return createScenarioParameters();
			case ModelPackage.VENDOR_EXTENSION: return createVendorExtension();
			case ModelPackage.TRIANGULAR_DISTRIBUTION_TYPE: return createTriangularDistributionType();
			case ModelPackage.TRUNCATED_NORMAL_DISTRIBUTION_TYPE: return createTruncatedNormalDistributionType();
			case ModelPackage.UNIFORM_DISTRIBUTION_TYPE: return createUniformDistributionType();
			case ModelPackage.USER_DISTRIBUTION_DATA_POINT_TYPE: return createUserDistributionDataPointType();
			case ModelPackage.USER_DISTRIBUTION_TYPE: return createUserDistributionType();
			case ModelPackage.WEIBULL_DISTRIBUTION_TYPE: return createWeibullDistributionType();
			default:
				throw new IllegalArgumentException("The class '" + eClass.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object createFromString(EDataType eDataType, String initialValue) {
		switch (eDataType.getClassifierID()) {
			case ModelPackage.RESULT_TYPE:
				return createResultTypeFromString(eDataType, initialValue);
			case ModelPackage.TIME_UNIT:
				return createTimeUnitFromString(eDataType, initialValue);
			case ModelPackage.PACKAGE_NAME_TYPE:
				return createPackageNameTypeFromString(eDataType, initialValue);
			case ModelPackage.PRIORITY_TYPE:
				return createPriorityTypeFromString(eDataType, initialValue);
			case ModelPackage.RULE_FLOW_GROUP_TYPE:
				return createRuleFlowGroupTypeFromString(eDataType, initialValue);
			case ModelPackage.TASK_NAME_TYPE:
				return createTaskNameTypeFromString(eDataType, initialValue);
			case ModelPackage.VERSION_TYPE:
				return createVersionTypeFromString(eDataType, initialValue);
			case ModelPackage.RESULT_TYPE_OBJECT:
				return createResultTypeObjectFromString(eDataType, initialValue);
			case ModelPackage.TIME_UNIT_OBJECT:
				return createTimeUnitObjectFromString(eDataType, initialValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String convertToString(EDataType eDataType, Object instanceValue) {
		switch (eDataType.getClassifierID()) {
			case ModelPackage.RESULT_TYPE:
				return convertResultTypeToString(eDataType, instanceValue);
			case ModelPackage.TIME_UNIT:
				return convertTimeUnitToString(eDataType, instanceValue);
			case ModelPackage.PACKAGE_NAME_TYPE:
				return convertPackageNameTypeToString(eDataType, instanceValue);
			case ModelPackage.PRIORITY_TYPE:
				return convertPriorityTypeToString(eDataType, instanceValue);
			case ModelPackage.RULE_FLOW_GROUP_TYPE:
				return convertRuleFlowGroupTypeToString(eDataType, instanceValue);
			case ModelPackage.TASK_NAME_TYPE:
				return convertTaskNameTypeToString(eDataType, instanceValue);
			case ModelPackage.VERSION_TYPE:
				return convertVersionTypeToString(eDataType, instanceValue);
			case ModelPackage.RESULT_TYPE_OBJECT:
				return convertResultTypeObjectToString(eDataType, instanceValue);
			case ModelPackage.TIME_UNIT_OBJECT:
				return convertTimeUnitObjectToString(eDataType, instanceValue);
			default:
				throw new IllegalArgumentException("The datatype '" + eDataType.getName() + "' is not a valid classifier");
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BetaDistributionType createBetaDistributionType() {
		BetaDistributionTypeImpl betaDistributionType = new BetaDistributionTypeImpl();
		return betaDistributionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BinomialDistributionType createBinomialDistributionType() {
		BinomialDistributionTypeImpl binomialDistributionType = new BinomialDistributionTypeImpl();
		return binomialDistributionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanParameterType createBooleanParameterType() {
		BooleanParameterTypeImpl booleanParameterType = new BooleanParameterTypeImpl();
		return booleanParameterType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Calendar createCalendar() {
		CalendarImpl calendar = new CalendarImpl();
		return calendar;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DocumentRoot createDocumentRoot() {
		DocumentRootImpl documentRoot = new DocumentRootImpl();
		return documentRoot;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GlobalType createGlobalType() {
		GlobalTypeImpl globalType = new GlobalTypeImpl();
		return globalType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ControlParameters createControlParameters() {
		ControlParametersImpl controlParameters = new ControlParametersImpl();
		return controlParameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ConstantParameter createConstantParameter() {
		ConstantParameterImpl constantParameter = new ConstantParameterImpl();
		return constantParameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CostParameters createCostParameters() {
		CostParametersImpl costParameters = new CostParametersImpl();
		return costParameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ImportType createImportType() {
		ImportTypeImpl importType = new ImportTypeImpl();
		return importType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DateTimeParameterType createDateTimeParameterType() {
		DateTimeParameterTypeImpl dateTimeParameterType = new DateTimeParameterTypeImpl();
		return dateTimeParameterType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DecimalParameterType createDecimalParameterType() {
		DecimalParameterTypeImpl decimalParameterType = new DecimalParameterTypeImpl();
		return decimalParameterType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OnEntryScriptType createOnEntryScriptType() {
		OnEntryScriptTypeImpl onEntryScriptType = new OnEntryScriptTypeImpl();
		return onEntryScriptType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ElementParameters createElementParameters() {
		ElementParametersImpl elementParameters = new ElementParametersImpl();
		return elementParameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OnExitScriptType createOnExitScriptType() {
		OnExitScriptTypeImpl onExitScriptType = new OnExitScriptTypeImpl();
		return onExitScriptType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DurationParameterType createDurationParameterType() {
		DurationParameterTypeImpl durationParameterType = new DurationParameterTypeImpl();
		return durationParameterType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumParameterType createEnumParameterType() {
		EnumParameterTypeImpl enumParameterType = new EnumParameterTypeImpl();
		return enumParameterType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ErlangDistributionType createErlangDistributionType() {
		ErlangDistributionTypeImpl erlangDistributionType = new ErlangDistributionTypeImpl();
		return erlangDistributionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExpressionParameterType createExpressionParameterType() {
		ExpressionParameterTypeImpl expressionParameterType = new ExpressionParameterTypeImpl();
		return expressionParameterType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FloatingParameterType createFloatingParameterType() {
		FloatingParameterTypeImpl floatingParameterType = new FloatingParameterTypeImpl();
		return floatingParameterType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GammaDistributionType createGammaDistributionType() {
		GammaDistributionTypeImpl gammaDistributionType = new GammaDistributionTypeImpl();
		return gammaDistributionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LogNormalDistributionType createLogNormalDistributionType() {
		LogNormalDistributionTypeImpl logNormalDistributionType = new LogNormalDistributionTypeImpl();
		return logNormalDistributionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetadataType createMetadataType() {
		MetadataTypeImpl metadataType = new MetadataTypeImpl();
		return metadataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NegativeExponentialDistributionType createNegativeExponentialDistributionType() {
		NegativeExponentialDistributionTypeImpl negativeExponentialDistributionType = new NegativeExponentialDistributionTypeImpl();
		return negativeExponentialDistributionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NormalDistributionType createNormalDistributionType() {
		NormalDistributionTypeImpl normalDistributionType = new NormalDistributionTypeImpl();
		return normalDistributionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Parameter createParameter() {
		ParameterImpl parameter = new ParameterImpl();
		return parameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NumericParameterType createNumericParameterType() {
		NumericParameterTypeImpl numericParameterType = new NumericParameterTypeImpl();
		return numericParameterType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetaentryType createMetaentryType() {
		MetaentryTypeImpl metaentryType = new MetaentryTypeImpl();
		return metaentryType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DistributionParameter createDistributionParameter() {
		DistributionParameterImpl distributionParameter = new DistributionParameterImpl();
		return distributionParameter;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessAnalysisDataType createProcessAnalysisDataType() {
		ProcessAnalysisDataTypeImpl processAnalysisDataType = new ProcessAnalysisDataTypeImpl();
		return processAnalysisDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyParameters createPropertyParameters() {
		PropertyParametersImpl propertyParameters = new PropertyParametersImpl();
		return propertyParameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RandomDistributionType createRandomDistributionType() {
		RandomDistributionTypeImpl randomDistributionType = new RandomDistributionTypeImpl();
		return randomDistributionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParameterValue createParameterValue() {
		ParameterValueImpl parameterValue = new ParameterValueImpl();
		return parameterValue;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PoissonDistributionType createPoissonDistributionType() {
		PoissonDistributionTypeImpl poissonDistributionType = new PoissonDistributionTypeImpl();
		return poissonDistributionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PropertyType createPropertyType() {
		PropertyTypeImpl propertyType = new PropertyTypeImpl();
		return propertyType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PriorityParameters createPriorityParameters() {
		PriorityParametersImpl priorityParameters = new PriorityParametersImpl();
		return priorityParameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TimeParameters createTimeParameters() {
		TimeParametersImpl timeParameters = new TimeParametersImpl();
		return timeParameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringParameterType createStringParameterType() {
		StringParameterTypeImpl stringParameterType = new StringParameterTypeImpl();
		return stringParameterType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public Scenario createScenario() {
		ScenarioImpl scenario = new ScenarioImpl();
		return scenario;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResourceParameters createResourceParameters() {
		ResourceParametersImpl resourceParameters = new ResourceParametersImpl();
		return resourceParameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ScenarioParameters createScenarioParameters() {
		ScenarioParametersImpl scenarioParameters = new ScenarioParametersImpl();
		return scenarioParameters;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public VendorExtension createVendorExtension() {
		VendorExtensionImpl vendorExtension = new VendorExtensionImpl();
		return vendorExtension;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TriangularDistributionType createTriangularDistributionType() {
		TriangularDistributionTypeImpl triangularDistributionType = new TriangularDistributionTypeImpl();
		return triangularDistributionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TruncatedNormalDistributionType createTruncatedNormalDistributionType() {
		TruncatedNormalDistributionTypeImpl truncatedNormalDistributionType = new TruncatedNormalDistributionTypeImpl();
		return truncatedNormalDistributionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UniformDistributionType createUniformDistributionType() {
		UniformDistributionTypeImpl uniformDistributionType = new UniformDistributionTypeImpl();
		return uniformDistributionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UserDistributionDataPointType createUserDistributionDataPointType() {
		UserDistributionDataPointTypeImpl userDistributionDataPointType = new UserDistributionDataPointTypeImpl();
		return userDistributionDataPointType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UserDistributionType createUserDistributionType() {
		UserDistributionTypeImpl userDistributionType = new UserDistributionTypeImpl();
		return userDistributionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WeibullDistributionType createWeibullDistributionType() {
		WeibullDistributionTypeImpl weibullDistributionType = new WeibullDistributionTypeImpl();
		return weibullDistributionType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResultType createResultTypeFromString(EDataType eDataType, String initialValue) {
		ResultType result = ResultType.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertResultTypeToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TimeUnit createTimeUnitFromString(EDataType eDataType, String initialValue) {
		TimeUnit result = TimeUnit.get(initialValue);
		if (result == null) throw new IllegalArgumentException("The value '" + initialValue + "' is not a valid enumerator of '" + eDataType.getName() + "'");
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTimeUnitToString(EDataType eDataType, Object instanceValue) {
		return instanceValue == null ? null : instanceValue.toString();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createPackageNameTypeFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPackageNameTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BigInteger createPriorityTypeFromString(EDataType eDataType, String initialValue) {
		return (BigInteger)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.INTEGER, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertPriorityTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.INTEGER, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createRuleFlowGroupTypeFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertRuleFlowGroupTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createTaskNameTypeFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTaskNameTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String createVersionTypeFromString(EDataType eDataType, String initialValue) {
		return (String)XMLTypeFactory.eINSTANCE.createFromString(XMLTypePackage.Literals.STRING, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertVersionTypeToString(EDataType eDataType, Object instanceValue) {
		return XMLTypeFactory.eINSTANCE.convertToString(XMLTypePackage.Literals.STRING, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ResultType createResultTypeObjectFromString(EDataType eDataType, String initialValue) {
		return createResultTypeFromString(ModelPackage.Literals.RESULT_TYPE, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertResultTypeObjectToString(EDataType eDataType, Object instanceValue) {
		return convertResultTypeToString(ModelPackage.Literals.RESULT_TYPE, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TimeUnit createTimeUnitObjectFromString(EDataType eDataType, String initialValue) {
		return createTimeUnitFromString(ModelPackage.Literals.TIME_UNIT, initialValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String convertTimeUnitObjectToString(EDataType eDataType, Object instanceValue) {
		return convertTimeUnitToString(ModelPackage.Literals.TIME_UNIT, instanceValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelPackage getModelPackage() {
		return (ModelPackage)getEPackage();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @deprecated
	 * @generated
	 */
	@Deprecated
	public static ModelPackage getPackage() {
		return ModelPackage.eINSTANCE;
	}

} //ModelFactoryImpl

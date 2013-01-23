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
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.util;

import java.math.BigInteger;
import java.util.Map;

import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.*;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.GlobalType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ImportType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.OnEntryScriptType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.OnExitScriptType;
import org.eclipse.emf.common.util.DiagnosticChain;
import org.eclipse.emf.common.util.ResourceLocator;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EObjectValidator;
import org.eclipse.emf.ecore.xml.type.util.XMLTypeValidator;

/**
 * <!-- begin-user-doc -->
 * The <b>Validator</b> for the model.
 * <!-- end-user-doc -->
 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage
 * @generated
 */
public class ModelValidator extends EObjectValidator {
	/**
	 * The cached model package
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static final ModelValidator INSTANCE = new ModelValidator();

	/**
	 * A constant for the {@link org.eclipse.emf.common.util.Diagnostic#getSource() source} of diagnostic {@link org.eclipse.emf.common.util.Diagnostic#getCode() codes} from this package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.common.util.Diagnostic#getSource()
	 * @see org.eclipse.emf.common.util.Diagnostic#getCode()
	 * @generated
	 */
	public static final String DIAGNOSTIC_SOURCE = "org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model";

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static final int GENERATED_DIAGNOSTIC_CODE_COUNT = 0;

	/**
	 * A constant with a fixed name that can be used as the base value for additional hand written constants in a derived class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected static final int DIAGNOSTIC_CODE_COUNT = GENERATED_DIAGNOSTIC_CODE_COUNT;

	/**
	 * The cached base package validator.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected XMLTypeValidator xmlTypeValidator;

	/**
	 * Creates an instance of the switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelValidator() {
		super();
		xmlTypeValidator = XMLTypeValidator.INSTANCE;
	}

	/**
	 * Returns the package of this validator switch.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EPackage getEPackage() {
	  return ModelPackage.eINSTANCE;
	}

	/**
	 * Calls <code>validateXXX</code> for the corresponding classifier of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected boolean validate(int classifierID, Object value, DiagnosticChain diagnostics, Map<Object, Object> context) {
		switch (classifierID) {
			case ModelPackage.BETA_DISTRIBUTION_TYPE:
				return validateBetaDistributionType((BetaDistributionType)value, diagnostics, context);
			case ModelPackage.BINOMIAL_DISTRIBUTION_TYPE:
				return validateBinomialDistributionType((BinomialDistributionType)value, diagnostics, context);
			case ModelPackage.BOOLEAN_PARAMETER_TYPE:
				return validateBooleanParameterType((BooleanParameterType)value, diagnostics, context);
			case ModelPackage.CALENDAR:
				return validateCalendar((Calendar)value, diagnostics, context);
			case ModelPackage.DOCUMENT_ROOT:
				return validateDocumentRoot((DocumentRoot)value, diagnostics, context);
			case ModelPackage.GLOBAL_TYPE:
				return validateGlobalType((GlobalType)value, diagnostics, context);
			case ModelPackage.CONTROL_PARAMETERS:
				return validateControlParameters((ControlParameters)value, diagnostics, context);
			case ModelPackage.CONSTANT_PARAMETER:
				return validateConstantParameter((ConstantParameter)value, diagnostics, context);
			case ModelPackage.COST_PARAMETERS:
				return validateCostParameters((CostParameters)value, diagnostics, context);
			case ModelPackage.IMPORT_TYPE:
				return validateImportType((ImportType)value, diagnostics, context);
			case ModelPackage.DATE_TIME_PARAMETER_TYPE:
				return validateDateTimeParameterType((DateTimeParameterType)value, diagnostics, context);
			case ModelPackage.DECIMAL_PARAMETER_TYPE:
				return validateDecimalParameterType((DecimalParameterType)value, diagnostics, context);
			case ModelPackage.ON_ENTRY_SCRIPT_TYPE:
				return validateOnEntryScriptType((OnEntryScriptType)value, diagnostics, context);
			case ModelPackage.ELEMENT_PARAMETERS:
				return validateElementParameters((ElementParameters)value, diagnostics, context);
			case ModelPackage.ON_EXIT_SCRIPT_TYPE:
				return validateOnExitScriptType((OnExitScriptType)value, diagnostics, context);
			case ModelPackage.DURATION_PARAMETER_TYPE:
				return validateDurationParameterType((DurationParameterType)value, diagnostics, context);
			case ModelPackage.ENUM_PARAMETER_TYPE:
				return validateEnumParameterType((EnumParameterType)value, diagnostics, context);
			case ModelPackage.ERLANG_DISTRIBUTION_TYPE:
				return validateErlangDistributionType((ErlangDistributionType)value, diagnostics, context);
			case ModelPackage.EXPRESSION_PARAMETER_TYPE:
				return validateExpressionParameterType((ExpressionParameterType)value, diagnostics, context);
			case ModelPackage.FLOATING_PARAMETER_TYPE:
				return validateFloatingParameterType((FloatingParameterType)value, diagnostics, context);
			case ModelPackage.GAMMA_DISTRIBUTION_TYPE:
				return validateGammaDistributionType((GammaDistributionType)value, diagnostics, context);
			case ModelPackage.LOG_NORMAL_DISTRIBUTION_TYPE:
				return validateLogNormalDistributionType((LogNormalDistributionType)value, diagnostics, context);
			case ModelPackage.METADATA_TYPE:
				return validateMetadataType((MetadataType)value, diagnostics, context);
			case ModelPackage.NEGATIVE_EXPONENTIAL_DISTRIBUTION_TYPE:
				return validateNegativeExponentialDistributionType((NegativeExponentialDistributionType)value, diagnostics, context);
			case ModelPackage.NORMAL_DISTRIBUTION_TYPE:
				return validateNormalDistributionType((NormalDistributionType)value, diagnostics, context);
			case ModelPackage.PARAMETER:
				return validateParameter((Parameter)value, diagnostics, context);
			case ModelPackage.NUMERIC_PARAMETER_TYPE:
				return validateNumericParameterType((NumericParameterType)value, diagnostics, context);
			case ModelPackage.METAENTRY_TYPE:
				return validateMetaentryType((MetaentryType)value, diagnostics, context);
			case ModelPackage.DISTRIBUTION_PARAMETER:
				return validateDistributionParameter((DistributionParameter)value, diagnostics, context);
			case ModelPackage.PROCESS_ANALYSIS_DATA_TYPE:
				return validateProcessAnalysisDataType((ProcessAnalysisDataType)value, diagnostics, context);
			case ModelPackage.PROPERTY_PARAMETERS:
				return validatePropertyParameters((PropertyParameters)value, diagnostics, context);
			case ModelPackage.RANDOM_DISTRIBUTION_TYPE:
				return validateRandomDistributionType((RandomDistributionType)value, diagnostics, context);
			case ModelPackage.PARAMETER_VALUE:
				return validateParameterValue((ParameterValue)value, diagnostics, context);
			case ModelPackage.POISSON_DISTRIBUTION_TYPE:
				return validatePoissonDistributionType((PoissonDistributionType)value, diagnostics, context);
			case ModelPackage.PROPERTY_TYPE:
				return validatePropertyType((PropertyType)value, diagnostics, context);
			case ModelPackage.PRIORITY_PARAMETERS:
				return validatePriorityParameters((PriorityParameters)value, diagnostics, context);
			case ModelPackage.TIME_PARAMETERS:
				return validateTimeParameters((TimeParameters)value, diagnostics, context);
			case ModelPackage.STRING_PARAMETER_TYPE:
				return validateStringParameterType((StringParameterType)value, diagnostics, context);
			case ModelPackage.SCENARIO:
				return validateScenario((Scenario)value, diagnostics, context);
			case ModelPackage.RESOURCE_PARAMETERS:
				return validateResourceParameters((ResourceParameters)value, diagnostics, context);
			case ModelPackage.SCENARIO_PARAMETERS:
				return validateScenarioParameters((ScenarioParameters)value, diagnostics, context);
			case ModelPackage.VENDOR_EXTENSION:
				return validateVendorExtension((VendorExtension)value, diagnostics, context);
			case ModelPackage.TRIANGULAR_DISTRIBUTION_TYPE:
				return validateTriangularDistributionType((TriangularDistributionType)value, diagnostics, context);
			case ModelPackage.TRUNCATED_NORMAL_DISTRIBUTION_TYPE:
				return validateTruncatedNormalDistributionType((TruncatedNormalDistributionType)value, diagnostics, context);
			case ModelPackage.UNIFORM_DISTRIBUTION_TYPE:
				return validateUniformDistributionType((UniformDistributionType)value, diagnostics, context);
			case ModelPackage.USER_DISTRIBUTION_DATA_POINT_TYPE:
				return validateUserDistributionDataPointType((UserDistributionDataPointType)value, diagnostics, context);
			case ModelPackage.USER_DISTRIBUTION_TYPE:
				return validateUserDistributionType((UserDistributionType)value, diagnostics, context);
			case ModelPackage.WEIBULL_DISTRIBUTION_TYPE:
				return validateWeibullDistributionType((WeibullDistributionType)value, diagnostics, context);
			case ModelPackage.RESULT_TYPE:
				return validateResultType((ResultType)value, diagnostics, context);
			case ModelPackage.TIME_UNIT:
				return validateTimeUnit((TimeUnit)value, diagnostics, context);
			case ModelPackage.PACKAGE_NAME_TYPE:
				return validatePackageNameType((String)value, diagnostics, context);
			case ModelPackage.PRIORITY_TYPE:
				return validatePriorityType((BigInteger)value, diagnostics, context);
			case ModelPackage.RULE_FLOW_GROUP_TYPE:
				return validateRuleFlowGroupType((String)value, diagnostics, context);
			case ModelPackage.TASK_NAME_TYPE:
				return validateTaskNameType((String)value, diagnostics, context);
			case ModelPackage.VERSION_TYPE:
				return validateVersionType((String)value, diagnostics, context);
			case ModelPackage.RESULT_TYPE_OBJECT:
				return validateResultTypeObject((ResultType)value, diagnostics, context);
			case ModelPackage.TIME_UNIT_OBJECT:
				return validateTimeUnitObject((TimeUnit)value, diagnostics, context);
			default:
				return true;
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateBetaDistributionType(BetaDistributionType betaDistributionType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(betaDistributionType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateBinomialDistributionType(BinomialDistributionType binomialDistributionType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(binomialDistributionType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateBooleanParameterType(BooleanParameterType booleanParameterType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(booleanParameterType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCalendar(Calendar calendar, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(calendar, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDocumentRoot(DocumentRoot documentRoot, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(documentRoot, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateGlobalType(GlobalType globalType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(globalType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateControlParameters(ControlParameters controlParameters, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(controlParameters, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateConstantParameter(ConstantParameter constantParameter, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(constantParameter, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateCostParameters(CostParameters costParameters, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(costParameters, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateImportType(ImportType importType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(importType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDateTimeParameterType(DateTimeParameterType dateTimeParameterType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(dateTimeParameterType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDecimalParameterType(DecimalParameterType decimalParameterType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(decimalParameterType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateOnEntryScriptType(OnEntryScriptType onEntryScriptType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(onEntryScriptType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateElementParameters(ElementParameters elementParameters, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(elementParameters, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateOnExitScriptType(OnExitScriptType onExitScriptType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(onExitScriptType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDurationParameterType(DurationParameterType durationParameterType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(durationParameterType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateEnumParameterType(EnumParameterType enumParameterType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(enumParameterType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateErlangDistributionType(ErlangDistributionType erlangDistributionType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(erlangDistributionType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateExpressionParameterType(ExpressionParameterType expressionParameterType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(expressionParameterType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateFloatingParameterType(FloatingParameterType floatingParameterType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(floatingParameterType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateGammaDistributionType(GammaDistributionType gammaDistributionType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(gammaDistributionType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateLogNormalDistributionType(LogNormalDistributionType logNormalDistributionType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(logNormalDistributionType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetadataType(MetadataType metadataType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(metadataType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNegativeExponentialDistributionType(NegativeExponentialDistributionType negativeExponentialDistributionType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(negativeExponentialDistributionType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNormalDistributionType(NormalDistributionType normalDistributionType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(normalDistributionType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateParameter(Parameter parameter, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(parameter, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateNumericParameterType(NumericParameterType numericParameterType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(numericParameterType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateMetaentryType(MetaentryType metaentryType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(metaentryType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateDistributionParameter(DistributionParameter distributionParameter, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(distributionParameter, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateProcessAnalysisDataType(ProcessAnalysisDataType processAnalysisDataType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(processAnalysisDataType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePropertyParameters(PropertyParameters propertyParameters, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(propertyParameters, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRandomDistributionType(RandomDistributionType randomDistributionType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(randomDistributionType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateParameterValue(ParameterValue parameterValue, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(parameterValue, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePoissonDistributionType(PoissonDistributionType poissonDistributionType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(poissonDistributionType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePropertyType(PropertyType propertyType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(propertyType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePriorityParameters(PriorityParameters priorityParameters, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(priorityParameters, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTimeParameters(TimeParameters timeParameters, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(timeParameters, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateStringParameterType(StringParameterType stringParameterType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(stringParameterType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateScenario(Scenario scenario, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(scenario, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateResourceParameters(ResourceParameters resourceParameters, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(resourceParameters, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateScenarioParameters(ScenarioParameters scenarioParameters, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(scenarioParameters, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateVendorExtension(VendorExtension vendorExtension, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(vendorExtension, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTriangularDistributionType(TriangularDistributionType triangularDistributionType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(triangularDistributionType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTruncatedNormalDistributionType(TruncatedNormalDistributionType truncatedNormalDistributionType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(truncatedNormalDistributionType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateUniformDistributionType(UniformDistributionType uniformDistributionType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(uniformDistributionType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateUserDistributionDataPointType(UserDistributionDataPointType userDistributionDataPointType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(userDistributionDataPointType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateUserDistributionType(UserDistributionType userDistributionType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(userDistributionType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateWeibullDistributionType(WeibullDistributionType weibullDistributionType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return validate_EveryDefaultConstraint(weibullDistributionType, diagnostics, context);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateResultType(ResultType resultType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTimeUnit(TimeUnit timeUnit, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePackageNameType(String packageNameType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePriorityType(BigInteger priorityType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = validatePriorityType_Min(priorityType, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @see #validatePriorityType_Min
	 */
	public static final BigInteger PRIORITY_TYPE__MIN__VALUE = new BigInteger("1");

	/**
	 * Validates the Min constraint of '<em>Priority Type</em>'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validatePriorityType_Min(BigInteger priorityType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		boolean result = priorityType.compareTo(PRIORITY_TYPE__MIN__VALUE) >= 0;
		if (!result && diagnostics != null)
			reportMinViolation(ModelPackage.Literals.PRIORITY_TYPE, priorityType, PRIORITY_TYPE__MIN__VALUE, true, diagnostics, context);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateRuleFlowGroupType(String ruleFlowGroupType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTaskNameType(String taskNameType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateVersionType(String versionType, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateResultTypeObject(ResultType resultTypeObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public boolean validateTimeUnitObject(TimeUnit timeUnitObject, DiagnosticChain diagnostics, Map<Object, Object> context) {
		return true;
	}

	/**
	 * Returns the resource locator that will be used to fetch messages for this validator's diagnostics.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public ResourceLocator getResourceLocator() {
		// TODO
		// Specialize this to return a resource locator for messages specific to this validator.
		// Ensure that you remove @generated or mark it @generated NOT
		return super.getResourceLocator();
	}

} //ModelValidator

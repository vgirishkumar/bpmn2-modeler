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

import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.BetaDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.BinomialDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.BooleanParameterType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DateTimeParameterType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DecimalParameterType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DurationParameterType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.EnumParameterType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ErlangDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ExpressionParameterType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.FloatingParameterType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.GammaDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.GlobalType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.LogNormalDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.MetadataType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.MetaentryType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ImportType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.NegativeExponentialDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.NormalDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.NumericParameterType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.OnEntryScriptType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.OnExitScriptType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ParameterValue;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.PoissonDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ProcessAnalysisDataType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.RandomDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.StringParameterType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TriangularDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TruncatedNormalDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.UniformDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.UserDistributionDataPointType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.UserDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.WeibullDistributionType;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getBetaDistribution <em>Beta Distribution</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getParameterValue <em>Parameter Value</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getBinomialDistribution <em>Binomial Distribution</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getBooleanParameter <em>Boolean Parameter</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getDateTimeParameter <em>Date Time Parameter</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getDecimalParameter <em>Decimal Parameter</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getDurationParameter <em>Duration Parameter</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getEnumParameter <em>Enum Parameter</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getErlangDistribution <em>Erlang Distribution</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getExpressionParameter <em>Expression Parameter</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getFloatingParameter <em>Floating Parameter</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getGammaDistribution <em>Gamma Distribution</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getGlobal <em>Global</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getLogNormalDistribution <em>Log Normal Distribution</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getMetadata <em>Metadata</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getMetaentry <em>Metaentry</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getNegativeExponentialDistribution <em>Negative Exponential Distribution</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getNormalDistribution <em>Normal Distribution</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getNumericParameter <em>Numeric Parameter</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getOnEntryScript <em>On Entry Script</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getOnExitScript <em>On Exit Script</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getPoissonDistribution <em>Poisson Distribution</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getProcessAnalysisData <em>Process Analysis Data</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getRandomDistribution <em>Random Distribution</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getStringParameter <em>String Parameter</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getTriangularDistribution <em>Triangular Distribution</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getTruncatedNormalDistribution <em>Truncated Normal Distribution</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getUniformDistribution <em>Uniform Distribution</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getUserDistribution <em>User Distribution</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getUserDistributionDataPoint <em>User Distribution Data Point</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getWeibullDistribution <em>Weibull Distribution</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getPackageName <em>Package Name</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getPriority <em>Priority</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getRuleFlowGroup <em>Rule Flow Group</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getTaskName <em>Task Name</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.impl.DocumentRootImpl#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class DocumentRootImpl extends org.eclipse.bpmn2.impl.DocumentRootImpl implements DocumentRoot {
	/**
	 * The default value of the '{@link #getPackageName() <em>Package Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackageName()
	 * @generated
	 * @ordered
	 */
	protected static final String PACKAGE_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPackageName() <em>Package Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPackageName()
	 * @generated
	 * @ordered
	 */
	protected String packageName = PACKAGE_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getPriority() <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPriority()
	 * @generated
	 * @ordered
	 */
	protected static final BigInteger PRIORITY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getPriority() <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getPriority()
	 * @generated
	 * @ordered
	 */
	protected BigInteger priority = PRIORITY_EDEFAULT;

	/**
	 * The default value of the '{@link #getRuleFlowGroup() <em>Rule Flow Group</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuleFlowGroup()
	 * @generated
	 * @ordered
	 */
	protected static final String RULE_FLOW_GROUP_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getRuleFlowGroup() <em>Rule Flow Group</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getRuleFlowGroup()
	 * @generated
	 * @ordered
	 */
	protected String ruleFlowGroup = RULE_FLOW_GROUP_EDEFAULT;

	/**
	 * The default value of the '{@link #getTaskName() <em>Task Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTaskName()
	 * @generated
	 * @ordered
	 */
	protected static final String TASK_NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getTaskName() <em>Task Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getTaskName()
	 * @generated
	 * @ordered
	 */
	protected String taskName = TASK_NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected DocumentRootImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ModelPackage.Literals.DOCUMENT_ROOT;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BetaDistributionType getBetaDistribution() {
		return (BetaDistributionType)getMixed().get(ModelPackage.Literals.DOCUMENT_ROOT__BETA_DISTRIBUTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBetaDistribution(BetaDistributionType newBetaDistribution, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.DOCUMENT_ROOT__BETA_DISTRIBUTION, newBetaDistribution, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBetaDistribution(BetaDistributionType newBetaDistribution) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.DOCUMENT_ROOT__BETA_DISTRIBUTION, newBetaDistribution);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ParameterValue getParameterValue() {
		return (ParameterValue)getMixed().get(ModelPackage.Literals.DOCUMENT_ROOT__PARAMETER_VALUE, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetParameterValue(ParameterValue newParameterValue, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.DOCUMENT_ROOT__PARAMETER_VALUE, newParameterValue, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setParameterValue(ParameterValue newParameterValue) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.DOCUMENT_ROOT__PARAMETER_VALUE, newParameterValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BinomialDistributionType getBinomialDistribution() {
		return (BinomialDistributionType)getMixed().get(ModelPackage.Literals.DOCUMENT_ROOT__BINOMIAL_DISTRIBUTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBinomialDistribution(BinomialDistributionType newBinomialDistribution, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.DOCUMENT_ROOT__BINOMIAL_DISTRIBUTION, newBinomialDistribution, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBinomialDistribution(BinomialDistributionType newBinomialDistribution) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.DOCUMENT_ROOT__BINOMIAL_DISTRIBUTION, newBinomialDistribution);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BooleanParameterType getBooleanParameter() {
		return (BooleanParameterType)getMixed().get(ModelPackage.Literals.DOCUMENT_ROOT__BOOLEAN_PARAMETER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetBooleanParameter(BooleanParameterType newBooleanParameter, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.DOCUMENT_ROOT__BOOLEAN_PARAMETER, newBooleanParameter, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setBooleanParameter(BooleanParameterType newBooleanParameter) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.DOCUMENT_ROOT__BOOLEAN_PARAMETER, newBooleanParameter);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DateTimeParameterType getDateTimeParameter() {
		return (DateTimeParameterType)getMixed().get(ModelPackage.Literals.DOCUMENT_ROOT__DATE_TIME_PARAMETER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDateTimeParameter(DateTimeParameterType newDateTimeParameter, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.DOCUMENT_ROOT__DATE_TIME_PARAMETER, newDateTimeParameter, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDateTimeParameter(DateTimeParameterType newDateTimeParameter) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.DOCUMENT_ROOT__DATE_TIME_PARAMETER, newDateTimeParameter);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DecimalParameterType getDecimalParameter() {
		return (DecimalParameterType)getMixed().get(ModelPackage.Literals.DOCUMENT_ROOT__DECIMAL_PARAMETER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDecimalParameter(DecimalParameterType newDecimalParameter, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.DOCUMENT_ROOT__DECIMAL_PARAMETER, newDecimalParameter, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDecimalParameter(DecimalParameterType newDecimalParameter) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.DOCUMENT_ROOT__DECIMAL_PARAMETER, newDecimalParameter);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public DurationParameterType getDurationParameter() {
		return (DurationParameterType)getMixed().get(ModelPackage.Literals.DOCUMENT_ROOT__DURATION_PARAMETER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetDurationParameter(DurationParameterType newDurationParameter, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.DOCUMENT_ROOT__DURATION_PARAMETER, newDurationParameter, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setDurationParameter(DurationParameterType newDurationParameter) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.DOCUMENT_ROOT__DURATION_PARAMETER, newDurationParameter);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EnumParameterType getEnumParameter() {
		return (EnumParameterType)getMixed().get(ModelPackage.Literals.DOCUMENT_ROOT__ENUM_PARAMETER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetEnumParameter(EnumParameterType newEnumParameter, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.DOCUMENT_ROOT__ENUM_PARAMETER, newEnumParameter, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setEnumParameter(EnumParameterType newEnumParameter) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.DOCUMENT_ROOT__ENUM_PARAMETER, newEnumParameter);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ErlangDistributionType getErlangDistribution() {
		return (ErlangDistributionType)getMixed().get(ModelPackage.Literals.DOCUMENT_ROOT__ERLANG_DISTRIBUTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetErlangDistribution(ErlangDistributionType newErlangDistribution, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.DOCUMENT_ROOT__ERLANG_DISTRIBUTION, newErlangDistribution, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setErlangDistribution(ErlangDistributionType newErlangDistribution) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.DOCUMENT_ROOT__ERLANG_DISTRIBUTION, newErlangDistribution);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ExpressionParameterType getExpressionParameter() {
		return (ExpressionParameterType)getMixed().get(ModelPackage.Literals.DOCUMENT_ROOT__EXPRESSION_PARAMETER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetExpressionParameter(ExpressionParameterType newExpressionParameter, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.DOCUMENT_ROOT__EXPRESSION_PARAMETER, newExpressionParameter, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setExpressionParameter(ExpressionParameterType newExpressionParameter) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.DOCUMENT_ROOT__EXPRESSION_PARAMETER, newExpressionParameter);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public FloatingParameterType getFloatingParameter() {
		return (FloatingParameterType)getMixed().get(ModelPackage.Literals.DOCUMENT_ROOT__FLOATING_PARAMETER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetFloatingParameter(FloatingParameterType newFloatingParameter, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.DOCUMENT_ROOT__FLOATING_PARAMETER, newFloatingParameter, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setFloatingParameter(FloatingParameterType newFloatingParameter) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.DOCUMENT_ROOT__FLOATING_PARAMETER, newFloatingParameter);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GammaDistributionType getGammaDistribution() {
		return (GammaDistributionType)getMixed().get(ModelPackage.Literals.DOCUMENT_ROOT__GAMMA_DISTRIBUTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGammaDistribution(GammaDistributionType newGammaDistribution, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.DOCUMENT_ROOT__GAMMA_DISTRIBUTION, newGammaDistribution, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGammaDistribution(GammaDistributionType newGammaDistribution) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.DOCUMENT_ROOT__GAMMA_DISTRIBUTION, newGammaDistribution);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public GlobalType getGlobal() {
		return (GlobalType)getMixed().get(ModelPackage.Literals.DOCUMENT_ROOT__GLOBAL, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetGlobal(GlobalType newGlobal, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.DOCUMENT_ROOT__GLOBAL, newGlobal, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setGlobal(GlobalType newGlobal) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.DOCUMENT_ROOT__GLOBAL, newGlobal);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public LogNormalDistributionType getLogNormalDistribution() {
		return (LogNormalDistributionType)getMixed().get(ModelPackage.Literals.DOCUMENT_ROOT__LOG_NORMAL_DISTRIBUTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetLogNormalDistribution(LogNormalDistributionType newLogNormalDistribution, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.DOCUMENT_ROOT__LOG_NORMAL_DISTRIBUTION, newLogNormalDistribution, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setLogNormalDistribution(LogNormalDistributionType newLogNormalDistribution) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.DOCUMENT_ROOT__LOG_NORMAL_DISTRIBUTION, newLogNormalDistribution);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetadataType getMetadata() {
		return (MetadataType)getMixed().get(ModelPackage.Literals.DOCUMENT_ROOT__METADATA, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMetadata(MetadataType newMetadata, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.DOCUMENT_ROOT__METADATA, newMetadata, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMetadata(MetadataType newMetadata) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.DOCUMENT_ROOT__METADATA, newMetadata);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public MetaentryType getMetaentry() {
		return (MetaentryType)getMixed().get(ModelPackage.Literals.DOCUMENT_ROOT__METAENTRY, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetMetaentry(MetaentryType newMetaentry, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.DOCUMENT_ROOT__METAENTRY, newMetaentry, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setMetaentry(MetaentryType newMetaentry) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.DOCUMENT_ROOT__METAENTRY, newMetaentry);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NegativeExponentialDistributionType getNegativeExponentialDistribution() {
		return (NegativeExponentialDistributionType)getMixed().get(ModelPackage.Literals.DOCUMENT_ROOT__NEGATIVE_EXPONENTIAL_DISTRIBUTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNegativeExponentialDistribution(NegativeExponentialDistributionType newNegativeExponentialDistribution, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.DOCUMENT_ROOT__NEGATIVE_EXPONENTIAL_DISTRIBUTION, newNegativeExponentialDistribution, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNegativeExponentialDistribution(NegativeExponentialDistributionType newNegativeExponentialDistribution) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.DOCUMENT_ROOT__NEGATIVE_EXPONENTIAL_DISTRIBUTION, newNegativeExponentialDistribution);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NormalDistributionType getNormalDistribution() {
		return (NormalDistributionType)getMixed().get(ModelPackage.Literals.DOCUMENT_ROOT__NORMAL_DISTRIBUTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNormalDistribution(NormalDistributionType newNormalDistribution, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.DOCUMENT_ROOT__NORMAL_DISTRIBUTION, newNormalDistribution, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNormalDistribution(NormalDistributionType newNormalDistribution) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.DOCUMENT_ROOT__NORMAL_DISTRIBUTION, newNormalDistribution);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NumericParameterType getNumericParameter() {
		return (NumericParameterType)getMixed().get(ModelPackage.Literals.DOCUMENT_ROOT__NUMERIC_PARAMETER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetNumericParameter(NumericParameterType newNumericParameter, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.DOCUMENT_ROOT__NUMERIC_PARAMETER, newNumericParameter, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setNumericParameter(NumericParameterType newNumericParameter) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.DOCUMENT_ROOT__NUMERIC_PARAMETER, newNumericParameter);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OnEntryScriptType getOnEntryScript() {
		return (OnEntryScriptType)getMixed().get(ModelPackage.Literals.DOCUMENT_ROOT__ON_ENTRY_SCRIPT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOnEntryScript(OnEntryScriptType newOnEntryScript, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.DOCUMENT_ROOT__ON_ENTRY_SCRIPT, newOnEntryScript, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOnEntryScript(OnEntryScriptType newOnEntryScript) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.DOCUMENT_ROOT__ON_ENTRY_SCRIPT, newOnEntryScript);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public OnExitScriptType getOnExitScript() {
		return (OnExitScriptType)getMixed().get(ModelPackage.Literals.DOCUMENT_ROOT__ON_EXIT_SCRIPT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetOnExitScript(OnExitScriptType newOnExitScript, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.DOCUMENT_ROOT__ON_EXIT_SCRIPT, newOnExitScript, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setOnExitScript(OnExitScriptType newOnExitScript) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.DOCUMENT_ROOT__ON_EXIT_SCRIPT, newOnExitScript);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public PoissonDistributionType getPoissonDistribution() {
		return (PoissonDistributionType)getMixed().get(ModelPackage.Literals.DOCUMENT_ROOT__POISSON_DISTRIBUTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetPoissonDistribution(PoissonDistributionType newPoissonDistribution, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.DOCUMENT_ROOT__POISSON_DISTRIBUTION, newPoissonDistribution, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPoissonDistribution(PoissonDistributionType newPoissonDistribution) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.DOCUMENT_ROOT__POISSON_DISTRIBUTION, newPoissonDistribution);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ProcessAnalysisDataType getProcessAnalysisData() {
		return (ProcessAnalysisDataType)getMixed().get(ModelPackage.Literals.DOCUMENT_ROOT__PROCESS_ANALYSIS_DATA, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetProcessAnalysisData(ProcessAnalysisDataType newProcessAnalysisData, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.DOCUMENT_ROOT__PROCESS_ANALYSIS_DATA, newProcessAnalysisData, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setProcessAnalysisData(ProcessAnalysisDataType newProcessAnalysisData) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.DOCUMENT_ROOT__PROCESS_ANALYSIS_DATA, newProcessAnalysisData);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public RandomDistributionType getRandomDistribution() {
		return (RandomDistributionType)getMixed().get(ModelPackage.Literals.DOCUMENT_ROOT__RANDOM_DISTRIBUTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetRandomDistribution(RandomDistributionType newRandomDistribution, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.DOCUMENT_ROOT__RANDOM_DISTRIBUTION, newRandomDistribution, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRandomDistribution(RandomDistributionType newRandomDistribution) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.DOCUMENT_ROOT__RANDOM_DISTRIBUTION, newRandomDistribution);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public StringParameterType getStringParameter() {
		return (StringParameterType)getMixed().get(ModelPackage.Literals.DOCUMENT_ROOT__STRING_PARAMETER, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetStringParameter(StringParameterType newStringParameter, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.DOCUMENT_ROOT__STRING_PARAMETER, newStringParameter, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setStringParameter(StringParameterType newStringParameter) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.DOCUMENT_ROOT__STRING_PARAMETER, newStringParameter);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TriangularDistributionType getTriangularDistribution() {
		return (TriangularDistributionType)getMixed().get(ModelPackage.Literals.DOCUMENT_ROOT__TRIANGULAR_DISTRIBUTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTriangularDistribution(TriangularDistributionType newTriangularDistribution, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.DOCUMENT_ROOT__TRIANGULAR_DISTRIBUTION, newTriangularDistribution, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTriangularDistribution(TriangularDistributionType newTriangularDistribution) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.DOCUMENT_ROOT__TRIANGULAR_DISTRIBUTION, newTriangularDistribution);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public TruncatedNormalDistributionType getTruncatedNormalDistribution() {
		return (TruncatedNormalDistributionType)getMixed().get(ModelPackage.Literals.DOCUMENT_ROOT__TRUNCATED_NORMAL_DISTRIBUTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetTruncatedNormalDistribution(TruncatedNormalDistributionType newTruncatedNormalDistribution, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.DOCUMENT_ROOT__TRUNCATED_NORMAL_DISTRIBUTION, newTruncatedNormalDistribution, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTruncatedNormalDistribution(TruncatedNormalDistributionType newTruncatedNormalDistribution) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.DOCUMENT_ROOT__TRUNCATED_NORMAL_DISTRIBUTION, newTruncatedNormalDistribution);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UniformDistributionType getUniformDistribution() {
		return (UniformDistributionType)getMixed().get(ModelPackage.Literals.DOCUMENT_ROOT__UNIFORM_DISTRIBUTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUniformDistribution(UniformDistributionType newUniformDistribution, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.DOCUMENT_ROOT__UNIFORM_DISTRIBUTION, newUniformDistribution, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUniformDistribution(UniformDistributionType newUniformDistribution) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.DOCUMENT_ROOT__UNIFORM_DISTRIBUTION, newUniformDistribution);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UserDistributionType getUserDistribution() {
		return (UserDistributionType)getMixed().get(ModelPackage.Literals.DOCUMENT_ROOT__USER_DISTRIBUTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUserDistribution(UserDistributionType newUserDistribution, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.DOCUMENT_ROOT__USER_DISTRIBUTION, newUserDistribution, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUserDistribution(UserDistributionType newUserDistribution) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.DOCUMENT_ROOT__USER_DISTRIBUTION, newUserDistribution);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public UserDistributionDataPointType getUserDistributionDataPoint() {
		return (UserDistributionDataPointType)getMixed().get(ModelPackage.Literals.DOCUMENT_ROOT__USER_DISTRIBUTION_DATA_POINT, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetUserDistributionDataPoint(UserDistributionDataPointType newUserDistributionDataPoint, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.DOCUMENT_ROOT__USER_DISTRIBUTION_DATA_POINT, newUserDistributionDataPoint, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setUserDistributionDataPoint(UserDistributionDataPointType newUserDistributionDataPoint) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.DOCUMENT_ROOT__USER_DISTRIBUTION_DATA_POINT, newUserDistributionDataPoint);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public WeibullDistributionType getWeibullDistribution() {
		return (WeibullDistributionType)getMixed().get(ModelPackage.Literals.DOCUMENT_ROOT__WEIBULL_DISTRIBUTION, true);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public NotificationChain basicSetWeibullDistribution(WeibullDistributionType newWeibullDistribution, NotificationChain msgs) {
		return ((FeatureMap.Internal)getMixed()).basicAdd(ModelPackage.Literals.DOCUMENT_ROOT__WEIBULL_DISTRIBUTION, newWeibullDistribution, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWeibullDistribution(WeibullDistributionType newWeibullDistribution) {
		((FeatureMap.Internal)getMixed()).set(ModelPackage.Literals.DOCUMENT_ROOT__WEIBULL_DISTRIBUTION, newWeibullDistribution);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getPackageName() {
		return packageName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPackageName(String newPackageName) {
		String oldPackageName = packageName;
		packageName = newPackageName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.DOCUMENT_ROOT__PACKAGE_NAME, oldPackageName, packageName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public BigInteger getPriority() {
		return priority;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setPriority(BigInteger newPriority) {
		BigInteger oldPriority = priority;
		priority = newPriority;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.DOCUMENT_ROOT__PRIORITY, oldPriority, priority));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getRuleFlowGroup() {
		return ruleFlowGroup;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setRuleFlowGroup(String newRuleFlowGroup) {
		String oldRuleFlowGroup = ruleFlowGroup;
		ruleFlowGroup = newRuleFlowGroup;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.DOCUMENT_ROOT__RULE_FLOW_GROUP, oldRuleFlowGroup, ruleFlowGroup));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getTaskName() {
		return taskName;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setTaskName(String newTaskName) {
		String oldTaskName = taskName;
		taskName = newTaskName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.DOCUMENT_ROOT__TASK_NAME, oldTaskName, taskName));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ModelPackage.DOCUMENT_ROOT__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID) {
			case ModelPackage.DOCUMENT_ROOT__BETA_DISTRIBUTION:
				return basicSetBetaDistribution(null, msgs);
			case ModelPackage.DOCUMENT_ROOT__PARAMETER_VALUE:
				return basicSetParameterValue(null, msgs);
			case ModelPackage.DOCUMENT_ROOT__BINOMIAL_DISTRIBUTION:
				return basicSetBinomialDistribution(null, msgs);
			case ModelPackage.DOCUMENT_ROOT__BOOLEAN_PARAMETER:
				return basicSetBooleanParameter(null, msgs);
			case ModelPackage.DOCUMENT_ROOT__DATE_TIME_PARAMETER:
				return basicSetDateTimeParameter(null, msgs);
			case ModelPackage.DOCUMENT_ROOT__DECIMAL_PARAMETER:
				return basicSetDecimalParameter(null, msgs);
			case ModelPackage.DOCUMENT_ROOT__DURATION_PARAMETER:
				return basicSetDurationParameter(null, msgs);
			case ModelPackage.DOCUMENT_ROOT__ENUM_PARAMETER:
				return basicSetEnumParameter(null, msgs);
			case ModelPackage.DOCUMENT_ROOT__ERLANG_DISTRIBUTION:
				return basicSetErlangDistribution(null, msgs);
			case ModelPackage.DOCUMENT_ROOT__EXPRESSION_PARAMETER:
				return basicSetExpressionParameter(null, msgs);
			case ModelPackage.DOCUMENT_ROOT__FLOATING_PARAMETER:
				return basicSetFloatingParameter(null, msgs);
			case ModelPackage.DOCUMENT_ROOT__GAMMA_DISTRIBUTION:
				return basicSetGammaDistribution(null, msgs);
			case ModelPackage.DOCUMENT_ROOT__GLOBAL:
				return basicSetGlobal(null, msgs);
			case ModelPackage.DOCUMENT_ROOT__LOG_NORMAL_DISTRIBUTION:
				return basicSetLogNormalDistribution(null, msgs);
			case ModelPackage.DOCUMENT_ROOT__METADATA:
				return basicSetMetadata(null, msgs);
			case ModelPackage.DOCUMENT_ROOT__METAENTRY:
				return basicSetMetaentry(null, msgs);
			case ModelPackage.DOCUMENT_ROOT__NEGATIVE_EXPONENTIAL_DISTRIBUTION:
				return basicSetNegativeExponentialDistribution(null, msgs);
			case ModelPackage.DOCUMENT_ROOT__NORMAL_DISTRIBUTION:
				return basicSetNormalDistribution(null, msgs);
			case ModelPackage.DOCUMENT_ROOT__NUMERIC_PARAMETER:
				return basicSetNumericParameter(null, msgs);
			case ModelPackage.DOCUMENT_ROOT__ON_ENTRY_SCRIPT:
				return basicSetOnEntryScript(null, msgs);
			case ModelPackage.DOCUMENT_ROOT__ON_EXIT_SCRIPT:
				return basicSetOnExitScript(null, msgs);
			case ModelPackage.DOCUMENT_ROOT__POISSON_DISTRIBUTION:
				return basicSetPoissonDistribution(null, msgs);
			case ModelPackage.DOCUMENT_ROOT__PROCESS_ANALYSIS_DATA:
				return basicSetProcessAnalysisData(null, msgs);
			case ModelPackage.DOCUMENT_ROOT__RANDOM_DISTRIBUTION:
				return basicSetRandomDistribution(null, msgs);
			case ModelPackage.DOCUMENT_ROOT__STRING_PARAMETER:
				return basicSetStringParameter(null, msgs);
			case ModelPackage.DOCUMENT_ROOT__TRIANGULAR_DISTRIBUTION:
				return basicSetTriangularDistribution(null, msgs);
			case ModelPackage.DOCUMENT_ROOT__TRUNCATED_NORMAL_DISTRIBUTION:
				return basicSetTruncatedNormalDistribution(null, msgs);
			case ModelPackage.DOCUMENT_ROOT__UNIFORM_DISTRIBUTION:
				return basicSetUniformDistribution(null, msgs);
			case ModelPackage.DOCUMENT_ROOT__USER_DISTRIBUTION:
				return basicSetUserDistribution(null, msgs);
			case ModelPackage.DOCUMENT_ROOT__USER_DISTRIBUTION_DATA_POINT:
				return basicSetUserDistributionDataPoint(null, msgs);
			case ModelPackage.DOCUMENT_ROOT__WEIBULL_DISTRIBUTION:
				return basicSetWeibullDistribution(null, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ModelPackage.DOCUMENT_ROOT__BETA_DISTRIBUTION:
				return getBetaDistribution();
			case ModelPackage.DOCUMENT_ROOT__PARAMETER_VALUE:
				return getParameterValue();
			case ModelPackage.DOCUMENT_ROOT__BINOMIAL_DISTRIBUTION:
				return getBinomialDistribution();
			case ModelPackage.DOCUMENT_ROOT__BOOLEAN_PARAMETER:
				return getBooleanParameter();
			case ModelPackage.DOCUMENT_ROOT__DATE_TIME_PARAMETER:
				return getDateTimeParameter();
			case ModelPackage.DOCUMENT_ROOT__DECIMAL_PARAMETER:
				return getDecimalParameter();
			case ModelPackage.DOCUMENT_ROOT__DURATION_PARAMETER:
				return getDurationParameter();
			case ModelPackage.DOCUMENT_ROOT__ENUM_PARAMETER:
				return getEnumParameter();
			case ModelPackage.DOCUMENT_ROOT__ERLANG_DISTRIBUTION:
				return getErlangDistribution();
			case ModelPackage.DOCUMENT_ROOT__EXPRESSION_PARAMETER:
				return getExpressionParameter();
			case ModelPackage.DOCUMENT_ROOT__FLOATING_PARAMETER:
				return getFloatingParameter();
			case ModelPackage.DOCUMENT_ROOT__GAMMA_DISTRIBUTION:
				return getGammaDistribution();
			case ModelPackage.DOCUMENT_ROOT__GLOBAL:
				return getGlobal();
			case ModelPackage.DOCUMENT_ROOT__LOG_NORMAL_DISTRIBUTION:
				return getLogNormalDistribution();
			case ModelPackage.DOCUMENT_ROOT__METADATA:
				return getMetadata();
			case ModelPackage.DOCUMENT_ROOT__METAENTRY:
				return getMetaentry();
			case ModelPackage.DOCUMENT_ROOT__NEGATIVE_EXPONENTIAL_DISTRIBUTION:
				return getNegativeExponentialDistribution();
			case ModelPackage.DOCUMENT_ROOT__NORMAL_DISTRIBUTION:
				return getNormalDistribution();
			case ModelPackage.DOCUMENT_ROOT__NUMERIC_PARAMETER:
				return getNumericParameter();
			case ModelPackage.DOCUMENT_ROOT__ON_ENTRY_SCRIPT:
				return getOnEntryScript();
			case ModelPackage.DOCUMENT_ROOT__ON_EXIT_SCRIPT:
				return getOnExitScript();
			case ModelPackage.DOCUMENT_ROOT__POISSON_DISTRIBUTION:
				return getPoissonDistribution();
			case ModelPackage.DOCUMENT_ROOT__PROCESS_ANALYSIS_DATA:
				return getProcessAnalysisData();
			case ModelPackage.DOCUMENT_ROOT__RANDOM_DISTRIBUTION:
				return getRandomDistribution();
			case ModelPackage.DOCUMENT_ROOT__STRING_PARAMETER:
				return getStringParameter();
			case ModelPackage.DOCUMENT_ROOT__TRIANGULAR_DISTRIBUTION:
				return getTriangularDistribution();
			case ModelPackage.DOCUMENT_ROOT__TRUNCATED_NORMAL_DISTRIBUTION:
				return getTruncatedNormalDistribution();
			case ModelPackage.DOCUMENT_ROOT__UNIFORM_DISTRIBUTION:
				return getUniformDistribution();
			case ModelPackage.DOCUMENT_ROOT__USER_DISTRIBUTION:
				return getUserDistribution();
			case ModelPackage.DOCUMENT_ROOT__USER_DISTRIBUTION_DATA_POINT:
				return getUserDistributionDataPoint();
			case ModelPackage.DOCUMENT_ROOT__WEIBULL_DISTRIBUTION:
				return getWeibullDistribution();
			case ModelPackage.DOCUMENT_ROOT__PACKAGE_NAME:
				return getPackageName();
			case ModelPackage.DOCUMENT_ROOT__PRIORITY:
				return getPriority();
			case ModelPackage.DOCUMENT_ROOT__RULE_FLOW_GROUP:
				return getRuleFlowGroup();
			case ModelPackage.DOCUMENT_ROOT__TASK_NAME:
				return getTaskName();
			case ModelPackage.DOCUMENT_ROOT__VERSION:
				return getVersion();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ModelPackage.DOCUMENT_ROOT__BETA_DISTRIBUTION:
				setBetaDistribution((BetaDistributionType)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__PARAMETER_VALUE:
				setParameterValue((ParameterValue)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__BINOMIAL_DISTRIBUTION:
				setBinomialDistribution((BinomialDistributionType)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__BOOLEAN_PARAMETER:
				setBooleanParameter((BooleanParameterType)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__DATE_TIME_PARAMETER:
				setDateTimeParameter((DateTimeParameterType)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__DECIMAL_PARAMETER:
				setDecimalParameter((DecimalParameterType)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__DURATION_PARAMETER:
				setDurationParameter((DurationParameterType)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__ENUM_PARAMETER:
				setEnumParameter((EnumParameterType)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__ERLANG_DISTRIBUTION:
				setErlangDistribution((ErlangDistributionType)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__EXPRESSION_PARAMETER:
				setExpressionParameter((ExpressionParameterType)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__FLOATING_PARAMETER:
				setFloatingParameter((FloatingParameterType)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__GAMMA_DISTRIBUTION:
				setGammaDistribution((GammaDistributionType)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__GLOBAL:
				setGlobal((GlobalType)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__LOG_NORMAL_DISTRIBUTION:
				setLogNormalDistribution((LogNormalDistributionType)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__METADATA:
				setMetadata((MetadataType)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__METAENTRY:
				setMetaentry((MetaentryType)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__NEGATIVE_EXPONENTIAL_DISTRIBUTION:
				setNegativeExponentialDistribution((NegativeExponentialDistributionType)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__NORMAL_DISTRIBUTION:
				setNormalDistribution((NormalDistributionType)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__NUMERIC_PARAMETER:
				setNumericParameter((NumericParameterType)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__ON_ENTRY_SCRIPT:
				setOnEntryScript((OnEntryScriptType)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__ON_EXIT_SCRIPT:
				setOnExitScript((OnExitScriptType)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__POISSON_DISTRIBUTION:
				setPoissonDistribution((PoissonDistributionType)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__PROCESS_ANALYSIS_DATA:
				setProcessAnalysisData((ProcessAnalysisDataType)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__RANDOM_DISTRIBUTION:
				setRandomDistribution((RandomDistributionType)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__STRING_PARAMETER:
				setStringParameter((StringParameterType)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__TRIANGULAR_DISTRIBUTION:
				setTriangularDistribution((TriangularDistributionType)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__TRUNCATED_NORMAL_DISTRIBUTION:
				setTruncatedNormalDistribution((TruncatedNormalDistributionType)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__UNIFORM_DISTRIBUTION:
				setUniformDistribution((UniformDistributionType)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__USER_DISTRIBUTION:
				setUserDistribution((UserDistributionType)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__USER_DISTRIBUTION_DATA_POINT:
				setUserDistributionDataPoint((UserDistributionDataPointType)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__WEIBULL_DISTRIBUTION:
				setWeibullDistribution((WeibullDistributionType)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__PACKAGE_NAME:
				setPackageName((String)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__PRIORITY:
				setPriority((BigInteger)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__RULE_FLOW_GROUP:
				setRuleFlowGroup((String)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__TASK_NAME:
				setTaskName((String)newValue);
				return;
			case ModelPackage.DOCUMENT_ROOT__VERSION:
				setVersion((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ModelPackage.DOCUMENT_ROOT__BETA_DISTRIBUTION:
				setBetaDistribution((BetaDistributionType)null);
				return;
			case ModelPackage.DOCUMENT_ROOT__PARAMETER_VALUE:
				setParameterValue((ParameterValue)null);
				return;
			case ModelPackage.DOCUMENT_ROOT__BINOMIAL_DISTRIBUTION:
				setBinomialDistribution((BinomialDistributionType)null);
				return;
			case ModelPackage.DOCUMENT_ROOT__BOOLEAN_PARAMETER:
				setBooleanParameter((BooleanParameterType)null);
				return;
			case ModelPackage.DOCUMENT_ROOT__DATE_TIME_PARAMETER:
				setDateTimeParameter((DateTimeParameterType)null);
				return;
			case ModelPackage.DOCUMENT_ROOT__DECIMAL_PARAMETER:
				setDecimalParameter((DecimalParameterType)null);
				return;
			case ModelPackage.DOCUMENT_ROOT__DURATION_PARAMETER:
				setDurationParameter((DurationParameterType)null);
				return;
			case ModelPackage.DOCUMENT_ROOT__ENUM_PARAMETER:
				setEnumParameter((EnumParameterType)null);
				return;
			case ModelPackage.DOCUMENT_ROOT__ERLANG_DISTRIBUTION:
				setErlangDistribution((ErlangDistributionType)null);
				return;
			case ModelPackage.DOCUMENT_ROOT__EXPRESSION_PARAMETER:
				setExpressionParameter((ExpressionParameterType)null);
				return;
			case ModelPackage.DOCUMENT_ROOT__FLOATING_PARAMETER:
				setFloatingParameter((FloatingParameterType)null);
				return;
			case ModelPackage.DOCUMENT_ROOT__GAMMA_DISTRIBUTION:
				setGammaDistribution((GammaDistributionType)null);
				return;
			case ModelPackage.DOCUMENT_ROOT__GLOBAL:
				setGlobal((GlobalType)null);
				return;
			case ModelPackage.DOCUMENT_ROOT__LOG_NORMAL_DISTRIBUTION:
				setLogNormalDistribution((LogNormalDistributionType)null);
				return;
			case ModelPackage.DOCUMENT_ROOT__METADATA:
				setMetadata((MetadataType)null);
				return;
			case ModelPackage.DOCUMENT_ROOT__METAENTRY:
				setMetaentry((MetaentryType)null);
				return;
			case ModelPackage.DOCUMENT_ROOT__NEGATIVE_EXPONENTIAL_DISTRIBUTION:
				setNegativeExponentialDistribution((NegativeExponentialDistributionType)null);
				return;
			case ModelPackage.DOCUMENT_ROOT__NORMAL_DISTRIBUTION:
				setNormalDistribution((NormalDistributionType)null);
				return;
			case ModelPackage.DOCUMENT_ROOT__NUMERIC_PARAMETER:
				setNumericParameter((NumericParameterType)null);
				return;
			case ModelPackage.DOCUMENT_ROOT__ON_ENTRY_SCRIPT:
				setOnEntryScript((OnEntryScriptType)null);
				return;
			case ModelPackage.DOCUMENT_ROOT__ON_EXIT_SCRIPT:
				setOnExitScript((OnExitScriptType)null);
				return;
			case ModelPackage.DOCUMENT_ROOT__POISSON_DISTRIBUTION:
				setPoissonDistribution((PoissonDistributionType)null);
				return;
			case ModelPackage.DOCUMENT_ROOT__PROCESS_ANALYSIS_DATA:
				setProcessAnalysisData((ProcessAnalysisDataType)null);
				return;
			case ModelPackage.DOCUMENT_ROOT__RANDOM_DISTRIBUTION:
				setRandomDistribution((RandomDistributionType)null);
				return;
			case ModelPackage.DOCUMENT_ROOT__STRING_PARAMETER:
				setStringParameter((StringParameterType)null);
				return;
			case ModelPackage.DOCUMENT_ROOT__TRIANGULAR_DISTRIBUTION:
				setTriangularDistribution((TriangularDistributionType)null);
				return;
			case ModelPackage.DOCUMENT_ROOT__TRUNCATED_NORMAL_DISTRIBUTION:
				setTruncatedNormalDistribution((TruncatedNormalDistributionType)null);
				return;
			case ModelPackage.DOCUMENT_ROOT__UNIFORM_DISTRIBUTION:
				setUniformDistribution((UniformDistributionType)null);
				return;
			case ModelPackage.DOCUMENT_ROOT__USER_DISTRIBUTION:
				setUserDistribution((UserDistributionType)null);
				return;
			case ModelPackage.DOCUMENT_ROOT__USER_DISTRIBUTION_DATA_POINT:
				setUserDistributionDataPoint((UserDistributionDataPointType)null);
				return;
			case ModelPackage.DOCUMENT_ROOT__WEIBULL_DISTRIBUTION:
				setWeibullDistribution((WeibullDistributionType)null);
				return;
			case ModelPackage.DOCUMENT_ROOT__PACKAGE_NAME:
				setPackageName(PACKAGE_NAME_EDEFAULT);
				return;
			case ModelPackage.DOCUMENT_ROOT__PRIORITY:
				setPriority(PRIORITY_EDEFAULT);
				return;
			case ModelPackage.DOCUMENT_ROOT__RULE_FLOW_GROUP:
				setRuleFlowGroup(RULE_FLOW_GROUP_EDEFAULT);
				return;
			case ModelPackage.DOCUMENT_ROOT__TASK_NAME:
				setTaskName(TASK_NAME_EDEFAULT);
				return;
			case ModelPackage.DOCUMENT_ROOT__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ModelPackage.DOCUMENT_ROOT__BETA_DISTRIBUTION:
				return getBetaDistribution() != null;
			case ModelPackage.DOCUMENT_ROOT__PARAMETER_VALUE:
				return getParameterValue() != null;
			case ModelPackage.DOCUMENT_ROOT__BINOMIAL_DISTRIBUTION:
				return getBinomialDistribution() != null;
			case ModelPackage.DOCUMENT_ROOT__BOOLEAN_PARAMETER:
				return getBooleanParameter() != null;
			case ModelPackage.DOCUMENT_ROOT__DATE_TIME_PARAMETER:
				return getDateTimeParameter() != null;
			case ModelPackage.DOCUMENT_ROOT__DECIMAL_PARAMETER:
				return getDecimalParameter() != null;
			case ModelPackage.DOCUMENT_ROOT__DURATION_PARAMETER:
				return getDurationParameter() != null;
			case ModelPackage.DOCUMENT_ROOT__ENUM_PARAMETER:
				return getEnumParameter() != null;
			case ModelPackage.DOCUMENT_ROOT__ERLANG_DISTRIBUTION:
				return getErlangDistribution() != null;
			case ModelPackage.DOCUMENT_ROOT__EXPRESSION_PARAMETER:
				return getExpressionParameter() != null;
			case ModelPackage.DOCUMENT_ROOT__FLOATING_PARAMETER:
				return getFloatingParameter() != null;
			case ModelPackage.DOCUMENT_ROOT__GAMMA_DISTRIBUTION:
				return getGammaDistribution() != null;
			case ModelPackage.DOCUMENT_ROOT__GLOBAL:
				return getGlobal() != null;
			case ModelPackage.DOCUMENT_ROOT__LOG_NORMAL_DISTRIBUTION:
				return getLogNormalDistribution() != null;
			case ModelPackage.DOCUMENT_ROOT__METADATA:
				return getMetadata() != null;
			case ModelPackage.DOCUMENT_ROOT__METAENTRY:
				return getMetaentry() != null;
			case ModelPackage.DOCUMENT_ROOT__NEGATIVE_EXPONENTIAL_DISTRIBUTION:
				return getNegativeExponentialDistribution() != null;
			case ModelPackage.DOCUMENT_ROOT__NORMAL_DISTRIBUTION:
				return getNormalDistribution() != null;
			case ModelPackage.DOCUMENT_ROOT__NUMERIC_PARAMETER:
				return getNumericParameter() != null;
			case ModelPackage.DOCUMENT_ROOT__ON_ENTRY_SCRIPT:
				return getOnEntryScript() != null;
			case ModelPackage.DOCUMENT_ROOT__ON_EXIT_SCRIPT:
				return getOnExitScript() != null;
			case ModelPackage.DOCUMENT_ROOT__POISSON_DISTRIBUTION:
				return getPoissonDistribution() != null;
			case ModelPackage.DOCUMENT_ROOT__PROCESS_ANALYSIS_DATA:
				return getProcessAnalysisData() != null;
			case ModelPackage.DOCUMENT_ROOT__RANDOM_DISTRIBUTION:
				return getRandomDistribution() != null;
			case ModelPackage.DOCUMENT_ROOT__STRING_PARAMETER:
				return getStringParameter() != null;
			case ModelPackage.DOCUMENT_ROOT__TRIANGULAR_DISTRIBUTION:
				return getTriangularDistribution() != null;
			case ModelPackage.DOCUMENT_ROOT__TRUNCATED_NORMAL_DISTRIBUTION:
				return getTruncatedNormalDistribution() != null;
			case ModelPackage.DOCUMENT_ROOT__UNIFORM_DISTRIBUTION:
				return getUniformDistribution() != null;
			case ModelPackage.DOCUMENT_ROOT__USER_DISTRIBUTION:
				return getUserDistribution() != null;
			case ModelPackage.DOCUMENT_ROOT__USER_DISTRIBUTION_DATA_POINT:
				return getUserDistributionDataPoint() != null;
			case ModelPackage.DOCUMENT_ROOT__WEIBULL_DISTRIBUTION:
				return getWeibullDistribution() != null;
			case ModelPackage.DOCUMENT_ROOT__PACKAGE_NAME:
				return PACKAGE_NAME_EDEFAULT == null ? packageName != null : !PACKAGE_NAME_EDEFAULT.equals(packageName);
			case ModelPackage.DOCUMENT_ROOT__PRIORITY:
				return PRIORITY_EDEFAULT == null ? priority != null : !PRIORITY_EDEFAULT.equals(priority);
			case ModelPackage.DOCUMENT_ROOT__RULE_FLOW_GROUP:
				return RULE_FLOW_GROUP_EDEFAULT == null ? ruleFlowGroup != null : !RULE_FLOW_GROUP_EDEFAULT.equals(ruleFlowGroup);
			case ModelPackage.DOCUMENT_ROOT__TASK_NAME:
				return TASK_NAME_EDEFAULT == null ? taskName != null : !TASK_NAME_EDEFAULT.equals(taskName);
			case ModelPackage.DOCUMENT_ROOT__VERSION:
				return VERSION_EDEFAULT == null ? version != null : !VERSION_EDEFAULT.equals(version);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (packageName: ");
		result.append(packageName);
		result.append(", priority: ");
		result.append(priority);
		result.append(", ruleFlowGroup: ");
		result.append(ruleFlowGroup);
		result.append(", taskName: ");
		result.append(taskName);
		result.append(", version: ");
		result.append(version);
		result.append(')');
		return result.toString();
	}

} //DocumentRootImpl

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

import java.math.BigInteger;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Document Root</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getBetaDistribution <em>Beta Distribution</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getParameterValue <em>Parameter Value</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getBinomialDistribution <em>Binomial Distribution</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getBooleanParameter <em>Boolean Parameter</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getDateTimeParameter <em>Date Time Parameter</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getDecimalParameter <em>Decimal Parameter</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getDurationParameter <em>Duration Parameter</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getEnumParameter <em>Enum Parameter</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getErlangDistribution <em>Erlang Distribution</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getExpressionParameter <em>Expression Parameter</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getFloatingParameter <em>Floating Parameter</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getGammaDistribution <em>Gamma Distribution</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getGlobal <em>Global</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getImportType <em>Import Type</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getLogNormalDistribution <em>Log Normal Distribution</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getMetadata <em>Metadata</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getMetaentry <em>Metaentry</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getNegativeExponentialDistribution <em>Negative Exponential Distribution</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getNormalDistribution <em>Normal Distribution</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getNumericParameter <em>Numeric Parameter</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getOnEntryScript <em>On Entry Script</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getOnExitScript <em>On Exit Script</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getPoissonDistribution <em>Poisson Distribution</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getProcessAnalysisData <em>Process Analysis Data</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getRandomDistribution <em>Random Distribution</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getStringParameter <em>String Parameter</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getTriangularDistribution <em>Triangular Distribution</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getTruncatedNormalDistribution <em>Truncated Normal Distribution</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getUniformDistribution <em>Uniform Distribution</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getUserDistribution <em>User Distribution</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getUserDistributionDataPoint <em>User Distribution Data Point</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getWeibullDistribution <em>Weibull Distribution</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getPackageName <em>Package Name</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getPriority <em>Priority</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getRuleFlowGroup <em>Rule Flow Group</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getTaskName <em>Task Name</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getVersion <em>Version</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot()
 * @model extendedMetaData="name='' kind='mixed'"
 * @generated
 */
public interface DocumentRoot extends org.eclipse.bpmn2.DocumentRoot {
	/**
	 * Returns the value of the '<em><b>Beta Distribution</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Beta Distribution</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Beta Distribution</em>' containment reference.
	 * @see #setBetaDistribution(BetaDistributionType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_BetaDistribution()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='BetaDistribution' namespace='##targetNamespace' affiliation='ParameterValue'"
	 * @generated
	 */
	BetaDistributionType getBetaDistribution();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getBetaDistribution <em>Beta Distribution</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Beta Distribution</em>' containment reference.
	 * @see #getBetaDistribution()
	 * @generated
	 */
	void setBetaDistribution(BetaDistributionType value);

	/**
	 * Returns the value of the '<em><b>Parameter Value</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Parameter Value</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Parameter Value</em>' containment reference.
	 * @see #setParameterValue(ParameterValue)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_ParameterValue()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ParameterValue' namespace='##targetNamespace'"
	 * @generated
	 */
	ParameterValue getParameterValue();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getParameterValue <em>Parameter Value</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Parameter Value</em>' containment reference.
	 * @see #getParameterValue()
	 * @generated
	 */
	void setParameterValue(ParameterValue value);

	/**
	 * Returns the value of the '<em><b>Binomial Distribution</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Binomial Distribution</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Binomial Distribution</em>' containment reference.
	 * @see #setBinomialDistribution(BinomialDistributionType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_BinomialDistribution()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='BinomialDistribution' namespace='##targetNamespace' affiliation='ParameterValue'"
	 * @generated
	 */
	BinomialDistributionType getBinomialDistribution();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getBinomialDistribution <em>Binomial Distribution</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Binomial Distribution</em>' containment reference.
	 * @see #getBinomialDistribution()
	 * @generated
	 */
	void setBinomialDistribution(BinomialDistributionType value);

	/**
	 * Returns the value of the '<em><b>Boolean Parameter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Boolean Parameter</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Boolean Parameter</em>' containment reference.
	 * @see #setBooleanParameter(BooleanParameterType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_BooleanParameter()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='BooleanParameter' namespace='##targetNamespace' affiliation='ParameterValue'"
	 * @generated
	 */
	BooleanParameterType getBooleanParameter();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getBooleanParameter <em>Boolean Parameter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Boolean Parameter</em>' containment reference.
	 * @see #getBooleanParameter()
	 * @generated
	 */
	void setBooleanParameter(BooleanParameterType value);

	/**
	 * Returns the value of the '<em><b>Date Time Parameter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Date Time Parameter</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Date Time Parameter</em>' containment reference.
	 * @see #setDateTimeParameter(DateTimeParameterType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_DateTimeParameter()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='DateTimeParameter' namespace='##targetNamespace' affiliation='ParameterValue'"
	 * @generated
	 */
	DateTimeParameterType getDateTimeParameter();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getDateTimeParameter <em>Date Time Parameter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Date Time Parameter</em>' containment reference.
	 * @see #getDateTimeParameter()
	 * @generated
	 */
	void setDateTimeParameter(DateTimeParameterType value);

	/**
	 * Returns the value of the '<em><b>Decimal Parameter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Decimal Parameter</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Decimal Parameter</em>' containment reference.
	 * @see #setDecimalParameter(DecimalParameterType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_DecimalParameter()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='DecimalParameter' namespace='##targetNamespace' affiliation='ParameterValue'"
	 * @generated
	 */
	DecimalParameterType getDecimalParameter();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getDecimalParameter <em>Decimal Parameter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Decimal Parameter</em>' containment reference.
	 * @see #getDecimalParameter()
	 * @generated
	 */
	void setDecimalParameter(DecimalParameterType value);

	/**
	 * Returns the value of the '<em><b>Duration Parameter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Duration Parameter</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Duration Parameter</em>' containment reference.
	 * @see #setDurationParameter(DurationParameterType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_DurationParameter()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='DurationParameter' namespace='##targetNamespace' affiliation='ParameterValue'"
	 * @generated
	 */
	DurationParameterType getDurationParameter();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getDurationParameter <em>Duration Parameter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Duration Parameter</em>' containment reference.
	 * @see #getDurationParameter()
	 * @generated
	 */
	void setDurationParameter(DurationParameterType value);

	/**
	 * Returns the value of the '<em><b>Enum Parameter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Enum Parameter</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Enum Parameter</em>' containment reference.
	 * @see #setEnumParameter(EnumParameterType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_EnumParameter()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='EnumParameter' namespace='##targetNamespace' affiliation='ParameterValue'"
	 * @generated
	 */
	EnumParameterType getEnumParameter();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getEnumParameter <em>Enum Parameter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Enum Parameter</em>' containment reference.
	 * @see #getEnumParameter()
	 * @generated
	 */
	void setEnumParameter(EnumParameterType value);

	/**
	 * Returns the value of the '<em><b>Erlang Distribution</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Erlang Distribution</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Erlang Distribution</em>' containment reference.
	 * @see #setErlangDistribution(ErlangDistributionType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_ErlangDistribution()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ErlangDistribution' namespace='##targetNamespace' affiliation='ParameterValue'"
	 * @generated
	 */
	ErlangDistributionType getErlangDistribution();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getErlangDistribution <em>Erlang Distribution</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Erlang Distribution</em>' containment reference.
	 * @see #getErlangDistribution()
	 * @generated
	 */
	void setErlangDistribution(ErlangDistributionType value);

	/**
	 * Returns the value of the '<em><b>Expression Parameter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Expression Parameter</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Expression Parameter</em>' containment reference.
	 * @see #setExpressionParameter(ExpressionParameterType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_ExpressionParameter()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ExpressionParameter' namespace='##targetNamespace' affiliation='ParameterValue'"
	 * @generated
	 */
	ExpressionParameterType getExpressionParameter();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getExpressionParameter <em>Expression Parameter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Expression Parameter</em>' containment reference.
	 * @see #getExpressionParameter()
	 * @generated
	 */
	void setExpressionParameter(ExpressionParameterType value);

	/**
	 * Returns the value of the '<em><b>Floating Parameter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Floating Parameter</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Floating Parameter</em>' containment reference.
	 * @see #setFloatingParameter(FloatingParameterType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_FloatingParameter()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='FloatingParameter' namespace='##targetNamespace' affiliation='ParameterValue'"
	 * @generated
	 */
	FloatingParameterType getFloatingParameter();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getFloatingParameter <em>Floating Parameter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Floating Parameter</em>' containment reference.
	 * @see #getFloatingParameter()
	 * @generated
	 */
	void setFloatingParameter(FloatingParameterType value);

	/**
	 * Returns the value of the '<em><b>Gamma Distribution</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Gamma Distribution</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Gamma Distribution</em>' containment reference.
	 * @see #setGammaDistribution(GammaDistributionType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_GammaDistribution()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='GammaDistribution' namespace='##targetNamespace' affiliation='ParameterValue'"
	 * @generated
	 */
	GammaDistributionType getGammaDistribution();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getGammaDistribution <em>Gamma Distribution</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Gamma Distribution</em>' containment reference.
	 * @see #getGammaDistribution()
	 * @generated
	 */
	void setGammaDistribution(GammaDistributionType value);

	/**
	 * Returns the value of the '<em><b>Global</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Global</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Global</em>' containment reference.
	 * @see #setGlobal(GlobalType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_Global()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='global' namespace='##targetNamespace'"
	 * @generated
	 */
	GlobalType getGlobal();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getGlobal <em>Global</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Global</em>' containment reference.
	 * @see #getGlobal()
	 * @generated
	 */
	void setGlobal(GlobalType value);

	/**
	 * Returns the value of the '<em><b>Import Type</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Import Type</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Import Type</em>' containment reference.
	 * @see #setImportType(ImportType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_ImportType()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='import' namespace='##targetNamespace'"
	 * @generated
	 */
	ImportType getImportType();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getImportType <em>Import Type</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Import Type</em>' containment reference.
	 * @see #getImportType()
	 * @generated
	 */
	void setImportType(ImportType value);

	/**
	 * Returns the value of the '<em><b>Log Normal Distribution</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Log Normal Distribution</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Log Normal Distribution</em>' containment reference.
	 * @see #setLogNormalDistribution(LogNormalDistributionType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_LogNormalDistribution()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='LogNormalDistribution' namespace='##targetNamespace' affiliation='ParameterValue'"
	 * @generated
	 */
	LogNormalDistributionType getLogNormalDistribution();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getLogNormalDistribution <em>Log Normal Distribution</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Log Normal Distribution</em>' containment reference.
	 * @see #getLogNormalDistribution()
	 * @generated
	 */
	void setLogNormalDistribution(LogNormalDistributionType value);

	/**
	 * Returns the value of the '<em><b>Metadata</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metadata</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metadata</em>' containment reference.
	 * @see #setMetadata(MetadataType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_Metadata()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='metadata' namespace='##targetNamespace'"
	 * @generated
	 */
	MetadataType getMetadata();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getMetadata <em>Metadata</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Metadata</em>' containment reference.
	 * @see #getMetadata()
	 * @generated
	 */
	void setMetadata(MetadataType value);

	/**
	 * Returns the value of the '<em><b>Metaentry</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Metaentry</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Metaentry</em>' containment reference.
	 * @see #setMetaentry(MetaentryType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_Metaentry()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='metaentry' namespace='##targetNamespace'"
	 * @generated
	 */
	MetaentryType getMetaentry();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getMetaentry <em>Metaentry</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Metaentry</em>' containment reference.
	 * @see #getMetaentry()
	 * @generated
	 */
	void setMetaentry(MetaentryType value);

	/**
	 * Returns the value of the '<em><b>Negative Exponential Distribution</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Negative Exponential Distribution</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Negative Exponential Distribution</em>' containment reference.
	 * @see #setNegativeExponentialDistribution(NegativeExponentialDistributionType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_NegativeExponentialDistribution()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='NegativeExponentialDistribution' namespace='##targetNamespace' affiliation='ParameterValue'"
	 * @generated
	 */
	NegativeExponentialDistributionType getNegativeExponentialDistribution();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getNegativeExponentialDistribution <em>Negative Exponential Distribution</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Negative Exponential Distribution</em>' containment reference.
	 * @see #getNegativeExponentialDistribution()
	 * @generated
	 */
	void setNegativeExponentialDistribution(NegativeExponentialDistributionType value);

	/**
	 * Returns the value of the '<em><b>Normal Distribution</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Normal Distribution</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Normal Distribution</em>' containment reference.
	 * @see #setNormalDistribution(NormalDistributionType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_NormalDistribution()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='NormalDistribution' namespace='##targetNamespace' affiliation='ParameterValue'"
	 * @generated
	 */
	NormalDistributionType getNormalDistribution();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getNormalDistribution <em>Normal Distribution</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Normal Distribution</em>' containment reference.
	 * @see #getNormalDistribution()
	 * @generated
	 */
	void setNormalDistribution(NormalDistributionType value);

	/**
	 * Returns the value of the '<em><b>Numeric Parameter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Numeric Parameter</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Numeric Parameter</em>' containment reference.
	 * @see #setNumericParameter(NumericParameterType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_NumericParameter()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='NumericParameter' namespace='##targetNamespace' affiliation='ParameterValue'"
	 * @generated
	 */
	NumericParameterType getNumericParameter();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getNumericParameter <em>Numeric Parameter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Numeric Parameter</em>' containment reference.
	 * @see #getNumericParameter()
	 * @generated
	 */
	void setNumericParameter(NumericParameterType value);

	/**
	 * Returns the value of the '<em><b>On Entry Script</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>On Entry Script</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>On Entry Script</em>' containment reference.
	 * @see #setOnEntryScript(OnEntryScriptType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_OnEntryScript()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='onEntry-script' namespace='##targetNamespace'"
	 * @generated
	 */
	OnEntryScriptType getOnEntryScript();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getOnEntryScript <em>On Entry Script</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>On Entry Script</em>' containment reference.
	 * @see #getOnEntryScript()
	 * @generated
	 */
	void setOnEntryScript(OnEntryScriptType value);

	/**
	 * Returns the value of the '<em><b>On Exit Script</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>On Exit Script</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>On Exit Script</em>' containment reference.
	 * @see #setOnExitScript(OnExitScriptType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_OnExitScript()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='onExit-script' namespace='##targetNamespace'"
	 * @generated
	 */
	OnExitScriptType getOnExitScript();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getOnExitScript <em>On Exit Script</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>On Exit Script</em>' containment reference.
	 * @see #getOnExitScript()
	 * @generated
	 */
	void setOnExitScript(OnExitScriptType value);

	/**
	 * Returns the value of the '<em><b>Poisson Distribution</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Poisson Distribution</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Poisson Distribution</em>' containment reference.
	 * @see #setPoissonDistribution(PoissonDistributionType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_PoissonDistribution()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='PoissonDistribution' namespace='##targetNamespace' affiliation='ParameterValue'"
	 * @generated
	 */
	PoissonDistributionType getPoissonDistribution();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getPoissonDistribution <em>Poisson Distribution</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Poisson Distribution</em>' containment reference.
	 * @see #getPoissonDistribution()
	 * @generated
	 */
	void setPoissonDistribution(PoissonDistributionType value);

	/**
	 * Returns the value of the '<em><b>Process Analysis Data</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Process Analysis Data</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Process Analysis Data</em>' containment reference.
	 * @see #setProcessAnalysisData(ProcessAnalysisDataType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_ProcessAnalysisData()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='ProcessAnalysisData' namespace='##targetNamespace'"
	 * @generated
	 */
	ProcessAnalysisDataType getProcessAnalysisData();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getProcessAnalysisData <em>Process Analysis Data</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Process Analysis Data</em>' containment reference.
	 * @see #getProcessAnalysisData()
	 * @generated
	 */
	void setProcessAnalysisData(ProcessAnalysisDataType value);

	/**
	 * Returns the value of the '<em><b>Random Distribution</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Random Distribution</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Random Distribution</em>' containment reference.
	 * @see #setRandomDistribution(RandomDistributionType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_RandomDistribution()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='RandomDistribution' namespace='##targetNamespace' affiliation='ParameterValue'"
	 * @generated
	 */
	RandomDistributionType getRandomDistribution();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getRandomDistribution <em>Random Distribution</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Random Distribution</em>' containment reference.
	 * @see #getRandomDistribution()
	 * @generated
	 */
	void setRandomDistribution(RandomDistributionType value);

	/**
	 * Returns the value of the '<em><b>String Parameter</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>String Parameter</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>String Parameter</em>' containment reference.
	 * @see #setStringParameter(StringParameterType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_StringParameter()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='StringParameter' namespace='##targetNamespace' affiliation='ParameterValue'"
	 * @generated
	 */
	StringParameterType getStringParameter();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getStringParameter <em>String Parameter</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>String Parameter</em>' containment reference.
	 * @see #getStringParameter()
	 * @generated
	 */
	void setStringParameter(StringParameterType value);

	/**
	 * Returns the value of the '<em><b>Triangular Distribution</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Triangular Distribution</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Triangular Distribution</em>' containment reference.
	 * @see #setTriangularDistribution(TriangularDistributionType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_TriangularDistribution()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='TriangularDistribution' namespace='##targetNamespace' affiliation='ParameterValue'"
	 * @generated
	 */
	TriangularDistributionType getTriangularDistribution();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getTriangularDistribution <em>Triangular Distribution</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Triangular Distribution</em>' containment reference.
	 * @see #getTriangularDistribution()
	 * @generated
	 */
	void setTriangularDistribution(TriangularDistributionType value);

	/**
	 * Returns the value of the '<em><b>Truncated Normal Distribution</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Truncated Normal Distribution</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Truncated Normal Distribution</em>' containment reference.
	 * @see #setTruncatedNormalDistribution(TruncatedNormalDistributionType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_TruncatedNormalDistribution()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='TruncatedNormalDistribution' namespace='##targetNamespace' affiliation='ParameterValue'"
	 * @generated
	 */
	TruncatedNormalDistributionType getTruncatedNormalDistribution();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getTruncatedNormalDistribution <em>Truncated Normal Distribution</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Truncated Normal Distribution</em>' containment reference.
	 * @see #getTruncatedNormalDistribution()
	 * @generated
	 */
	void setTruncatedNormalDistribution(TruncatedNormalDistributionType value);

	/**
	 * Returns the value of the '<em><b>Uniform Distribution</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Uniform Distribution</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Uniform Distribution</em>' containment reference.
	 * @see #setUniformDistribution(UniformDistributionType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_UniformDistribution()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='UniformDistribution' namespace='##targetNamespace' affiliation='ParameterValue'"
	 * @generated
	 */
	UniformDistributionType getUniformDistribution();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getUniformDistribution <em>Uniform Distribution</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Uniform Distribution</em>' containment reference.
	 * @see #getUniformDistribution()
	 * @generated
	 */
	void setUniformDistribution(UniformDistributionType value);

	/**
	 * Returns the value of the '<em><b>User Distribution</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User Distribution</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User Distribution</em>' containment reference.
	 * @see #setUserDistribution(UserDistributionType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_UserDistribution()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='UserDistribution' namespace='##targetNamespace' affiliation='ParameterValue'"
	 * @generated
	 */
	UserDistributionType getUserDistribution();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getUserDistribution <em>User Distribution</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User Distribution</em>' containment reference.
	 * @see #getUserDistribution()
	 * @generated
	 */
	void setUserDistribution(UserDistributionType value);

	/**
	 * Returns the value of the '<em><b>User Distribution Data Point</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>User Distribution Data Point</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>User Distribution Data Point</em>' containment reference.
	 * @see #setUserDistributionDataPoint(UserDistributionDataPointType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_UserDistributionDataPoint()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='UserDistributionDataPoint' namespace='##targetNamespace'"
	 * @generated
	 */
	UserDistributionDataPointType getUserDistributionDataPoint();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getUserDistributionDataPoint <em>User Distribution Data Point</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>User Distribution Data Point</em>' containment reference.
	 * @see #getUserDistributionDataPoint()
	 * @generated
	 */
	void setUserDistributionDataPoint(UserDistributionDataPointType value);

	/**
	 * Returns the value of the '<em><b>Weibull Distribution</b></em>' containment reference.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Weibull Distribution</em>' containment reference isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Weibull Distribution</em>' containment reference.
	 * @see #setWeibullDistribution(WeibullDistributionType)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_WeibullDistribution()
	 * @model containment="true" upper="-2" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='WeibullDistribution' namespace='##targetNamespace' affiliation='ParameterValue'"
	 * @generated
	 */
	WeibullDistributionType getWeibullDistribution();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getWeibullDistribution <em>Weibull Distribution</em>}' containment reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Weibull Distribution</em>' containment reference.
	 * @see #getWeibullDistribution()
	 * @generated
	 */
	void setWeibullDistribution(WeibullDistributionType value);

	/**
	 * Returns the value of the '<em><b>Package Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Package Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Package Name</em>' attribute.
	 * @see #setPackageName(String)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_PackageName()
	 * @model dataType="org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.PackageNameType"
	 *        extendedMetaData="kind='attribute' name='packageName' namespace='##targetNamespace'"
	 * @generated
	 */
	String getPackageName();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getPackageName <em>Package Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Package Name</em>' attribute.
	 * @see #getPackageName()
	 * @generated
	 */
	void setPackageName(String value);

	/**
	 * Returns the value of the '<em><b>Priority</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Priority</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Priority</em>' attribute.
	 * @see #setPriority(BigInteger)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_Priority()
	 * @model dataType="org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.PriorityType"
	 *        extendedMetaData="kind='attribute' name='priority' namespace='##targetNamespace'"
	 * @generated
	 */
	BigInteger getPriority();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getPriority <em>Priority</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Priority</em>' attribute.
	 * @see #getPriority()
	 * @generated
	 */
	void setPriority(BigInteger value);

	/**
	 * Returns the value of the '<em><b>Rule Flow Group</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Rule Flow Group</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Rule Flow Group</em>' attribute.
	 * @see #setRuleFlowGroup(String)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_RuleFlowGroup()
	 * @model dataType="org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.RuleFlowGroupType"
	 *        extendedMetaData="kind='attribute' name='ruleFlowGroup' namespace='##targetNamespace'"
	 * @generated
	 */
	String getRuleFlowGroup();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getRuleFlowGroup <em>Rule Flow Group</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Rule Flow Group</em>' attribute.
	 * @see #getRuleFlowGroup()
	 * @generated
	 */
	void setRuleFlowGroup(String value);

	/**
	 * Returns the value of the '<em><b>Task Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Task Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Task Name</em>' attribute.
	 * @see #setTaskName(String)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_TaskName()
	 * @model dataType="org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TaskNameType"
	 *        extendedMetaData="kind='attribute' name='taskName' namespace='##targetNamespace'"
	 * @generated
	 */
	String getTaskName();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getTaskName <em>Task Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Task Name</em>' attribute.
	 * @see #getTaskName()
	 * @generated
	 */
	void setTaskName(String value);

	/**
	 * Returns the value of the '<em><b>Version</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Version</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Version</em>' attribute.
	 * @see #setVersion(String)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDocumentRoot_Version()
	 * @model dataType="org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.VersionType"
	 *        extendedMetaData="kind='attribute' name='version' namespace='##targetNamespace'"
	 * @generated
	 */
	String getVersion();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot#getVersion <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Version</em>' attribute.
	 * @see #getVersion()
	 * @generated
	 */
	void setVersion(String value);

} // DocumentRoot

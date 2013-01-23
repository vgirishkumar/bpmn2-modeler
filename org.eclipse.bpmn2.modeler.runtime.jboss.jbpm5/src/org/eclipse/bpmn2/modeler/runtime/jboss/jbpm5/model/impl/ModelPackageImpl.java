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

import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.di.BpmnDiPackage;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.BetaDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.BinomialDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.BooleanParameterType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Calendar;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ConstantParameter;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ControlParameters;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.CostParameters;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DateTimeParameterType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DecimalParameterType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DistributionParameter;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DocumentRoot;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DurationParameterType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ElementParameters;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.EnumParameterType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ErlangDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ExpressionParameterType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.FloatingParameterType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.GammaDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.GlobalType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ImportType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.LogNormalDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.MetadataType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.MetaentryType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelFactory;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.NegativeExponentialDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.NormalDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.NumericParameterType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.OnEntryScriptType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.OnExitScriptType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Parameter;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ParameterValue;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.PoissonDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.PriorityParameters;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ProcessAnalysisDataType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.PropertyParameters;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.PropertyType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.RandomDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ResourceParameters;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ResultType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.Scenario;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ScenarioParameters;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.StringParameterType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TimeParameters;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TimeUnit;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TriangularDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.TruncatedNormalDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.UniformDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.UserDistributionDataPointType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.UserDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.VendorExtension;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.WeibullDistributionType;
import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.util.ModelValidator;
import org.eclipse.dd.dc.DcPackage;
import org.eclipse.dd.di.DiPackage;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EValidator;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model <b>Package</b>.
 * <!-- end-user-doc -->
 * @generated
 */
public class ModelPackageImpl extends EPackageImpl implements ModelPackage {
	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass betaDistributionTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass binomialDistributionTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass booleanParameterTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass calendarEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass documentRootEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass globalTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass controlParametersEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass constantParameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass costParametersEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass importTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass dateTimeParameterTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass decimalParameterTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass onEntryScriptTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass elementParametersEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass onExitScriptTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass durationParameterTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass enumParameterTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass erlangDistributionTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass expressionParameterTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass floatingParameterTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass gammaDistributionTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass logNormalDistributionTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass metadataTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass negativeExponentialDistributionTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass normalDistributionTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass numericParameterTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass metaentryTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass distributionParameterEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass processAnalysisDataTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyParametersEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass randomDistributionTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass parameterValueEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass poissonDistributionTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass propertyTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass priorityParametersEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass timeParametersEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass stringParameterTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass scenarioEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass resourceParametersEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass scenarioParametersEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass vendorExtensionEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass triangularDistributionTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass truncatedNormalDistributionTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass uniformDistributionTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass userDistributionDataPointTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass userDistributionTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EClass weibullDistributionTypeEClass = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum resultTypeEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EEnum timeUnitEEnum = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType packageNameTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType priorityTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType ruleFlowGroupTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType taskNameTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType versionTypeEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType resultTypeObjectEDataType = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private EDataType timeUnitObjectEDataType = null;

	/**
	 * Creates an instance of the model <b>Package</b>, registered with
	 * {@link org.eclipse.emf.ecore.EPackage.Registry EPackage.Registry} by the package
	 * package URI value.
	 * <p>Note: the correct way to create the package is via the static
	 * factory method {@link #init init()}, which also performs
	 * initialization of the package, or returns the registered package,
	 * if one already exists.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see org.eclipse.emf.ecore.EPackage.Registry
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#eNS_URI
	 * @see #init()
	 * @generated
	 */
	private ModelPackageImpl() {
		super(eNS_URI, ModelFactory.eINSTANCE);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private static boolean isInited = false;

	/**
	 * Creates, registers, and initializes the <b>Package</b> for this model, and for any others upon which it depends.
	 * 
	 * <p>This method is used to initialize {@link ModelPackage#eINSTANCE} when that field is accessed.
	 * Clients should not invoke it directly. Instead, they should simply access that field to obtain the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #eNS_URI
	 * @see #createPackageContents()
	 * @see #initializePackageContents()
	 * @generated
	 */
	public static ModelPackage init() {
		if (isInited) return (ModelPackage)EPackage.Registry.INSTANCE.getEPackage(ModelPackage.eNS_URI);

		// Obtain or create and register package
		ModelPackageImpl theModelPackage = (ModelPackageImpl)(EPackage.Registry.INSTANCE.get(eNS_URI) instanceof ModelPackageImpl ? EPackage.Registry.INSTANCE.get(eNS_URI) : new ModelPackageImpl());

		isInited = true;

		// Initialize simple dependencies
		Bpmn2Package.eINSTANCE.eClass();
		BpmnDiPackage.eINSTANCE.eClass();
		DiPackage.eINSTANCE.eClass();
		DcPackage.eINSTANCE.eClass();
		XMLTypePackage.eINSTANCE.eClass();

		// Create package meta-data objects
		theModelPackage.createPackageContents();

		// Initialize created meta-data
		theModelPackage.initializePackageContents();

		// Register package validator
		EValidator.Registry.INSTANCE.put
			(theModelPackage, 
			 new EValidator.Descriptor() {
				 public EValidator getEValidator() {
					 return ModelValidator.INSTANCE;
				 }
			 });

		// Mark meta-data to indicate it can't be changed
		theModelPackage.freeze();

  
		// Update the registry and return the package
		EPackage.Registry.INSTANCE.put(ModelPackage.eNS_URI, theModelPackage);
		return theModelPackage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBetaDistributionType() {
		return betaDistributionTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBetaDistributionType_Scale() {
		return (EAttribute)betaDistributionTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBetaDistributionType_Shape() {
		return (EAttribute)betaDistributionTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBinomialDistributionType() {
		return binomialDistributionTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBinomialDistributionType_Probability() {
		return (EAttribute)binomialDistributionTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBinomialDistributionType_Trials() {
		return (EAttribute)binomialDistributionTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getBooleanParameterType() {
		return booleanParameterTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getBooleanParameterType_Value() {
		return (EAttribute)booleanParameterTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCalendar() {
		return calendarEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCalendar_Value() {
		return (EAttribute)calendarEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCalendar_Id() {
		return (EAttribute)calendarEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCalendar_Name() {
		return (EAttribute)calendarEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDocumentRoot() {
		return documentRootEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_BetaDistribution() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_ParameterValue() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_BinomialDistribution() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_BooleanParameter() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_DateTimeParameter() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_DecimalParameter() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_DurationParameter() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_EnumParameter() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_ErlangDistribution() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_ExpressionParameter() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_FloatingParameter() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_GammaDistribution() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Global() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_LogNormalDistribution() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Metadata() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(14);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_Metaentry() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(15);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_NegativeExponentialDistribution() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(16);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_NormalDistribution() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(17);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_NumericParameter() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(18);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_OnEntryScript() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(19);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_OnExitScript() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(20);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_PoissonDistribution() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(21);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_ProcessAnalysisData() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(22);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_RandomDistribution() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(23);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_StringParameter() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(24);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_TriangularDistribution() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(25);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_TruncatedNormalDistribution() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(26);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_UniformDistribution() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(27);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_UserDistribution() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(28);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_UserDistributionDataPoint() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(29);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getDocumentRoot_WeibullDistribution() {
		return (EReference)documentRootEClass.getEStructuralFeatures().get(30);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_PackageName() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(31);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_Priority() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(32);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_RuleFlowGroup() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(33);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_TaskName() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(34);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDocumentRoot_Version() {
		return (EAttribute)documentRootEClass.getEStructuralFeatures().get(35);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGlobalType() {
		return globalTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGlobalType_Identifier() {
		return (EAttribute)globalTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGlobalType_Type() {
		return (EAttribute)globalTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getControlParameters() {
		return controlParametersEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getControlParameters_Probability() {
		return (EReference)controlParametersEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getControlParameters_InterTriggerTimer() {
		return (EReference)controlParametersEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getControlParameters_MaxTriggerCount() {
		return (EReference)controlParametersEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getConstantParameter() {
		return constantParameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getCostParameters() {
		return costParametersEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCostParameters_FixedCost() {
		return (EReference)costParametersEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getCostParameters_UnitCost() {
		return (EReference)costParametersEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getCostParameters_CurrencyUnit() {
		return (EAttribute)costParametersEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getImportType() {
		return importTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getImportType_Name() {
		return (EAttribute)importTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDateTimeParameterType() {
		return dateTimeParameterTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDateTimeParameterType_Value() {
		return (EAttribute)dateTimeParameterTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDecimalParameterType() {
		return decimalParameterTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDecimalParameterType_Value() {
		return (EAttribute)decimalParameterTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOnEntryScriptType() {
		return onEntryScriptTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOnEntryScriptType_Script() {
		return (EAttribute)onEntryScriptTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOnEntryScriptType_ScriptFormat() {
		return (EAttribute)onEntryScriptTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getElementParameters() {
		return elementParametersEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getElementParameters_TimeParameters() {
		return (EReference)elementParametersEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getElementParameters_ControlParameters() {
		return (EReference)elementParametersEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getElementParameters_ResourceParameters() {
		return (EReference)elementParametersEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getElementParameters_PriorityParameters() {
		return (EReference)elementParametersEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getElementParameters_CostParameters() {
		return (EReference)elementParametersEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getElementParameters_PropertyParameters() {
		return (EReference)elementParametersEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getElementParameters_VendorExtension() {
		return (EReference)elementParametersEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getElementParameters_ElementId() {
		return (EAttribute)elementParametersEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getElementParameters_Id() {
		return (EAttribute)elementParametersEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getOnExitScriptType() {
		return onExitScriptTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOnExitScriptType_Script() {
		return (EAttribute)onExitScriptTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getOnExitScriptType_ScriptFormat() {
		return (EAttribute)onExitScriptTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDurationParameterType() {
		return durationParameterTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDurationParameterType_Value() {
		return (EAttribute)durationParameterTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getEnumParameterType() {
		return enumParameterTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEnumParameterType_Group() {
		return (EAttribute)enumParameterTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getEnumParameterType_ParameterValueGroup() {
		return (EAttribute)enumParameterTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getEnumParameterType_ParameterValue() {
		return (EReference)enumParameterTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getErlangDistributionType() {
		return erlangDistributionTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getErlangDistributionType_K() {
		return (EAttribute)erlangDistributionTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getErlangDistributionType_Mean() {
		return (EAttribute)erlangDistributionTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getExpressionParameterType() {
		return expressionParameterTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getExpressionParameterType_Value() {
		return (EAttribute)expressionParameterTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getFloatingParameterType() {
		return floatingParameterTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getFloatingParameterType_Value() {
		return (EAttribute)floatingParameterTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getGammaDistributionType() {
		return gammaDistributionTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGammaDistributionType_Scale() {
		return (EAttribute)gammaDistributionTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getGammaDistributionType_Shape() {
		return (EAttribute)gammaDistributionTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getLogNormalDistributionType() {
		return logNormalDistributionTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLogNormalDistributionType_Mean() {
		return (EAttribute)logNormalDistributionTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getLogNormalDistributionType_StandardDeviation() {
		return (EAttribute)logNormalDistributionTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMetadataType() {
		return metadataTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getMetadataType_Metaentry() {
		return (EReference)metadataTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNegativeExponentialDistributionType() {
		return negativeExponentialDistributionTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNegativeExponentialDistributionType_Mean() {
		return (EAttribute)negativeExponentialDistributionTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNormalDistributionType() {
		return normalDistributionTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNormalDistributionType_Mean() {
		return (EAttribute)normalDistributionTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNormalDistributionType_StandardDeviation() {
		return (EAttribute)normalDistributionTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getParameter() {
		return parameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameter_ResultRequest() {
		return (EAttribute)parameterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameter_ParameterValueGroup() {
		return (EAttribute)parameterEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getParameter_ParameterValue() {
		return (EReference)parameterEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameter_Kpi() {
		return (EAttribute)parameterEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameter_Sla() {
		return (EAttribute)parameterEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getNumericParameterType() {
		return numericParameterTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getNumericParameterType_Value() {
		return (EAttribute)numericParameterTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getMetaentryType() {
		return metaentryTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMetaentryType_Name() {
		return (EAttribute)metaentryTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getMetaentryType_Value() {
		return (EAttribute)metaentryTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getDistributionParameter() {
		return distributionParameterEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getDistributionParameter_Discrete() {
		return (EAttribute)distributionParameterEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getProcessAnalysisDataType() {
		return processAnalysisDataTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getProcessAnalysisDataType_Group() {
		return (EAttribute)processAnalysisDataTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getProcessAnalysisDataType_Scenario() {
		return (EReference)processAnalysisDataTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPropertyParameters() {
		return propertyParametersEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPropertyParameters_Property() {
		return (EReference)propertyParametersEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getRandomDistributionType() {
		return randomDistributionTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRandomDistributionType_Max() {
		return (EAttribute)randomDistributionTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getRandomDistributionType_Min() {
		return (EAttribute)randomDistributionTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getParameterValue() {
		return parameterValueEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameterValue_Instance() {
		return (EAttribute)parameterValueEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameterValue_Result() {
		return (EAttribute)parameterValueEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getParameterValue_ValidFor() {
		return (EAttribute)parameterValueEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPoissonDistributionType() {
		return poissonDistributionTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPoissonDistributionType_Mean() {
		return (EAttribute)poissonDistributionTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPropertyType() {
		return propertyTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getPropertyType_Name() {
		return (EAttribute)propertyTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getPriorityParameters() {
		return priorityParametersEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPriorityParameters_Interruptible() {
		return (EReference)priorityParametersEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getPriorityParameters_Priority() {
		return (EReference)priorityParametersEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTimeParameters() {
		return timeParametersEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTimeParameters_TransferTime() {
		return (EReference)timeParametersEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTimeParameters_QueueTime() {
		return (EReference)timeParametersEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTimeParameters_WaitTime() {
		return (EReference)timeParametersEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTimeParameters_SetUpTime() {
		return (EReference)timeParametersEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTimeParameters_ProcessingTime() {
		return (EReference)timeParametersEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTimeParameters_ValidationTime() {
		return (EReference)timeParametersEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getTimeParameters_ReworkTime() {
		return (EReference)timeParametersEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTimeParameters_TimeUnit() {
		return (EAttribute)timeParametersEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getStringParameterType() {
		return stringParameterTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getStringParameterType_Value() {
		return (EAttribute)stringParameterTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getScenario() {
		return scenarioEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScenario_ScenarioParameters() {
		return (EReference)scenarioEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScenario_ElementParameters() {
		return (EReference)scenarioEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScenario_Calendar() {
		return (EReference)scenarioEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScenario_VendorExtension() {
		return (EReference)scenarioEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScenario_Author() {
		return (EAttribute)scenarioEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScenario_Created() {
		return (EAttribute)scenarioEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScenario_Description() {
		return (EAttribute)scenarioEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScenario_Id() {
		return (EAttribute)scenarioEClass.getEStructuralFeatures().get(7);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScenario_Inherits() {
		return (EAttribute)scenarioEClass.getEStructuralFeatures().get(8);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScenario_Modified() {
		return (EAttribute)scenarioEClass.getEStructuralFeatures().get(9);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScenario_Name() {
		return (EAttribute)scenarioEClass.getEStructuralFeatures().get(10);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScenario_Result() {
		return (EAttribute)scenarioEClass.getEStructuralFeatures().get(11);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScenario_Vendor() {
		return (EAttribute)scenarioEClass.getEStructuralFeatures().get(12);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScenario_Version() {
		return (EAttribute)scenarioEClass.getEStructuralFeatures().get(13);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getResourceParameters() {
		return resourceParametersEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getResourceParameters_Selection() {
		return (EReference)resourceParametersEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getResourceParameters_Availability() {
		return (EReference)resourceParametersEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getResourceParameters_Quantity() {
		return (EReference)resourceParametersEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getResourceParameters_Workinghours() {
		return (EReference)resourceParametersEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getResourceParameters_Role() {
		return (EReference)resourceParametersEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getScenarioParameters() {
		return scenarioParametersEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScenarioParameters_Start() {
		return (EReference)scenarioParametersEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScenarioParameters_Duration() {
		return (EReference)scenarioParametersEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getScenarioParameters_PropertyParameters() {
		return (EReference)scenarioParametersEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScenarioParameters_BaseCurrencyUnit() {
		return (EAttribute)scenarioParametersEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScenarioParameters_BaseTimeUnit() {
		return (EAttribute)scenarioParametersEClass.getEStructuralFeatures().get(4);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScenarioParameters_Replication() {
		return (EAttribute)scenarioParametersEClass.getEStructuralFeatures().get(5);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getScenarioParameters_Seed() {
		return (EAttribute)scenarioParametersEClass.getEStructuralFeatures().get(6);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getVendorExtension() {
		return vendorExtensionEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVendorExtension_Any() {
		return (EAttribute)vendorExtensionEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVendorExtension_Name() {
		return (EAttribute)vendorExtensionEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getVendorExtension_AnyAttribute() {
		return (EAttribute)vendorExtensionEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTriangularDistributionType() {
		return triangularDistributionTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTriangularDistributionType_Max() {
		return (EAttribute)triangularDistributionTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTriangularDistributionType_Min() {
		return (EAttribute)triangularDistributionTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTriangularDistributionType_MostLikely() {
		return (EAttribute)triangularDistributionTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getTruncatedNormalDistributionType() {
		return truncatedNormalDistributionTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTruncatedNormalDistributionType_Max() {
		return (EAttribute)truncatedNormalDistributionTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTruncatedNormalDistributionType_Mean() {
		return (EAttribute)truncatedNormalDistributionTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTruncatedNormalDistributionType_Min() {
		return (EAttribute)truncatedNormalDistributionTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getTruncatedNormalDistributionType_StandardDeviation() {
		return (EAttribute)truncatedNormalDistributionTypeEClass.getEStructuralFeatures().get(3);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUniformDistributionType() {
		return uniformDistributionTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUniformDistributionType_Max() {
		return (EAttribute)uniformDistributionTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUniformDistributionType_Min() {
		return (EAttribute)uniformDistributionTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUserDistributionDataPointType() {
		return userDistributionDataPointTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUserDistributionDataPointType_ParameterValueGroup() {
		return (EAttribute)userDistributionDataPointTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUserDistributionDataPointType_ParameterValue() {
		return (EReference)userDistributionDataPointTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUserDistributionDataPointType_Probability() {
		return (EAttribute)userDistributionDataPointTypeEClass.getEStructuralFeatures().get(2);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getUserDistributionType() {
		return userDistributionTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getUserDistributionType_Group() {
		return (EAttribute)userDistributionTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EReference getUserDistributionType_UserDistributionDataPoint() {
		return (EReference)userDistributionTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EClass getWeibullDistributionType() {
		return weibullDistributionTypeEClass;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWeibullDistributionType_Scale() {
		return (EAttribute)weibullDistributionTypeEClass.getEStructuralFeatures().get(0);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EAttribute getWeibullDistributionType_Shape() {
		return (EAttribute)weibullDistributionTypeEClass.getEStructuralFeatures().get(1);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getResultType() {
		return resultTypeEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EEnum getTimeUnit() {
		return timeUnitEEnum;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getPackageNameType() {
		return packageNameTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getPriorityType() {
		return priorityTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getRuleFlowGroupType() {
		return ruleFlowGroupTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getTaskNameType() {
		return taskNameTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getVersionType() {
		return versionTypeEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getResultTypeObject() {
		return resultTypeObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public EDataType getTimeUnitObject() {
		return timeUnitObjectEDataType;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public ModelFactory getModelFactory() {
		return (ModelFactory)getEFactoryInstance();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isCreated = false;

	/**
	 * Creates the meta-model objects for the package.  This method is
	 * guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void createPackageContents() {
		if (isCreated) return;
		isCreated = true;

		// Create classes and their features
		betaDistributionTypeEClass = createEClass(BETA_DISTRIBUTION_TYPE);
		createEAttribute(betaDistributionTypeEClass, BETA_DISTRIBUTION_TYPE__SCALE);
		createEAttribute(betaDistributionTypeEClass, BETA_DISTRIBUTION_TYPE__SHAPE);

		binomialDistributionTypeEClass = createEClass(BINOMIAL_DISTRIBUTION_TYPE);
		createEAttribute(binomialDistributionTypeEClass, BINOMIAL_DISTRIBUTION_TYPE__PROBABILITY);
		createEAttribute(binomialDistributionTypeEClass, BINOMIAL_DISTRIBUTION_TYPE__TRIALS);

		booleanParameterTypeEClass = createEClass(BOOLEAN_PARAMETER_TYPE);
		createEAttribute(booleanParameterTypeEClass, BOOLEAN_PARAMETER_TYPE__VALUE);

		calendarEClass = createEClass(CALENDAR);
		createEAttribute(calendarEClass, CALENDAR__VALUE);
		createEAttribute(calendarEClass, CALENDAR__ID);
		createEAttribute(calendarEClass, CALENDAR__NAME);

		documentRootEClass = createEClass(DOCUMENT_ROOT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__BETA_DISTRIBUTION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__PARAMETER_VALUE);
		createEReference(documentRootEClass, DOCUMENT_ROOT__BINOMIAL_DISTRIBUTION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__BOOLEAN_PARAMETER);
		createEReference(documentRootEClass, DOCUMENT_ROOT__DATE_TIME_PARAMETER);
		createEReference(documentRootEClass, DOCUMENT_ROOT__DECIMAL_PARAMETER);
		createEReference(documentRootEClass, DOCUMENT_ROOT__DURATION_PARAMETER);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ENUM_PARAMETER);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ERLANG_DISTRIBUTION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__EXPRESSION_PARAMETER);
		createEReference(documentRootEClass, DOCUMENT_ROOT__FLOATING_PARAMETER);
		createEReference(documentRootEClass, DOCUMENT_ROOT__GAMMA_DISTRIBUTION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__GLOBAL);
		createEReference(documentRootEClass, DOCUMENT_ROOT__LOG_NORMAL_DISTRIBUTION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__METADATA);
		createEReference(documentRootEClass, DOCUMENT_ROOT__METAENTRY);
		createEReference(documentRootEClass, DOCUMENT_ROOT__NEGATIVE_EXPONENTIAL_DISTRIBUTION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__NORMAL_DISTRIBUTION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__NUMERIC_PARAMETER);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ON_ENTRY_SCRIPT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__ON_EXIT_SCRIPT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__POISSON_DISTRIBUTION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__PROCESS_ANALYSIS_DATA);
		createEReference(documentRootEClass, DOCUMENT_ROOT__RANDOM_DISTRIBUTION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__STRING_PARAMETER);
		createEReference(documentRootEClass, DOCUMENT_ROOT__TRIANGULAR_DISTRIBUTION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__TRUNCATED_NORMAL_DISTRIBUTION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__UNIFORM_DISTRIBUTION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__USER_DISTRIBUTION);
		createEReference(documentRootEClass, DOCUMENT_ROOT__USER_DISTRIBUTION_DATA_POINT);
		createEReference(documentRootEClass, DOCUMENT_ROOT__WEIBULL_DISTRIBUTION);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__PACKAGE_NAME);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__PRIORITY);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__RULE_FLOW_GROUP);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__TASK_NAME);
		createEAttribute(documentRootEClass, DOCUMENT_ROOT__VERSION);

		globalTypeEClass = createEClass(GLOBAL_TYPE);
		createEAttribute(globalTypeEClass, GLOBAL_TYPE__IDENTIFIER);
		createEAttribute(globalTypeEClass, GLOBAL_TYPE__TYPE);

		controlParametersEClass = createEClass(CONTROL_PARAMETERS);
		createEReference(controlParametersEClass, CONTROL_PARAMETERS__PROBABILITY);
		createEReference(controlParametersEClass, CONTROL_PARAMETERS__INTER_TRIGGER_TIMER);
		createEReference(controlParametersEClass, CONTROL_PARAMETERS__MAX_TRIGGER_COUNT);

		constantParameterEClass = createEClass(CONSTANT_PARAMETER);

		costParametersEClass = createEClass(COST_PARAMETERS);
		createEReference(costParametersEClass, COST_PARAMETERS__FIXED_COST);
		createEReference(costParametersEClass, COST_PARAMETERS__UNIT_COST);
		createEAttribute(costParametersEClass, COST_PARAMETERS__CURRENCY_UNIT);

		importTypeEClass = createEClass(IMPORT_TYPE);
		createEAttribute(importTypeEClass, IMPORT_TYPE__NAME);

		dateTimeParameterTypeEClass = createEClass(DATE_TIME_PARAMETER_TYPE);
		createEAttribute(dateTimeParameterTypeEClass, DATE_TIME_PARAMETER_TYPE__VALUE);

		decimalParameterTypeEClass = createEClass(DECIMAL_PARAMETER_TYPE);
		createEAttribute(decimalParameterTypeEClass, DECIMAL_PARAMETER_TYPE__VALUE);

		onEntryScriptTypeEClass = createEClass(ON_ENTRY_SCRIPT_TYPE);
		createEAttribute(onEntryScriptTypeEClass, ON_ENTRY_SCRIPT_TYPE__SCRIPT);
		createEAttribute(onEntryScriptTypeEClass, ON_ENTRY_SCRIPT_TYPE__SCRIPT_FORMAT);

		elementParametersEClass = createEClass(ELEMENT_PARAMETERS);
		createEReference(elementParametersEClass, ELEMENT_PARAMETERS__TIME_PARAMETERS);
		createEReference(elementParametersEClass, ELEMENT_PARAMETERS__CONTROL_PARAMETERS);
		createEReference(elementParametersEClass, ELEMENT_PARAMETERS__RESOURCE_PARAMETERS);
		createEReference(elementParametersEClass, ELEMENT_PARAMETERS__PRIORITY_PARAMETERS);
		createEReference(elementParametersEClass, ELEMENT_PARAMETERS__COST_PARAMETERS);
		createEReference(elementParametersEClass, ELEMENT_PARAMETERS__PROPERTY_PARAMETERS);
		createEReference(elementParametersEClass, ELEMENT_PARAMETERS__VENDOR_EXTENSION);
		createEAttribute(elementParametersEClass, ELEMENT_PARAMETERS__ELEMENT_ID);
		createEAttribute(elementParametersEClass, ELEMENT_PARAMETERS__ID);

		onExitScriptTypeEClass = createEClass(ON_EXIT_SCRIPT_TYPE);
		createEAttribute(onExitScriptTypeEClass, ON_EXIT_SCRIPT_TYPE__SCRIPT);
		createEAttribute(onExitScriptTypeEClass, ON_EXIT_SCRIPT_TYPE__SCRIPT_FORMAT);

		durationParameterTypeEClass = createEClass(DURATION_PARAMETER_TYPE);
		createEAttribute(durationParameterTypeEClass, DURATION_PARAMETER_TYPE__VALUE);

		enumParameterTypeEClass = createEClass(ENUM_PARAMETER_TYPE);
		createEAttribute(enumParameterTypeEClass, ENUM_PARAMETER_TYPE__GROUP);
		createEAttribute(enumParameterTypeEClass, ENUM_PARAMETER_TYPE__PARAMETER_VALUE_GROUP);
		createEReference(enumParameterTypeEClass, ENUM_PARAMETER_TYPE__PARAMETER_VALUE);

		erlangDistributionTypeEClass = createEClass(ERLANG_DISTRIBUTION_TYPE);
		createEAttribute(erlangDistributionTypeEClass, ERLANG_DISTRIBUTION_TYPE__K);
		createEAttribute(erlangDistributionTypeEClass, ERLANG_DISTRIBUTION_TYPE__MEAN);

		expressionParameterTypeEClass = createEClass(EXPRESSION_PARAMETER_TYPE);
		createEAttribute(expressionParameterTypeEClass, EXPRESSION_PARAMETER_TYPE__VALUE);

		floatingParameterTypeEClass = createEClass(FLOATING_PARAMETER_TYPE);
		createEAttribute(floatingParameterTypeEClass, FLOATING_PARAMETER_TYPE__VALUE);

		gammaDistributionTypeEClass = createEClass(GAMMA_DISTRIBUTION_TYPE);
		createEAttribute(gammaDistributionTypeEClass, GAMMA_DISTRIBUTION_TYPE__SCALE);
		createEAttribute(gammaDistributionTypeEClass, GAMMA_DISTRIBUTION_TYPE__SHAPE);

		logNormalDistributionTypeEClass = createEClass(LOG_NORMAL_DISTRIBUTION_TYPE);
		createEAttribute(logNormalDistributionTypeEClass, LOG_NORMAL_DISTRIBUTION_TYPE__MEAN);
		createEAttribute(logNormalDistributionTypeEClass, LOG_NORMAL_DISTRIBUTION_TYPE__STANDARD_DEVIATION);

		metadataTypeEClass = createEClass(METADATA_TYPE);
		createEReference(metadataTypeEClass, METADATA_TYPE__METAENTRY);

		negativeExponentialDistributionTypeEClass = createEClass(NEGATIVE_EXPONENTIAL_DISTRIBUTION_TYPE);
		createEAttribute(negativeExponentialDistributionTypeEClass, NEGATIVE_EXPONENTIAL_DISTRIBUTION_TYPE__MEAN);

		normalDistributionTypeEClass = createEClass(NORMAL_DISTRIBUTION_TYPE);
		createEAttribute(normalDistributionTypeEClass, NORMAL_DISTRIBUTION_TYPE__MEAN);
		createEAttribute(normalDistributionTypeEClass, NORMAL_DISTRIBUTION_TYPE__STANDARD_DEVIATION);

		parameterEClass = createEClass(PARAMETER);
		createEAttribute(parameterEClass, PARAMETER__RESULT_REQUEST);
		createEAttribute(parameterEClass, PARAMETER__PARAMETER_VALUE_GROUP);
		createEReference(parameterEClass, PARAMETER__PARAMETER_VALUE);
		createEAttribute(parameterEClass, PARAMETER__KPI);
		createEAttribute(parameterEClass, PARAMETER__SLA);

		numericParameterTypeEClass = createEClass(NUMERIC_PARAMETER_TYPE);
		createEAttribute(numericParameterTypeEClass, NUMERIC_PARAMETER_TYPE__VALUE);

		metaentryTypeEClass = createEClass(METAENTRY_TYPE);
		createEAttribute(metaentryTypeEClass, METAENTRY_TYPE__NAME);
		createEAttribute(metaentryTypeEClass, METAENTRY_TYPE__VALUE);

		distributionParameterEClass = createEClass(DISTRIBUTION_PARAMETER);
		createEAttribute(distributionParameterEClass, DISTRIBUTION_PARAMETER__DISCRETE);

		processAnalysisDataTypeEClass = createEClass(PROCESS_ANALYSIS_DATA_TYPE);
		createEAttribute(processAnalysisDataTypeEClass, PROCESS_ANALYSIS_DATA_TYPE__GROUP);
		createEReference(processAnalysisDataTypeEClass, PROCESS_ANALYSIS_DATA_TYPE__SCENARIO);

		propertyParametersEClass = createEClass(PROPERTY_PARAMETERS);
		createEReference(propertyParametersEClass, PROPERTY_PARAMETERS__PROPERTY);

		randomDistributionTypeEClass = createEClass(RANDOM_DISTRIBUTION_TYPE);
		createEAttribute(randomDistributionTypeEClass, RANDOM_DISTRIBUTION_TYPE__MAX);
		createEAttribute(randomDistributionTypeEClass, RANDOM_DISTRIBUTION_TYPE__MIN);

		parameterValueEClass = createEClass(PARAMETER_VALUE);
		createEAttribute(parameterValueEClass, PARAMETER_VALUE__INSTANCE);
		createEAttribute(parameterValueEClass, PARAMETER_VALUE__RESULT);
		createEAttribute(parameterValueEClass, PARAMETER_VALUE__VALID_FOR);

		poissonDistributionTypeEClass = createEClass(POISSON_DISTRIBUTION_TYPE);
		createEAttribute(poissonDistributionTypeEClass, POISSON_DISTRIBUTION_TYPE__MEAN);

		propertyTypeEClass = createEClass(PROPERTY_TYPE);
		createEAttribute(propertyTypeEClass, PROPERTY_TYPE__NAME);

		priorityParametersEClass = createEClass(PRIORITY_PARAMETERS);
		createEReference(priorityParametersEClass, PRIORITY_PARAMETERS__INTERRUPTIBLE);
		createEReference(priorityParametersEClass, PRIORITY_PARAMETERS__PRIORITY);

		timeParametersEClass = createEClass(TIME_PARAMETERS);
		createEReference(timeParametersEClass, TIME_PARAMETERS__TRANSFER_TIME);
		createEReference(timeParametersEClass, TIME_PARAMETERS__QUEUE_TIME);
		createEReference(timeParametersEClass, TIME_PARAMETERS__WAIT_TIME);
		createEReference(timeParametersEClass, TIME_PARAMETERS__SET_UP_TIME);
		createEReference(timeParametersEClass, TIME_PARAMETERS__PROCESSING_TIME);
		createEReference(timeParametersEClass, TIME_PARAMETERS__VALIDATION_TIME);
		createEReference(timeParametersEClass, TIME_PARAMETERS__REWORK_TIME);
		createEAttribute(timeParametersEClass, TIME_PARAMETERS__TIME_UNIT);

		stringParameterTypeEClass = createEClass(STRING_PARAMETER_TYPE);
		createEAttribute(stringParameterTypeEClass, STRING_PARAMETER_TYPE__VALUE);

		scenarioEClass = createEClass(SCENARIO);
		createEReference(scenarioEClass, SCENARIO__SCENARIO_PARAMETERS);
		createEReference(scenarioEClass, SCENARIO__ELEMENT_PARAMETERS);
		createEReference(scenarioEClass, SCENARIO__CALENDAR);
		createEReference(scenarioEClass, SCENARIO__VENDOR_EXTENSION);
		createEAttribute(scenarioEClass, SCENARIO__AUTHOR);
		createEAttribute(scenarioEClass, SCENARIO__CREATED);
		createEAttribute(scenarioEClass, SCENARIO__DESCRIPTION);
		createEAttribute(scenarioEClass, SCENARIO__ID);
		createEAttribute(scenarioEClass, SCENARIO__INHERITS);
		createEAttribute(scenarioEClass, SCENARIO__MODIFIED);
		createEAttribute(scenarioEClass, SCENARIO__NAME);
		createEAttribute(scenarioEClass, SCENARIO__RESULT);
		createEAttribute(scenarioEClass, SCENARIO__VENDOR);
		createEAttribute(scenarioEClass, SCENARIO__VERSION);

		resourceParametersEClass = createEClass(RESOURCE_PARAMETERS);
		createEReference(resourceParametersEClass, RESOURCE_PARAMETERS__SELECTION);
		createEReference(resourceParametersEClass, RESOURCE_PARAMETERS__AVAILABILITY);
		createEReference(resourceParametersEClass, RESOURCE_PARAMETERS__QUANTITY);
		createEReference(resourceParametersEClass, RESOURCE_PARAMETERS__WORKINGHOURS);
		createEReference(resourceParametersEClass, RESOURCE_PARAMETERS__ROLE);

		scenarioParametersEClass = createEClass(SCENARIO_PARAMETERS);
		createEReference(scenarioParametersEClass, SCENARIO_PARAMETERS__START);
		createEReference(scenarioParametersEClass, SCENARIO_PARAMETERS__DURATION);
		createEReference(scenarioParametersEClass, SCENARIO_PARAMETERS__PROPERTY_PARAMETERS);
		createEAttribute(scenarioParametersEClass, SCENARIO_PARAMETERS__BASE_CURRENCY_UNIT);
		createEAttribute(scenarioParametersEClass, SCENARIO_PARAMETERS__BASE_TIME_UNIT);
		createEAttribute(scenarioParametersEClass, SCENARIO_PARAMETERS__REPLICATION);
		createEAttribute(scenarioParametersEClass, SCENARIO_PARAMETERS__SEED);

		vendorExtensionEClass = createEClass(VENDOR_EXTENSION);
		createEAttribute(vendorExtensionEClass, VENDOR_EXTENSION__ANY);
		createEAttribute(vendorExtensionEClass, VENDOR_EXTENSION__NAME);
		createEAttribute(vendorExtensionEClass, VENDOR_EXTENSION__ANY_ATTRIBUTE);

		triangularDistributionTypeEClass = createEClass(TRIANGULAR_DISTRIBUTION_TYPE);
		createEAttribute(triangularDistributionTypeEClass, TRIANGULAR_DISTRIBUTION_TYPE__MAX);
		createEAttribute(triangularDistributionTypeEClass, TRIANGULAR_DISTRIBUTION_TYPE__MIN);
		createEAttribute(triangularDistributionTypeEClass, TRIANGULAR_DISTRIBUTION_TYPE__MOST_LIKELY);

		truncatedNormalDistributionTypeEClass = createEClass(TRUNCATED_NORMAL_DISTRIBUTION_TYPE);
		createEAttribute(truncatedNormalDistributionTypeEClass, TRUNCATED_NORMAL_DISTRIBUTION_TYPE__MAX);
		createEAttribute(truncatedNormalDistributionTypeEClass, TRUNCATED_NORMAL_DISTRIBUTION_TYPE__MEAN);
		createEAttribute(truncatedNormalDistributionTypeEClass, TRUNCATED_NORMAL_DISTRIBUTION_TYPE__MIN);
		createEAttribute(truncatedNormalDistributionTypeEClass, TRUNCATED_NORMAL_DISTRIBUTION_TYPE__STANDARD_DEVIATION);

		uniformDistributionTypeEClass = createEClass(UNIFORM_DISTRIBUTION_TYPE);
		createEAttribute(uniformDistributionTypeEClass, UNIFORM_DISTRIBUTION_TYPE__MAX);
		createEAttribute(uniformDistributionTypeEClass, UNIFORM_DISTRIBUTION_TYPE__MIN);

		userDistributionDataPointTypeEClass = createEClass(USER_DISTRIBUTION_DATA_POINT_TYPE);
		createEAttribute(userDistributionDataPointTypeEClass, USER_DISTRIBUTION_DATA_POINT_TYPE__PARAMETER_VALUE_GROUP);
		createEReference(userDistributionDataPointTypeEClass, USER_DISTRIBUTION_DATA_POINT_TYPE__PARAMETER_VALUE);
		createEAttribute(userDistributionDataPointTypeEClass, USER_DISTRIBUTION_DATA_POINT_TYPE__PROBABILITY);

		userDistributionTypeEClass = createEClass(USER_DISTRIBUTION_TYPE);
		createEAttribute(userDistributionTypeEClass, USER_DISTRIBUTION_TYPE__GROUP);
		createEReference(userDistributionTypeEClass, USER_DISTRIBUTION_TYPE__USER_DISTRIBUTION_DATA_POINT);

		weibullDistributionTypeEClass = createEClass(WEIBULL_DISTRIBUTION_TYPE);
		createEAttribute(weibullDistributionTypeEClass, WEIBULL_DISTRIBUTION_TYPE__SCALE);
		createEAttribute(weibullDistributionTypeEClass, WEIBULL_DISTRIBUTION_TYPE__SHAPE);

		// Create enums
		resultTypeEEnum = createEEnum(RESULT_TYPE);
		timeUnitEEnum = createEEnum(TIME_UNIT);

		// Create data types
		packageNameTypeEDataType = createEDataType(PACKAGE_NAME_TYPE);
		priorityTypeEDataType = createEDataType(PRIORITY_TYPE);
		ruleFlowGroupTypeEDataType = createEDataType(RULE_FLOW_GROUP_TYPE);
		taskNameTypeEDataType = createEDataType(TASK_NAME_TYPE);
		versionTypeEDataType = createEDataType(VERSION_TYPE);
		resultTypeObjectEDataType = createEDataType(RESULT_TYPE_OBJECT);
		timeUnitObjectEDataType = createEDataType(TIME_UNIT_OBJECT);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	private boolean isInitialized = false;

	/**
	 * Complete the initialization of the package and its meta-model.  This
	 * method is guarded to have no affect on any invocation but its first.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void initializePackageContents() {
		if (isInitialized) return;
		isInitialized = true;

		// Initialize package
		setName(eNAME);
		setNsPrefix(eNS_PREFIX);
		setNsURI(eNS_URI);

		// Obtain other dependent packages
		XMLTypePackage theXMLTypePackage = (XMLTypePackage)EPackage.Registry.INSTANCE.getEPackage(XMLTypePackage.eNS_URI);
		Bpmn2Package theBpmn2Package = (Bpmn2Package)EPackage.Registry.INSTANCE.getEPackage(Bpmn2Package.eNS_URI);

		// Create type parameters

		// Set bounds for type parameters

		// Add supertypes to classes
		betaDistributionTypeEClass.getESuperTypes().add(this.getDistributionParameter());
		binomialDistributionTypeEClass.getESuperTypes().add(this.getDistributionParameter());
		booleanParameterTypeEClass.getESuperTypes().add(this.getConstantParameter());
		documentRootEClass.getESuperTypes().add(theBpmn2Package.getDocumentRoot());
		constantParameterEClass.getESuperTypes().add(this.getParameterValue());
		dateTimeParameterTypeEClass.getESuperTypes().add(this.getConstantParameter());
		decimalParameterTypeEClass.getESuperTypes().add(this.getConstantParameter());
		durationParameterTypeEClass.getESuperTypes().add(this.getConstantParameter());
		enumParameterTypeEClass.getESuperTypes().add(this.getParameterValue());
		erlangDistributionTypeEClass.getESuperTypes().add(this.getDistributionParameter());
		expressionParameterTypeEClass.getESuperTypes().add(this.getParameterValue());
		floatingParameterTypeEClass.getESuperTypes().add(this.getConstantParameter());
		gammaDistributionTypeEClass.getESuperTypes().add(this.getDistributionParameter());
		logNormalDistributionTypeEClass.getESuperTypes().add(this.getDistributionParameter());
		negativeExponentialDistributionTypeEClass.getESuperTypes().add(this.getDistributionParameter());
		normalDistributionTypeEClass.getESuperTypes().add(this.getDistributionParameter());
		numericParameterTypeEClass.getESuperTypes().add(this.getConstantParameter());
		distributionParameterEClass.getESuperTypes().add(this.getParameterValue());
		randomDistributionTypeEClass.getESuperTypes().add(this.getDistributionParameter());
		poissonDistributionTypeEClass.getESuperTypes().add(this.getDistributionParameter());
		propertyTypeEClass.getESuperTypes().add(this.getParameter());
		stringParameterTypeEClass.getESuperTypes().add(this.getConstantParameter());
		triangularDistributionTypeEClass.getESuperTypes().add(this.getDistributionParameter());
		truncatedNormalDistributionTypeEClass.getESuperTypes().add(this.getDistributionParameter());
		uniformDistributionTypeEClass.getESuperTypes().add(this.getDistributionParameter());
		userDistributionTypeEClass.getESuperTypes().add(this.getDistributionParameter());
		weibullDistributionTypeEClass.getESuperTypes().add(this.getDistributionParameter());

		// Initialize classes and features; add operations and parameters
		initEClass(betaDistributionTypeEClass, BetaDistributionType.class, "BetaDistributionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBetaDistributionType_Scale(), theXMLTypePackage.getDouble(), "scale", null, 0, 1, BetaDistributionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBetaDistributionType_Shape(), theXMLTypePackage.getDouble(), "shape", null, 0, 1, BetaDistributionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(binomialDistributionTypeEClass, BinomialDistributionType.class, "BinomialDistributionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBinomialDistributionType_Probability(), theXMLTypePackage.getDouble(), "probability", null, 0, 1, BinomialDistributionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getBinomialDistributionType_Trials(), theXMLTypePackage.getLong(), "trials", null, 0, 1, BinomialDistributionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(booleanParameterTypeEClass, BooleanParameterType.class, "BooleanParameterType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getBooleanParameterType_Value(), theXMLTypePackage.getBoolean(), "value", null, 0, 1, BooleanParameterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(calendarEClass, Calendar.class, "Calendar", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getCalendar_Value(), theXMLTypePackage.getString(), "value", null, 0, 1, Calendar.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCalendar_Id(), theXMLTypePackage.getID(), "id", null, 0, 1, Calendar.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCalendar_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, Calendar.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(documentRootEClass, DocumentRoot.class, "DocumentRoot", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getDocumentRoot_BetaDistribution(), this.getBetaDistributionType(), null, "betaDistribution", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_ParameterValue(), this.getParameterValue(), null, "parameterValue", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_BinomialDistribution(), this.getBinomialDistributionType(), null, "binomialDistribution", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_BooleanParameter(), this.getBooleanParameterType(), null, "booleanParameter", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_DateTimeParameter(), this.getDateTimeParameterType(), null, "dateTimeParameter", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_DecimalParameter(), this.getDecimalParameterType(), null, "decimalParameter", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_DurationParameter(), this.getDurationParameterType(), null, "durationParameter", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_EnumParameter(), this.getEnumParameterType(), null, "enumParameter", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_ErlangDistribution(), this.getErlangDistributionType(), null, "erlangDistribution", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_ExpressionParameter(), this.getExpressionParameterType(), null, "expressionParameter", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_FloatingParameter(), this.getFloatingParameterType(), null, "floatingParameter", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_GammaDistribution(), this.getGammaDistributionType(), null, "gammaDistribution", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Global(), this.getGlobalType(), null, "global", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_LogNormalDistribution(), this.getLogNormalDistributionType(), null, "logNormalDistribution", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Metadata(), this.getMetadataType(), null, "metadata", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_Metaentry(), this.getMetaentryType(), null, "metaentry", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_NegativeExponentialDistribution(), this.getNegativeExponentialDistributionType(), null, "negativeExponentialDistribution", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_NormalDistribution(), this.getNormalDistributionType(), null, "normalDistribution", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_NumericParameter(), this.getNumericParameterType(), null, "numericParameter", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_OnEntryScript(), this.getOnEntryScriptType(), null, "onEntryScript", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_OnExitScript(), this.getOnExitScriptType(), null, "onExitScript", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_PoissonDistribution(), this.getPoissonDistributionType(), null, "poissonDistribution", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_ProcessAnalysisData(), this.getProcessAnalysisDataType(), null, "processAnalysisData", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_RandomDistribution(), this.getRandomDistributionType(), null, "randomDistribution", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_StringParameter(), this.getStringParameterType(), null, "stringParameter", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_TriangularDistribution(), this.getTriangularDistributionType(), null, "triangularDistribution", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_TruncatedNormalDistribution(), this.getTruncatedNormalDistributionType(), null, "truncatedNormalDistribution", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_UniformDistribution(), this.getUniformDistributionType(), null, "uniformDistribution", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_UserDistribution(), this.getUserDistributionType(), null, "userDistribution", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_UserDistributionDataPoint(), this.getUserDistributionDataPointType(), null, "userDistributionDataPoint", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getDocumentRoot_WeibullDistribution(), this.getWeibullDistributionType(), null, "weibullDistribution", null, 0, -2, null, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_PackageName(), this.getPackageNameType(), "packageName", null, 0, 1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_Priority(), this.getPriorityType(), "priority", null, 0, 1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_RuleFlowGroup(), this.getRuleFlowGroupType(), "ruleFlowGroup", null, 0, 1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_TaskName(), this.getTaskNameType(), "taskName", null, 0, 1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getDocumentRoot_Version(), this.getVersionType(), "version", null, 0, 1, null, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(globalTypeEClass, GlobalType.class, "GlobalType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGlobalType_Identifier(), theXMLTypePackage.getString(), "identifier", null, 1, 1, GlobalType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGlobalType_Type(), theXMLTypePackage.getString(), "type", null, 1, 1, GlobalType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(controlParametersEClass, ControlParameters.class, "ControlParameters", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getControlParameters_Probability(), this.getParameter(), null, "probability", null, 0, 1, ControlParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getControlParameters_InterTriggerTimer(), this.getParameter(), null, "interTriggerTimer", null, 0, 1, ControlParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getControlParameters_MaxTriggerCount(), this.getParameter(), null, "maxTriggerCount", null, 0, 1, ControlParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(constantParameterEClass, ConstantParameter.class, "ConstantParameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);

		initEClass(costParametersEClass, CostParameters.class, "CostParameters", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getCostParameters_FixedCost(), this.getParameter(), null, "fixedCost", null, 0, 1, CostParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getCostParameters_UnitCost(), this.getParameter(), null, "unitCost", null, 0, 1, CostParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getCostParameters_CurrencyUnit(), theXMLTypePackage.getString(), "currencyUnit", null, 0, 1, CostParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(importTypeEClass, ImportType.class, "ImportType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getImportType_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, ImportType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(dateTimeParameterTypeEClass, DateTimeParameterType.class, "DateTimeParameterType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDateTimeParameterType_Value(), theXMLTypePackage.getDateTime(), "value", null, 0, 1, DateTimeParameterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(decimalParameterTypeEClass, DecimalParameterType.class, "DecimalParameterType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDecimalParameterType_Value(), theXMLTypePackage.getDecimal(), "value", null, 0, 1, DecimalParameterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(onEntryScriptTypeEClass, OnEntryScriptType.class, "OnEntryScriptType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getOnEntryScriptType_Script(), theXMLTypePackage.getString(), "script", null, 1, 1, OnEntryScriptType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOnEntryScriptType_ScriptFormat(), theXMLTypePackage.getString(), "scriptFormat", null, 1, 1, OnEntryScriptType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(elementParametersEClass, ElementParameters.class, "ElementParameters", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getElementParameters_TimeParameters(), this.getTimeParameters(), null, "timeParameters", null, 0, 1, ElementParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getElementParameters_ControlParameters(), this.getControlParameters(), null, "controlParameters", null, 0, 1, ElementParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getElementParameters_ResourceParameters(), this.getResourceParameters(), null, "resourceParameters", null, 0, 1, ElementParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getElementParameters_PriorityParameters(), this.getPriorityParameters(), null, "priorityParameters", null, 0, 1, ElementParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getElementParameters_CostParameters(), this.getCostParameters(), null, "costParameters", null, 0, 1, ElementParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getElementParameters_PropertyParameters(), this.getPropertyParameters(), null, "propertyParameters", null, 0, 1, ElementParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getElementParameters_VendorExtension(), this.getVendorExtension(), null, "vendorExtension", null, 0, -1, ElementParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getElementParameters_ElementId(), theXMLTypePackage.getString(), "elementId", null, 0, 1, ElementParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getElementParameters_Id(), theXMLTypePackage.getID(), "id", null, 0, 1, ElementParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(onExitScriptTypeEClass, OnExitScriptType.class, "OnExitScriptType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getOnExitScriptType_Script(), theXMLTypePackage.getString(), "script", null, 1, 1, OnExitScriptType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getOnExitScriptType_ScriptFormat(), theXMLTypePackage.getString(), "scriptFormat", null, 1, 1, OnExitScriptType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(durationParameterTypeEClass, DurationParameterType.class, "DurationParameterType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDurationParameterType_Value(), theXMLTypePackage.getDuration(), "value", null, 0, 1, DurationParameterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(enumParameterTypeEClass, EnumParameterType.class, "EnumParameterType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getEnumParameterType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, EnumParameterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getEnumParameterType_ParameterValueGroup(), ecorePackage.getEFeatureMapEntry(), "parameterValueGroup", null, 1, -1, EnumParameterType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEReference(getEnumParameterType_ParameterValue(), this.getParameterValue(), null, "parameterValue", null, 1, -1, EnumParameterType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(erlangDistributionTypeEClass, ErlangDistributionType.class, "ErlangDistributionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getErlangDistributionType_K(), theXMLTypePackage.getDouble(), "k", null, 0, 1, ErlangDistributionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getErlangDistributionType_Mean(), theXMLTypePackage.getDouble(), "mean", null, 0, 1, ErlangDistributionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(expressionParameterTypeEClass, ExpressionParameterType.class, "ExpressionParameterType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getExpressionParameterType_Value(), theXMLTypePackage.getString(), "value", null, 0, 1, ExpressionParameterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(floatingParameterTypeEClass, FloatingParameterType.class, "FloatingParameterType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getFloatingParameterType_Value(), theXMLTypePackage.getDouble(), "value", null, 0, 1, FloatingParameterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(gammaDistributionTypeEClass, GammaDistributionType.class, "GammaDistributionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getGammaDistributionType_Scale(), theXMLTypePackage.getDouble(), "scale", null, 0, 1, GammaDistributionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getGammaDistributionType_Shape(), theXMLTypePackage.getDouble(), "shape", null, 0, 1, GammaDistributionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(logNormalDistributionTypeEClass, LogNormalDistributionType.class, "LogNormalDistributionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getLogNormalDistributionType_Mean(), theXMLTypePackage.getDouble(), "mean", null, 0, 1, LogNormalDistributionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getLogNormalDistributionType_StandardDeviation(), theXMLTypePackage.getDouble(), "standardDeviation", null, 0, 1, LogNormalDistributionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(metadataTypeEClass, MetadataType.class, "MetadataType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getMetadataType_Metaentry(), this.getMetaentryType(), null, "metaentry", null, 1, -1, MetadataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(negativeExponentialDistributionTypeEClass, NegativeExponentialDistributionType.class, "NegativeExponentialDistributionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNegativeExponentialDistributionType_Mean(), theXMLTypePackage.getDouble(), "mean", null, 0, 1, NegativeExponentialDistributionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(normalDistributionTypeEClass, NormalDistributionType.class, "NormalDistributionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNormalDistributionType_Mean(), theXMLTypePackage.getDouble(), "mean", null, 0, 1, NormalDistributionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getNormalDistributionType_StandardDeviation(), theXMLTypePackage.getDouble(), "standardDeviation", null, 0, 1, NormalDistributionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(parameterEClass, Parameter.class, "Parameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getParameter_ResultRequest(), this.getResultType(), "resultRequest", null, 0, -1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getParameter_ParameterValueGroup(), ecorePackage.getEFeatureMapEntry(), "parameterValueGroup", null, 0, -1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getParameter_ParameterValue(), this.getParameterValue(), null, "parameterValue", null, 0, -1, Parameter.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getParameter_Kpi(), theXMLTypePackage.getBoolean(), "kpi", "false", 0, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getParameter_Sla(), theXMLTypePackage.getBoolean(), "sla", "false", 0, 1, Parameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(numericParameterTypeEClass, NumericParameterType.class, "NumericParameterType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getNumericParameterType_Value(), theXMLTypePackage.getLong(), "value", null, 0, 1, NumericParameterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(metaentryTypeEClass, MetaentryType.class, "MetaentryType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getMetaentryType_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, MetaentryType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getMetaentryType_Value(), theXMLTypePackage.getString(), "value", null, 1, 1, MetaentryType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(distributionParameterEClass, DistributionParameter.class, "DistributionParameter", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getDistributionParameter_Discrete(), theXMLTypePackage.getBoolean(), "discrete", "false", 0, 1, DistributionParameter.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(processAnalysisDataTypeEClass, ProcessAnalysisDataType.class, "ProcessAnalysisDataType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getProcessAnalysisDataType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, ProcessAnalysisDataType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getProcessAnalysisDataType_Scenario(), this.getScenario(), null, "scenario", null, 1, -1, ProcessAnalysisDataType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(propertyParametersEClass, PropertyParameters.class, "PropertyParameters", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPropertyParameters_Property(), this.getPropertyType(), null, "property", null, 0, -1, PropertyParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(randomDistributionTypeEClass, RandomDistributionType.class, "RandomDistributionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getRandomDistributionType_Max(), theXMLTypePackage.getDouble(), "max", null, 0, 1, RandomDistributionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getRandomDistributionType_Min(), theXMLTypePackage.getDouble(), "min", null, 0, 1, RandomDistributionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(parameterValueEClass, ParameterValue.class, "ParameterValue", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getParameterValue_Instance(), theXMLTypePackage.getString(), "instance", null, 0, 1, ParameterValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getParameterValue_Result(), this.getResultType(), "result", null, 0, 1, ParameterValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getParameterValue_ValidFor(), theXMLTypePackage.getIDREF(), "validFor", null, 0, 1, ParameterValue.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(poissonDistributionTypeEClass, PoissonDistributionType.class, "PoissonDistributionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPoissonDistributionType_Mean(), theXMLTypePackage.getDouble(), "mean", null, 0, 1, PoissonDistributionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(propertyTypeEClass, PropertyType.class, "PropertyType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getPropertyType_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, PropertyType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(priorityParametersEClass, PriorityParameters.class, "PriorityParameters", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getPriorityParameters_Interruptible(), this.getParameter(), null, "interruptible", null, 0, 1, PriorityParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getPriorityParameters_Priority(), this.getParameter(), null, "priority", null, 0, 1, PriorityParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(timeParametersEClass, TimeParameters.class, "TimeParameters", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getTimeParameters_TransferTime(), this.getParameter(), null, "transferTime", null, 0, 1, TimeParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTimeParameters_QueueTime(), this.getParameter(), null, "queueTime", null, 0, 1, TimeParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTimeParameters_WaitTime(), this.getParameter(), null, "waitTime", null, 0, 1, TimeParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTimeParameters_SetUpTime(), this.getParameter(), null, "setUpTime", null, 0, 1, TimeParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTimeParameters_ProcessingTime(), this.getParameter(), null, "processingTime", null, 0, 1, TimeParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTimeParameters_ValidationTime(), this.getParameter(), null, "validationTime", null, 0, 1, TimeParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getTimeParameters_ReworkTime(), this.getParameter(), null, "reworkTime", null, 0, 1, TimeParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTimeParameters_TimeUnit(), this.getTimeUnit(), "timeUnit", null, 0, 1, TimeParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(stringParameterTypeEClass, StringParameterType.class, "StringParameterType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getStringParameterType_Value(), theXMLTypePackage.getString(), "value", null, 0, 1, StringParameterType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(scenarioEClass, Scenario.class, "Scenario", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getScenario_ScenarioParameters(), this.getScenarioParameters(), null, "scenarioParameters", null, 0, 1, Scenario.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getScenario_ElementParameters(), this.getElementParameters(), null, "elementParameters", null, 0, -1, Scenario.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getScenario_Calendar(), this.getCalendar(), null, "calendar", null, 0, -1, Scenario.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getScenario_VendorExtension(), this.getVendorExtension(), null, "vendorExtension", null, 0, -1, Scenario.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScenario_Author(), theXMLTypePackage.getString(), "author", null, 0, 1, Scenario.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScenario_Created(), theXMLTypePackage.getDateTime(), "created", null, 0, 1, Scenario.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScenario_Description(), theXMLTypePackage.getString(), "description", null, 0, 1, Scenario.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScenario_Id(), theXMLTypePackage.getID(), "id", null, 1, 1, Scenario.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScenario_Inherits(), theXMLTypePackage.getIDREF(), "inherits", null, 0, 1, Scenario.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScenario_Modified(), theXMLTypePackage.getDateTime(), "modified", null, 0, 1, Scenario.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScenario_Name(), theXMLTypePackage.getString(), "name", null, 0, 1, Scenario.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScenario_Result(), theXMLTypePackage.getIDREF(), "result", null, 0, 1, Scenario.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScenario_Vendor(), theXMLTypePackage.getString(), "vendor", null, 0, 1, Scenario.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScenario_Version(), theXMLTypePackage.getString(), "version", null, 0, 1, Scenario.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(resourceParametersEClass, ResourceParameters.class, "ResourceParameters", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getResourceParameters_Selection(), this.getParameter(), null, "selection", null, 0, 1, ResourceParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getResourceParameters_Availability(), this.getParameter(), null, "availability", null, 0, 1, ResourceParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getResourceParameters_Quantity(), this.getParameter(), null, "quantity", null, 0, 1, ResourceParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getResourceParameters_Workinghours(), this.getParameter(), null, "workinghours", null, 0, 1, ResourceParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getResourceParameters_Role(), this.getParameter(), null, "role", null, 0, -1, ResourceParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(scenarioParametersEClass, ScenarioParameters.class, "ScenarioParameters", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEReference(getScenarioParameters_Start(), this.getParameter(), null, "start", null, 0, 1, ScenarioParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getScenarioParameters_Duration(), this.getParameter(), null, "duration", null, 0, 1, ScenarioParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getScenarioParameters_PropertyParameters(), this.getPropertyParameters(), null, "propertyParameters", null, 0, 1, ScenarioParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScenarioParameters_BaseCurrencyUnit(), theXMLTypePackage.getString(), "baseCurrencyUnit", null, 0, 1, ScenarioParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScenarioParameters_BaseTimeUnit(), this.getTimeUnit(), "baseTimeUnit", null, 0, 1, ScenarioParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScenarioParameters_Replication(), theXMLTypePackage.getInt(), "replication", null, 0, 1, ScenarioParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getScenarioParameters_Seed(), theXMLTypePackage.getLong(), "seed", null, 0, 1, ScenarioParameters.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(vendorExtensionEClass, VendorExtension.class, "VendorExtension", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getVendorExtension_Any(), ecorePackage.getEFeatureMapEntry(), "any", null, 0, 1, VendorExtension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVendorExtension_Name(), theXMLTypePackage.getString(), "name", null, 1, 1, VendorExtension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getVendorExtension_AnyAttribute(), ecorePackage.getEFeatureMapEntry(), "anyAttribute", null, 0, -1, VendorExtension.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(triangularDistributionTypeEClass, TriangularDistributionType.class, "TriangularDistributionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTriangularDistributionType_Max(), theXMLTypePackage.getDouble(), "max", null, 0, 1, TriangularDistributionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTriangularDistributionType_Min(), theXMLTypePackage.getDouble(), "min", null, 0, 1, TriangularDistributionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTriangularDistributionType_MostLikely(), theXMLTypePackage.getDouble(), "mostLikely", null, 0, 1, TriangularDistributionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(truncatedNormalDistributionTypeEClass, TruncatedNormalDistributionType.class, "TruncatedNormalDistributionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getTruncatedNormalDistributionType_Max(), theXMLTypePackage.getDouble(), "max", null, 0, 1, TruncatedNormalDistributionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTruncatedNormalDistributionType_Mean(), theXMLTypePackage.getDouble(), "mean", null, 0, 1, TruncatedNormalDistributionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTruncatedNormalDistributionType_Min(), theXMLTypePackage.getDouble(), "min", null, 0, 1, TruncatedNormalDistributionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getTruncatedNormalDistributionType_StandardDeviation(), theXMLTypePackage.getDouble(), "standardDeviation", null, 0, 1, TruncatedNormalDistributionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(uniformDistributionTypeEClass, UniformDistributionType.class, "UniformDistributionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUniformDistributionType_Max(), theXMLTypePackage.getDouble(), "max", null, 0, 1, UniformDistributionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getUniformDistributionType_Min(), theXMLTypePackage.getDouble(), "min", null, 0, 1, UniformDistributionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(userDistributionDataPointTypeEClass, UserDistributionDataPointType.class, "UserDistributionDataPointType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUserDistributionDataPointType_ParameterValueGroup(), ecorePackage.getEFeatureMapEntry(), "parameterValueGroup", null, 1, 1, UserDistributionDataPointType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUserDistributionDataPointType_ParameterValue(), this.getParameterValue(), null, "parameterValue", null, 1, 1, UserDistributionDataPointType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);
		initEAttribute(getUserDistributionDataPointType_Probability(), theXMLTypePackage.getFloat(), "probability", null, 0, 1, UserDistributionDataPointType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		initEClass(userDistributionTypeEClass, UserDistributionType.class, "UserDistributionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getUserDistributionType_Group(), ecorePackage.getEFeatureMapEntry(), "group", null, 0, -1, UserDistributionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, !IS_UNSETTABLE, !IS_ID, !IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEReference(getUserDistributionType_UserDistributionDataPoint(), this.getUserDistributionDataPointType(), null, "userDistributionDataPoint", null, 1, -1, UserDistributionType.class, IS_TRANSIENT, IS_VOLATILE, IS_CHANGEABLE, IS_COMPOSITE, !IS_RESOLVE_PROXIES, !IS_UNSETTABLE, IS_UNIQUE, IS_DERIVED, IS_ORDERED);

		initEClass(weibullDistributionTypeEClass, WeibullDistributionType.class, "WeibullDistributionType", !IS_ABSTRACT, !IS_INTERFACE, IS_GENERATED_INSTANCE_CLASS);
		initEAttribute(getWeibullDistributionType_Scale(), theXMLTypePackage.getDouble(), "scale", null, 0, 1, WeibullDistributionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);
		initEAttribute(getWeibullDistributionType_Shape(), theXMLTypePackage.getDouble(), "shape", null, 0, 1, WeibullDistributionType.class, !IS_TRANSIENT, !IS_VOLATILE, IS_CHANGEABLE, IS_UNSETTABLE, !IS_ID, IS_UNIQUE, !IS_DERIVED, IS_ORDERED);

		// Initialize enums and add enum literals
		initEEnum(resultTypeEEnum, ResultType.class, "ResultType");
		addEEnumLiteral(resultTypeEEnum, ResultType.ENUM);
		addEEnumLiteral(resultTypeEEnum, ResultType.MIN);
		addEEnumLiteral(resultTypeEEnum, ResultType.MAX);
		addEEnumLiteral(resultTypeEEnum, ResultType.MEAN);
		addEEnumLiteral(resultTypeEEnum, ResultType.COUNT);
		addEEnumLiteral(resultTypeEEnum, ResultType.SUM);

		initEEnum(timeUnitEEnum, TimeUnit.class, "TimeUnit");
		addEEnumLiteral(timeUnitEEnum, TimeUnit.MS);
		addEEnumLiteral(timeUnitEEnum, TimeUnit.S);
		addEEnumLiteral(timeUnitEEnum, TimeUnit.MIN);
		addEEnumLiteral(timeUnitEEnum, TimeUnit.HOUR);
		addEEnumLiteral(timeUnitEEnum, TimeUnit.DAY);
		addEEnumLiteral(timeUnitEEnum, TimeUnit.YEAR);

		// Initialize data types
		initEDataType(packageNameTypeEDataType, String.class, "PackageNameType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(priorityTypeEDataType, BigInteger.class, "PriorityType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(ruleFlowGroupTypeEDataType, String.class, "RuleFlowGroupType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(taskNameTypeEDataType, String.class, "TaskNameType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(versionTypeEDataType, String.class, "VersionType", IS_SERIALIZABLE, !IS_GENERATED_INSTANCE_CLASS);
		initEDataType(resultTypeObjectEDataType, ResultType.class, "ResultTypeObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);
		initEDataType(timeUnitObjectEDataType, TimeUnit.class, "TimeUnitObject", IS_SERIALIZABLE, IS_GENERATED_INSTANCE_CLASS);

		// Create resource
		createResource(eNS_URI);

		// Create annotations
		// http:///org/eclipse/emf/ecore/util/ExtendedMetaData
		createExtendedMetaDataAnnotations();
	}

	/**
	 * Initializes the annotations for <b>http:///org/eclipse/emf/ecore/util/ExtendedMetaData</b>.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void createExtendedMetaDataAnnotations() {
		String source = "http:///org/eclipse/emf/ecore/util/ExtendedMetaData";		
		addAnnotation
		  (betaDistributionTypeEClass, 
		   source, 
		   new String[] {
			 "name", "BetaDistribution_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getBetaDistributionType_Scale(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "scale"
		   });		
		addAnnotation
		  (getBetaDistributionType_Shape(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "shape"
		   });		
		addAnnotation
		  (binomialDistributionTypeEClass, 
		   source, 
		   new String[] {
			 "name", "BinomialDistribution_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getBinomialDistributionType_Probability(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "probability"
		   });		
		addAnnotation
		  (getBinomialDistributionType_Trials(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "trials"
		   });		
		addAnnotation
		  (booleanParameterTypeEClass, 
		   source, 
		   new String[] {
			 "name", "BooleanParameter_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getBooleanParameterType_Value(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "value"
		   });		
		addAnnotation
		  (calendarEClass, 
		   source, 
		   new String[] {
			 "name", "Calendar",
			 "kind", "simple"
		   });		
		addAnnotation
		  (getCalendar_Value(), 
		   source, 
		   new String[] {
			 "name", ":0",
			 "kind", "simple"
		   });		
		addAnnotation
		  (getCalendar_Id(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "id"
		   });		
		addAnnotation
		  (getCalendar_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name"
		   });		
		addAnnotation
		  (documentRootEClass, 
		   source, 
		   new String[] {
			 "name", "",
			 "kind", "mixed"
		   });		
		addAnnotation
		  (getDocumentRoot_BetaDistribution(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "BetaDistribution",
			 "namespace", "##targetNamespace",
			 "affiliation", "ParameterValue"
		   });		
		addAnnotation
		  (getDocumentRoot_ParameterValue(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ParameterValue",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_BinomialDistribution(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "BinomialDistribution",
			 "namespace", "##targetNamespace",
			 "affiliation", "ParameterValue"
		   });		
		addAnnotation
		  (getDocumentRoot_BooleanParameter(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "BooleanParameter",
			 "namespace", "##targetNamespace",
			 "affiliation", "ParameterValue"
		   });		
		addAnnotation
		  (getDocumentRoot_DateTimeParameter(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "DateTimeParameter",
			 "namespace", "##targetNamespace",
			 "affiliation", "ParameterValue"
		   });		
		addAnnotation
		  (getDocumentRoot_DecimalParameter(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "DecimalParameter",
			 "namespace", "##targetNamespace",
			 "affiliation", "ParameterValue"
		   });		
		addAnnotation
		  (getDocumentRoot_DurationParameter(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "DurationParameter",
			 "namespace", "##targetNamespace",
			 "affiliation", "ParameterValue"
		   });		
		addAnnotation
		  (getDocumentRoot_EnumParameter(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "EnumParameter",
			 "namespace", "##targetNamespace",
			 "affiliation", "ParameterValue"
		   });		
		addAnnotation
		  (getDocumentRoot_ErlangDistribution(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ErlangDistribution",
			 "namespace", "##targetNamespace",
			 "affiliation", "ParameterValue"
		   });		
		addAnnotation
		  (getDocumentRoot_ExpressionParameter(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ExpressionParameter",
			 "namespace", "##targetNamespace",
			 "affiliation", "ParameterValue"
		   });		
		addAnnotation
		  (getDocumentRoot_FloatingParameter(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "FloatingParameter",
			 "namespace", "##targetNamespace",
			 "affiliation", "ParameterValue"
		   });		
		addAnnotation
		  (getDocumentRoot_GammaDistribution(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "GammaDistribution",
			 "namespace", "##targetNamespace",
			 "affiliation", "ParameterValue"
		   });		
		addAnnotation
		  (getDocumentRoot_Global(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "global",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_LogNormalDistribution(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "LogNormalDistribution",
			 "namespace", "##targetNamespace",
			 "affiliation", "ParameterValue"
		   });		
		addAnnotation
		  (getDocumentRoot_Metadata(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "metadata",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Metaentry(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "metaentry",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_NegativeExponentialDistribution(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "NegativeExponentialDistribution",
			 "namespace", "##targetNamespace",
			 "affiliation", "ParameterValue"
		   });		
		addAnnotation
		  (getDocumentRoot_NormalDistribution(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "NormalDistribution",
			 "namespace", "##targetNamespace",
			 "affiliation", "ParameterValue"
		   });		
		addAnnotation
		  (getDocumentRoot_NumericParameter(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "NumericParameter",
			 "namespace", "##targetNamespace",
			 "affiliation", "ParameterValue"
		   });		
		addAnnotation
		  (getDocumentRoot_OnEntryScript(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "onEntry-script",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_OnExitScript(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "onExit-script",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_PoissonDistribution(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "PoissonDistribution",
			 "namespace", "##targetNamespace",
			 "affiliation", "ParameterValue"
		   });		
		addAnnotation
		  (getDocumentRoot_ProcessAnalysisData(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ProcessAnalysisData",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_RandomDistribution(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "RandomDistribution",
			 "namespace", "##targetNamespace",
			 "affiliation", "ParameterValue"
		   });		
		addAnnotation
		  (getDocumentRoot_StringParameter(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "StringParameter",
			 "namespace", "##targetNamespace",
			 "affiliation", "ParameterValue"
		   });		
		addAnnotation
		  (getDocumentRoot_TriangularDistribution(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "TriangularDistribution",
			 "namespace", "##targetNamespace",
			 "affiliation", "ParameterValue"
		   });		
		addAnnotation
		  (getDocumentRoot_TruncatedNormalDistribution(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "TruncatedNormalDistribution",
			 "namespace", "##targetNamespace",
			 "affiliation", "ParameterValue"
		   });		
		addAnnotation
		  (getDocumentRoot_UniformDistribution(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "UniformDistribution",
			 "namespace", "##targetNamespace",
			 "affiliation", "ParameterValue"
		   });		
		addAnnotation
		  (getDocumentRoot_UserDistribution(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "UserDistribution",
			 "namespace", "##targetNamespace",
			 "affiliation", "ParameterValue"
		   });		
		addAnnotation
		  (getDocumentRoot_UserDistributionDataPoint(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "UserDistributionDataPoint",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_WeibullDistribution(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "WeibullDistribution",
			 "namespace", "##targetNamespace",
			 "affiliation", "ParameterValue"
		   });		
		addAnnotation
		  (getDocumentRoot_PackageName(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "packageName",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Priority(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "priority",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_RuleFlowGroup(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "ruleFlowGroup",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_TaskName(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "taskName",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getDocumentRoot_Version(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "version",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (globalTypeEClass, 
		   source, 
		   new String[] {
			 "name", "global_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getGlobalType_Identifier(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "identifier"
		   });		
		addAnnotation
		  (getGlobalType_Type(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "type"
		   });		
		addAnnotation
		  (controlParametersEClass, 
		   source, 
		   new String[] {
			 "name", "ControlParameters",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getControlParameters_Probability(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Probability",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getControlParameters_InterTriggerTimer(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "InterTriggerTimer",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getControlParameters_MaxTriggerCount(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "MaxTriggerCount",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (constantParameterEClass, 
		   source, 
		   new String[] {
			 "name", "ConstantParameter",
			 "kind", "empty"
		   });		
		addAnnotation
		  (costParametersEClass, 
		   source, 
		   new String[] {
			 "name", "CostParameters",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getCostParameters_FixedCost(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "FixedCost",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getCostParameters_UnitCost(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "UnitCost",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getCostParameters_CurrencyUnit(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "CurrencyUnit",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (importTypeEClass, 
		   source, 
		   new String[] {
			 "name", "import_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getImportType_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name"
		   });		
		addAnnotation
		  (dateTimeParameterTypeEClass, 
		   source, 
		   new String[] {
			 "name", "DateTimeParameter_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getDateTimeParameterType_Value(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "value"
		   });		
		addAnnotation
		  (decimalParameterTypeEClass, 
		   source, 
		   new String[] {
			 "name", "DecimalParameter_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getDecimalParameterType_Value(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "value"
		   });		
		addAnnotation
		  (onEntryScriptTypeEClass, 
		   source, 
		   new String[] {
			 "name", "onEntry-script_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getOnEntryScriptType_Script(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "script",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getOnEntryScriptType_ScriptFormat(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "scriptFormat"
		   });		
		addAnnotation
		  (elementParametersEClass, 
		   source, 
		   new String[] {
			 "name", "ElementParameters",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getElementParameters_TimeParameters(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "TimeParameters",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getElementParameters_ControlParameters(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ControlParameters",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getElementParameters_ResourceParameters(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ResourceParameters",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getElementParameters_PriorityParameters(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "PriorityParameters",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getElementParameters_CostParameters(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "CostParameters",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getElementParameters_PropertyParameters(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "PropertyParameters",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getElementParameters_VendorExtension(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "VendorExtension",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getElementParameters_ElementId(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "elementId"
		   });		
		addAnnotation
		  (getElementParameters_Id(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "id"
		   });		
		addAnnotation
		  (onExitScriptTypeEClass, 
		   source, 
		   new String[] {
			 "name", "onExit-script_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getOnExitScriptType_Script(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "script",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getOnExitScriptType_ScriptFormat(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "scriptFormat"
		   });		
		addAnnotation
		  (durationParameterTypeEClass, 
		   source, 
		   new String[] {
			 "name", "DurationParameter_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getDurationParameterType_Value(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "value"
		   });		
		addAnnotation
		  (packageNameTypeEDataType, 
		   source, 
		   new String[] {
			 "name", "packageName_._type",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#string"
		   });		
		addAnnotation
		  (enumParameterTypeEClass, 
		   source, 
		   new String[] {
			 "name", "EnumParameter_._type",
			 "kind", "elementOnly"
		   });			
		addAnnotation
		  (getEnumParameterType_Group(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "group:3"
		   });		
		addAnnotation
		  (getEnumParameterType_ParameterValueGroup(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "ParameterValue:group",
			 "namespace", "##targetNamespace",
			 "group", "#group:3"
		   });		
		addAnnotation
		  (getEnumParameterType_ParameterValue(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ParameterValue",
			 "namespace", "##targetNamespace",
			 "group", "ParameterValue:group"
		   });		
		addAnnotation
		  (erlangDistributionTypeEClass, 
		   source, 
		   new String[] {
			 "name", "ErlangDistribution_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getErlangDistributionType_K(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "k"
		   });		
		addAnnotation
		  (getErlangDistributionType_Mean(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "mean"
		   });		
		addAnnotation
		  (expressionParameterTypeEClass, 
		   source, 
		   new String[] {
			 "name", "ExpressionParameter_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getExpressionParameterType_Value(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "value"
		   });		
		addAnnotation
		  (floatingParameterTypeEClass, 
		   source, 
		   new String[] {
			 "name", "FloatingParameter_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getFloatingParameterType_Value(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "value"
		   });		
		addAnnotation
		  (gammaDistributionTypeEClass, 
		   source, 
		   new String[] {
			 "name", "GammaDistribution_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getGammaDistributionType_Scale(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "scale"
		   });		
		addAnnotation
		  (getGammaDistributionType_Shape(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "shape"
		   });		
		addAnnotation
		  (priorityTypeEDataType, 
		   source, 
		   new String[] {
			 "name", "priority_._type",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#integer",
			 "minInclusive", "1"
		   });		
		addAnnotation
		  (ruleFlowGroupTypeEDataType, 
		   source, 
		   new String[] {
			 "name", "ruleFlowGroup_._type",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#string"
		   });		
		addAnnotation
		  (logNormalDistributionTypeEClass, 
		   source, 
		   new String[] {
			 "name", "LogNormalDistribution_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getLogNormalDistributionType_Mean(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "mean"
		   });		
		addAnnotation
		  (getLogNormalDistributionType_StandardDeviation(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "standardDeviation"
		   });		
		addAnnotation
		  (metadataTypeEClass, 
		   source, 
		   new String[] {
			 "name", "metadata_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getMetadataType_Metaentry(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "metaentry",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (taskNameTypeEDataType, 
		   source, 
		   new String[] {
			 "name", "taskName_._type",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#string"
		   });		
		addAnnotation
		  (negativeExponentialDistributionTypeEClass, 
		   source, 
		   new String[] {
			 "name", "NegativeExponentialDistribution_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getNegativeExponentialDistributionType_Mean(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "mean"
		   });		
		addAnnotation
		  (normalDistributionTypeEClass, 
		   source, 
		   new String[] {
			 "name", "NormalDistribution_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getNormalDistributionType_Mean(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "mean"
		   });		
		addAnnotation
		  (getNormalDistributionType_StandardDeviation(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "standardDeviation"
		   });		
		addAnnotation
		  (parameterEClass, 
		   source, 
		   new String[] {
			 "name", "Parameter",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getParameter_ResultRequest(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ResultRequest",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getParameter_ParameterValueGroup(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "ParameterValue:group",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getParameter_ParameterValue(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ParameterValue",
			 "namespace", "##targetNamespace",
			 "group", "ParameterValue:group"
		   });		
		addAnnotation
		  (getParameter_Kpi(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "kpi"
		   });		
		addAnnotation
		  (getParameter_Sla(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "sla"
		   });		
		addAnnotation
		  (numericParameterTypeEClass, 
		   source, 
		   new String[] {
			 "name", "NumericParameter_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getNumericParameterType_Value(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "value"
		   });		
		addAnnotation
		  (metaentryTypeEClass, 
		   source, 
		   new String[] {
			 "name", "metaentry_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getMetaentryType_Name(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "name",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getMetaentryType_Value(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "value",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (distributionParameterEClass, 
		   source, 
		   new String[] {
			 "name", "DistributionParameter",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getDistributionParameter_Discrete(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "discrete"
		   });		
		addAnnotation
		  (versionTypeEDataType, 
		   source, 
		   new String[] {
			 "name", "version_._type",
			 "baseType", "http://www.eclipse.org/emf/2003/XMLType#string"
		   });		
		addAnnotation
		  (processAnalysisDataTypeEClass, 
		   source, 
		   new String[] {
			 "name", "ProcessAnalysisData_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getProcessAnalysisDataType_Group(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "group:0"
		   });		
		addAnnotation
		  (getProcessAnalysisDataType_Scenario(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Scenario",
			 "namespace", "##targetNamespace",
			 "group", "#group:0"
		   });		
		addAnnotation
		  (resultTypeEEnum, 
		   source, 
		   new String[] {
			 "name", "ResultType"
		   });		
		addAnnotation
		  (propertyParametersEClass, 
		   source, 
		   new String[] {
			 "name", "PropertyParameters",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getPropertyParameters_Property(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Property",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (randomDistributionTypeEClass, 
		   source, 
		   new String[] {
			 "name", "RandomDistribution_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getRandomDistributionType_Max(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "max"
		   });		
		addAnnotation
		  (getRandomDistributionType_Min(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "min"
		   });		
		addAnnotation
		  (parameterValueEClass, 
		   source, 
		   new String[] {
			 "name", "ParameterValue",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getParameterValue_Instance(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "instance"
		   });		
		addAnnotation
		  (getParameterValue_Result(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "result"
		   });		
		addAnnotation
		  (getParameterValue_ValidFor(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "validFor"
		   });		
		addAnnotation
		  (poissonDistributionTypeEClass, 
		   source, 
		   new String[] {
			 "name", "PoissonDistribution_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getPoissonDistributionType_Mean(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "mean"
		   });		
		addAnnotation
		  (resultTypeObjectEDataType, 
		   source, 
		   new String[] {
			 "name", "ResultType:Object",
			 "baseType", "ResultType"
		   });		
		addAnnotation
		  (propertyTypeEClass, 
		   source, 
		   new String[] {
			 "name", "Property_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getPropertyType_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name"
		   });		
		addAnnotation
		  (priorityParametersEClass, 
		   source, 
		   new String[] {
			 "name", "PriorityParameters",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getPriorityParameters_Interruptible(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Interruptible",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getPriorityParameters_Priority(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Priority",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (timeUnitEEnum, 
		   source, 
		   new String[] {
			 "name", "TimeUnit"
		   });		
		addAnnotation
		  (timeParametersEClass, 
		   source, 
		   new String[] {
			 "name", "TimeParameters",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getTimeParameters_TransferTime(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "TransferTime",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTimeParameters_QueueTime(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "QueueTime",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTimeParameters_WaitTime(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "WaitTime",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTimeParameters_SetUpTime(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "SetUpTime",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTimeParameters_ProcessingTime(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ProcessingTime",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTimeParameters_ValidationTime(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ValidationTime",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTimeParameters_ReworkTime(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ReworkTime",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getTimeParameters_TimeUnit(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "TimeUnit",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (stringParameterTypeEClass, 
		   source, 
		   new String[] {
			 "name", "StringParameter_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getStringParameterType_Value(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "value"
		   });		
		addAnnotation
		  (scenarioEClass, 
		   source, 
		   new String[] {
			 "name", "Scenario",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getScenario_ScenarioParameters(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ScenarioParameters",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getScenario_ElementParameters(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ElementParameters",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getScenario_Calendar(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Calendar",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getScenario_VendorExtension(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "VendorExtension",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getScenario_Author(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "author"
		   });		
		addAnnotation
		  (getScenario_Created(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "created"
		   });		
		addAnnotation
		  (getScenario_Description(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "description"
		   });		
		addAnnotation
		  (getScenario_Id(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "id"
		   });		
		addAnnotation
		  (getScenario_Inherits(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "inherits"
		   });		
		addAnnotation
		  (getScenario_Modified(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "modified"
		   });		
		addAnnotation
		  (getScenario_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name"
		   });		
		addAnnotation
		  (getScenario_Result(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "result"
		   });		
		addAnnotation
		  (getScenario_Vendor(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "vendor"
		   });		
		addAnnotation
		  (getScenario_Version(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "version"
		   });		
		addAnnotation
		  (resourceParametersEClass, 
		   source, 
		   new String[] {
			 "name", "ResourceParameters",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getResourceParameters_Selection(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Selection",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getResourceParameters_Availability(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Availability",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getResourceParameters_Quantity(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Quantity",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getResourceParameters_Workinghours(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Workinghours",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getResourceParameters_Role(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Role",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (timeUnitObjectEDataType, 
		   source, 
		   new String[] {
			 "name", "TimeUnit:Object",
			 "baseType", "TimeUnit"
		   });		
		addAnnotation
		  (scenarioParametersEClass, 
		   source, 
		   new String[] {
			 "name", "ScenarioParameters",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getScenarioParameters_Start(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Start",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getScenarioParameters_Duration(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "Duration",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getScenarioParameters_PropertyParameters(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "PropertyParameters",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getScenarioParameters_BaseCurrencyUnit(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "baseCurrencyUnit"
		   });		
		addAnnotation
		  (getScenarioParameters_BaseTimeUnit(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "baseTimeUnit"
		   });		
		addAnnotation
		  (getScenarioParameters_Replication(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "replication"
		   });		
		addAnnotation
		  (getScenarioParameters_Seed(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "seed"
		   });		
		addAnnotation
		  (vendorExtensionEClass, 
		   source, 
		   new String[] {
			 "name", "VendorExtension",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getVendorExtension_Any(), 
		   source, 
		   new String[] {
			 "kind", "elementWildcard",
			 "wildcards", "##other",
			 "name", ":0",
			 "processing", "strict"
		   });		
		addAnnotation
		  (getVendorExtension_Name(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "name"
		   });		
		addAnnotation
		  (getVendorExtension_AnyAttribute(), 
		   source, 
		   new String[] {
			 "kind", "attributeWildcard",
			 "wildcards", "##other",
			 "name", ":2",
			 "processing", "strict"
		   });		
		addAnnotation
		  (triangularDistributionTypeEClass, 
		   source, 
		   new String[] {
			 "name", "TriangularDistribution_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getTriangularDistributionType_Max(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "max"
		   });		
		addAnnotation
		  (getTriangularDistributionType_Min(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "min"
		   });		
		addAnnotation
		  (getTriangularDistributionType_MostLikely(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "mostLikely"
		   });		
		addAnnotation
		  (truncatedNormalDistributionTypeEClass, 
		   source, 
		   new String[] {
			 "name", "TruncatedNormalDistribution_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getTruncatedNormalDistributionType_Max(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "max"
		   });		
		addAnnotation
		  (getTruncatedNormalDistributionType_Mean(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "mean"
		   });		
		addAnnotation
		  (getTruncatedNormalDistributionType_Min(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "min"
		   });		
		addAnnotation
		  (getTruncatedNormalDistributionType_StandardDeviation(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "standardDeviation"
		   });		
		addAnnotation
		  (uniformDistributionTypeEClass, 
		   source, 
		   new String[] {
			 "name", "UniformDistribution_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getUniformDistributionType_Max(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "max"
		   });		
		addAnnotation
		  (getUniformDistributionType_Min(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "min"
		   });		
		addAnnotation
		  (userDistributionDataPointTypeEClass, 
		   source, 
		   new String[] {
			 "name", "UserDistributionDataPoint_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getUserDistributionDataPointType_ParameterValueGroup(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "ParameterValue:group",
			 "namespace", "##targetNamespace"
		   });		
		addAnnotation
		  (getUserDistributionDataPointType_ParameterValue(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "ParameterValue",
			 "namespace", "##targetNamespace",
			 "group", "ParameterValue:group"
		   });		
		addAnnotation
		  (getUserDistributionDataPointType_Probability(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "probability"
		   });		
		addAnnotation
		  (userDistributionTypeEClass, 
		   source, 
		   new String[] {
			 "name", "UserDistribution_._type",
			 "kind", "elementOnly"
		   });		
		addAnnotation
		  (getUserDistributionType_Group(), 
		   source, 
		   new String[] {
			 "kind", "group",
			 "name", "group:4"
		   });		
		addAnnotation
		  (getUserDistributionType_UserDistributionDataPoint(), 
		   source, 
		   new String[] {
			 "kind", "element",
			 "name", "UserDistributionDataPoint",
			 "namespace", "##targetNamespace",
			 "group", "#group:4"
		   });		
		addAnnotation
		  (weibullDistributionTypeEClass, 
		   source, 
		   new String[] {
			 "name", "WeibullDistribution_._type",
			 "kind", "empty"
		   });		
		addAnnotation
		  (getWeibullDistributionType_Scale(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "scale"
		   });		
		addAnnotation
		  (getWeibullDistributionType_Shape(), 
		   source, 
		   new String[] {
			 "kind", "attribute",
			 "name", "shape"
		   });
	}

} //ModelPackageImpl

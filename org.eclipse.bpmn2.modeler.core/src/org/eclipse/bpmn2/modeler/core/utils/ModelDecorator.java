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

package org.eclipse.bpmn2.modeler.core.utils;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.Collaboration;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.ExtensionAttributeValue;
import org.eclipse.bpmn2.Participant;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.di.BPMNDiagram;
import org.eclipse.bpmn2.di.BpmnDiPackage;
import org.eclipse.bpmn2.modeler.core.EDataTypeConversionFactory;
import org.eclipse.bpmn2.modeler.core.adapters.AdapterRegistry;
import org.eclipse.bpmn2.modeler.core.adapters.AdapterUtil;
import org.eclipse.bpmn2.modeler.core.adapters.ExtendedPropertiesAdapter;
import org.eclipse.bpmn2.modeler.core.adapters.FeatureDescriptor;
import org.eclipse.bpmn2.modeler.core.adapters.InsertionAdapter;
import org.eclipse.bpmn2.modeler.core.model.Bpmn2ModelerFactory;
import org.eclipse.core.runtime.Assert;
import org.eclipse.dd.dc.DcPackage;
import org.eclipse.dd.di.DiPackage;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.common.notify.Notifier;
import org.eclipse.emf.common.notify.impl.AdapterFactoryImpl;
import org.eclipse.emf.common.notify.impl.AdapterImpl;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EDataType;
import org.eclipse.emf.ecore.EDataType.Internal.ConversionDelegate;
import org.eclipse.emf.ecore.EEnum;
import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcoreFactory;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.impl.DynamicEObjectImpl;
import org.eclipse.emf.ecore.impl.EAttributeImpl;
import org.eclipse.emf.ecore.impl.EFactoryImpl;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.BasicFeatureMap;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.ExtendedMetaData;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMap.Entry;
import org.eclipse.emf.ecore.util.FeatureMapUtil;
import org.eclipse.emf.ecore.xmi.impl.XMLResourceFactoryImpl;
import org.eclipse.emf.ecore.xml.type.AnyType;
import org.eclipse.emf.ecore.xml.type.XMLTypeFactory;
import org.eclipse.emf.ecore.xml.type.XMLTypePackage;
import org.eclipse.emf.transaction.RecordingCommand;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.osgi.util.NLS;

/**
 * This class wraps an EPackage and provides methods for dynamic EMF.
 */
public class ModelDecorator {
	final static EcoreFactory theCoreFactory = EcoreFactory.eINSTANCE;
	protected EPackage ePackage;
	protected static ResourceSet resourceSet;
	protected List<EPackage> relatedEPackages;

	static class MyObjectFactory extends EFactoryImpl {
		public ModelDecorator modelDecorator;
		
		public MyObjectFactory(ModelDecorator modelDecorator) {
			this.modelDecorator = modelDecorator;
		}
		
		@Override
		public EObject create(EClass eClass) {
			EObject object;
			if (eClass == EcorePackage.eINSTANCE.getEObject())
				object = XMLTypeFactory.eINSTANCE.createAnyType();
			else
				object = super.create(eClass);
			ExtendedPropertiesAdapter adapter = ExtendedPropertiesAdapter.adapt(object);
			return object;
		}
		
		protected EObject basicCreate(EClass eClass) {
			return eClass.getInstanceClassName() == "java.util.Map$Entry" ?
					new DynamicEObjectImpl.BasicEMapEntry<String, String>(eClass) :
					XMLTypeFactory.eINSTANCE.createAnyType();
		}
	}
	
	static class MyAdaptorFactory extends AdapterFactoryImpl {

		public static MyAdaptorFactory INSTANCE = new MyAdaptorFactory();
		
		@Override
		public boolean isFactoryForType(Object type) {
			return type == AnyType.class;
		}

		@Override
		public Adapter adaptNew(Notifier object, Object type) {
			Adapter adapter = null;
			if (type == ExtendedPropertiesAdapter.class) {
				if (object instanceof EClass) {
					object = ExtendedPropertiesAdapter.getDummyObject((EClass)object);
				}
				adapter = new AnyTypeExtendedPropertiesAdapter(this, (AnyType)object);
			}
			return adapter;
		}
	}
	
	static class AnyTypeExtendedPropertiesAdapter extends ExtendedPropertiesAdapter<AnyType> {

		ModelDecorator modelDecorator;

		public AnyTypeExtendedPropertiesAdapter(AdapterFactory adapterFactory, AnyType object) {
			super(adapterFactory, object);
		}

		public ModelDecorator getModelDecorator(EStructuralFeature feature) {

			if (modelDecorator==null) {
				String nsURI = ExtendedMetaData.INSTANCE.getNamespace(feature);
				EPackage pkg = ModelDecorator.getEPackage(nsURI);
				if (pkg!=null) {
					ModelDecoratorAdapter mda = AdapterUtil.adapt(pkg, ModelDecoratorAdapter.class);
					modelDecorator = mda.getModelDecorator();
				}
			}
			return modelDecorator;
		}
		
		@Override
		public EStructuralFeature getFeature(String name) {
			AnyType object = (AnyType) getTarget();
			EStructuralFeature feature = object.eClass().getEStructuralFeature(name);
			adaptFeature(feature);
			return feature;
		}

		@Override
		public FeatureDescriptor<AnyType> getFeatureDescriptor(EStructuralFeature feature) {
			adaptFeature(feature);
			return super.getFeatureDescriptor(feature);
		}
		
		private void adaptFeature(EStructuralFeature feature) {
			if (modelDecorator==null) {
				AnyType object = (AnyType) getTarget();
				if (getModelDecorator(feature) != null) {
					modelDecorator.adaptFeature(this, object, feature);
				}
			}
		}
	}
	
	public class ModelDecoratorAdapter extends AdapterImpl {
		ModelDecorator modelDecorator;
		
		public ModelDecoratorAdapter(ModelDecorator modelDecorator) {
			this.modelDecorator = modelDecorator;
		}
		
		public ModelDecorator getModelDecorator() {
			return modelDecorator;
		}

		public EClass getEClass(EObject object) {
			if (object instanceof EClass)
				return modelDecorator.getEClass(((EClass)object).getName());
			// FIXME: The ExtensionAttributeValues container is used to attach EXTENSION ELEMENTS (not attributes)
			// to a BaseElement object (i.e. EXTENSION ELEMENTS are contained by an ExtensionAttributeValue
			// object owned by the BaseElement, not the BaseElement itself). We need to resolve this ownership
			// issue in a central place.
			if (object instanceof ExtensionAttributeValue && object.eContainer()!=null) {
				object = object.eContainer();
			}
			return modelDecorator.getEClass(object.eClass().getName());
		}
		
		public EStructuralFeature getEStructuralFeature(EObject object, EStructuralFeature feature) {
			EClass eClass = getEClass(object);
			if (eClass!=null) {
				feature = eClass.getEStructuralFeature(feature.getName());
				if (feature!=null) {
					adaptFeature(null, object, feature);
					return feature;
				}
			}
			return null;
		}
		
		public EStructuralFeature getEStructuralFeature(EObject object, String name) {
			EClass eClass = getEClass(object);
			if (eClass!=null) {
				EStructuralFeature feature = eClass.getEStructuralFeature(name);
				if (feature!=null) {
					adaptFeature(null, object, feature);
					return feature;
				}
			}
			return null;
		}
	}
	
	static {
		EClass e = XMLTypePackage.eINSTANCE.getAnyType();
		AdapterRegistry.INSTANCE.registerFactory(e.getInstanceClass(), MyAdaptorFactory.INSTANCE);
	}
	
	public ModelDecorator(EPackage pkg) {
		Assert.isTrue( isValid(pkg) );
		String name = pkg.getName()+" Extensions";
		String nsPrefix = pkg.getNsPrefix()+"_x";
		String nsURI = pkg.getNsURI()+"/extensions";
		getResourceSet();
		ePackage = (EPackage) resourceSet.getPackageRegistry().get(nsURI);
		if (ePackage==null) {
			ePackage = createEPackage(name,nsPrefix,nsURI);
			initPackage();
		}
		addRelatedEPackage(pkg);
	}
	
	public ModelDecorator(String name, String nsPrefix, String nsURI) {
		ePackage = (EPackage) getResourceSet().getPackageRegistry().get(nsURI);
		if (ePackage==null) {
			ePackage = createEPackage(name,nsPrefix,nsURI);
		}
		initPackage();
	}
	
	public void dispose() {
		if (resourceSet!=null) {
			resourceSet.getPackageRegistry().clear();
			if (ePackage!=null)
				EcoreUtil.delete(ePackage);
		}
	}
	
	private static ResourceSet getResourceSet() {
		if (resourceSet==null)
			resourceSet = new ResourceSetImpl();
		return resourceSet;
	}
	
	private void initPackage() {
		ePackage.setEFactoryInstance(new MyObjectFactory(this));
		ModelDecoratorAdapter adapter = new ModelDecoratorAdapter(this);
		ePackage.eAdapters().add(adapter);
		List<String> delegates = new ArrayList<String>();
		delegates.add(EDataTypeConversionFactory.DATATYPE_CONVERSION_FACTORY_URI);
		EcoreUtil.setConversionDelegates(ePackage, delegates);
	}
	
	public EPackage getEPackage() {
		Assert.isNotNull(ePackage);
		return ePackage;
	}
	
	public static EPackage getEPackage(String nsURI) {
		EPackage pkg = (EPackage) getResourceSet().getPackageRegistry().get(nsURI);
		return pkg;
	}
	
	public static EPackage getEPackageForFeature(EStructuralFeature feature) {
		String nsURI = ExtendedMetaData.INSTANCE.getNamespace(feature);
		return (EPackage) getResourceSet().getPackageRegistry().get(nsURI);
	}
	
	public void addRelatedEPackage(EPackage pkg) {
		if (pkg!=ePackage && !getRelatedEPackages().contains(pkg))
			getRelatedEPackages().add(pkg);
	}
	
	public List<EPackage> getRelatedEPackages() {
		if (relatedEPackages==null) {
			relatedEPackages = new ArrayList<EPackage>();
		}
		return relatedEPackages;
	}
	
	private EPackage createEPackage(String name, String nsPrefix, String nsURI) {
		ePackage = theCoreFactory.createEPackage();
		ePackage.setName(name);
		ePackage.setNsPrefix(nsPrefix);
		ePackage.setNsURI(nsURI);
	
		getResourceSet();
		resourceSet.getResourceFactoryRegistry().getExtensionToFactoryMap().put("*", new XMLResourceFactoryImpl());
		resourceSet.getPackageRegistry().put(nsURI, ePackage);

		return ePackage;
	}
	
	private List<String> getSuperTypes(String type) {
		List<String> supertypes = new ArrayList<String>();
		if (type.contains(":")) {
			String a[] = type.split(":");
			if (a.length>1) {
				a = a[1].split(",");
			}
			else {
				a = a[0].split(",");
			}
			for (int i=0; i<a.length; ++i) {
				supertypes.add(a[i]);
			}
		}
		return supertypes;
	}
	
	private String getType(String type) {
		String a[] = type.split(":");
		return a[0];
	}
	
	public EClassifier getEClassifier(String type) {
		EClassifier eClassifier = ePackage.getEClassifier(type);
		if (eClassifier != null) {
			return eClassifier;
		}
		for (EPackage p : getRelatedEPackages()) {
			eClassifier = p.getEClassifier(type);
			if (eClassifier != null) {
				return eClassifier;
			}
		}
		Assert.isTrue(eClassifier==null);
		return null;
	}
	
	public EClassifier createEClassifier(String type) {
		EClassifier eClassifier = getEClassifier(type);
		if (eClassifier!=null)
			return eClassifier;
		
		EClassifier eDataType = null;
		for (String st : getSuperTypes(type)) {
			EClassifier ec = findEClassifier(st);
			if (EDataType.class.isAssignableFrom( ec.getInstanceClass() )) {
				eDataType = ec;
				break;
			}
		}
		
		if (eDataType==null) {
			if (EDataTypeConversionFactory.isFactoryFor(type))
				return createEDataType(type);
			return createEClass(type);
		}
		
		if (EEnum.class.isAssignableFrom(eDataType.getInstanceClass()))
			eClassifier = theCoreFactory.createEEnum();
		else
			eClassifier = theCoreFactory.createEDataType();

		eClassifier.setName(getType(type));
		ePackage.getEClassifiers().add(eClassifier);

		// make this class look like a DocumentRoot so that it can be added
		// to the containing object's "anyType" feature.
		ExtendedMetaData.INSTANCE.setName(eClassifier, "");

		return eClassifier;
	}
	
	public EEnumLiteral createEEnumLiteral(String name, String owningtype) {

		EClassifier eClassifier = getEClassifier(owningtype);
		if (eClassifier==null) {
			eClassifier = createEClassifier(owningtype + ":EEnum");
		}
		if (!(eClassifier instanceof EEnum))
			return null;
		
		return createEEnumLiteral(name, (EEnum)eClassifier);
	}
	
	public EEnumLiteral createEEnumLiteral(String name, EEnum eEnum) {
		
		EEnumLiteral literal = theCoreFactory.createEEnumLiteral();
		literal.setLiteral(name);
		literal.setName(name.toUpperCase());

		eEnum.getELiterals().add(literal);

		return literal;
	}
	
	public EDataType getEDataType(String type) {
		EClassifier eClassifier = getEClassifier(type);
		if (eClassifier instanceof EDataType) {
			return (EDataType) eClassifier;
		}
		Assert.isTrue(eClassifier==null);
		return null;
	}
	
	public EDataType createEDataType(String type) {
		type = getType(type);
		EDataType eDataType = getEDataType(type);
		if (eDataType!=null)
			return eDataType;
		
		eDataType = theCoreFactory.createEDataType();
		eDataType.setName(type);
		
		ePackage.getEClassifiers().add(eDataType);
		// make this look like a DocumentRoot so that it can be added
		// to the containing object's "anyType" feature.
		ExtendedMetaData.INSTANCE.setName(eDataType, "");
		EAnnotation ea = theCoreFactory.createEAnnotation();
		ea.setEModelElement(eDataType);
		ea.setSource(EDataTypeConversionFactory.DATATYPE_CONVERSION_FACTORY_URI);
		ConversionDelegate cd = EDataTypeConversionFactory.INSTANCE.createConversionDelegate(eDataType);
		if (cd!=null) {
			Object value = cd.createFromString("");
			eDataType.setInstanceClass(value.getClass());
		}
		eDataType.getEAnnotations().add(ea);

		return eDataType;
	}

	public EClass getEClass(String type) {
		EClassifier eClassifier = getEClassifier(type);
		if (eClassifier instanceof EClass) {
			return (EClass) eClassifier;
		}
		Assert.isTrue(eClassifier==null);
		return null;
	}
	
	public EClass createEClass(String type) {
		EClass eClass = getEClass(type);
		if (eClass!=null)
			return eClass;
		
		eClass = theCoreFactory.createEClass();
		eClass.setName(getType(type));
		eClass.getESuperTypes().add(XMLTypePackage.eINSTANCE.getAnyType());
		
		ePackage.getEClassifiers().add(eClass);
		for (String st : getSuperTypes(type)) {
			EClassifier eClassifier = findEClassifier(st);
			if (eClassifier instanceof EClass)
				eClass.getESuperTypes().add((EClass) eClassifier);
		}
		// make this class look like a DocumentRoot so that it can be added
		// to the containing object's "anyType" feature.
		ExtendedMetaData.INSTANCE.setName(eClass, "");
		eClass.setInstanceClass(AnyType.class);

		return eClass;
	}
	
	public EAttribute getEAttribute(String name, String type, String owningtype) {
		if (type==null)
			type = "EString";
		
		EClass eClass = getEClass(owningtype);
		if (eClass!=null) {
			// the EClass already exists in our EPackage: check if the named feature was already created
			EStructuralFeature feature = eClass.getEStructuralFeature(name);
			if (feature instanceof EAttribute) {
				EClassifier eClassifier = findEClassifier(type);
				Assert.isTrue(eClassifier == feature.getEType());
				return (EAttribute) feature;
			}
			Assert.isTrue(feature==null);
			return null;
		}
		else {
			// if not, check other related packages including the Bpmn2Package
			EClassifier ec = findEClassifier(owningtype);
			if ( !isValid(ec) && ec instanceof EClass ) {
				// the EClass does not belong to us, but if the feature exists in that EClass, use it.
				EStructuralFeature feature = ((EClass)ec).getEStructuralFeature(name);
				if (feature instanceof EAttribute) {
					return (EAttribute) feature;
				}
			}
		}
		Assert.isTrue(eClass==null);
		return null;
	}
	
	public EAttribute createEAttribute(String name, String type, String owningtype, String defaultValue) {
		if (type==null)
			type = "EString";
		
		EAttribute eAttribute = getEAttribute(name,type,owningtype);
		if (eAttribute!=null)
			return eAttribute;

		// if the class type does not exist, create it in this package
		EClassifier eClassifier = findEClassifier(type);
		if (eClassifier==null) {
			eClassifier = createEClassifier(type);
		}

		// check if owning class is in this package
		EClass eClass = getEClass(owningtype);
		if (eClass==null) {
			// if not, check other related packages including the Bpmn2Package
			EClassifier ec = findEClassifier(owningtype);
			if ( !isValid(ec) ) {
				ec = createEClass(owningtype);
			}
			if (ec instanceof EClass)
				eClass = (EClass) ec;
		}
		Assert.isNotNull(eClass);

		eAttribute = theCoreFactory.createEAttribute();
		eAttribute.setName(name);
		eAttribute.setEType(eClassifier);
		
		eClass.getEStructuralFeatures().add(eAttribute);
		
		ExtendedMetaData.INSTANCE.setNamespace(eAttribute, ePackage.getNsURI());
		ExtendedMetaData.INSTANCE.setFeatureKind(eAttribute, ExtendedMetaData.ATTRIBUTE_FEATURE);
		ExtendedMetaData.INSTANCE.setName(eAttribute, name);
		
		if (eClassifier instanceof EEnum) {
			if (defaultValue!=null) {
				boolean setDefault = true;
				for (String v : defaultValue.split(" ")) {
					if (setDefault) {
						eAttribute.setDefaultValue(v);
						setDefault = false;
					}
					createEEnumLiteral(v, (EEnum)eClassifier);
				}
			}
		}
		else if (eClassifier instanceof EDataType) {
			if (defaultValue!=null) {
				eAttribute.setDefaultValue(defaultValue);
			}
		}

		return eAttribute;
	}

	public EReference getEReference(String name, String type, String owningtype, boolean containment, boolean many) {
		EClass eClass = getEClass(owningtype);
		if (eClass != null) {
			// the EClass already exists in our EPackage: check if the named feature was already created
			EStructuralFeature feature = eClass.getEStructuralFeature(name);
			if (feature instanceof EReference) {
				EClassifier eClassifier = findEClassifier(type);
				Assert.isTrue(eClassifier == feature.getEType());
				Assert.isTrue(containment == ((EReference) feature)
						.isContainment());
				Assert.isTrue(many ? ((EReference) feature).getUpperBound() == EStructuralFeature.UNBOUNDED_MULTIPLICITY
						: true);
				return (EReference) feature;
			}
			Assert.isTrue(feature == null);
			return null;
		}
		else {
			// if not, check other related packages including the Bpmn2Package
			EClassifier ec = findEClassifier(owningtype);
			if ( !isValid(ec) && ec instanceof EClass ) {
				// the EClass does not belong to us, but if the feature exists in that EClass, use it.
				EStructuralFeature feature = ((EClass)ec).getEStructuralFeature(name);
				if (feature instanceof EReference) {
					return (EReference) feature;
				}
			}
		}
		Assert.isTrue(eClass==null);
		return null;
	}

	public EReference createEReference(String name, String type, String owningtype, boolean containment, boolean many) {
		EReference eReference = getEReference(name,type,owningtype,containment,many);
		if (eReference!=null)
			return eReference;

		// if the class type does not exist, create it in this package
		EClassifier eClassifier = findEClassifier(type);
		if (eClassifier==null) {
			eClassifier = createEClass(type);
		}

		eReference = theCoreFactory.createEReference();
		eReference.setName(name);
		eReference.setChangeable(true);
		eReference.setUnsettable(true);
		eReference.setUnique(true);
		eReference.setContainment(containment);
		if (many)
			eReference.setUpperBound(EStructuralFeature.UNBOUNDED_MULTIPLICITY);
		eReference.setEType(eClassifier);
		
		// check if owning class is in this package
		EClass eClass = getEClass(owningtype);
		if (eClass==null) {
			// if not, check other related packages
			EClassifier ec = findEClassifier(owningtype);
			if ( !isValid(ec) ) {
				ec = createEClass(owningtype);
			}
			if (ec instanceof EClass)
				eClass = (EClass) ec;
		}
		Assert.isNotNull(eClass);
		eClass.getEStructuralFeatures().add(eReference);
		
		ExtendedMetaData.INSTANCE.setNamespace(eReference, ePackage.getNsURI());
		ExtendedMetaData.INSTANCE.setFeatureKind(eReference, ExtendedMetaData.ELEMENT_FEATURE);
		ExtendedMetaData.INSTANCE.setName(eReference, name);

		return eReference;
	}

	public boolean isValid(EClassifier eClassifier) {
		EPackage p = eClassifier==null ? null : eClassifier.getEPackage();
		return eClassifier!=null &&
				(p == ePackage || getRelatedEPackages().contains(p));
	}
	
	public static boolean isValid(EPackage pkg) {
		return pkg!=null &&
				pkg != EcorePackage.eINSTANCE &&
				pkg != Bpmn2Package.eINSTANCE &&
				pkg != BpmnDiPackage.eINSTANCE &&
				pkg != DcPackage.eINSTANCE &&
				pkg != DiPackage.eINSTANCE;
	}
	
	public EClassifier findEClassifier(String type) {
		// parse out just the class type, excluding super types
		type = getType(type);
		EClassifier eClassifier = null;
		if (type==null) {
			return EcorePackage.eINSTANCE.getEObject();
		}
		
		if (ePackage!=null) {
			eClassifier = ePackage.getEClassifier(type);
			if (eClassifier!=null)
				return eClassifier;
		}
		for (EPackage p : getRelatedEPackages()) {
			eClassifier = p.getEClassifier(type);
			if (eClassifier!=null)
				return eClassifier;
		}
		
		return findEClassifier(null,type);
	}

	public static EClassifier findEClassifier(EPackage pkg, String type) {
		EClassifier eClassifier = null;
		if (type==null) {
			return EcorePackage.eINSTANCE.getEObject();
		}
		
		if (pkg!=null) {
			eClassifier = pkg.getEClassifier(type);
			if (eClassifier!=null)
				return eClassifier;
		}
		
		eClassifier = EcorePackage.eINSTANCE.getEClassifier(type);
		if (eClassifier!=null)
			return eClassifier;
		
		eClassifier = Bpmn2Package.eINSTANCE.getEClassifier(type);
		if (eClassifier!=null)
			return eClassifier;
		
		eClassifier = BpmnDiPackage.eINSTANCE.getEClassifier(type);
		if (eClassifier!=null)
			return eClassifier;
		
		eClassifier = DiPackage.eINSTANCE.getEClassifier(type);
		if (eClassifier!=null)
			return eClassifier;
		
		eClassifier = DcPackage.eINSTANCE.getEClassifier(type);
		if (eClassifier!=null)
			return eClassifier;
		
		return null;
	}

	public static EStructuralFeature getAnyAttribute(EObject object, String name) {
		EStructuralFeature anyAttribute = ((EObject)object).eClass().getEStructuralFeature("anyAttribute"); //$NON-NLS-1$
		if (anyAttribute!=null && object.eGet(anyAttribute) instanceof BasicFeatureMap) {
			BasicFeatureMap map = (BasicFeatureMap)object.eGet(anyAttribute);
			for (Entry entry : map) {
				EStructuralFeature feature = entry.getEStructuralFeature();
				if (feature.getName().equals(name))
					return feature;
			}
		}
		return null;
	}

	public static List<EStructuralFeature> getAnyAttributes(EObject object) {
		List<EStructuralFeature> list = new ArrayList<EStructuralFeature>();
		EStructuralFeature anyAttribute = ((EObject)object).eClass().getEStructuralFeature("anyAttribute"); //$NON-NLS-1$
		if (anyAttribute!=null && object.eGet(anyAttribute) instanceof BasicFeatureMap) {
			BasicFeatureMap map = (BasicFeatureMap)object.eGet(anyAttribute);
			for (Entry entry : map) {
				EStructuralFeature feature = entry.getEStructuralFeature();
				list.add(feature);
			}
		}
		return list;
	}
	
	// FIXME: this can't be static because the EPackage for the new feature may not be
	// created and initialize properly by ExtendedMetadata.demandFeature().
	// Access to anyAttribute MUST go through the ModelExtensionDescriptor's
	// modelDecorator so that we can properly find, and optionally create and initialize
	// the EPackage that contains the extensions
	@SuppressWarnings("unchecked")
	public EStructuralFeature addAnyAttribute(EObject childObject, String namespace, String name, String type, Object value) {
		EStructuralFeature attr = null;
		EClass eclass = null;
		if (childObject instanceof EClass) {
			eclass = (EClass)childObject;
			childObject = ExtendedPropertiesAdapter.getDummyObject(eclass);
		}
		else
			eclass = childObject.eClass();
		EStructuralFeature anyAttribute = eclass.getEStructuralFeature(Bpmn2Package.BASE_ELEMENT__ANY_ATTRIBUTE);
		List<BasicFeatureMap.Entry> anyMap = (List<BasicFeatureMap.Entry>)childObject.eGet(anyAttribute);
		if (anyMap==null)
			return null;
		for (BasicFeatureMap.Entry fe : anyMap) {
			if (fe.getEStructuralFeature() instanceof EAttributeImpl) {
				EAttributeImpl a = (EAttributeImpl) fe.getEStructuralFeature();
				if (namespace.equals(a.getExtendedMetaData().getNamespace()) && name.equals(a.getName())) {
					attr = a;
					break;
				}
			}
		}
		
		// this featuremap can only hold attributes, not elements
		if (type==null)
			type = "E" + value.getClass().getSimpleName(); //$NON-NLS-1$
		EPackage pkg = ModelDecorator.getEPackage(namespace);
		EDataType eDataType = (EDataType)ModelDecorator.findEClassifier(pkg, type);//(EDataType)EcorePackage.eINSTANCE.getEClassifier(type);
		if (eDataType!=null) {
			if (attr==null) {
				// FIXME: demandFeature() will create a new EPackage if none exists for the given namespace
				// This is where its EFactory needs to be set since it's null after demandFeature() creates
				// the EPackage.
				attr = createEAttribute(name, type, eclass.getName(), null);
				anyMap.add( FeatureMapUtil.createEntry(attr, value) );
			}
			else {
				EClassifier dt = attr.getEType();
				if (dt==null || !eDataType.getInstanceClass().isAssignableFrom(dt.getInstanceClass()))
					throw new IllegalArgumentException(
						NLS.bind(
							Messages.ModelUtil_Illegal_Value,
							new Object[] {
								childObject.eClass().getName(),
								attr.getName(),
								attr.getEType().getName(),
								value.toString()
							}
						)
					);
				anyMap.add( FeatureMapUtil.createEntry(attr, value) );
			}
		}
		else if (attr==null) {
			attr = createEAttribute(name, type, eclass.getName(), null);
			anyMap.add( FeatureMapUtil.createEntry(attr, value) );
		}
		else {
			anyMap.add( FeatureMapUtil.createEntry(attr, value) );
		}
		return attr;
	}

	public EStructuralFeature addAnyAttribute(EObject object, String name, String type, Object value) {
		EPackage pkg = object.eClass().getEPackage();
		String nsURI = pkg.getNsURI();
		return addAnyAttribute(object, nsURI, name, type, value);
	}

	public static void addExtensionAttributeValue(EObject object, EStructuralFeature feature, Object value) {
		addExtensionAttributeValue(object, feature, value, -1, false);
	}

	public static void addExtensionAttributeValue(EObject object, EStructuralFeature feature, Object value, boolean delay) {
		addExtensionAttributeValue(object, feature, value, -1, delay);
	}

	// FIXME: this can't be static because the EPackage for the new feature may not be
	// created and initialized properly by ExtendedMetadata.demandFeature().
	// Access to anyAttribute MUST go through the ModelExtensionDescriptor's
	// modelDecorator so that we can properly find, and optionally create and initialize
	// the EPackage that contains the extensions
	@SuppressWarnings("unchecked")
	public static void addExtensionAttributeValue(EObject object, EStructuralFeature feature, Object value, int index, boolean delay) {
		if (object instanceof ExtensionAttributeValue)
			object = object.eContainer();
		EStructuralFeature evf = object.eClass().getEStructuralFeature("extensionValues"); //$NON-NLS-1$
		EList<EObject> list = (EList<EObject>)object.eGet(evf);
		
		if (list.size()==0) {
			ExtensionAttributeValue newItem = Bpmn2ModelerFactory.create(ExtensionAttributeValue.class);
			ModelUtil.setID(newItem);
			FeatureMap map = newItem.getValue();
			map.add(feature, value);
			if (delay) {
				InsertionAdapter.add(object, feature, (EObject)value);
			}
			else {
				list.add(newItem);
			}
		}
		else {
			ExtensionAttributeValue oldItem = (ExtensionAttributeValue) list.get(0);
			if (delay) {
				InsertionAdapter.add(object, feature, (EObject)value);
			}
			else {
				FeatureMap map = oldItem.getValue();
				if (!feature.isMany()) {
					// only one of these features is allowed: remove existing one(s)
					for (int i=0; i<map.size(); ++i) {
						Entry entry = map.get(i);
						if (entry.getEStructuralFeature().getName().equals(feature.getName())) {
							map.remove(i--);
						}
					}
					map.add(feature, value);
				}
				else if (index>=0){
				}
				else {
					map.add(feature, value);
				}
			}
		}
	}

	public static List<ExtensionAttributeValue> getExtensionAttributeValues(EObject be) {
		if (be instanceof Participant) {
			final Participant participant = (Participant) be;
			if (participant.getProcessRef() == null) {
				if (participant.eContainer() instanceof Collaboration) {
					Collaboration collab = (Collaboration) participant.eContainer();
					if (collab.eContainer() instanceof Definitions) {
						final Definitions definitions = ModelUtil.getDefinitions(collab);
						
						TransactionalEditingDomain domain = TransactionUtil.getEditingDomain(definitions.eResource());
						
						domain.getCommandStack().execute(new RecordingCommand(domain) {
							@Override
							protected void doExecute() {
								Process process = Bpmn2ModelerFactory.create(Process.class);
								participant.setProcessRef(process);
								definitions.getRootElements().add(process);
								ModelUtil.setID(process);
							}
							
						});
						
					}
				}
			}
			return participant.getProcessRef().getExtensionValues();
		}
		if (be instanceof BPMNDiagram) {
			BPMNDiagram diagram = (BPMNDiagram) be;
			BaseElement bpmnElement = diagram.getPlane().getBpmnElement();
			if (bpmnElement instanceof org.eclipse.bpmn2.Process) {
				return bpmnElement.getExtensionValues();
			}
		}
		if (be instanceof BaseElement) {
			return ((BaseElement) be).getExtensionValues();
		}
	
		return new ArrayList<ExtensionAttributeValue>();
	}

	@SuppressWarnings("unchecked")
	public static <T> List<T> getAllExtensionAttributeValues(EObject object, Class<T> clazz) {
		List<T> results = new ArrayList<T>();
		
		if (object!=null) {
			EStructuralFeature evf = object.eClass().getEStructuralFeature("extensionValues"); //$NON-NLS-1$
			EList<ExtensionAttributeValue> list = (EList<ExtensionAttributeValue>)object.eGet(evf);
			for (ExtensionAttributeValue eav : list) {
				FeatureMap fm = eav.getValue();
				for (Entry e : fm) {
					if (clazz.isInstance(e.getValue())) {
						results.add((T)e.getValue());
					}
				}
			}
		}
		return results;
	}

	public static List getAllExtensionAttributeValues(EObject object, EStructuralFeature feature) {
		List<Object> results = new ArrayList<Object>();
		
		if (object!=null) {
			String name = feature.getName();
			EStructuralFeature evf = object.eClass().getEStructuralFeature("extensionValues"); //$NON-NLS-1$
			EList<ExtensionAttributeValue> list = (EList<ExtensionAttributeValue>)object.eGet(evf);
			for (ExtensionAttributeValue eav : list) {
				FeatureMap fm = eav.getValue();
				for (Entry e : fm) {
					if (e.getEStructuralFeature().getName().equals(name)) {
						results.add(e.getValue());
					}
				}
			}
		}
		return results;
	}

	@SuppressWarnings("unchecked")
	public static EStructuralFeature getExtensionAttribute(EObject object, String name) {
		if (object!=null) {
			EStructuralFeature evf = object.eClass().getEStructuralFeature("extensionValues"); //$NON-NLS-1$
			EList<ExtensionAttributeValue> list = (EList<ExtensionAttributeValue>)object.eGet(evf);
			for (ExtensionAttributeValue eav : list) {
				FeatureMap fm = eav.getValue();
				for (Entry e : fm) {
					if (e.getEStructuralFeature().getName().equals(name)) {
						return e.getEStructuralFeature();
					}
				}
			}
		}
		return null;
	}

	public EStructuralFeature getFeature(EObject object, String prefix, String name, boolean isElement) {
		// search for the object's type in our own package
		EClass eClass = getEClass(object.eClass().getName());
		if (eClass!=null) {
			// found it! check if it has the requested feature
			EStructuralFeature feature = eClass.getEStructuralFeature(name);
			if (feature!=null) {
				if (isElement) {
					if (feature instanceof EReference)
						return feature;
				}
				else {
					if (feature instanceof EAttribute)
						return feature;
				}
			}
		}
		object = object.eContainer();
		return null;
	}

	public EStructuralFeature getFeature(EObject object, String name) {
		EStructuralFeature feature = getAnyAttribute(object, name);
		if (feature!=null)
			return feature;
		feature = getExtensionAttribute(object, name);
		if (feature!=null)
			return feature;

		return null;
	}
	
	//
	// ExpandedPropertiesAdapter overrides for feature mutators
	//

	@SuppressWarnings("rawtypes")
	public boolean adaptFeature(ExtendedPropertiesAdapter adapter, EObject object, EStructuralFeature feature) {
		boolean added = true;
		// FIXME: see discussion about resolving ownership of EXTENSION ELEMENTS, above
		if (object instanceof ExtensionAttributeValue)
			object = object.eContainer();

		if (adapter==null)
			adapter = ExtendedPropertiesAdapter.adapt(object);
		
		if (adapter.hasFeatureDescriptor(feature)) {
			FeatureDescriptor fd = adapter.getFeatureDescriptor(feature);
			if (fd instanceof AnyTypeFeatureDescriptor)
				added = false;
		}
		
		if (added) {
			adapter.setFeatureDescriptor(feature, new AnyTypeFeatureDescriptor(adapter, object, feature));
		}
		return added;
	}
	
	class AnyTypeFeatureDescriptor extends FeatureDescriptor {

		public AnyTypeFeatureDescriptor(ExtendedPropertiesAdapter adapter,
				EObject object, EStructuralFeature feature) {
			super(adapter.getAdapterFactory(), object, feature);
		}
		
		private boolean hasStructuralFeatureFeature(EObject object, EStructuralFeature feature) {
			String name = feature.getName();
			if (object instanceof EClass)
				return ((EClass)object).getEStructuralFeature(name) != null;
			return object.eClass().getEStructuralFeature(name) != null;
		}
		
		private boolean isAnyAttribute(EObject object, EStructuralFeature feature) {
			if (hasStructuralFeatureFeature(object,feature))
				return false;
			String name = feature.getName();
			feature = getAnyAttribute(object, name);
			if (feature!=null)
				return true;
			return false;
		}

		private boolean isExtensionAttribute(EObject object, EStructuralFeature feature) {
			if (hasStructuralFeatureFeature(object,feature))
				return false;
			String name = feature.getName();
			feature = getExtensionAttribute(object, name);
			if (feature!=null)
				return true;
			return false;
		}

		@Override
		public Object getValue(int index) {
			if (hasStructuralFeatureFeature(object,feature)) {
				return super.getValue(index);
			}
			if (isAnyAttribute(object,feature)) {
				Object value = null;
				try {
					value = object.eGet(feature);
				}
				catch (Exception e1) {
					object = getPrototype(object.eClass());
					if (object!=null) {
						try {
							value = object.eGet(feature);
						}
						catch (Exception e2) {
							return null;
						}
					}
				}
				return value;
			}
			if (isExtensionAttribute(object,feature)) {
				List result = getAllExtensionAttributeValues(object, feature);
				if (result.size()==0) {
					return null;
				}
				if (index>=0)
					return result.get(index);
				return result.get(0);
			}
			return null;
		}

		private EObject getPrototype(EClass eClass) {
			return null;
		}
		
		@Override
		protected void internalSet(EObject object, EStructuralFeature feature, Object value, int index) {
			if (hasStructuralFeatureFeature(object,feature) || feature.isMany()) {
				object.eGet(feature);
				super.internalSet(object,feature,value,index);
			}
			else {
				// the feature does not exist in this object, so we either need to
				// create an "anyAttribute" entry or, if the object is an ExtensionAttributeValue,
				// create an entry in its "value" feature map.
				String name = feature.getName();
				if (feature instanceof EAttribute) {
					EStructuralFeature f = ModelDecorator.getAnyAttribute(object, name);
					if (f!=null) {
						object.eSet(f, value);
					}
					else {
						String namespace = ExtendedMetaData.INSTANCE.getNamespace(feature);
						String type = feature.getEType().getName();
						addAnyAttribute(object, namespace, name, type, value);
					}
				}
				else {
					// FIXME: access to ExtensionAttributeValues MUST go through the ModelExtensionDescriptor's
					// modelDecorator so that we can properly find, and optionally create and initialize
					// the EPackage that contains the extensions
					addExtensionAttributeValue(object, feature, value, index, false);
				}
			}
		}
	}

}

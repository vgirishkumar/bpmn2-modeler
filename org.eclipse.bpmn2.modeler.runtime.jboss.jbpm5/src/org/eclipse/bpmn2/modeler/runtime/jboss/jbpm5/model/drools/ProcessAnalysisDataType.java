/**
 */
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools;

import org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.Scenario;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.FeatureMap;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Process Analysis Data Type</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.ProcessAnalysisDataType#getGroup <em>Group</em>}</li>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.ProcessAnalysisDataType#getScenario <em>Scenario</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DroolsPackage#getProcessAnalysisDataType()
 * @model extendedMetaData="name='ProcessAnalysisData_._type' kind='elementOnly'"
 * @generated
 */
public interface ProcessAnalysisDataType extends EObject {
	/**
	 * Returns the value of the '<em><b>Group</b></em>' attribute list.
	 * The list contents are of type {@link org.eclipse.emf.ecore.util.FeatureMap.Entry}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Group</em>' attribute list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Group</em>' attribute list.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DroolsPackage#getProcessAnalysisDataType_Group()
	 * @model unique="false" dataType="org.eclipse.emf.ecore.EFeatureMapEntry" many="true"
	 *        extendedMetaData="kind='group' name='group:0'"
	 * @generated
	 */
	FeatureMap getGroup();

	/**
	 * Returns the value of the '<em><b>Scenario</b></em>' containment reference list.
	 * The list contents are of type {@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.bpsim.Scenario}.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Scenario</em>' containment reference list isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Scenario</em>' containment reference list.
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.drools.DroolsPackage#getProcessAnalysisDataType_Scenario()
	 * @model containment="true" required="true" transient="true" volatile="true" derived="true"
	 *        extendedMetaData="kind='element' name='Scenario' namespace='##targetNamespace' group='#group:0'"
	 * @generated
	 */
	EList<Scenario> getScenario();

} // ProcessAnalysisDataType

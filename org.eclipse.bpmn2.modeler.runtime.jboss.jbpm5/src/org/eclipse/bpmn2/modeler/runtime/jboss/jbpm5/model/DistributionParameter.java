/**
 */
package org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model;


/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Distribution Parameter</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * <ul>
 *   <li>{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DistributionParameter#isDiscrete <em>Discrete</em>}</li>
 * </ul>
 * </p>
 *
 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDistributionParameter()
 * @model extendedMetaData="name='DistributionParameter' kind='empty'"
 * @generated
 */
public interface DistributionParameter extends ParameterValue {
	/**
	 * Returns the value of the '<em><b>Discrete</b></em>' attribute.
	 * The default value is <code>"false"</code>.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Discrete</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Discrete</em>' attribute.
	 * @see #isSetDiscrete()
	 * @see #unsetDiscrete()
	 * @see #setDiscrete(boolean)
	 * @see org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.ModelPackage#getDistributionParameter_Discrete()
	 * @model default="false" unsettable="true" dataType="org.eclipse.emf.ecore.xml.type.Boolean"
	 *        extendedMetaData="kind='attribute' name='discrete'"
	 * @generated
	 */
	boolean isDiscrete();

	/**
	 * Sets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DistributionParameter#isDiscrete <em>Discrete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Discrete</em>' attribute.
	 * @see #isSetDiscrete()
	 * @see #unsetDiscrete()
	 * @see #isDiscrete()
	 * @generated
	 */
	void setDiscrete(boolean value);

	/**
	 * Unsets the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DistributionParameter#isDiscrete <em>Discrete</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #isSetDiscrete()
	 * @see #isDiscrete()
	 * @see #setDiscrete(boolean)
	 * @generated
	 */
	void unsetDiscrete();

	/**
	 * Returns whether the value of the '{@link org.eclipse.bpmn2.modeler.runtime.jboss.jbpm5.model.DistributionParameter#isDiscrete <em>Discrete</em>}' attribute is set.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return whether the value of the '<em>Discrete</em>' attribute is set.
	 * @see #unsetDiscrete()
	 * @see #isDiscrete()
	 * @see #setDiscrete(boolean)
	 * @generated
	 */
	boolean isSetDiscrete();

} // DistributionParameter

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

package org.eclipse.bpmn2.modeler.core.runtime;

/**
 *
 */
@SuppressWarnings("serial")
public class TargetRuntimeConfigurationException extends IllegalArgumentException {

	Throwable cause;
	TargetRuntime targetRuntime;
	
	/**
	 * @param e
	 */
	public TargetRuntimeConfigurationException(TargetRuntime targetRuntime, Exception cause) {
		this.targetRuntime = targetRuntime;
		this.cause = cause;
	}

	@Override
	public String getMessage() {
		if (targetRuntime==null) {
			if (cause instanceof TargetRuntimeConfigurationException) {
				String msg = ((TargetRuntimeConfigurationException)cause).getMessage();
				if (!msg.contains("Unknown"))
					return msg;
			}
		}
		return "Configuration Error for Target Runtime "+
				(targetRuntime==null ? "Unknown" :targetRuntime.getName());
	}

	@Override
	public synchronized Throwable getCause() {
		if (cause instanceof TargetRuntimeConfigurationException)
			return ((TargetRuntimeConfigurationException)cause).getCause();
		return cause;
	}
}

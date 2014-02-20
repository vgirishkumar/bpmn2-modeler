package org.eclipse.bpmn2.modeler.core.runtime;

import org.eclipse.core.resources.IFile;

public interface IRuntimeExtensionDescriptor {

	String getExtensionName();
	void setRuntime(TargetRuntime targetRuntime);
	TargetRuntime getRuntime();
	IFile getConfigFile();
	void setConfigFile(IFile configFile);
	void dispose();
}

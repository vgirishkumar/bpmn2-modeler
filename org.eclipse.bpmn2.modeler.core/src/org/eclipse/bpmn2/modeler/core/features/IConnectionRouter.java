package org.eclipse.bpmn2.modeler.core.features;
import org.eclipse.graphiti.mm.pictograms.Connection;

public interface IConnectionRouter {
	public boolean route(Connection connection);
}

package org.eclipse.bpmn2.modeler.runtime.example;

import org.eclipse.bpmn2.modeler.ui.diagram.BPMNToolBehaviorProvider;
import org.eclipse.graphiti.dt.IDiagramTypeProvider;
import org.eclipse.graphiti.mm.algorithms.GraphicsAlgorithm;

public class SampleToolProvider extends BPMNToolBehaviorProvider {

	public SampleToolProvider(IDiagramTypeProvider diagramTypeProvider) {
		super(diagramTypeProvider);
	}

	@Override
	public Object getToolTip(GraphicsAlgorithm ga) {
		return ga.eClass().getName();
	}

}

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
package org.eclipse.bpmn2.modeler.core.validation;

import java.util.List;

import org.eclipse.bpmn2.Assignment;
import org.eclipse.bpmn2.BaseElement;
import org.eclipse.bpmn2.Bpmn2Package;
import org.eclipse.bpmn2.CallActivity;
import org.eclipse.bpmn2.CatchEvent;
import org.eclipse.bpmn2.CompensateEventDefinition;
import org.eclipse.bpmn2.ComplexGateway;
import org.eclipse.bpmn2.ConditionalEventDefinition;
import org.eclipse.bpmn2.DataAssociation;
import org.eclipse.bpmn2.DataObject;
import org.eclipse.bpmn2.Definitions;
import org.eclipse.bpmn2.EndEvent;
import org.eclipse.bpmn2.Error;
import org.eclipse.bpmn2.ErrorEventDefinition;
import org.eclipse.bpmn2.Escalation;
import org.eclipse.bpmn2.EscalationEventDefinition;
import org.eclipse.bpmn2.EventBasedGateway;
import org.eclipse.bpmn2.EventDefinition;
import org.eclipse.bpmn2.ExclusiveGateway;
import org.eclipse.bpmn2.FlowElement;
import org.eclipse.bpmn2.FlowNode;
import org.eclipse.bpmn2.FormalExpression;
import org.eclipse.bpmn2.Gateway;
import org.eclipse.bpmn2.GatewayDirection;
import org.eclipse.bpmn2.Import;
import org.eclipse.bpmn2.InclusiveGateway;
import org.eclipse.bpmn2.InputOutputSpecification;
import org.eclipse.bpmn2.Interface;
import org.eclipse.bpmn2.ItemDefinition;
import org.eclipse.bpmn2.Message;
import org.eclipse.bpmn2.MessageEventDefinition;
import org.eclipse.bpmn2.Operation;
import org.eclipse.bpmn2.ParallelGateway;
import org.eclipse.bpmn2.Process;
import org.eclipse.bpmn2.ScriptTask;
import org.eclipse.bpmn2.SendTask;
import org.eclipse.bpmn2.SequenceFlow;
import org.eclipse.bpmn2.Signal;
import org.eclipse.bpmn2.SignalEventDefinition;
import org.eclipse.bpmn2.StartEvent;
import org.eclipse.bpmn2.ThrowEvent;
import org.eclipse.bpmn2.TimerEventDefinition;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.validation.AbstractModelConstraint;
import org.eclipse.emf.validation.EMFEventType;
import org.eclipse.emf.validation.IValidationContext;

public class BPMN2BatchValidationConstraint extends AbstractModelConstraint {

	public final static String ERROR_ID = "org.eclipse.bpmn2.modeler.core.validation.error";
	public final static String WARNING_ID = "org.eclipse.bpmn2.modeler.core.validation.warning";
	
	private boolean warnings = false;
	
	public BPMN2BatchValidationConstraint() {
	}

	@Override
	public IStatus validate(IValidationContext ctx) {
		EObject eObj = ctx.getTarget();
		EMFEventType eType = ctx.getEventType();
		String id = ctx.getCurrentConstraintId();
		if (WARNING_ID.equals(id))
			warnings = true;
		else
			warnings = false;
		
		// In the case of batch mode.
		if (eType == EMFEventType.NULL) {
			if (eObj instanceof Definitions) {
				return validateDefinitions(ctx, (Definitions) eObj);
			}
			if (eObj instanceof BaseElement) {
				return validateBaseElement(ctx, (BaseElement) eObj);
			}
		} else { // In the case of live mode.
		}

		return ctx.createSuccessStatus();
	}

	private IStatus validateDefinitions(IValidationContext ctx, Definitions def) {
		if (def.getTargetNamespace()==null || def.getTargetNamespace().isEmpty()) {
			if (warnings) {
			}
			else {
				ctx.addResult(def);
				return ctx.createFailureStatus("No targetNamespace defined");
			}			
		}
		
		return ctx.createSuccessStatus();
	}

	private IStatus validateBaseElement(IValidationContext ctx, BaseElement be) {

		if (be instanceof Process) {
			Process process = (Process) be;

			if (warnings) {
				// report warnings only
				boolean foundStartEvent = false;
				boolean foundEndEvent = false;
				List<FlowElement> flowElements = process.getFlowElements();
				for (FlowElement fe : flowElements) {
					if (fe instanceof StartEvent) {
						foundStartEvent = true;
					}
					if (fe instanceof EndEvent) {
						foundEndEvent = true;
					}
				}
				if (!foundStartEvent) {
					ctx.addResult(be);
					return ctx.createFailureStatus("Process has no Start Event");
				}
				if (!foundEndEvent) {
					ctx.addResult(be);
					return ctx.createFailureStatus("Process has no End Event");
				}
				if (isEmpty(process.getName())) {
					ctx.addResult(be);
					return ctx.createFailureStatus("Process has no name");
				}
			}
			else {
				// report errors only
			}
		}
		else if (be instanceof Import) {
			Import imp = (Import)be;
			if (warnings) {
			}
			else {
				if (isEmpty(imp.getLocation())) {
					ctx.addResult(be);
					return ctx.createFailureStatus("Import has no location");
				}
				if (isEmpty(imp.getNamespace())) {
					ctx.addResult(be);
					return ctx.createFailureStatus("Import has no namespace");
				}
				if (isEmpty(imp.getImportType())) {
					ctx.addResult(be);
					return ctx.createFailureStatus("Import has no import type");
				}
			}
		}
		else if (be instanceof Error) {
			if (warnings) {
				if (((Error)be).getStructureRef()==null) {
					ctx.addResult(be);
					return ctx.createFailureStatus("Error has no type definition");
				}
			}
		}
		else if (be instanceof Escalation) {
			if (warnings) {
				if (((Escalation)be).getStructureRef()==null) {
					ctx.addResult(be);
					return ctx.createFailureStatus("Escalation has no type definition");
				}
			}
		}
		else if (be instanceof Message) {
			if (warnings) {
				if (((Message)be).getItemRef()==null) {
					ctx.addResult(be);
					return ctx.createFailureStatus("Message has no type definition");
				}
			}
		}
		else if (be instanceof Signal) {
			if (warnings) {
				if (((Signal)be).getStructureRef()==null) {
					ctx.addResult(be);
					return ctx.createFailureStatus("Signal has no type definition");
				}
			}
		}
		else if (be instanceof ItemDefinition) {
			if (!warnings) {
				if (((ItemDefinition)be).getStructureRef()==null) {
					ctx.addResult(be);
					return ctx.createFailureStatus("Item Definition has no structure");
				}
			}
		}
		else if (be instanceof StartEvent) {
			StartEvent se = (StartEvent) be;
			
			if (!warnings) {
				if (se.getOutgoing() == null || se.getOutgoing().size() < 1) {
					return ctx.createFailureStatus("Start Event has no outgoing connections");
				}
			}
		}
		else if (be instanceof EndEvent) {
			EndEvent ee = (EndEvent) be;
			
			if (!warnings) {
				if (ee.getIncoming() == null || ee.getIncoming().size() < 1) {
					return ctx.createFailureStatus("End Event has no incoming connections");
				}
			}
		}
		else if (be instanceof ScriptTask) {
			ScriptTask st = (ScriptTask) be;
			
			if (warnings) {
				if (isEmpty(st.getScript())) {
					return ctx.createFailureStatus("Script Task has no script");
				}
				if (isEmpty(st.getScriptFormat())) {
					return ctx.createFailureStatus("Script Task has no script format");
				}
			}
		}
		else if (be instanceof SendTask) {
			SendTask st = (SendTask) be;

			if (!warnings) {
				if (st.getOperationRef() == null) {
					return ctx.createFailureStatus("Send Task has no operation");
				}
				if (st.getMessageRef() == null) {
					return ctx.createFailureStatus("Send Task has no message");
				}
			}
		}
		else if (be instanceof CatchEvent) {
			CatchEvent event = (CatchEvent) be;

			if (!warnings) {
				List<EventDefinition> eventdefs = event.getEventDefinitions();
				if (eventdefs.size()==0) {
					return ctx.createFailureStatus("Catch Event has no event definitions");
				}
				
				for (EventDefinition ed : eventdefs) {
					if (ed instanceof TimerEventDefinition) {
						TimerEventDefinition ted = (TimerEventDefinition) ed;
						if (	ted.getTimeDate() == null
								&& ted.getTimeDuration() == null
								&& ted.getTimeCycle() == null
						) {
							return ctx.createFailureStatus("Timer Event has no timer definition");
						}
					} else if (ed instanceof SignalEventDefinition) {
						if (((SignalEventDefinition) ed).getSignalRef() == null) {
							return ctx.createFailureStatus("Signal Event has no signal definition");
						}
					} else if (ed instanceof ErrorEventDefinition) {
						if (((ErrorEventDefinition) ed).getErrorRef() == null) {
							return ctx.createFailureStatus("Error Event has no error definition");
						}
					} else if (ed instanceof ConditionalEventDefinition) {
						FormalExpression conditionalExp = (FormalExpression) ((ConditionalEventDefinition) ed)
								.getCondition();
						if (conditionalExp.getBody() == null) {
							return ctx.createFailureStatus("Conditional Event has no condition expression");
						}
					} else if (ed instanceof EscalationEventDefinition) {
						if (((EscalationEventDefinition) ed).getEscalationRef() == null) {
							return ctx.createFailureStatus("Escalation Event has no escalation definition");
						}
					} else if (ed instanceof MessageEventDefinition) {
						if (((MessageEventDefinition) ed).getMessageRef() == null) {
							return ctx.createFailureStatus("Message Event has no message definition");
						}
					} else if (ed instanceof CompensateEventDefinition) {
						if (((CompensateEventDefinition) ed).getActivityRef() == null) {
							return ctx.createFailureStatus("Compensate Event has no activity definition");
						}
					}
				}
			}
		}
		else if (be instanceof ThrowEvent) {
			ThrowEvent event = (ThrowEvent) be;

			if (!warnings) {
				List<EventDefinition> eventdefs = event.getEventDefinitions();
				if (eventdefs.size()==0) {
					return ctx.createFailureStatus("Throw Event has no event definitions");
				}

				for (EventDefinition ed : eventdefs) {
					if (ed instanceof TimerEventDefinition) {
						TimerEventDefinition ted = (TimerEventDefinition) ed;
						if (	ted.getTimeDate() == null
								&& ted.getTimeDuration() == null
								&& ted.getTimeCycle() == null
						) {
							return ctx.createFailureStatus("Timer Event has no timer definition");
						}
					} else if (ed instanceof SignalEventDefinition) {
						if (((SignalEventDefinition) ed).getSignalRef() == null) {
							return ctx.createFailureStatus("Signal Event has no signal definition");
						}
					} else if (ed instanceof ErrorEventDefinition) {
						if (((ErrorEventDefinition) ed).getErrorRef() == null) {
							return ctx.createFailureStatus("Error Event has no error definition");
						}
					} else if (ed instanceof ConditionalEventDefinition) {
						FormalExpression conditionalExp = (FormalExpression) ((ConditionalEventDefinition) ed)
								.getCondition();
						if (conditionalExp.getBody() == null) {
							return ctx.createFailureStatus("Conditional Event has no condition expression");
						}
					} else if (ed instanceof EscalationEventDefinition) {
						if (((EscalationEventDefinition) ed).getEscalationRef() == null) {
							return ctx.createFailureStatus("Escalation Event has no conditional escalation definition");
						}
					} else if (ed instanceof MessageEventDefinition) {
						if (((MessageEventDefinition) ed).getMessageRef() == null) {
							return ctx.createFailureStatus("Message Event has no conditional message definition");
						}
					} else if (ed instanceof CompensateEventDefinition) {
						if (((CompensateEventDefinition) ed).getActivityRef() == null) {
							return ctx.createFailureStatus("Compensate Event has no conditional activity definition");
						}
					}
				}
			}
		}
		else if (be instanceof SequenceFlow) {
			SequenceFlow sf = (SequenceFlow) be;

			if (!warnings) {
				if (sf.getSourceRef() == null) {
					return ctx.createFailureStatus("Sequence Flow is not connected to a source");
				}
				if (sf.getTargetRef() == null) {
					return ctx.createFailureStatus("Sequence Flow is not connected to a target");
				}
			}
		}
		else if (be instanceof Gateway) {
			Gateway gw = (Gateway) be;

			if (!warnings) {
				if (gw.getGatewayDirection() == null
						|| gw.getGatewayDirection().getValue() == GatewayDirection.UNSPECIFIED.getValue()) {
					ctx.addResult(Bpmn2Package.eINSTANCE.getGateway_GatewayDirection());
					return ctx.createFailureStatus("Gateway does not specify a valid direction");
				}
				if (gw instanceof ExclusiveGateway) {
					if (gw.getGatewayDirection().getValue() != GatewayDirection.DIVERGING.getValue()
							&& gw.getGatewayDirection().getValue() != GatewayDirection.CONVERGING.getValue()) {
						return ctx.createFailureStatus(
								"Invalid Gateway direction for Exclusing Gateway. It should be 'Converging' or 'Diverging'");
					}
				}
				if (gw instanceof EventBasedGateway) {
					if (gw.getGatewayDirection().getValue() != GatewayDirection.DIVERGING.getValue()) {
						return ctx.createFailureStatus(
								"Invalid Gateway direction for EventBased Gateway. It should be 'Diverging'");
					}
				}
				if (gw instanceof ParallelGateway) {
					if (gw.getGatewayDirection().getValue() != GatewayDirection.DIVERGING.getValue()
							&& gw.getGatewayDirection().getValue() != GatewayDirection.CONVERGING.getValue()) {
						return ctx.createFailureStatus(
								"Invalid Gateway direction for Parallel Gateway. It should be 'Converging' or 'Diverging'");
					}
				}
				if (gw instanceof InclusiveGateway) {
					if (gw.getGatewayDirection().getValue() != GatewayDirection.DIVERGING.getValue()
							&& gw.getGatewayDirection().getValue() != GatewayDirection.CONVERGING.getValue()) {
						return ctx.createFailureStatus(
								"Invalid Gateway direction for Inclusive Gateway. It should be 'Converging' or 'Diverging'");
					}
				}
				if (gw instanceof ComplexGateway) {
					if (gw.getGatewayDirection().getValue() != GatewayDirection.DIVERGING.getValue()
							&& gw.getGatewayDirection().getValue() != GatewayDirection.CONVERGING.getValue()) {
						return ctx.createFailureStatus(
								"Invalid Gateway direction for Complex Gateway. It should be 'Converging' or 'Diverging'");
					}
				}
			}
		}
		else if (be instanceof CallActivity) {
			CallActivity ca = (CallActivity) be;

			if (!warnings) {
				if (ca.getCalledElementRef() == null) {
					return ctx.createFailureStatus(
							"Reusable Subprocess has no called element specified");
				}
			}
		}
		else if (be instanceof DataObject) {
			DataObject dao = (DataObject) be;

			if (!warnings) {
				if (dao.getName() == null || dao.getName().length() < 1) {
					return ctx.createFailureStatus("Data Object has no name");
				}
			}
		}
		else if (be instanceof Interface) {
			Interface iface = (Interface)be;
			if (warnings) {
			}
			else {
				if (isEmpty(iface.getOperations())) {
					ctx.addResult(be);
					return ctx.createFailureStatus("Interface has no Operations");
				}
				if (isEmpty(iface.getName())) {
					ctx.addResult(be);
					return ctx.createFailureStatus("Import has no name");
				}
			}
		}
		else if (be instanceof Operation) {
			Operation op = (Operation)be;
			if (warnings) {
			}
			else {
				if (isEmpty(op.getInMessageRef())) {
					ctx.addResult(be);
					return ctx.createFailureStatus("Operation has no input message");
				}
				if (isEmpty(op.getName())) {
					ctx.addResult(be);
					return ctx.createFailureStatus("Operation has no name");
				}
			}
		}
		else if (be instanceof DataAssociation) {
			DataAssociation assoc = (DataAssociation)be;
			if (warnings) {
			}
			else {
				if (isEmpty(assoc.getTargetRef())) {
					ctx.addResult(be);
					return ctx.createFailureStatus("Data Mapping has no target");
				}
			}
		}
		else if (be instanceof Assignment) {
			Assignment assign = (Assignment)be;
			if (warnings) {
			}
			else {
				if (isEmpty(assign.getFrom())) {
					ctx.addResult(be);
					return ctx.createFailureStatus("Assignment has no source");
				}
				if (isEmpty(assign.getTo())) {
					ctx.addResult(be);
					return ctx.createFailureStatus("Assignment has no target");
				}
			}
		}
		else if (be instanceof InputOutputSpecification) {
			InputOutputSpecification iospec = (InputOutputSpecification)be;
			if (warnings) {
			}
			else {
				if (isEmpty(iospec.getInputSets())) {
					ctx.addResult(be);
					return ctx.createFailureStatus("I/O Specification has no input sets");
				}
				if (isEmpty(iospec.getOutputSets())) {
					ctx.addResult(be);
					return ctx.createFailureStatus("I/O Specification has no output sets");
				}
			}
		}
		
		
		if (be instanceof FlowNode) {
			return validateFlowNode(ctx, (FlowNode) be);
		}

		return ctx.createSuccessStatus();
	}

	private IStatus validateFlowNode(IValidationContext ctx, FlowNode fn) {
		if (!warnings) {
			boolean needIncoming = true;
			boolean needOutgoing = true;
			if (fn instanceof ThrowEvent)
				needOutgoing = false;
			if (fn instanceof CatchEvent)
				needIncoming = false;
			
			if (needOutgoing) {
				if ((fn.getOutgoing() == null || fn.getOutgoing().size() < 1)) {
					return ctx.createFailureStatus("Node has no outgoing connections");
				}
			}
			if (needIncoming) {
				if ((fn.getIncoming() == null || fn.getIncoming().size() < 1)) {
					return ctx.createFailureStatus("Node has no incoming connections");
				}
			}
		}

		return ctx.createSuccessStatus();
	}

	private static boolean isEmpty(Object object) {
		if (object instanceof String) {
			String str = (String) object;
			return str == null || str.isEmpty();
		}
		else if (object instanceof List) {
			return ((List)object).isEmpty();
		}
		else if (object==null)
			return true;
		return false;
	}

	private boolean containsWhiteSpace(String testString) {
		if (testString != null) {
			for (int i = 0; i < testString.length(); i++) {
				if (Character.isWhitespace(testString.charAt(i))) {
					return true;
				}
			}
		}
		return false;
	}
}

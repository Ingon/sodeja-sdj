package org.sodeja.silan.instruction;

import org.sodeja.silan.Process;
import org.sodeja.silan.SILObject;
import org.sodeja.silan.context.Context;
import org.sodeja.silan.context.MethodContext;

public class ReturnValueInstruction implements Instruction {
	@Override
	public void execute(Process process) {
		MethodContext mc = (MethodContext) process.getActiveContext();
		SILObject obj = mc.pop();
		
		Context parent = mc.getParent();
		parent.push(obj);
		process.setActiveContext(parent);
	}
}

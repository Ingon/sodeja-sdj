package org.sodeja.silan.instruction;

import org.sodeja.silan.Process;
import org.sodeja.silan.context.Context;
import org.sodeja.silan.context.MethodContext;

public class ReturnSelfInstruction implements Instruction {
	@Override
	public void execute(Process process) {
		MethodContext mc = (MethodContext) process.getActiveContext();
		
		Context parent = mc.getParent();
		parent.push(mc.getReceiver());
		process.setActiveContext(parent);
	}
}

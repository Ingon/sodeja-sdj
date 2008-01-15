package org.sodeja.silan.instruction;

import org.sodeja.silan.ChildContext;
import org.sodeja.silan.Context;
import org.sodeja.silan.Process;
import org.sodeja.silan.SILObject;

public class ReturnMethodInstruction implements Instruction {
	public ReturnMethodInstruction() {
	}

	@Override
	public void execute(Process process) {
		ChildContext mc = (ChildContext) process.getActiveContext();
		SILObject obj = mc.pop();
		
		Context parent = mc.getParent();
		parent.push(obj);
		process.setActiveContext(parent);
	}
}

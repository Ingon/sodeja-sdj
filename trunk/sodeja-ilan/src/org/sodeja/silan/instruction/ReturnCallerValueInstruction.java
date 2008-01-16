package org.sodeja.silan.instruction;

import org.sodeja.silan.Process;
import org.sodeja.silan.SILObject;
import org.sodeja.silan.context.BlockContext;
import org.sodeja.silan.context.Context;
import org.sodeja.silan.context.MethodContext;

public class ReturnCallerValueInstruction implements Instruction {
	@Override
	public void execute(Process process) {
		BlockContext ctx = (BlockContext) process.getActiveContext();
		SILObject obj = ctx.pop();

		MethodContext mc = (MethodContext) ctx.getHome();
		
		Context parent = mc.getParent();
		parent.push(obj);
		process.setActiveContext(parent);
	}
}

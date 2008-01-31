package org.sodeja.silan.instruction;

import org.sodeja.silan.Process;
import org.sodeja.silan.SILObject;
import org.sodeja.silan.compiler.src.ExecutableCode;
import org.sodeja.silan.context.AbstractChildContext;
import org.sodeja.silan.context.BlockContext;
import org.sodeja.silan.context.Context;
import org.sodeja.silan.context.ExecutableContext;

public class ReturnCallerValueInstruction implements Instruction {
	@Override
	public void execute(Process process) {
		BlockContext ctx = (BlockContext) process.getActiveContext();
		SILObject obj = ctx.pop();

		Context hctx = ctx.getHome();
		while(hctx instanceof BlockContext) {
			hctx = ((BlockContext) hctx).getHome();
		}
		
		if(hctx instanceof AbstractChildContext) {
			Context parent = ((AbstractChildContext) hctx).getParent();
			parent.push(obj);
			process.setActiveContext(parent);
		} else {
			process.setValue(obj);
			process.setActiveContext(null);
		}
	}
}

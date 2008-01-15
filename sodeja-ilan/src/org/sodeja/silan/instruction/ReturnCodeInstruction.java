package org.sodeja.silan.instruction;

import org.sodeja.silan.Process;
import org.sodeja.silan.context.Context;

public class ReturnCodeInstruction implements Instruction {
	public ReturnCodeInstruction() {
	}

	@Override
	public void execute(Process process) {
		Context ctx = process.getActiveContext();
		process.setValue(ctx.pop());
		process.setActiveContext(null);
	}
}

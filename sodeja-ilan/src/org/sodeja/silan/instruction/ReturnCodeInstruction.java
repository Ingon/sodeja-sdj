package org.sodeja.silan.instruction;

import org.sodeja.silan.Context;
import org.sodeja.silan.Process;

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

package org.sodeja.silan;

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

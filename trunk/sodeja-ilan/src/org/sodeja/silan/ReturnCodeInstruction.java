package org.sodeja.silan;

public class ReturnCodeInstruction implements Instruction {
	private final int returnIndex;
	
	public ReturnCodeInstruction(int returnIndex) {
		this.returnIndex = returnIndex;
	}

	@Override
	public void execute(Process process) {
		Context ctx = process.getActiveContext();
		process.setValue(ctx.get(returnIndex));
		process.setActiveContext(null);
	}
}

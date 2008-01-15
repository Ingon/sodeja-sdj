package org.sodeja.silan;

public class ClearStackInstruction implements Instruction {
	@Override
	public void execute(Process process) {
		process.getActiveContext().clear();
	}
}

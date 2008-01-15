package org.sodeja.silan.instruction;

import org.sodeja.silan.Process;

public class ClearStackInstruction implements Instruction {
	@Override
	public void execute(Process process) {
		process.getActiveContext().clear();
	}
}

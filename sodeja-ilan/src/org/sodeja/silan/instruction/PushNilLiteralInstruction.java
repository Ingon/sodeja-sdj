package org.sodeja.silan.instruction;

import org.sodeja.silan.Process;

public class PushNilLiteralInstruction implements Instruction {
	@Override
	public void execute(Process process) {
		process.getActiveContext().push(process.vm.getObjectManager().getNil());
	}
}

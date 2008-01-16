package org.sodeja.silan.instruction;

import org.sodeja.silan.Process;

public class PushBooleanLiteralInstruction implements Instruction {
	public final Boolean value;
	
	public PushBooleanLiteralInstruction(Boolean value) {
		this.value = value;
	}
	
	@Override
	public void execute(Process process) {
		process.getActiveContext().push(process.vm.objects.newBoolean(value));
	}
}

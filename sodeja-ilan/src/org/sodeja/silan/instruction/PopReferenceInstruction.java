package org.sodeja.silan.instruction;

import org.sodeja.silan.Process;

public class PopReferenceInstruction implements Instruction {
	public final String reference;
	
	public PopReferenceInstruction(String reference) {
		this.reference = reference;
	}

	@Override
	public void execute(Process process) {
		process.getActiveContext().update(reference, process.getActiveContext().pop());
	}
}

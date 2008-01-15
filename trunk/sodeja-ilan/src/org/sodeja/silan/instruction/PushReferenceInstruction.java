package org.sodeja.silan.instruction;

import org.sodeja.silan.Process;
import org.sodeja.silan.SILObject;

public class PushReferenceInstruction implements Instruction {

	public final String reference;
	
	public PushReferenceInstruction(String reference) {
		this.reference = reference;
	}

	@Override
	public void execute(Process process) {
		SILObject obj = process.getActiveContext().resolve(reference);
		process.getActiveContext().push(obj);
	}
}

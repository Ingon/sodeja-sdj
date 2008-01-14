package org.sodeja.silan;

public class PushReferenceInstruction implements Instruction {

	public final String reference;
	
	public PushReferenceInstruction(String reference) {
		this.reference = reference;
	}

	@Override
	public void execute(Process process) {
		process.getActiveContext().findSymbolValue()
	}
}

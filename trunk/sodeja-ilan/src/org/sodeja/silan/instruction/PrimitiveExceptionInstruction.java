package org.sodeja.silan.instruction;

import org.sodeja.silan.Process;

public class PrimitiveExceptionInstruction implements Instruction {

	public final String text;
	
	public PrimitiveExceptionInstruction(String text) {
		this.text = text;
	}

	@Override
	public void execute(Process process) {
		throw new RuntimeException(text);
	}
}

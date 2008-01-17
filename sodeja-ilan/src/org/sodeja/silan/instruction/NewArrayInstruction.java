package org.sodeja.silan.instruction;

import org.sodeja.silan.Process;

public class NewArrayInstruction implements Instruction {

	public final int size;
	
	public NewArrayInstruction(int size) {
		this.size = size;
	}

	@Override
	public void execute(Process process) {
		throw new UnsupportedOperationException();
	}
}

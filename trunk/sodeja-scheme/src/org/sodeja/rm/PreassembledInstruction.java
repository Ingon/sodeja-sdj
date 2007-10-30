package org.sodeja.rm;

import org.sodeja.runtime.scheme.model.Combination;

public class PreassembledInstruction implements Instruction {
	
	public final Combination expr;
	
	public PreassembledInstruction(Combination expr) {
		this.expr = expr;
	}

	@Override
	public void execute() {
		throw new IllegalArgumentException();
	}
}

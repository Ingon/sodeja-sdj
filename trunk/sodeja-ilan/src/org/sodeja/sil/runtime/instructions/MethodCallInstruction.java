package org.sodeja.sil.runtime.instructions;

import org.sodeja.sil.runtime.Interpreter;

public class MethodCallInstruction implements Instruction {

	private int receiverIndex;
	private String selector;
	private int[] argumentsCount;
	
	@Override
	public void execute(Interpreter interpreter) {
	}
}

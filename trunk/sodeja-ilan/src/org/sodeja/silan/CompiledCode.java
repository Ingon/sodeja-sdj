package org.sodeja.silan;

import java.util.List;

public class CompiledCode {
	private final int tempCount;
	private final List<Instruction> instructions;
	
	public CompiledCode(int tempCount, List<Instruction> instructions) {
		this.tempCount = tempCount;
		this.instructions = instructions;
	}
}

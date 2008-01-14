package org.sodeja.silan;

import java.util.List;

public class CompiledCode {
	public final int tempCount;
	public final List<Instruction> instructions;
	
	public CompiledCode(int tempCount, List<Instruction> instructions) {
		this.tempCount = tempCount;
		this.instructions = instructions;
	}
}

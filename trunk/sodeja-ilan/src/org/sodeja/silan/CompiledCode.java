package org.sodeja.silan;

import java.util.List;

public class CompiledCode {
	public final List<String> localVariables;
	public final int tempCount;
	public final List<Instruction> instructions;
	
	public CompiledCode(List<String> localVariables, int tempCount, List<Instruction> instructions) {
		this.localVariables = localVariables;
		this.tempCount = tempCount;
		this.instructions = instructions;
	}
}

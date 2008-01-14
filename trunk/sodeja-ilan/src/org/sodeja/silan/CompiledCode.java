package org.sodeja.silan;

import java.util.List;

public class CompiledCode {
	public final List<String> localVariables;
	public final int maxStackSize;
	public final List<Instruction> instructions;
	
	public CompiledCode(List<String> localVariables, int maxStackSize, List<Instruction> instructions) {
		this.localVariables = localVariables;
		this.maxStackSize = maxStackSize;
		this.instructions = instructions;
	}
}

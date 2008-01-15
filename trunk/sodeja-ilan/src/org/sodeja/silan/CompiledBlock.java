package org.sodeja.silan;

import java.util.List;

import org.sodeja.silan.instruction.Instruction;

public class CompiledBlock extends CompiledCode {
	public final List<String> arguments;

	public CompiledBlock(List<String> arguments, List<String> localVariables, int tempCount, List<Instruction> instructions) {
		super(localVariables, tempCount, instructions);
		
		this.arguments = arguments;
	}
}

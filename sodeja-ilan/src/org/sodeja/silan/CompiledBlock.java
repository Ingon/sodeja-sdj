package org.sodeja.silan;

import java.util.List;

import org.sodeja.silan.instruction.Instruction;

public class CompiledBlock extends CallableCompiledCode {
	public CompiledBlock(List<String> arguments, List<String> localVariables, 
			int tempCount, List<Instruction> instructions) {
		super(arguments, localVariables, tempCount, instructions);
	}
}

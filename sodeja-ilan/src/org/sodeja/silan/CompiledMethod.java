package org.sodeja.silan;

import java.util.List;

import org.sodeja.silan.instruction.Instruction;

public class CompiledMethod extends CompiledCode {
	
	public final String selector;
	public final List<String> arguments;
	
	public CompiledMethod(String selector, List<String> arguments, List<String> localVariables, int tempCount, List<Instruction> instructions) {
		super(localVariables, tempCount, instructions);
		
		this.selector = selector;
		this.arguments = arguments;
	}
}

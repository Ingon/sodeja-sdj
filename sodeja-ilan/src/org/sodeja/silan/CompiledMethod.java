package org.sodeja.silan;

import java.util.Collections;
import java.util.List;

import org.sodeja.silan.instruction.Instruction;

public class CompiledMethod extends CallableCompiledCode {
	public final String selector;
	
	public CompiledMethod(String selector, List<String> arguments, 
			List<String> localVariables, int tempCount, List<Instruction> instructions) {
		super(arguments, localVariables, tempCount, instructions);
		
		this.selector = selector;
	}

	public CompiledMethod(String selector, List<String> arguments, int tempCount, List<Instruction> instructions) {
		this(selector, arguments, Collections.EMPTY_LIST, tempCount, instructions);
	}
}

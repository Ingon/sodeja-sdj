package org.sodeja.silan;

import java.util.List;

public class CompiledMethod extends CompiledCode {
	
	public final int argumentCount;
	
	public CompiledMethod(List<String> localVariables, int argumentCount, int tempCount, List<Instruction> instructions) {
		super(localVariables, tempCount, instructions);
		this.argumentCount = argumentCount;
	}
}

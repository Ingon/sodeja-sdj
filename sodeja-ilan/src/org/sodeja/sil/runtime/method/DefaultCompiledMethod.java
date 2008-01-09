package org.sodeja.sil.runtime.method;

import java.util.List;

import org.sodeja.sil.runtime.instructions.Instruction;

public class DefaultCompiledMethod implements CompiledMethod {
	public final Integer argumentsCount;
	public final List<Instruction> instructions;
	
	public DefaultCompiledMethod(Integer argumentsCount, List<Instruction> instructions) {
		this.argumentsCount = argumentsCount;
		this.instructions = instructions;
	}
}

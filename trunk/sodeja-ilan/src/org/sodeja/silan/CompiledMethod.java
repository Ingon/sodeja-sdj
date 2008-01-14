package org.sodeja.silan;

import java.util.List;

import org.sodeja.collections.ListUtils;

public class CompiledMethod extends CompiledCode {
	
	public final int argumentCount;
	
	public CompiledMethod(int argumentCount, int tempCount, List<Instruction> instructions) {
		super(tempCount, instructions);
		this.argumentCount = argumentCount;
	}
	
	// XXX used only for primitive methods
	protected CompiledMethod(int argumentCount, int tempCount, Instruction instruction) {
		this(argumentCount, tempCount, ListUtils.asList(instruction));
	}
}

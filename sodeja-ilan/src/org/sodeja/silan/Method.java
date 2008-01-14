package org.sodeja.silan;

import java.util.List;

import org.sodeja.collections.ListUtils;

public class Method extends CompiledCode {
	
	public final int argumentCount;
	
	public Method(int argumentCount, int tempCount, List<Instruction> instructions) {
		super(tempCount, instructions);
		this.argumentCount = argumentCount;
	}
	
	// XXX used only for primitive methods
	protected Method(int argumentCount, int tempCount, Instruction instruction) {
		this(argumentCount, tempCount, ListUtils.asList(instruction));
	}
}

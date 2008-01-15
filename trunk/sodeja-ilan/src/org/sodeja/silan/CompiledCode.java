package org.sodeja.silan;

import java.util.Collections;
import java.util.List;

import org.sodeja.silan.instruction.Instruction;

public class CompiledCode {
	private String source;
	public final List<String> localVariables;
	public final int maxStackSize;
	public final List<Instruction> instructions;
	
	public CompiledCode(List<String> localVariables, int maxStackSize, List<Instruction> instructions) {
		if(localVariables == null) {
			localVariables = Collections.emptyList();
		}
		this.localVariables = localVariables;
		this.maxStackSize = maxStackSize;
		this.instructions = instructions;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}
}

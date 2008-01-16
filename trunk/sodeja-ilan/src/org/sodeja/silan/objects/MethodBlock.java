package org.sodeja.silan.objects;

import java.util.List;

public class MethodBlock {
	public final List<InstructionDefinition> instructions;

	public MethodBlock(List<InstructionDefinition> instructions) {
		this.instructions = instructions;
	}
}

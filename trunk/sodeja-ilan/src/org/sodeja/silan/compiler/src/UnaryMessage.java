package org.sodeja.silan.compiler.src;

import java.util.List;

import org.sodeja.silan.compiler.Compiler;
import org.sodeja.silan.instruction.Instruction;
import org.sodeja.silan.instruction.MessageInstruction;

public class UnaryMessage implements Message {
	public final String selector;

	public UnaryMessage(String selector) {
		this.selector = selector;
	}

	@Override
	public void compile(Compiler compiler, ExecutableCode codeModel, List<Instruction> instructions) {
		instructions.add(new MessageInstruction(selector, 0));
	}
}

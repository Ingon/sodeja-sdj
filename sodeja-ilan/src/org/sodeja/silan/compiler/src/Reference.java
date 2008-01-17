package org.sodeja.silan.compiler.src;

import java.util.List;

import org.sodeja.silan.compiler.Compiler;
import org.sodeja.silan.instruction.Instruction;
import org.sodeja.silan.instruction.PushReferenceInstruction;

public class Reference implements Primary {
	public final String value;

	public Reference(String value) {
		this.value = value;
	}

	@Override
	public void compile(Compiler compiler, ExecutableCode codeModel, List<Instruction> instructions) {
		instructions.add(new PushReferenceInstruction(value));
	}
}

package org.sodeja.silan.compiler.src;

import java.util.List;

import org.sodeja.silan.compiler.Compiler;
import org.sodeja.silan.instruction.Instruction;
import org.sodeja.silan.instruction.PushStringLiteralInstruction;

public class StringLiteral implements Literal {
	public final String value;

	public StringLiteral(String value) {
		this.value = value.substring(1, value.length() - 1);
	}

	@Override
	public void compile(Compiler compiler, ExecutableCode codeModel, List<Instruction> instructions) {
		instructions.add(new PushStringLiteralInstruction(value));
	}
}

package org.sodeja.silan.compiler.src;

import java.util.List;

import org.sodeja.silan.compiler.Compiler;
import org.sodeja.silan.instruction.Instruction;

public class NestedExpression implements Primary {
	public final Statement statement;

	public NestedExpression(Statement statement) {
		this.statement = statement;
	}

	@Override
	public void compile(Compiler compiler, ExecutableCode codeModel, List<Instruction> instructions) {
		statement.compile(compiler, codeModel, instructions);
	}
}

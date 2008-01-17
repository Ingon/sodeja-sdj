package org.sodeja.silan.compiler.src;

import java.util.List;

import org.sodeja.silan.compiler.Compiler;
import org.sodeja.silan.instruction.Instruction;
import org.sodeja.silan.instruction.PopReferenceInstruction;

public class Statement implements Compiling {
	public final String assignment;
	public final Expression expression;
	
	public Statement(String assignment, Expression expression) {
		this.assignment = assignment;
		this.expression = expression;
	}

	@Override
	public void compile(Compiler compiler, ExecutableCode codeModel, List<Instruction> instructions) {
		expression.compile(compiler, codeModel, instructions);
		if(assignment != null) {
			instructions.add(new PopReferenceInstruction(assignment));
		}
	}
}

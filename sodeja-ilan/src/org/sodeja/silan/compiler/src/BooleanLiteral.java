package org.sodeja.silan.compiler.src;

import java.util.List;

import org.sodeja.silan.compiler.Compiler;
import org.sodeja.silan.instruction.Instruction;
import org.sodeja.silan.instruction.PushBooleanLiteralInstruction;

public class BooleanLiteral implements Literal, LiteralElement {
	public final Boolean value;
	
	public BooleanLiteral(String val) {
		this.value = Boolean.valueOf(val);
	}

	@Override
	public void compile(Compiler compiler, ExecutableCode codeModel, List<Instruction> instructions) {
		instructions.add(new PushBooleanLiteralInstruction(value));
	}
}

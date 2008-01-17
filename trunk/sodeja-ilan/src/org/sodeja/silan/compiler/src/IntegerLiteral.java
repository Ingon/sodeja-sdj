package org.sodeja.silan.compiler.src;

import java.util.List;

import org.sodeja.silan.compiler.Compiler;
import org.sodeja.silan.instruction.Instruction;
import org.sodeja.silan.instruction.PushIntegerLiteralInstruction;

public class IntegerLiteral implements Literal, LiteralElement {
	public final Integer value;

	public IntegerLiteral(String strValue) {
		this.value = new Integer(strValue);
	}

	@Override
	public void compile(Compiler compiler, ExecutableCode codeModel, List<Instruction> instructions) {
		instructions.add(new PushIntegerLiteralInstruction(value));
	}
}

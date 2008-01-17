package org.sodeja.silan.compiler.src;

import java.util.List;

import org.sodeja.silan.compiler.Compiler;
import org.sodeja.silan.instruction.Instruction;
import org.sodeja.silan.instruction.PushNilLiteralInstruction;

public class NilLiteral implements Literal, LiteralElement {
	
	private static final PushNilLiteralInstruction INSTRUCTION = new PushNilLiteralInstruction();
	
	// TODO make it unique
	public NilLiteral(String str) {
	}

	@Override
	public void compile(Compiler compiler, ExecutableCode codeModel, List<Instruction> instructions) {
		instructions.add(INSTRUCTION);
	}
}

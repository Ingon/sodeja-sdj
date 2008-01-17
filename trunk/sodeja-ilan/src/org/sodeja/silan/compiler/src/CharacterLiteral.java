package org.sodeja.silan.compiler.src;

import java.util.List;

import org.sodeja.silan.compiler.Compiler;
import org.sodeja.silan.instruction.Instruction;
import org.sodeja.silan.instruction.PushCharacterLiteralInstruction;

public class CharacterLiteral implements Literal, LiteralElement {
	public final Character value;
	
	public CharacterLiteral(String str) {
		this.value = str.charAt(1);
	}

	@Override
	public void compile(Compiler compiler, ExecutableCode codeModel, List<Instruction> instructions) {
		instructions.add(new PushCharacterLiteralInstruction(value));
	}
}

package org.sodeja.silan.compiler.src;

import java.util.List;

import org.sodeja.silan.compiler.Compiler;
import org.sodeja.silan.instruction.Instruction;

public class CharacterLiteral implements Literal {
	public final Character value;
	
	public CharacterLiteral(String str) {
		this.value = str.charAt(1);
	}

	@Override
	public void compile(Compiler compiler, ExecutableCode codeModel, List<Instruction> instructions) {
		throw new UnsupportedOperationException();
	}
}

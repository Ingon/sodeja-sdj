package org.sodeja.silan.compiler.src;

import java.util.List;

import org.sodeja.silan.compiler.Compiler;
import org.sodeja.silan.instruction.Instruction;
import org.sodeja.silan.instruction.NewArrayInstruction;

public class ObjectArrayLiteral implements ArrayLiteral {
	public final List<LiteralElement> elements;

	public ObjectArrayLiteral(List<LiteralElement> elements) {
		this.elements = elements;
	}

	@Override
	public void compile(Compiler compiler, ExecutableCode codeModel, List<Instruction> instructions) {
		for(LiteralElement element : elements) {
			element.compile(compiler, codeModel, instructions);
		}
		
		instructions.add(new NewArrayInstruction(elements.size()));
	}
}

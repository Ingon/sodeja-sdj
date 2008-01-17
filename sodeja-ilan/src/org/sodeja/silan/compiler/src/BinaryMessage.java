package org.sodeja.silan.compiler.src;

import java.util.List;

import org.sodeja.silan.compiler.Compiler;
import org.sodeja.silan.instruction.Instruction;
import org.sodeja.silan.instruction.MessageInstruction;

public class BinaryMessage extends AbstractMessage {
	public final String selector;
	public final BinaryMessageOperand operand;
	
	public BinaryMessage(String selector, BinaryMessageOperand operand) {
		this.selector = selector;
		this.operand = operand;
	}

	@Override
	public void compile(Compiler compiler, ExecutableCode codeModel, List<Instruction> instructions) {
		operand.primary.compile(compiler, codeModel, instructions);
		compileUnaryChain(compiler, codeModel, instructions, operand.unaries);
		instructions.add(new MessageInstruction(selector, 1));
	}
}

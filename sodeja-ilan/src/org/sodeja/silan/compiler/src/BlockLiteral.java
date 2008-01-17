package org.sodeja.silan.compiler.src;

import java.util.Collections;
import java.util.List;

import org.sodeja.silan.CompiledBlock;
import org.sodeja.silan.CompiledCode;
import org.sodeja.silan.compiler.CompileTargetType;
import org.sodeja.silan.compiler.Compiler;
import org.sodeja.silan.instruction.Instruction;
import org.sodeja.silan.instruction.PushBlockInstruction;

public class BlockLiteral implements Literal {
	public final List<String> argument;
	public final ExecutableCode code;
	
	public BlockLiteral(List<String> argument, ExecutableCode code) {
		if(argument == null) {
			argument = Collections.emptyList();
		}
		this.argument = argument;
		this.code = code;
	}

	@Override
	public void compile(Compiler compiler, ExecutableCode codeModel, List<Instruction> instructions) {
		CompiledCode code = compiler.compileCode(this.code, CompileTargetType.BLOCK);
		CompiledBlock block = new CompiledBlock(argument, 
				code.localVariables, code.maxStackSize, code.instructions);
		instructions.add(new PushBlockInstruction(block));
	}
}

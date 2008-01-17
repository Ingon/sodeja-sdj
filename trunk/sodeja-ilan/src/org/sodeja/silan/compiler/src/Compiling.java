package org.sodeja.silan.compiler.src;

import java.util.List;

import org.sodeja.silan.compiler.Compiler;
import org.sodeja.silan.instruction.Instruction;

public interface Compiling {
	public void compile(Compiler compiler, ExecutableCode codeModel, List<Instruction> instructions);
}

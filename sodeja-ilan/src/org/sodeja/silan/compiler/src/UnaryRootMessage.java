package org.sodeja.silan.compiler.src;

import java.util.List;

import org.sodeja.silan.compiler.Compiler;
import org.sodeja.silan.instruction.Instruction;

public class UnaryRootMessage extends AbstractMessage {
	public final List<UnaryMessage> unaries;
	public final List<BinaryMessage> binaries;
	public final KeywordMessage keyword;
	
	public UnaryRootMessage(List<UnaryMessage> unaries, List<BinaryMessage> binaries, KeywordMessage keyword) {
		this.unaries = unaries;
		this.binaries = binaries;
		this.keyword = keyword;
	}

	@Override
	public void compile(Compiler compiler, ExecutableCode codeModel, List<Instruction> instructions) {
		compileUnaryChain(compiler, codeModel, instructions, unaries);
		compileBinaryChain(compiler, codeModel, instructions, binaries);
		compileKeyword(compiler, codeModel, instructions, keyword);
	}
}

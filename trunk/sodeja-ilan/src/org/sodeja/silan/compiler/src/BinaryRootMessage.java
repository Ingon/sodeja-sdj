package org.sodeja.silan.compiler.src;

import java.util.List;

import org.sodeja.silan.compiler.Compiler;
import org.sodeja.silan.instruction.Instruction;

public class BinaryRootMessage extends AbstractMessage {
	public final List<BinaryMessage> binaries;
	public final KeywordMessage keyword;
	
	public BinaryRootMessage(List<BinaryMessage> binaries, KeywordMessage keyword) {
		this.binaries = binaries;
		this.keyword = keyword;
	}

	@Override
	public void compile(Compiler compiler, ExecutableCode codeModel,
			List<Instruction> instructions) {
		
		compileBinaryChain(compiler, codeModel, instructions, binaries);
		compileKeyword(compiler, codeModel, instructions, keyword);
	}
}

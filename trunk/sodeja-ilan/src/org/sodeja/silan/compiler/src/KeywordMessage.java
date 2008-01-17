package org.sodeja.silan.compiler.src;

import java.util.List;

import org.sodeja.functional.Function1;
import org.sodeja.lang.StringUtils;
import org.sodeja.silan.compiler.Compiler;
import org.sodeja.silan.instruction.Instruction;

public class KeywordMessage extends AbstractMessage {
	public final String selector;
	public final List<KeywordMessageArgument> arguments;
	
	public KeywordMessage(List<KeywordMessageArgument> arguments) {
		this.arguments = arguments;
		this.selector = StringUtils.appendWithSeparator(arguments, "", new Function1<String, KeywordMessageArgument>() {
			@Override
			public String execute(KeywordMessageArgument p) {
				return p.keyword;
			}});
	}

	@Override
	public void compile(Compiler compiler, ExecutableCode codeModel, List<Instruction> instructions) {
		compileKeyword(compiler, codeModel, instructions, this);
	}
}

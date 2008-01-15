package org.sodeja.silan.compiler.src;

import java.util.List;

public class BlockLiteral implements Literal {
	public final List<String> argument;
	public final ExecutableCode code;
	
	public BlockLiteral(List<String> argument, ExecutableCode code) {
		this.argument = argument;
		this.code = code;
	}
}

package org.sodeja.silan.compiler.src;

import java.util.Collections;
import java.util.List;

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
}

package org.sodeja.sil.compiler.model.literal;

import java.util.List;

import org.sodeja.sil.compiler.model.BlockArgument;
import org.sodeja.sil.compiler.model.ExecutableCode;
import org.sodeja.sil.compiler.model.Literal;

public class BlockLiteral implements Literal {
	public final List<BlockArgument> arguments;
	public final ExecutableCode code;
	
	public BlockLiteral(List<BlockArgument> arguments, ExecutableCode code) {
		this.arguments = arguments;
		this.code = code;
	}
}

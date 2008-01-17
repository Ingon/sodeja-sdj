package org.sodeja.silan.compiler.src;

import java.util.List;

import org.sodeja.collections.CollectionUtils;

public class ExecutableCode {
	public final List<String> localVariables;
	public final List<Statement> statements;
	public final FinalStatement finalStatement;
	
	public ExecutableCode(List<String> localVariables,
			List<Statement> statements, FinalStatement finalStatement) {
		this.localVariables = localVariables;
		this.statements = statements;
		this.finalStatement = finalStatement;
		
		if(CollectionUtils.isEmpty(statements) && finalStatement == null) {
			throw new RuntimeException("Compilation failed");
		}
	}
}

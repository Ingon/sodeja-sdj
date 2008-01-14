package org.sodeja.silan.compiler;

import java.util.List;

public class ExecutableCode {
	public final List<String> localVariables;
	public final List<Statement> statements;
	public final Statement finalStatement;
	
	public ExecutableCode(List<String> localVariables,
			List<Statement> statements, Statement finalStatement) {
		this.localVariables = localVariables;
		this.statements = statements;
		this.finalStatement = finalStatement;
	}
}

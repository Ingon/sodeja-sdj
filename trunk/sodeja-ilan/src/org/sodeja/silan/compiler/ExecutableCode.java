package org.sodeja.silan.compiler;

import java.util.List;

public class ExecutableCode {
	public final List<String> localVariables;
	public final List<Statement> statements;
	
	public ExecutableCode(List<String> localVariables, List<Statement> statements) {
		this.localVariables = localVariables;
		this.statements = statements;
	}
}

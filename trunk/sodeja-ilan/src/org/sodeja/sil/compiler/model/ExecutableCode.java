package org.sodeja.sil.compiler.model;

import java.util.List;

public class ExecutableCode {
	public final List<Identifier> localVariables;
	public final List<Statement> statements;
	
	public ExecutableCode(List<Identifier> localVariables, List<Statement> statements) {
		this.localVariables = localVariables;
		this.statements = statements;
	}
}

package org.sodeja.silan.compiler;

import java.util.ArrayList;
import java.util.List;

public class ExecutableCode {
	public final List<String> localVariables;
	public final List<Statement> statements;
	
	public ExecutableCode(List<String> localVariables, List<Statement> statements) {
		this.localVariables = localVariables;
		this.statements = statements;
	}

	public ExecutableCode(List<Statement> statements) {
		this.localVariables = new ArrayList<String>();
		this.statements = statements;
	}
	
	public ExecutableCode(Statement statement) {
		this.localVariables = new ArrayList<String>();
		this.statements = new ArrayList<Statement>();
		this.statements.add(statement);
	}
}

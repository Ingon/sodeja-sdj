package org.sodeja.sil.compiler.model;

public class ReturnStatement implements Statement {
	public final DefaultStatement statement;

	public ReturnStatement(DefaultStatement statement) {
		this.statement = statement;
	}
}

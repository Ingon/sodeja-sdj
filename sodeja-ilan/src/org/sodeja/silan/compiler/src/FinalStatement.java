package org.sodeja.silan.compiler.src;

public class FinalStatement extends Statement {
	public final String token;
	
	public FinalStatement(String token, Statement internal) {
		super(internal.assignment, internal.expression);
		this.token = token;
	}
	
	public boolean isExplicit() {
		return token != null;
	}
}

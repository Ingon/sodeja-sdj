package org.sodeja.sdj.expression;

public class Constructor extends Expression {
	public final Integer tag;
	public final Integer arity;
	
	public Constructor(final Integer tag, final Integer arity) {
		this.tag = tag;
		this.arity = arity;
	}
}
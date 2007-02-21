package org.sodeja.sdj.expression;

public class Application<T> extends Expression<T> {
	public final Expression<T> left;
	public final Expression<T> right;
	
	public Application(final Expression<T> left, final Expression<T> right) {
		this.left = left;
		this.right = right;
	}
}
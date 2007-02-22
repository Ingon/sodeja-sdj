package org.sodeja.sdj.expression;

public class Operator<T> extends Expression<T> {
	public final BinaryOperator operator;
	public final Expression<T> first;
	public final Expression<T> second;
	
	public Operator(final BinaryOperator operator, final Expression<T> first, final Expression<T> second) {
		this.operator = operator;
		this.first = first;
		this.second = second;
	}
}

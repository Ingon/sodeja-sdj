package org.sodeja.sdj.expression;

import java.util.List;

public class Lambda<T> extends Expression<T> {
	public final List<Variable<T>> bindings; // TODO is name ok ?
	public final Expression<T> lambda;
	
	public Lambda(final List<Variable<T>> bindings, final Expression<T> lambda) {
		this.bindings = bindings;
		this.lambda = lambda;
	}		
}
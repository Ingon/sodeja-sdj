package org.sodeja.sdj.expression;

import java.util.List;

public class Supercombinator<T> {
	public final Name name;
	public final List<T> bindings;
	public final Expression<T> expression;
	
	public Supercombinator(final Name name, final List<T> bindings, final Expression<T> expression) {
		this.name = name;
		this.bindings = bindings;
		this.expression = expression;
	}
}

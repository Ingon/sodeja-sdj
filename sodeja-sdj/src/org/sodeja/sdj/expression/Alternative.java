package org.sodeja.sdj.expression;

import java.util.List;

public class Alternative<T> {
	public final Number<T> id;
	public final List<Variable<T>> bindings;
	public final Expression<T> expression;
	
	public Alternative(final Number<T> id, final List<Variable<T>> bindings, final Expression<T> expression) {
		this.id = id;
		this.bindings = bindings;
		this.expression = expression;
	}
}

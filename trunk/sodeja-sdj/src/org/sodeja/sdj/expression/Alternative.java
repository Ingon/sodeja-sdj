package org.sodeja.sdj.expression;

import java.util.List;

public class Alternative<T> {
	public final Integer id;
	public final List<T> bindings;
	public final Expression<T> expression;
	
	public Alternative(final Integer id, final List<T> bindings, final Expression<T> expression) {
		this.id = id;
		this.bindings = bindings;
		this.expression = expression;
	}
}

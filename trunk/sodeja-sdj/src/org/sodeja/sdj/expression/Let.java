package org.sodeja.sdj.expression;

import java.util.Collection;
import java.util.Map;

public class Let<T> extends Expression<T> {
	public final Boolean isRec;
	public final Map<T, Expression<T>> definitions;
	public final Expression<T> body;
	
	public Let(final Boolean isRec, final Map<T, Expression<T>> definitions, final Expression<T> body) {
		this.isRec = isRec;
		this.definitions = definitions;
		this.body = body;
	}

	public Collection<T> binders() {
		return definitions.keySet();
	}
	
	public Collection<Expression<T>> expressions() {
		return definitions.values();
	}
}
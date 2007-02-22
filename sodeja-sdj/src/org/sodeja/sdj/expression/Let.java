package org.sodeja.sdj.expression;

import java.util.Collection;
import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;

public class Let<T> extends Expression<T> {
	public final Boolean isRec;
	public final List<Definition<T>> definitions;
	public final Expression<T> body;
	
	public Let(final Boolean isRec, final List<Definition<T>> definitions, final Expression<T> body) {
		this.isRec = isRec;
		this.definitions = definitions;
		this.body = body;
	}

	public Collection<Variable<T>> binders() {
		return ListUtils.map(definitions, new Function1<Variable<T>, Definition<T>>() {
			public Variable<T> execute(Definition<T> p) {
				return p.first;
			}});
	}
	
	public Collection<Expression<T>> expressions() {
		return ListUtils.map(definitions, new Function1<Expression<T>, Definition<T>>() {
			public Expression<T> execute(Definition<T> p) {
				return p.second;
			}});
	}
}
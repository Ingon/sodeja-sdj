package org.sodeja.sdj.expression;

import java.util.List;

public class Case<T> extends Expression<T> {
	public final Expression<T> scrutinise;
	public final List<Alternative<T>> alternatives;
	
	public Case(Expression<T> scrutinise, List<Alternative<T>> alternatives) {
		this.scrutinise = scrutinise;
		this.alternatives = alternatives;
	}
}
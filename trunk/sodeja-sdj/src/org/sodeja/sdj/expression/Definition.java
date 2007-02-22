package org.sodeja.sdj.expression;

import org.sodeja.functional.Pair;

public class Definition<T> extends Pair<Variable<T>, Expression<T>>{
	public Definition(Variable<T> first, Expression<T> second) {
		super(first, second);
	}
}

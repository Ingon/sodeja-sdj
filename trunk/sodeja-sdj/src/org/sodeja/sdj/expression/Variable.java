package org.sodeja.sdj.expression;

public class Variable<T> extends Expression<T> {
	public final Name name;

	public Variable(Name name) {
		this.name = name;
	}
	
	@Override
	public boolean isAtomic() {
		return true;
	}
}
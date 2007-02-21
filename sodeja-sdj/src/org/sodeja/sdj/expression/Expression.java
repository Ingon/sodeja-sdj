package org.sodeja.sdj.expression;

public abstract class Expression<T> {
	Expression() {
	}
	
	public boolean isAtomic() {
		return false;
	}
}

package org.sodeja.sdj.expression;

public class Number<T> extends Expression<T> {
	public final Integer value;

	public Number(Integer value) {
		this.value = value;
	}
	
	@Override
	public boolean isAtomic() {
		return true;
	}
}
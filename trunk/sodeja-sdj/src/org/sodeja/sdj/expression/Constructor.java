package org.sodeja.sdj.expression;

public class Constructor<T> extends Expression<T> {
	public final Number<T> tag;
	public final Number<T> arity;
	
	public Constructor(final Number<T> tag, final Number<T> arity) {
		this.tag = tag;
		this.arity = arity;
	}

	@Override
	public boolean isAtomic() {
		return true;
	}
}
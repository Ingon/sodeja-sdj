package org.sodeja.il.runtime;

public interface Context {
	public void define(ILSymbol symbol, ILObject value);
	public boolean exists(ILSymbol symbol);
	public ILObject get(ILSymbol symbol);
}

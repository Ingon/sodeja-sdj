package org.sodeja.il.runtime;

public interface Context {
	public void define(ILSymbol name, ILObject value);
	public ILObject get(ILSymbol name);
	public RootContext getRoot();
}

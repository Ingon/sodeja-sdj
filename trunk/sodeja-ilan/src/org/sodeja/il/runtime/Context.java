package org.sodeja.il.runtime;

import org.sodeja.il.sdk.ILObject;
import org.sodeja.il.sdk.ILSymbol;

public interface Context {
	public void define(ILSymbol symbol, ILObject value);
	public boolean exists(ILSymbol symbol);
	public ILObject get(ILSymbol symbol);
}

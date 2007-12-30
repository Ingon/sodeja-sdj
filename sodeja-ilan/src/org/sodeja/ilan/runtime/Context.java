package org.sodeja.ilan.runtime;

import org.sodeja.ilan.ildk.ILObject;
import org.sodeja.ilan.ildk.ILSymbol;

public interface Context {

	public ILObject get(ILSymbol symbol);

	public void def(ILSymbol symbol, ILObject value);

	public Process getProcess();
}
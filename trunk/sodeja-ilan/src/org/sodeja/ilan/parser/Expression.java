package org.sodeja.ilan.parser;

import org.sodeja.ilan.ildk.ILObject;
import org.sodeja.ilan.runtime.IContext;

public interface Expression {
	public ILObject eval(IContext context);
}

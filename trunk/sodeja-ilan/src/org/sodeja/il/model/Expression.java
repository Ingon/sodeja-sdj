package org.sodeja.il.model;

import org.sodeja.il.runtime.Context;
import org.sodeja.il.runtime.ILObject;

public interface Expression {
	public ILObject eval(Context ctx);
}

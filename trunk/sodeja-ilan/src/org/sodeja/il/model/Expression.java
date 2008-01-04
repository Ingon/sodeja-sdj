package org.sodeja.il.model;

import org.sodeja.il.runtime.Context;
import org.sodeja.il.sdk.ILObject;

public interface Expression {
	public ILObject eval(Context ctx);
}

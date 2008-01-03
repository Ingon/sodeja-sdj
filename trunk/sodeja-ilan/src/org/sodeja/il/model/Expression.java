package org.sodeja.il.model;

import org.sodeja.il.runtime.Context;

public interface Expression {
	public Object eval(Context ctx);
}

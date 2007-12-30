package org.sodeja.ilan.parser;

import org.sodeja.ilan.ildk.ILObject;
import org.sodeja.ilan.runtime.Context;

public interface Expression {
	public ILObject eval(Context context);
}

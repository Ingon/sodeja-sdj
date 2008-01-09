package org.sodeja.sil.sdk;

import org.sodeja.sil.runtime.SILObject;
import org.sodeja.sil.runtime.context.Context;

public interface PrimitiveFunction {
	public SILObject perform(Context ctx);
}

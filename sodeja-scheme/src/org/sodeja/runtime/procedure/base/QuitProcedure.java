package org.sodeja.runtime.procedure.base;

import org.sodeja.scheme.execute.primitive.PrimitiveProcedure;

public class QuitProcedure implements PrimitiveProcedure {
	@Override
	public Object apply(Object... vals) {
		System.exit(0);
		return null;
	}
}

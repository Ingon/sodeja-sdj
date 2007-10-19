package org.sodeja.runtime.procedure.base;

import org.sodeja.runtime.Procedure;

public class QuitProcedure implements Procedure {
	@Override
	public Object apply(Object... vals) {
		System.exit(0);
		return null;
	}
}

package org.sodeja.scheme.execute.primitive;

public class QuitProcedure implements PrimitiveProcedure {
	@Override
	public Object execute(Object... vals) {
		System.exit(0);
		return null;
	}
}

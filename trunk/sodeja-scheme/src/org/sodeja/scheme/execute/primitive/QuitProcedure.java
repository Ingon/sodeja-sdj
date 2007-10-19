package org.sodeja.scheme.execute.primitive;

public class QuitProcedure implements PrimitiveProcedure {
	@Override
	public Object apply(Object... vals) {
		System.exit(0);
		return null;
	}
}

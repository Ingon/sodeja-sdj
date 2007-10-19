package org.sodeja.scheme.execute.primitive;

public class EqProcedure implements PrimitiveProcedure {
	@Override
	public Object apply(Object... vals) {
		if(vals.length != 2) {
			throw new IllegalArgumentException("Wrong number of arguments");
		}
		return vals[0].equals(vals[1]);
	}
}

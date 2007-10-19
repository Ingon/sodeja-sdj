package org.sodeja.runtime.scheme.procedure.logical;

import org.sodeja.runtime.Procedure;

public class EqProcedure implements Procedure {
	@Override
	public Object apply(Object... vals) {
		if(vals.length != 2) {
			throw new IllegalArgumentException("Wrong number of arguments");
		}
		return vals[0].equals(vals[1]);
	}
}

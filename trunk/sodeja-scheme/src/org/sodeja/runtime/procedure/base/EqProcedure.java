package org.sodeja.runtime.procedure.base;

import org.sodeja.runtime.Procedure;

public class EqProcedure implements Procedure {
	@Override
	public Object apply(Object... values) {
		if(values.length != 2) {
			throw new IllegalArgumentException("Wrong number of arguments");
		}
		return values[0].equals(values[1]);
	}
}

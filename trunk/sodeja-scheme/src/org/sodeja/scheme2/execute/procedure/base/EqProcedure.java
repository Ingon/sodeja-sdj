package org.sodeja.scheme2.execute.procedure.base;

import org.sodeja.scheme2.execute.Procedure;

public class EqProcedure implements Procedure {
	@Override
	public Object apply(Object... values) {
		if(values.length != 2) {
			throw new IllegalArgumentException("Wrong number of arguments");
		}
		return values[0].equals(values[1]);
	}
}

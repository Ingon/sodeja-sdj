package org.sodeja.scheme2.execute.procedure.logical;

import org.sodeja.runtime.Procedure;

public class NotProcedure implements Procedure {
	@Override
	public Object apply(Object... values) {
		if(values.length != 1) {
			throw new IllegalArgumentException("Only one value is expected");
		}
		
		Object val = (Object) values[0];
		if(! (val instanceof Boolean)) {
			throw new IllegalArgumentException("Value of a boolean type is expected");
		}
		
		return ! ((Boolean) val).booleanValue();
	}
}

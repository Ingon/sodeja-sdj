package org.sodeja.scheme.execute.primitive.logical;

import org.sodeja.scheme.execute.primitive.PrimitiveProcedure;

public class NotProcedure implements PrimitiveProcedure {
	@Override
	public Object execute(Object... values) {
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

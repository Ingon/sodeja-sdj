package org.sodeja.scheme2.execute.procedure.pair;

import org.sodeja.functional.Pair;
import org.sodeja.runtime.Procedure;

public class CarProcedure implements Procedure {
	@SuppressWarnings("unchecked")
	@Override
	public Object apply(Object... values) {
		return ((Pair) values[0]).first;
	}
}

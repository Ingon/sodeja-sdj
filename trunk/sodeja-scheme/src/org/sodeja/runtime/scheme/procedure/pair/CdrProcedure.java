package org.sodeja.runtime.scheme.procedure.pair;

import org.sodeja.functional.Pair;
import org.sodeja.runtime.Procedure;

public class CdrProcedure implements Procedure {
	@SuppressWarnings("unchecked")
	@Override
	public Object apply(Object... values) {
		return ((Pair) values[0]).second;
	}
}

package org.sodeja.scheme.execute.primitive.pair;

import org.sodeja.functional.Pair;
import org.sodeja.scheme.execute.primitive.PrimitiveProcedure;

public class CdrProcedure implements PrimitiveProcedure {
	@SuppressWarnings("unchecked")
	@Override
	public Object execute(Object... vals) {
		return ((Pair) vals[0]).second;
	}
}

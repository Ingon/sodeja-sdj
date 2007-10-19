package org.sodeja.scheme.execute.primitive.pair;

import org.sodeja.functional.Pair;
import org.sodeja.scheme.execute.primitive.PrimitiveProcedure;

public class CarProcedure implements PrimitiveProcedure {
	@SuppressWarnings("unchecked")
	@Override
	public Object apply(Object... vals) {
		return ((Pair) vals[0]).first;
	}
}

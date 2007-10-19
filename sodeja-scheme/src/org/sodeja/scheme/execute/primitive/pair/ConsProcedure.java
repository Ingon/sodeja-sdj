package org.sodeja.scheme.execute.primitive.pair;

import org.sodeja.functional.Pair;
import org.sodeja.scheme.execute.primitive.PrimitiveProcedure;

public class ConsProcedure implements PrimitiveProcedure {
	@Override
	public Object apply(Object... vals) {
		return new Pair<Object, Object>(vals[0], vals[1]);
	}
}

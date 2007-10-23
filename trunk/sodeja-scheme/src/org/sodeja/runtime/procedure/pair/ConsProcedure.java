package org.sodeja.runtime.procedure.pair;

import org.sodeja.functional.Pair;
import org.sodeja.runtime.Procedure;

public class ConsProcedure implements Procedure {
	@Override
	public Object apply(Object... values) {
		return Pair.of(values[0], values[1]);
	}
}

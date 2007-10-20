package org.sodeja.runtime.procedure.relational;

import org.sodeja.collections.ArrayUtils;
import org.sodeja.math.Rational;
import org.sodeja.runtime.Procedure;

public abstract class AbstractCompareProcedure implements Procedure {
	@Override
	public Object apply(Object... values) {
		if(ArrayUtils.isEmpty(values) || values.length != 2) {
			throw new IllegalArgumentException("Wrong number of arguments!");
		}

		if(! (values[0] instanceof Rational)) {
			throw new IllegalArgumentException("Wrong value type: " + values[0].getClass());
		}

		if(! (values[1] instanceof Rational)) {
			throw new IllegalArgumentException("Wrong value type: " + values[1].getClass());
		}
		
		Rational first = (Rational) values[0];
		Rational second = (Rational) values[1];
		return compare(first.compareTo(second));
	}
	
	public abstract Boolean compare(int val);
}

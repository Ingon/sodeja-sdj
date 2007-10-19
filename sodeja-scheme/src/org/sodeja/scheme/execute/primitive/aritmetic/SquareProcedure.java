package org.sodeja.scheme.execute.primitive.aritmetic;

import org.sodeja.math.Rational;

public class SquareProcedure extends AbstractAritmeticProcedure {
	@Override
	public Object apply(Object... vals) {
		if(vals.length != 1) {
			throw new IllegalArgumentException("Wrong number of arguments");
		}
		
		Rational val = convert(vals[0]);
		return val.multiply(val);
	}
}

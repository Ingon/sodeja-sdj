package org.sodeja.scheme.execute.primitive.aritmetic;

import org.sodeja.math.Rational;

public class SumProcedure extends AbstractAritmeticProcedure {
	@Override
	public Object apply(Object... vals) {
		Rational result = Rational.ZERO;
		for(Object obj : vals) {
			result = result.add(convert(obj));
		}
		return result;
	}
}

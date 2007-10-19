package org.sodeja.runtime.scheme.procedure.arithmetic;

import org.sodeja.math.Rational;

public class SumProcedure extends AbstractAritmeticProcedure {
	@Override
	public Object apply(Object... values) {
		Rational result = Rational.ZERO;
		for(Object obj : values) {
			result = result.add(convert(obj));
		}
		return result;
	}
}

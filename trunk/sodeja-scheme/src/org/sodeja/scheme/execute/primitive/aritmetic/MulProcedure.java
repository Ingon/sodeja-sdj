package org.sodeja.scheme.execute.primitive.aritmetic;

import org.sodeja.math.Rational;

public class MulProcedure extends AbstractAritmeticProcedure {
	@Override
	public Object apply(Object... vals) {
		Rational result = Rational.ONE;
		for(Object obj : vals) {
			result = result.multiply(convert(obj));
		}
		return result;
	}
}

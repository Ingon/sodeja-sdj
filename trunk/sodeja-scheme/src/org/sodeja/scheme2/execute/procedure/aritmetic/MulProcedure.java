package org.sodeja.scheme2.execute.procedure.aritmetic;

import org.sodeja.math.Rational;

public class MulProcedure extends AbstractAritmeticProcedure {
	@Override
	public Object apply(Object... values) {
		Rational result = Rational.ONE;
		for(Object obj : values) {
			result = result.multiply(convert(obj));
		}
		return result;
	}
}

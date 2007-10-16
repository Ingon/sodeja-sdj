package org.sodeja.scheme.execute.primitive.aritmetic;

import org.sodeja.math.Rational;

public class AverageProcedure extends AbstractAritmeticProcedure {
	private static final Rational TWO = new Rational(2);

	@Override
	public Object execute(Object... vals) {
		Rational x = convert(vals[0]);
		Rational y = convert(vals[1]);
		return x.add(y).divide(TWO);
	}
}

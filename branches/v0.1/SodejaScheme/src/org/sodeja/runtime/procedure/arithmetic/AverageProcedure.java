package org.sodeja.runtime.procedure.arithmetic;

import org.sodeja.math.Rational;

public class AverageProcedure extends AbstractAritmeticProcedure {
	private static final Rational TWO = new Rational(2);

	@Override
	public Object apply(Object... vals) {
		Rational x = convert(vals[0]);
		Rational y = convert(vals[1]);
		return x.add(y).divide(TWO);
	}
}

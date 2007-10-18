package org.sodeja.runtime.scheme.procedure.arithmetic;

import org.sodeja.math.Rational;

public class DivProcedure extends AbstractAritmeticProcedure {
	@Override
	public Object apply(Object... values) {
		Rational div = convert(values[0]);
		for(int i = 1, n = values.length;i < n;i++) {
			div = div.divide(convert(values[i]));
		}
		return div;
	}
}

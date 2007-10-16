package org.sodeja.scheme.execute.primitive.aritmetic;

import org.sodeja.math.Rational;

public class DivProcedure extends AbstractAritmeticProcedure {
	@Override
	public Object execute(Object... vals) {
		Rational div = convert(vals[0]);
		for(int i = 1, n = vals.length;i < n;i++) {
			div = div.divide(convert(vals[i]));
		}
		return div;
	}
}

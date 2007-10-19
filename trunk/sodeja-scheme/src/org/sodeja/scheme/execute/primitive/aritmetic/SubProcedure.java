package org.sodeja.scheme.execute.primitive.aritmetic;

import org.sodeja.collections.ArrayUtils;
import org.sodeja.math.Rational;

public class SubProcedure extends AbstractAritmeticProcedure {
	@Override
	public Object apply(Object... vals) {
		if(ArrayUtils.isEmpty(vals)) {
			throw new IllegalArgumentException("Should provide at least one param!");
		}
				
		Rational sub = convert(vals[0]);
		if(vals.length == 1) {
			return sub.negate();
		}
		
		for(int i = 1, n = vals.length;i < n;i++) {
			sub = sub.substract(convert(vals[i]));
		}
		
		return sub;
	}
}

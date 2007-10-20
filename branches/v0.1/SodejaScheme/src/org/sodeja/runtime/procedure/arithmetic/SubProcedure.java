package org.sodeja.runtime.procedure.arithmetic;

import org.sodeja.collections.ArrayUtils;
import org.sodeja.math.Rational;

public class SubProcedure extends AbstractAritmeticProcedure {
	@Override
	public Object apply(Object... values) {
		if(ArrayUtils.isEmpty(values)) {
			throw new IllegalArgumentException("Should provide at least one param!");
		}
				
		Rational sub = convert(values[0]);
		if(values.length == 1) {
			return sub.negate();
		}
		
		for(int i = 1, n = values.length;i < n;i++) {
			sub = sub.substract(convert(values[i]));
		}
		
		return sub;
	}
}

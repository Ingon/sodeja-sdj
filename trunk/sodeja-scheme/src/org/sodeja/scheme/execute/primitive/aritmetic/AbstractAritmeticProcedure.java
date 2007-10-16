package org.sodeja.scheme.execute.primitive.aritmetic;

import org.sodeja.math.Rational;
import org.sodeja.scheme.execute.primitive.PrimitiveProcedure;

public abstract class AbstractAritmeticProcedure implements PrimitiveProcedure {
	protected Rational convert(Object obj) {
		if(obj instanceof Rational) {
			return (Rational) obj;
		}
		
		throw new IllegalArgumentException("Wrong value type: " + obj.getClass());
	}
}

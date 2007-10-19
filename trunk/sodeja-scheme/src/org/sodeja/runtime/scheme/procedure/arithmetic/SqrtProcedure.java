package org.sodeja.runtime.scheme.procedure.arithmetic;

public class SqrtProcedure extends AbstractAritmeticProcedure {
	@Override
	public Object apply(Object... vals) {
		if(vals.length != 1) {
			throw new IllegalArgumentException("Wrong number of arguments");
		}
		
		return convert(vals[0]).sqrt();
	}
}

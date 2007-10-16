package org.sodeja.scheme.execute.primitive.aritmetic;

public class SqrtProcedure extends AbstractAritmeticProcedure {
	@Override
	public Object execute(Object... vals) {
		if(vals.length != 1) {
			throw new IllegalArgumentException("Wrong number of arguments");
		}
		
		return convert(vals[0]).sqrt();
	}
}

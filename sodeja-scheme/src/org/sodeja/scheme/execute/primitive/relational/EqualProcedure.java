package org.sodeja.scheme.execute.primitive.relational;

public class EqualProcedure extends AbstractCompareProcedure {
	@Override
	public Boolean compare(int val) {
		return val == 0;
	}
}

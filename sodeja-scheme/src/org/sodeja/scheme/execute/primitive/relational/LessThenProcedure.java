package org.sodeja.scheme.execute.primitive.relational;

public class LessThenProcedure extends AbstractCompareProcedure {
	@Override
	public Boolean compare(int val) {
		return val == -1;
	}
}

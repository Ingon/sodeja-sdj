package org.sodeja.scheme2.execute.procedure.relational;

public class BiggerThenProcedure extends AbstractCompareProcedure {
	@Override
	public Boolean compare(int val) {
		return val == 1;
	}
}

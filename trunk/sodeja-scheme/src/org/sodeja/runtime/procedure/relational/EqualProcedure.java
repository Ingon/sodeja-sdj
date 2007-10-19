package org.sodeja.runtime.procedure.relational;

public class EqualProcedure extends AbstractCompareProcedure {
	@Override
	public Boolean compare(int val) {
		return val == 0;
	}
}

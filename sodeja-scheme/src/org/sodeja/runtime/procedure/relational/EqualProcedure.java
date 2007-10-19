package org.sodeja.runtime.scheme.procedure.relational;

public class EqualProcedure extends AbstractCompareProcedure {
	@Override
	public Boolean compare(int val) {
		return val == 0;
	}
}

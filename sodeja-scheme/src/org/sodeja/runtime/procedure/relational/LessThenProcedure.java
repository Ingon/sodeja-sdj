package org.sodeja.runtime.procedure.relational;

public class LessThenProcedure extends AbstractCompareProcedure {
	@Override
	public Boolean compare(int val) {
		return val == -1;
	}
}

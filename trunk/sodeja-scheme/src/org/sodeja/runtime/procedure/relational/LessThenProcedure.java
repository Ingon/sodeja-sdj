package org.sodeja.runtime.scheme.procedure.relational;

public class LessThenProcedure extends AbstractCompareProcedure {
	@Override
	public Boolean compare(int val) {
		return val == -1;
	}
}

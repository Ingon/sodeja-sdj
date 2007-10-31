package org.sodeja.explicit2;

class Rational implements CompiledExpression {
	
	private final org.sodeja.math.Rational value;
	
	public Rational(org.sodeja.math.Rational value) {
		this.value = value;
	}
	
	@Override
	public void eval(Machine machine) {
		machine.val.setValue(value);
		machine.exp.setValue(machine.cont.getValue());
	}

	@Override
	public String toString() {
		return value.toString();
	}
}

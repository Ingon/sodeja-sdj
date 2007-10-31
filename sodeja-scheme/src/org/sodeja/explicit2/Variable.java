package org.sodeja.explicit2;

class Variable implements CompiledExpression {
	private final Reference ref;
	
	public Variable(Reference ref) {
		this.ref = ref;
	}

	@Override
	public void eval(Machine machine) {
		LexicalEnviroment env = machine.env.getValue();
		machine.val.setValue(ref.resolveValue(machine.dynamic, env));
		machine.exp.setValue(machine.cont.getValue());
	}

	@Override
	public String toString() {
		return ref.toString();
	}
}

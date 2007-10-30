package org.sodeja.explicit2;

class ApplyOperandLast implements CompiledExpression {
	
	public static final ApplyOperandLast instance = new ApplyOperandLast();
	
	private ApplyOperandLast() {
	}
	
	@Override
	public void eval(Machine machine) {
		machine.argl.restore();
		
		Object value = machine.val.getValue();
		machine.argl.getValue().add(value);

		machine.proc.restore();
		
		machine.exp.setValue(Apply.instance);
	}
}

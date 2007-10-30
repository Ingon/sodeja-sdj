package org.sodeja.explicit2;

import java.util.List;

class ApplyOperandPost implements CompiledExpression {
	
	public static final ApplyOperandPost instance = new ApplyOperandPost();
	
	private ApplyOperandPost() {
	}
	
	@Override
	public void eval(Machine machine) {
		machine.unev.restore();
		machine.env.restore();
		machine.argl.restore();
		
		Object value = machine.val.getValue();
		machine.argl.getValue().add(value);
		
		List<CompiledExpression> left = machine.unev.getValue();
		machine.unev.setValue(left.subList(1, left.size()));
		
		machine.exp.setValue(ApplyOperand.instance);
	}
}

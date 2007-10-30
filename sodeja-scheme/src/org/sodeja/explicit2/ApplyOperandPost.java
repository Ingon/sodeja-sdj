package org.sodeja.explicit2;

import org.sodeja.collections.ConsList;

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
		
		ConsList<CompiledExpression> left = (ConsList<CompiledExpression>) machine.unev.getValue();
		machine.unev.setValue(left.tail());
		
		machine.exp.setValue(ApplyOperand.instance);
	}
}

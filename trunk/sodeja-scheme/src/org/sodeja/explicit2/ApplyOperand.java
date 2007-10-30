package org.sodeja.explicit2;

import org.sodeja.collections.ConsList;

class ApplyOperand implements CompiledExpression {
	
	public static final ApplyOperand instance = new ApplyOperand();
	
	private ApplyOperand() {
	}
	
	@Override
	public void eval(Machine machine) {
		machine.argl.save();
		
		ConsList<CompiledExpression> operands = (ConsList<CompiledExpression>) machine.unev.getValue();
		CompiledExpression headOperand = operands.head();
		machine.exp.setValue(headOperand);
		
		if(operands.size() == 1) {
			machine.cont.setValue(ApplyOperandLast.instance);
			return;
		}
		
		machine.env.save();
		machine.unev.save();
		
		machine.cont.setValue(ApplyOperandPost.instance);
	}
}

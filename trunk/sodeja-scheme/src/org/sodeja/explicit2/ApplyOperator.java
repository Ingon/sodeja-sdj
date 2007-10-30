package org.sodeja.explicit2;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.runtime.Procedure;

class ApplyOperator implements CompiledExpression {
	
	public static final ApplyOperator instance = new ApplyOperator();
	
	private ApplyOperator() {
	}
	
	@Override
	public void eval(Machine machine) {
		machine.unev.restore();
		machine.env.restore();
		
		machine.argl.setValue(new ArrayList<Object>());
		machine.proc.setValue((Procedure) machine.val.getValue());
		
		List<CompiledExpression> operands = machine.unev.getValue();
		if(operands.isEmpty()) {
			machine.exp.setValue(Apply.instance);
			return;
		}
		
		machine.proc.save();
		machine.exp.setValue(ApplyOperand.instance);
	}
}

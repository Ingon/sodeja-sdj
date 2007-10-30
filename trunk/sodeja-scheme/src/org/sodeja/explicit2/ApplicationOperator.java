package org.sodeja.explicit2;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.runtime.Procedure;

class ApplicationOperator implements CompiledExpression {
	
	public static final ApplicationOperator instance = new ApplicationOperator();
	
	private ApplicationOperator() {
	}
	
	@Override
	public void eval(Machine machine) {
		machine.unev.restore();
		machine.env.restore();
		
		machine.argl.setValue(new ArrayList<Object>());
		machine.proc.setValue((Procedure) machine.val.getValue());
		
		List<CompiledExpression> operands = machine.unev.getValue();
		if(operands.isEmpty()) {
			machine.exp.setValue(ApplyDispatch.instance);
			return;
		}
		
		machine.proc.save();
		machine.exp.setValue(ApplyOperand.instance);
	}
}

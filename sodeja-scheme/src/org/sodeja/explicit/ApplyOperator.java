package org.sodeja.explicit;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.runtime.Procedure;
import org.sodeja.runtime.scheme.SchemeExpression;

class ApplyOperator implements Executable {

	@Override
	public String execute(Machine machine) {
		machine.unev.restore();
		machine.env.restore();
		
		machine.argl.setValue(new ArrayList<Object>());
		machine.proc.setValue((Procedure) machine.val.getValue());
		
		List<SchemeExpression> operands = machine.unev.getValue();
		if(operands.isEmpty()) {
			return "apply-dispatch";
		}
		
		machine.proc.save();
		
		return "ev-appl-operand-loop";
	}

}

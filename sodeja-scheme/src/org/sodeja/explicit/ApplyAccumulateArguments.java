package org.sodeja.explicit;

import java.util.List;

import org.sodeja.runtime.scheme.SchemeExpression;

class ApplyAccumulateArguments implements Executable {
	@Override
	public String execute(Machine machine) {
		machine.unev.restore();
		machine.env.restore();
		machine.argl.restore();
		
		Object value = machine.val.getValue();
		machine.argl.getValue().add(value);
		
		List<SchemeExpression> left = machine.unev.getValue();
		machine.unev.setValue(left.subList(1, left.size()));
		
		return "ev-appl-operand-loop";
	}
}

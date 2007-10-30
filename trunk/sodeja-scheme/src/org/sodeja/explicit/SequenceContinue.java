package org.sodeja.explicit;

import java.util.List;

import org.sodeja.runtime.scheme.SchemeExpression;

class SequenceContinue implements Executable {
	@Override
	public String execute(Machine machine) {
		machine.env.restore();
		machine.unev.restore();
		
		List<SchemeExpression> rest = machine.unev.getValue();
		machine.unev.setValue(rest.subList(1, rest.size()));
		
		return "ev-sequence";
	}
}

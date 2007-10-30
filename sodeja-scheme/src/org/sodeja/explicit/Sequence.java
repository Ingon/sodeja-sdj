package org.sodeja.explicit;

import java.util.List;

import org.sodeja.runtime.scheme.SchemeExpression;

class Sequence implements Executable {
	@Override
	public String execute(Machine machine) {
		List<SchemeExpression> seq = machine.unev.getValue();
		if(seq.isEmpty()) {
			return "ev-sequence-end";
		}
		
		machine.exp.setValue(seq.get(0));
		
		machine.unev.save();
		machine.env.save();
		
		machine.cont.setValue("ev-sequence-continue");
		
		return "eval-dispatch";
	}
}

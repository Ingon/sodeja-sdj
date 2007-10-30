package org.sodeja.explicit;

import java.util.List;

import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.model.Combination;

class Lambda implements Executable {
	@Override
	public String execute(Machine machine) {
		Combination comb = (Combination) machine.exp.getValue();
		
		Combination params = (Combination) comb.get(1);
		List<SchemeExpression> body = comb.subList(2, comb.size());
		
		machine.val.setValue(new CompoundProcedure(machine.env.getValue(), params, body));
		
		return machine.cont.getValue();
	}
}

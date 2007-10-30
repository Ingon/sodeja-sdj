package org.sodeja.explicit;

import org.sodeja.collections.ListUtils;
import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.model.Combination;

public class Assign implements Executable {
	@Override
	public String execute(Machine machine) {
		Combination comb = (Combination) machine.exp.getValue();
		
		SchemeExpression nameExpr = comb.get(1);
		
		machine.unev.setValue(ListUtils.asList(nameExpr));

		machine.exp.setValue(comb.get(2));

		machine.unev.save();
		
		machine.env.save();
		
		machine.cont.save();
		machine.cont.setValue("ev-assignment-1");
		
		return "eval-dispatch";
	}
}

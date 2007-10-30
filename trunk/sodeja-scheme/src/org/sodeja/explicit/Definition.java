package org.sodeja.explicit;

import org.sodeja.collections.ListUtils;
import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.model.Combination;
import org.sodeja.runtime.scheme.model.Symbol;

class Definition implements Executable {
	@Override
	public String execute(Machine machine) {
		Combination comb = (Combination) machine.exp.getValue();
		
		SchemeExpression nameExpr = comb.get(1);
		if(nameExpr instanceof Symbol) {
			machine.unev.setValue(ListUtils.asList(nameExpr));

			machine.exp.setValue(comb.get(2));
		} else if(nameExpr instanceof Combination) {
			Combination nameAndLambdaParams = (Combination) nameExpr;
			Combination lambdaParams = new Combination();
			lambdaParams.addAll(nameAndLambdaParams.subList(1, nameAndLambdaParams.size()));
			
			Combination lambda = new Combination();
			lambda.add(new Symbol("lambda"));
			lambda.add(lambdaParams);
			lambda.addAll(comb.subList(2, comb.size()));
			
			machine.unev.setValue(ListUtils.asList((SchemeExpression) nameAndLambdaParams.get(0)));

			machine.exp.setValue(lambda);
		} else {
			throw new IllegalArgumentException();
		}
		
		machine.unev.save();
		
		machine.env.save();
		
		machine.cont.save();
		machine.cont.setValue("ev-definition-1");
		
		return "eval-dispatch";
	}
}

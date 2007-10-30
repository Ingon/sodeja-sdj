package org.sodeja.explicit;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.math.Rational;
import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.model.Combination;
import org.sodeja.runtime.scheme.model.Symbol;

public class EvalDispatch implements Executable {
	
	private Map<String, String> formToLabel;
	
	public EvalDispatch() {
		formToLabel = new HashMap<String, String>() {
			private static final long serialVersionUID = -1474351439696915009L;
		{
			put("quote", "ev-quoted");
			put("set!", "ev-assignment");
			put("define", "ev-definition");
			put("if", "ev-if");
			put("lambda", "ev-lambda");
			put("begin", "ev-begin");
		}};
	}
	
	@Override
	public String execute(Machine machine) {
		SchemeExpression exp = machine.exp.getValue();
		if(exp instanceof Symbol) {
			return execute(machine, (Symbol) exp);
		}
		
		if(exp instanceof Combination) {
			return execute(machine, (Combination) exp);
		}
		
		throw new IllegalArgumentException();
	}

	private String execute(Machine machine, Symbol sym) {
		String value = sym.value;
		
		try {
//			new Integer(value);
			new Rational(value);
			return "ev-self-eval";
		} catch(NumberFormatException exc) {
			return "ev-variable";
		}
	}

	private String execute(Machine machine, Combination comb) {
		SchemeExpression expr = comb.get(0);
		if(expr instanceof Combination) {
			return "ev-application";
		}
		
		Symbol sym = (Symbol) expr;
		String value = sym.value;
		
		String formLabel = formToLabel.get(value);
		if(formLabel != null) {
			return formLabel;
		}
		
		return "ev-application";
	}
}

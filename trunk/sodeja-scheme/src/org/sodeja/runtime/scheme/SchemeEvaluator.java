package org.sodeja.runtime.scheme;

import org.sodeja.math.Rational;
import org.sodeja.runtime.Dialect;
import org.sodeja.runtime.Frame;
import org.sodeja.runtime.Library;
import org.sodeja.runtime.Procedure;
import org.sodeja.runtime.abs.AbstractEvaluator;
import org.sodeja.runtime.scheme.model.Combination;
import org.sodeja.runtime.scheme.model.Symbol;

public class SchemeEvaluator extends AbstractEvaluator<SchemeExpression> {

	public SchemeEvaluator(Dialect<SchemeExpression> dialect,
			Library<SchemeExpression> library) {
		this(dialect, library, new SchemeFrame());
	}

	public SchemeEvaluator(Dialect<SchemeExpression> dialect,
			Library<SchemeExpression> library, SchemeFrame rootFrame) {
		super(dialect, library, rootFrame);
	}
	
	@Override
	protected boolean isApplication(SchemeExpression expression) {
		return expression instanceof Combination;
	}
	
	@Override
	protected Object apply(Frame<SchemeExpression> frame, SchemeExpression expression) {
		Combination combination = (Combination) expression;
		if(combination.parts.size() == 0) {
			throw new IllegalArgumentException("Application without at least procedure given");
		}
		
		Object procedureObj = eval(frame, combination.get(0));
		if(! (procedureObj instanceof Procedure)) {
			throw new IllegalArgumentException("Only procedures can be applied");
		}
		Procedure procedure = (Procedure) procedureObj;
		
		Object[] params = new Object[combination.size() - 1];
		for(int i = 1, n = combination.size();i < n;i++) {
			params[i - 1] = eval(frame, combination.get(i));
		}
		
		return procedure.apply(params);
	}

	@Override
	protected boolean isPrimitive(SchemeExpression expression) {
		if(! (expression instanceof Symbol)) {
			return false;
		}
		
		String value = ((Symbol) expression).value;		
		try {
			// TODO very bad idea - better to have pattern to check this one!!!
			new Rational(value);
			return true;
		} catch(NumberFormatException exc) {
			return false;
		}
	}

	@Override
	protected Object evalPrimitive(SchemeExpression expression) {
		return new Rational(((Symbol) expression).value);
	}

	@Override
	protected boolean isVariable(SchemeExpression expression) {
		return expression instanceof Symbol;
	}
}

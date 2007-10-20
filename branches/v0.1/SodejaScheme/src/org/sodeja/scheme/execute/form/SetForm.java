package org.sodeja.scheme.execute.form;

import java.util.List;

import org.sodeja.scheme.execute.Frame;
import org.sodeja.scheme.parse.model.Expression;
import org.sodeja.scheme.parse.model.SymbolExpression;

public class SetForm implements Form {
	@Override
	public Object execute(Frame frame, List<Expression> expressions) {
		SymbolExpression varExp = (SymbolExpression) expressions.get(0);
		Object value = frame.eval(expressions.get(1));
		
		// Destruct the current value by adding additional
		frame.setSymbol(varExp.value, value);
		
		return value;
	}
}

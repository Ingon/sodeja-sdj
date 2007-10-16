package org.sodeja.scheme.execute.form;

import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.scheme.execute.Frame;
import org.sodeja.scheme.parse.model.Expression;
import org.sodeja.scheme.parse.model.SExpression;
import org.sodeja.scheme.parse.model.SymbolExpression;

public class DefineForm implements Form {
	@Override
	public Object execute(Frame frame, List<Expression> expressions) {
		// TODO check for errors
		
		Expression what = expressions.get(0);
		
		if(what instanceof SymbolExpression) {
			String symbol = ((SymbolExpression) what).name;
			Object evalResult = frame.eval(expressions.get(1));
			return define(frame, symbol, evalResult);
		}
		
		return defineFunction(frame, (SExpression) what, ListUtils.tail(expressions));
	}

	private String define(Frame frame, String symbol, Object value) {
		frame.addSymbol(symbol, value);
		return symbol;
	}
	
	/** 
	 * Syntactic sugar for function definition 
	 * 	(def <symbol> (\ <params> <evals>)) 
	 * becomes 
	 * 	(def (<symbol> <params>) (<evals>))
	 */
	private String defineFunction(Frame frame, SExpression definition, List<Expression> evals) {
		String symbol = ((SymbolExpression) definition.expressions.get(0)).name;
		LispProcedure proc = FormUtils.makeProcedure(frame, ListUtils.tail(definition.expressions), evals);
		return define(frame, symbol, proc);
	}
}

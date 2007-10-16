package org.sodeja.scheme.execute.form;

import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;
import org.sodeja.scheme.execute.Frame;
import org.sodeja.scheme.parse.model.Expression;
import org.sodeja.scheme.parse.model.SymbolExpression;

class FormUtils {
	private static List<String> convertParams(List<Expression> expressions) {
		return ListUtils.map(expressions, new Function1<String, Expression>() {
			@Override
			public String execute(Expression p) {
				return ((SymbolExpression) p).name;
			}});
	}
	
	public static LispProcedure makeProcedure(Frame parent, List<Expression> params, List<Expression> evals) {
		return new LispProcedure(parent, convertParams(params), evals);
	}
}

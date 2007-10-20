package org.sodeja.scheme.execute.form;

import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.scheme.execute.Frame;
import org.sodeja.scheme.parse.model.Expression;
import org.sodeja.scheme.parse.model.SExpression;

public class LambdaForm implements Form {
	@Override
	public Object execute(Frame frame, List<Expression> expressions) {
		// TODO check for errors
		SExpression paramsExp = (SExpression) expressions.get(0);
		return FormUtils.makeProcedure(frame, paramsExp.expressions, ListUtils.tail(expressions));
	}
}

package org.sodeja.scheme.execute.form;

import java.util.List;

import org.sodeja.scheme.execute.Frame;
import org.sodeja.scheme.parse.model.Expression;

public class IfForm implements Form {
	@Override
	public Object execute(Frame frame, List<Expression> expressions) {
		if(expressions.size() != 3) {
			throw new IllegalArgumentException("Expecting predicate, true action and false action");
		}
		
		Expression predicate = expressions.get(0);
		Object obj = frame.eval(predicate);
		if(! (obj instanceof Boolean)) {
			throw new IllegalArgumentException("Predicate does not returns boolean!");
		}

		if(((Boolean) obj).booleanValue()) {
			return frame.eval(expressions.get(1));
		} else {
			return frame.eval(expressions.get(2));
		}
	}
}

package org.sodeja.scheme.execute.form;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sodeja.collections.ListUtils;
import org.sodeja.scheme.execute.Frame;
import org.sodeja.scheme.parse.model.Expression;
import org.sodeja.scheme.parse.model.SExpression;
import org.sodeja.scheme.parse.model.SymbolExpression;

public class LetForm implements Form {
	@Override
	public Object execute(Frame frame, List<Expression> expressions) {
		if(expressions.size() < 2) {
			throw new IllegalArgumentException("Expect at least two expressions - var binding and and executed");
		}
		
		Map<String, Object> objects = new HashMap<String, Object>();
		SExpression bindings = (SExpression) ListUtils.head(expressions);
		for(Expression bindingExp : bindings.expressions) {
			SExpression binding = (SExpression) bindingExp;
			if(binding.expressions.size() != 2) {
				throw new IllegalArgumentException("Wrong binding part - (var exp)");
			}
			
			SymbolExpression symbolExp = (SymbolExpression) binding.expressions.get(0);
			
			// If we construct frame now and use it in every consequent expression maybe letrec?
			Object value = frame.eval(binding.expressions.get(1));
			
			objects.put(symbolExp.value, value);
		}
		
		Frame newFrame = new Frame(frame, objects);
		
		Object val = null;
		for(Expression exp : ListUtils.tail(expressions)) {
			val = newFrame.eval(exp);
		}
		return val;
	}
}

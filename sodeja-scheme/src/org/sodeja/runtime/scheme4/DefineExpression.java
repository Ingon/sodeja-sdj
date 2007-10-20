package org.sodeja.runtime.scheme4;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.runtime.Evaluator;
import org.sodeja.runtime.Frame;

public class DefineExpression implements CompiledSchemeExpression {
	
	private final NameExpression nameExpression;
	private final List<CompiledSchemeExpression> valueExpressions;
	
	public DefineExpression(NameExpression nameExpression, List<CompiledSchemeExpression> valueExpressions) {
		this.nameExpression = nameExpression;
		this.valueExpressions = valueExpressions;
	}

	public DefineExpression(NameExpression nameExpression, CompiledSchemeExpression valueExpression) {
		this.nameExpression = nameExpression;
		this.valueExpressions = new ArrayList<CompiledSchemeExpression>();
		this.valueExpressions.add(valueExpression);
	}
	
	@Override
	public Object eval(Evaluator<CompiledSchemeExpression> evaluator,
			Frame<CompiledSchemeExpression> frame) {
		
		frame.addObject(nameExpression, CompiledSchemeUtils.evalSingle(evaluator, frame, valueExpressions));		
		return nameExpression.eval(evaluator, frame);
	}
}

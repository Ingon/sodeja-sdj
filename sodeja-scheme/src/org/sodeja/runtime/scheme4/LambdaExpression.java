package org.sodeja.runtime.scheme4;

import java.util.Iterator;
import java.util.List;

import org.sodeja.collections.CollectionUtils;
import org.sodeja.runtime.Evaluator;
import org.sodeja.runtime.Frame;
import org.sodeja.runtime.Procedure;

public class LambdaExpression implements CompiledSchemeExpression {
	
	private final List<NameExpression> formalParameters;
	private final List<CompiledSchemeExpression> body;
	
	public LambdaExpression(List<NameExpression> params, List<CompiledSchemeExpression> body) {
		this.formalParameters = params;
		this.body = body;
	}

	@Override
	public Object eval(final Evaluator<CompiledSchemeExpression> evaluator, 
			final Frame<CompiledSchemeExpression> frame) {
		
		final CompiledSchemeFrame realFrame = (CompiledSchemeFrame) frame;
		return new Procedure() {
			@Override
			public Object apply(Object... values) {
				if(formalParameters.size() < values.length) {
					throw new IllegalArgumentException("Too few parameters");
				}
			
				if(formalParameters.size() > values.length) {
					throw new IllegalArgumentException("Too many parameters");
				}
				
				CompiledSchemeFrame newFrame = realFrame.createChild(values);
				if (!CollectionUtils.isEmpty(formalParameters)) {
					int index = 0;
					for (Iterator<NameExpression> paramsIte = formalParameters.iterator(); paramsIte.hasNext(); index++) {
						newFrame.addObject(paramsIte.next(), values[index]);
					}
				}
				
				return CompiledSchemeUtils.evalSingle(evaluator, newFrame, body);
			}
		};
	}
}

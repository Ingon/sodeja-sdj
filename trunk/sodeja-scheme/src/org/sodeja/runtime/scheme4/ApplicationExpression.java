package org.sodeja.runtime.scheme4;

import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;
import org.sodeja.runtime.Evaluator;
import org.sodeja.runtime.Frame;
import org.sodeja.runtime.Procedure;

public class ApplicationExpression implements CompiledSchemeExpression {
	
	public final CompiledSchemeExpression procedure;
	public final List<CompiledSchemeExpression> arguments;
	
	public ApplicationExpression(CompiledSchemeExpression procedure,
			List<CompiledSchemeExpression> arguments) {
		this.procedure = procedure;
		this.arguments = arguments;
	}

	@Override
	public Object eval(final Evaluator<CompiledSchemeExpression> evaluator, final Frame<CompiledSchemeExpression> frame) {
		Object procedureObject = procedure.eval(evaluator, frame);
		if(! (procedureObject instanceof Procedure)) {
			throw new IllegalArgumentException("Only procedures can be applied");
		}
		
		List<Object> argumentsList = ListUtils.map(arguments, new Function1<Object, CompiledSchemeExpression>() {
			@Override
			public Object execute(CompiledSchemeExpression p) {
				return p.eval(evaluator, frame);
			}});
		
		Object[] values = argumentsList.toArray(new Object[argumentsList.size()]);
		return ((Procedure) procedureObject).apply(values);
	}
}

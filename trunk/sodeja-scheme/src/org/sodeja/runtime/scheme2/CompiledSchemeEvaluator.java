package org.sodeja.runtime.scheme2;

import org.sodeja.runtime.Library;
import org.sodeja.runtime.impl.CompiledEvaluatorImpl;

public class CompiledSchemeEvaluator extends CompiledEvaluatorImpl<CompiledSchemeExpression> {
	public CompiledSchemeEvaluator(Library<CompiledSchemeExpression> library) {
		super(library, new CompiledSchemeFrame());
	}
}

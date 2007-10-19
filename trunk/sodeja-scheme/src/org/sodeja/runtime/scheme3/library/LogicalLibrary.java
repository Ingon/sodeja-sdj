package org.sodeja.runtime.scheme3.library;

import org.sodeja.runtime.Frame;
import org.sodeja.runtime.procedure.logical.NotProcedure;
import org.sodeja.runtime.scheme3.CompiledSchemeExpression;
import org.sodeja.runtime.scheme3.CompiledSchemeLibrary;

public class LogicalLibrary extends CompiledSchemeLibrary {
	@Override
	public void extend(Frame<CompiledSchemeExpression> frame) {
		extend(frame, ("not"), new NotProcedure());
	}
}

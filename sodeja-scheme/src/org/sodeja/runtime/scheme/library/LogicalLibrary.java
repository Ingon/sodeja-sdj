package org.sodeja.runtime.scheme.library;

import org.sodeja.runtime.Frame;
import org.sodeja.runtime.procedure.logical.NotProcedure;
import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.SchemeLibrary;
import org.sodeja.runtime.scheme.model.Symbol;

public class LogicalLibrary extends SchemeLibrary {
	@Override
	public void extend(Frame<SchemeExpression> frame) {
		frame.addObject(new Symbol("not"), new NotProcedure());
	}
}

package org.sodeja.runtime.procedure;

import org.sodeja.runtime.Frame;
import org.sodeja.runtime.procedure.logical.EqProcedure;
import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.SchemeLibrary;
import org.sodeja.runtime.scheme.model.Symbol;

public class BaseLibrary extends SchemeLibrary {
	@Override
	public void extend(Frame<SchemeExpression> frame) {
		frame.addObject(new Symbol("eq?"), new EqProcedure());
	}
}

package org.sodeja.runtime.scheme.procedure;

import org.sodeja.runtime.Frame;
import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.SchemeLibrary;
import org.sodeja.runtime.scheme.model.Symbol;
import org.sodeja.runtime.scheme.procedure.relational.BiggerThenProcedure;
import org.sodeja.runtime.scheme.procedure.relational.EqualProcedure;
import org.sodeja.runtime.scheme.procedure.relational.LessThenProcedure;

public class RelationalLibrary extends SchemeLibrary {
	@Override
	public void extend(Frame<SchemeExpression> frame) {
		frame.addObject(new Symbol("<"), new LessThenProcedure());
		frame.addObject(new Symbol("="), new EqualProcedure());
		frame.addObject(new Symbol(">"), new BiggerThenProcedure());
	}
}

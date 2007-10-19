package org.sodeja.runtime.procedure;

import org.sodeja.runtime.Frame;
import org.sodeja.runtime.procedure.relational.BiggerThenProcedure;
import org.sodeja.runtime.procedure.relational.EqualProcedure;
import org.sodeja.runtime.procedure.relational.LessThenProcedure;
import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.SchemeLibrary;
import org.sodeja.runtime.scheme.model.Symbol;

public class RelationalLibrary extends SchemeLibrary {
	@Override
	public void extend(Frame<SchemeExpression> frame) {
		frame.addObject(new Symbol("<"), new LessThenProcedure());
		frame.addObject(new Symbol("="), new EqualProcedure());
		frame.addObject(new Symbol(">"), new BiggerThenProcedure());
	}
}

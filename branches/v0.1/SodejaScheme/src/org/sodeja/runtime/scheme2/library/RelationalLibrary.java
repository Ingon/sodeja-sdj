package org.sodeja.runtime.scheme2.library;

import org.sodeja.runtime.Frame;
import org.sodeja.runtime.procedure.relational.BiggerThenProcedure;
import org.sodeja.runtime.procedure.relational.EqualProcedure;
import org.sodeja.runtime.procedure.relational.LessThenProcedure;
import org.sodeja.runtime.scheme2.CompiledSchemeExpression;
import org.sodeja.runtime.scheme2.CompiledSchemeLibrary;

public class RelationalLibrary extends CompiledSchemeLibrary {
	@Override
	public void extend(Frame<CompiledSchemeExpression> frame) {
		extend(frame, ("<"), new LessThenProcedure());
		extend(frame, ("="), new EqualProcedure());
		extend(frame, (">"), new BiggerThenProcedure());
	}
}

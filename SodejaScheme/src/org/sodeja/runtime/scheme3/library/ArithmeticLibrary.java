package org.sodeja.runtime.scheme3.library;

import org.sodeja.runtime.Frame;
import org.sodeja.runtime.procedure.arithmetic.DivProcedure;
import org.sodeja.runtime.procedure.arithmetic.MulProcedure;
import org.sodeja.runtime.procedure.arithmetic.SubProcedure;
import org.sodeja.runtime.procedure.arithmetic.SumProcedure;
import org.sodeja.runtime.scheme3.CompiledSchemeExpression;
import org.sodeja.runtime.scheme3.CompiledSchemeLibrary;

public class ArithmeticLibrary extends CompiledSchemeLibrary {
	@Override
	public void extend(Frame<CompiledSchemeExpression> frame) {
		extend(frame, "+", new SumProcedure());
		extend(frame, "-", new SubProcedure());
		extend(frame, "*", new MulProcedure());
		extend(frame, "/", new DivProcedure());
	}
}

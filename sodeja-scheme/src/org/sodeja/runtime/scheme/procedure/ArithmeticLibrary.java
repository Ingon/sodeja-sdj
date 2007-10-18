package org.sodeja.runtime.scheme.procedure;

import org.sodeja.runtime.Frame;
import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.SchemeLibrary;
import org.sodeja.runtime.scheme.model.Symbol;
import org.sodeja.runtime.scheme.procedure.arithmetic.DivProcedure;
import org.sodeja.runtime.scheme.procedure.arithmetic.MulProcedure;
import org.sodeja.runtime.scheme.procedure.arithmetic.SubProcedure;
import org.sodeja.runtime.scheme.procedure.arithmetic.SumProcedure;

public class ArithmeticLibrary extends SchemeLibrary {
	@Override
	public void extend(Frame<SchemeExpression> frame) {
		frame.addObject(new Symbol("+"), new SumProcedure());
		frame.addObject(new Symbol("-"), new SubProcedure());
		frame.addObject(new Symbol("*"), new MulProcedure());
		frame.addObject(new Symbol("/"), new DivProcedure());
	}
}

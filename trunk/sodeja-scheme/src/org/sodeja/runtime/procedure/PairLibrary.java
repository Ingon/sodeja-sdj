package org.sodeja.runtime.scheme.procedure;

import org.sodeja.runtime.Frame;
import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.SchemeLibrary;
import org.sodeja.runtime.scheme.model.Symbol;
import org.sodeja.runtime.scheme.procedure.pair.CarProcedure;
import org.sodeja.runtime.scheme.procedure.pair.CdrProcedure;
import org.sodeja.runtime.scheme.procedure.pair.ConsProcedure;

public class PairLibrary extends SchemeLibrary {
	@Override
	public void extend(Frame<SchemeExpression> frame) {
		frame.addObject(new Symbol("cons"), new ConsProcedure());
		frame.addObject(new Symbol("car"), new CarProcedure());
		frame.addObject(new Symbol("cdr"), new CdrProcedure());
	}
}

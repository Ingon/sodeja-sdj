package org.sodeja.runtime.scheme2.library;

import org.sodeja.runtime.Frame;
import org.sodeja.runtime.procedure.pair.CarProcedure;
import org.sodeja.runtime.procedure.pair.CdrProcedure;
import org.sodeja.runtime.procedure.pair.ConsProcedure;
import org.sodeja.runtime.scheme2.CompiledSchemeExpression;
import org.sodeja.runtime.scheme2.CompiledSchemeLibrary;

public class PairLibrary extends CompiledSchemeLibrary {
	@Override
	public void extend(Frame<CompiledSchemeExpression> frame) {
		extend(frame, ("cons"), new ConsProcedure());
		extend(frame, ("car"), new CarProcedure());
		extend(frame, ("cdr"), new CdrProcedure());
	}
}

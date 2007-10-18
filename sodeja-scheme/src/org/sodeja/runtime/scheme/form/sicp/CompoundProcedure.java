package org.sodeja.runtime.scheme.form.sicp;

import java.util.Iterator;
import java.util.List;

import org.sodeja.collections.CollectionUtils;
import org.sodeja.runtime.Evaluator;
import org.sodeja.runtime.Frame;
import org.sodeja.runtime.Procedure;
import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.SchemeFrame;
import org.sodeja.runtime.scheme.Utils;
import org.sodeja.runtime.scheme.model.Symbol;

public class CompoundProcedure implements Procedure {
	
	private final Evaluator<SchemeExpression> evaluator;
	private final Frame<SchemeExpression> frame;
	private final List<Symbol> params;
	private final List<SchemeExpression> parts;
	
	public CompoundProcedure(Evaluator<SchemeExpression> evaluator,
			Frame<SchemeExpression> frame, List<Symbol> params,
			List<SchemeExpression> parts) {
		
		this.evaluator = evaluator;
		this.frame = frame;
		this.params = params;
		this.parts = parts;
	}

	@Override
	public Object apply(Object... values) {
		if(params.size() < values.length) {
			throw new IllegalArgumentException("Too few parameters");
		}
	
		if(params.size() > values.length) {
			throw new IllegalArgumentException("Too many parameters");
		}

		SchemeFrame newFrame = new SchemeFrame(frame);		
		if (!CollectionUtils.isEmpty(params)) {
			int index = 0;
			for (Iterator<Symbol> paramsIte = params.iterator(); paramsIte.hasNext(); index++) {
				newFrame.addObject(paramsIte.next(), values[index]);
			}
		}
		
		return Utils.evalSingle(evaluator, newFrame, parts);
	}
}

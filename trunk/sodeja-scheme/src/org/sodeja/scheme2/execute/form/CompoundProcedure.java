package org.sodeja.scheme2.execute.form;

import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.sodeja.collections.CollectionUtils;
import org.sodeja.scheme2.Utils;
import org.sodeja.scheme2.execute.Frame;
import org.sodeja.scheme2.execute.Procedure;
import org.sodeja.scheme2.model.Symbol;
import org.sodeja.scheme2.model.Token;

public class CompoundProcedure implements Procedure {

	private final Frame frame;
	private final Deque<Symbol> params;
	private final Deque<Token> parts;
	
	public CompoundProcedure(Frame frame, Deque<Symbol> params, Deque<Token> parts) {
		this.frame = frame;
		this.params = params;
		this.parts = parts;
	}

	@Override
	public Object apply(final Object... values) {
		if(params.size() < values.length) {
			throw new IllegalArgumentException("Too few parameters");
		}
		
		if(params.size() > values.length) {
			throw new IllegalArgumentException("Too many parameters");
		}
		
		Map<Symbol, Object> paramsMapping = new HashMap<Symbol, Object>() {
			private static final long serialVersionUID = 7740081972870762415L;
		{
			if(! CollectionUtils.isEmpty(params)) {
				int index = 0;
				for(Iterator<Symbol> paramsIte = params.iterator();paramsIte.hasNext();index++) {
					put(paramsIte.next(), values[index]);
				}
			}
		}};
		Frame newFrame = new Frame(frame, paramsMapping);
		
		return Utils.evalDeque(newFrame, parts).peekLast();
	}
}

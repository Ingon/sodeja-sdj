package org.sodeja.ilan.runtime;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.ilan.ildk.ILObject;
import org.sodeja.ilan.ildk.ILSymbol;
import org.sodeja.ilan.parser.Expression;

public class Process implements Context {

	private Map<ILSymbol, ILObject> values;

	public Process() {
		values = new HashMap<ILSymbol, ILObject>();
	}
	
	public ILObject eval(Expression expression) {
		return expression.eval(this);
	}

	@Override
	public ILObject get(ILSymbol symbol) {
		ILObject object = values.get(symbol);
		if(object == null) {
			throw new RuntimeException("Unknown symbol: " + symbol);
		}
		return object;
	}
	
	@Override
	public void def(ILSymbol symbol, ILObject value) {
		ILObject object = values.get(symbol);
		if(object != null) {
			throw new RuntimeException("Cannot override definition");
		}
		values.put(symbol, value);
	}
}

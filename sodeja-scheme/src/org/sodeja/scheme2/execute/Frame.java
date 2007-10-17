package org.sodeja.scheme2.execute;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.scheme2.model.Symbol;
import org.sodeja.scheme2.model.Token;

public class Frame {
	protected final Runtime runtime;
	protected final Frame parent;
	protected final Map<Symbol, Object> objects;
	
	private Frame() {
		runtime = null;
		parent = null;
		objects = null;
	}
	
	public Frame(Runtime runtime) {
		this(runtime, null, new HashMap<Symbol, Object>());
	}

	public Frame(Frame parent, Map<Symbol, Object> objects) {
		this(parent.runtime, parent, objects);
	}
	
	private Frame(Runtime runtime, Frame parent, Map<Symbol, Object> objects) {
		this.runtime = runtime;
		if(parent == null) {
			this.parent = new NullFrame();
		} else {
			this.parent = parent;
		}
		this.objects = objects;
	}
	
	protected Object getSymbolValue(Symbol symbol) {
		Object value = objects.get(symbol);
		if(value != null) {
			return value;
		}
		return parent.getSymbolValue(symbol);
	}
	
	protected boolean containsSymbol(String symbol) {
		if(objects.containsKey(symbol)) {
			return true;
		}
		return parent.containsSymbol(symbol);
	}

	public void extend(Symbol symbol, Object value) {
		if(objects.containsKey(symbol)) {
			throw new IllegalArgumentException("Not possible to redefine names in this context");
		}
		objects.put(symbol, value);
	}
	
	public void update(Symbol symbol, Object value) {
		if(! objects.containsKey(symbol)) {
			parent.update(symbol, value);
			return;
		}
		objects.put(symbol, value);
	}

	public Object eval(Token token) {
		return runtime.eval(this, token);
	}

	private static class NullFrame extends Frame {
		@Override
		public boolean containsSymbol(String symbol) {
			return false;
		}

		@Override
		public Object getSymbolValue(Symbol symbol) {
			throw new IllegalArgumentException("Symbol '" + symbol + "' does not exists!");
		}

		@Override
		public void update(Symbol symbol, Object value) {
			throw new IllegalArgumentException("Symbol '" + symbol + "' does not exists!");
		}
	}
}

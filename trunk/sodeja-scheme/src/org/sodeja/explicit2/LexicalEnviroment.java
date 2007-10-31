package org.sodeja.explicit2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sodeja.runtime.scheme.model.Symbol;

class LexicalEnviroment {
	public static int envCount = 0;
	
	private final LexicalEnviroment parent;
	private Map<Symbol, Object> locals;
	private final List<Object> lexicalVals;
	
	public LexicalEnviroment(DynamicEnviroment dynamic, List<Object> lexicalVals) {
		this(new NullEnviroment(dynamic), lexicalVals);
	}
	
	public LexicalEnviroment(LexicalEnviroment parent, List<Object> lexicalVals) {
		this.parent = parent;
		
//		this.locals = new HashMap<Symbol, Object>();
		this.lexicalVals = lexicalVals;
		
		envCount++;
	}
	
	protected LexicalEnviroment getParent() {
		return parent;
	}
	
	protected Object lookup(Symbol sym) {
		if(locals == null) {
			return parent.lookup(sym);
		}
		
		Object result = locals.get(sym);
		if(result == null) {
			return parent.lookup(sym);
		}
		return result;
	}
	
	protected void define(Symbol sym, Object value) {
		createLocals();
		locals.put(sym, value);
	}
	
	protected void set(Symbol sym, Object value) {
		if(locals != null && locals.containsKey(sym)) {
			locals.put(sym, value);
			return;
		}
		parent.set(sym, value);
	}

	private void createLocals() {
		if(locals == null) {
			locals = new HashMap<Symbol, Object>();
		}
	}
	
	protected Object lexicalLookup(int index) {
		return lexicalVals.get(index);
	}

	private static class NullEnviroment extends LexicalEnviroment {
		private final DynamicEnviroment dynamic;
		
		public NullEnviroment(DynamicEnviroment dynamic) {
			super((LexicalEnviroment) null, null);
			this.dynamic = dynamic;
		}

		@Override
		protected void define(Symbol sym, Object value) {
			dynamic.define(sym, value);
		}

		@Override
		protected Object lookup(Symbol sym) {
			return dynamic.lookup(sym);
		}

		@Override
		protected void set(Symbol sym, Object value) {
			dynamic.set(sym, value);
		}

		@Override
		protected Object lexicalLookup(int index) {
			throw new UnsupportedOperationException();
		}
	}
}

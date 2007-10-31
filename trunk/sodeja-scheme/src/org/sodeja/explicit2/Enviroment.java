package org.sodeja.explicit2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sodeja.runtime.scheme.model.Symbol;

class Enviroment {

	public static int envCount = 0;
	
	private final Enviroment parent;
	private final Map<Symbol, Object> mapping;
	private final List<Object> lexicalVals;
	
	public Enviroment() {
		this(new NullEnviroment(), null);
	}
	
	public Enviroment(Enviroment parent, List<Object> lexicalVals) {
		this.parent = parent;
		
		this.mapping = new HashMap<Symbol, Object>();
		this.lexicalVals = lexicalVals;
		
		envCount++;
	}
	
	public void define(Symbol sym, Object value) {
		mapping.put(sym, value);
	}
	
	public void set(Symbol sym, Object value) {
		// TODO maybe error check?
		mapping.put(sym, value);
	}

	public Object lookup(Symbol sym) {
		Object result = mapping.get(sym);
		if(result == null) {
			return parent.lookup(sym);
		}
		return result;
	}
	
	protected Object lookup(Reference ref) {
		if(ref instanceof DynamicReference) {
			return lookup(((DynamicReference) ref).name);
		}
		
		if(ref instanceof ParentLexicalReference) {
			return parent.lookup(((ParentLexicalReference) ref).parent);
		}
		
		return lexicalVals.get(((LexicalReference) ref).index);
	}
	
	private static class NullEnviroment extends Enviroment {
		public NullEnviroment() {
			super(null, null);
		}

		@Override
		public void define(Symbol sym, Object value) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Object lookup(Symbol sym) {
			throw new IllegalArgumentException("Symbol '" + sym + "' not found!");
		}

		@Override
		protected Object lookup(Reference ref) {
			throw new UnsupportedOperationException();
		}
	}
}

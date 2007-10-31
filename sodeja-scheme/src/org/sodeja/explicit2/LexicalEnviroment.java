package org.sodeja.explicit2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sodeja.runtime.scheme.model.Symbol;

class LexicalEnviroment {
	public static int envCount = 0;
	
	private final long id;
	private int usages;
	private boolean sealed = false;
	
	private LexicalEnviroment parent;
	private Map<Symbol, Object> locals;
	private List<Object> lexicalVals;
	
	protected LexicalEnviroment(LexicalEnviroment parent, List<Object> lexicalVals) {
		this.parent = parent;
		if(parent != null) {
			this.parent.addUse();
		}
		
		locals = new HashMap<Symbol, Object>();
		
		this.lexicalVals = lexicalVals;
		
		this.id = envCount++;
		this.usages = 0;
	}
		
	protected void init(LexicalEnviroment parent, List<Object> lexicalVals) {
		this.parent = parent;
		this.parent.addUse();
		
		this.lexicalVals = lexicalVals;
	}
	
	protected void clear(LexicalEnviromentFactory factory) {
		parent.removeUse();
		if(parent.isFree()) {
			factory.unget(parent);
		}
		
		parent = null;
		
		if(locals != null) {
			locals.clear();
		}
		
		this.lexicalVals = null;
	}
	
	protected LexicalEnviroment getParent() {
		return parent;
	}
	
	protected Object lookup(Symbol sym) {
		return lookup(this, sym);
//		if(locals == null) {
//			return parent.lookup(sym);
//		}
//		
//		Object result = locals.get(sym);
//		if(result == null) {
//			return parent.lookup(sym);
//		}
//		return result;
	}
	
	protected static Object lookup(LexicalEnviroment env, Symbol sym) {
		LexicalEnviroment last = null;
		do {
			if(env.locals == null) {
				last = env;
				env = env.parent;
				continue;
			}
			
			Object result = env.locals.get(sym);
			if(result == null) {
				last = env;
				env = env.parent;
				continue;
			}
			
			return result;
		} while(env != null);
		
		return last.lookup(sym);
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

	protected void addUse() {
		usages++;
	}
	
	protected void removeUse() {
		usages--;
	}
	
	protected boolean isFree() {
		return usages == 0;
	}
	
	protected void seal() {
		this.sealed = true;
	}
	
	protected boolean isSealed() {
		return this.sealed;
	}
	
	@Override
	public String toString() {
		return "ID: " + id + " U: " + usages;
	}

	@Override
	public boolean equals(Object obj) {
		return obj instanceof LexicalEnviroment && id == ((LexicalEnviroment) obj).id;
	}

	protected static class NullEnviroment extends LexicalEnviroment {
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

		@Override
		protected void clear(LexicalEnviromentFactory factory) {
		}
	}
}

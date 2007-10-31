package org.sodeja.explicit2;

import org.sodeja.runtime.scheme.model.Symbol;

class DynamicReference implements Reference {

	protected final Symbol name;
	
	public DynamicReference(Symbol name) {
		this.name = name;
	}

	@Override
	public Object resolveValue(DynamicEnviroment dynamic, LexicalEnviroment lexical) {
		if(lexical != null) {
			return lexical.lookup(name);
		}
		return dynamic.lookup(name);
	}

	@Override
	public String toString() {
		return name.toString();
	}
}

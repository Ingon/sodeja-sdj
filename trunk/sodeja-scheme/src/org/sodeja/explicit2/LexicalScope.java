package org.sodeja.explicit2;

import java.util.List;

import org.sodeja.runtime.scheme.model.Symbol;

public class LexicalScope {
	private final LexicalScope parent;
	private final List<Symbol> vars;
	
	public LexicalScope(List<Symbol> vars) {
		this(null, vars);
	}
	
	public LexicalScope(LexicalScope parent, List<Symbol> vars) {
		this.parent = parent;
		this.vars = vars;
	}

	public LexicalScope getParent() {
		return parent;
	}

	public Reference find(Symbol name) {
		int index = vars.indexOf(name);
		if(index >= 0) {
			return new LexicalReference(index);
		}
		
		if(parent == null) {
			return new DynamicReference(name);
		}
		
		Reference parentRef = parent.find(name);
		if(parentRef instanceof DynamicReference) {
			return parentRef;
		}
		
		return new ParentLexicalReference(parentRef);
	}
}

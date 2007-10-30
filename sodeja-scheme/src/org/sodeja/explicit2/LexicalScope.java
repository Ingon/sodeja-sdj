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

	public int find(Symbol name) {
		// TODO but for more than one?
		return vars.indexOf(name);
	}
}

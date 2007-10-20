package org.sodeja.runtime.scheme4;

import java.util.List;

public class Scope {
	private final Scope parent;
	private final List<NameExpression> expressions;
	
	public Scope(List<NameExpression> expressions) {
		this(null, expressions);
	}
	
	public Scope(Scope parent, List<NameExpression> expressions) {
		this.parent = parent;
		this.expressions = expressions;
	}

	public Scope getParent() {
		return parent;
	}

	public int find(NameExpression nameExpression) {
		return expressions.indexOf(nameExpression);
	}
}

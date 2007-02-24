package org.sodeja.sdj.ti;

import java.util.List;

import org.sodeja.sdj.expression.Expression;
import org.sodeja.sdj.expression.Name;
import org.sodeja.sdj.expression.Supercombinator;

public class SupercombinatorNode<T> implements Node {
	public final Name name;
	public final List<T> bindings;
	public final Expression<T> expression;
	
	SupercombinatorNode(Supercombinator<T> delegate) {
		this.name = delegate.name;
		this.bindings = delegate.bindings;
		this.expression = delegate.expression;
	}
}

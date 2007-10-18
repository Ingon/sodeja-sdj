package org.sodeja.runtime.abs;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.runtime.Dialect;
import org.sodeja.runtime.Expression;
import org.sodeja.runtime.Form;

public abstract class AbstractDialect<E extends Expression> implements Dialect<E> {
	
	protected final Map<E, Form<E>> mapping;	
	
	public AbstractDialect() {
		mapping = new HashMap<E, Form<E>>();
		init();
	}
	
	protected void init() {
	}

	@Override
	public final Form<E> find(E expression) {
		if(! isSupported(expression)) {
			return null;
		}
		return mapping.get(decompose(expression));
	}
	
	protected boolean isSupported(E expression) {
		return true;
	}
	
	protected E decompose(E expression) {
		return expression;
	}
}

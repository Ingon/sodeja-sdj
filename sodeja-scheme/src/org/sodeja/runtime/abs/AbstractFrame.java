package org.sodeja.runtime.abs;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.runtime.Expression;
import org.sodeja.runtime.Frame;

public class AbstractFrame<E extends Expression> implements Frame<E> {

	protected final Map<E, Object> values;
	
	public AbstractFrame() {
		values = new HashMap<E, Object>();
	}
	
	public AbstractFrame(Map<E, Object> values) {
		this.values = values;
	}
	
	@Override
	public void addObject(E expression, Object value) {
		values.put(expression, value);
	}

	@Override
	public void setObject(E expression, Object value) {
		values.put(expression, value);
	}

	@Override
	public Object findObject(E expression) {
		return values.get(expression);
	}
}

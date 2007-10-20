package org.sodeja.runtime.scheme;

import org.sodeja.runtime.Frame;
import org.sodeja.runtime.abs.AbstractFrame;

public class SchemeFrame extends AbstractFrame<SchemeExpression> {

	private final Frame<SchemeExpression> parent;
	
	protected SchemeFrame() {
		this.parent = new NullSchemeFrame();
	}
	
	protected SchemeFrame(Frame<SchemeExpression> parent) {
		this.parent = parent;
	}
	
	@Override
	public void addObject(SchemeExpression expression, Object value) {
		if(values.containsKey(expression)) {
			throw new IllegalArgumentException("Not possible to redefine names in this context");
		}
		super.addObject(expression, value);
	}

	@Override
	public Object findObject(SchemeExpression expression) {
		Object value = super.findObject(expression);
		if(value != null) {
			return value;
		}
		return parent.findObject(expression);
	}

	@Override
	public void setObject(SchemeExpression expression, Object value) {
		if(! values.containsKey(expression)) {
			parent.setObject(expression, value);
			return;
		}
		super.setObject(expression, value);
	}
	
	public SchemeFrame createChild() {
		return new SchemeFrame(this);
	}
	
	private class NullSchemeFrame extends SchemeFrame {
		public NullSchemeFrame() {
			super(null);
		}

		@Override
		public Object findObject(SchemeExpression expression) {
			throw new IllegalArgumentException("Symbol '" + expression + "' does not exists!");
		}

		@Override
		public void setObject(SchemeExpression expression, Object value) {
			throw new IllegalArgumentException("Symbol '" + expression + "' does not exists!");
		}
	}
}

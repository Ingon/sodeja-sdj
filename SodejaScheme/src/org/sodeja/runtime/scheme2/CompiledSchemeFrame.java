package org.sodeja.runtime.scheme2;

import org.sodeja.runtime.Frame;
import org.sodeja.runtime.abs.AbstractFrame;

public class CompiledSchemeFrame extends AbstractFrame<CompiledSchemeExpression> {
	private final Frame<CompiledSchemeExpression> parent;
	
	protected CompiledSchemeFrame() {
		this.parent = new NullSchemeFrame();
	}
	
	protected CompiledSchemeFrame(Frame<CompiledSchemeExpression> parent) {
		this.parent = parent;
	}
	
	@Override
	public void addObject(CompiledSchemeExpression expression, Object value) {
		if(values.containsKey(expression)) {
			throw new IllegalArgumentException("Not possible to redefine names in this context");
		}
		super.addObject(expression, value);
	}

	@Override
	public Object findObject(CompiledSchemeExpression expression) {
		Object value = super.findObject(expression);
		if(value != null) {
			return value;
		}
		return parent.findObject(expression);
	}

	@Override
	public void setObject(CompiledSchemeExpression expression, Object value) {
		if(! values.containsKey(expression)) {
			parent.setObject(expression, value);
			return;
		}
		super.setObject(expression, value);
	}
	
	public CompiledSchemeFrame createChild() {
		return new CompiledSchemeFrame(this);
	}
	
	private class NullSchemeFrame extends CompiledSchemeFrame {
		public NullSchemeFrame() {
			super(null);
		}

		@Override
		public Object findObject(CompiledSchemeExpression expression) {
			throw new IllegalArgumentException("Symbol '" + expression + "' does not exists!");
		}

		@Override
		public void setObject(CompiledSchemeExpression expression, Object value) {
			throw new IllegalArgumentException("Symbol '" + expression + "' does not exists!");
		}
	}
}

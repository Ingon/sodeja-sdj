package org.sodeja.runtime.scheme4;

import org.sodeja.runtime.Frame;
import org.sodeja.runtime.abs.AbstractFrame;

public class CompiledSchemeFrame extends AbstractFrame<CompiledSchemeExpression> {
	public static int frameCount;
	public static int maxLength = 0;
	
	private final Frame<CompiledSchemeExpression> parent;
	private final Object[] actualParameters;
	
	protected CompiledSchemeFrame() {
		this.parent = new NullSchemeFrame();
		this.actualParameters = null;
	}
	
	protected CompiledSchemeFrame(Frame<CompiledSchemeExpression> parent, Object[] actualParameters) {
		this.parent = parent;
		this.actualParameters = actualParameters;
		
		++frameCount;
		maxLength = Math.max(maxLength, length());
	}
	
	protected int length() {
		return 1 + ((CompiledSchemeFrame) parent).length();
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
	
	public CompiledSchemeFrame createChild(Object[] actualParameters) {
		return new CompiledSchemeFrame(this, actualParameters);
	}
	
	public Object[] getActualParameters() {
		return actualParameters;
	}
	
	private class NullSchemeFrame extends CompiledSchemeFrame {
		public NullSchemeFrame() {
			super(null, null);
		}

		@Override
		protected int length() {
			return 0;
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

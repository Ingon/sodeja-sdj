package org.sodeja.runtime.abs;

import org.sodeja.runtime.Dialect;
import org.sodeja.runtime.Evaluator;
import org.sodeja.runtime.Expression;
import org.sodeja.runtime.Form;
import org.sodeja.runtime.Frame;
import org.sodeja.runtime.Library;

public abstract class AbstractEvaluator<E extends Expression> implements Evaluator<E> {
	
	protected final Dialect<E> dialect;
	protected final Library<E> library;
	protected final Frame<E> rootFrame;
	
	public AbstractEvaluator(Dialect<E> dialect, Library<E> library, Frame<E> rootFrame) {
		this.dialect = dialect;
		this.library = library;
		this.rootFrame = rootFrame;
		
		this.library.extend(rootFrame);
	}

	public Object eval(Frame<E> frame, E expression) {
		if(isPrimitive(expression)) {
			return evalPrimitive(expression);
		}
		
		if(isVariable(expression)) {
			return frame.findObject(expression);
		}
		
		Form<E> form = dialect.find(expression);
		if(form != null) {
			return form.eval(this, frame, expression);
		}

		if(isApplication(expression)) {
			return apply(frame, expression);
		}
		
		throw new IllegalArgumentException("Wrong expression: " + expression);
	}

	protected abstract boolean isPrimitive(E expression);

	protected abstract Object evalPrimitive(E expression);

	protected abstract boolean isVariable(E expression);
	
	protected abstract boolean isApplication(E expression);
	
	protected abstract Object apply(Frame<E> frame, E expression);
}

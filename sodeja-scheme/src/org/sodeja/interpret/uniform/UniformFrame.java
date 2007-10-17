package org.sodeja.interpret.uniform;

import org.sodeja.interpret.Frame;
import org.sodeja.interpret.Token;

public interface UniformFrame<T extends Token> extends Frame<T>, UniformLibrary<T> {
	void addObject(T symbol, Object value);
	void setObject(T symbol, Object value);
}

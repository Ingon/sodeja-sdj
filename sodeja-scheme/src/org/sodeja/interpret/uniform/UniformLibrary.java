package org.sodeja.interpret.uniform;

import org.sodeja.interpret.Library;
import org.sodeja.interpret.Token;

public interface UniformLibrary<T extends Token> extends Library<T> {
	Object findObject(T symbol);
}

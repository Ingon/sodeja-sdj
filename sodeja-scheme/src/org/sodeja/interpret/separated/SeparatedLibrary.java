package org.sodeja.interpret.separated;

import org.sodeja.interpret.Procedure;
import org.sodeja.interpret.Token;
import org.sodeja.interpret.uniform.UniformLibrary;

public interface SeparatedLibrary<T extends Token> extends UniformLibrary<T> {
	Procedure findProcedure(T token);
}

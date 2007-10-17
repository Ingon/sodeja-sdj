package org.sodeja.interpret.separated;

import org.sodeja.interpret.Frame;
import org.sodeja.interpret.Procedure;
import org.sodeja.interpret.Token;

public interface SeparatedFrame<T extends Token> extends Frame<T>, SeparatedLibrary<T> {
	void addProcedure(T token, Procedure procedure);
	void setProcedure(T token, Procedure procedure);
}

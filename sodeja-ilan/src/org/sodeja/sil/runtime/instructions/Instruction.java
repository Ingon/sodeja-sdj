package org.sodeja.sil.runtime.instructions;

import org.sodeja.sil.runtime.Interpreter;

public interface Instruction {
	void execute(Interpreter interpreter);
}

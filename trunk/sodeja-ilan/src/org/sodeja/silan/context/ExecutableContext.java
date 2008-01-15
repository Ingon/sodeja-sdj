package org.sodeja.silan.context;

import org.sodeja.silan.CompiledCode;
import org.sodeja.silan.Process;

public class ExecutableContext extends AbstractContext {
	public ExecutableContext(Process process, CompiledCode code) {
		super(process, code);
	}
}

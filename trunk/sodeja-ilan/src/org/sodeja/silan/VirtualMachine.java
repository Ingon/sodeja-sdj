package org.sodeja.silan;

import org.sodeja.silan.compiler.Compiler;

public class VirtualMachine {
	
	private final Compiler compiler;
	private final ProcessManager processes;
	
	public VirtualMachine() {
		compiler = new Compiler(this);
		processes = new ProcessManager(this);
	}

	public void compileAndExecute(String string) {
		CompiledCode code = compiler.compile(string);
		execute(code);
	}

	private void execute(CompiledCode code) {
		Process proc = processes.newProcess(code);
		proc.run();
	}
}

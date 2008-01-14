package org.sodeja.silan;

import org.sodeja.silan.compiler.Compiler;

public class VirtualMachine {
	
	private final Compiler compiler;
	private final ProcessManager processes;
	private final ObjectManager objects;
	
	public VirtualMachine() {
		compiler = new Compiler(this);
		processes = new ProcessManager(this);
		objects = new ObjectManager(this);
	}

	public SILObject compileAndExecute(String string) {
		CompiledCode code = compiler.compile(string);
		return execute(code);
	}

	private SILObject execute(CompiledCode code) {
		Process proc = processes.newProcess(code);
		proc.run();
		return proc.getValue();
	}
	
	public ObjectManager getObjectManager() {
		return objects;
	}
}

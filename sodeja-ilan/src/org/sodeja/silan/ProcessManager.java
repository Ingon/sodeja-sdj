package org.sodeja.silan;

public class ProcessManager {
	
	private final VirtualMachine vm;
	
	public ProcessManager(VirtualMachine vm) {
		this.vm = vm;
	}

	public Process newProcess(CompiledCode code) {
		throw new UnsupportedOperationException();
	}
}

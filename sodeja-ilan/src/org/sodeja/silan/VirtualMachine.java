package org.sodeja.silan;

import java.util.List;

import org.sodeja.silan.compiler.Compiler;

public class VirtualMachine {
	
	private final Compiler compiler;
	private final ProcessManager processes;
	private final ObjectManager objects;
	
	public VirtualMachine() {
		compiler = new Compiler();
		processes = new ProcessManager(this);
		objects = new ObjectManager(this);
	}

	public void subclass(String parentName, String newClassName, List<String> instanceVariables) {
		objects.subclass(parentName, newClassName, instanceVariables);
	}
	
	public void compileAndAttach(String source, String className) {
		CompiledMethod method = compiler.compileMethod(source);
		objects.attach(className, method);
	}
	
	public SILObject compileAndExecute(String string) {
		CompiledCode code = compiler.compileCode(string);
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

package org.sodeja.silan;

import java.util.List;

import org.sodeja.silan.compiler.Compiler;
import org.sodeja.silan.objects.ImageObjectManager;

public class VirtualMachine {
	
	public final Compiler compiler;
	public final ProcessManager processes;
	public final ImageObjectManager objects;
	
	public VirtualMachine() {
		compiler = new Compiler();
		processes = new ProcessManager(this);
		objects = new ImageObjectManager(this);
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
}

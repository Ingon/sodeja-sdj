package org.sodeja.silan.compiler;

import org.sodeja.silan.CompiledCode;
import org.sodeja.silan.VirtualMachine;

public class Compiler {
	private final VirtualMachine vm;
	
	public Compiler(VirtualMachine vm) {
		this.vm = vm;
	}

	public CompiledCode compile(String string) {
		ExecutableCode code = parse(string);
		int tempCount = code.localVariables.size();
		
		return null;
	}

	private ExecutableCode parse(String string) {
		// TODO Auto-generated method stub
		return null;
	}
}

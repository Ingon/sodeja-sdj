package org.sodeja.sil.vm;

public class VirtualMachine {
	private Interpreter interpreter;
	
	public VirtualMachine() {
		interpreter = new Interpreter();
	}

	public void run() {
		new Thread(interpreter).start();
	}
}

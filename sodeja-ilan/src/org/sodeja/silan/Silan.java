package org.sodeja.silan;

public class Silan {
	public static void main(String[] args) {
		VirtualMachine vm = new VirtualMachine();
		vm.compileAndExecute("3 + 5.");
	}
}

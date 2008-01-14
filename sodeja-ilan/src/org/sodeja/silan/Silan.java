package org.sodeja.silan;

public class Silan {
	public static void main(String[] args) {
		VirtualMachine vm = new VirtualMachine();
		SILObject value = vm.compileAndExecute("3 + 5.");
		System.out.println("Value: " + value);
	}
}

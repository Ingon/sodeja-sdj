package org.sodeja.silan;

public class Silan {
	public static void main(String[] args) {
		VirtualMachine vm = new VirtualMachine();
		SILObject value = vm.compileAndExecute("| a | \r\n" +
				"a := 3 + 5.\r\n" +
				"a + 2.");
		System.out.println("Value: " + value);
	}
}

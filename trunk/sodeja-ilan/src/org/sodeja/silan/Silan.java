package org.sodeja.silan;

import java.io.IOException;

import org.sodeja.files.FileUtils;

public class Silan {
	public static void main(String[] args) throws IOException {
		VirtualMachine vm = new VirtualMachine();
		
		String frameSource = FileUtils.readFully("test/frame.silan");
		SILObject value = vm.compileAndExecute(frameSource);
		System.out.println("Value: " + value);
	}
}
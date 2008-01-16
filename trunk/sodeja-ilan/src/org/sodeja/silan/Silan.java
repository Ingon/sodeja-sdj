package org.sodeja.silan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.sodeja.collections.ListUtils;

public class Silan {
	public static void main(String[] args) throws IOException {
		VirtualMachine vm = new VirtualMachine();
		
		vm.subclass("Object", "Association", ListUtils.asList("key", "value"));
//		vm.compileAndAttach(readFully("test/silan/6_1.silan"), "Association");
//		vm.compileAndAttach(readFully("test/silan/6_2.silan"), "Association");
//		vm.compileAndAttach(readFully("test/silan/7_1.silan"), "Association");
//		vm.compileAndAttach(readFully("test/silan/7_2.silan"), "Association");
//		vm.compileAndAttach(readFully("test/silan/11_1.silan"), "Association");
		vm.compileAndAttach(readFully("test/silan/15_1.silan"), "True");
//		vm.compileAndAttach(readFully("test/silan/15_5.silan"), "Boolean");
		
		String source = readFully("test/silan/15.silan");
		SILObject value = vm.compileAndExecute(source);
		System.out.println("Value: " + value);
	}
	
	private static String readFully(String file) throws IOException {
		return readFully(new FileReader(file));
	}
	
	private static String readFully(Reader reader) throws IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = new BufferedReader(reader);
		
		for(String str = br.readLine();str != null;str = br.readLine()) {
			sb.append(str);
			sb.append("\r\n");
		}
		
		return sb.toString();
	}
}

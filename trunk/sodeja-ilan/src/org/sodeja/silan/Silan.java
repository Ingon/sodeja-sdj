package org.sodeja.silan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.sodeja.collections.ListUtils;

public class Silan {
	public static void main(String[] args) throws IOException {
		VirtualMachine vm = new VirtualMachine();
//		vm.getObjectManager().subclass("Object", "Association", ListUtils.asList("key", "value"));
		
		String source = readFully("test/silan/2.silan");
		
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

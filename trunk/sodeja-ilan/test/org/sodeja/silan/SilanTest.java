package org.sodeja.silan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.sodeja.collections.ListUtils;

import junit.framework.TestCase;

public class SilanTest extends TestCase {
	private VirtualMachine vm;

	@Override
	protected void setUp() throws Exception {
		vm = new VirtualMachine();
		
		vm.subclass("Object", "Association", ListUtils.asList("key", "value"));
		vm.compileAndAttach(readFully("test/silan/6_1.silan"), "Association");
		vm.compileAndAttach(readFully("test/silan/6_2.silan"), "Association");
		vm.compileAndAttach(readFully("test/silan/7_1.silan"), "Association");
		vm.compileAndAttach(readFully("test/silan/7_2.silan"), "Association");
		vm.compileAndAttach(readFully("test/silan/11_1.silan"), "Association");
	}

	public void test1() throws Exception {
		assertPrimitiveInteger(8, execute("1"));
	}

	public void test2() throws Exception {
		assertPrimitiveInteger(10, execute("2"));
	}

	public void test3() throws Exception {
		assertPrimitiveInteger(-8, execute("3"));
	}

	public void test4() throws Exception {
		assertPrimitiveInteger(8, execute("4"));
	}

	public void test5() throws Exception {
		assertPrimitiveInteger(64, execute("5"));
	}

	public void test6() throws Exception {
		assertPrimitiveInteger(8, execute("6"));
	}

	public void test7() throws Exception {
		assertPrimitiveInteger(15, execute("7"));
	}

	public void test8() throws Exception {
		assertPrimitiveInteger(14, execute("8"));
	}

	public void test9() throws Exception {
		assertPrimitiveInteger(8, execute("9"));
	}

	public void test10() throws Exception {
		assertPrimitiveInteger(16, execute("10"));
	}

	public void test11() throws Exception {
		assertPrimitiveInteger(17, execute("11"));
	}

	public void test12() throws Exception {
		assertPrimitiveString("iuhu", execute("12"));
	}

	public void test14() throws Exception {
		SILObject val = execute("14");
		assertSame(vm.getObjectManager().getNil(), val);
	}
	
	public void testBlock1() throws Exception {
		SILObject val = vm.compileAndExecute("[3 + 4.] value.");
		assertPrimitiveInteger(7, val);
	}
	
	public void testBlock2() throws Exception {
		SILObject val = vm.compileAndExecute("[1 * 2 * 3 * 4 * 5.] value.");
		assertPrimitiveInteger(120, val);
	}

	public void testBlock3() throws Exception {
		SILObject val = vm.compileAndExecute("[:x | x * x.] value: 6.");
		assertPrimitiveInteger(36, val);
	}
	
	private void assertPrimitiveInteger(Integer expected, SILObject actual) {
		assertTrue(actual instanceof SILPrimitiveObject);
		assertEquals(expected, ((SILPrimitiveObject<Integer>) actual).value);
	}
	
	private void assertPrimitiveString(String expected, SILObject actual) {
		assertTrue(actual instanceof SILPrimitiveObject);
		assertEquals(expected, ((SILPrimitiveObject<String>) actual).value);
	}

	private SILObject execute(String fileNumber) throws IOException {
		String source = readFully("test/silan/" + fileNumber + ".silan");
		return vm.compileAndExecute(source);
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

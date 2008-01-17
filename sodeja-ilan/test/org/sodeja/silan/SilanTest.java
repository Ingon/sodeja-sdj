package org.sodeja.silan;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import junit.framework.TestCase;

public class SilanTest extends TestCase {
	private VirtualMachine vm;

	@Override
	protected void setUp() throws Exception {
		vm = new VirtualMachine();
		
//		vm.subclass("Object", "Association", ListUtils.asList("key", "value"));
//		vm.compileAndAttach(readFully("test/silan/6_1.silan"), "Association");
//		vm.compileAndAttach(readFully("test/silan/6_2.silan"), "Association");
//		vm.compileAndAttach(readFully("test/silan/7_1.silan"), "Association");
//		vm.compileAndAttach(readFully("test/silan/7_2.silan"), "Association");
//		vm.compileAndAttach(readFully("test/silan/11_1.silan"), "Association");
	}

	public void testObject() throws Exception {
		try {
			vm.compileAndExecute("$a subclassResponsibility.");
			assertTrue(false);
		} catch(RuntimeException exc) {
			assertEquals("SubclassResponsibility", exc.getMessage());
		}
	}
	
	public void test1() throws Exception {
		assertPrimitiveInteger(8, execute("3 + 5"));
	}

	public void test2() throws Exception {
		assertPrimitiveInteger(10, execute("| a | a := 3 + 5. a + 2"));
	}

	public void test3() throws Exception {
		assertPrimitiveInteger(-8, executeFF("3"));
	}

	public void test4() throws Exception {
		assertPrimitiveInteger(8, executeFF("4"));
	}

	public void test5() throws Exception {
		assertPrimitiveInteger(64, executeFF("5"));
	}

	public void test6() throws Exception {
		assertPrimitiveInteger(8, executeFF("6"));
	}

	public void test7() throws Exception {
		assertPrimitiveInteger(15, executeFF("7"));
	}

	public void test8() throws Exception {
		assertPrimitiveInteger(14, executeFF("8"));
	}

	public void test9() throws Exception {
		assertPrimitiveInteger(8, executeFF("9"));
	}

	public void test10() throws Exception {
		assertPrimitiveInteger(16, executeFF("10"));
	}

	public void test11() throws Exception {
		assertPrimitiveInteger(17, executeFF("11"));
	}

	public void test12() throws Exception {
		assertPrimitiveString("iuhu", executeFF("12"));
	}

	public void test13() throws Exception {
		executeFF("13");
	}
	
	public void test14() throws Exception {
		SILObject val = executeFF("14");
		assertSame(vm.objects.nil(), val);
	}
	
	public void testEq1() throws Exception {
		SILObject val = vm.compileAndExecute("| a | a := 3. ^a == a.");
		assertPrimitiveBoolean(true, val);
	}

	public void testEq2() throws Exception {
		SILObject val = vm.compileAndExecute("| a | a := 3. ^a ~~ a.");
		assertPrimitiveBoolean(false, val);
	}

	public void testEq3() throws Exception {
		SILObject val = vm.compileAndExecute("| a | a := 3. ^a = a.");
		assertPrimitiveBoolean(true, val);
	}

	public void testEq4() throws Exception {
		SILObject val = vm.compileAndExecute("| a | a := 3. ^a ~= a.");
		assertPrimitiveBoolean(false, val);
	}
	
	public void testClass() throws Exception {
		SILObject val = vm.compileAndExecute("3 class.");
		assertTrue(val instanceof SILClass);

		val = vm.compileAndExecute("3 class class.");
		assertTrue(val instanceof SILClass);
		assertTrue(val instanceof SILClassClass);
	}
	
	public void testCopy() throws Exception {
		SILObject val = vm.compileAndExecute("| a b | a := Pair new key: 2 value: 5. b := a copy. a = b.");
		assertPrimitiveBoolean(true, val);
		
		val = vm.compileAndExecute("| a b | a := Pair new key: 2 value: 5. b := a copy. a == b.");
		assertPrimitiveBoolean(false, val);

		val = vm.compileAndExecute("| a b | a := Pair new key: 2 value: 5. b := a copy. a ~= b.");
		assertPrimitiveBoolean(false, val);

		val = vm.compileAndExecute("| a b | a := Pair new key: 2 value: 5. b := a copy. a ~~ b.");
		assertPrimitiveBoolean(true, val);
	}
	
	public void testBoolean() throws Exception {
		assertPrimitiveBoolean(false, execute("false & false"));
		assertPrimitiveBoolean(false, execute("false & true"));
		assertPrimitiveBoolean(false, execute("true & false"));
		assertPrimitiveBoolean(true, execute("true & true"));

		assertPrimitiveBoolean(false, execute("false | false"));
		assertPrimitiveBoolean(true, execute("false | true"));
		assertPrimitiveBoolean(true, execute("true | false"));
		assertPrimitiveBoolean(true, execute("true | true"));
		
		SILObject val = vm.compileAndExecute("true | false.");
		assertPrimitiveBoolean(true, val);

		val = vm.compileAndExecute("true eqv: true.");
		assertPrimitiveBoolean(true, val);
		
		val = vm.compileAndExecute("true eqv: false.");
		assertPrimitiveBoolean(false, val);

		val = vm.compileAndExecute("false eqv: true.");
		assertPrimitiveBoolean(false, val);
		
		val = vm.compileAndExecute("false eqv: false.");
		assertPrimitiveBoolean(true, val);
	}
	
	public void testHash() throws Exception {
		SILObject val = vm.compileAndExecute("| a | a := Object new. a hash = a identityHash.");
		assertPrimitiveBoolean(true, val);
	}
	
	public void testClassMembership() throws Exception {
		SILObject val = vm.compileAndExecute("3 isMemberOf: Integer.");
		assertPrimitiveBoolean(true, val);
		
		val = vm.compileAndExecute("3 isMemberOf: Object.");
		assertPrimitiveBoolean(false, val);
		
		try {
			vm.compileAndExecute("3 isKindOf: Boolean.");
			assertTrue(false);
		} catch(RuntimeException exc) {
			assertEquals("NotImplemented", exc.getMessage());
		}
	}
	
	public void testNil() throws Exception {
		SILObject val = vm.compileAndExecute("3 isNil.");
		assertPrimitiveBoolean(false, val);
		
		val = vm.compileAndExecute("3 notNil.");
		assertPrimitiveBoolean(true, val);
		
		val = vm.compileAndExecute("nil isNil.");
		assertPrimitiveBoolean(true, val);

		val = vm.compileAndExecute("nil notNil.");
		assertPrimitiveBoolean(false, val);
	}
	
	public void testChar() throws Exception {
		SILObject val = vm.compileAndExecute("$a.");
		assertPrimitiveCharacter('a', val);

		val = vm.compileAndExecute("$a = $a.");
		assertPrimitiveBoolean(true, val);

		val = vm.compileAndExecute("$a ~= $a.");
		assertPrimitiveBoolean(false, val);
		
		val = vm.compileAndExecute("$a asLowercase.");
		assertPrimitiveCharacter('a', val);

		val = vm.compileAndExecute("$A asLowercase.");
		assertPrimitiveCharacter('a', val);
		
		val = vm.compileAndExecute("$a asString.");
		assertPrimitiveString("a", val);

		val = vm.compileAndExecute("$a asUppercase.");
		assertPrimitiveCharacter('A', val);

		val = vm.compileAndExecute("$A asUppercase.");
		assertPrimitiveCharacter('A', val);

		try {
			vm.compileAndExecute("$a codePoint.");
			assertTrue(false);
		} catch(RuntimeException exc) {
			assertEquals("NotImplemented", exc.getMessage());
		}
		
		val = vm.compileAndExecute("$a < $A.");
		assertPrimitiveBoolean(false, val);

		val = vm.compileAndExecute("$A < $a.");
		assertPrimitiveBoolean(true, val);

		val = vm.compileAndExecute("$a > $A.");
		assertPrimitiveBoolean(true, val);

		val = vm.compileAndExecute("$A > $a.");
		assertPrimitiveBoolean(false, val);

		val = vm.compileAndExecute("$a isDigit.");
		assertPrimitiveBoolean(false, val);
		
		val = vm.compileAndExecute("$3 isDigit.");
		assertPrimitiveBoolean(true, val);

		val = vm.compileAndExecute("$a isLetter.");
		assertPrimitiveBoolean(true, val);
		
		val = vm.compileAndExecute("$3 isLetter.");
		assertPrimitiveBoolean(false, val);

		val = vm.compileAndExecute("$a isAlphaNumeric.");
		assertPrimitiveBoolean(true, val);
		
		val = vm.compileAndExecute("$3 isAlphaNumeric.");
		assertPrimitiveBoolean(true, val);

		val = vm.compileAndExecute("$% isAlphaNumeric.");
		assertPrimitiveBoolean(false, val);
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

	public void testBlock4() throws Exception {
		SILObject val = vm.compileAndExecute("[:a :b | a < b.] value: 3 value: 4.");
		assertPrimitiveBoolean(true, val);
	}
	
	public void testBlock5() throws Exception {
		
	}
	
	public void testControl1() throws Exception {
		SILObject val = vm.compileAndExecute("3 < 4 ifTrue: [4 - 3.].");
		assertPrimitiveInteger(1, val);
	}

	public void testControl2() throws Exception {
		SILObject val = vm.compileAndExecute("23 > 6 ifTrue: [6 - 23.] ifFalse: [23 - 6.].");
		assertPrimitiveInteger(-17, val);
	}
	
	public void testControl3() throws Exception {
//		vm.compileAndExecute("[Transcript show: 'I love you'.] repeat.");
	}
	
	public void testControl4() throws Exception {
		vm.compileAndExecute("3 timesRepeat: [Transcript show: 'I love you'.].");
	}
	
	public void testControl5() throws Exception {
		vm.compileAndExecute("1 to: 5 do: [:index | Transcript show: index printString.].");		
	}
	
	private void assertPrimitiveInteger(Integer expected, SILObject actual) {
		assertTrue(actual instanceof SILPrimitiveObject);
		assertEquals(expected, ((SILPrimitiveObject<Integer>) actual).value);
	}

	private void assertPrimitiveCharacter(Character expected, SILObject actual) {
		assertTrue(actual instanceof SILPrimitiveObject);
		assertEquals(expected, ((SILPrimitiveObject<Character>) actual).value);
	}
	
	private void assertPrimitiveString(String expected, SILObject actual) {
		assertTrue(actual instanceof SILPrimitiveObject);
		assertEquals(expected, ((SILPrimitiveObject<String>) actual).value);
	}

	private void assertPrimitiveBoolean(Boolean expected, SILObject actual) {
		assertTrue(actual instanceof SILPrimitiveObject);
		assertEquals(expected, ((SILPrimitiveObject<Boolean>) actual).value);
	}
	
	private SILObject execute(String text) {
		return vm.compileAndExecute(text);
	}
	
	private SILObject executeFF(String fileNumber) throws IOException {
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

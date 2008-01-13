package org.sodeja.sil;

import org.sodeja.sil.runtime.ObjectManager;

public class SIL {
	public static void main(String[] args) throws Exception {
//		VirtualMachine machine = new VirtualMachine();
//		if(args.length > 0) {
//			machine.restoreImage(new FileInputStream(args[0]));
//		} else {
//			machine.makeNewImage();
//		}
//		machine.run();
		ObjectManager objectManager = new ObjectManager();
//		MethodManager methodManager = new MethodManager();
		System.out.println(objectManager);
		
//		Parser execCode = SILParser.getInstance().getExecutableCodeParser();
//		
////		ConsList<Character> charStream = loadTokens(new FileReader("test/test.sil"));
//		ConsList<Character> charStream = loadTokens(new FileReader("test/test1.sil"));
//		ParseResult pr = execCode.match(charStream);
//		
//		System.out.println("pr: " + pr);
	}
	
//	private static ConsList<Character> loadTokens(Reader reader) throws IOException {
//		List<Character> chars = new ArrayList<Character>();
//		int currentChar = -1;
//		while((currentChar = reader.read()) != -1) {
//			chars.add((char) currentChar);
//		}
//		return ConsList.createList(chars);
//	}
}

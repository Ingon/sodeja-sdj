package org.sodeja.sil;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.parsec2.ParseResult;
import org.sodeja.parsec2.Parser;

public class SIL {
	public static void main(String[] args) throws Exception {
		Parser execCode = SILParser.getInstance().getExecutableCodeParser();
		
//		ConsList<Character> charStream = loadTokens(new FileReader("test/test.sil"));
		ConsList<Character> charStream = loadTokens(new FileReader("test/test1.sil"));
		ParseResult pr = execCode.match(charStream);
		
		System.out.println("pr: " + pr);
	}
	
	private static ConsList<Character> loadTokens(Reader reader) throws IOException {
		List<Character> chars = new ArrayList<Character>();
		int currentChar = -1;
		while((currentChar = reader.read()) != -1) {
			chars.add((char) currentChar);
		}
		return ConsList.createList(chars);
	}
}

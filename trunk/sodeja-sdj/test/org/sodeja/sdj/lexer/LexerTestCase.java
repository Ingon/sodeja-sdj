package org.sodeja.sdj.lexer;

import java.io.FileReader;
import java.util.List;

import junit.framework.TestCase;

public class LexerTestCase extends TestCase {
	public void testTokenize() throws Exception {
		Lexer lexer = new Lexer(new FileReader("test1.sdj"));
		List<Token> tokens = lexer.tokenize();
		
		for(Token tok : tokens) {
			System.out.format("%s: <%s> ", tok.line, tok.name);
			System.out.println();
		}
		fail("Not yet implemented");
	}
}

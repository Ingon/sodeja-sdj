package org.sodeja.ilan;

import org.sodeja.generator.Generator;
import org.sodeja.generator.Generators;
import org.sodeja.ilan.lexer.ILanLexer;
import org.sodeja.ilan.lexer.ILanToken;
import org.sodeja.ilan.parser.ILanParser;
import org.sodeja.ilan.parser.ILanProgram;

public class ILan {
	public static void main(String[] args) {
		Generator<ILanToken> tokens = ILanLexer.tokenize("a + b;");
		System.out.println(Generators.readFully(tokens));
		
		ILanParser parser = new ILanParser();
		ILanProgram program = parser.parseTokens(tokens);
		System.out.println("Program: " + program);
	}
}

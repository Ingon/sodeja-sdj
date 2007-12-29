package org.sodeja.ilan;

import org.sodeja.generator.Generator;
import org.sodeja.generator.Generators;
import org.sodeja.ilan.lexer_old.ILLexer;
import org.sodeja.ilan.lexer_old.Token;
import org.sodeja.ilan.parser.ILParser;
import org.sodeja.ilan.parser.Program;
import org.sodeja.ilan.runtime.ILRuntime;

public class ILan {
	public static void main(String[] args) {
		Generator<Token> tokens = ILLexer.tokenize("4 + 3;");
		System.out.println(Generators.readFully(tokens));
		
		ILParser parser = new ILParser();
		Program program = parser.parseTokens(tokens);
		
		System.out.println("Program: " + program);
		
		ILRuntime runtime = new ILRuntime();
		Object result = runtime.execute(program);
		
		System.out.println("Result: " + result);
	}
}

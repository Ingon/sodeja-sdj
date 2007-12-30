package org.sodeja.ilan;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.sodeja.ilan.lexer.Datum;
import org.sodeja.ilan.lexer.Lexer;

public class ILan {
	public static void main(String[] args) throws IOException {
		List<Datum> datums = Lexer.tokenize(new FileReader("test/base.ilan"));
		System.out.println("Datums: " + datums);
		
//		Generator<Token> tokens = ILLexer.tokenize("4 + 3;");
//		System.out.println(Generators.readFully(tokens));
//		
//		ILParser parser = new ILParser();
//		Program program = parser.parseTokens(tokens);
//		
//		System.out.println("Program: " + program);
//		
//		ILRuntime runtime = new ILRuntime();
//		Object result = runtime.execute(program);
//		
//		System.out.println("Result: " + result);
	}
}

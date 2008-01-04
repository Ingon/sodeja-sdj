package org.sodeja.il;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.sodeja.il.model.Expression;
import org.sodeja.il.runtime.Context;
import org.sodeja.il.runtime.RootContext;

public class IL {
	public static void main(String[] args) throws IOException {
//		ILLexer lexer = new ILLexer(new FileReader("test/simple.il"));
		ILLexer lexer = new ILLexer(new FileReader("test/tests.il"));
//		ILLexer lexer = new ILLexer(new FileReader("test/fancy.il"));
		List<String> tokens = lexer.tokenize();
//		System.out.println(tokens);
		
		ILParser parser = new ILParser();
		List<Expression> prog = parser.parse(tokens);
//		System.out.println(prog);
		
		Context ctx = new RootContext();
		for(Expression expr : prog) {
			System.out.println(" >" + expr);
			Object value = expr.eval(ctx);
			System.out.println("=>" + value);
		}
	}
}

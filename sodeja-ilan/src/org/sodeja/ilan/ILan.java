package org.sodeja.ilan;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.sodeja.ilan.ildk.ILObject;
import org.sodeja.ilan.lexer.Datum;
import org.sodeja.ilan.lexer.Lexer;
import org.sodeja.ilan.parser.Expression;
import org.sodeja.ilan.parser.Parser;
import org.sodeja.ilan.runtime.Runtime;

public class ILan {
	public static void main(String[] args) throws IOException {
		List<Datum> datums = Lexer.tokenize(new FileReader("test/parser.ilan"));
		List<Expression> expressions = Parser.parse(datums);
		Runtime runtime = new Runtime();
		for(Expression expr : expressions) {
			System.out.println(":>" + expr);
			ILObject value = runtime.eval(expr);
			System.out.println("=>" + value);
			System.out.println();
		}
	}
}

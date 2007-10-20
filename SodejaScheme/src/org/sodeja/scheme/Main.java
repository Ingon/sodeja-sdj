package org.sodeja.scheme;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.List;

import org.sodeja.scheme.execute.LispExecutor;
import org.sodeja.scheme.parse.Lexer;
import org.sodeja.scheme.parse.SemanticParser;
import org.sodeja.scheme.parse.model.Script;

public class Main {
	public static void main(String[] args) throws Exception {
		if(args.length == 0) {
			console();
			return;
		}
		
		long execStart = System.currentTimeMillis();
		for(String arg : args) {
			Lexer lex = new Lexer(new FileReader(arg));
			List<String> tokens = lex.tokenize();

			SemanticParser parser = new SemanticParser();
			Script script = parser.parse(tokens);
			
			LispExecutor executor = new LispExecutor();
			executor.execute(script);
		}
		System.out.println("TOTAL: " + (System.currentTimeMillis() - execStart));
	}

	private static void console() throws IOException {
		SemanticParser parser = new SemanticParser();
		LispExecutor executor = new LispExecutor();
		
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		
		while(true) {
			System.out.print("=> ");
			String line = reader.readLine();
			
			Lexer lex = new Lexer(new StringReader(line));
			List<String> tokens = lex.tokenize();
			Script script = parser.parse(tokens);
			executor.execute(script);
		}
	}
}

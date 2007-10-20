package org.sodeja.scheme2;

import java.io.FileReader;
import java.io.IOException;
import java.util.Deque;

import org.sodeja.scheme2.execute.Runtime;
import org.sodeja.scheme2.model.Token;
import org.sodeja.scheme2.parse.StreamParser;

public class Main {
	public static void main(String[] args) throws Exception {
		if(args.length == 0) {
			console();
			return;
		}

		long execStart = System.currentTimeMillis();
		StreamParser parser = new StreamParser();
		for(String arg : args) {
			Runtime runtime = new Runtime();
			Deque<Token> tokens = parser.tokenize(new FileReader(arg));
			for(Token tok : tokens) {
				System.out.println(tok);
				long start = System.currentTimeMillis();
				Object obj = runtime.evalToken(tok);
				long end = System.currentTimeMillis();
				System.out.println("(" + (end - start) + ")>" + obj);
			}
		}
		System.out.println("TOTAL: " + (System.currentTimeMillis() - execStart));
	}

	private static void console() throws IOException {
//		SemanticParser parser = new SemanticParser();
//		LispExecutor executor = new LispExecutor();
//		
//		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
//		
//		while(true) {
//			System.out.print("=> ");
//			String line = reader.readLine();
//			
//			Lexer lex = new Lexer(new StringReader(line));
//			List<String> tokens = lex.tokenize();
//			Script script = parser.parse(tokens);
//			executor.execute(script);
//		}
	}
}

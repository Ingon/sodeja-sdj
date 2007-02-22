package org.sodeja.sdj;

import java.io.FileReader;
import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;
import org.sodeja.functional.Pair;
import org.sodeja.parsec.Parser;
import org.sodeja.sdj.expression.Name;
import org.sodeja.sdj.expression.Program;
import org.sodeja.sdj.lexer.Lexer;
import org.sodeja.sdj.lexer.Token;
import org.sodeja.sdj.parser.SdjParser;

public class Main {
	public static void main(String[] args) throws Exception {
//		List<String> initial = ListUtils.asList("hello", "John", "!");
//		System.out.println("Initial: " + initial);
//		List<Pair<String, List<String>>> result = PLit.Util.getParserFor("hello").execute(initial);
//		System.out.println("Result: " + result);
		
//		List<String> initial = Collections.unmodifiableList(ListUtils.asList("hello", "John", "!", "hello", "John", "!"));
//		System.out.println("Initial: " + initial);
//		
//		Parser<String, String> helloOrGoodbye = new AlternativeParser<String, String>(
//				new PLit("hello"), new PLit("goodbye"));
//		
//		Parser<String, Pair<String, String>> greeting = new ThenParser3<String, Pair<String, String>, String, String, String>(
//				helloOrGoodbye, new PVar(), new PLit("!"), 
//				new Function3<Pair<String, String>, String, String, String>() {
//					public Pair<String, String> execute(String p1, String p2, String p3) {
//						return new Pair<String, String>(p1, p2);
//					}
//				});
//
//		System.out.println("Result(greeting): " + greeting.execute(initial));
//		
//		Parser<String, List<Pair<String, String>>> oneOrMoreGreetings = new OneOrMoreParser<String, Pair<String, String>>(greeting);
//		System.out.println("Result(oneOrMoreGreetings): " + oneOrMoreGreetings.execute(initial));
		
		Lexer lexer = new Lexer(new FileReader("test1.sdj"));
		List<Token> tokens = lexer.tokenize();
		for(Token tok : tokens) {
			System.out.format("%s: <%s> ", tok.line, tok.name);
			System.out.println();
		}
		
		List<String> strTokens = ListUtils.map(tokens, new Function1<String, Token>() {
			public String execute(Token p) {
				return p.name;
			}});
		
		Parser<String, Program<Name>> program = SdjParser.PROGRAM_PARSER;
		List<Pair<Program<Name>, List<String>>> parserResult = program.execute(strTokens);
		
//		System.out.println("Result: " + parserResult);
	}
}

package org.sodeja.sdj;

import java.io.FileReader;
import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;
import org.sodeja.sdj.expression.Name;
import org.sodeja.sdj.expression.Program;
import org.sodeja.sdj.lexer.Lexer;
import org.sodeja.sdj.lexer.Token;
import org.sodeja.sdj.parser.SdjParser;
import org.sodeja.sdj.ti.TiState;

public class Main {
	public static void main(String[] args) throws Exception {
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
		
		SdjParser sdjParser = new SdjParser();
		Program<Name> program = sdjParser.parse(strTokens);
		
		PrettyPrinter.print(program);
		
		TiState compiled = new TiState(program);
	}
}

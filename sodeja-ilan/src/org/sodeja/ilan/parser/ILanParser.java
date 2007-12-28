package org.sodeja.ilan.parser;

import static org.sodeja.parsec.combinator.ParsecCombinators.*;

import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.functional.Function1;
import org.sodeja.ilan.lexer.Identifier;
import org.sodeja.ilan.lexer.Serparator;
import org.sodeja.ilan.lexer.Token;
import org.sodeja.parsec.AbstractParser;
import org.sodeja.parsec.ParseError;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.ParsingResult;
import org.sodeja.parsec.semantic.AbstractSemanticParser;

public class ILanParser extends AbstractSemanticParser<Token, Program> {
	
	private Parser<Token, Serparator> SEMI_COLUMN = new AbstractParser<Token, Serparator>("SEMI_COLUMN") {
		@Override
		protected ParsingResult<Token, Serparator> executeDelegate(ConsList<Token> tokens) {
			Token head = tokens.head();
			if(! (head instanceof Serparator)) {
				return new ParseError<Token, Serparator>("Not an separator", tokens);
			}
			Serparator sep = (Serparator) head;
			if(sep != Serparator.SEMI_COLON) {
				return new ParseError<Token, Serparator>("Not semi-column separator", tokens);
			}
			return success(sep, tokens.tail());
		}
	};
		
	private Parser<Token, org.sodeja.ilan.lexer.Number> NUMBER_DEL = new AbstractParser<Token, org.sodeja.ilan.lexer.Number>("NUMBER_DEL") {
		@Override
		protected ParsingResult<Token, org.sodeja.ilan.lexer.Number> executeDelegate(ConsList<Token> tokens) {
			Token head = tokens.head();
			if(! (head instanceof Number)) {
				return new ParseError<Token, org.sodeja.ilan.lexer.Number>("Not an identifier", tokens);
			}
			return success((org.sodeja.ilan.lexer.Number) head, tokens.tail());
		}
	};
	
//	private Parser<Token, ValueExpression<org.sodeja.ilan.idk.Number>> NUMBER = apply("NUMBER", NUMBER_DEL, new Function1<ValueExpression<Number>, Number>() {
//		@Override
//		public ValueExpression<Number> execute(Number p) {
//			return new ValueExpression<Number>(p);
//		}});
		
	private Parser<Token, Identifier> IDENTIFIER = new AbstractParser<Token, Identifier>("IDENTIFIER") {
		@Override
		protected ParsingResult<Token, Identifier> executeDelegate(ConsList<Token> tokens) {
			Token head = tokens.head();
			if(! (head instanceof Identifier)) {
				return new ParseError<Token, Identifier>("Not an identifier", tokens);
			}
			return success((Identifier) head, tokens.tail());
		}
	};
	
//	private Parser<ILanToken, >
		
	private Parser<Token, List<Identifier>> IDENTIFIERS = zeroOrMore("IDENTIFIERS", IDENTIFIER);
	
	private Parser<Token, Application> APPLICATION = thenParserCons1("APPLICATION", IDENTIFIERS, SEMI_COLUMN, Application.class);

	private Parser<Token, Expression> EXPRESSION = oneOf1("EXPRESSION", APPLICATION);

	private Parser<Token, List<Expression>> EXPRESSIONS = zeroOrMore("PROGRAM", EXPRESSION);
	
	private Parser<Token, Program> PROGRAM = applyCons("PROGRAM", EXPRESSIONS, Program.class);
	
	@Override
	protected Parser<Token, Program> getParser() {
		return PROGRAM;
	}
}

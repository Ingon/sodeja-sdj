package org.sodeja.ilan.parser;

import static org.sodeja.parsec.combinator.ParsecCombinators.*;

import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.functional.Function1;
import org.sodeja.ilan.lexer.IdentifierToken;
import org.sodeja.ilan.lexer.SerparatorToken;
import org.sodeja.ilan.lexer.Token;
import org.sodeja.parsec.AbstractParser;
import org.sodeja.parsec.ParseError;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.ParsingResult;
import org.sodeja.parsec.semantic.AbstractSemanticParser;

public class ILanParser extends AbstractSemanticParser<Token, Program> {
	
	private Parser<Token, SerparatorToken> SEMI_COLUMN = new AbstractParser<Token, SerparatorToken>("SEMI_COLUMN") {
		@Override
		protected ParsingResult<Token, SerparatorToken> executeDelegate(ConsList<Token> tokens) {
			Token head = tokens.head();
			if(! (head instanceof SerparatorToken)) {
				return new ParseError<Token, SerparatorToken>("Not an separator", tokens);
			}
			SerparatorToken sep = (SerparatorToken) head;
			if(sep != SerparatorToken.SEMI_COLON) {
				return new ParseError<Token, SerparatorToken>("Not semi-column separator", tokens);
			}
			return success(sep, tokens.tail());
		}
	};
		
	private Parser<Token, org.sodeja.ilan.lexer.NumberToken> NUMBER_DEL = new AbstractParser<Token, org.sodeja.ilan.lexer.NumberToken>("NUMBER_DEL") {
		@Override
		protected ParsingResult<Token, org.sodeja.ilan.lexer.NumberToken> executeDelegate(ConsList<Token> tokens) {
			Token head = tokens.head();
			if(! (head instanceof Number)) {
				return new ParseError<Token, org.sodeja.ilan.lexer.NumberToken>("Not an identifier", tokens);
			}
			return success((org.sodeja.ilan.lexer.NumberToken) head, tokens.tail());
		}
	};
	
//	private Parser<Token, ValueExpression<org.sodeja.ilan.idk.Number>> NUMBER = apply("NUMBER", NUMBER_DEL, new Function1<ValueExpression<Number>, Number>() {
//		@Override
//		public ValueExpression<Number> execute(Number p) {
//			return new ValueExpression<Number>(p);
//		}});
		
	private Parser<Token, IdentifierToken> IDENTIFIER = new AbstractParser<Token, IdentifierToken>("IDENTIFIER") {
		@Override
		protected ParsingResult<Token, IdentifierToken> executeDelegate(ConsList<Token> tokens) {
			Token head = tokens.head();
			if(! (head instanceof IdentifierToken)) {
				return new ParseError<Token, IdentifierToken>("Not an identifier", tokens);
			}
			return success((IdentifierToken) head, tokens.tail());
		}
	};
	
//	private Parser<ILanToken, >
		
	private Parser<Token, List<IdentifierToken>> IDENTIFIERS = zeroOrMore("IDENTIFIERS", IDENTIFIER);
	
	private Parser<Token, Application> APPLICATION = thenParserCons1("APPLICATION", IDENTIFIERS, SEMI_COLUMN, Application.class);

	private Parser<Token, Expression> EXPRESSION = oneOf1("EXPRESSION", APPLICATION);

	private Parser<Token, List<Expression>> EXPRESSIONS = zeroOrMore("PROGRAM", EXPRESSION);
	
	private Parser<Token, Program> PROGRAM = applyCons("PROGRAM", EXPRESSIONS, Program.class);
	
	@Override
	protected Parser<Token, Program> getParser() {
		return PROGRAM;
	}
}

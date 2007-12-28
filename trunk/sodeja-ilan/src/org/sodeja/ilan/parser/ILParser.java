package org.sodeja.ilan.parser;

import static org.sodeja.parsec.combinator.ParsecCombinators.*;

import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.functional.Function1;
import org.sodeja.ilan.ildk.ILInteger;
import org.sodeja.ilan.ildk.ILNumber;
import org.sodeja.ilan.lexer.IdentifierToken;
import org.sodeja.ilan.lexer.NumberToken;
import org.sodeja.ilan.lexer.SerparatorToken;
import org.sodeja.ilan.lexer.Token;
import org.sodeja.parsec.AbstractParser;
import org.sodeja.parsec.ParseError;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.ParsingResult;
import org.sodeja.parsec.semantic.AbstractSemanticParser;

public class ILParser extends AbstractSemanticParser<Token, Program> {
	
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
		
	private Parser<Token, NumberToken> NUMBER_DEL = new AbstractParser<Token, NumberToken>("NUMBER_DEL") {
		@Override
		protected ParsingResult<Token, NumberToken> executeDelegate(ConsList<Token> tokens) {
			Token head = tokens.head();
			if(! (head instanceof NumberToken)) {
				return new ParseError<Token, NumberToken>("Not an identifier", tokens);
			}
			return success((NumberToken) head, tokens.tail());
		}
	};
	
	private Parser<Token, ValueExpression<ILNumber>> NUMBER = apply("NUMBER", NUMBER_DEL, new Function1<ValueExpression<ILNumber>, NumberToken>() {
		@Override
		public ValueExpression<ILNumber> execute(NumberToken p) {
			return new ValueExpression<ILNumber>(new ILInteger(p.value));
		}});
	
	private Parser<Token, IdentifierToken> IDENTIFIER_DEL = new AbstractParser<Token, IdentifierToken>("IDENTIFIER_DEL") {
		@Override
		protected ParsingResult<Token, IdentifierToken> executeDelegate(ConsList<Token> tokens) {
			Token head = tokens.head();
			if(! (head instanceof IdentifierToken)) {
				return new ParseError<Token, IdentifierToken>("Not an identifier", tokens);
			}
			return success((IdentifierToken) head, tokens.tail());
		}
	};
	
	private Parser<Token, VariableExpression> IDENTIFIER = apply("IDENTIFIER", IDENTIFIER_DEL, new Function1<VariableExpression, IdentifierToken>() {
		@Override
		public VariableExpression execute(IdentifierToken p) {
			return new VariableExpression(p.name);
		}});
	
	private Parser<Token, SimpleExpression> SIMPLE_EXPRESSION = oneOf1("SIMPLE_EXPRESSION", NUMBER, IDENTIFIER);
		
	private Parser<Token, List<SimpleExpression>> SIMPLE_EXPRESSIONS = zeroOrMore("SIMPLE_EXPRESSIONS", SIMPLE_EXPRESSION);
	
	private Parser<Token, Application> APPLICATION = thenParserCons1("APPLICATION", SIMPLE_EXPRESSIONS, SEMI_COLUMN, Application.class);

	private Parser<Token, Expression> EXPRESSION = oneOf1("EXPRESSION", APPLICATION);

	private Parser<Token, List<Expression>> EXPRESSIONS = zeroOrMore("PROGRAM", EXPRESSION);
	
	private Parser<Token, Program> PROGRAM = applyCons("PROGRAM", EXPRESSIONS, Program.class);
	
	@Override
	protected Parser<Token, Program> getParser() {
		return PROGRAM;
	}
}

package org.sodeja.ilan.parser;

import static org.sodeja.parsec.combinator.ParsecCombinators.*;

import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.ilan.lexer.ILanIdentifier;
import org.sodeja.ilan.lexer.ILanNumber;
import org.sodeja.ilan.lexer.ILanSerparator;
import org.sodeja.ilan.lexer.ILanToken;
import org.sodeja.parsec.AbstractParser;
import org.sodeja.parsec.ParseError;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.ParsingResult;
import org.sodeja.parsec.semantic.AbstractSemanticParser;

public class ILanParser extends AbstractSemanticParser<ILanToken, ILanProgram> {
	
	private Parser<ILanToken, ILanSerparator> SEMI_COLUMN = new AbstractParser<ILanToken, ILanSerparator>("SEMI_COLUMN") {
		@Override
		protected ParsingResult<ILanToken, ILanSerparator> executeDelegate(ConsList<ILanToken> tokens) {
			ILanToken head = tokens.head();
			if(! (head instanceof ILanSerparator)) {
				return new ParseError<ILanToken, ILanSerparator>("Not an separator", tokens);
			}
			ILanSerparator sep = (ILanSerparator) head;
			if(sep != ILanSerparator.SEMI_COLON) {
				return new ParseError<ILanToken, ILanSerparator>("Not semi-column separator", tokens);
			}
			return success(sep, tokens.tail());
		}
	};
		
	private Parser<ILanToken, ILanNumber> NUMBER = new AbstractParser<ILanToken, ILanNumber>("Number") {
		@Override
		protected ParsingResult<ILanToken, ILanNumber> executeDelegate(ConsList<ILanToken> tokens) {
			ILanToken head = tokens.head();
			if(! (head instanceof ILanNumber)) {
				return new ParseError<ILanToken, ILanNumber>("Not an identifier", tokens);
			}
			return success((ILanNumber) head, tokens.tail());
		}
	};
		
	private Parser<ILanToken, ILanIdentifier> IDENTIFIER = new AbstractParser<ILanToken, ILanIdentifier>("IDENTIFIER") {
		@Override
		protected ParsingResult<ILanToken, ILanIdentifier> executeDelegate(ConsList<ILanToken> tokens) {
			ILanToken head = tokens.head();
			if(! (head instanceof ILanIdentifier)) {
				return new ParseError<ILanToken, ILanIdentifier>("Not an identifier", tokens);
			}
			return success((ILanIdentifier) head, tokens.tail());
		}
	};
	
//	private Parser<ILanToken, >
		
	private Parser<ILanToken, List<ILanIdentifier>> IDENTIFIERS = zeroOrMore("IDENTIFIERS", IDENTIFIER);
	
	private Parser<ILanToken, ILanApplication> APPLICATION = thenParserCons1("APPLICATION", IDENTIFIERS, SEMI_COLUMN, ILanApplication.class);

	private Parser<ILanToken, ILanExpression> EXPRESSION = oneOf1("EXPRESSION", APPLICATION);

	private Parser<ILanToken, List<ILanExpression>> EXPRESSIONS = zeroOrMore("PROGRAM", EXPRESSION);
	
	private Parser<ILanToken, ILanProgram> PROGRAM = applyCons("PROGRAM", EXPRESSIONS, ILanProgram.class);
	
	@Override
	protected Parser<ILanToken, ILanProgram> getParser() {
		return PROGRAM;
	}
}

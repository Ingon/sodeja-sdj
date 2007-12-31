package org.sodeja.il;

import static org.sodeja.parsec.combinator.ParsecCombinators.*;
import static org.sodeja.parsec.standart.StandartParsers.*;

import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.parsec.AbstractParser;
import org.sodeja.parsec.ParseError;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.ParsingResult;
import org.sodeja.parsec.combinator.DelegateParser;
import org.sodeja.parsec.semantic.AbstractSemanticParser;

public class ILParser extends AbstractSemanticParser<String, Program> {

	private final DelegateParser<String, List<Expression>> EXPRESSIONS_DEL = new DelegateParser<String, List<Expression>>("EXPRESSIONS_DEL");
	
	private final Parser<String, String> IDENTIFIER = new AbstractParser<String, String>("IDENTIFIER") {
		@Override
		protected ParsingResult<String, String> executeDelegate(ConsList<String> tokens) {
			String head = tokens.head();
			if(head.length() > 1) {
				return success(head, tokens.tail());
			}
			
			char ch = head.charAt(0);
			if(ILLexer.isDivider(ch)) {
				return new ParseError<String, String>("Expecting identifier", tokens);
			}
			
			return success(head, tokens.tail());
		}};
	
	private final Parser<String, List<String>> IDENTIFIERS = zeroOrMore("IDENTIFIERS", IDENTIFIER);
	
	private final Parser<String, ApplyExpression> APPLY = thenParserCons1("APPLY", IDENTIFIERS, literal(";"), ApplyExpression.class);
	
	private final Parser<String, BlockExpression> BLOCK = thenParser3Cons2("BLOCK", literal("{"), EXPRESSIONS_DEL, literal("}"), BlockExpression.class);
	
	private final Parser<String, Expression> FUNCTION_BODY = oneOf1("FUNCTION_BODY", BLOCK, APPLY);
	
	private final Parser<String, FunctionExpression> FUNCTION = thenParser3Cons13("FUNCTION", IDENTIFIERS, literal("="), FUNCTION_BODY, FunctionExpression.class);
	
	private final Parser<String, ClassExpression> CLASS = thenParser3Cons23("CLASS", literal("class"), IDENTIFIER, BLOCK, ClassExpression.class);

	private final Parser<String, Expression> EXPRESSION = oneOf1("EXPRESSION", CLASS, BLOCK, FUNCTION, APPLY);
	
	private final Parser<String, List<Expression>> EXPRESSIONS = zeroOrMore("EXPRESSIONS", EXPRESSION);
	
	private final Parser<String, Program> PROGRAM = applyCons("PROGRAM", EXPRESSIONS, Program.class);
	
	public ILParser() {
		EXPRESSIONS_DEL.delegate = EXPRESSIONS;
	}
	
	@Override
	protected Parser<String, Program> getParser() {
		return PROGRAM;
	}
}

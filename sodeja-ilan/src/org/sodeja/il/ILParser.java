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

public class ILParser extends AbstractSemanticParser<String, List<Expression>> {

	private final DelegateParser<String, List<Expression>> ROOT_EXPRESSIONS_DEL = new DelegateParser<String, List<Expression>>("ROOT_EXPRESSIONS_DEL");
	
	private final DelegateParser<String, Expression> EXPRESSION_DEL = new DelegateParser<String, Expression>("EXPRESSIONS_DEL");
	
	private final DelegateParser<String, ApplyExpression> APPLY_DEL = new DelegateParser<String, ApplyExpression>("APPLY_DEL");
	
	private final Parser<String, VariableExpression> IDENTIFIER = new AbstractParser<String, VariableExpression>("IDENTIFIER") {
		@Override
		protected ParsingResult<String, VariableExpression> executeDelegate(ConsList<String> tokens) {
			String head = tokens.head();
			if(head == ILLexer.CRLF) {
				return new ParseError<String, VariableExpression>("Expecting identifier", tokens);
			}
			
			if(head.length() > 1) {
				return success(new VariableExpression(head), tokens.tail());
			}
			
			char ch = head.charAt(0);
			if(ILLexer.isDivider(ch)) {
				return new ParseError<String, VariableExpression>("Expecting identifier", tokens);
			}
			
			return success(new VariableExpression(head), tokens.tail());
		}};
	
	private final Parser<String, List<VariableExpression>> IDENTIFIERS = oneOrMore("IDENTIFIERS", IDENTIFIER);
	
//	private final Parser<String, Boolean> OPTIONAL_EOL = zeroOrOneBoolean("OPTIONAL_EOL", literal(ILLexer.CRLF));
	
	private final Parser<String, String> CURLY_OPEN = thenParserJust1("CURLY_OPEN", literal("{"), literal(ILLexer.CRLF));

	private final Parser<String, String> CURLY_CLOSE = thenParserJust1("CURLY_OPEN", literal(ILLexer.CRLF), literal("}"));
	
	private final Parser<String, BlockExpression> BLOCK = thenParser3Cons2("BLOCK", CURLY_OPEN, ROOT_EXPRESSIONS_DEL, CURLY_CLOSE, BlockExpression.class);
	
	private final Parser<String, Expression> LAMBDA_BODY = oneOf1("LAMBDA_BODY", BLOCK, EXPRESSION_DEL);
	
	private final Parser<String, LambdaExpression> LAMBDA = thenParser4Cons24("LAMBDA", literal("\\"), IDENTIFIERS, literal("="), LAMBDA_BODY, LambdaExpression.class);
	
	private final Parser<String, PrecedenceExpression> PRECEDENSE = thenParser3Cons2("PRECEDENCE", literal("("), EXPRESSION_DEL, literal(")"), PrecedenceExpression.class);

	private final Parser<String, SetExpression> SET = thenParser3Cons13("SET", IDENTIFIER, literal("="), EXPRESSION_DEL, SetExpression.class);
	
	private final Parser<String, FunctionExpression> FUNCTION = thenParser4Cons24("FUNCTION", literal("fun"), IDENTIFIERS, literal("="), LAMBDA_BODY, FunctionExpression.class);
	
	private final Parser<String, ClassExpression> CLASS = thenParser3Cons23("CLASS", literal("class"), IDENTIFIER, BLOCK, ClassExpression.class);

	private final Parser<String, Expression> SIMPLE_EXPRESSION = oneOf1("EXPRESSION", LAMBDA, PRECEDENSE, IDENTIFIER);
	
	private final Parser<String, List<Expression>> APPLY_BODY = nOrMore("SIMPLE_EXPRESSIONS", SIMPLE_EXPRESSION, 2);
	
	private final Parser<String, ApplyExpression> APPLY = applyCons("APPLY", APPLY_BODY, ApplyExpression.class);
	
	private final Parser<String, Expression> EXPRESSION = oneOf1("EXPRESSION", APPLY, SIMPLE_EXPRESSION);
	
	private final Parser<String, Expression> ROOT_EXPRESSION = oneOf1("ROOT_EXPRESSION", CLASS, FUNCTION, BLOCK, SET, EXPRESSION);
	 
	private final Parser<String, List<Expression>> ROOT_EXPRESSIONS = oneOrMoreSep("ROOT_EXPRESSIONS", ROOT_EXPRESSION, literal(ILLexer.CRLF));	
	
	public ILParser() {
		ROOT_EXPRESSIONS_DEL.delegate = ROOT_EXPRESSIONS;
		EXPRESSION_DEL.delegate = EXPRESSION;
		APPLY_DEL.delegate = APPLY;
	}
	
	@Override
	protected Parser<String, List<Expression>> getParser() {
		return ROOT_EXPRESSIONS;
	}
}

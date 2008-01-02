package org.sodeja.il;

import static org.sodeja.parsec.combinator.ParsecCombinators.*;
import static org.sodeja.parsec.standart.StandartParsers.*;

import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.functional.Function1;
import org.sodeja.functional.FunctionN;
import org.sodeja.parsec.AbstractParser;
import org.sodeja.parsec.ParseError;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.ParsingResult;
import org.sodeja.parsec.combinator.DelegateParser;
import org.sodeja.parsec.combinator.ThenParserN;
import org.sodeja.parsec.semantic.AbstractSemanticParser;

public class ILParser extends AbstractSemanticParser<String, Program> {

	private final DelegateParser<String, List<Expression>> EXPRESSIONS_DEL = new DelegateParser<String, List<Expression>>("EXPRESSIONS_DEL");
	
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
	
	private final Parser<String, NOPExpression> EOL = apply("EOL_EXPRESSION", literal(ILLexer.CRLF), new Function1<NOPExpression, String>() {
		@Override
		public NOPExpression execute(String p) {
			return NOPExpression.instance;
		}});
	
	private final Parser<String, List<VariableExpression>> IDENTIFIERS = zeroOrMore("IDENTIFIERS", IDENTIFIER);

	private final DelegateParser<String, List<Expression>> APPLY_BODYS_DEL = new DelegateParser<String, List<Expression>>("APPLY_BODY_DEL");
	
	private final Parser<String, PrecedenceExpression> PRECEDENSE = thenParser3Cons2("PRECEDENCE", literal("("), APPLY_BODYS_DEL, literal(")"), PrecedenceExpression.class);
	
	private final Parser<String, Expression> APPLY_BODY = alternative1("APPLY_BODY", PRECEDENSE, IDENTIFIER);

	private final Parser<String, List<Expression>> APPLY_BODYS = oneOrMore("APPLY_BODYS", APPLY_BODY);
	
	private final Parser<String, ApplyExpression> APPLY = thenParserCons1("APPLY", APPLY_BODYS , EOL, ApplyExpression.class);
	
	private final Parser<String, SetExpression> SET = thenParser3Cons13("SET", IDENTIFIER, literal("="), APPLY, SetExpression.class);
	
	private final Parser<String, BlockExpression> BLOCK = thenParser3Cons2("BLOCK", literal("{"), EXPRESSIONS_DEL, literal("}"), BlockExpression.class);
	
	private final Parser<String, Expression> FUNCTION_BODY = oneOf1("FUNCTION_BODY", BLOCK, APPLY);
	
	@SuppressWarnings("unchecked")
	private final Parser<String, FunctionExpression> FUNCTION = new ThenParserN<String, FunctionExpression>("FUNCTION",
			new FunctionN<FunctionExpression, Object>() {
				@Override
				public FunctionExpression execute(Object... obj) {
					return new FunctionExpression((VariableExpression) obj[1], (List<VariableExpression>) obj[2], (Expression) obj[4]);
				}}, 
		literal("def"), IDENTIFIER, IDENTIFIERS, literal("="), FUNCTION_BODY);
	
	private final Parser<String, ClassExpression> CLASS = thenParser3Cons23("CLASS", literal("class"), IDENTIFIER, BLOCK, ClassExpression.class);

	private final Parser<String, Expression> EXPRESSION = oneOf1("EXPRESSION", CLASS, BLOCK, FUNCTION, SET, APPLY, EOL);
	
	private final Parser<String, List<Expression>> EXPRESSIONS = oneOrMore("EXPRESSIONS", EXPRESSION);
	
	private final Parser<String, Program> PROGRAM = applyCons("PROGRAM", EXPRESSIONS, Program.class);
	
	public ILParser() {
		EXPRESSIONS_DEL.delegate = EXPRESSIONS;
		APPLY_BODYS_DEL.delegate = APPLY_BODYS;
	}
	
	@Override
	protected Parser<String, Program> getParser() {
		return PROGRAM;
	}
}

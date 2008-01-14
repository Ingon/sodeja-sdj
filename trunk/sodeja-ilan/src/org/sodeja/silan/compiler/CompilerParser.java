package org.sodeja.silan.compiler;

import static org.sodeja.parsec.combinator.ParsecCombinators.*;
import static org.sodeja.parsec.standart.StandartParsers.*;

import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.parsec.AbstractParser;
import org.sodeja.parsec.ParseError;
import org.sodeja.parsec.ParseSuccess;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.ParsingResult;

public class CompilerParser {

	private final Parser<String, String> IDENTIFIER = javaIdentifier("IDENTIFIER");
	
	private final Parser<String, String> KEYWORD = new AbstractParser<String, String>("KEYWORD") {
		@Override
		protected ParsingResult<String, String> executeDelegate(ConsList<String> tokens) {
			String head = tokens.head();
			if(! head.endsWith(":")) {
				return new ParseError<String, String>("Keword expected but was: " + head, tokens);
			}
			
			String sub = head.substring(0, head.length() - 1);
			return IDENTIFIER.execute(new ConsList<String>(sub, tokens.tail()));
		}};
	
	private final Parser<String, String> WHITESPACE = literal(CompilerLexer.WHITESPACE);
	
	private final Parser<String, String> OP_WHITESPACE = zeroOrOne("OP_WHITESPACE", WHITESPACE);

	private final Parser<String, IntegerLiteral> INTEGER_LITERAL = applyCons("INTEGER_LITERAL", simpleIntegerParser("INTEGER"), IntegerLiteral.class);

//	private final Parser<String, Literal> LITERAL = oneOf1(CONSTANT_REFERENCE, INTEGER_LITERAL, CHARACTER_LITERAL, STRING_LITERAL);
	private final Parser<String, Literal> LITERAL = oneOf1("LITERAL", INTEGER_LITERAL);
	
	private final Parser<String, Reference> REFERENCE = applyCons("REFERENCE", IDENTIFIER, Reference.class);
	
//	private final Parser<String, Primary> PRIMARY = oneOf1("PRIMARY", LITERAL, REFERENCE, NESTED);
	private final Parser<String, Primary> PRIMARY = oneOf1("PRIMARY", LITERAL, REFERENCE);
	
	private final Parser<String, List<Message>> MESSAGES = null;
	
	private final Parser<String, Expression> EXPRESSION = thenParserCons("EXPRESSION", PRIMARY, MESSAGES, Expression.class);
	
	private final Parser<String, String> ASSIGNMENT = thenParserJust1("ASSIGNMENT", IDENTIFIER, literal(":="));

	private final Parser<String, String> OPTIONAL_ASSIGNMENT = zeroOrOne("OPTIONAL_ASSIGNMENT", ASSIGNMENT);
	
	private final Parser<String, Statement> STATEMENT_INT = thenParser3Cons13("STATEMENT_INT", OPTIONAL_ASSIGNMENT, OP_WHITESPACE, EXPRESSION, Statement.class);
	
	private final Parser<String, Statement> STATEMENT = thenParser3Just1("STATEMENT", STATEMENT_INT, OP_WHITESPACE, literal("."));
	
	private final Parser<String, Statement> FINAL_STATEMENT = thenParser3Just3("FINAL_STATEMENT", OP_WHITESPACE, literal("^"), STATEMENT);

	private final Parser<String, Statement> OPTIONAL_FINAL_STATEMENT = zeroOrOne("OPTIONAL_FINAL_STATEMENT", FINAL_STATEMENT);
	
	private final Parser<String, List<Statement>> STATEMENTS = zeroOrMore("STATEMENTS", STATEMENT);
	
	private final Parser<String, List<String>> IDENTIFIER_LIST = oneOrMoreSep("IDENTIFIER_LIST", IDENTIFIER, WHITESPACE);
	
	private final Parser<String, List<String>> LOCAL_VARIABLES_INT = thenParser3Just2("LOCAL_VARIABLES_INT", OP_WHITESPACE, IDENTIFIER_LIST, OP_WHITESPACE);
	
	private final Parser<String, List<String>> LOCAL_VARIABLES = thenParser4Just3("LOCAL_VARIABLES", OP_WHITESPACE, literal("|"), LOCAL_VARIABLES_INT, literal("|"));
	
	private final Parser<String, List<String>> OPTIONAL_LOCAL_VARIABLE = zeroOrOne("OPTIONAL_LOCAL_VARIABLE", LOCAL_VARIABLES);
	
	private final Parser<String, ExecutableCode> EXECUTABLE_CODE = thenParser3Cons123("EXECUTABLE_CODE", OPTIONAL_LOCAL_VARIABLE, STATEMENTS, OPTIONAL_FINAL_STATEMENT, ExecutableCode.class);
	
	CompilerParser() {
	}
	
	public ExecutableCode parseCode(List<String> tokensList) {
		ConsList<String> tokens = ConsList.createList(tokensList);
		ParsingResult<String, ExecutableCode> parseResults = EXECUTABLE_CODE.execute(tokens);
		if(parseResults instanceof ParseError) {
			throw new RuntimeException(((ParseError<String, ExecutableCode>) parseResults).error);
		}
		
		return ((ParseSuccess<String, ExecutableCode>) parseResults).result;
	}
	
	public Method parseMethod(List<String> tokens) {
		throw new UnsupportedOperationException();
	}
}

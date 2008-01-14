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
import org.sodeja.parsec.combinator.DelegateParser;

public class CompilerParser {

	private final Parser<Token, String> IDENTIFIER = matchByType(TokenType.IDENTIFIER);
	
	private final Parser<Token, String> KEYWORD = matchByType(TokenType.KEYWORD);
	
	private final Parser<Token, String> BINARY = matchByType(TokenType.OPERATOR);
		
	private final Parser<Token, String> WHITESPACE = matchByType(TokenType.WHITESPACE);
	
	private final Parser<Token, String> OP_WHITESPACE = zeroOrOne("OP_WHITESPACE", WHITESPACE);

	private final Parser<Token, String> INTEGER = matchByType(TokenType.INTEGER);
	
	private final Parser<Token, IntegerLiteral> INTEGER_LITERAL = applyCons("INTEGER_LITERAL", INTEGER, IntegerLiteral.class);

//	private final Parser<String, Literal> LITERAL = oneOf1(CONSTANT_REFERENCE, INTEGER_LITERAL, CHARACTER_LITERAL, STRING_LITERAL);
	private final Parser<Token, Literal> LITERAL = oneOf1("LITERAL", INTEGER_LITERAL);
	
	private final Parser<Token, Reference> REFERENCE = applyCons("REFERENCE", IDENTIFIER, Reference.class);
	
//	private final Parser<String, Primary> PRIMARY = oneOf1("PRIMARY", LITERAL, REFERENCE, NESTED);
	private final Parser<Token, Primary> PRIMARY = oneOf1("PRIMARY", LITERAL, REFERENCE);

	private final Parser<Token, UnaryMessage> UNARY_MESSAGE = applyCons("UNARY_MESSAGE", IDENTIFIER, UnaryMessage.class);

	private final Parser<Token, List<UnaryMessage>> UNARY_MESSAGE_CHAIN_ZERO = zeroOrMore("UNARY_MESSAGE_CHAIN_ZERO", thenParserJust2("UNARY_MESSAGE_CHAIN_ZERO_INT", OP_WHITESPACE, UNARY_MESSAGE));

	private final Parser<Token, List<UnaryMessage>> UNARY_MESSAGE_CHAIN_ONE = oneOrMoreSep("UNARY_MESSAGE_CHAIN_ONE", UNARY_MESSAGE, UNARY_MESSAGE);
	
	private final Parser<Token, BinaryMessageOperand> BINARY_OPERAND = thenParserCons("BINARY_OPERAND", PRIMARY, UNARY_MESSAGE_CHAIN_ZERO, BinaryMessageOperand.class);
	
	private final Parser<Token, BinaryMessage> BINARY_MESSAGE = thenParser3Cons13("BINARY_MESSAGE", BINARY, OP_WHITESPACE, BINARY_OPERAND, BinaryMessage.class);

	private final Parser<Token, List<BinaryMessage>> BINARY_CHAIN_ZERO = zeroOrMore("BINARY_CHAIN_ZERO", thenParserJust2("BINARY_CHAIN_INT", OP_WHITESPACE, BINARY_MESSAGE));

	private final Parser<Token, List<BinaryMessage>> BINARY_CHAIN_ONE = oneOrMoreSep("BINARY_CHAIN_ONE", BINARY_MESSAGE, OP_WHITESPACE);
	
	private final Parser<Token, KeywordMessageArgument> KEYWORD_MESSAGE_ARGUMENT = thenParser4Cons("KEYWORD_MESSAGE_ARGUMENT", KEYWORD, PRIMARY, UNARY_MESSAGE_CHAIN_ZERO, BINARY_CHAIN_ZERO, KeywordMessageArgument.class);
	
	private final Parser<Token, KeywordMessage> KEYWORD_MESSAGE = applyCons("KEYWORD_MESSAGE", oneOrMore("KEYWORD_MESSAGE_ARGUMENTS", KEYWORD_MESSAGE_ARGUMENT), KeywordMessage.class);

	private final Parser<Token, KeywordMessage> OPTIONAL_KEYWORD_MESSAGE = zeroOrOne("OPTIONAL_KEYWORD_MESSAGE", KEYWORD_MESSAGE);
	
	private final Parser<Token, BinaryRootMessage> BINARY_ROOT_MESSAGE = thenParserCons("BINARY_ROOT_MESSAGE", BINARY_CHAIN_ONE, OPTIONAL_KEYWORD_MESSAGE, BinaryRootMessage.class);
	
	private final Parser<Token, UnaryRootMessage> UNARY_ROOT_MESSAGE = thenParser3Cons123("UNARY_ROOT_MESSAGE", UNARY_MESSAGE_CHAIN_ONE, BINARY_CHAIN_ZERO, OPTIONAL_KEYWORD_MESSAGE, UnaryRootMessage.class);

	private final Parser<Token, Message> MESSAGE = thenParserJust2("MESSAGE", OP_WHITESPACE, (Parser) oneOf1("MESSAGE", UNARY_ROOT_MESSAGE, BINARY_ROOT_MESSAGE, KEYWORD_MESSAGE));
	
	private final Parser<Token, List<Message>> MESSAGES = zeroOrMoreSep("MESSAGES", MESSAGE, matchLiteral(";"));
	
	private final Parser<Token, Expression> EXPRESSION = thenParserCons("EXPRESSION", PRIMARY, MESSAGES, Expression.class);
	
	private final Parser<Token, String> ASSIGNMENT = thenParser4Just2("ASSIGNMENT", OP_WHITESPACE, IDENTIFIER, OP_WHITESPACE, matchLiteral(":="));

	private final Parser<Token, String> OPTIONAL_ASSIGNMENT = zeroOrOne("OPTIONAL_ASSIGNMENT", ASSIGNMENT);
	
	private final Parser<Token, Statement> STATEMENT_INT = thenParser3Cons13("STATEMENT_INT", OPTIONAL_ASSIGNMENT, OP_WHITESPACE, EXPRESSION, Statement.class);
	
	private final Parser<Token, Statement> STATEMENT = thenParser3Just1("STATEMENT", STATEMENT_INT, OP_WHITESPACE, matchLiteral("."));
	
	private final Parser<Token, Statement> FINAL_STATEMENT = thenParser3Just3("FINAL_STATEMENT", OP_WHITESPACE, matchLiteral("^"), STATEMENT);

	private final Parser<Token, Statement> OPTIONAL_FINAL_STATEMENT = zeroOrOne("OPTIONAL_FINAL_STATEMENT", FINAL_STATEMENT);
	
	private final Parser<Token, List<Statement>> STATEMENTS = zeroOrMore("STATEMENTS", STATEMENT);
	
	private final Parser<Token, List<String>> IDENTIFIER_LIST = oneOrMoreSep("IDENTIFIER_LIST", IDENTIFIER, WHITESPACE);
	
	private final Parser<Token, List<String>> LOCAL_VARIABLES_INT = thenParser3Just2("LOCAL_VARIABLES_INT", OP_WHITESPACE, IDENTIFIER_LIST, OP_WHITESPACE);
	
	private final Parser<Token, List<String>> LOCAL_VARIABLES = thenParser4Just3("LOCAL_VARIABLES", OP_WHITESPACE, matchLiteral("|"), LOCAL_VARIABLES_INT, matchLiteral("|"));
	
	private final Parser<Token, List<String>> OPTIONAL_LOCAL_VARIABLE = zeroOrOne("OPTIONAL_LOCAL_VARIABLE", LOCAL_VARIABLES);
	
	private final Parser<Token, ExecutableCode> EXECUTABLE_CODE = thenParser3Cons123("EXECUTABLE_CODE", OPTIONAL_LOCAL_VARIABLE, STATEMENTS, OPTIONAL_FINAL_STATEMENT, ExecutableCode.class);
	
	CompilerParser() {
	}
	
	public ExecutableCode parseCode(List<Token> tokensList) {
		ConsList<Token> tokens = ConsList.createList(tokensList);
		ParsingResult<Token, ExecutableCode> parseResults = EXECUTABLE_CODE.execute(tokens);
		if(parseResults instanceof ParseError) {
			throw new RuntimeException(((ParseError<Token, ExecutableCode>) parseResults).error);
		}
		
		return ((ParseSuccess<Token, ExecutableCode>) parseResults).result;
	}
	
	public Method parseMethod(List<Token> tokens) {
		throw new UnsupportedOperationException();
	}
	
	private static Parser<Token, String> matchByType(final TokenType type) {
		return new AbstractParser<Token, String>(type.name()) {
			@Override
			protected ParsingResult<Token, String> executeDelegate(ConsList<Token> tokens) {
				Token head = tokens.head();
				if(head.type != type) {
					return new ParseError<Token, String>("Expected " + type.name(), tokens);
				}

				return new ParseSuccess<Token, String>(head.value, tokens.tail());
			}};
	}
	
	private static Parser<Token, String> matchLiteral(final String str) {
		return new AbstractParser<Token, String>(str) {
			@Override
			protected ParsingResult<Token, String> executeDelegate(ConsList<Token> tokens) {
				Token head = tokens.head();
				if(head.type != TokenType.SEPARATOR && head.type != TokenType.OPERATOR) {
					return new ParseError<Token, String>("Expected " + TokenType.SEPARATOR.name(), tokens);
				}

				if(! head.value.equals(str)) {
					return new ParseError<Token, String>("Expected " + str, tokens);
				}
				
				return new ParseSuccess<Token, String>(head.value, tokens.tail());
			}};
	}
}

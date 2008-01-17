package org.sodeja.silan.compiler;

import static org.sodeja.parsec.combinator.ParsecCombinators.*;

import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.parsec.AbstractParser;
import org.sodeja.parsec.ParseError;
import org.sodeja.parsec.ParseSuccess;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.ParsingResult;
import org.sodeja.parsec.combinator.DelegateParser;
import org.sodeja.silan.compiler.src.BinaryHeader;
import org.sodeja.silan.compiler.src.BinaryMessage;
import org.sodeja.silan.compiler.src.BinaryMessageOperand;
import org.sodeja.silan.compiler.src.BinaryRootMessage;
import org.sodeja.silan.compiler.src.BlockLiteral;
import org.sodeja.silan.compiler.src.BooleanLiteral;
import org.sodeja.silan.compiler.src.CharacterLiteral;
import org.sodeja.silan.compiler.src.ExecutableCode;
import org.sodeja.silan.compiler.src.Expression;
import org.sodeja.silan.compiler.src.FinalStatement;
import org.sodeja.silan.compiler.src.IntegerLiteral;
import org.sodeja.silan.compiler.src.KeywordHeader;
import org.sodeja.silan.compiler.src.KeywordHeaderSegment;
import org.sodeja.silan.compiler.src.KeywordMessage;
import org.sodeja.silan.compiler.src.KeywordMessageArgument;
import org.sodeja.silan.compiler.src.Literal;
import org.sodeja.silan.compiler.src.LiteralElement;
import org.sodeja.silan.compiler.src.Message;
import org.sodeja.silan.compiler.src.MethodDeclaration;
import org.sodeja.silan.compiler.src.MethodHeader;
import org.sodeja.silan.compiler.src.NestedExpression;
import org.sodeja.silan.compiler.src.NilLiteral;
import org.sodeja.silan.compiler.src.ObjectArrayLiteral;
import org.sodeja.silan.compiler.src.Primary;
import org.sodeja.silan.compiler.src.Reference;
import org.sodeja.silan.compiler.src.Statement;
import org.sodeja.silan.compiler.src.StringLiteral;
import org.sodeja.silan.compiler.src.Token;
import org.sodeja.silan.compiler.src.TokenType;
import org.sodeja.silan.compiler.src.UnaryHeader;
import org.sodeja.silan.compiler.src.UnaryMessage;
import org.sodeja.silan.compiler.src.UnaryRootMessage;

public class CompilerParser {

	private final DelegateParser<Token, Statement> STATEMENT_INT_DEL = new DelegateParser<Token, Statement>("STATEMENT_INT_DEL");
	
	private final DelegateParser<Token, ExecutableCode> EXECUTABLE_CODE_DEL = new DelegateParser<Token, ExecutableCode>("EXECUTABLE_CODE_DEL");
	
	private final Parser<Token, String> IDENTIFIER = matchByType(TokenType.IDENTIFIER);
	
	private final Parser<Token, String> KEYWORD = matchByType(TokenType.KEYWORD);
	
	private final Parser<Token, String> BINARY = matchByType(TokenType.OPERATOR);
		
	private final Parser<Token, String> WHITESPACE = matchByType(TokenType.WHITESPACE);
	
	private final Parser<Token, String> OP_WHITESPACE = zeroOrOne("OP_WHITESPACE", WHITESPACE);

	private final Parser<Token, IntegerLiteral> INTEGER_LITERAL = applyCons("INTEGER_LITERAL", matchByType(TokenType.INTEGER), IntegerLiteral.class);

	private final Parser<Token, CharacterLiteral> CHARACTER_LITERAL = applyCons("CHARACTER_LITERAL", matchByType(TokenType.CHARACTER), CharacterLiteral.class);
	
	private final Parser<Token, StringLiteral> STRING_LITERAL = applyCons("STRING_LITERAL", matchByType(TokenType.STRING), StringLiteral.class);
	
	private final Parser<Token, NilLiteral> NIL_LITERAL = applyCons("NIL_LITERAL", matchConstantReference("nil"), NilLiteral.class);
	
	private final Parser<Token, BooleanLiteral> TRUE_LITERAL = applyCons("TRUE_LITERAL", matchConstantReference("true"), BooleanLiteral.class);

	private final Parser<Token, BooleanLiteral> FALSE_LITERAL = applyCons("FALSE_LITERAL", matchConstantReference("false"), BooleanLiteral.class);
	
	private final Parser<Token, Literal> CONSTANT_REFERENCE = oneOf1("CONSTANT_REFERENCE", NIL_LITERAL, TRUE_LITERAL, FALSE_LITERAL);

	private final Parser<Token, List<String>> BLOCK_LITERAL_ARGUMENTS_LIST = oneOrMoreSep("BLOCK_LITERAL_ARGUMENTS_LIST", matchByType(TokenType.BLOCK_ARGUMENT) , WHITESPACE);
	
	private final Parser<Token, List<String>> BLOCK_LITERAL_ARGUMENTS = thenParser4Just2("BLOCK_LITERAL_ARGUMENTS", OP_WHITESPACE, BLOCK_LITERAL_ARGUMENTS_LIST , OP_WHITESPACE, matchLiteral("|"));
	
	private final Parser<Token, ExecutableCode> EXECUTABLE_CODE_SPACE = thenParserJust1("EXECUTABLE_CODE_SPACE", EXECUTABLE_CODE_DEL, OP_WHITESPACE);
	
	// TODO only simple values are supported currently, make it correct
	private final Parser<Token, LiteralElement> LITERAL_ARRAY_ELEMENT = oneOf1("LITERAL", CONSTANT_REFERENCE, INTEGER_LITERAL, CHARACTER_LITERAL, STRING_LITERAL);
	
	private final Parser<Token, List<LiteralElement>> LITERAL_ARRAY_ELEMENTS = zeroOrMoreSep("LITERAL_ARRAY_ELEMENTS", LITERAL_ARRAY_ELEMENT, WHITESPACE);

	private final Parser<Token, ObjectArrayLiteral> OBJECT_ARRAY_LITERAL = thenParser3Cons2("OBJECT_ARRAY_LITERAL", OP_WHITESPACE, LITERAL_ARRAY_ELEMENTS, OP_WHITESPACE, ObjectArrayLiteral.class);
	
	private final Parser<Token, ObjectArrayLiteral> ARRAY_LITERAL = thenParser4Just3("ARRAY_LITERAL", matchLiteral("#"), matchLiteral("("), OBJECT_ARRAY_LITERAL, matchLiteral(")"));
	
	private final Parser<Token, BlockLiteral> BLOCK_LITERAL = thenParser4Cons23("BLOCK_LITERAL", matchLiteral("["), zeroOrOne("BLOCK_LITERAL_ARGUMENTS_", BLOCK_LITERAL_ARGUMENTS), EXECUTABLE_CODE_SPACE, matchLiteral("]"), BlockLiteral.class);
	
	private final Parser<Token, Literal> LITERAL = oneOf1("LITERAL", CONSTANT_REFERENCE, INTEGER_LITERAL, CHARACTER_LITERAL, STRING_LITERAL, ARRAY_LITERAL, BLOCK_LITERAL);
	
	private final Parser<Token, NestedExpression> NESTED = thenParser4Cons2("NESTED", matchLiteral("("), STATEMENT_INT_DEL, OP_WHITESPACE, matchLiteral(")"), NestedExpression.class);
	
	private final Parser<Token, Reference> REFERENCE = applyCons("REFERENCE", IDENTIFIER, Reference.class);
	
	private final Parser<Token, Primary> PRIMARY = oneOf1("PRIMARY", LITERAL, REFERENCE, NESTED);

	private final Parser<Token, UnaryMessage> UNARY_MESSAGE = applyCons("UNARY_MESSAGE", IDENTIFIER, UnaryMessage.class);

	private final Parser<Token, List<UnaryMessage>> UNARY_MESSAGE_CHAIN_ZERO = zeroOrMore("UNARY_MESSAGE_CHAIN_ZERO", thenParserJust2("UNARY_MESSAGE_CHAIN_ZERO_INT", OP_WHITESPACE, UNARY_MESSAGE));

	private final Parser<Token, List<UnaryMessage>> UNARY_MESSAGE_CHAIN_ONE = oneOrMoreSep("UNARY_MESSAGE_CHAIN_ONE", UNARY_MESSAGE, WHITESPACE);
	
	private final Parser<Token, BinaryMessageOperand> BINARY_OPERAND = thenParserCons("BINARY_OPERAND", PRIMARY, UNARY_MESSAGE_CHAIN_ZERO, BinaryMessageOperand.class);
	
	private final Parser<Token, BinaryMessage> BINARY_MESSAGE = thenParser3Cons13("BINARY_MESSAGE", BINARY, OP_WHITESPACE, BINARY_OPERAND, BinaryMessage.class);

	private final Parser<Token, List<BinaryMessage>> BINARY_CHAIN_ZERO = zeroOrMore("BINARY_CHAIN_ZERO", thenParserJust2("BINARY_CHAIN_INT", OP_WHITESPACE, BINARY_MESSAGE));

	private final Parser<Token, List<BinaryMessage>> BINARY_CHAIN_ONE = oneOrMoreSep("BINARY_CHAIN_ONE", BINARY_MESSAGE, OP_WHITESPACE);
	
	private final Parser<Token, Primary> WHITESPACE_PRIMARY = thenParserJust2("WHITESPACE_PRIMARY", OP_WHITESPACE, PRIMARY);
	
	private final Parser<Token, KeywordMessageArgument> KEYWORD_MESSAGE_ARGUMENT = thenParser4Cons("KEYWORD_MESSAGE_ARGUMENT", KEYWORD, WHITESPACE_PRIMARY, UNARY_MESSAGE_CHAIN_ZERO, BINARY_CHAIN_ZERO, KeywordMessageArgument.class);
	
	private final Parser<Token, List<KeywordMessageArgument>> KEYWORD_MESSAGE_ARGUMENTS = oneOrMoreSep("KEYWORD_MESSAGE_ARGUMENTS", KEYWORD_MESSAGE_ARGUMENT, OP_WHITESPACE);
	
	private final Parser<Token, KeywordMessage> KEYWORD_MESSAGE = applyCons("KEYWORD_MESSAGE", KEYWORD_MESSAGE_ARGUMENTS, KeywordMessage.class);

//	private final Parser<Token, KeywordMessage> OPTIONAL_KEYWORD_MESSAGE = thenParserJust2("OPTIONAL_KEYWORD_MESSAGE", OP_WHITESPACE, zeroOrOne("OPTIONAL_KEYWORD_MESSAGE", KEYWORD_MESSAGE));
	private final Parser<Token, KeywordMessage> OPTIONAL_KEYWORD_MESSAGE = zeroOrOne("OPTIONAL_KEYWORD_MESSAGE", thenParserJust2("OPTIONAL_KEYWORD_MESSAGE", OP_WHITESPACE, KEYWORD_MESSAGE));
	
	private final Parser<Token, BinaryRootMessage> BINARY_ROOT_MESSAGE = thenParserCons("BINARY_ROOT_MESSAGE", BINARY_CHAIN_ONE, OPTIONAL_KEYWORD_MESSAGE, BinaryRootMessage.class);
	
	private final Parser<Token, UnaryRootMessage> UNARY_ROOT_MESSAGE = thenParser3Cons123("UNARY_ROOT_MESSAGE", UNARY_MESSAGE_CHAIN_ONE, BINARY_CHAIN_ZERO, OPTIONAL_KEYWORD_MESSAGE, UnaryRootMessage.class);

	private final Parser<Token, Message> MESSAGE = thenParserJust2("MESSAGE", OP_WHITESPACE, (Parser) oneOf1("MESSAGE", UNARY_ROOT_MESSAGE, BINARY_ROOT_MESSAGE, KEYWORD_MESSAGE));
	
	private final Parser<Token, List<Message>> CASCADE_MESSAGES = zeroOrMoreSep("CASCADE_MESSAGES", MESSAGE, matchLiteral(";"));
	
	private final Parser<Token, List<Message>> MESSAGES = zeroOrOne("MESSAGES", CASCADE_MESSAGES);
	
	private final Parser<Token, Expression> EXPRESSION = thenParserCons("EXPRESSION", PRIMARY, MESSAGES, Expression.class);
	
	private final Parser<Token, String> ASSIGNMENT = thenParser4Just2("ASSIGNMENT", OP_WHITESPACE, IDENTIFIER, OP_WHITESPACE, matchLiteral(":="));

	private final Parser<Token, String> OPTIONAL_ASSIGNMENT = zeroOrOne("OPTIONAL_ASSIGNMENT", ASSIGNMENT);
	
	private final Parser<Token, Statement> STATEMENT_INT = thenParser3Cons13("STATEMENT_INT", OPTIONAL_ASSIGNMENT, OP_WHITESPACE, EXPRESSION, Statement.class);
	
	private final Parser<Token, Statement> STATEMENT = thenParser3Just1("STATEMENT", STATEMENT_INT, OP_WHITESPACE, matchLiteral("."));

	private final Parser<Token, Statement> FINAL_STATEMENT_INT = thenParser3Just1("STATEMENT", STATEMENT_INT, OP_WHITESPACE, zeroOrOne("FINAL_STATEMENT_INT_.", matchLiteral(".")));
	
//	private final Parser<Token, Statement> FINAL_STATEMENT = thenParser3Just3("FINAL_STATEMENT", OP_WHITESPACE, matchLiteral("^"), STATEMENT);
	private final Parser<Token, FinalStatement> FINAL_STATEMENT = thenParser3Cons23("FINAL_STATEMENT", OP_WHITESPACE, zeroOrOne("FINAL_STATEMENT_^", matchLiteral("^")), FINAL_STATEMENT_INT, FinalStatement.class);

	private final Parser<Token, FinalStatement> OPTIONAL_FINAL_STATEMENT = zeroOrOne("OPTIONAL_FINAL_STATEMENT", FINAL_STATEMENT);
	
	private final Parser<Token, List<Statement>> STATEMENTS = zeroOrMore("STATEMENTS", STATEMENT);
	
	private final Parser<Token, List<String>> IDENTIFIER_LIST = oneOrMoreSep("IDENTIFIER_LIST", IDENTIFIER, WHITESPACE);
	
	private final Parser<Token, List<String>> LOCAL_VARIABLES_INT = thenParser3Just2("LOCAL_VARIABLES_INT", OP_WHITESPACE, IDENTIFIER_LIST, OP_WHITESPACE);
	
	private final Parser<Token, List<String>> LOCAL_VARIABLES = thenParser4Just3("LOCAL_VARIABLES", OP_WHITESPACE, matchLiteral("|"), LOCAL_VARIABLES_INT, matchLiteral("|"));
	
	private final Parser<Token, List<String>> OPTIONAL_LOCAL_VARIABLE = zeroOrOne("OPTIONAL_LOCAL_VARIABLE", LOCAL_VARIABLES);
	
	private final Parser<Token, ExecutableCode> EXECUTABLE_CODE = thenParser3Cons123("EXECUTABLE_CODE", OPTIONAL_LOCAL_VARIABLE, STATEMENTS, OPTIONAL_FINAL_STATEMENT, ExecutableCode.class);

	private final Parser<Token, UnaryHeader> UNARY_HEADER = applyCons("UNARY_HEADER", IDENTIFIER, UnaryHeader.class);

	private final Parser<Token, BinaryHeader> BINARY_HEADER = thenParser3Cons13("BINARY_HEADER", BINARY, OP_WHITESPACE, IDENTIFIER, BinaryHeader.class);

	private final Parser<Token, KeywordHeaderSegment> KEYWORD_HEADER_SEGMENT = thenParser3Cons13("KEYWORD_HEADER_SEGMENT", KEYWORD, OP_WHITESPACE, IDENTIFIER, KeywordHeaderSegment.class);
	
	private final Parser<Token, KeywordHeader> KEYWORD_HEADER = applyCons("KEYWORD_HEADER", oneOrMoreSep("KEYWORD_HEADER", KEYWORD_HEADER_SEGMENT, WHITESPACE), KeywordHeader.class);
	
	private final Parser<Token, MethodHeader> METHOD_HEADER = oneOf1("METHOD_HEADER", UNARY_HEADER, BINARY_HEADER, KEYWORD_HEADER);
	
	private final Parser<Token, MethodDeclaration> METHOD_DECLARATION = thenParser3Cons23("", OP_WHITESPACE, METHOD_HEADER, EXECUTABLE_CODE, MethodDeclaration.class);
	
	CompilerParser() {
		STATEMENT_INT_DEL.delegate = STATEMENT_INT;
		EXECUTABLE_CODE_DEL.delegate = EXECUTABLE_CODE;
	}
	
	public ExecutableCode parseCode(List<Token> tokensList) {
		ConsList<Token> tokens = ConsList.createList(tokensList);
		ParsingResult<Token, ExecutableCode> parseResults = EXECUTABLE_CODE.execute(tokens);
		if(parseResults instanceof ParseError) {
			throw new RuntimeException(((ParseError<Token, ExecutableCode>) parseResults).error);
		}
		
		return ((ParseSuccess<Token, ExecutableCode>) parseResults).result;
	}
	
	public MethodDeclaration parseMethod(List<Token> tokensList) {
		ConsList<Token> tokens = ConsList.createList(tokensList);
		ParsingResult<Token, MethodDeclaration> parseResults = METHOD_DECLARATION.execute(tokens);
		if(parseResults instanceof ParseError) {
			throw new RuntimeException(((ParseError<Token, MethodDeclaration>) parseResults).error);
		}
		
		return ((ParseSuccess<Token, MethodDeclaration>) parseResults).result;
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
	
	private static Parser<Token, String> matchConstantReference(final String str) {
		return new AbstractParser<Token, String>(str) {
			@Override
			protected ParsingResult<Token, String> executeDelegate(ConsList<Token> tokens) {
				Token head = tokens.head();
				if(head.type != TokenType.IDENTIFIER) {
					return new ParseError<Token, String>("Expected " + str, tokens);
				}

				if(! head.value.equals(str)) {
					return new ParseError<Token, String>("Expected " + str, tokens);
				}
				
				return new ParseSuccess<Token, String>(str, tokens.tail());
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

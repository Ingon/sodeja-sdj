package org.sodeja.silan.objects;

import static org.sodeja.parsec.combinator.ParsecCombinators.*;

import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.parsec.AbstractParser;
import org.sodeja.parsec.ParseError;
import org.sodeja.parsec.ParseSuccess;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.ParsingResult;
import org.sodeja.parsec.semantic.AbstractSemanticParser;

class ScriptParser extends AbstractSemanticParser<Token, Script> {

	private final Parser<Token, String> IDENTIFIER = new AbstractParser<Token, String>("IDENTIFIER") {
		@Override
		protected ParsingResult<Token, String> executeDelegate(ConsList<Token> tokens) {
			Token head = tokens.head();
			if(head.type != TokenType.IDENTIFIER) {
				return new ParseError<Token, String>("Not an identifier", tokens);
			}
			
			return new ParseSuccess<Token, String>(head.value, tokens.tail());
		}};
	
	private final Parser<Token, String> WHITESPACE = new AbstractParser<Token, String>("WHITESPACE") {
		@Override
		protected ParsingResult<Token, String> executeDelegate(ConsList<Token> tokens) {
			Token head = tokens.head();
			if(head.type != TokenType.WHITESPACE) {
				return new ParseError<Token, String>("Not an whitespace", tokens);
			}
			
			return new ParseSuccess<Token, String>(head.value, tokens.tail());
		}};
	
	private final Parser<Token, Integer> INTEGER_INT = new AbstractParser<Token, Integer>("INTEGER") {
		@Override
		protected ParsingResult<Token, Integer> executeDelegate(
				ConsList<Token> tokens) {
			Token head = tokens.head();
			if(head.type != TokenType.INTEGER) {
				return new ParseError<Token, Integer>("Not an integer", tokens);
			}
			
			return new ParseSuccess<Token, Integer>(new Integer(head.value), tokens.tail());
		}};
	
		
	private final Parser<Token, String> OPTIONAL_WHITESPACE = zeroOrOne("OPTIONAL_WHITESPACE", WHITESPACE);
	
	private final Parser<Token, Integer> INTEGER = thenParserJust2("INTEGER", OPTIONAL_WHITESPACE, INTEGER_INT);
	
	private final Parser<Token, String> CURLY_START = thenParserJust2("CURLY_START", OPTIONAL_WHITESPACE, matchSeparator("{"));

	private final Parser<Token, String> CURLY_END = thenParserJust2("CURLY_END", OPTIONAL_WHITESPACE, matchSeparator("}"));

	private final Parser<Token, String> BRACKET_START = thenParserJust2("BRACKET_START", OPTIONAL_WHITESPACE, matchSeparator("("));

	private final Parser<Token, String> BRACKET_END = thenParserJust2("BRACKET_END", OPTIONAL_WHITESPACE, matchSeparator(")"));

	private final Parser<Token, String> DOT = thenParserJust2("DOT", OPTIONAL_WHITESPACE, matchSeparator("."));
	
	private final Parser<Token, List<String>> IDENTIFIERS = zeroOrMoreSep("IDENTIFIERS", IDENTIFIER, WHITESPACE);

	private final Parser<Token, List<String>> IDENTIFIERS_LIST = thenParser4Just3("IDENTIFIERS_LIST", BRACKET_START, OPTIONAL_WHITESPACE, IDENTIFIERS, BRACKET_END);
	
	private final Parser<Token, List<String>> OPTIONAL_IDENTIFIERS = zeroOrOne("OPTIONAL_IDENTIFIERS", IDENTIFIERS_LIST);
	
	private final Parser<Token, String> METHOD_NAME = thenParserJust2("METHOD_NAME", OPTIONAL_WHITESPACE, IDENTIFIER);
	
	private final Parser<Token, MethodHeader> METHOD_HEADER = thenParser4Cons("METHOD_HEADER", METHOD_NAME, OPTIONAL_IDENTIFIERS, OPTIONAL_IDENTIFIERS, INTEGER, MethodHeader.class);

	private final Parser<Token, List<String>> INSTRUCTION = thenParser3Just2("INSTRUCTION_INT", OPTIONAL_WHITESPACE, IDENTIFIERS, DOT);
	
	private final Parser<Token, InstructionDefinition> INSTRUCTION_DEF = applyCons("INSTRUCTION_DEF", INSTRUCTION, InstructionDefinition.class);

	private final Parser<Token, List<InstructionDefinition>> INSTRUCTIONS = zeroOrMoreSep("INSTRUCTIONS", INSTRUCTION_DEF, WHITESPACE);
	
	private final Parser<Token, MethodBlock> METHOD_BLOCK = thenParser3Cons2("METHOD_BLOCK", CURLY_START, INSTRUCTIONS, CURLY_END, MethodBlock.class);
	
	private final Parser<Token, InstructionMethod> INSTRUCTION_METHOD = thenParserCons("INSTRUCTION_METHOD", METHOD_HEADER, METHOD_BLOCK, InstructionMethod.class);
	
	private final Parser<Token, LiteralMethod> LITERAL_METHOD_INT = new AbstractParser<Token, LiteralMethod>("LITERAL_METHOD_INT") {
		@Override
		protected ParsingResult<Token, LiteralMethod> executeDelegate(ConsList<Token> tokens) {
			Token head = tokens.head();
			if(head.type != TokenType.LITERAL_METHOD) {
				return new ParseError<Token, LiteralMethod>("Not an literal method", tokens);
			}
			
			return new ParseSuccess<Token, LiteralMethod>(new LiteralMethod(head.value), tokens.tail());
		}};

	private final Parser<Token, LiteralMethod> LITERAL_METHOD = thenParserJust2("LITERAL_METHOD", OPTIONAL_WHITESPACE, LITERAL_METHOD_INT);
		
	private final Parser<Token, Method> METHOD = oneOf1("METHOD", LITERAL_METHOD, INSTRUCTION_METHOD);
	
	private final Parser<Token, List<Method>> METHODS = zeroOrMoreSep("METHODS", METHOD, OPTIONAL_WHITESPACE);
	
	private final Parser<Token, MethodsDefinition> METHODS_DEFINITION = thenParser3Cons2("METHOD_BLOCK", CURLY_START, METHODS, CURLY_END, MethodsDefinition.class);
	
	private final Parser<Token, ClassDefinition> CLASS_DEFINITION = thenParser4Cons134("CLASS_DEFINITION", IDENTIFIER, WHITESPACE, IDENTIFIER, IDENTIFIERS_LIST, ClassDefinition.class);
	
	private final Parser<Token, Script> SCRIPT = thenParser3Cons123("SCRIPT", CLASS_DEFINITION, METHODS_DEFINITION, METHODS_DEFINITION, Script.class);
	
	@Override
	protected Parser<Token, Script> getParser() {
		return SCRIPT;
	}
	
	private static Parser<Token, String> matchSeparator(final String str) {
		return new AbstractParser<Token, String>(str) {
			@Override
			protected ParsingResult<Token, String> executeDelegate(ConsList<Token> tokens) {
				Token head = tokens.head();
				if(head.type != TokenType.SEPARATOR) {
					return new ParseError<Token, String>("Not an separator", tokens);
				}
				
				String value = head.value;
				if(! value.equals(str)) {
					return new ParseError<Token, String>("Not an separator " + str, tokens);
				}
				
				return new ParseSuccess<Token, String>(head.value, tokens.tail());
			}};
	}
}

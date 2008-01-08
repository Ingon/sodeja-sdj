package org.sodeja.sil;

import static org.sodeja.parsec2.PrimitiveParsers.*;
import static org.sodeja.parsec2.ParserCombinators.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import org.sodeja.collections.ConsList;
import org.sodeja.parsec2.DelegateParser;
import org.sodeja.parsec2.ParseResult;
import org.sodeja.parsec2.Parser;

public class SIL {
	public static void main(String[] args) throws Exception {
		Parser execCode = getParser();
		
		ConsList<Character> charStream = loadTokens(new FileReader("test/test.sil"));
		ParseResult pr = execCode.match(charStream);
		
		System.out.println("pr: " + pr);
	}
	
	private static ConsList<Character> loadTokens(Reader reader) throws IOException {
		List<Character> chars = new ArrayList<Character>();
		int currentChar = -1;
		while((currentChar = reader.read()) != -1) {
			chars.add((char) currentChar);
		}
		return ConsList.createList(chars);
	}
	
	private static Parser getParser() {
		Parser anyChar = anyCharacter();
		Parser whitespaceChar = whitespaceCharacter();
		
		Parser decimalDigit = oneOfCharacters("0123456789");
		Parser letter = oneOfCharacters("ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz");
		
		Parser commentChar = matchUnless(anyChar, character('"'));
		Parser comment = then("comment", character('"'), commentChar, character('"'));
		
		Parser optionalWhitespace = repeated(or(whitespaceChar, comment));
		Parser whitespace = then("whitespace", or(whitespaceChar, comment), optionalWhitespace);
		
		Parser letterOrDigit = or("letterOrDigit", decimalDigit, letter);
		Parser identifier = then("identifier", or(letter, character('_')), repeated(or(letterOrDigit, character('_'))));
		
		Parser reference = identifier;
		
		Parser constantReference = or("constantReference", string("nil"), string("false"), string("true"));
		Parser pseudoVariableReference = or("pseudoVariableReference", string("self"), string("super"), string("thisContext"));
		Parser reservedIdentifier = or("reservedIdentifier", pseudoVariableReference, constantReference);
		
		Parser bindableIdentifier = matchUnless(identifier, reservedIdentifier);
		
		Parser unaryMessageSelector = identifier;
		Parser keyword = then("keyword", identifier, character(':'));
		Parser keywordMessageSelector = then("keywordMessageSelector", keyword, repeated(keyword));
		
		Parser binarySelectorChar = oneOfCharacters("~!@%&*-+=|\\<>,?/");
		Parser binaryMessageSelector = then("binaryMessageSelector", binarySelectorChar, optional(binarySelectorChar));
		
		Parser decimalIntegerLiteral = then("decimalIntegerLiteral", decimalDigit, repeated(decimalDigit));
		// TODO currently match only 4343 literals
		Parser unsignedIntegerLiteral = decimalIntegerLiteral; 
		Parser integerLiteral = then("integerLiteral", character('-'), unsignedIntegerLiteral);
		
		Parser characterLiteral = then("characterLiteral", character('$'), anyChar);
		
		Parser stringLiteralCharacter = matchUnless(anyChar, character('\''));
		Parser stringLiteral = then("stringLiteral", character('\''), stringLiteralCharacter, character('\''));
		
		Parser symbolInArrayLiteral = or("symbolInArrayLiteral", matchUnless(unaryMessageSelector, constantReference), keywordMessageSelector, binaryMessageSelector);
		Parser symbolLiteral = then("symbolLiteral", character('#'), or(symbolInArrayLiteral, constantReference, stringLiteral));
		
		DelegateParser objectArrayLiteral = new DelegateParser("objectArrayLiteral");
		DelegateParser byteArrayLiteral = new DelegateParser("byteArrayLiteral");
		Parser arrayLiteral = or("arrayLiteral", objectArrayLiteral, byteArrayLiteral);
		
		DelegateParser literalArrayElement = new DelegateParser("literalArrayElement");
		Parser nestedObjectArrayLiteral = then("nestedObjectArrayLiteral", character('('), optionalWhitespace, optionalThen(literalArrayElement, repeatedThen(optionalWhitespace, literalArrayElement)), optionalWhitespace, character(')'));
		
		objectArrayLiteral.delegate = then("objectArrayLiteral", character('#'), nestedObjectArrayLiteral);
		byteArrayLiteral.delegate = then("byteArrayLiteral", string("#["), optionalWhitespace, optionalThen(unsignedIntegerLiteral, repeatedThen(whitespace, unsignedIntegerLiteral)), optionalWhitespace, character(']'));
		
		DelegateParser literal = new DelegateParser("literal");
		DelegateParser blockLiteral = new DelegateParser("blockLiteral");
		literalArrayElement.delegate = or("literalArrayElement", matchUnless(literal , blockLiteral), nestedObjectArrayLiteral, symbolInArrayLiteral, constantReference);
		
		Parser formalBlockArgumentDeclaration = then("formalBlockArgumentDeclaration", character(':'), bindableIdentifier);
		Parser formalBlockArgumentDeclarationList = then("formalBlockArgumentDeclarationList", formalBlockArgumentDeclaration, repeatedThen(whitespace, formalBlockArgumentDeclaration));
		
		DelegateParser executableCode = new DelegateParser("executableCode");
		blockLiteral.delegate = then("blockLiteral", character('['), optionalThen(optionalWhitespace, formalBlockArgumentDeclarationList, optionalWhitespace, character('|')), executableCode, optionalWhitespace, character(']'));
		// TODO does not matches ScaledDecimalLiteral and FloatingPointLiteral
		literal.delegate = or("literal", constantReference, integerLiteral, characterLiteral, stringLiteral, symbolLiteral, arrayLiteral, blockLiteral);
		
		DelegateParser statement = new DelegateParser("statement");
		Parser nestedExpression = then("nestedExpression", character('('), statement , optionalWhitespace, character(')'));
		Parser operand = or("operand", literal, reference, nestedExpression);
		
		Parser unaryMessage = unaryMessageSelector;
		Parser unaryMessageChain = repeatedThen(optionalWhitespace, unaryMessage);
		
		Parser binaryMessageOperand = then("binaryMessageOperand", operand, unaryMessageChain);
		Parser binaryMessage = then("binaryMessage", binaryMessageSelector, optionalWhitespace, binaryMessageOperand);
		Parser binaryMessageChain = repeatedThen(optionalWhitespace, binaryMessage);
		
		Parser keywordMessageArgument = then("keywordMessageArgument", binaryMessageOperand, binaryMessageChain);
		Parser keywordMessageSegment = then("keywordMessageSegment", keyword, optionalWhitespace, keywordMessageArgument);
		Parser keywordMessage = then("keywordMessage", keywordMessageSegment, repeatedThen(optionalWhitespace, keywordMessageSegment));
		
		Parser messageChain = or("messageChain", then(unaryMessage, unaryMessageChain, binaryMessageChain, optional(keywordMessage)), 
				then(binaryMessage, binaryMessageChain, optional(keywordMessage)), keywordMessage);
		Parser cascadedMessage = then("cascadedMessage", character(';'), optionalWhitespace, messageChain);
		
		Parser expression = then("expression", operand, optionalThen(optionalWhitespace, messageChain, repeatedThen(optionalWhitespace, cascadedMessage)));
		Parser assignmentOperation = then("assignmentOperation", optionalWhitespace, bindableIdentifier, optionalWhitespace, string(":="));
		
		statement.delegate = then("statement", repeated(assignmentOperation), optionalWhitespace, expression);
		
		Parser methodReturnOperator = then("methodReturnOperator", optionalWhitespace, character('^'));
		Parser finalStatement = then("finalStatement", optional(methodReturnOperator), statement);
		
		Parser localVariableDeclarationList = then("localVariableDeclarationList", optionalWhitespace, character('|'), optionalWhitespace, optionalThen(bindableIdentifier, repeatedThen(whitespace, bindableIdentifier)), optionalWhitespace, character('|'));
		executableCode.delegate = then("executableCode", optional(localVariableDeclarationList), optionalThen(repeatedThen(statement, optionalWhitespace, character('.')), finalStatement, optional(character('.'))));
		
		Parser unaryMethodHeader = unaryMessageSelector;
		Parser binaryMethodHeader = then("binaryMethodHeader", binaryMessageSelector, optionalWhitespace, bindableIdentifier);
		Parser keywordMethodHeaderSegment = then("keywordMethodHeaderSegment", keyword, optionalWhitespace, bindableIdentifier);
		Parser keywordMethodHeader = then("keywordMethodHeader", keywordMethodHeaderSegment, repeatedThen(whitespace, keywordMethodHeaderSegment));
		
		Parser methodHeader = or("methodHeader", unaryMethodHeader, binaryMethodHeader, keywordMethodHeader);
		// TODO make a global parsers
		Parser methodDeclaration = then("methodDeclaration", optionalWhitespace, methodHeader, executableCode);
		
		return executableCode;
	}
}

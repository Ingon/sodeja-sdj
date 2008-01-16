package org.sodeja.silan.objects;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.silan.compiler.CompilerLexer;

class ScriptLexer {
	private static final String WHITESPACE = " ";

	ScriptLexer() {
	}
	
	public List<Token> lexify(String str) {
		List<Token> result = new ArrayList<Token>();
		for(int i = 0, n = str.length();i < n;i++) {
			char ch = str.charAt(i);
			
			if(CompilerLexer.whitespaceChar(ch)) {
				if(result.size() == 0) {
					result.add(new Token(TokenType.WHITESPACE, WHITESPACE));
				} else if(result.get(result.size() - 1).type != TokenType.WHITESPACE) {
					result.add(new Token(TokenType.WHITESPACE, WHITESPACE));
				}
				
				continue;
			}
			
			if(CompilerLexer.identifierStart(ch)) {
				int identifierEnd = readUntilIdentifierEnd(str, i);
				String identifier = str.substring(i, identifierEnd);
				
				result.add(new Token(TokenType.IDENTIFIER, identifier));
				i = identifierEnd - 1;
				continue;
			}

			if(CompilerLexer.isBinaryChar(ch)) {
				int binaryEnd = CompilerLexer.readUntilBinary(str, i);
				String binary = str.substring(i, binaryEnd);
				result.add(new Token(TokenType.IDENTIFIER, binary));
				i = binaryEnd - 1;
				continue;
			}
			
			if(CompilerLexer.isDigit(ch)) {
				int integerEnd = CompilerLexer.readUntilDigits(str, i);
				String val = str.substring(i, integerEnd);
				result.add(new Token(TokenType.INTEGER, val));
				i = integerEnd - 1;
				continue;
			}
			
			if(isSeparator(ch)) {
				result.add(new Token(TokenType.SEPARATOR, String.valueOf(ch)));
				continue;
			}
			
			if(isThreeHashes(str, i)) {
				int pos = readUntilHashes(str, i);
				if(pos == -1) {
					throw new RuntimeException("Unclosed method literal");
				}
				
				String val = str.substring(i + 3, pos);
				result.add(new Token(TokenType.LITERAL_METHOD, val.trim()));
				i = pos + 2;
				continue;
			}
			
			throw new RuntimeException("Unknown character: " + ch);
		}
		return result;
	}
	
	private int readUntilHashes(String str, int pos) {
		for(int i = pos + 2;i < str.length();i++) {
			if(isThreeHashes(str, i)) {
				return i;
			}
		}
		
		return -1;
	}

	private static int readUntilIdentifierEnd(String str, int start) {
		for(int i = start + 1, n = str.length();i < n;i++) {
			char ch = str.charAt(i);
			if(! identifierPart(ch)) {
				return i;
			}
		}
		
		return str.length() - 1;
	}
	
	private static boolean identifierPart(char ch) {
		return CompilerLexer.identifierPart(ch) || ch == ':';
	}

	static boolean isSeparator(char ch) {
		return ch == '(' || ch == ')' || ch == '{' || ch == '}' || ch == '.';
	}
	
	static boolean isHash(char ch) {
		return ch == '#';
	}
	
	static boolean isThreeHashes(String str, int pos) {
		if(str.length() <= pos + 2) {
			return false;
		}
		
		return isHash(str.charAt(pos)) && 
		isHash(str.charAt(pos + 1)) && 
		isHash(str.charAt(pos + 1));
	}
}

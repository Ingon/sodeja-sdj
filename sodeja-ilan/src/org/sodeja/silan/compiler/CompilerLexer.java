package org.sodeja.silan.compiler;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.silan.compiler.src.Token;
import org.sodeja.silan.compiler.src.TokenType;

public class CompilerLexer {
	static final String WHITESPACE = " ";
	
	static final String STATEMENT_END = ".";
	static final String ASSIGNMENT = ":=";
	static final String RETURN = "^";
	
	CompilerLexer() {
	}
	
	public List<Token> lexify(String str) {
		List<Token> result = new ArrayList<Token>();
		for(int i = 0, n = str.length();i < n;i++) {
			char ch = str.charAt(i);
			
			if(whitespaceChar(ch)) {
				addWhiteSpace(result);
				
				continue;
			}
			
			if(isCommentChar(ch)) {
				int commentEnd = readUntilCommentEnd(str, i);
				if(commentEnd == -1) {
					throw new RuntimeException("Comment starting on " + i + " is not closed");
				}
				
				addWhiteSpace(result);
				
				i = commentEnd;
				continue;
			}
			
			if(identifierStart(ch)) {
				int identifierEnd = readUntilIdentifierEnd(str, i);
				String identifier = str.substring(i, identifierEnd);
				
				TokenType type = TokenType.IDENTIFIER;
				// Check keyword
				if(n > identifierEnd && str.charAt(identifierEnd) == ':') {
					identifier += ':';
					type = TokenType.KEYWORD;
					identifierEnd++;
				}
				
				result.add(new Token(identifier, type));
				i = identifierEnd - 1;
				continue;
			}
			
			if(digit(ch)) {
				int integerEnd = readUntilDigits(str, i);
				String val = str.substring(i, integerEnd);
				result.add(new Token(val, TokenType.INTEGER));
				i = integerEnd - 1;
				continue;
			}
			
			if(ch == '-') {
				char next = str.charAt(i + 1);
				if(digit(next)) {
					throw new UnsupportedOperationException();
				} else {
					throw new UnsupportedOperationException();
				}
			}
			
			if(isBinaryChar(ch)) {
				int binaryEnd = readUntilBinary(str, i);
				String binary = str.substring(i, binaryEnd);
				result.add(new Token(binary, TokenType.OPERATOR));
				i = binaryEnd - 1;
				continue;
			}
			
			if(ch == '$') {
				if(i + 1 == n) {
					throw new RuntimeException("Starting char literal without char");
				}
				result.add(new Token("$" + str.charAt(i + 1), TokenType.CHARACTER));
				i++;
				continue;
			}
			
			if(ch == '\'') {
				int stringEnd = readUntilStringEnd(str, i);
				if(stringEnd == -1) {
					throw new RuntimeException("String starting on " + i + " is not closed");
				}
				String stringLiteral = str.substring(i, stringEnd + 1);
				result.add(new Token(stringLiteral, TokenType.STRING));
				i = stringEnd;
				continue;
			}
			
			if(ch == '#') {
				// Symbol or array literal - not implemented
				throw new UnsupportedOperationException();
			}
			
			if(ch == '.') {
				result.add(new Token(STATEMENT_END, TokenType.SEPARATOR));
				continue;
			}
			
			if(ch == ':') {
				char next = str.charAt(i + 1);
				if(next == '=') {
					result.add(new Token(ASSIGNMENT, TokenType.SEPARATOR));
					i++;
					continue;
				}
			}
			
			if(ch == '^') {
				result.add(new Token(RETURN, TokenType.OPERATOR));
				continue;
			}
			
			throw new RuntimeException("Unknown character");
		}
		return result;
	}

	private void addWhiteSpace(List<Token> result) {
		if(result.size() == 0) {
			result.add(new Token(WHITESPACE, TokenType.WHITESPACE));
		} else if(result.get(result.size() - 1).type != TokenType.WHITESPACE) {
			result.add(new Token(WHITESPACE, TokenType.WHITESPACE));
		}
	}
	
	private int readUntilCommentEnd(String str, int start) {
		for(int i = start + 1, n = str.length();i < n;i++) {
			char ch = str.charAt(i);
			if(isCommentChar(ch)) {
				return i;
			}
		}
		
		return -1;
	}
	
	private int readUntilIdentifierEnd(String str, int start) {
		for(int i = start + 1, n = str.length();i < n;i++) {
			char ch = str.charAt(i);
			if(! identifierPart(ch)) {
				return i;
			}
		}
		
		return str.length() - 1;
	}

	private int readUntilDigits(String str, int start) {
		for(int i = start + 1, n = str.length();i < n;i++) {
			char ch = str.charAt(i);
			if(! digit(ch)) {
				return i;
			}
		}
		
		return str.length() - 1;
	}
	
	private int readUntilBinary(String str, int start) {
		for(int i = start + 1, n = str.length();i < n;i++) {
			char ch = str.charAt(i);
			if(! isBinaryChar(ch)) {
				return i;
			}
		}
		
		return str.length() - 1;
	}

	private int readUntilStringEnd(String str, int start) {
		for(int i = start + 1, n = str.length();i < n;i++) {
			char ch = str.charAt(i);
			if(ch != '\'') {
				continue;
			}

			int nextIndex = i + 1;
			if(nextIndex == n) {
				return i;
			}
			
			char next = str.charAt(i + 1);
			if(next == '\'') {
				i++;
				continue;
			}
			
			return i;
		}
		
		return -1;
	}

	
	static boolean whitespaceChar(char ch) {
		return Character.isWhitespace(ch);
	}
	
	static boolean digit(char ch) {
		return ch >= '0' && ch <= '9';
	}
	
	static boolean letter(char ch) {
		return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
	}
	
	static boolean letterOrDigit(char ch) {
		return letter(ch) || digit(ch);
	}
	
	static boolean identifierStart(char ch) {
		return letter(ch) || ch == '_';
	}

	static boolean identifierPart(char ch) {
		return letterOrDigit(ch) || ch == '_';
	}
	
	static boolean isCommentChar(char ch) {
		return ch == '"';
	}
	
	static boolean isBinaryChar(char ch) {
		return ch == '~' || ch == '!' || ch == '@' || ch == '%' || ch == '*'
			|| ch == '-' || ch == '+' || ch == '=' || ch == '|' || ch == '\\'
			|| ch == '<' || ch == '>' || ch == ',' || ch == '?' || ch == '/';
	}
}

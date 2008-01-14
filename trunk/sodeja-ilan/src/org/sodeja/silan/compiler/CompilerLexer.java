package org.sodeja.silan.compiler;

import java.util.ArrayList;
import java.util.List;

public class CompilerLexer {
	static final String WHITESPACE = " ";
	static final String STATEMENT_END = ".";
	
	CompilerLexer() {
	}
	
	public List<String> lexify(String str) {
		List<String> result = new ArrayList<String>();
		for(int i = 0, n = str.length();i < n;i++) {
			char ch = str.charAt(i);
			
			if(whitespaceChar(ch)) {
				if(result.get(result.size() - 1) != WHITESPACE) {
					result.add(WHITESPACE);
				}
				
				continue;
			}
			
			if(isCommentChar(ch)) {
				int commentEnd = readUntilCommentEnd(str, i);
				if(commentEnd != -1) {
					throw new RuntimeException("Comment starting on " + i + " is not closed");
				}
				
				if(result.get(result.size() - 1) != WHITESPACE) {
					result.add(WHITESPACE);
				}
				
				i = commentEnd;
				continue;
			}
			
			if(identifierStart(ch)) {
				int identifierEnd = readUntilIdentifierEnd(str, i);
				String identifier = str.substring(i, identifierEnd);
				
				// Check keyword
				if(n > identifierEnd && str.charAt(identifierEnd) == ':') {
					identifier += ':';
				}
				
				result.add(identifier);
				continue;
			}
			
			if(digit(ch)) {
				int integerEnd = readUntilDigits(str, i);
				String val = str.substring(i, integerEnd);
				result.add(val);
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
				result.add(binary);
				i = binaryEnd - 1;
				continue;
			}
			
			if(ch == '$') {
				if(i + 1 == n) {
					throw new RuntimeException("Starting char literal without char");
				}
				result.add("$" + str.charAt(i + 1));
				i++;
				continue;
			}
			
			if(ch == '\'') {
				int stringEnd = readUntilStringEnd(str, i);
				if(stringEnd == -1) {
					throw new RuntimeException("String starting on " + i + " is not closed");
				}
				String stringLiteral = str.substring(i, stringEnd + 1);
				result.add(stringLiteral);
				i = stringEnd;
				continue;
			}
			
			if(ch == '#') {
				// Symbol or array literal - not implemented
				throw new UnsupportedOperationException();
			}
			
			if(ch == '.') {
				result.add(STATEMENT_END);
				continue;
			}
			
			throw new RuntimeException("Unknown character");
		}
		return result;
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

	
	private boolean whitespaceChar(char ch) {
		return Character.isWhitespace(ch);
	}
	
	private boolean digit(char ch) {
		return ch >= '0' && ch <= '9';
	}
	
	private boolean letter(char ch) {
		return (ch >= 'a' && ch <= 'z') || (ch >= 'A' && ch <= 'Z');
	}
	
	private boolean letterOrDigit(char ch) {
		return letter(ch) || digit(ch);
	}
	
	private boolean identifierStart(char ch) {
		return letter(ch) || ch == '_';
	}

	private boolean identifierPart(char ch) {
		return letterOrDigit(ch) || ch == '_';
	}
	
	private boolean isCommentChar(char ch) {
		return ch == '"';
	}
	
	private boolean isBinaryChar(char ch) {
		return ch == '~' || ch == '!' || ch == '@' || ch == '%' || ch == '*'
			|| ch == '-' || ch == '+' || ch == '=' || ch == '|' || ch == '\\'
			|| ch == '<' || ch == '>' || ch == ',' || ch == '?' || ch == '/';
	}
}

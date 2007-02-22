package org.sodeja.sdj.lexer;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class Lexer {
	
	private static final String[] TWO_CHARS = {"==", "~=", ">=", "<=", "->"};
	
	private PushbackReader reader;
	private List<Token> tokens;
	private StringBuilder helper;
	private int line = 0;
	
	public Lexer(Reader originalReader) {
		reader = new PushbackReader(originalReader, 1);
		tokens = new ArrayList<Token>();
		helper = new StringBuilder();
	}
	
	public List<Token> tokenize() {
		for(Character ch = nextChar(); ch != null; ch = nextChar()) {
			if(Character.isWhitespace(ch)) {
				if(readEOL(ch)) {
					line++;
				}
				continue;
			}
			
			if(ch == '-') {
				char nextChar = nextChar();
				if(nextChar == '-') {
					readTillEOL();
					continue;
				}
				returnChar(nextChar);
			}
			
			if(Character.isDigit(ch)) {
				readNumberToken(ch);
				continue;
			}
			
			if(Character.isJavaIdentifierStart(ch)) {
				readIdentifier(ch);
				continue;
			}
			
			if(twoChars(ch)) {
				continue;
			}
			
			tokens.add(new Token(line, ch));
		}
		
		return tokens;
	}
	
	private boolean readEOL(Character ch) {
		if(ch == null) {
			return false;
		}
		
		if(ch == '\n') {
			return true;
		}
		
		if(ch == '\r') {
			Character next = nextChar();
			if(next == '\n') {
				return true;
			}
			returnChar(next);
			return true;
		}
		
		return false;
	}
	
	private void readTillEOL() {
		while(! readEOL(nextChar())) {
			;
		}
		line++;
	}
	
	private void readNumberToken(char initial) {
		helper.setLength(0);
		helper.append(initial);
		
		Character ch = null;
		for(ch = nextChar(); Character.isDigit(ch) ; ch = nextChar()) {
			helper.append(ch);
		}
		returnChar(ch);
		
		tokens.add(new Token(line, helper));
	}
	
	private void readIdentifier(char initial) {
		helper.setLength(0);
		helper.append(initial);
		
		Character ch = null;
		for(ch = nextChar(); Character.isJavaIdentifierPart(ch) ; ch = nextChar()) {
			helper.append(ch);
		}
		returnChar(ch);
		
		tokens.add(new Token(line, helper));
	}

	private boolean twoChars(char initial) {
		for(String str : TWO_CHARS) {
			if(str.charAt(0) == initial) {
				char next = nextChar();
				if(str.charAt(1) == next) {
					tokens.add(new Token(line, str));
					return true;
				}
				returnChar(next);
			}
		}
		return false;
	}

	private Character nextChar() {
		try {
			int value = reader.read();
//			System.out.println(":" + value);
			if(value == -1) {
				return null;
			}
			return (char) value;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void returnChar(char ch) {
		try {
			reader.unread(ch);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}

package org.sodeja.ilan.lexer;

import static org.sodeja.functional.Maybe.just;
import static org.sodeja.functional.Maybe.nothing;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PushbackReader;
import java.io.Reader;
import java.io.StringReader;

import org.sodeja.functional.Maybe;
import org.sodeja.generator.Generator;
import org.sodeja.generator.GeneratorFunction;

public class ILanLexer implements GeneratorFunction<Token> {
	public static Generator<Token> tokenize(String str) {
		return tokenize(new StringReader(str));
	}
	
	public static Generator<Token> tokenize(InputStream is) {
		return tokenize(new InputStreamReader(is));
	}
	
	public static Generator<Token> tokenize(Reader reader) {
		return new Generator<Token>(new ILanLexer(reader));
	}

	private PushbackReader reader;
	
	private ILanLexer(Reader reader) {
		this.reader = new PushbackReader(reader);
	}

	@Override
	public Maybe<Token> execute() {
		Character ch = readChar();
		if(ch == null) {
			return nothing();
		}
		
		if(Character.isWhitespace(ch)) {
			ch = readWhiteSpaces();
			if(ch == null) {
				return nothing();
			}
		}
		
		Token result = readSeparator(ch);
		if(result != null) {
			return just(result);
		}
		
		result = readNumber(ch);
		if(result != null) {
			return just(result);
		}
		
		result = readIdentifier(ch);
		if(result != null) {
			return just(result);
		}
		
		throw new IllegalArgumentException("Unknown char: " + ch);
	}

	private Character readWhiteSpaces() {
		for(Character ch = readChar();ch != null;ch = readChar()) {
			if(! Character.isWhitespace(ch)) {
				return ch;
			}
		}
		return null;
	}
	
	private SerparatorToken readSeparator(Character ch) {
		for(SerparatorToken sep : SerparatorToken.values()) {
			if(sep.getCh() == ch) {
				return sep;
			}
		}
		return null;
	}
	
	private NumberToken readNumber(Character initial) {
		// TODO does not performs proper check
		if(! Character.isDigit(initial)) {
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(initial);
		
		Character ch = null;
		for(ch = readChar(); Character.isDigit(ch) ; ch = readChar()) {
			sb.append(ch);
		}
		
		unreadChar(ch);
		
		return new NumberToken(new Integer(sb.toString()));
	}
	
	private IdentifierToken readIdentifier(Character initial) {
		if(! isIdentifierStart(initial)) {
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		sb.append(initial);
		
		Character ch = null;
		for(ch = readChar(); isIdentifierPart(ch) ; ch = readChar()) {
			sb.append(ch);
		}
		
		unreadChar(ch);
		
		return new IdentifierToken(sb.toString());
	}
	
	private static boolean isIdentifierStart(char ch) {
		return Character.isJavaIdentifierStart(ch) || ch == '+';
	}
	
	private static boolean isIdentifierPart(char ch) {
		return Character.isJavaIdentifierPart(ch) || ch == '+';
	}

	private Character readChar() {
		try {
			int value = reader.read();
			if(value == -1) {
				return null;
			}
			return (char) value;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	private void unreadChar(char ch) {
		try {
			reader.unread(ch);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}

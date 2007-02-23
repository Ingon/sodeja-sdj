package org.sodeja.sdj.lexer;

import java.io.Reader;

import org.sodeja.parsec.lexer.AbstractLexer;
import org.sodeja.parsec.lexer.LexerHelper;

public class Lexer extends AbstractLexer<Token> {
	
	private static final String[] TWO_CHARS = {"==", "~=", ">=", "<=", "->"};
	
	private int line = 0;
	
	public Lexer(Reader originalReader) {
		super(originalReader);
	}

	@Override
	protected Token createTokenFrom(String str) {
		return new Token(line, helper);
	}

	@Override
	protected void tokenizeDelegate(char ch) {
		if (Character.isWhitespace(ch)) {
			if (LexerHelper.readEOL(this, ch)) {
				line++;
			}
			return;
		}

		if (ch == '-') {
			char nextChar = readChar();
			if (nextChar == '-') {
				LexerHelper.readTillEOL(this);
				line++;
				return;
			}
			unreadChar(nextChar);
		}

		if (Character.isDigit(ch)) {
			LexerHelper.readNumberToken(this, ch);
			return;
		}

		if (Character.isJavaIdentifierStart(ch)) {
			LexerHelper.readIdentifier(this, ch);
			return;
		}

		if (twoChars(ch)) {
			return;
		}

		tokens.add(new Token(line, ch));
	}
	
	private boolean twoChars(char initial) {
		for(String str : TWO_CHARS) {
			if(str.charAt(0) == initial) {
				char next = readChar();
				if(str.charAt(1) == next) {
					tokens.add(new Token(line, str));
					return true;
				}
				unreadChar(next);
			}
		}
		return false;
	}
}

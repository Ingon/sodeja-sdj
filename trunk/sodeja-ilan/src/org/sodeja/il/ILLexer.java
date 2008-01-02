package org.sodeja.il;

import java.io.Reader;

import org.sodeja.parsec.lexer.AbstractLexer;
import org.sodeja.parsec.lexer.LexerHelper;

public class ILLexer extends AbstractLexer<String> {
	
	private static char[] dividers = new char[] {';', '(', ')', '{', '}', ' ', '\r', '\n', '='};

	public static boolean isDivider(char ch) {
		for(int i = 0;i < dividers.length;i++) {
			if(dividers[i] == ch) {
				return true;
			}
		}
		return false;
	}
	
	public static final String CRLF = "\r\n";

	public ILLexer(Reader originalReader) {
		super(originalReader);
	}

	@Override
	protected String createTokenFrom(String str) {
		return str;
	}

	@Override
	protected void tokenizeDelegate(char ch) {
		if(Character.isWhitespace(ch)) {
			if(LexerHelper.readEOL(this, ch)) {
				if(tokens.get(tokens.size() - 1) != CRLF) {
					tokens.add(CRLF);
				}
			}
			return;
		}
		
		if(Character.isDigit(ch)) {
			LexerHelper.readNumberToken(this, ch);
			return;
		}
		
		if(ch == '\"') {
			LexerHelper.readString(this, ch);
			return;
		}
		
		if(ch == '\'') {
			LexerHelper.readQString(this, ch);
			return;
		}
		
		if(ch == ';') {
			LexerHelper.readTillEOL(this);
			return;
		}
		
		if(isDivider(ch)) {
			tokens.add(String.valueOf(ch));
			return;
		}
		
		readToDivider(ch);
	}

	private void readToDivider(char init) {
		helper.setLength(0);
		helper.append(init);
		
		for(Character ch = readChar(); ch != null; ch = readChar()) {
			if(isDivider(ch)) {
				tokens.add(createTokenFrom(helper.toString()));
				unreadChar(ch);
				return;
			}
			
			helper.append(ch);
		}
		
		tokens.add(createTokenFrom(helper.toString()));
	}
}

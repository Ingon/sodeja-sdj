package org.sodeja.scheme.parse;

import java.io.Reader;

import org.sodeja.collections.ListUtils;
import org.sodeja.parsec.lexer.AbstractLexer;
import org.sodeja.parsec.lexer.LexerHelper;

public class Lexer extends AbstractLexer<String> {
	public Lexer(Reader originalReader) {
		super(originalReader);
	}

	@Override
	protected String createTokenFrom(String str) {
		return str;
	}

	@Override
	protected void tokenizeDelegate(char ch) {
		if(Character.isWhitespace(ch)) {
			if(! " ".equals(ListUtils.last(tokens))) {
				tokens.add(" ");
			}
			
			Character temp = readChar();
			if(temp == null) {
				int lastIndex = tokens.size() - 1;
				String last = tokens.get(lastIndex);
				if(" ".equals(last)) {
					tokens.remove(lastIndex);
				}
			} else {
				unreadChar(temp);
			}
			
			return;
		}

		if(ch == ';') {
			LexerHelper.readTillEOL(this);
			return;
		}
		
		if(ch == '(') {
			tokens.add("(");
			return;
		}
//		if(Character.isDigit(ch) || Character.isLetter(ch)) {
			LexerHelper.readIdentifierS(this, ch);
//			return;
//		}
//		
//		tokens.add(String.valueOf(ch));
	}
}

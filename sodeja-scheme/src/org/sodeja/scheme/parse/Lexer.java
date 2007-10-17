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
			String last = ListUtils.last(tokens);
			if(last != null && !" ".equals(last)) { 
				tokens.add(" ");
			}
			
			Character temp = readChar();
			if(temp == null) {
				int lastIndex = tokens.size() - 1;
				String lastToken = tokens.get(lastIndex);
				if(" ".equals(lastToken)) {
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
		
		LexerHelper.readIdentifierS(this, ch);
	}
}

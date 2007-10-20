package org.sodeja.scheme2.parse;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.Deque;
import java.util.LinkedList;

import org.sodeja.scheme2.model.Combination;
import org.sodeja.scheme2.model.Symbol;
import org.sodeja.scheme2.model.Token;

public class StreamParser {
	public Deque<Token> tokenize(Reader baseReader) throws IOException {
		PushbackReader reader = new PushbackReader(baseReader);
		
		Deque<Token> stack = new LinkedList<Token>();
		stack.offerLast(new Combination());
		
		StringBuilder currentPrimitiveToken = new StringBuilder();
		for(Character ch = read(reader);ch != null;ch = read(reader)) {
			if(ch == ';') {
				while((ch = read(reader)) != null) {
					if(ch == '\n') {
						break;
					}
					
					if(ch == '\r') {
						Character next = read(reader);
						if(next == '\n') {
							break;
						}
						unread(reader, next);
						break;
					}
				}
				continue;
			}
			
			if(ch == '(') {
				Combination listToken = new Combination();
				((Combination) stack.peekLast()).addToken(listToken);
				stack.addLast(listToken);
				continue;
			} 

			if(ch == ')') {
				Combination theToken = (Combination) stack.pollLast();
				if(currentPrimitiveToken.length() != 0) {
					theToken.addToken(new Symbol(currentPrimitiveToken.toString()));
					currentPrimitiveToken.setLength(0);
				}
				
				if(isQuoteToken(theToken) || isQuoteToken(stack.peekLast())) {
					stack.pollLast();
				}
				continue;
			}
			
			if(Character.isWhitespace(ch)) {
				if(currentPrimitiveToken.length() == 0) {
					if(isQuoteToken(stack.peekLast())) {
						stack.pollLast();
					}
					continue;
				}
				
				Combination theToken = (Combination) stack.peekLast();
				theToken.addToken(new Symbol(currentPrimitiveToken.toString()));
				currentPrimitiveToken.setLength(0);
				if(isQuoteToken(theToken)) {
					stack.pollLast();
				}
				continue;
			}
			
			if(ch == '\'') {
				Combination listToken = new Combination();
				listToken.addToken(new Symbol("quote"));
				
				((Combination) stack.peekLast()).addToken(listToken);
				stack.addLast(listToken);
				continue;
			}
			
			currentPrimitiveToken.append(ch);
		}
		
		if(currentPrimitiveToken.length() != 0) {
			Combination theToken = (Combination) stack.peekLast();
			theToken.addToken(new Symbol(currentPrimitiveToken.toString()));
		}
		
		if(stack.isEmpty()) {
			throw new RuntimeException("Unable to parse");
		}
		
		if(stack.size() == 2) {
			Token last = stack.pollLast();
			if(! isQuoteToken(last)) {
				throw new RuntimeException("Unable to parse");
			}
		}
		
		return ((Combination) (stack.pollLast())).tokens;
	}
	
	private Character read(PushbackReader reader) throws IOException {
		int value = reader.read();
		if(value == -1) {
			return null;
		}
		return (char) value;
	}
	
	private void unread(PushbackReader reader, Character ch) throws IOException {
		if(ch == null) {
			return;
		}
		reader.unread(ch);
	}
	
	private boolean isQuoteToken(Token token) {
		if(! (token instanceof Combination)) {
			return false;
		}
		
		Combination list = (Combination) token;
		if(list.size() != 2) {
			return false;
		}
		
		Token first = list.tokens.peekFirst();
		if(! (first instanceof Symbol)) {
			return false;
		}
		
		return ((Symbol) first).value.equals("quote");
	}
}

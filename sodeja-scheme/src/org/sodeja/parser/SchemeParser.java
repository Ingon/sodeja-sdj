package org.sodeja.parser;

import java.io.IOException;
import java.io.PushbackReader;
import java.io.Reader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

@SuppressWarnings("unchecked")
public class SchemeParser<V, C extends List> {
	
	private final Class<V> symbolClazz;
	private final Class<C> combinationClazz;

	private final V quote;
	
	public SchemeParser(Class<V> symbolClazz, Class<C> combinationClazz) {
		this.symbolClazz = symbolClazz;
		this.combinationClazz = combinationClazz;
		
		this.quote = createSymbol("quote");
	}
	
	public C tokenize(Reader baseReader) throws IOException {
		PushbackReader reader = new PushbackReader(baseReader);
		
		Deque<C> stack = new LinkedList<C>();
		stack.offerLast(createCombination());
		
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
				C listToken = createCombination();
				((C) stack.peekLast()).add(listToken);
				stack.addLast(listToken);
				continue;
			} 

			if(ch == ')') {
				C theToken = (C) stack.pollLast();
				if(currentPrimitiveToken.length() != 0) {
					theToken.add(createSymbol(currentPrimitiveToken.toString()));
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
				
				C theToken = (C) stack.peekLast();
				theToken.add(createSymbol(currentPrimitiveToken.toString()));
				currentPrimitiveToken.setLength(0);
				if(isQuoteToken(theToken)) {
					stack.pollLast();
				}
				continue;
			}
			
			if(ch == '\'') {
				C listToken = createCombination();
				listToken.add(quote);
				
				((C) stack.peekLast()).add(listToken);
				stack.addLast(listToken);
				continue;
			}
			
			if(ch == '\"') {
				StringBuilder sb = new StringBuilder();
				sb.append(ch);
				while((ch = read(reader)) != '\"') {
					sb.append(ch);
				}
				sb.append(ch);
				
				C theToken = (C) stack.peekLast();
				theToken.add(createSymbol(sb.toString()));
				
				continue;
			}
			
			currentPrimitiveToken.append(ch);
		}
		
		if(currentPrimitiveToken.length() != 0) {
			C theToken = (C) stack.peekLast();
			theToken.add(createSymbol(currentPrimitiveToken.toString()));
		}
		
		if(stack.isEmpty()) {
			throw new RuntimeException("Unable to parse");
		}
		
		if(stack.size() == 2) {
			Object last = stack.pollLast();
			if(! isQuoteToken(last)) {
				throw new RuntimeException("Unable to parse");
			}
		}
		
		return (C) (stack.pollLast());
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
	
	private boolean isQuoteToken(Object token) {
		if(! combinationClazz.isInstance(token)) {
			return false;
		}
		
		C list = (C) token;
		if(list.size() != 2) {
			return false;
		}
		
		Object first = list.get(0);
		if(! (symbolClazz.isInstance(first))) {
			return false;
		}
		
		return first.equals(quote);
	}
	
	protected V createSymbol(String value) {
		try {
			return (V) symbolClazz.getConstructors()[0].newInstance(value);
		} catch(Exception exc) {
			throw new RuntimeException(exc);
		}
	}
	
	protected C createCombination() {
		try {
			return (C) combinationClazz.newInstance();
		} catch(Exception exc) {
			throw new RuntimeException(exc);
		}
	}
}

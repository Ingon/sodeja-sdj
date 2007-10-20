package org.sodeja.scheme2.model;

import java.util.Deque;
import java.util.LinkedList;

import org.sodeja.scheme2.Utils;

public class Combination implements Token {
	public final Deque<Token> tokens;
	
	public Combination() {
		this.tokens = new LinkedList<Token>();
	}
	
	public void addToken(Token token) {
		tokens.offerLast(token);
	}
	
	public int size() {
		return tokens.size();
	}
	
	public Token head() {
		return Utils.head(tokens);
	}
	
	public Deque<Token> tail() {
		return Utils.tail(tokens);
	}
	
	@Override
	public String toString() {
		return tokens.toString();
	}
}

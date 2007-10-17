package org.sodeja.scheme2.execute;

import java.util.Deque;

import org.sodeja.scheme2.model.Token;

public interface Form {
	public Object eval(Frame frame, Deque<Token> parts);
}

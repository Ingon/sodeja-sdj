package org.sodeja.scheme2;

import java.util.Deque;
import java.util.LinkedList;

import org.sodeja.collections.CollectionUtils;
import org.sodeja.functional.Function1;
import org.sodeja.scheme2.execute.Frame;
import org.sodeja.scheme2.model.Token;

public class Utils {
	public static <T> T head(Deque<T> deque) {
		return deque.peekFirst();
	}
	
	public static <T> Deque<T> tail(Deque<T> deque) {
		LinkedList<T> result = new LinkedList<T>(deque);
		result.pollFirst();
		return result;
	}
	
	public static <T, R> Deque<R> map(Deque<T> source, Function1<R, T> functor) {
		 return (Deque<R>) CollectionUtils.map(source, new LinkedList<R>(), functor);
	}
	
	public static Deque<Object> evalDeque(final Frame frame, Deque<Token> parts) {
		return map(parts, new Function1<Object, Token>() {
			@Override
			public Object execute(Token p) {
				return frame.eval(p);
			}});
	}
	
	public static Object unknownToken(Token token) {
		throw new IllegalArgumentException("Unknown token: " + token);
	}
}

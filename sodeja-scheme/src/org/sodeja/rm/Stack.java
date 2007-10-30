package org.sodeja.rm;

import java.util.Deque;
import java.util.LinkedList;

public class Stack {
	private Deque<Object> vals;
	
	public Stack() {
		vals = new LinkedList<Object>();
	}
	
	public Object pop() {
		return vals.removeFirst();
	}
	
	public void push(Object obj) {
		vals.addFirst(obj);
	}
}

package org.sodeja.runtime.scheme.model;

import java.util.LinkedList;
import java.util.List;

import org.sodeja.runtime.scheme.SchemeExpression;

public class Combination implements SchemeExpression {
	public final List<SchemeExpression> parts;
	
	public Combination() {
		this.parts = new LinkedList<SchemeExpression>();
	}

	public int size() {
		return parts.size();
	}
	
	public SchemeExpression get(int index) {
		return parts.get(index);
	}
	
	@Override
	public String toString() {
		return parts.toString();
	}
}

package org.sodeja.sil.model.literal;

import java.util.List;

import org.sodeja.sil.model.Literal;

public class ObjectArrayLiteral implements Literal {
	public final List<LiteralArrayElement> elements;

	public ObjectArrayLiteral(List<LiteralArrayElement> elements) {
		this.elements = elements;
	}
}

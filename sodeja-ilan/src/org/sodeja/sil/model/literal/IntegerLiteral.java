package org.sodeja.sil.model.literal;

import org.sodeja.sil.model.Literal;

public class IntegerLiteral implements Literal, LiteralArrayElement {
	public final Integer value;

	public IntegerLiteral(Integer value) {
		this.value = value;
	}
}

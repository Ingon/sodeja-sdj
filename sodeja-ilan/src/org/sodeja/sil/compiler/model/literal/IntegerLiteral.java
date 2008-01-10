package org.sodeja.sil.compiler.model.literal;

import org.sodeja.sil.compiler.model.Literal;

public class IntegerLiteral implements Literal, LiteralArrayElement {
	public final Integer value;

	public IntegerLiteral(Integer value) {
		this.value = value;
	}
}

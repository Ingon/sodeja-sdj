package org.sodeja.silan.compiler.src;

public class StringLiteral implements Literal {
	public final String value;

	public StringLiteral(String value) {
		this.value = value.substring(1, value.length() - 1);
	}
}

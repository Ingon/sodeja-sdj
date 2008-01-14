package org.sodeja.silan.compiler;

public class IntegerLiteral implements Literal {
	public final Integer value;

	public IntegerLiteral(String strValue) {
		this.value = new Integer(strValue);
	}
}

package org.sodeja.silan.compiler.src;

public class BooleanLiteral implements Literal {
	public final Boolean value;
	
	public BooleanLiteral(String val) {
		this.value = Boolean.valueOf(val);
	}
}

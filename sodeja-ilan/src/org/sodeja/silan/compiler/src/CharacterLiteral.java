package org.sodeja.silan.compiler.src;

public class CharacterLiteral implements Literal {
	public final Character value;
	
	public CharacterLiteral(String str) {
		this.value = str.charAt(1);
	}
}

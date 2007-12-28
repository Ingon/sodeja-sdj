package org.sodeja.ilan.lexer;

public class ILanIdentifier implements ILanToken {
	public final String name;

	public ILanIdentifier(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "I[" + name + "]";
	}
}

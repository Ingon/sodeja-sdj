package org.sodeja.silan.compiler;

public class BinaryHeader implements Header {
	public final String selector;
	public final String argument;
	
	public BinaryHeader(String selector, String argument) {
		this.selector = selector;
		this.argument = argument;
	}
}

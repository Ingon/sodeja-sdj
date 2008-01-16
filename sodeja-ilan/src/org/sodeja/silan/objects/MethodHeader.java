package org.sodeja.silan.objects;

import java.util.List;

public class MethodHeader {
	public final String selector;
	public final List<String> arguments;
	public final List<String> locals;
	public final Integer stackSize;
	
	public MethodHeader(String selector, List<String> arguments,
			List<String> locals, Integer stackSize) {
		this.selector = selector;
		this.arguments = arguments;
		this.locals = locals;
		this.stackSize = stackSize;
	}
}

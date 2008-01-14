package org.sodeja.sil.runtime.exec;

import java.util.List;

public class Method {
	public final String selector;
	public final Object methodClass;
	public final String source;
	public final int argumentsCount;
	public final int tempCount;
	public final List<Instruction> instructions;
	
	public Method(String selector, Object methodClass, String source,
			int argumentsCount, int frameSize, List<Instruction> instructions) {
		this.selector = selector;
		this.methodClass = methodClass;
		this.source = source;
		this.argumentsCount = argumentsCount;
		this.tempCount = frameSize;
		this.instructions = instructions;
	}
}

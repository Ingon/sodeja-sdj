package org.sodeja.silan.compiler;

public class BinaryMessage implements Message {
	public final String selector;
	public final BinaryMessageOperand operand;
	
	public BinaryMessage(String selector, BinaryMessageOperand operand) {
		this.selector = selector;
		this.operand = operand;
	}
}

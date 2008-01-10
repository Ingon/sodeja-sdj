package org.sodeja.sil.compiler.model;

public class BinaryMessageOperand {
	public final Operand operand;
	public final UnaryMessageChain chain;
	
	public BinaryMessageOperand(Operand operand, UnaryMessageChain chain) {
		this.operand = operand;
		this.chain = chain;
	}
}

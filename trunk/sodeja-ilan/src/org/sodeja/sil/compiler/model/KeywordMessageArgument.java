package org.sodeja.sil.compiler.model;

public class KeywordMessageArgument {
	public final BinaryMessageOperand operand;
	public final BinaryMessageChain chain;
	
	public KeywordMessageArgument(BinaryMessageOperand operand, BinaryMessageChain chain) {
		this.operand = operand;
		this.chain = chain;
	}
}

package org.sodeja.sil.compiler.model;

import java.util.List;

public class Expression {
	public final Operand operand;
	public final MessageChain chain;
	public final List<CascadedMessage> cascade;
	
	public Expression(Operand operand, MessageChain chain, List<CascadedMessage> cascade) {
		this.operand = operand;
		this.chain = chain;
		this.cascade = cascade;
	} 
}

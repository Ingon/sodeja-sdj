package org.sodeja.sil.model;

public class MessageChainUnary implements MessageChain {
	public final UnaryMessage um;
	public final UnaryMessageChain umc;
	public final BinaryMessageChain bmc;
	public final KeywordMessage km;
	
	public MessageChainUnary(UnaryMessage um, UnaryMessageChain umc,
			BinaryMessageChain bmc, KeywordMessage km) {
		this.um = um;
		this.umc = umc;
		this.bmc = bmc;
		this.km = km;
	} 
}

package org.sodeja.sil.compiler.model;

public class MessageChainBinary implements MessageChain {
	public final BinaryMessage bm;
	public final BinaryMessageChain bmc;
	public final KeywordMessage km;
	
	public MessageChainBinary(BinaryMessage bm, BinaryMessageChain bmc, KeywordMessage km) {
		this.bm = bm;
		this.bmc = bmc;
		this.km = km;
	}
}

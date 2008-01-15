package org.sodeja.silan.compiler.src;

import java.util.List;

public class BinaryRootMessage implements Message {
	public final List<BinaryMessage> binaries;
	public final KeywordMessage keyword;
	
	public BinaryRootMessage(List<BinaryMessage> binaries, KeywordMessage keyword) {
		this.binaries = binaries;
		this.keyword = keyword;
	}
}

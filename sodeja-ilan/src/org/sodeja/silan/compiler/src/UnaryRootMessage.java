package org.sodeja.silan.compiler.src;

import java.util.List;

public class UnaryRootMessage implements Message {
	public final List<UnaryMessage> unaries;
	public final List<BinaryMessage> binaries;
	public final KeywordMessage keyword;
	
	public UnaryRootMessage(List<UnaryMessage> unaries, List<BinaryMessage> binaries, KeywordMessage keyword) {
		this.unaries = unaries;
		this.binaries = binaries;
		this.keyword = keyword;
	}
}

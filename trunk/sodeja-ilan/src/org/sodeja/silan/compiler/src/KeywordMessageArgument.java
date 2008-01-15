package org.sodeja.silan.compiler.src;

import java.util.List;

public class KeywordMessageArgument {
	public final String keyword;
	public final Primary primary;
	public final List<UnaryMessage> unaries;
	public final List<BinaryMessage> binaries;
	
	public KeywordMessageArgument(String keyword, Primary primary, List<UnaryMessage> unaries, List<BinaryMessage> binaries) {
		this.keyword = keyword;
		this.primary = primary;
		this.unaries = unaries;
		this.binaries = binaries;
	}
}

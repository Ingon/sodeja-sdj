package org.sodeja.sil.compiler.model;

public class KeywordMethodHeaderSegment {
	public final Keyword keyword;
	public final Identifier identifier;
	
	public KeywordMethodHeaderSegment(Keyword keyword, Identifier identifier) {
		this.keyword = keyword;
		this.identifier = identifier;
	}
}

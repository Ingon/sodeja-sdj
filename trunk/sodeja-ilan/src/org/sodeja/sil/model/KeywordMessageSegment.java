package org.sodeja.sil.model;

public class KeywordMessageSegment {
	public final Keyword keyword;
	public final KeywordMessageArgument argument;
	
	public KeywordMessageSegment(Keyword keyword, KeywordMessageArgument argument) {
		this.keyword = keyword;
		this.argument = argument;
	}
}

package org.sodeja.sil.model;

import java.util.List;

public class KeywordMessage implements MessageChain {
	public final KeywordMessageSegment start;
	public final List<KeywordMessageSegment> rest;
	
	public KeywordMessage(KeywordMessageSegment start, List<KeywordMessageSegment> rest) {
		this.start = start;
		this.rest = rest;
	}
}

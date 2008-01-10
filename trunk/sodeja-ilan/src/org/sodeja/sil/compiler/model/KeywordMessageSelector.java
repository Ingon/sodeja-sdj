package org.sodeja.sil.compiler.model;

import java.util.List;

public class KeywordMessageSelector {
	public final Keyword start;
	public final List<Keyword> rest;
	
	public KeywordMessageSelector(Keyword start, List<Keyword> rest) {
		this.start = start;
		this.rest = rest;
	}
}

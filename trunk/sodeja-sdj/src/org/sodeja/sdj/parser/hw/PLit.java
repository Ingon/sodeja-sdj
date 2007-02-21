package org.sodeja.sdj.parser.hw;

import java.util.List;

import org.sodeja.functional.Pair;
import org.sodeja.sdj.parser.AbstractParser;

public class PLit extends AbstractParser<String, String> {
	private final String expected;	
	
	public PLit(String expected) {
		this.expected = expected;
	}
		
	@Override
	protected List<Pair<String, List<String>>> executeDelegate(List<String> tokens) {
		if(tokens.get(0).equals(expected)) {
			return AbstractParser.createWithRemove(expected, tokens);
		}
		return EMPTY;
	}
}

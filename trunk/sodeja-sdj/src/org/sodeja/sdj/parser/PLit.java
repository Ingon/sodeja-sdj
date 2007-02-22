package org.sodeja.sdj.parser;

import org.sodeja.functional.Predicate1;
import org.sodeja.parsec.SatisfiesParser;

public class PLit extends SatisfiesParser<String> {
	public PLit(final String expected) {
		super(expected, new Predicate1<String>() {
			public Boolean execute(String p) {
				return expected.equals(p);
			}});
	}
}

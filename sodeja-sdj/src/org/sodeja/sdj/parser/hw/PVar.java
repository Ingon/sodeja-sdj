package org.sodeja.sdj.parser.hw;

import java.util.List;

import org.sodeja.functional.Pair;
import org.sodeja.sdj.parser.AbstractParser;

public class PVar extends AbstractParser<String, String> {
	@Override
	protected List<Pair<String, List<String>>> executeDelegate(List<String> tokens) {
		if(Character.isJavaIdentifierStart(tokens.get(0).charAt(0))) {
			return createWithRemove(tokens.get(0), tokens);
		}
		return EMPTY;
	}
}

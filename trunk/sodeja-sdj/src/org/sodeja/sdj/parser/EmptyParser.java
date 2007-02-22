package org.sodeja.sdj.parser;

import java.util.List;

import org.sodeja.functional.Pair;

public class EmptyParser<Tok, Res> extends AbstractParser<Tok, Res> {
	@Override
	protected List<Pair<Res, List<Tok>>> executeDelegate(List<Tok> tokens) {
		return EMPTY;
	}
}

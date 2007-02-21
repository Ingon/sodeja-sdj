package org.sodeja.sdj.parser;

import java.util.List;

import org.sodeja.functional.Pair;

public interface Parser<Tok, Res> {
	public List<Pair<Res, List<Tok>>> execute(List<Tok> tokens);
}

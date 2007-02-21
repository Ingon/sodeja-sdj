package org.sodeja.sdj.parser;

import java.util.List;

import org.sodeja.functional.Function2;
import org.sodeja.functional.Pair;

public class ThenParser<Tok, Res, Res1, Res2> extends AbstractParser<Tok, Res> {

	private final Parser<Tok, Res1> first;
	private final Parser<Tok, Res2> second;
	private final Function2<Res, Res1, Res2> combinator;
	
	public ThenParser(final Parser<Tok, Res1> first, final Parser<Tok, Res2> second, final Function2<Res, Res1, Res2> combinator) {
		this.first = first;
		this.second = second;
		this.combinator = combinator;
	}

	@Override
	protected List<Pair<Res, List<Tok>>> executeDelegate(List<Tok> tokens) {
		List<Pair<Res1, List<Tok>>> firstResult = first.execute(tokens);
		List<Pair<Res2, List<Tok>>> secondResult = second.execute(firstResult.get(0).second);
		
		return create(
				combinator.execute(firstResult.get(0).first, secondResult.get(0).first), 
				secondResult.get(0).second);
	}
}

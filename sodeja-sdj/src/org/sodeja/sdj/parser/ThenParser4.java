package org.sodeja.sdj.parser;

import java.util.List;

import org.sodeja.functional.Function4;
import org.sodeja.functional.Pair;

public class ThenParser4<Tok, Res, Res1, Res2, Res3, Res4> extends AbstractParser<Tok, Res> {

	private final Parser<Tok, Res1> first;
	private final Parser<Tok, Res2> second;
	private final Parser<Tok, Res3> third;
	private final Parser<Tok, Res4> fourth;
	private final Function4<Res, Res1, Res2, Res3, Res4> combinator;
	
	public ThenParser4(final Parser<Tok, Res1> first, final Parser<Tok, Res2> second, 
			final Parser<Tok, Res3> third, final Parser<Tok, Res4> fourth, 
			final Function4<Res, Res1, Res2, Res3, Res4> combinator) {
		this.first = first;
		this.second = second;
		this.third = third;
		this.fourth = fourth;
		this.combinator = combinator;
	}

	@Override
	protected List<Pair<Res, List<Tok>>> executeDelegate(List<Tok> tokens) {
		List<Pair<Res1, List<Tok>>> firstResult = first.execute(tokens);
		List<Pair<Res2, List<Tok>>> secondResult = second.execute(firstResult.get(0).second);
		List<Pair<Res3, List<Tok>>> thirdResult = third.execute(secondResult.get(0).second);
		List<Pair<Res4, List<Tok>>> fourthResult = fourth.execute(thirdResult.get(0).second);
		
		return create(
				combinator.execute(
						firstResult.get(0).first, 
						secondResult.get(0).first, 
						thirdResult.get(0).first,
						fourthResult.get(0).first), 
				fourthResult.get(0).second);
	}
}

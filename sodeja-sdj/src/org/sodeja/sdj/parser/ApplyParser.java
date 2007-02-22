package org.sodeja.sdj.parser;

import java.util.List;

import org.sodeja.functional.Function1;
import org.sodeja.functional.Pair;

public class ApplyParser<Tok, Res, Res1> extends AbstractParser<Tok, Res> {

	private final Parser<Tok, Res1> parser;
	private final Function1<Res, Res1> functor;
	
	public ApplyParser(final Parser<Tok, Res1> parser, final Function1<Res, Res1> functor) {
		this.parser = parser;
		this.functor = functor;
	}

	@Override
	protected List<Pair<Res, List<Tok>>> executeDelegate(List<Tok> tokens) {
		List<Pair<Res1, List<Tok>>> result = parser.execute(tokens);
		return create(functor.execute(result.get(0).first), result.get(0).second);
	}
}

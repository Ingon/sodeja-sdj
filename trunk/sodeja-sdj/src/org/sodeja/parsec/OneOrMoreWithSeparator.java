package org.sodeja.parsec;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.functional.Pair;

public class OneOrMoreWithSeparator<Tok, Res, Res1> extends AbstractParser<Tok, Res> {

	private final Parser<Tok, Res> internal;
	private final Parser<Tok, Res1> separator;
	
	public OneOrMoreWithSeparator(final Parser<Tok, Res> internal, final Parser<Tok, Res1> separator) {
		this.internal = internal;
		this.separator = separator;
	}

	@Override
	protected List<Pair<Res, List<Tok>>> executeDelegate(List<Tok> tokens) {
		List<Pair<Res, List<Tok>>> result = new ArrayList<Pair<Res,List<Tok>>>();
		List<Tok> tempTokens = tokens;
		
		List<Pair<Res, List<Tok>>> internalResult = internal.execute(tempTokens);
		if(internalResult.isEmpty()) {
			return EMPTY;
		}
		
		result.addAll(internalResult);
		
		for(List<Pair<Res1, List<Tok>>> separatorResult = separator.execute(tempTokens); 
				! separatorResult.isEmpty(); 
				separatorResult = separator.execute(tempTokens)) {
			
			// TODO Maybe a loop ?
			Pair<Res1, List<Tok>> separatorPair = separatorResult.get(0);
			tempTokens = separatorPair.second;
			
			internalResult = internal.execute(tempTokens);
			if(internalResult.isEmpty()) {
				return EMPTY;
			}
			
			result.addAll(internalResult);
		}
		
		return result;
	}
}

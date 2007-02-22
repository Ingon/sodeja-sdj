package org.sodeja.sdj.parser;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.functional.Function3;
import org.sodeja.functional.Pair;

public class ThenParser3<Tok, Res, Res1, Res2, Res3> extends AbstractParser<Tok, Res> {

	private final Parser<Tok, Res1> first;
	private final Parser<Tok, Res2> second;
	private final Parser<Tok, Res3> third;
	private final Function3<Res, Res1, Res2, Res3> combinator;
	
	public ThenParser3(final Parser<Tok, Res1> first, final Parser<Tok, Res2> second, final Parser<Tok, Res3> third, final Function3<Res, Res1, Res2, Res3> combinator) {
		this.first = first;
		this.second = second;
		this.third = third;
		this.combinator = combinator;
	}

	@Override
	protected List<Pair<Res, List<Tok>>> executeDelegate(List<Tok> tokens) {
		List<Pair<Res, List<Tok>>> result = new ArrayList<Pair<Res, List<Tok>>>();
		List<Tok> tempTokens = tokens;
		
		for(List<Pair<Res1, List<Tok>>> firstResult = first.execute(tempTokens); !firstResult.isEmpty(); firstResult = first.execute(tempTokens)) {
			for(Pair<Res1, List<Tok>> firstPair : firstResult) {
				List<Tok> intTokens = firstPair.second;
				
				for(List<Pair<Res2, List<Tok>>> secondResult = second.execute(intTokens); ! secondResult.isEmpty(); secondResult = second.execute(intTokens)) {
					for(Pair<Res2, List<Tok>> secondPair : secondResult) {
						List<Tok> int2Tokens = secondPair.second;
						
						for(List<Pair<Res3, List<Tok>>> thirdResult = third.execute(int2Tokens); ! thirdResult.isEmpty(); thirdResult = third.execute(int2Tokens)) {
							for(Pair<Res3, List<Tok>> thirdPair : thirdResult) {
								result.add(new Pair<Res, List<Tok>>(combinator.execute(firstPair.first, secondPair.first, thirdPair.first), thirdPair.second));
							}
						}
					}
				}
			}
		}
		
		return result;
//		List<Pair<Res1, List<Tok>>> firstResult = first.execute(tokens);
//		List<Pair<Res2, List<Tok>>> secondResult = second.execute(firstResult.get(0).second);
//		List<Pair<Res3, List<Tok>>> thirdResult = third.execute(secondResult.get(0).second);
//		
//		return create(
//				combinator.execute(firstResult.get(0).first, secondResult.get(0).first, thirdResult.get(0).first), 
//				thirdResult.get(0).second);
	}
}

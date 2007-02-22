package org.sodeja.parsec;

import java.util.List;

import org.sodeja.functional.Function1;
import org.sodeja.functional.Function2;
import org.sodeja.functional.Function3;
import org.sodeja.functional.Function4;

public class ParsecUtils {
	private ParsecUtils() {
	}
	
	public static <Tok, Res> Parser<Tok, List<Res>> oneOrMore(Parser<Tok, Res> parser) {
		return new OneOrMoreParser<Tok, Res>(parser);
	}
	
	public static <Tok, Res, Res1> Parser<Tok, List<Res>> oneOrMoreSep(Parser<Tok, Res> parser, Parser<Tok, Res1> sep) {
		return new OneOrMoreWithSeparator<Tok, Res, Res1>(parser, sep);
	}
	
	public static <Tok, Res> Parser<Tok, List<Res>> zeroOrMore(Parser<Tok, Res> parser) {
		return new ZeroOrMoreParser<Tok, Res>(parser);
	}
	
	public static <Tok, Res, Res1> Parser<Tok, Res> apply(Parser<Tok, Res1> parser, Function1<Res, Res1> functor) {
		return new ApplyParser<Tok, Res, Res1>(parser, functor);
	}
	
	public static <Tok, Res> Parser<Tok, Res> alternative(Parser<Tok, Res> first, Parser<Tok, Res> second) {
		return new AlternativeParser<Tok, Res>(first, second);
	}

	public static <Tok, Res> Parser<Tok, Res> alternative1(Parser first, Parser second) {
		return new AlternativeParser<Tok, Res>(first, second);
	}
	
	public static <Tok, Res, Res1, Res2> Parser<Tok, Res> thenParser(Parser<Tok, Res1> first, 
			Parser<Tok, Res2> second, 
			Function2<Res, Res1, Res2> combinator) {
		return new ThenParser<Tok, Res, Res1, Res2>(first, second, combinator);
	}

	public static <Tok, Res, Res1, Res2, Res3> Parser<Tok, Res> thenParser3(Parser<Tok, Res1> first, 
			Parser<Tok, Res2> second, 
			Parser<Tok, Res3> third, 
			Function3<Res, Res1, Res2, Res3> combinator) {
		return new ThenParser3<Tok, Res, Res1, Res2, Res3>(first, second, third, combinator);
	}

	public static <Tok, Res, Res1, Res2, Res3, Res4> Parser<Tok, Res> thenParser4(Parser<Tok, Res1> first, 
			Parser<Tok, Res2> second, 
			Parser<Tok, Res3> third, 
			Parser<Tok, Res4> fourth, 
			Function4<Res, Res1, Res2, Res3, Res4> combinator) {
		return new ThenParser4<Tok, Res, Res1, Res2, Res3, Res4>(first, second, third, fourth, combinator);
	}
}

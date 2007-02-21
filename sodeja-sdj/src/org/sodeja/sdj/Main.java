package org.sodeja.sdj;

import java.util.Collections;
import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function2;
import org.sodeja.functional.Pair;
import org.sodeja.sdj.parser.AlternativeParser;
import org.sodeja.sdj.parser.Parser;
import org.sodeja.sdj.parser.ThenParser;
import org.sodeja.sdj.parser.hw.PLit;
import org.sodeja.sdj.parser.hw.PVar;


public class Main {
	public static void main(String[] args) throws Exception {
//		List<String> initial = ListUtils.asList("hello", "John", "!");
//		System.out.println("Initial: " + initial);
//		List<Pair<String, List<String>>> result = PLit.Util.getParserFor("hello").execute(initial);
//		System.out.println("Result: " + result);
		
		List<String> initial = Collections.unmodifiableList(ListUtils.asList("hello", "John", "!"));
		System.out.println("Initial: " + initial);
		
		Parser<String, String> helloOrGoodbye = new AlternativeParser<String, String>(
				new PLit("hello"), new PLit("goodbye"));
		
		Parser<String, Pair<String, String>> greeting1 = new ThenParser<String, Pair<String, String>, String, String>(
				helloOrGoodbye, new PVar(), Function2.Utils.createPairFunctor(String.class, String.class));
		
		Parser<String, Pair<String, String>> greeting = new ThenParser<String, Pair<String,String>, Pair<String,String>, String>(
				greeting1, new PLit("!"), new Function2<Pair<String,String>, Pair<String,String>, String>() {
					public Pair<String, String> execute(Pair<String, String> p1, String p2) {
						return p1;
					}});

		List result = greeting.execute(initial);
		System.out.println("Result: " + result);
	}
}

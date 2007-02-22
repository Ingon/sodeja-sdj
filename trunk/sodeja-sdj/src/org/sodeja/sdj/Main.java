package org.sodeja.sdj;

import java.util.Collections;
import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function3;
import org.sodeja.functional.Pair;
import org.sodeja.sdj.parser.AlternativeParser;
import org.sodeja.sdj.parser.OneOrMoreParser;
import org.sodeja.sdj.parser.Parser;
import org.sodeja.sdj.parser.ThenParser3;
import org.sodeja.sdj.parser.hw.PLit;
import org.sodeja.sdj.parser.hw.PVar;

// TODO currently something very wrong. Test cases are needed more
public class Main {
	public static void main(String[] args) throws Exception {
//		List<String> initial = ListUtils.asList("hello", "John", "!");
//		System.out.println("Initial: " + initial);
//		List<Pair<String, List<String>>> result = PLit.Util.getParserFor("hello").execute(initial);
//		System.out.println("Result: " + result);
		
		List<String> initial = Collections.unmodifiableList(ListUtils.asList("hello", "John", "!", "hello", "John", "!"));
		System.out.println("Initial: " + initial);
		
		Parser<String, String> helloOrGoodbye = new AlternativeParser<String, String>(
				new PLit("hello"), new PLit("goodbye"));
		
		Parser<String, Pair<String, String>> greeting = new ThenParser3<String, Pair<String, String>, String, String, String>(
				helloOrGoodbye, new PVar(), new PLit("!"), 
				new Function3<Pair<String, String>, String, String, String>() {
					public Pair<String, String> execute(String p1, String p2, String p3) {
						return new Pair<String, String>(p1, p2);
					}
				});

		System.out.println("Result(greeting): " + greeting.execute(initial));
		
		Parser<String, List<Pair<String, String>>> oneOrMoreGreetings = new OneOrMoreParser<String, Pair<String, String>>(greeting);
		System.out.println("Result(oneOrMoreGreetings): " + oneOrMoreGreetings.execute(initial));
	}
}

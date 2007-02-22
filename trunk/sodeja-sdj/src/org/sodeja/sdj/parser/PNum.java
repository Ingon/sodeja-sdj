package org.sodeja.sdj.parser;

import java.util.List;

import org.sodeja.functional.Function1;
import org.sodeja.functional.Pair;
import org.sodeja.functional.Predicate1;
import org.sodeja.parsec.AbstractParser;
import org.sodeja.parsec.ApplyParser;
import org.sodeja.parsec.Parser;
import org.sodeja.parsec.SatisfiesParser;

public class PNum extends AbstractParser<String, Integer> {
	
	private static boolean allDigits(CharSequence chars) {
		for(int i = 0, n = chars.length();i < n;i++) {
			if(! Character.isDigit(chars.charAt(i))) {
				return false;
			}
		}
		return true;
	}
	
	private final Parser<String, String> digitsParser;
	private final Parser<String, Integer> convertParser;
	
	public PNum() {
		digitsParser = new SatisfiesParser<String>(new Predicate1<String>() {
			public Boolean execute(String p) {
				return allDigits(p);
			}});
		
		convertParser = new ApplyParser<String, Integer, String>(digitsParser, new Function1<Integer, String>() {
			public Integer execute(String p) {
				return new Integer(p);
			}});
	}
	
	@Override
	protected List<Pair<Integer, List<String>>> executeDelegate(List<String> tokens) {
		return convertParser.execute(tokens);
	}
}

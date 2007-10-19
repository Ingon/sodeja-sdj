package org.sodeja.runtime.runners;

import java.io.FileReader;
import java.util.List;

import org.sodeja.runtime.Library;
import org.sodeja.runtime.abs.CompoundLibrary;
import org.sodeja.runtime.scheme.SchemeEvaluator;
import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.form.SicpDialect;
import org.sodeja.runtime.scheme.library.ArithmeticLibrary;
import org.sodeja.runtime.scheme.library.BaseLibrary;
import org.sodeja.runtime.scheme.library.LogicalLibrary;
import org.sodeja.runtime.scheme.library.PairLibrary;
import org.sodeja.runtime.scheme.library.RelationalLibrary;
import org.sodeja.runtime.scheme.parse.SchemeParser;

public class SicpScheme {
	public static void main(String[] args) throws Exception {
		if(args.length == 0) {
			console();
			return;
		}

		long execStart = System.currentTimeMillis();
		SchemeParser parser = new SchemeParser();
		for(String arg : args) {
			SicpEvaluator runtime = new SicpEvaluator();
			List<SchemeExpression> expressions = parser.tokenize(new FileReader(arg));
			for(SchemeExpression expr : expressions) {
				System.out.println(expr);
				long start = System.currentTimeMillis();
				Object obj = runtime.eval(expr);
				long end = System.currentTimeMillis();
				System.out.println("(" + (end - start) + ")>" + obj);
			}
		}
		System.out.println("TOTAL: " + (System.currentTimeMillis() - execStart));
	}

	private static void console() {
		throw new UnsupportedOperationException();
	}
	
	private static class SicpEvaluator extends SchemeEvaluator {
		public SicpEvaluator() {
			super(new SicpDialect(), loadLibraries());
		}

		public Object eval(SchemeExpression expr) {
			return eval(this.rootFrame, expr);
		}
	}

	private static Library<SchemeExpression> loadLibraries() {
		CompoundLibrary<SchemeExpression> library = new CompoundLibrary<SchemeExpression>();
		library.addLibrary(new BaseLibrary());
		library.addLibrary(new LogicalLibrary());
		library.addLibrary(new PairLibrary());
		library.addLibrary(new ArithmeticLibrary());
		library.addLibrary(new RelationalLibrary());
		return library;
	}
}

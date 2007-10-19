package org.sodeja.runtime.runners;

import java.io.FileReader;
import java.util.List;

import org.sodeja.parser.SchemeParser;
import org.sodeja.runtime.Library;
import org.sodeja.runtime.impl.CompoundLibrary;
import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.model.Combination;
import org.sodeja.runtime.scheme.model.Symbol;
import org.sodeja.runtime.scheme3.CompiledSchemeDialect;
import org.sodeja.runtime.scheme3.CompiledSchemeEvaluator;
import org.sodeja.runtime.scheme3.CompiledSchemeExpression;
import org.sodeja.runtime.scheme3.CompiledSchemeFrame;
import org.sodeja.runtime.scheme3.library.ArithmeticLibrary;
import org.sodeja.runtime.scheme3.library.BaseLibrary;
import org.sodeja.runtime.scheme3.library.LogicalLibrary;
import org.sodeja.runtime.scheme3.library.PairLibrary;
import org.sodeja.runtime.scheme3.library.RelationalLibrary;

public class SicpCompilingScheme3 {
	public static void main(String[] args) throws Exception {
		if(args.length == 0) {
			console();
			return;
		}

		long execStart = System.currentTimeMillis();
		SchemeParser<Symbol, Combination> parser = new SchemeParser<Symbol, Combination>(Symbol.class, Combination.class);
		for(String arg : args) {
			SicpEvaluator runtime = new SicpEvaluator();
			List<SchemeExpression> expressions = parser.tokenize(new FileReader(arg));
			
			CompiledSchemeDialect compilator = new CompiledSchemeDialect();
			
			for(SchemeExpression expr : expressions) {
				System.out.println(expr);
				long start = System.currentTimeMillis();
				CompiledSchemeExpression compiledExpr = compilator.compile(expr);
				Object obj = runtime.eval(compiledExpr);
				long end = System.currentTimeMillis();
				System.out.println("(" + (end - start) + ")>" + obj);
			}
		}
		System.out.println("TOTAL: " + (System.currentTimeMillis() - execStart));
	}

	private static void console() {
		throw new UnsupportedOperationException();
	}
	
	private static class SicpEvaluator extends CompiledSchemeEvaluator {
		public SicpEvaluator() {
			super(loadLibraries());
		}

		public Object eval(CompiledSchemeExpression expr) {
			try {
				return eval(this.rootFrame, expr);
			} finally {
				System.out.println("FRAME COUNT: " + CompiledSchemeFrame.frameCount);
				System.out.println("FRAME LENGTH: " + CompiledSchemeFrame.maxLength);
			}
		}
	}

	private static Library<CompiledSchemeExpression> loadLibraries() {
		CompoundLibrary<CompiledSchemeExpression> library = new CompoundLibrary<CompiledSchemeExpression>();
		library.addLibrary(new BaseLibrary());
		library.addLibrary(new LogicalLibrary());
		library.addLibrary(new PairLibrary());
		library.addLibrary(new ArithmeticLibrary());
		library.addLibrary(new RelationalLibrary());
		return library;
	}
}

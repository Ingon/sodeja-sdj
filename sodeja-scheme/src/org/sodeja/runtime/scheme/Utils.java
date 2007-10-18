package org.sodeja.runtime.scheme;

import java.util.List;

import org.sodeja.runtime.Evaluator;
import org.sodeja.runtime.Frame;
import org.sodeja.runtime.scheme.model.Combination;

public class Utils {
	public static List<SchemeExpression> extractParts(Combination comb) {
		return comb.parts.subList(2, comb.parts.size());
	}
	
	public static <E extends SchemeExpression> Object[] eval(Evaluator<E> evaluator, Frame<E> frame, List<E> expressions) {
		return eval(evaluator, frame, expressions, 0);
	}

	public static <E extends SchemeExpression> Object[] eval(Evaluator<E> evaluator, Frame<E> frame, List<E> expressions, int startIndex) {
		Object[] values = new Object[expressions.size() - startIndex];
		for(int i = startIndex, n = expressions.size();i < n;i++) {
			values[i - startIndex] = evaluator.eval(frame, expressions.get(i));
		}
		return values;
	}

	public static <E extends SchemeExpression> Object evalSingle(Evaluator<E> evaluator, Frame<E> frame, List<E> expressions) {
		return evalSingle(evaluator, frame, expressions, 0);
	}

	public static <E extends SchemeExpression> Object evalSingle(Evaluator<E> evaluator, Frame<E> frame, List<E> expressions, int startIndex) {
		Object value = null;
		for(int i = startIndex, n = expressions.size();i < n;i++) {
			value = evaluator.eval(frame, expressions.get(i));
		}
		return value;
	}
}

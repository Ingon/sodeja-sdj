package org.sodeja.sdj;

import org.sodeja.sdj.expression.Application;
import org.sodeja.sdj.expression.Expression;
import org.sodeja.sdj.expression.Number;
import org.sodeja.sdj.expression.Program;
import org.sodeja.sdj.expression.Supercombinator;
import org.sodeja.sdj.expression.Variable;

public class PrettyPrinter {
	
	public static <T> void print(Program<T> program) {
		for(Supercombinator<T> combinator : program.definitions) {
			System.out.print(combinator.name);
			
			for(T binding : combinator.bindings) {
				System.out.print(" " + binding);
			}
			
			System.out.print(" = ");
			
			print(combinator.expression);
			
			System.out.println();
		}
	}
	
	public static <T> void print(Expression<T> expression) {
		if(expression instanceof Number) {
			System.out.print(((Number<T>) expression).value);
		} else if(expression instanceof Variable) {
			System.out.print(((Variable<T>) expression).name);
		} else if(expression instanceof Application) {
			Application<T> application = (Application<T>) expression;
			print(application.left);
			System.out.print(" ");
			printA(application.right);
		} else {
			throw new UnsupportedOperationException();
		}
	}
	
	public static <T> void printA(Expression<T> expression) {
		if(expression.isAtomic()) {
			print(expression);
		} else {
			System.out.print("(");
			print(expression);
			System.out.print(")");
		}
	}
}

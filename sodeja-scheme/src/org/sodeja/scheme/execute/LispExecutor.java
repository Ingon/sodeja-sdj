package org.sodeja.scheme.execute;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.VFunction1;
import org.sodeja.runtime.scheme.procedure.arithmetic.AverageProcedure;
import org.sodeja.runtime.scheme.procedure.arithmetic.DivProcedure;
import org.sodeja.runtime.scheme.procedure.arithmetic.MulProcedure;
import org.sodeja.runtime.scheme.procedure.arithmetic.SqrtProcedure;
import org.sodeja.runtime.scheme.procedure.arithmetic.SquareProcedure;
import org.sodeja.runtime.scheme.procedure.arithmetic.SubProcedure;
import org.sodeja.runtime.scheme.procedure.arithmetic.SumProcedure;
import org.sodeja.runtime.scheme.procedure.logical.NotProcedure;
import org.sodeja.runtime.scheme.procedure.pair.CarProcedure;
import org.sodeja.runtime.scheme.procedure.pair.CdrProcedure;
import org.sodeja.runtime.scheme.procedure.pair.ConsProcedure;
import org.sodeja.runtime.scheme.procedure.relational.BiggerThenProcedure;
import org.sodeja.runtime.scheme.procedure.relational.EqualProcedure;
import org.sodeja.runtime.scheme.procedure.relational.LessThenProcedure;
import org.sodeja.scheme.execute.form.CondForm;
import org.sodeja.scheme.execute.form.DefineForm;
import org.sodeja.scheme.execute.form.IfForm;
import org.sodeja.scheme.execute.form.LambdaForm;
import org.sodeja.scheme.execute.form.LetForm;
import org.sodeja.scheme.execute.form.SetForm;
import org.sodeja.scheme.execute.primitive.EqProcedure;
import org.sodeja.scheme.execute.primitive.QuitProcedure;
import org.sodeja.scheme.parse.model.Expression;
import org.sodeja.scheme.parse.model.Script;

public class LispExecutor {
	
	private final Frame frame;
	
	public LispExecutor() {
		final Map<String, Object> procedures = new HashMap<String, Object>() {
			private static final long serialVersionUID = -6084661181815911365L;
		{
			put("lambda", new LambdaForm());
			put("define", new DefineForm());
			put("let", new LetForm());
			put("cond", new CondForm());
			put("if", new IfForm());
			put("else", Boolean.TRUE);
			
			put("set!", new SetForm());
			
			// Here is an option - add these as libraries or something like this
			put("eq?", new EqProcedure());
			put("not", new NotProcedure());
			put("quit", new QuitProcedure());
			
			put("+", new SumProcedure());
			put("-", new SubProcedure());
			put("*", new MulProcedure());
			put("/", new DivProcedure());
			put("square", new SquareProcedure());
			put("sqrt", new SqrtProcedure());
			put("average", new AverageProcedure());
			
			put("<", new LessThenProcedure());
			put("=", new EqualProcedure());
			put(">", new BiggerThenProcedure());
						
			put("cons", new ConsProcedure());
			put("car", new CarProcedure());
			put("cdr", new CdrProcedure());
		}};
		
		frame = new Frame(null, procedures);
	}
	
	protected Frame getFrame() {
		return frame;
	}
	
	public void execute(final Script script) {
		ListUtils.execute(script.expressions, new VFunction1<Expression>() {
			@Override
			public void executeV(Expression p) {
				System.out.println(p);
				long start = System.currentTimeMillis();
				Object obj = frame.eval(p);
				long end = System.currentTimeMillis();
				System.out.println("(" + (end - start) + ")>" + obj);
			}});
	}
}

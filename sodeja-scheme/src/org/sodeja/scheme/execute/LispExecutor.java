package org.sodeja.scheme.execute;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.VFunction1;
import org.sodeja.scheme.execute.form.CondForm;
import org.sodeja.scheme.execute.form.DefineForm;
import org.sodeja.scheme.execute.form.IfForm;
import org.sodeja.scheme.execute.form.LambdaForm;
import org.sodeja.scheme.execute.form.LetForm;
import org.sodeja.scheme.execute.form.SetForm;
import org.sodeja.scheme.execute.primitive.EqProcedure;
import org.sodeja.scheme.execute.primitive.QuitProcedure;
import org.sodeja.scheme.execute.primitive.aritmetic.AverageProcedure;
import org.sodeja.scheme.execute.primitive.aritmetic.DivProcedure;
import org.sodeja.scheme.execute.primitive.aritmetic.MulProcedure;
import org.sodeja.scheme.execute.primitive.aritmetic.SqrtProcedure;
import org.sodeja.scheme.execute.primitive.aritmetic.SquareProcedure;
import org.sodeja.scheme.execute.primitive.aritmetic.SubProcedure;
import org.sodeja.scheme.execute.primitive.aritmetic.SumProcedure;
import org.sodeja.scheme.execute.primitive.logical.NotProcedure;
import org.sodeja.scheme.execute.primitive.pair.CarProcedure;
import org.sodeja.scheme.execute.primitive.pair.CdrProcedure;
import org.sodeja.scheme.execute.primitive.pair.ConsProcedure;
import org.sodeja.scheme.execute.primitive.relational.BiggerThenProcedure;
import org.sodeja.scheme.execute.primitive.relational.EqualProcedure;
import org.sodeja.scheme.execute.primitive.relational.LessThenProcedure;
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

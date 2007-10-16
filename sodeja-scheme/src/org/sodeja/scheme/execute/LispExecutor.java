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
import org.sodeja.scheme.execute.primitive.QuitProcedure;
import org.sodeja.scheme.execute.primitive.aritmetic.DivProcedure;
import org.sodeja.scheme.execute.primitive.aritmetic.MulProcedure;
import org.sodeja.scheme.execute.primitive.aritmetic.SubProcedure;
import org.sodeja.scheme.execute.primitive.aritmetic.SumProcedure;
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
			put("\\", new LambdaForm());
			put("def", new DefineForm());
			put("let", new LetForm());
			put("cond", new CondForm());
			put("if", new IfForm());
			put("else", Boolean.TRUE);
			
			put("+", new SumProcedure());
			put("-", new SubProcedure());
			put("*", new MulProcedure());
			put("/", new DivProcedure());
			
			put("<", new LessThenProcedure());
			put("=", new EqualProcedure());
			put(">", new BiggerThenProcedure());
			
			put("quit", new QuitProcedure());
			
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
				System.out.println("=> " + p);
				Object evalResult = frame.eval(p);
				System.out.println(evalResult);
			}});
	}
}

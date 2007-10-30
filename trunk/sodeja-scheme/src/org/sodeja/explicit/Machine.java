package org.sodeja.explicit;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sodeja.runtime.Procedure;
import org.sodeja.runtime.scheme.SchemeExpression;

public class Machine {
	
	protected Register<SchemeExpression> exp;
	protected Register<Enviroment> env;
	protected Register<Object> val;
	protected Register<String> cont;
	protected Register<Procedure> proc;
	protected Register<List<Object>> argl;
	protected Register<List<SchemeExpression>> unev;
	
	private String label;
	private Map<String, Executable> labelToExec;
	private boolean running;
	
	public Machine() {
		clear();
		initLabelToExec();
	}
	
	private void clear() {
		this.exp = new Register<SchemeExpression>();
		this.env = new Register<Enviroment>();
		this.val = new Register<Object>();
		this.cont = new Register<String>();
		this.proc = new Register<Procedure>();
		this.argl = new Register<List<Object>>();
		this.unev = new Register<List<SchemeExpression>>();
	}
	
	private void initLabelToExec() {
		labelToExec = new HashMap<String, Executable>() {
			private static final long serialVersionUID = -563256122489067937L;
		{
			put("start", new Executable() {
				@Override
				public String execute(Machine machine) {
					machine.cont.setValue("stop");
					return "eval-dispatch";
				}});
			
			put("eval-dispatch", new EvalDispatch());
			
			put("ev-self-eval", new Self());
			put("ev-variable", new Variable());
			put("ev-quoted", new Quoted());
			put("ev-lambda", new Lambda());
			
			put("ev-application", new Application());		
			put("ev-appl-did-operator", new ApplyOperator());
			put("ev-appl-operand-loop", new ApplyOperandLoop());
			put("ev-appl-accumulate-arg", new ApplyAccumulateArguments());
			put("ev-appl-last-arg", new ApplyLastArgument());
			put("ev-appl-accum-last-arg", new ApplyAccumulateLast());
			
			put("apply-dispatch", new ApplyDispatch());
			put("primitive-apply", new ApplyPrimitive());
			put("compound-apply", new ApplyCompound());
			
			put("ev-begin", new Begin());
			put("ev-sequence", new Sequence());
			put("ev-sequence-continue", new SequenceContinue());
			put("ev-sequence-end", new SequenceEnd());
			
			put("ev-if", new If());
			put("ev-if-decide", new IfDecide());
			put("ev-if-consequent", new IfConsequent());
			put("ev-if-alternative", new IfAlternative());
			
			put("ev-assignment", new Assign());
			put("ev-assignment-1", new Assign1());
			
			put("ev-definition", new Definition());
			put("ev-definition-1", new Definition1());
			
			put("stop", new Executable() {
				@Override
				public String execute(Machine machine) {
					running = false;
					return null;
				}});
		}};
	}

	public Object eval(SchemeExpression expr, Enviroment enviroment) {
		clear();
		
		this.exp.setValue(expr);
		this.env.setValue(enviroment);
		this.label = "start";
		
		running = true;
		run();
		
		return this.val.getValue();
	}
	
	private void run() {
		while(running) {
			Executable exec = labelToExec.get(label);
			if(exec == null) {
				throw new IllegalArgumentException("Unknown exec: " + label);
			}
			label = exec.execute(this);
		}
	}
}

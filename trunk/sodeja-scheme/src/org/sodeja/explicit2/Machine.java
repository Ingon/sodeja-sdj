package org.sodeja.explicit2;

import java.util.List;

import org.sodeja.explicit.Register;
import org.sodeja.runtime.Procedure;

public class Machine {
	protected Register<CompiledExpression> exp;
	protected Register<Enviroment> env;
	protected Register<Object> val;
	protected Register<CompiledExpression> cont;
	protected Register<Procedure> proc;
	protected Register<List<Object>> argl;
	protected Register<List<CompiledExpression>> unev;

	protected boolean running;
	
	public Machine() {
		clear();
	}
	
	private void clear() {
		this.exp = new Register<CompiledExpression>();
		this.env = new Register<Enviroment>();
		this.val = new Register<Object>();
		this.cont = new Register<CompiledExpression>();
		this.proc = new Register<Procedure>();
		this.argl = new Register<List<Object>>();
		this.unev = new Register<List<CompiledExpression>>();
	}
	
	public Object eval(CompiledExpression expr, Enviroment enviroment) {
		clear();
		
		this.exp.setValue(expr);
		this.env.setValue(enviroment);
		this.cont.setValue(new CompiledExpression() {
			@Override
			public void eval(Machine machine) {
				running = false;
			}});

		running = true;
		run();
		
		return this.val.getValue();
	}

	private void run() {
		while(running) {
			CompiledExpression expr = exp.getValue();
			if(expr != null) {
				expr.eval(this);
				continue;
			}
			
			expr = cont.getValue();
			expr.eval(this);
		}
	}
}

package org.sodeja.explicit2;

import java.util.List;

import org.sodeja.explicit.Register;
import org.sodeja.runtime.Procedure;

public class Machine {
	
	protected DynamicEnviroment dynamic;
	protected LexicalEnviromentFactory lexicalFactory;
	
	protected Register<CompiledExpression> exp;
	protected Register<LexicalEnviroment> env;
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
		this.env = new EnviromentRegister();
		this.val = new Register<Object>();
		this.cont = new Register<CompiledExpression>();
		this.proc = new Register<Procedure>();
		this.argl = new Register<List<Object>>();
		this.unev = new Register<List<CompiledExpression>>();
	}
	
	public Object eval(CompiledExpression expr, DynamicEnviroment dynamic) {
		clear();
		
		this.exp.setValue(expr);
		this.dynamic = dynamic;
		this.lexicalFactory = new LexicalEnviromentFactory(dynamic);

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
	
	private class EnviromentRegister extends Register<LexicalEnviroment> {
		@Override
		public void restore() {
			LexicalEnviroment env = this.getValue();
		
			super.restore();
			
			if(env != null) {
				env.removeUse();
				
				if(env.isFree()) {
					lexicalFactory.unget(env);
				}
			}
		}

		@Override
		public void save() {
			LexicalEnviroment env = this.getValue();
			
			super.save();
			
			if(env != null) {
				env.addUse();
			}
		}

		@Override
		public void setValue(LexicalEnviroment value) {
			LexicalEnviroment env = this.getValue();
			if(env != null) {
				env.removeUse();
				
				if(env.isFree()) {
					lexicalFactory.unget(env);
				}
			}
			
			super.setValue(value);
			
			if(value != null) {
				value.addUse();
			}
		}
	}
}

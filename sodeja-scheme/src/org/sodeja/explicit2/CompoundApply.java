package org.sodeja.explicit2;

import java.util.List;

import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.model.Symbol;

class CompoundApply implements CompiledExpression {
	
	public static final CompoundApply instance = new CompoundApply();
	
	private CompoundApply() {
	}
	
	@Override
	public void eval(Machine machine) {
		CompoundProcedure proc = (CompoundProcedure) machine.proc.getValue();
		
		List<Symbol> params = proc.params;
		Enviroment env = proc.enviroment;
		
		Enviroment newEnv = new Enviroment(env);
		List<Object> argVals = machine.argl.getValue();
		for(int i = 0;i < params.size();i++) {
			SchemeExpression arg = params.get(i);
			if(! (arg instanceof Symbol)) {
				throw new IllegalArgumentException("Symbol is expected as parameter");
			}
			
			newEnv.define((Symbol) arg, argVals.get(i));
		}

		machine.env.setValue(newEnv);
		machine.unev.setValue(proc.body);
		
		machine.exp.setValue(Sequence.instance);
	}
}

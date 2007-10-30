package org.sodeja.explicit2;

import java.util.List;

import org.sodeja.runtime.Procedure;
import org.sodeja.runtime.scheme.model.Symbol;

class Apply implements CompiledExpression {
	
	public static final Apply instance = new Apply();
	
	private Apply() {
	}
	
	@Override
	public void eval(Machine machine) {
		Procedure proc = machine.proc.getValue();
		
		if(proc instanceof CompoundProcedure) {
			CompoundProcedure rproc = (CompoundProcedure) proc;
			
			List<Symbol> params = rproc.params;
			Enviroment env = rproc.enviroment;
			
			Enviroment newEnv = new Enviroment(env);
			List<Object> argVals = machine.argl.getValue();
			for(int i = 0;i < params.size();i++) {
				newEnv.define(params.get(i), argVals.get(i));
			}

			machine.env.setValue(newEnv);
			machine.unev.setValue(rproc.body);
			
			machine.exp.setValue(Sequence.instance);
			return;
		}
		
		List<Object> arguments = machine.argl.getValue();
		Object[] vals = arguments.toArray(new Object[arguments.size()]);
		
		machine.val.setValue(proc.apply(vals));
		
		machine.cont.restore();
		machine.exp.setValue(machine.cont.getValue());
	}
}

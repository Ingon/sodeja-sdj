package org.sodeja.explicit2;

import java.util.List;

import org.sodeja.runtime.Procedure;

class Apply implements CompiledExpression {
	
	public static final Apply instance = new Apply();
	
	private Apply() {
	}
	
	@Override
	public void eval(Machine machine) {
		Procedure proc = machine.proc.getValue();
		
		if(proc instanceof CompoundProcedure) {
			CompoundProcedure rproc = (CompoundProcedure) proc;
			
			List<Object> argVals = machine.argl.getValue();
			
			LexicalEnviroment parent = rproc.lexical;
			LexicalEnviroment newEnv = machine.lexicalFactory.get(machine, parent, argVals);

			machine.env.setValue(newEnv);
			machine.unev.setValue(rproc.body);
			
			Sequence.instance.eval(machine);
			return;
		}
		
		List<Object> arguments = machine.argl.getValue();
		Object[] vals = arguments.toArray(new Object[arguments.size()]);
		
		machine.val.setValue(proc.apply(vals));
		
		machine.cont.restore();
		machine.exp.setValue(machine.cont.getValue());
	}
}

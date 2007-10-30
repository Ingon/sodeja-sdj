package org.sodeja.explicit2;

import java.util.List;

import org.sodeja.explicit2.CompoundProcedure;
import org.sodeja.runtime.Procedure;

class ApplyDispatch implements CompiledExpression {
	
	public static final ApplyDispatch instance = new ApplyDispatch();
	
	private ApplyDispatch() {
	}
	
	@Override
	public void eval(Machine machine) {
		Procedure proc = machine.proc.getValue();
		
		if(proc instanceof CompoundProcedure) {
			machine.exp.setValue(CompoundApply.instance);
			return;
		}
		
		List<Object> arguments = machine.argl.getValue();
		Object[] vals = arguments.toArray(new Object[arguments.size()]);
		
		machine.val.setValue(proc.apply(vals));
		
		machine.cont.restore();
		machine.exp.setValue(machine.cont.getValue());
	}
}

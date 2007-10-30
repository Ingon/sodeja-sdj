package org.sodeja.explicit;

import java.util.List;

import org.sodeja.runtime.Procedure;

class ApplyPrimitive implements Executable {
	@Override
	public String execute(Machine machine) {
		Procedure proc = machine.proc.getValue();
		List<Object> arguments = machine.argl.getValue();
		Object[] vals = arguments.toArray(new Object[arguments.size()]);
		
		machine.val.setValue(proc.apply(vals));
		
		machine.cont.restore();
		
		return machine.cont.getValue();
	}
}

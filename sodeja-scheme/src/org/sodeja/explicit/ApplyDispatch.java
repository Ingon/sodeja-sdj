package org.sodeja.explicit;

import org.sodeja.runtime.Procedure;

class ApplyDispatch implements Executable {
	@Override
	public String execute(Machine machine) {
		Procedure proc = machine.proc.getValue();
		
		if(proc instanceof CompoundProcedure) {
			return "compound-apply";
		}
		
		return "primitive-apply";
	}
}

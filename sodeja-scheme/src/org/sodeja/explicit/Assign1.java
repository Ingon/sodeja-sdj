package org.sodeja.explicit;

import org.sodeja.runtime.scheme.model.Symbol;

class Assign1 implements Executable {
	@Override
	public String execute(Machine machine) {
		machine.cont.restore();
		machine.env.restore();
		machine.unev.restore();

		Enviroment env = machine.env.getValue();
		env.set((Symbol) machine.unev.getValue().get(0), machine.val.getValue());
		
		machine.val.setValue("ok");
		
		return machine.cont.getValue();
	}
}

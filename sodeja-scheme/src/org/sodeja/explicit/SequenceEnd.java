package org.sodeja.explicit;

class SequenceEnd implements Executable {
	@Override
	public String execute(Machine machine) {
		machine.cont.restore();
		
		return machine.cont.getValue();
	}
}

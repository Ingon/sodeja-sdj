package org.sodeja.explicit;

class SequenceLast implements Executable {
	@Override
	public String execute(Machine machine) {
		machine.cont.restore();
		
		return "eval-dispatch";
//		return machine.cont.getValue();
	}
}

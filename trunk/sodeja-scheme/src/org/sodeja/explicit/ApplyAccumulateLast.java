package org.sodeja.explicit;

class ApplyAccumulateLast implements Executable {
	@Override
	public String execute(Machine machine) {
		machine.argl.restore();
		
		Object value = machine.val.getValue();
		machine.argl.getValue().add(value);

		machine.proc.restore();
		
		return "apply-dispatch";
	}
}

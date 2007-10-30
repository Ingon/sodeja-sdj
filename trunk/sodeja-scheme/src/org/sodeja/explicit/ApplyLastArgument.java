package org.sodeja.explicit;

class ApplyLastArgument implements Executable {
	@Override
	public String execute(Machine machine) {
		machine.cont.setValue("ev-appl-accum-last-arg");
		
		return "eval-dispatch";
	}
}

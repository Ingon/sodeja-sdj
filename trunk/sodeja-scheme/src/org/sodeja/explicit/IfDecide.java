package org.sodeja.explicit;

public class IfDecide implements Executable {
	@Override
	public String execute(Machine machine) {
		machine.cont.restore();
		machine.env.restore();
		machine.exp.restore();
		
		Boolean test = (Boolean) machine.val.getValue();
		if(test) {
			return "ev-if-consequent";
		}
		
		return "ev-if-alternative";
	}
}

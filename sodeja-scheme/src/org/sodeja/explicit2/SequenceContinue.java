package org.sodeja.explicit2;

import java.util.List;

class SequenceContinue implements CompiledExpression {
	
	public static final SequenceContinue instance = new SequenceContinue();
	
	private SequenceContinue() {
	}
	
	@Override
	public void eval(Machine machine) {
		machine.env.restore();
		machine.unev.restore();
		
		List<CompiledExpression> rest = machine.unev.getValue();
		machine.unev.setValue(rest.subList(1, rest.size()));
		
		machine.exp.setValue(Sequence.instance);
	}
}

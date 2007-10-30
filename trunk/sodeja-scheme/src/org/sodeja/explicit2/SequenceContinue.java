package org.sodeja.explicit2;

import org.sodeja.collections.ConsList;

class SequenceContinue implements CompiledExpression {
	
	public static final SequenceContinue instance = new SequenceContinue();
	
	private SequenceContinue() {
	}
	
	@Override
	public void eval(Machine machine) {
		machine.env.restore();
		machine.unev.restore();
		
		ConsList<CompiledExpression> rest = (ConsList<CompiledExpression>) machine.unev.getValue();
		machine.unev.setValue(rest.tail());
		
		machine.exp.setValue(Sequence.instance);
	}
}

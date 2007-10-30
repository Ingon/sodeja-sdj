package org.sodeja.explicit2;

import org.sodeja.collections.ConsList;

class Sequence implements CompiledExpression {
	
	public static final Sequence instance = new Sequence();
	
	private Sequence() {
	}
	
	@Override
	public void eval(Machine machine) {
		ConsList<CompiledExpression> seq = (ConsList<CompiledExpression>) machine.unev.getValue();
		
		machine.exp.setValue(seq.head());
		if(seq.size() == 1) {
			machine.cont.restore();
			return;
		}
		
		machine.unev.save();
		machine.env.save();
		
		machine.cont.setValue(SequenceContinue.instance);
	}
}

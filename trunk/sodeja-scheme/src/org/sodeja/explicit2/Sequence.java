package org.sodeja.explicit2;

import java.util.List;

class Sequence implements CompiledExpression {
	
	public static final Sequence instance = new Sequence();
	
	private Sequence() {
	}
	
	@Override
	public void eval(Machine machine) {
		List<CompiledExpression> seq = machine.unev.getValue();
//		if(seq.isEmpty()) {
//			machine.cont.restore();
//			machine.exp.setValue(machine.cont.getValue());
//			return;
//		}
		
		machine.exp.setValue(seq.get(0));
		if(seq.size() == 1) {
			machine.cont.restore();
			return;
		}
		
		machine.unev.save();
		machine.env.save();
		
		machine.cont.setValue(SequenceContinue.instance);
	}
}

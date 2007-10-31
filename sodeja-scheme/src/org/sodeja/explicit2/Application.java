package org.sodeja.explicit2;

import java.util.List;

import org.sodeja.collections.ConsList;

class Application implements CompiledExpression {

	private final CompiledExpression proc;
	private final ConsList<CompiledExpression> args;
	
	public Application(CompiledExpression proc, List<CompiledExpression> args) {
		this.proc = proc;
		this.args = ConsList.createList(args);
	}

	@Override
	public void eval(Machine machine) {
		machine.cont.save();
		machine.env.save();

		machine.unev.setValue(args);
		machine.unev.save();
		
		machine.exp.setValue(proc);
		
		machine.cont.setValue(ApplyOperator.instance);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(proc);
		sb.append(" ");
		sb.append(args);
		sb.append("]");
		return sb.toString();
	}
}

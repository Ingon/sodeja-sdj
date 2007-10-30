package org.sodeja.rm;

import java.util.List;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;

public class OpInstructionPart extends AbstractInstructionPart {
	
	private final String operationName;
	private final List<InstructionPart> args;
	
	public OpInstructionPart(Machine machine, String operationName, List<InstructionPart> args) {
		super(machine);
		
		this.operationName = operationName;
		this.args = args;
	}

	@Override
	public Object execute() {
		List<Object> objects = ListUtils.map(args, new Function1<Object, InstructionPart>() {
			@Override
			public Object execute(InstructionPart p) {
				return p.execute();
			}});
		
		return machine.findOperation(operationName).invoke(objects);
	}
}

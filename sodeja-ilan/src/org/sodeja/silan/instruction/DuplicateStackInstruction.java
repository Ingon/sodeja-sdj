package org.sodeja.silan.instruction;

import org.sodeja.silan.Process;
import org.sodeja.silan.SILObject;
import org.sodeja.silan.context.Context;

public class DuplicateStackInstruction implements PushInstruction {
	@Override
	public void execute(Process process) {
		Context ctx = process.getActiveContext();
		SILObject obj = ctx.pop();
		ctx.push(obj);
		ctx.push(obj);
	}
}

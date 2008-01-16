package org.sodeja.silan.context;

import org.sodeja.silan.CompiledCode;
import org.sodeja.silan.Process;
import org.sodeja.silan.SILObject;

public class ExecutableContext extends AbstractContext {
	public ExecutableContext(Process process, CompiledCode code) {
		super(process, code);
	}

	@Override
	public SILObject resolve(String reference) {
		SILObject value = super.resolve(reference);
		if(value != null) {
			return value;
		}
		return process.vm.objects.getGlobal(reference);
	}
}

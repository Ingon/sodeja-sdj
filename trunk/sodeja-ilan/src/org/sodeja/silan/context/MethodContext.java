package org.sodeja.silan.context;

import org.sodeja.silan.CompiledMethod;
import org.sodeja.silan.SILObject;

public class MethodContext extends AbstractChildContext {
	private final SILObject receiver;
	
	public MethodContext(Context parent, SILObject receiver, 
			CompiledMethod method, SILObject[] arguments) {
		super(parent, method, arguments);
		
		this.receiver = receiver;
	}

	@Override
	public SILObject resolve(String reference) {
		SILObject val = argumentValues.get(reference);
		if(val != null) {
			return val;
		}
		
		val = super.resolve(reference);
		if(val != null) {
			return val;
		}
		
		val = receiver.get(reference);
		if(val != null) {
			return val;
		}
		
		throw new UnsupportedOperationException("Not implemented above instance scope");
	}

	@Override
	public boolean update(String reference, SILObject value) {
		if(super.update(reference, value)) {
			return true;
		}
		
		receiver.set(reference, value);
		return true;
	}

	public SILObject getReceiver() {
		return receiver;
	}
}

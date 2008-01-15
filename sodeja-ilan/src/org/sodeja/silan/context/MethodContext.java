package org.sodeja.silan.context;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.silan.CompiledMethod;
import org.sodeja.silan.SILObject;

public class MethodContext extends AbstractContext implements ChildContext {
	private final Context parent;
	private final SILObject receiver;
	private final Map<String, SILObject> argumentValues;
	
	public MethodContext(Context parent, SILObject receiver, 
			CompiledMethod method, SILObject[] arguments) {
		super(parent.getProcess(), method);
		this.parent = parent;
		
		this.receiver = receiver;
		
		if(method.arguments.size() != arguments.length) {
			throw new RuntimeException("Difference arguments count");
		}
		
		this.argumentValues = new HashMap<String, SILObject>();
		for(int i = 0, n = method.arguments.size(); i < n;i++) {
			argumentValues.put(method.arguments.get(i), arguments[i]);
		}
	}

	@Override
	public Context getParent() {
		return parent;
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
		
		throw new UnsupportedOperationException("Should look in the receiver");
	}

	public SILObject getReceiver() {
		return receiver;
	}
}

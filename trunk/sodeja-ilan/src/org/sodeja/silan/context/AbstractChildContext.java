package org.sodeja.silan.context;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.silan.CallableCompiledCode;
import org.sodeja.silan.SILObject;

public abstract class AbstractChildContext extends AbstractContext implements ChildContext {
	private final Context parent;
	
	protected final Map<String, SILObject> argumentValues;

	public AbstractChildContext(Context parent, CallableCompiledCode code, 
			SILObject[] arguments) {
		super(parent.getProcess(), code);
		
		this.parent = parent;
		
		if(code.arguments.size() != arguments.length) {
			throw new RuntimeException("Difference arguments count");
		}
		
		this.argumentValues = new HashMap<String, SILObject>();
		for(int i = 0, n = code.arguments.size(); i < n;i++) {
			argumentValues.put(code.arguments.get(i), arguments[i]);
		}
	}
	
	@Override
	public Context getParent() {
		return parent;
	}

}

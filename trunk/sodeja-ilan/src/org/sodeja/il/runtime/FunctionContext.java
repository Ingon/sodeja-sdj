package org.sodeja.il.runtime;

import java.util.List;

public class FunctionContext extends AbstractContext {

	private final Context parent;
	
	public FunctionContext(Context parent, List<ILSymbol> arguments, List<ILObject> values) {
		this.parent = parent;
		
		if(arguments.size() != values.size()) {
			throw new RuntimeException("Wrong number of arguments");
		}
		
		for(int i = 0, n = arguments.size();i < n;i++) {
			this.values.put(arguments.get(i), values.get(i));
		}
	}

	@Override
	public void define(ILSymbol name, ILObject value) {
		if(name.value.startsWith("@")) {
			parent.define(name, value);
		}
		super.define(name, value);
	}

	@Override
	public ILObject get(ILSymbol name) {
		ILObject object = super.get(name);
		if(object != null) {
			return object;
		}
		return parent.get(name);
	}

	@Override
	public boolean exists(ILSymbol symbol) {
		if(super.exists(symbol)) {
			return true;
		}
		return parent.exists(symbol);
	}
}

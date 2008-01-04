package org.sodeja.il.runtime;

import java.util.List;

import org.sodeja.il.sdk.ILClass;
import org.sodeja.il.sdk.ILFreeLambda;
import org.sodeja.il.sdk.ILObject;
import org.sodeja.il.sdk.ILSymbol;

public class RootContext extends AbstractContext {
	
	public RootContext() {
		define(new ILSymbol("print"), new ILFreeLambda() {
			@Override
			public ILObject apply(List<ILObject> values) {
				for(ILObject val : values) {
					System.out.println(val);
				}
				return null;
			}

			@Override
			public ILClass getType() {
				throw new UnsupportedOperationException();
			}});
	}
	
	@Override
	public void define(ILSymbol name, ILObject value) {
		ILObject tvalue = super.get(name);
		if(tvalue != null) {
			throw new RuntimeException("Redefine is not possible");
		}
		super.define(name, value);
	}

	@Override
	public ILObject get(ILSymbol name) {
		ILObject temp = super.get(name);
		if(temp == null) {
			throw new RuntimeException("Not defined: " + name);
		}
		return temp;
	}
}

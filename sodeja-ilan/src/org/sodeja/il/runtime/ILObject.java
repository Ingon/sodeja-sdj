package org.sodeja.il.runtime;

import java.util.HashMap;
import java.util.Map;

public class ILObject {
	protected final ILClass type;
	protected final Map<String, ILObject> values;
	
	public ILObject(ILClass type) {
		this.type = type;
		this.values = new HashMap<String, ILObject>();
	}
}

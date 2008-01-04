package org.sodeja.il.sdk;

import java.util.List;

import org.sodeja.lang.reflect.ReflectUtils;

public class ILJavaClass extends ILClass {
	
	private final Class clazz;
	
	public ILJavaClass(ILSymbol name, ILClass parent, Class clazz) {
		super(name, parent);
		this.clazz = clazz;
	}

	@Override
	public ILObject makeInstance(Object... values) {
		return new ILJavaObject(this, values[0]);
	}

	@Override
	public ILObject newInstance(List<ILObject> values) {
		if(values.size() != 0) {
			throw new UnsupportedOperationException();
		}
		return new ILJavaObject(this, ReflectUtils.newInstance(clazz));
	}
}

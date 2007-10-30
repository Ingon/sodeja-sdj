package org.sodeja.explicit;

import org.sodeja.collections.ConsList;

public class Register<T> {
	private ConsList<T> vals;
	
	public Register() {
		vals = new ConsList<T>(null);
	}
	
	public T getValue() {
		return vals.head();
	}
	
	public void setValue(T value) {
		vals.setHead(value);
	}
	
	public void save() {
		vals = new ConsList<T>(null, vals);
	}
	
	public void restore() {
		vals = vals.tail();
	}
}

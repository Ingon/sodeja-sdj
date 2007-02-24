package org.sodeja.sdj.util;

public interface Heap<T> {
	public Address alloc(T obj);
	public void update(Address addr, T obj);
	public void free(Address addr);
	
	public Object lookup(Address addr);
	
	public Address[] addresses();
	public int size();
	
	public Address nulled();
	public boolean isNulled(Address addr);
}

package org.sodeja.sdj.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.sodeja.collections.ListUtils;

public class HeapImpl<T> implements Heap<T> {

	private static final Address NULL_ADDRESS = new LongAddress();
	
	public Map<Address, T> values;
	
	public Address alloc(T obj) {
		Address addr = new LongAddress();
		values.put(addr, obj);
		return addr;
	}

	public void update(Address addr, T obj) {
		values.put(addr, obj);
	}

	public void free(Address addr) {
		values.remove(addr);
	}

	public T lookup(Address addr) {
		return values.get(addr);
	}

	public Address[] addresses() {
		List<Address> addrs = new ArrayList<Address>(values.keySet());
		return ListUtils.asArray(addrs);
	}

	public int size() {
		return values.size();
	}

	public Address nulled() {
		return NULL_ADDRESS;
	}

	public boolean isNulled(Address addr) {
		return addr == NULL_ADDRESS;
	}

	private static long maxAddress = 0;
	private static class LongAddress implements Address {
		public final long value;

		public LongAddress() {
			value = maxAddress++;
		}

		@Override
		public int hashCode() {
			final int PRIME = 31;
			int result = 1;
			result = PRIME * result + (int) (value ^ (value >>> 32));
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			final LongAddress other = (LongAddress) obj;
			if (value != other.value)
				return false;
			return true;
		}
	}
}

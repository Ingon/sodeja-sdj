package org.sodeja.runtime.abs;

import java.util.ArrayList;
import java.util.List;

import org.sodeja.runtime.Expression;
import org.sodeja.runtime.Frame;
import org.sodeja.runtime.Library;

public class CompoundLibrary<E extends Expression> implements Library<E> {
	
	private final List<Library<E>> libraries;
	
	public CompoundLibrary() {
		this.libraries = new ArrayList<Library<E>>();		
	}

	public void addLibrary(Library<E> lib) {
		libraries.add(lib);
	}
	
	@Override
	public void extend(Frame<E> frame) {
		for(Library<E> lib : libraries) {
			lib.extend(frame);
		}
	}
}

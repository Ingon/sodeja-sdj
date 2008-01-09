package org.sodeja.sil.runtime.context;

import java.util.List;
import java.util.Map;

import org.sodeja.sil.runtime.Reference;
import org.sodeja.sil.runtime.method.CompiledMethod;

public class MethodContext implements Context {
	private Reference sender;
	private CompiledMethod method;
	private Integer instructionPointer;
	private Reference receiver;
	private List<Reference> arguments;
	private Map<String, Reference> registers;
}

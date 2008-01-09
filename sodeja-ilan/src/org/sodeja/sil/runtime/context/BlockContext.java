package org.sodeja.sil.runtime.context;

import org.sodeja.sil.runtime.Reference;

public class BlockContext implements Context {
	private Reference caller;
	private Integer instructionPointer;
	private Integer argumentCount;
	private Integer initialInstructionPointer;
	private Reference home;
}

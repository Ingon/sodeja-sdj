package org.sodeja.sdj.ti;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sodeja.sdj.CoreSupercombinators;
import org.sodeja.sdj.expression.Name;
import org.sodeja.sdj.expression.Program;
import org.sodeja.sdj.expression.Supercombinator;
import org.sodeja.sdj.util.Address;
import org.sodeja.sdj.util.Heap;
import org.sodeja.sdj.util.HeapImpl;

public class TiState {
	
	private static final Name MAIN_NAME = new Name("main");
	
	private List<Address> tiStack;
	private Object tiDump = null;
	private Heap<Node> tiHeap;
	private Map<Name, Address> tiGlobals; // TODO may be better then assoc list
	
	public TiState(Program<Name> program) {
		initPrelude(program);
		buildHeapAndGlobals(program);
		initStack();
	}
	
	public TiState(TiState oldState) {
		tiStack = new ArrayList<Address>(oldState.tiStack);
		tiHeap = new HeapImpl<Node>(oldState.tiHeap);
		tiGlobals = new HashMap<Name, Address>(oldState.tiGlobals);
	}

	private void initPrelude(Program<Name> program) {
		program.definitions.add(CoreSupercombinators.I);
		program.definitions.add(CoreSupercombinators.K);
		program.definitions.add(CoreSupercombinators.K1);
		program.definitions.add(CoreSupercombinators.S);
		program.definitions.add(CoreSupercombinators.COMPOSE);
		program.definitions.add(CoreSupercombinators.TWICE);
	}
	
	private void buildHeapAndGlobals(Program<Name> program) {
		tiHeap = new HeapImpl<Node>();
		tiGlobals = new HashMap<Name, Address>();
		
		for(Supercombinator<Name> combinator : program.definitions) {
			SupercombinatorNode<Name> node = new SupercombinatorNode<Name>(combinator);
			Address addr = tiHeap.alloc(node);
			tiGlobals.put(node.name, addr);
		}
	}
	
	private void initStack() {
		tiStack = new ArrayList<Address>();
		Address addr = tiGlobals.get(MAIN_NAME);
		if(addr == null) {
			throw new RuntimeException("Main is missing");
		}
		tiStack.add(addr);
	}
	
	public Heap<Node> getHeap() {
		return tiHeap;
	}
	
	public List<Address> getStack() {
		return tiStack;
	}
}

package org.sodeja.explicit2;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;

class LexicalEnviromentFactory {
	
	private final LexicalEnviroment.NullEnviroment nullEnviroment;
	private final Deque<LexicalEnviroment> enviroments;
	
	protected LexicalEnviromentFactory(DynamicEnviroment env) {
		nullEnviroment = new LexicalEnviroment.NullEnviroment(env);
		enviroments = new LinkedList<LexicalEnviroment>();
	}
	
	public LexicalEnviroment get(Machine machine, LexicalEnviroment parent, List<Object> argVals) {
		if(enviroments.isEmpty()) {
			return create(machine, parent, argVals);
		}
		
		LexicalEnviroment env = enviroments.pollFirst();
		init(machine, parent, argVals, env);
		return env;
	}

	public void unget(LexicalEnviroment env) {
		if(env == null) {
			return;
		}
		env.clear();
		enviroments.addLast(env);
	}
	
	private LexicalEnviroment create(Machine machine, LexicalEnviroment parent, List<Object> argVals) {
		if(parent != null) {
			return new LexicalEnviroment(parent, argVals);
		} else {
			return new LexicalEnviroment(nullEnviroment, argVals);
		}
	}
	
	private void init(Machine machine, LexicalEnviroment parent, List<Object> argVals, LexicalEnviroment env) {
		if(parent != null) {
			env.init(parent, argVals);
		} else {
			env.init(nullEnviroment, argVals);
		}
	}
}

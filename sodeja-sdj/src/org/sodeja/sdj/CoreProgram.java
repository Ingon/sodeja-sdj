package org.sodeja.sdj;

import java.util.List;

import org.sodeja.sdj.expression.Name;
import org.sodeja.sdj.expression.Program;
import org.sodeja.sdj.expression.Supercombinator;

public class CoreProgram extends Program<Name> {
	
	public CoreProgram(List<Supercombinator<Name>> definitions) {
		super(definitions);
		initPrelude();
	}

	private void initPrelude() {
		definitions.add(CoreSupercombinators.I);
		definitions.add(CoreSupercombinators.K);
		definitions.add(CoreSupercombinators.K1);
		definitions.add(CoreSupercombinators.S);
		definitions.add(CoreSupercombinators.COMPOSE);
		definitions.add(CoreSupercombinators.TWICE);
	}
}

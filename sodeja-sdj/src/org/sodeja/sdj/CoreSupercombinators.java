package org.sodeja.sdj;

import org.sodeja.collections.ListUtils;
import org.sodeja.sdj.expression.Application;
import org.sodeja.sdj.expression.Name;
import org.sodeja.sdj.expression.Supercombinator;
import org.sodeja.sdj.expression.Variable;

public class CoreSupercombinators {
	public static final Supercombinator<Name> I = new Supercombinator<Name>(
			new Name("I"), 
			ListUtils.asList(new Name("x")), 
			new Variable<Name>(new Name("x"))
	);
	
	public static final Supercombinator<Name> K = new Supercombinator<Name>(
			new Name("K"), 
			ListUtils.asList(new Name("x"), new Name("y")), 
			new Variable<Name>(new Name("x"))
	);
	
	public static final Supercombinator<Name> K1 = new Supercombinator<Name>(
			new Name("K1"), 
			ListUtils.asList(new Name("x"), new Name("y")), 
			new Variable<Name>(new Name("y"))
	);
	
	public static final Supercombinator<Name> S = new Supercombinator<Name>(
			new Name("S"),
			ListUtils.asList(new Name("f"), new Name("g"), new Name("x")), 
			new Application<Name>(
					new Application<Name>(new Variable<Name>(new Name("f")), new Variable<Name>(new Name("x"))),
					new Application<Name>(new Variable<Name>(new Name("g")), new Variable<Name>(new Name("x")))
			)
	);
	
	public static final Supercombinator<Name> COMPOSE = new Supercombinator<Name>(
			new Name("compose"),
			ListUtils.asList(new Name("f"), new Name("g"), new Name("x")), 
			new Application<Name>(
					new Variable<Name>(new Name("f")),
					new Application<Name>(new Variable<Name>(new Name("g")), new Variable<Name>(new Name("x")))
			)
	);
	
	public static final Supercombinator<Name> TWICE = new Supercombinator<Name>(
			new Name("twice"), 
			ListUtils.asList(new Name("f")), 
			new Application<Name>(
					new Application<Name>(new Variable<Name>(new Name("compose")), new Variable<Name>(new Name("f"))),
					new Variable<Name>(new Name("f"))
			)
	);
}

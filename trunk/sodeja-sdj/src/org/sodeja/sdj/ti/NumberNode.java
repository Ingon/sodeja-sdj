package org.sodeja.sdj.ti;

import org.sodeja.sdj.expression.Name;
import org.sodeja.sdj.expression.Number;

public class NumberNode implements Node {

	public final Integer value; 
	
	public NumberNode(Number<Name> number) {
		this.value = number.value;
	}

}

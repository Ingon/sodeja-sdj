package org.sodeja.il.model;

import org.sodeja.il.runtime.ClassContext;
import org.sodeja.il.runtime.Context;
import org.sodeja.il.runtime.SDK;
import org.sodeja.il.sdk.ILClass;
import org.sodeja.il.sdk.ILObject;

public class ClassExpression implements Expression {
	public final VariableExpression name;
	public final BlockExpression block;
	
	public ClassExpression(VariableExpression name, BlockExpression block) {
		this.name = name;
		this.block = block;
	}

	@Override
	public ILObject eval(Context ctx) {
		ILClass clazz = null;
		if(ctx.exists(name.name)) {
			clazz = (ILClass) ctx.get(name.name);
		} else {
			clazz = new ILClass(name.name, SDK.getInstance().getRootType());
			ctx.define(name.name, clazz);
		}

		Context newContext = new ClassContext(ctx, clazz);
		block.eval(newContext);
		
		return clazz;
	}
}

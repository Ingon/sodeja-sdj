package org.sodeja.il.model;

import org.sodeja.il.runtime.Context;
import org.sodeja.il.runtime.SDK;
import org.sodeja.il.sdk.ILObject;
import org.sodeja.il.sdk.ILSymbol;

public class ImportExpression implements Expression {
	public final VariableExpression name;
	
	public ImportExpression(VariableExpression name) {
		this.name = name;
		
		SDK.getInstance().registerJavaClass(name.name.getValue());
	}

	@Override
	public ILObject eval(Context ctx) {
		String val = name.name.getValue();
		String rname = val.substring(val.lastIndexOf(".") + 1);
		
		ILSymbol importName = SDK.getInstance().makeSymbol(rname);
		ctx.define(importName, SDK.getInstance().getJavaClass(val));
		return importName;
	}
}

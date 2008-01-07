package org.sodeja.il.model;

import org.sodeja.il.runtime.Context;
import org.sodeja.il.runtime.SDK;
import org.sodeja.il.sdk.ILClass;
import org.sodeja.il.sdk.ILObject;
import org.sodeja.il.sdk.ILSymbol;

public class ImportExpression implements Expression {
	public final VariableExpression name;
	public final ILClass type;
	
	public ImportExpression(VariableExpression name) {
		this.name = name;
		
		type = SDK.getInstance().registerJavaClass(name.name.getValue());
	}

	@Override
	public ILObject eval(Context ctx) {
		String val = name.name.getValue();
		String rname = val.substring(val.lastIndexOf(".") + 1);
		
		ILSymbol importName = new ILSymbol(rname);
		ctx.define(importName, type);
		return importName;
	}

	@Override
	public String toString() {
		return "import " + name;
	}
}

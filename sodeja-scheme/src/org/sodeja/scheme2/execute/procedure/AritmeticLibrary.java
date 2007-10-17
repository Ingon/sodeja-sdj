package org.sodeja.scheme2.execute.procedure;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.scheme2.execute.Library;
import org.sodeja.scheme2.execute.procedure.aritmetic.DivProcedure;
import org.sodeja.scheme2.execute.procedure.aritmetic.MulProcedure;
import org.sodeja.scheme2.execute.procedure.aritmetic.SubProcedure;
import org.sodeja.scheme2.execute.procedure.aritmetic.SumProcedure;

public class AritmeticLibrary implements Library {
	@Override
	public Map<String, Object> contents() {
		return new HashMap<String, Object>() {
			private static final long serialVersionUID = 4309646054015722531L;
		{
			put("+", new SumProcedure());
			put("-", new SubProcedure());
			put("*", new MulProcedure());
			put("/", new DivProcedure());
		}};
	}
}

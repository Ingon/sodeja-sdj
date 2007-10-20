package org.sodeja.scheme2.execute.procedure;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.runtime.procedure.arithmetic.DivProcedure;
import org.sodeja.runtime.procedure.arithmetic.MulProcedure;
import org.sodeja.runtime.procedure.arithmetic.SubProcedure;
import org.sodeja.runtime.procedure.arithmetic.SumProcedure;
import org.sodeja.scheme2.execute.Library;

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

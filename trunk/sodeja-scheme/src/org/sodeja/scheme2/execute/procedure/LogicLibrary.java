package org.sodeja.scheme2.execute.procedure;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.scheme2.execute.Library;
import org.sodeja.scheme2.execute.procedure.logical.NotProcedure;

public class LogicLibrary implements Library {
	@Override
	public Map<String, Object> contents() {
		return new HashMap<String, Object>() {
			private static final long serialVersionUID = -5151161291606232048L;
		{
			put("not", new NotProcedure());
		}};
	}
}

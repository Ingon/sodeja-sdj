package org.sodeja.scheme2.execute.procedure;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.runtime.procedure.relational.BiggerThenProcedure;
import org.sodeja.runtime.procedure.relational.EqualProcedure;
import org.sodeja.runtime.procedure.relational.LessThenProcedure;
import org.sodeja.scheme2.execute.Library;

public class RelationalLibrary implements Library {
	@Override
	public Map<String, Object> contents() {
		return new HashMap<String, Object>() {
			private static final long serialVersionUID = 4309646054015722531L;
		{
			put("<", new LessThenProcedure());
			put("=", new EqualProcedure());
			put(">", new BiggerThenProcedure());
		}};
	}
}

package org.sodeja.scheme2.execute.procedure;

import java.util.HashMap;
import java.util.Map;

import org.sodeja.scheme2.execute.Library;
import org.sodeja.scheme2.execute.procedure.relational.BiggerThenProcedure;
import org.sodeja.scheme2.execute.procedure.relational.EqualProcedure;
import org.sodeja.scheme2.execute.procedure.relational.LessThenProcedure;

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

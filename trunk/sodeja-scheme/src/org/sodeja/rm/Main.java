package org.sodeja.rm;

import java.io.FileReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sodeja.collections.ListUtils;

public class Main {
	public static void main(String[] args) throws Exception {
		Map<String, Operation> ops = new HashMap<String, Operation>();
		ops.put("user-print", new Operation() {
			@Override
			public Object invoke(List<Object> objects) {
				System.out.println(objects.get(0));
				return null;
			}});
		
		Machine machine = new Machine(ListUtils.asList("exp", "env", "val", "proc", "argl", "continue", "unev"), 
				ops, new FileReader("scripts/explicit.sch"));
		machine.start();
//		String text = "test-b\r\n" +
//				"(test (op =) (reg b) (const 0))\r\n" +
//				"(branch (label gcd-done))\r\n" +
//				"(assign t (op rem) (reg a) (reg b))\r\n" +
//				"(assign a (reg b))\r\n" +
//				"(assign b (reg t))\r\n" +
//				"(goto (label test-b))\r\n" +
//				"gcd-done";
//
//		Map<String, Operation> ops = new HashMap<String, Operation>();
//		ops.put("=", new Operation() {
//			@Override
//			public Object invoke(List<Object> objects) {
//				Integer i0 = (Integer) objects.get(0);
//				Integer i1 = (Integer) objects.get(1);
//				return i0.equals(i1);
//			}});
//		ops.put("rem", new Operation() {
//			@Override
//			public Object invoke(List<Object> objects) {
//				Integer i0 = (Integer) objects.get(0);
//				Integer i1 = (Integer) objects.get(1);
//				return i0 % i1;
//			}});
//		
//		Machine machine = new Machine(ListUtils.asList("a", "b", "t"), ops, text);
//		
//		machine.setRegisterValue("a", 206);
//		machine.setRegisterValue("b", 40);
//		
//		machine.start();
//		
//		System.out.println("Value: " + machine.getRegisterValue("a"));
	}
}

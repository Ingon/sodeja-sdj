package org.sodeja.rm;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Triple;
import org.sodeja.parser.SchemeParser;
import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.model.Combination;
import org.sodeja.runtime.scheme.model.Symbol;

public class Main {
	public static void main(String[] args) throws Exception {
		SchemeParser<Symbol, Combination> parser = new SchemeParser<Symbol, Combination>(Symbol.class, Combination.class);
		Combination comb = parser.tokenize(new StringReader("(define (factorial n) " +
				"(if (= n 1) " +
				"1 " +
				"(* (factorial (- n 1)) n)))"));
		
		Map<String, Operation> ops = new HashMap<String, Operation>();
		
		final Machine machine = new Machine(ListUtils.asList("exp", "env", "val", "proc", "argl", "continue", "unev"), 
				ops, new FileReader("scripts/explicit.sch"));

		fillReadEvalPrintLoop(ops, machine);
		fillEvalDispatch(ops, machine);
		fillApplyDispatch(ops, machine);
		fillEvLambda(ops, machine);
		fillEvDefinition(ops, machine);
		fillPrintResult(ops, machine);
		
		machine.setRegisterValue("exp", comb.get(0));		
		
		machine.start();
	}
	
	private static void fillReadEvalPrintLoop(Map<String, Operation> ops, final Machine machine) {
		ops.put("prompt-for-input", new Operation() {
			@Override
			public Object invoke(List<Object> objects) {
				System.out.println(objects.get(0));
				return null;
			}});
		ops.put("initialize-stack", new Operation() {
			@Override
			public Object invoke(List<Object> objects) {
				machine.getStack().clear();
				return null;
			}});
		ops.put("read", new Operation() {
			SchemeParser<Symbol, Combination> parser = new SchemeParser<Symbol, Combination>(Symbol.class, Combination.class);
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));			
			@Override
			public Object invoke(List<Object> objects) {
				try {
					return parser.tokenize(new StringReader(br.readLine())).get(0);
				} catch (IOException e) {
					throw new RuntimeException(e);
				}
			}
		});
		
		final Map<Symbol, Object> env = new HashMap<Symbol, Object>();
		ops.put("get-global-environment", new Operation() {
			@Override
			public Object invoke(List<Object> objects) {
				return env;
			}});
		ops.put("define-variable!", new Operation() {
			@Override
			public Object invoke(List<Object> objects) {
				Symbol name = (Symbol) objects.get(0);
				Object value = objects.get(1);
				Map<Symbol, Object> env = (Map<Symbol, Object>) objects.get(2);
				env.put(name, value);
				return name;
			}});
		ops.put("lookup-variable-value", new Operation() {
			@Override
			public Object invoke(List<Object> objects) {
				Symbol name = (Symbol) objects.get(0);
				Map<Symbol, Object> env = (Map<Symbol, Object>) objects.get(1);
				Object value = env.get(name);
				if(value == null) {
					throw new IllegalArgumentException();
				}
				return value;
			}});
	}

	private static void fillEvalDispatch(Map<String, Operation> ops, final Machine machine) {
		ops.put("self-evaluating?", new Operation() {
			@Override
			public Object invoke(List<Object> objects) {
				SchemeExpression expr = (SchemeExpression) objects.get(0);
				if(! (expr instanceof Symbol)) {
					return Boolean.FALSE;
				}
				
				Symbol sym = (Symbol) expr;
				try {
					new Integer(sym.value);
					return Boolean.TRUE;
				} catch(NumberFormatException exc) {
					return Boolean.FALSE;
				}
			}});
		ops.put("variable?", new Operation() {
			@Override
			public Object invoke(List<Object> objects) {
				SchemeExpression expr = (SchemeExpression) objects.get(0);
				return expr instanceof Symbol;
			}});
		ops.put("quoted?", new FormOperationCheck("quote"));
		ops.put("assignment?", new FormOperationCheck("set!"));
		ops.put("definition?", new FormOperationCheck("define"));
		ops.put("if?", new FormOperationCheck("if"));
		ops.put("lambda?", new FormOperationCheck("lambda"));
		ops.put("begin?", new FormOperationCheck("begin"));
		ops.put("application?", new Operation() {
			@Override
			public Object invoke(List<Object> objects) {
				return objects.get(0) instanceof Combination;
			}});
	}
	
	private static class FormOperationCheck implements Operation {
		
		private final String expected;
		
		public FormOperationCheck(String expected) {
			this.expected = expected;
		}

		@Override
		public Object invoke(List<Object> objects) {
			Combination comb = (Combination) objects.get(0);
			SchemeExpression fst = comb.get(0);
			if(fst instanceof Combination) {
				return Boolean.FALSE;
			}
			
			Symbol sym = (Symbol) fst;
			return sym.value.equals(expected);
		}
	}
	
	private static void fillEvLambda(Map<String, Operation> ops, final Machine machine) {
		ops.put("lambda-parameters", new Operation() {
			@Override
			public Object invoke(List<Object> objects) {
				Combination comb = (Combination) objects.get(0);
				return comb.get(1);
			}});
		ops.put("lambda-body", new Operation() {
			@Override
			public Object invoke(List<Object> objects) {
				Combination comb = (Combination) objects.get(0);
				return comb.get(2);
			}});
		ops.put("make-procedure", new Operation() {
			@Override
			public Object invoke(List<Object> objects) {
				return Triple.of(objects.get(0), objects.get(1), objects.get(2));
			}});
		ops.put("operands", new Operation() {
			@Override
			public Object invoke(List<Object> objects) {
				Combination comb = (Combination) objects.get(0);
				return comb.subList(1, comb.size());
			}});
		ops.put("operator", new Operation() {
			@Override
			public Object invoke(List<Object> objects) {
				Combination comb = (Combination) objects.get(0);
				return comb.get(0);
			}});
		ops.put("empty-arglist", new Operation() {
			@Override
			public Object invoke(List<Object> objects) {
				return new ArrayList<Object>();
			}});
		ops.put("no-operands?", new Operation() {
			@Override
			public Object invoke(List<Object> objects) {
				return ((List) objects.get(0)).isEmpty();
			}});
		ops.put("first-operand", new Operation() {
			@Override
			public Object invoke(List<Object> objects) {
				List<SchemeExpression> exprs = (List<SchemeExpression>) objects.get(0);
				return exprs.get(0);
			}});
		ops.put("last-operand?", new Operation() {
			@Override
			public Object invoke(List<Object> objects) {
				List<SchemeExpression> exprs = (List<SchemeExpression>) objects.get(0);
				return exprs.size() == 1;
			}});
		ops.put("adjoin-arg", new Operation() {
			@Override
			public Object invoke(List<Object> objects) {
				Object val = objects.get(0);
				List args = (List) objects.get(1);
				args.add(val);
				return args;
			}});
	}
	
	private static void fillApplyDispatch(Map<String, Operation> ops, final Machine machine) {
		ops.put("primitive-procedure?", new Operation() {
			@Override
			public Object invoke(List<Object> objects) {
				// TODO revise this one
				return Boolean.FALSE;
			}});
		ops.put("compound-procedure?", new Operation() {
			@Override
			public Object invoke(List<Object> objects) {
				return objects.get(0) instanceof Triple;
			}});
		ops.put("procedure-parameters", new Operation() {
			@Override
			public Object invoke(List<Object> objects) {
				return ((Triple) objects.get(0)).first;
			}});
		ops.put("procedure-environment", new Operation() {
			@Override
			public Object invoke(List<Object> objects) {
				return ((Triple) objects.get(0)).third;
			}});
		ops.put("extend-environment", new Operation() {
			@Override
			public Object invoke(List<Object> objects) {
				Map<Symbol, Object> env = (Map<Symbol, Object>) objects.get(2);
				Map<Symbol, Object> nenv = new HashMap<Symbol, Object>(env);
				
				List<Symbol> names = (List<Symbol>) objects.get(0);
				List<Object> values = (List<Object>) objects.get(1);
				
				for(int i = 0;i < names.size();i++) {
					nenv.put(names.get(i), values.get(i));
				}
				
				return nenv;
			}});
		ops.put("procedure-body", new Operation() {
			@Override
			public Object invoke(List<Object> objects) {
				return ((Triple) objects.get(0)).second;
			}});
	}
	
	private static void fillEvDefinition(Map<String, Operation> ops, final Machine machine) {
		ops.put("definition-variable", new Operation() {
			@Override
			public Object invoke(List<Object> objects) {
				SchemeExpression expr = ((Combination) objects.get(0)).get(1);
				if(expr instanceof Symbol) {
					return expr;
				}
				return ((Combination) expr).get(0);
			}});

		ops.put("definition-value", new Operation() {
			@Override
			public Object invoke(List<Object> objects) {
				SchemeExpression expr = ((Combination) objects.get(0)).get(1);
				if(expr instanceof Symbol) {
					return ((Combination) objects.get(0)).get(2);
				}
				
				Combination nameAndLambdaParams = (Combination) expr;
				Combination lambdaParams = new Combination();
				lambdaParams.addAll(nameAndLambdaParams.subList(1, nameAndLambdaParams.size()));
				
				Combination lambda = new Combination();
				lambda.add(new Symbol("lambda"));
				lambda.add(lambdaParams);
				lambda.addAll((Combination) ((Combination) objects.get(0)).get(2));
				return lambda;
			}});
	}
	
	private static void fillPrintResult(Map<String, Operation> ops, final Machine machine) {
		ops.put("announce-output", ops.get("prompt-for-input"));
		ops.put("user-print", ops.get("prompt-for-input"));
	}
	// (define (factorial n) (if (= n 1) 1 (* (factorial (- n 1)) n)))
}

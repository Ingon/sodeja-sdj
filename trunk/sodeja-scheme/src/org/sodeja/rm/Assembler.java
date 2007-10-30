package org.sodeja.rm;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.sodeja.collections.ListUtils;
import org.sodeja.functional.Function1;
import org.sodeja.math.Rational;
import org.sodeja.parser.SchemeParser;
import org.sodeja.runtime.scheme.SchemeExpression;
import org.sodeja.runtime.scheme.model.Combination;
import org.sodeja.runtime.scheme.model.Symbol;

public class Assembler {

	private Machine machine;
	private Combination tokens;
	
	private Map<Symbol, Integer> labelPositions;
	private List<Instruction> instructions;
	
	public Assembler(Machine machine, String text) throws Exception {
		this.machine = machine;
		
		SchemeParser<Symbol, Combination> schemeParser = new SchemeParser<Symbol, Combination>(Symbol.class, Combination.class);
		tokens = schemeParser.tokenize(new StringReader(text));
		
		this.labelPositions = new HashMap<Symbol, Integer>();
		this.instructions = new ArrayList<Instruction>();
	}

	public Assembler(Machine machine, Reader reader) throws Exception {
		this.machine = machine;
		
		SchemeParser<Symbol, Combination> schemeParser = new SchemeParser<Symbol, Combination>(Symbol.class, Combination.class);
		tokens = schemeParser.tokenize(reader);
		
		this.labelPositions = new HashMap<Symbol, Integer>();
		this.instructions = new ArrayList<Instruction>();
	}

	public List<Instruction> instructions() {
		if(instructions.size() == 0) {
			makeInstructions();
		}
		return instructions;
	}
	
	private void makeInstructions() {
		for(int i = 0;i < tokens.size();i++) {
			SchemeExpression expr = tokens.get(i);
			if(expr instanceof Symbol) {
				Symbol sym = (Symbol) expr;
				instructions.add(new LabelInstruction(machine, sym.value));
				labelPositions.put(sym, i);
			} else {
				instructions.add(new PreassembledInstruction((Combination) expr));
			}
		}
		
		for(int i = 0;i < instructions.size();i++) {
			Instruction instr = instructions.get(i);
			if(instr instanceof PreassembledInstruction) {
				instructions.set(i, makeInstruction(((PreassembledInstruction) instr).expr));
			}
		}
	}

	private Instruction makeInstruction(Combination comb) {
		Symbol instr = (Symbol) comb.get(0);
		String value = instr.value;
		if(value.equals("assign")) {
			Symbol registerSym = (Symbol) comb.get(1);
			return new AssignInstruction(machine, registerSym.value, makeInstructionPart(comb, 2));
		} else if(value.equals("perform")) {
			return new PerformInstruction(machine, makeInstructionPart(comb, 1));
		} else if(value.equals("test")) {
			return new TestInstruction(machine, makeInstructionPart(comb, 1));
		} else if(value.equals("branch")) {
			return new BranchInstruction(machine, makeInstructionPart(comb, 1));
		} else if(value.equals("goto")) {
			return new GotoInstruction(machine, makeInstructionPart(comb, 1));
		} else if(value.equals("save")) {
			Symbol registerSym = (Symbol) comb.get(1);
			return new SaveInstruction(machine, registerSym.value);
		} else if(value.equals("restore")) {
			Symbol registerSym = (Symbol) comb.get(1);
			return new RestoreInstruction(machine, registerSym.value);
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	private InstructionPart makeInstructionPart(Combination comb, int from) {
		Combination targetComb = (Combination) comb.get(from);
		List<SchemeExpression> targetParts = comb.subList(from + 1, comb.size());
		return makeInstructionPart(targetComb, targetParts);
	}
	
	private InstructionPart makeInstructionPart(Combination comb, List<SchemeExpression> parts) {
		Symbol instr = (Symbol) comb.get(0);
		String value = instr.value;
		if(value.equals("reg")) {
			Symbol regSym = (Symbol) comb.get(1);
			return new RegInstructionPart(machine, regSym.value);
		} else if(value.equals("const")) {
			Symbol valSym = (Symbol) comb.get(1);
			return new ConstInstructionPart(machine, valSym.value);
		} else if(value.equals("op")) {
			final Symbol op = (Symbol) comb.get(1);
			final List<InstructionPart> instructions = ListUtils.map(parts, new Function1<InstructionPart, SchemeExpression>() {
				@Override
				public InstructionPart execute(SchemeExpression p) {
					return makeInstructionPart((Combination) p, null);
				}});
			return new OpInstructionPart(machine, op.value, instructions);
		} else if(value.equals("label")) {
			final Symbol label = (Symbol) comb.get(1);
			return new LabelInstructionPart(machine, label.value, labelPositions.get(label));
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	private void advancePc() {
		Integer val = (Integer) machine.getRegisterValue("pc");
		machine.setRegisterValue("pc", val + 1);
	}
}

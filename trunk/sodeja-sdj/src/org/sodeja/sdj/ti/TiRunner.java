package org.sodeja.sdj.ti;

import org.sodeja.sdj.expression.Application;
import org.sodeja.sdj.expression.Expression;
import org.sodeja.sdj.expression.Name;
import org.sodeja.sdj.expression.Number;
import org.sodeja.sdj.expression.Variable;
import org.sodeja.sdj.util.Address;

public class TiRunner {
	public TiState step(TiState original) {
		Node node = original.getHeap().lookup(original.getStack().get(0));
		if(node instanceof NumberNode) {
			return step(original, (NumberNode) node);
		} else if(node instanceof ApplyNode){
			return step(original, (ApplyNode) node);
		} else if(node instanceof SupercombinatorNode){
			return step(original, (SupercombinatorNode<Name>) node);
		}
		
		throw new IllegalArgumentException("Uknown heap elements");
	}
	
	private TiState step(TiState original, NumberNode node) {
		throw new IllegalStateException("Number applied as a function");
	}

	private TiState step(TiState original, ApplyNode node) {
		TiState result = new TiState(original);
		result.getStack().add(node.left);
		return result;
	}

	private TiState step(TiState original, SupercombinatorNode<Name> node) {
		TiState result = new TiState(original);
		
		for(int i = 0, n = node.bindings.size();i <= n;i++) {
			node.bindings.remove(0);
		}
		
		Address addr = instantiate(result, node.expression);
		
		return result;
	}
	
	private Address instantiate(TiState state, Expression<Name> expression) {
		if(expression instanceof Number) {
			return instantiateImpl(state, (Number<Name>) expression);
		} else if(expression instanceof Application) {
			return instantiateImpl(state, (Application<Name>) expression);
		} else if(expression instanceof Variable) {
			return instantiateImpl(state, (Variable<Name>) expression);
		} else {
			throw new IllegalArgumentException();
		}
	}
	
	private Address instantiateImpl(TiState state, Number<Name> number) {
		NumberNode node = new NumberNode(number);
		return state.getHeap().alloc(node);
	}
	
	private Address instantiateImpl(TiState state, Application<Name> name) {
		Address leftAddr = instantiate(state, name.left);
		Address rightAddr = instantiate(state, name.right);
		return state.getHeap().alloc(new ApplyNode(leftAddr, rightAddr));
	}
	
	private Address instantiateImpl(TiState state, Variable<Name> var) {
		return null;
	}
}

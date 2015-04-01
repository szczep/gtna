package edu.pw.elka.gtna.graph;

import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Node;

public class EdgeImpl<N extends Node> implements Edge<N> {

	N n1;
	N n2;
	
	EdgeImpl(){
	}
	
	EdgeImpl(N n1, N n2){
		this.n1 = n1;
		this.n2 = n2;
	}
	
	@Override
	public N getN1() {
		return n1;
	}

	@Override
	public N getN2() {
		return n2;
	}

	@Override
	public void setN1(N n1) {
		this.n1 = n1;	
	}

	@Override
	public void setN2(N n2) {
		this.n2 = n2;
	}

	@Override
	public N getN(N node) {
		if (node.equals(n1)) {
			return n2;
		} else if (node.equals(n2)){
			return n1;
		}
		return null;
	}


}

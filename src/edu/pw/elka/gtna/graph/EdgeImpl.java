package edu.pw.elka.gtna.graph;

public class EdgeImpl<N extends Node> implements Edge<N> {

	N n1;
	N n2;
	
	EdgeType type;
	
	EdgeImpl(EdgeType type){
		this.type = type;
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
	public boolean isDirected() {
		return (type.equals(EdgeType.DIRECTED));
	}

	@Override
	public N getAdjacent(N node) {
		if (n1.equals(node)){
			return n2;
		} else if (n2.equals(node)) {
			return n1;
		}
		return null;
	}

}

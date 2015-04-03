package edu.pw.elka.gtna.graph;

import edu.pw.elka.gtna.graph.interfaces.Node;

public class EdgeImpl<N extends Node> extends AbstractEdge<N> {
	
	EdgeImpl(){
	}
	
	EdgeImpl(N n1, N n2){
		super(n1,n2);
	}

	/**
	 * This has code ignore the order of n1 and n2
	 */
	@Override
	public int hashCode() {
		final int prime = 79;
		int result = 17;
		result = prime * result * (((n1 == null) ? 0 : n1.hashCode()) + ((n2 == null) ? 0 : n2.hashCode()));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		@SuppressWarnings("unchecked")
		EdgeImpl<N> other = (EdgeImpl<N>) obj;
		
		if (n1 == null || other.n1 == null || n2 == null || other.n2 == null)
			return false;
		
		
		N n11 = this.getN(other.getN2());
		N n22 = this.getN(other.getN1());
		
		if (!n11.equals(other.n1) || !n22.equals(other.n2))
			return false;
		
		return true;
	}
	



}

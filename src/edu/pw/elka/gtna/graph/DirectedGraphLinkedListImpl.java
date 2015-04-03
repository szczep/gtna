package edu.pw.elka.gtna.graph;

import edu.pw.elka.gtna.graph.interfaces.DirectedEdge;
import edu.pw.elka.gtna.graph.interfaces.DirectedGraph;
import edu.pw.elka.gtna.graph.interfaces.Node;


public class DirectedGraphLinkedListImpl<N extends Node,E extends DirectedEdge<N>> extends GraphLinkedListImpl<N, E> 
implements DirectedGraph<N,E> {

	DirectedGraphLinkedListImpl(){
		super();
	}
	
}

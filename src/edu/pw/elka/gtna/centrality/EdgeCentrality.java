package edu.pw.elka.gtna.centrality;

import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.Node;

abstract public class EdgeCentrality<N extends Node, E extends Edge<N>> extends AbstractCentrality<E,N,E> {

	public EdgeCentrality(Graph<N, E> graph) {
		super(graph);
	}

}

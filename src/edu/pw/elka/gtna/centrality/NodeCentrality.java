package edu.pw.elka.gtna.centrality;

import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.Node;



/**
 * @author Piotr Lech Szczepañski
 * @author P.Szczepanski@ii.pw.edu.pl 
 *
 */
public abstract class NodeCentrality<N extends Node, E extends Edge<N>> extends AbstractCentrality<N,N,E> {

	public NodeCentrality (Graph<N,E> graph){
		super(graph);
	}
	

}

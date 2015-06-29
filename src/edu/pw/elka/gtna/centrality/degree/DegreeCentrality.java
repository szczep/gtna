package edu.pw.elka.gtna.centrality.degree;

import edu.pw.elka.gtna.centrality.NodeCentrality;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.Node;


/**
 * @author Piotr Lech Szczepañski
 * @author P.Szczepanski@ii.pw.edu.pl 
 *
 */
public class DegreeCentrality<N extends Node> extends NodeCentrality<N,Edge<N>>{

	/**
	 * @param graph
	 */
	public DegreeCentrality(Graph<N,Edge<N>> graph) {
		super(graph);
	}
	

    public void computeCentrality(){
    	for(N node: graph.getNodes()){
    		centralities.put(node, (double) graph.getDegree(node)) ;
    	}
    }


}

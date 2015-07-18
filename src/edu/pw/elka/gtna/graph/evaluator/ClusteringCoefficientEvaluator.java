package edu.pw.elka.gtna.graph.evaluator;


import edu.pw.elka.gtna.graph.EdgeFactory;
import edu.pw.elka.gtna.graph.interfaces.Edge;
import edu.pw.elka.gtna.graph.interfaces.Graph;
import edu.pw.elka.gtna.graph.interfaces.Node;
import static edu.pw.elka.gtna.graph.interfaces.EdgeType.*;

public class ClusteringCoefficientEvaluator<N extends Node,E extends Edge<N>> extends AbstractGraphEvaluator<N,E> {

	public ClusteringCoefficientEvaluator(Graph<N, E> graph) {
		super(graph);
	}

	@Override
	public double evaluate() {
		
		long closedTriples = 0;
		long openTriples = 0;
		
		for (N a: graph.getNodes()){
			for (N b: graph.getNodes()){
				if (!a.equals(b)){
					for (N c: graph.getNodes()){
						if (!a.equals(c) && !b.equals(c)){
							if (graph.hasEdge((E) EdgeFactory.<N>newInstance(a, b, SIMPLE)) 
									&& graph.hasEdge((E) EdgeFactory.<N>newInstance(a, c ,SIMPLE)) 
									&& graph.hasEdge((E) EdgeFactory.<N>newInstance(b,c,SIMPLE))) {
								closedTriples++;
							}
							
							if ( (graph.hasEdge((E) EdgeFactory.<N>newInstance(a, b ,SIMPLE)) && 
								  graph.hasEdge((E) EdgeFactory.<N>newInstance(a, c ,SIMPLE))   )  ||
								  (graph.hasEdge((E) EdgeFactory.<N>newInstance(a, b ,SIMPLE)) &&
								 (graph.hasEdge((E) EdgeFactory.<N>newInstance(b, c ,SIMPLE))))  || 
								 (graph.hasEdge((E) EdgeFactory.<N>newInstance(a, c ,SIMPLE)) && 
								 (graph.hasEdge((E) EdgeFactory.<N>newInstance(b, c ,SIMPLE))) ) ) {
								openTriples++;	
							}						
						}
					}
				
				}
			}
		}
		return closedTriples/(double)openTriples;
	}

}
